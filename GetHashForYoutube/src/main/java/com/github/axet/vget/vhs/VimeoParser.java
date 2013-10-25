package com.github.axet.vget.vhs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

import com.github.axet.vget.info.VGetParser;
import com.github.axet.vget.info.VideoInfo;
import com.github.axet.vget.info.VideoInfo.States;
import com.github.axet.vget.info.VideoInfo.VideoQuality;
import com.github.axet.vget.info.VideoInfoUser;
import com.github.axet.wget.WGet;
import com.github.axet.wget.WGet.HtmlLoader;
import com.github.axet.wget.info.ex.DownloadError;

public class VimeoParser extends VGetParser {

    List<VideoDownload> sNextVideoURL = new ArrayList<VideoDownload>();

    URL source;

    public VimeoParser(URL input) {
        this.source = input;
    }

    public static boolean probe(URL url) {
        return url.toString().contains("vimeo.com");
    }

    public static String extractId(URL url) {
        // standard web url. format: "https://vimeo.com/49243107" or
        // "http://vimeo.com/channels/staffpicks/49243107"
        {
            Pattern u = Pattern.compile("vimeo.com.*/(\\d+)");
            Matcher um = u.matcher(url.toString());

            if (um.find())
                return um.group(1);
        }
        // rss feed url. format:
        // "http://vimeo.com/moogaloop.swf?clip_id=49243107"
        {
            Pattern u = Pattern.compile("vimeo.com.*=(\\d+)");
            Matcher um = u.matcher(url.toString());

            if (um.find())
                return um.group(1);
        }
        return null;
    }

    void downloadone(final VideoInfo info, final AtomicBoolean stop, final Runnable notify) {
        try {
            String id;
            String clip;
            {
                id = extractId(info.getWeb());
                if (id == null) {
                    throw new DownloadError("unknown url");
                }
                clip = "http://vimeo.com/" + id;
            }

            URL url = new URL(clip);

            String html = WGet.getHtml(url, new HtmlLoader() {
                @Override
                public void notifyRetry(int delay, Throwable e) {
                    info.setDelay(delay, e);
                    notify.run();
                }

                @Override
                public void notifyDownloading() {
                    info.setState(States.EXTRACTING);
                    notify.run();
                }

                @Override
                public void notifyMoved() {
                    info.setState(States.RETRYING);
                    notify.run();
                }
            }, stop);

            String sig;
            {
                Pattern u = Pattern.compile("\"signature\":\"([0-9a-f]+)\"");
                Matcher um = u.matcher(html);
                if (!um.find()) {
                    throw new DownloadError("unknown signature vimeo respond");
                }
                sig = um.group(1);
            }

            String exp;
            {
                Pattern u = Pattern.compile("\"timestamp\":(\\d+)");
                Matcher um = u.matcher(html);
                if (!um.find()) {
                    throw new DownloadError("unknown timestamp vimeo respond");
                }
                exp = um.group(1);
            }

            // "qualities":["hd","sd","mobile"]
            Set<String> qualities = new TreeSet<String>();
            {
                Pattern u = Pattern.compile("\"qualities\":\\[([^\\]]*\")\\]");
                Matcher um = u.matcher(html);
                if (!um.find()) {
                    throw new DownloadError("unknown qualities vimeo respond");
                }
                String list = um.group(1);
                String[] ll = list.split(",");
                for (String s : ll) {
                    qualities.add(s.replaceAll("\"", ""));
                }
            }

            String icon;
            {
                Pattern u = Pattern.compile("\"thumbnail\":\"([^\"]*)\"");
                Matcher um = u.matcher(html);
                if (!um.find()) {
                    throw new DownloadError("unknown timestamp vimeo respond");
                }
                icon = um.group(1);
                icon = StringEscapeUtils.unescapeJava(icon);
            }

            {
                Pattern u = Pattern.compile("\"title\":\"([^\"]+)\"");
                Matcher um = u.matcher(html);
                if (!um.find()) {
                    throw new DownloadError("unknown title vimeo respond");
                }
                String sTitle = um.group(1);
                sTitle = StringEscapeUtils.unescapeHtml4(sTitle);
                sTitle = StringEscapeUtils.unescapeJava(sTitle);
                info.setTitle(sTitle);
            }

            String get = "http://player.vimeo.com/play_redirect?clip_id=%s&sig=%s&time=%s&quality=%s&codecs=H264,VP8,VP6&type=moogaloop_local&embed_location=&seek=0";

            String hd = String.format(get, id, sig, exp, "hd");
            String sd = String.format(get, id, sig, exp, "sd");

            // TODO fix resolution calculation
            // https://vimeo.com/help/compression

            // can be 1080p or 720p
            if (qualities.contains("hd"))
                sNextVideoURL.add(new VideoDownload(VideoQuality.p1080, new URL(hd)));
            // can be 360p or 480p
            if (qualities.contains("sd"))
                sNextVideoURL.add(new VideoDownload(VideoQuality.p480, new URL(sd)));

            info.setIcon(new URL(icon));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void extract(VideoInfo info, VideoInfoUser user, AtomicBoolean stop, Runnable notify) {
        downloadone(info, stop, notify);
        getVideo(info, user, sNextVideoURL);
    }

}

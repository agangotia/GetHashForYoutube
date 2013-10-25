/**
 * 
 */
package com.github.gethashforyoutube;

import java.io.File;
import java.net.URL;

import com.github.axet.vget.VGet;

/**
 * @author Anupam
 *
 */
public class GetHashForYTMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            // ex: http://www.youtube.com/watch?v=Nj6PFaDmp6c
            String url = "http://www.youtube.com/watch?v=W0aE-w61Cb8";
            // ex: "/Users/axet/Downloads"
            String path = "C:\\Users\\anugan\\TEMP";
            VGet v = new VGet(new URL(url), new File(path));
            v.download();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

}

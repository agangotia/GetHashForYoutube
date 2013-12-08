/**
 * 
 */
package com.github.gethashforyoutube;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.github.axet.vget.VGet;
import com.github.gethashforyoutube.constants.ConstantStuff;
import com.github.gethashforyoutube.dumpwriter.HashWriter;
import com.github.gethashforyoutube.videourlreader.VideoUrlReader;

/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */
public class GetHashForYTMain {

	/**
	 * @param args
	 */

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
//lets try 
			VideoUrlReader objReader=new VideoUrlReader(',','"');//Created a CSV Reader for URLLIST.csv
			objReader.readValues();//Read all values into a list
			ArrayList<String> urlList=objReader.getUrlList();//returned list
			
			//Lets Create the Dump Files
            HashWriter objSHA1Dump=new HashWriter(',','"',"UTF-8",ConstantStuff.DUMP_FILE_PATH,ConstantStuff.DUMP_FILE_NAME_SHA1);
            HashWriter objMD5Dump=new HashWriter(',','"',"UTF-8",ConstantStuff.DUMP_FILE_PATH,ConstantStuff.DUMP_FILE_NAME_MD5);
            
			for(int i=0;i<urlList.size();i++){
				String urlCurrent=urlList.get(i);
				String urlNext=urlList.get(i+1);
				System.out.println("\n Reading two files"+urlCurrent);
				
				System.out.println("\n Creating Boundary Limits for Both files");
				
	            System.out.println("\n 2.Generating MD5 on these boundaries...");
                	
	            System.out.println("\n 2.Generating MD5...");
                if(ConstantStuff.IS_GEN_MD5){
              	 
              	DualFileItem objFileItem=new DualFileItem(ConstantStuff.DUMP_FILE_PATH,urlCurrent,ConstantStuff.DUMP_FILE_PATH,urlNext);
              	  objFileItem.setMD5Dump(objMD5Dump);
              	  objFileItem.setBoundaries();
                }
                i++;
          
			}
			
		System.out.println("\n 3.Closing Dump Files...");
		if(ConstantStuff.IS_GEN_MD5){
        	objMD5Dump.close();
        }
			
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

}

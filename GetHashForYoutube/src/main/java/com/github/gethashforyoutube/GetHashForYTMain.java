/**
 * 
 */
package com.github.gethashforyoutube;

import java.io.File;
import java.net.URL;

import com.github.axet.vget.VGet;
import com.github.gethashforyoutube.constants.ConstantStuff;
import com.github.gethashforyoutube.dumpwriter.HashWriter;

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
            // ex: http://www.youtube.com/watch?v=Nj6PFaDmp6c
            String url = "http://www.youtube.com/watch?v=W0aE-w61Cb8";
            // ex: "/Users/axet/Downloads"
            String path = "C:\\Users\\anugan\\TEMP";
            VGet v = new VGet(new URL(url), new File(path));
            v.download();
            
            //Lets Create the Dump Files
            HashWriter objSHA1Dump;
            HashWriter objMD5Dump;
            
            {//loop here for individual files
            	FileItem objFileItem=new FileItem("abc.mp4","C:\\Users\\anugan\\TEMP\\","a1.mp4");
            	
            	  if(ConstantStuff.IS_GEN_SHA1){
            		  objSHA1Dump=new HashWriter(',','"',"UTF-8",ConstantStuff.DUMP_FILE_PATH,ConstantStuff.DUMP_FILE_NAME_SHA1);
            		  objFileItem.setSHA1Dump(objSHA1Dump);
            		  objFileItem.generateSHA1();
            	  }
                  	
                  if(ConstantStuff.IS_GEN_MD5){
                	  objMD5Dump=new HashWriter(',','"',"UTF-8",ConstantStuff.DUMP_FILE_PATH,ConstantStuff.DUMP_FILE_NAME_MD5);
                	  objFileItem.setMD5Dump(objMD5Dump);
                	  objFileItem.generateMD5();
                  }
                  	
            }
            
            
            if(ConstantStuff.IS_GEN_SHA1){
	            	objSHA1Dump.close();
	      	 }
          
            
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

}

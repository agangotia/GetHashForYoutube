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
			
			VideoUrlReader objReader=new VideoUrlReader(',','"');//Created a CSV Reader for URLLIST.csv
			objReader.readValues();//Read all values into a list
			ArrayList<String> urlList=objReader.getUrlList();//returned list
			
			//Lets Create the Dump Files
            HashWriter objSHA1Dump=new HashWriter(',','"',"UTF-8",ConstantStuff.DUMP_FILE_PATH,ConstantStuff.DUMP_FILE_NAME_SHA1);
            HashWriter objMD5Dump=new HashWriter(',','"',"UTF-8",ConstantStuff.DUMP_FILE_PATH,ConstantStuff.DUMP_FILE_NAME_MD5);
            
			for(String urlCurrent:urlList){
				System.out.println("\n Fethed Url "+urlCurrent);
				System.out.println("\n 1.Downloading..."+urlCurrent);
				
	            VGet v = new VGet(new URL(urlCurrent), new File(ConstantStuff.DUMP_FILE_PATH));
	            v.download();
	            
	            System.out.println("\n 2.Generating SHA1...");
	            if(ConstantStuff.IS_GEN_SHA1){
          		  FileItem objFileItem=new FileItem(v.getFileName(),ConstantStuff.DUMP_FILE_PATH,v.getFileName());
          		 
          		  objFileItem.setSHA1Dump(objSHA1Dump);
          		  objFileItem.generateSHA1();
          	  }
                	
	            System.out.println("\n 2.Generating MD5...");
                if(ConstantStuff.IS_GEN_MD5){
              	  FileItem objFileItem=new FileItem(v.getFileName(),ConstantStuff.DUMP_FILE_PATH,v.getFileName());
              	  
              	  objFileItem.setMD5Dump(objMD5Dump);
              	  objFileItem.generateMD5();
                }
          
			}
			
			System.out.println("\n 3.Closing Dump Files...");
			if(ConstantStuff.IS_GEN_SHA1){
            	objSHA1Dump.close();
      	 }
        if(ConstantStuff.IS_GEN_MD5){
        	objMD5Dump.close();
        }
			/*
            // ex: http://www.youtube.com/watch?v=Nj6PFaDmp6c
            String url = "http://www.youtube.com/watch?v=W0aE-w61Cb8";
            // ex: "/Users/axet/Downloads"
            String path = "/home/anu/tmp/";
            VGet v = new VGet(new URL(url), new File(path));
            v.download();
            
            //Lets Create the Dump Files
            HashWriter objSHA1Dump;
            HashWriter objMD5Dump;
            
            {//loop here for individual files
            	
            	
            	  if(ConstantStuff.IS_GEN_SHA1){
            		  FileItem objFileItem=new FileItem(v.getFileName(),"/home/anu/tmp/",v.getFileName());
            		  objSHA1Dump=new HashWriter(',','"',"UTF-8",ConstantStuff.DUMP_FILE_PATH,ConstantStuff.DUMP_FILE_NAME_SHA1);
            		  objFileItem.setSHA1Dump(objSHA1Dump);
            		  objFileItem.generateSHA1();
            	  }
                  	
                  if(ConstantStuff.IS_GEN_MD5){
                	  FileItem objFileItem=new FileItem(v.getFileName(),"/home/anu/tmp/",v.getFileName());
                	  objMD5Dump=new HashWriter(',','"',"UTF-8",ConstantStuff.DUMP_FILE_PATH,ConstantStuff.DUMP_FILE_NAME_MD5);
                	  objFileItem.setMD5Dump(objMD5Dump);
                	  objFileItem.generateMD5();
                  }
                  	
            }
            
            
            if(ConstantStuff.IS_GEN_SHA1){
	            	objSHAif(ConstantStuff.IS_GEN_SHA1){
	            	objSHA1Dump.close();
	      	 }
            if(ConstantStuff.IS_GEN_MD5){
            	objMD5Dump.close();1Dump.close();
	      	 }
            if(ConstantStuff.IS_GEN_MD5){
            	objMD5Dump.close();
      	 }*/
            
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

}

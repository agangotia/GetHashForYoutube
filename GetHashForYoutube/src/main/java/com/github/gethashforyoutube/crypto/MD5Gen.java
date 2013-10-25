/**
 * 
 */
package com.github.gethashforyoutube.crypto;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import com.github.gethashforyoutube.constants.ConstantStuff;

/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */
public class MD5Gen {
	MessageDigest md;
	FileInputStream fis;
	byte[] dataBytes;
	
	public MD5Gen(FileInputStream fisP) throws NoSuchAlgorithmException{
		this.fis = fisP;
		
         md = MessageDigest.getInstance("MD5");
         dataBytes = new byte[ConstantStuff.BUFFER_CRYPTO_READ];
	}
	
	
	public ArrayList<String[]> fillMD5(){
	   	 int nread = 0;
	   	 int bytesReadInBlock = 0;
	   	 ArrayList<String[]> result=new ArrayList<String[]>();
	   	 
	        try {
				while ((nread = fis.read(dataBytes)) != -1) {
				   md.update(dataBytes, 0, nread);
				   bytesReadInBlock+=nread;
				   
				   if((bytesReadInBlock + ConstantStuff.BUFFER_CRYPTO_READ)>ConstantStuff.BLOCK_SIZE){
					   String[] temp=new String[2];
					   temp[0]=Integer.toString(bytesReadInBlock);
					   temp[1]=calculateMd5( md.digest());
					   result.add(temp);
					   
					   md.reset();
					   bytesReadInBlock=0;
				   }
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			};
			return result;
	    }
	
	public String calculateMd5(byte[] mdbytes){
		  //convert the byte to hex format method 2
	       StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<mdbytes.length;i++) {
	    	  hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
	    	}
	 
	    	System.out.println("Hex format : " + hexString.toString());
	   
	        return hexString.toString();
	}

}

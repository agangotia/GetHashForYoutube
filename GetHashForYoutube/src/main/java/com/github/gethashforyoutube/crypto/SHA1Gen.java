package com.github.gethashforyoutube.crypto;
/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.github.gethashforyoutube.constants.ConstantStuff;
import com.github.gethashforyoutube.dumpwriter.HashWriter;

public class SHA1Gen {
	MessageDigest md;
	FileInputStream fis;
	byte[] dataBytes;
	
	public SHA1Gen(FileInputStream fisP) throws NoSuchAlgorithmException{
		this.fis = fisP;
		
         md = MessageDigest.getInstance("SHA-256");
         dataBytes = new byte[ConstantStuff.BUFFER_CRYPTO_READ];
	}
	
	public String calculateSHA1(){
   	 int nread = 0; 
        try {
			while ((nread = fis.read(dataBytes)) != -1) {
			   md.update(dataBytes, 0, nread);
			 }fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "NA";
		};
		
        byte[] mdbytes = md.digest();
        //convert the byte to hex format method 2
       StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<mdbytes.length;i++) {
    	  hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
    	}
 
    	System.out.println("Hex format : " + hexString.toString());
   
        return hexString.toString();
   	 
    }

}

package com.github.gethashforyoutube;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;

import com.github.gethashforyoutube.crypto.SHA1Gen;
import com.github.gethashforyoutube.dumpwriter.HashWriter;

/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */
public class FileItem {
	private String fileNameItem;//Original File Name
	FileInputStream fisToreadFile;//This is the input stream to read file
	HashWriter SHA1Dump;
	HashWriter MD5Dump;
	
	FileItem(String fileNameItemP,String PathOfFileToFetchInputStreamP,String fileNameToFetchInputStreamP) throws FileNotFoundException{
		
		this.fisToreadFile = new FileInputStream(PathOfFileToFetchInputStreamP+fileNameToFetchInputStreamP);
		this.fileNameItem=fileNameItemP;
	}
	
	public String getFileNameItem() {
		return fileNameItem;
	}
	public void setFileNameItem(String fileNameItem) {
		this.fileNameItem = fileNameItem;
	}

	public FileInputStream getFisToreadFile() {
		return fisToreadFile;
	}
	public void setFisToreadFile(FileInputStream fisToreadFile) {
		this.fisToreadFile = fisToreadFile;
	}
	public HashWriter getSHA1Dump() {
		return SHA1Dump;
	}
	public void setSHA1Dump(HashWriter sHA1Dump) {
		SHA1Dump = sHA1Dump;
	}
	public HashWriter getMD5Dump() {
		return MD5Dump;
	}
	public void setMD5Dump(HashWriter mD5Dump) {
		MD5Dump = mD5Dump;
	}
	
	public int generateSHA1() throws NoSuchAlgorithmException{
		if (fisToreadFile!=null && SHA1Dump!=null){
			SHA1Gen OBJsha1=new SHA1Gen(fisToreadFile);
			SHA1Dump.writeToDump("col1","col2",OBJsha1.calculateSHA1());
			return 0;
		}else{
			return 1;
		}
			
		
	}
	public void generateMD5(){
		
	}
	

}

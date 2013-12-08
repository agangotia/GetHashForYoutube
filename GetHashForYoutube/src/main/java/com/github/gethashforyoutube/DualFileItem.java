package com.github.gethashforyoutube;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.github.gethashforyoutube.constants.ConstantStuff;
import com.github.gethashforyoutube.dumpwriter.HashWriter;

public class DualFileItem {
	private String fileNameItem1;//Original File Name
	private FileInputStream fisToreadFile1;//This is the input stream to read file
	private String fileNameItem2;//Original File Name
	private FileInputStream fisToreadFile2;//This is the input stream to read file
	ArrayList<Integer> BlockBoundary1;
	ArrayList<Integer> BlockBoundary2;
	byte[] dataBytes1;
	byte[] dataBytes2;
	HashWriter MD5Dump;
	MessageDigest md1;

	
	
	public HashWriter getMD5Dump() {
		return MD5Dump;
	}

	public void setMD5Dump(HashWriter mD5Dump) {
		MD5Dump = mD5Dump;
	}

	DualFileItem(String PathOfFileToFetchInputStreamP,String fileNameToFetchInputStreamP,String PathOfFileToFetchInputStreamP2,String fileNameToFetchInputStreamP2) throws FileNotFoundException, NoSuchAlgorithmException{
		System.out.println(PathOfFileToFetchInputStreamP+fileNameToFetchInputStreamP);
		this.fisToreadFile1 = new FileInputStream(PathOfFileToFetchInputStreamP+fileNameToFetchInputStreamP);
		this.fileNameItem1=fileNameToFetchInputStreamP;
		
		this.fisToreadFile2 = new FileInputStream(PathOfFileToFetchInputStreamP2+fileNameToFetchInputStreamP2);
		this.fileNameItem2=fileNameToFetchInputStreamP2;
		dataBytes1 = new byte[ConstantStuff.BUFFER_CRYPTO_READ];
		dataBytes2 = new byte[ConstantStuff.BUFFER_CRYPTO_READ];
		 md1 = MessageDigest.getInstance("MD5");

	}

	public String getFileNameItem1() {
		return fileNameItem1;
	}
	public void setFileNameItem1(String fileNameItem1) {
		this.fileNameItem1 = fileNameItem1;
	}
	public FileInputStream getFisToreadFile1() {
		return fisToreadFile1;
	}
	public void setFisToreadFile1(FileInputStream fisToreadFile1) {
		this.fisToreadFile1 = fisToreadFile1;
	}
	public String getFileNameItem2() {
		return fileNameItem2;
	}
	public void setFileNameItem2(String fileNameItem2) {
		this.fileNameItem2 = fileNameItem2;
	}
	public FileInputStream getFisToreadFile2() {
		return fisToreadFile2;
	}
	public void setFisToreadFile2(FileInputStream fisToreadFile2) {
		this.fisToreadFile2 = fisToreadFile2;
	}
	public ArrayList<Integer> getBlockBoundary1() {
		return BlockBoundary1;
	}
	public void setBlockBoundary1(ArrayList<Integer> blockBoundary1) {
		BlockBoundary1 = blockBoundary1;
	}
	public ArrayList<Integer> getBlockBoundary2() {
		return BlockBoundary2;
	}
	public void setBlockBoundary2(ArrayList<Integer> blockBoundary2) {
		BlockBoundary2 = blockBoundary2;
	}

	public void setBoundaries() {
		 int nread = 0;//number of bytes read in stream 1
		 int nread2 = 0;//number of bytes read in stream 2
		 ArrayList<Byte> tempList=new ArrayList<Byte>();//temporary arraylist of common bytes
		try {
			nread = fisToreadFile1.read(dataBytes1);//initial read
			nread2 = fisToreadFile2.read(dataBytes2);
			while ((nread != -1) || (nread2!= -1)) {//loop untill any of the read yields eof
				int limitRead=(nread>nread2?nread2:nread);//set limit as the lowest of both the read lengths
				for(int i=0;i<limitRead;i++){
					if(dataBytes1[i]!=dataBytes2[i]){
						if(tempList.size()>1){
							int j=0;
							byte[] result=new byte[tempList.size()];
							for(Byte temp:tempList){
								result[j++]=(byte)temp;
							}
							md1.update(result);
							String finalresult=calculateMd5( md1.digest());
							MD5Dump.writeToDump(fileNameItem1,""+tempList.size(),finalresult);
							MD5Dump.writeToDump(fileNameItem2,""+tempList.size(),finalresult);
							md1.reset();
						}
						tempList.clear();
					}else{
						tempList.add(dataBytes1[i]);
					}
				}//for loop end
				//while increment conditions
				nread = fisToreadFile1.read(dataBytes1);
				nread2 = fisToreadFile1.read(dataBytes2);
			}//while loop end
			
			if(tempList.size()>1){
				int j=0;
				byte[] result=new byte[tempList.size()];
				for(Byte temp:tempList){
					result[j++]=(byte)temp;
				}
				md1.update(result);
				String finalresult=calculateMd5( md1.digest());
				MD5Dump.writeToDump(fileNameItem1,""+tempList.size(),finalresult);
				MD5Dump.writeToDump(fileNameItem2,""+tempList.size(),finalresult);
				md1.reset();
			}
			tempList.clear();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String calculateMd5(byte[] mdbytes){
		  //convert the byte to hex format method 2
	       StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<mdbytes.length;i++) {
	    	  hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
	    	}
	 
	    	//System.out.println("Hex format : " + hexString.toString());
	   
	        return hexString.toString();
	}

}

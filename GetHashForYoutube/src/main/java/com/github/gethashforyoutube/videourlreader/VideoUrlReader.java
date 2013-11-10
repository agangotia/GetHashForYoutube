/**
 * 
 */
package com.github.gethashforyoutube.videourlreader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.gethashforyoutube.constants.ConstantStuff;


import au.com.bytecode.opencsv.CSVReader;
/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */
public class VideoUrlReader {
	CSVReader reader;
	ArrayList<String> urlList;
	
	
	public ArrayList<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(ArrayList<String> urlList) {
		this.urlList = urlList;
	}

	public VideoUrlReader(char delimiter,char quotechar) throws FileNotFoundException{
		reader = new CSVReader(new FileReader(ConstantStuff.URL_FILE_NAME),delimiter,quotechar);
		urlList=new ArrayList<String>();
		//reader = new CSVReader(new FileReader(ConstantStuff.URL_FILE_NAME));
	}
	
	public void readValues() throws IOException{
		String [] nextLine;
	    while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	    	//System.out.println(nextLine[0]);
	        urlList.add(nextLine[0].toString());
	    }
	
	}
    
}

package com.github.gethashforyoutube.dumpwriter;
/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */
import java.io.IOException;

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVWriter;
/**
 * @author Anupam
 *
 */
public class HashWriter {
	CSV fileDump;
	CSVWriter fileWriter;
	
	
	public HashWriter(char delimiter,char quotechar, String charset,String FilePath,String fileName ){
		fileDump =CSV 
				.separator(delimiter)
                .quote(quotechar)
                .charset(charset)
                .create();
		
		fileWriter=fileDump.writer(FilePath+fileName);
		
	}
	public void writeToDump(String... colArray){
		//writeToDump("a", "b", "c"); call like this
		fileWriter.writeNext(colArray);
		
	}
	public void close() throws IOException{
		//writeToDump("a", "b", "c"); call like this
		fileWriter.close();
		
	}
}

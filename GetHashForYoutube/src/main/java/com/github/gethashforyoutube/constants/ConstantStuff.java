package com.github.gethashforyoutube.constants;
/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */
public interface ConstantStuff {
	public static final String DUMP_FILE_PATH="/home/anu/tmp/";
	public static final String URL_FILE_NAME="URLLIST.csv";
	public static final String DUMP_FILE_NAME_SHA1="DUMPSHA1.csv";
	public static final String DUMP_FILE_NAME_MD5="DUMPMD5.csv";
	
	public static final boolean IS_GEN_SHA1=true;
	public static final boolean IS_GEN_MD5=true;
	
	public static final int BUFFER_CRYPTO_READ=1024;
	public static final int BLOCK_SIZE=4*1024;

}

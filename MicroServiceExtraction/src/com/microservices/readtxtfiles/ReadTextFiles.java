package com.microservices.readtxtfiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.microservice.createlogfiles.CreatingXesFiles;

public class ReadTextFiles {

	//This is to read the text files that contains the Package names and the tablenames
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 final File folder = new File("C:/Test");
			listFilesForFolder(folder);
	}

	
	
	
	 public static void listFilesForFolder(final File folder) throws Exception {
		    for (final File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		            listFilesForFolder(fileEntry);
		        } else {
		            
		           
		            String ext1 = FilenameUtils.getExtension(fileEntry.getAbsolutePath());
		            if(ext1.equalsIgnoreCase("txt")){
		            	String aa = FileUtils.readFileToString(
		            			new File(fileEntry.getAbsolutePath()), "UTF-8");

		            	String lines[] = aa.split(",");
		            	
		             CreatingXesFiles.creatXESFilesForPackageAndTable(lines);
		        }
		    }
		}
	 } 
}

package com.microservice.createlogfiles;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CreatingXesFiles {
	
	
	public static void creatXESFiles(ArrayList<String[]> allDetailsAboutExecution, ArrayList<String[]> allDetailsAboutQueryAndTime) throws Exception{
		
		 List<String> listWithClassnameAndMethod = new ArrayList<String>(); //This is to get XES file to generate the diagram
		 List<String> listWithClassnameAndMethodAndQuery = new ArrayList<String>(); // This will be used in the future when processing the diagram.
  	   	 List<String> listWithFolderNames = new ArrayList<String>(); 
		 
  	   for(int i=0;i<allDetailsAboutExecution.size();i++){
  		   	   String[] allExecutionDetails = allDetailsAboutExecution.get(i);
  		   	   String [] allQueryDetails = allDetailsAboutQueryAndTime.get(i);
  		   
  			   String[] methodtemp = allExecutionDetails[1].split(" ");
  			   String queriesused = "";
  			   String executiontime = "";
  			 
  			   for(int a=0;a<allQueryDetails.length;a++){
  				   
  				   if(allQueryDetails[a].contains("Execution")){
  					 executiontime = executiontime+"$$"+allQueryDetails[a];
  				   }else{
  					 queriesused = queriesused+"##"+allQueryDetails[a];
  					 System.out.println(allQueryDetails[a]);
  				   }
  			   }
  		
  			   listWithClassnameAndMethodAndQuery.add(allExecutionDetails[0]+"\\"+methodtemp[1]+"()"+queriesused+executiontime);
  			   listWithClassnameAndMethod.add(allExecutionDetails[0]+"\\"+methodtemp[1]+"()");
  			   //System.out.println(allExecutionDetails[0]);
  			   
  			 String [] temp = allExecutionDetails[0].split(Pattern.quote(File.separator));
  			 String foldersNames = "";
  			 for(int a = 0; a<temp.length-1;a++){
  				foldersNames = foldersNames+"_"+temp[a];
  			 }
  			 
  			listWithFolderNames.add(foldersNames);
  			
  			   
  	   }
  	   
  	 
  	   
  	   
  	   
  	 File file = new File("C:\\Test\\"+"Testing"+".xes");
		file.getParentFile().mkdir();
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try{
		    PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		    String xesText ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+"\n"+
		    "<log xes.version=\"1.0\" xes.features=\"nested-attributes\" openxes.version=\"1.0RC7\" xmlns=\"http://www.xes-standard.org/\">"+"\n"+
		    		"<extension name=\"Lifecycle\" prefix=\"lifecycle\" uri=\"http://www.xes-standard.org/lifecycle.xesext\"/>"+"\n"+
		    		"<extension name=\"Organizational\" prefix=\"org\" uri=\"http://www.xes-standard.org/org.xesext\"/>"+"\n"+
		    		"<extension name=\"Time\" prefix=\"time\" uri=\"http://www.xes-standard.org/time.xesext\"/>"+"\n"+
		    		"<extension name=\"Concept\" prefix=\"concept\" uri=\"http://www.xes-standard.org/concept.xesext\"/>"+"\n"+
		    		"<extension name=\"Semantic\" prefix=\"semantic\" uri=\"http://www.xes-standard.org/semantic.xesext\"/>"+"\n"+
		    		"<global scope=\"trace\">"+"\n"+
		    			"<string key=\"concept:name\" value=\"__INVALID__\"/>"+"\n"+
		    		"</global>"+"\n"+
		    		"<global scope=\"event\">"+"\n"+
		    			"<string key=\"concept:name\" value=\"__INVALID__\"/>"+"\n"+
		    			"<string key=\"lifecycle:transition\" value=\"complete\"/>"+"\n"+
		    		"</global>"+"\n"+
		    		"<classifier name=\"MXML Legacy Classifier\" keys=\"concept:name lifecycle:transition\"/>"+"\n"+
		    		"<classifier name=\"Event Name\" keys=\"concept:name\"/>"+"\n"+
		    		"<classifier name=\"Resource\" keys=\"org:resource\"/>"+"\n"+
		    		"<string key=\"source\" value=\"Rapid Synthesizer\"/>"+"\n"+
		    		"<string key=\"concept:name\" value=\"excercise1.mxml\"/>"+"\n"+
		    		"<string key=\"lifecycle:model\" value=\"standard\"/>"+"\n"+
		    		"<trace>"+"\n"+
		    			"<string key=\"concept:name\" value=\"Case2.0\"/>";
		    
		    for(int i=0;i<listWithFolderNames.size();i++){
		    	xesText = xesText+"<event>"+"\n"+
		    "<string key=\"org:resource\" value=\"UNDEFINED\"/>"+"\n"+
		    "<date key=\"time:timestamp\" value=\"2008-12-09T08:20:01.527+01:00\"/>"+"\n"+
		    "<string key=\"concept:name\" value=\""+listWithFolderNames.get(i)+"\"/>"+"\n"+
			"<string key=\"lifecycle:transition\" value=\"complete\"/>"+"\n"+"</event>"; 	
		    	
		    }
		    		
		    xesText = xesText+"</trace>"+"\n"+"</log>";
		   
		    //writer.println(allTheInformationOfMethods);
		    writer.println(xesText);
		    writer.close();
		    
		} catch (IOException e) {
		   // do something
		}
  	   
  	   
  	   
	}
	
	
	
	
	//This is for evaluating the log files based on alistiars requirment to get the queries
	public static void creatXESFiles(ArrayList<String[]> allDetailsAboutExecution, ArrayList<String[]> allDetailsAboutQueryAndTime, String name) throws Exception{
		
		 List<String> listWithClassnameAndMethod = new ArrayList<String>(); //This is to get XES file to generate the diagram
		 List<String> listWithClassnameAndMethodAndQuery = new ArrayList<String>(); // This will be used in the future when processing the diagram.
 	   
		 List<String> queriesUsedInExecution = new ArrayList<String>();
		 
 	   for(int i=0;i<allDetailsAboutExecution.size();i++){
 		   	   String[] allExecutionDetails = allDetailsAboutExecution.get(i);
 		   	   String [] allQueryDetails = allDetailsAboutQueryAndTime.get(i);
 		   
 			   String[] methodtemp = allExecutionDetails[1].split(" ");
 			   String queriesused = "";
 			   String executiontime = "";
 			 
 			   for(int a=0;a<allQueryDetails.length;a++){
 				   
 				   if(allQueryDetails[a].contains("Execution")){
 					 executiontime = executiontime+"$$"+allQueryDetails[a];
 				   }else{
 					 queriesused = queriesused+"##"+allQueryDetails[a];
 					 queriesUsedInExecution.add(allQueryDetails[a]);
 					 System.out.println(allQueryDetails[a]);
 				   }
 			   }
 		
 			   listWithClassnameAndMethodAndQuery.add(allExecutionDetails[0]+"\\"+methodtemp[1]+"()"+queriesused+executiontime);
 			   listWithClassnameAndMethod.add(allExecutionDetails[0]+"\\"+methodtemp[1]+"()");
 			   
 	   }
 	   
 	 
 	   
 	   
 	   
 	 File file = new File("C:\\Test\\"+name+".txt");
		file.getParentFile().mkdir();
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try{
		    PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		    String xesText =" ";
		    
		    for(int i=0;i<queriesUsedInExecution.size();i++){
		    	writer.println(queriesUsedInExecution.get(i)); 	
		    	writer.write(System.getProperty( "line.separator" ));
		    }
		    		
		  
		    //writer.println(allTheInformationOfMethods);
		 
		    writer.close();
		    
		} catch (IOException e) {
		   // do something
		}
 	   
 	   
 	   
	}
	

	
	
	//Create the text file with the class name and the query so we can get the execution path
		public static void creatTextFile(ArrayList<String> allDetailsAboutExecution, String fileName) throws Exception{
			
	 	 
	 	   
	 	   
	 	   
	 	 File file = new File("C:\\Test\\"+fileName+".txt");
			file.getParentFile().mkdir();
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try{
			    PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		
			    
			    for(int i=0;i<allDetailsAboutExecution.size();i++){
			    	//String array[] = allDetailsAboutExecution.get(i).split("######");
			    	writer.println(allDetailsAboutExecution.get(i)); 	
			    	//writer.write(System.getProperty( "line.separator" ));
			    }
			    		
			  
			    //writer.println(allTheInformationOfMethods);
			 
			    writer.close();
			    
			} catch (IOException e) {
			   // do something
			}
	 	   
	 	   
	 	   
		}
		
		
		
		//This is used to create the XES file which contains the Package and Query
		public static void creatXESFilesForPackageAndTable(String [] array, String fileName) throws Exception{
			
			
	  	 
	  	   
	  	   
	  	   
	  	 File file = new File("C:\\Test\\"+fileName+".xes");
			file.getParentFile().mkdir();
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try{
			    PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
			    String xesText ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+"\n"+
			    "<log xes.version=\"1.0\" xes.features=\"nested-attributes\" openxes.version=\"1.0RC7\" xmlns=\"http://www.xes-standard.org/\">"+"\n"+
			    		"<extension name=\"Lifecycle\" prefix=\"lifecycle\" uri=\"http://www.xes-standard.org/lifecycle.xesext\"/>"+"\n"+
			    		"<extension name=\"Organizational\" prefix=\"org\" uri=\"http://www.xes-standard.org/org.xesext\"/>"+"\n"+
			    		"<extension name=\"Time\" prefix=\"time\" uri=\"http://www.xes-standard.org/time.xesext\"/>"+"\n"+
			    		"<extension name=\"Concept\" prefix=\"concept\" uri=\"http://www.xes-standard.org/concept.xesext\"/>"+"\n"+
			    		"<extension name=\"Semantic\" prefix=\"semantic\" uri=\"http://www.xes-standard.org/semantic.xesext\"/>"+"\n"+
			    		"<global scope=\"trace\">"+"\n"+
			    			"<string key=\"concept:name\" value=\"__INVALID__\"/>"+"\n"+
			    		"</global>"+"\n"+
			    		"<global scope=\"event\">"+"\n"+
			    			"<string key=\"concept:name\" value=\"__INVALID__\"/>"+"\n"+
			    			"<string key=\"lifecycle:transition\" value=\"complete\"/>"+"\n"+
			    		"</global>"+"\n"+
			    		"<classifier name=\"MXML Legacy Classifier\" keys=\"concept:name lifecycle:transition\"/>"+"\n"+
			    		"<classifier name=\"Event Name\" keys=\"concept:name\"/>"+"\n"+
			    		"<classifier name=\"Resource\" keys=\"org:resource\"/>"+"\n"+
			    		"<string key=\"source\" value=\"Rapid Synthesizer\"/>"+"\n"+
			    		"<string key=\"concept:name\" value=\"excercise1.mxml\"/>"+"\n"+
			    		"<string key=\"lifecycle:model\" value=\"standard\"/>"+"\n"+
			    		"<trace>"+"\n"+
			    			"<string key=\"concept:name\" value=\"Case2.0\"/>";
			    
			    for(int i=0;i<array.length;i++){
			    	xesText = xesText+"<event>"+"\n"+
			    "<string key=\"org:resource\" value=\"UNDEFINED\"/>"+"\n"+
			    "<date key=\"time:timestamp\" value=\"2008-12-09T08:20:01.527+01:00\"/>"+"\n"+
			    "<string key=\"concept:name\" value=\""+array[i]+"\"/>"+"\n"+
				"<string key=\"lifecycle:transition\" value=\"complete\"/>"+"\n"+"</event>"; 	
			    	
			    }
			    		
			    xesText = xesText+"</trace>"+"\n"+"</log>";
			   
			    //writer.println(allTheInformationOfMethods);
			    writer.println(xesText);
			    writer.close();
			    
			} catch (IOException e) {
			   // do something
			}
	  	   
	  	   
	  	   
		}
		
		
		
		public static void creatXESFilesForPackageAndTable(String [] array) throws Exception{
			
			
		  	 
		  	   
		  	   
		  	   
		  	 File file = new File("C:\\Test\\"+"Testing"+".xes");
				file.getParentFile().mkdir();
				try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try{
				    PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
				    String xesText ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+"\n"+
				    "<log xes.version=\"1.0\" xes.features=\"nested-attributes\" openxes.version=\"1.0RC7\" xmlns=\"http://www.xes-standard.org/\">"+"\n"+
				    		"<extension name=\"Lifecycle\" prefix=\"lifecycle\" uri=\"http://www.xes-standard.org/lifecycle.xesext\"/>"+"\n"+
				    		"<extension name=\"Organizational\" prefix=\"org\" uri=\"http://www.xes-standard.org/org.xesext\"/>"+"\n"+
				    		"<extension name=\"Time\" prefix=\"time\" uri=\"http://www.xes-standard.org/time.xesext\"/>"+"\n"+
				    		"<extension name=\"Concept\" prefix=\"concept\" uri=\"http://www.xes-standard.org/concept.xesext\"/>"+"\n"+
				    		"<extension name=\"Semantic\" prefix=\"semantic\" uri=\"http://www.xes-standard.org/semantic.xesext\"/>"+"\n"+
				    		"<global scope=\"trace\">"+"\n"+
				    			"<string key=\"concept:name\" value=\"__INVALID__\"/>"+"\n"+
				    		"</global>"+"\n"+
				    		"<global scope=\"event\">"+"\n"+
				    			"<string key=\"concept:name\" value=\"__INVALID__\"/>"+"\n"+
				    			"<string key=\"lifecycle:transition\" value=\"complete\"/>"+"\n"+
				    		"</global>"+"\n"+
				    		"<classifier name=\"MXML Legacy Classifier\" keys=\"concept:name lifecycle:transition\"/>"+"\n"+
				    		"<classifier name=\"Event Name\" keys=\"concept:name\"/>"+"\n"+
				    		"<classifier name=\"Resource\" keys=\"org:resource\"/>"+"\n"+
				    		"<string key=\"source\" value=\"Rapid Synthesizer\"/>"+"\n"+
				    		"<string key=\"concept:name\" value=\"excercise1.mxml\"/>"+"\n"+
				    		"<string key=\"lifecycle:model\" value=\"standard\"/>"+"\n"+
				    		"<trace>"+"\n"+
				    			"<string key=\"concept:name\" value=\"Case2.0\"/>";
				    
				    for(int i=0;i<array.length;i++){
				    	xesText = xesText+"<event>"+"\n"+
				    "<string key=\"org:resource\" value=\"UNDEFINED\"/>"+"\n"+
				    "<date key=\"time:timestamp\" value=\"2008-12-09T08:20:01.527+01:00\"/>"+"\n"+
				    "<string key=\"concept:name\" value=\""+array[i]+"\"/>"+"\n"+
					"<string key=\"lifecycle:transition\" value=\"complete\"/>"+"\n"+"</event>"; 	
				    	
				    }
				    		
				    xesText = xesText+"</trace>"+"\n"+"</log>";
				   
				    //writer.println(allTheInformationOfMethods);
				    writer.println(xesText);
				    writer.close();
				    
				} catch (IOException e) {
				   // do something
				}
		  	   
		  	   
		  	   
			}
	
}

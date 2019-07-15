package com.microservice.logevaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.microservice.createlogfiles.CreatingXesFiles;
import com.microservice.drawflow.MethodFlowVisualization;
import com.microservice.processdiagramxml.XmlProcessingOfDependencyDiagram;

public class Logevaluation {
	//This class is responsible for readiing the log file and extracting the necessary information susch as 
	//class/Method/Line/Query/Execution time from the log file
	
public static String fileName = "";
	public static void main(String[] args) throws Exception {
        // TODO code application logic here
        final File folder = new File("C:/Test_files1/");
        //readingTheTxtFilesWithCalssPaths(folder);
        listFilesForFolder(folder);//Handling the value extration from event logs
		
		//XmlProcessingOfDependencyDiagram.readingXmlFile("C:/Test/Sales.xml");
    }
    
    
    
    public static void listFilesForFolder(final File folder) throws Exception {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            
	           
	            String ext1 = FilenameUtils.getExtension(fileEntry.getAbsolutePath());
	            if(ext1.equalsIgnoreCase("log")){
	            	String aa = FileUtils.readFileToString(
	            			new File(fileEntry.getAbsolutePath()), "UTF-8");
	            	fileName = FilenameUtils.removeExtension(fileEntry.getName());
	            	//System.out.println(fileName);
	            	
	            	List<String> list = new ArrayList<String>();   
                        //Pattern p = Pattern.compile("(Class:)(.*?)(Class:)",Pattern.DOTALL); 
	            	Pattern p = Pattern.compile("(Class:)(.*?)(@@@)",Pattern.DOTALL);
                        Matcher m = p.matcher(aa);
                        while (m.find()) {
                            //System.out.println("Value: "+m.group(2));
                            list.add(m.group(2));

                        }
		
                        processingToGetClassMethodAndQuery(list, fileName);
                        
	            	
	            	}
	            
	            
	            
	        }
	    }
	}
    
    
    
    //This is used to extract the Class, Method Line number and Query for future processing.
    public static void processingToGetClassMethodAndQuery(List<String> list,String file){
    	
    	//The arraylist of array to store the details about class[0]/ Method[1]/ Line[2] / Query[3]/execution time[4]
    	 ArrayList<String[]> allDetailsAboutExecution = new ArrayList<String[]>();
    	 ArrayList <String[]> allQueryDetails =  new ArrayList<String[]>(); 
    	 
    	 
    	 //Array list with the Class Path and the Query so we can get the database tables
         ArrayList <String> tempArrayListClassPathAndQuery = new ArrayList<String>();
    	 
    	 for(int i=0;i<list.size();i++){
    		 String temp = list.get(i);
    		 String[] temparray = temp.split("###");
    		 allDetailsAboutExecution.add(temparray);
    		 
    		 
    		 //--- This Part is to manage the queries and executions time---//
    		 String aa = temparray[3].replaceAll("\\d{2}/\\d{2}/\\d{2}", "###");
    		 
    		
    		 
    		 Pattern p = Pattern.compile("(Query:)(.*?)(###)",Pattern.DOTALL);
             Matcher m = p.matcher(aa);
             
            
             
             ArrayList <String> tempArrayList = new ArrayList<String>();
             while (m.find()) {
            	 //System.out.println("2 :"+temparray[0]);
                 //System.out.println("Value: "+i+" "+m.group(2));
                 String value =  m.group(2);
                 tempArrayList.add(value);
                 
                 if(value.contains("Execution")){
                	 System.out.println("In");
                 }else{
                	 
                	 String tempt = temparray[0]+"######"+temparray[1]+"######"+m.group(2)+"@@@";
                	 //String tempt = temparray[0]+"######"+m.group(2)+"@@@";
                	 tempArrayListClassPathAndQuery.add(tempt);
                 }
                 
                 //list.add(m.group(2));

             }
             
//             if(!m.find()){
//            	 String tempt = temparray[0]+"######"+temparray[1]+"@@@";
//            	 tempArrayListClassPathAndQuery.add(tempt);
//             }
             
             
             String []tt = tempArrayList.toArray(new String[tempArrayList.size()]);
             allQueryDetails.add(i, tt);
             
             
             
            
            
             
             
           //--- This Part is to manage the queries and executions time---//
    		
    	 }
    	
    	 
    	 
    	 
    	 
    	 //To get the Xes File 
    	 try {
    		 System.out.println(tempArrayListClassPathAndQuery.size());
    		 CreatingXesFiles.creatTextFile(tempArrayListClassPathAndQuery,file);
    	 // CreatingXesFiles.creatXESFiles(allDetailsAboutExecution,allQueryDetails);
    		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    
    
    
    //This is developed for subgraph mining in the campaign patterns
    public static void readingTheTxtFilesWithCalssPaths(final File folder) throws Exception {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            
	           
	            String ext1 = FilenameUtils.getExtension(fileEntry.getAbsolutePath());
	            if(ext1.equalsIgnoreCase("txt")){
	            	String aa = FileUtils.readFileToString(
	            			new File(fileEntry.getAbsolutePath()), "UTF-8");
	            	fileName = FilenameUtils.removeExtension(fileEntry.getName());
	            	//System.out.println(fileName);
	            	
	            	List<String> list = new ArrayList<String>();   
                    aa = aa.replaceAll(System.getProperty( "line.separator" ), "");
                    aa = aa.replace("\n", "").replace("\r", "");
	            	//String [] array = aa.split(System.getProperty( "line.separator" ));
	            	String [] array = aa.split(",");
	            	CreatingXesFiles.creatXESFilesForPackageAndTable(array,fileName);
	            	}
	            
	            
	            
	        }
	    }
	}
    
    
    
    
    
}

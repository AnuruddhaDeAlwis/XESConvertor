package com.microservice.processdiagramxml;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.SingleFieldDeclaration;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class XmlProcessingOfDependencyDiagram {
	
	public static void readingXmlFile(String path) throws Exception{
		List<String> allActivities = new ArrayList<String>();
		List<Integer> allIndexes = new ArrayList<Integer>();
		List<Integer> allTargetIndex = new ArrayList<Integer>();
		List<Integer> allSourceIndex = new ArrayList<Integer>();
		List<Integer> allMaxRepetitions = new ArrayList<Integer>();
		
		
		  Document doc = new SAXBuilder(  ).build(path);
		  Element rootNode = doc.getRootElement();    
		  
		  List processMap = rootNode.getChildren();
		  Element MainNode = (Element)processMap.get(1);
		  Element MainEdge = (Element)processMap.get(2);
		  
		  
		  List Nodes = MainNode.getChildren();
		  List Edges = MainEdge.getChildren();
		  
		  
		  for(int i=0;i<Nodes.size()-2;i++){
			  Element singelNode = (Element)Nodes.get(i);
			  String activity = singelNode.getAttributeValue("activity");
			  allActivities.add(activity);
			  int index = Integer.parseInt(singelNode.getAttributeValue("index"));
			  allIndexes.add(index);
					  
			  
		  }
		  
		  
		  for(int i=0;i<Edges.size();i++){
			  Element singelEdge = (Element)Edges.get(i);
			  int targetIndex = Integer.parseInt(singelEdge.getAttributeValue("targetIndex"));
			  allTargetIndex.add(targetIndex);
			  int sourceIndex = Integer.parseInt(singelEdge.getAttributeValue("sourceIndex"));
			  allSourceIndex.add(sourceIndex);
			  int maxRepetitions = Integer.parseInt(singelEdge.getChild("Frequency").getAttributeValue("maxRepetitions"));
			  allMaxRepetitions.add(maxRepetitions);
		  }
		  
		  
		  
	}

}

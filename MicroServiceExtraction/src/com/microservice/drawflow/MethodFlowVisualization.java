package com.microservice.drawflow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;


import com.mxgraph.shape.mxArrowShape;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;



public class MethodFlowVisualization extends JFrame {

	//This class is resposible for creating flow diagrams from the log files. However these diagrams are drwan by me and will not work 100% 
	
	public static void visualization(ArrayList<String[]> allDetailsAboutExecution) throws Exception{
		
		   mxGraph graph = new mxGraph();
	       Object parent = graph.getDefaultParent();
	       graph.getModel().beginUpdate();
	       
	       
	       // Trying to create custom shapes
	        //Hashtable<String, Object> baseStyle = new Hashtable<String, Object>();
	        //baseStyle.put(mxConstants.STYLE_SHAPE, mxConstants.PERIMETER_ELLIPSE);
	        //mxStylesheet stylesheet = graph.getStylesheet();
	        //stylesheet.putCellStyle("MyStyle", baseStyle);
	       

	       
	       
	     
	       
	       
	       try{
	           //Object v1 = graph.insertVertex(parent, null, "Hellow", 20, 20, 80, 30);
	           //Object v2 = graph.insertVertex(parent, null, "World", 150, 20, 80, 30);
	           //graph.insertEdge(parent, null, "Edge", v1, v2);
	    	   
	    	   //This contains all the values that we should visualize in the flow diagram. Class/Method/LineNo/Query/Executiontime
	    	   List<String> listWithClassnameAndMethod = new ArrayList<String>();
	    	   
	    	   for(int i=0;i<allDetailsAboutExecution.size();i++){
	    		   	   String[] temparray = allDetailsAboutExecution.get(i);
	    		   
	    			   String[] methodtemp = temparray[1].split(" ");
	    			   System.out.println(temparray[0] + "\n" +methodtemp[1]+"() \n"+temparray[2] );
	    			   listWithClassnameAndMethod.add(temparray[0] + "\n" +methodtemp[1]+"()\n"+temparray[2]);
	    		   
	    	   }
	    	   
	    	   //Visualization of the details in the diagram
	    	   int xValue = 20;
		       int yValue = 20;
		     
		       List<Object> listOfDiagramObject = new ArrayList<Object>();//Contains the main class diagram objects
		       List<String> listOfDiagramObjectDrawn = new ArrayList<String>();//Contains the names of the already drawn diagram objects
		       List<Object> listOfMiddleObjects = new ArrayList<Object>();//contains the diagram objects which keep the count
		       List<Integer> numberOfTimesRepeadted = new ArrayList<Integer>();//This is to keep the count on the count objects
		       
		       List<Integer> xValueOfMiddleObject = new ArrayList<Integer>();//to keep the x value of middle object
		       List<Integer> yValueOfMiddleObject = new ArrayList<Integer>();//to keep the y value of middle object
		       
		       Object v1 = graph.insertVertex(parent, null, listWithClassnameAndMethod.get(0), xValue, yValue, 400, 50);
		       listOfDiagramObjectDrawn.add(listWithClassnameAndMethod.get(0));
		       listOfDiagramObject.add(v1);
		       
	    	   for(int i=0;i<listWithClassnameAndMethod.size();i++){
	    		   	System.out.println("I Value: "+i);
	    		   //We already have assigned the first one before starting the for loop. So no need of taking it to account again
	    		   if(i>0){
	    			   int shouldAddNewVertex = 0;
	    			   for(int a=i-1;a>=0;a--){
	    				   
	    				   int xx = listOfDiagramObjectDrawn.indexOf(listWithClassnameAndMethod.get(a));
	    				   System.out.println(xx);
	    				   
	    				   if((listWithClassnameAndMethod.get(i)).equalsIgnoreCase(listWithClassnameAndMethod.get(a)) && listOfDiagramObjectDrawn.contains(listWithClassnameAndMethod.get(i)) && (listOfDiagramObjectDrawn.indexOf(listWithClassnameAndMethod.get(i-1))<listOfDiagramObjectDrawn.indexOf(listWithClassnameAndMethod.get(i)))){
	    					   //If there is a middle node and a path between two nodes which is already drawn then we use this
	    					   System.out.println("Now :"+listWithClassnameAndMethod.get(i));
	    					   System.out.println("Before :"+listWithClassnameAndMethod.get(i-1));
	    					   
	    					   int index = listOfDiagramObjectDrawn.indexOf(listWithClassnameAndMethod.get(i));
	    					  System.out.println("Index: "+index);
	    					  int newRepeatingValue = numberOfTimesRepeadted.get(index);
	    					  numberOfTimesRepeadted.set(index,(newRepeatingValue+1));
	    					  graph.insertVertex(parent, null, numberOfTimesRepeadted.get(index), xValueOfMiddleObject.get(index), yValueOfMiddleObject.get(index), 50, 30);
	    					  shouldAddNewVertex = 1;
	    					  break;
	    				   }else if(listWithClassnameAndMethod.get(i).equalsIgnoreCase(listWithClassnameAndMethod.get(a))){
	    					   //If the node is already there but need a middle node we use this one
	    					   
	    					   Object middle = graph.insertVertex(parent, null, 0, 695, yValue/i, 50, 30);
	    					   listOfMiddleObjects.add(middle);
	    					   numberOfTimesRepeadted.add(0);
	    					   xValueOfMiddleObject.add(695);
				    		   yValueOfMiddleObject.add(yValue/i);
	    					   
//	    					   graph.insertEdge(parent, null, "", listOfDiagramObject.get(i-1), listOfDiagramObject.get(a));
//	    					   listOfDiagramObject.add(listOfDiagramObject.get(a));
	    					   
	    					   graph.insertEdge(parent, null, "", listOfDiagramObject.get(i-1), listOfMiddleObjects.get(i-1));
				    		   graph.insertEdge(parent, null, "", listOfMiddleObjects.get(i-1), listOfDiagramObject.get(a));
	    					   System.out.println("In");
	    					   shouldAddNewVertex = 1;
	    					   break;
	    				   }
	    			   }
	    			   
	    			   
	    			   //Here we add a new node when it is not already added to the diagrams
	    			   if(shouldAddNewVertex == 0){
	    				   listOfDiagramObjectDrawn.add(listWithClassnameAndMethod.get(i));
    					   yValue += 70;
    					   Object middle = graph.insertVertex(parent, null, 0, 195, yValue, 50, 30);
    					   xValueOfMiddleObject.add(195);
			    		   yValueOfMiddleObject.add(yValue);
    					   listOfMiddleObjects.add(middle);
    					   numberOfTimesRepeadted.add(0);
    					   yValue += 70;
			    		   Object v2 = graph.insertVertex(parent, null, listWithClassnameAndMethod.get(i), xValue, yValue, 400, 50);
			    		   listOfDiagramObject.add(v2);
			    		   graph.insertEdge(parent, null, "", listOfDiagramObject.get(i-1), listOfMiddleObjects.get(i-1));
			    		   graph.insertEdge(parent, null, "", listOfMiddleObjects.get(i-1), listOfDiagramObject.get(i));
			    		   
	    			   }
    				   
	    			   
	    		   }
	    		   
	    	   }
	    	   
	    	   
	    	   
	    	  
	       }finally{
	           graph.getModel().endUpdate();
	       }
	       
	       mxGraphComponent graphComponent = new mxGraphComponent(graph);
	       
	       MethodFlowVisualization frame = new MethodFlowVisualization();
	       frame.add(graphComponent);
	       frame.pack();
	       //frame.setLocationRelativeTo(null);
	       frame.setVisible(true);
	       //String filename = "C:/SugarCrmResults/DigramFiles/"+"Testing"+".pdf";
	      // File file = new File(filename);
	       
	      // frame.PrintFrameToPDF(file);
	       
	       
		   //frame.dispose();
		
		
	}
	
}

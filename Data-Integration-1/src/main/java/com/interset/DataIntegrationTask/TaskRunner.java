package com.interset.DataIntegrationTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class TaskRunner {
	
	static ArrayList<Long> eventIDs=new ArrayList<Long>();
	static ArrayList<String> users=new ArrayList<String>();
	static ArrayList<String> files=new ArrayList<String>();
	static Date startDate=null;
	static Date endDate=null;
	
    public static void main(String args[]) {
    	
    	

        // Check arguments
        if (args.length != 2) {
            System.out.println("We currently only expect 2 arguments! A path to a JSON file to read, and a path for a CSV file to write.");
            System.exit(1);
        }

        // Read arguments
        Path jsonFile = null;

        try {
            jsonFile = Paths.get(args[0]);
        } catch (InvalidPathException e) {
            System.err.println("Couldn't convert JSON file argument [" + args[0] + "] into a path!");
            throw e;
        }

        Path csvFile = null;

        try {
            csvFile = Paths.get(args[1]);
        } catch (InvalidPathException e) {
            System.err.println("Couldn't convert CSV file argument [" + args[1] + "] into a path!");
            throw e;
        }

        // Validate arguments
        if (!Files.exists(jsonFile)) {
            System.err.println("JSON file [" + jsonFile.toString() + "] doesn't exist!");
            System.exit(1);
        }

        if (!Files.isWritable(csvFile.getParent())) {
            System.err.println("Can't write to the directory [" + csvFile.getParent().toString() + "] to create the CSV file! Does directory exist?");
            System.exit(1);
        }

        // Create the CSV file
        System.out.println("Reading file [" + jsonFile.toString() + "], and writing to file [" + csvFile.toString() + "].");

        parseJsonFileAndCreateCsvFile(jsonFile, csvFile);

    }

    public static void parseJsonFileAndCreateCsvFile(Path jsonFile, Path csvFile) {
    	// TODO
    	
    	final String csvHeader="TIMESTP,ACTION,USER,FOLDER,FILENE,IP";
    	final String newLine="\n";
    	Report report=new Report() ;
    	
    	boolean DrpRecord=false;
    	
    	

        Scanner linReader=null;
    	FileWriter csvfileWriter=null;
    	StringBuilder csvString=null;
    	
    	
		try {
			linReader = new Scanner(new File(jsonFile.toString()));
			
			//Write header to CSV file
			csvfileWriter=new FileWriter(csvFile.toString());
			csvfileWriter.append(csvHeader);
			csvfileWriter.append(newLine);
			
	    	 while (linReader.hasNext())
	         {
	             String line = linReader.nextLine();
	             UserData user=new ObjectMapper().readValue(line, UserData.class);
	             //process userData to update report and verify record can be logged into CSV file or not
	             //DrpRecord=process(user, report);
	             
	             if (!process(user, report)){
	            	 csvString=parserJsonString(user);
	            	 csvfileWriter.append(csvString);
	            	 csvfileWriter.append(newLine);
	            	 
	             }       
	             //System.out.println(csvString);
	         }
		} catch (FileNotFoundException e) {
			System.out.println("Error in file to read:"+ e.getMessage());
			e.printStackTrace();
		} catch (IOException e){
			System.out.println("Error in cvs file to write: "+ e.getMessage());
			e.printStackTrace();			
		}
		finally{
			//System.out.println("In finial");
			if 	(linReader!=null){
				linReader.close();
			}
			if (csvfileWriter!=null){
				try {
					csvfileWriter.flush();
					csvfileWriter.close();
				} catch (IOException e) {
					System.out.println("Error in cvs file to flush/close: "+ e.getMessage());
					e.printStackTrace();
				}
				
			}
         

		}
		
		//Printout report
		ObjectMapper mapper=new ObjectMapper();
		try {
			String reportInjson=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(report);
			System.out.println("\n\n========================REPORT=======================================\n");
			System.out.println(reportInjson);
		} catch (JsonProcessingException e) {
			System.out.println("Error in printout report: "+ e.getMessage());
			e.printStackTrace();
		} 
		

    }
    
    //This method is to construct valid string from user data
    public static StringBuilder parserJsonString(UserData userdata){
    	String strTSTMP=userdata.getTimeSmpString();
		String strUser=userdata.getUserString();
		String strAct=userdata.getActionString();
		String strFolder=userdata.getFolder();
		String strFileName=userdata.getFileName();
		String strIP=userdata.getIpAddr();
		
		StringBuilder strblder=new StringBuilder();
		strblder.append(strTSTMP).append(",").append(strAct).append(",").append(strUser)
		.append(",").append(strFolder).append(",").append(strFileName).append(",")
		.append(strIP);
		
		return strblder;
		
    }
    	
  //This method is to verify whether record should be dropped or not; and update report accordingly
    public static boolean process(UserData userdata, Report report){
    	boolean drop=false;
    	
    	report.addLineRead();
    	
    	//Check eventID for duplicate
    	if (!eventIDs.contains(userdata.getEventId())){
    		
    		eventIDs.add(userdata.getEventId());
    		
    	} else{
    		drop=true;
    		report.addDroppedEventCounts();
    		report.addDuplicate();
    		
    	}
    	
    	//Check NO action mapping    	
    	if (userdata.getActionString()==null){
    		drop=true;
    		report.addNoActionMapping();
    		report.addDroppedEventCounts();
    		
    	}
    	
    	//Check whether it is unique user
    	if (!users.contains(userdata.getUser())){
    		users.add(userdata.getUser());
    		report.addUniqueUsers();
    	}
    	
    	//check whether it is file
    	if (!files.contains(userdata.getFile())){
    		files.add(userdata.getFile());
    		report.addUniqueFiles();
    		
    	}
    	
    	//Get action calculation
    	if (userdata.getActionString()!=null){
	    	if (userdata.getActionString().equalsIgnoreCase("ADD")){
	    		report.addADD();
	    	
	    	}else if (userdata.getActionString().equalsIgnoreCase("REMOVE")){
	    		report.addREMOVE();
	    	}else if (userdata.getActionString().equalsIgnoreCase("ACCESSED")){
	    		report.addACCESSED();
	    	}
    	}
	    	
    	//Update start/end date
    	if (report.getStartDate()==null||startDate==null){
    		try {
				startDate=new SimpleDateFormat("MM/dd/yyyy hh:mm:ssa").parse(userdata.getTimestamp());
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		report.setStartDate(report.convertDate(userdata.getTimestamp()));
    	} 
    	
    	if (report.getEndDate()==null||endDate==null){
    		try {
				endDate=new SimpleDateFormat("MM/dd/yyyy hh:mm:ssa").parse(userdata.getTimestamp());
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		report.setEndDate(report.convertDate(userdata.getTimestamp()));    		
    		
    	} 
    	
    	//Compare user time stamp is before/after start date, if after, then startdate needs to updated. 
     	//Compare user time stamp is before/after end date, if before, then enddate needs to updated. 
    	Date newDate=null;
    	try {
			newDate=new SimpleDateFormat("MM/dd/yyyy hh:mm:ssa").parse(userdata.getTimestamp());
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	if (startDate.compareTo(newDate)>0){
			startDate=newDate;
			report.setStartDate(report.convertDate(userdata.getTimestamp()));
    	}
			
		if (endDate.compareTo(newDate)<0){
			endDate=newDate;
			report.setEndDate(report.convertDate(userdata.getTimestamp())); 
		}
			
    
    	return drop;
    	
    }
    

}

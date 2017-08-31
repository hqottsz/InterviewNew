package com.interset.DataIntegrationTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Report {
	
	Long linesRead;
	Long droopedEventsCounts;
	DroppedEvent droopedEvents;
	Long uniqueUsers;
	Long uniqueFiles;
	String startDate;
	String endDate;
	Actions actions;
	
	
	public Report(){
		linesRead=0L;
		droopedEventsCounts=0L;
		droopedEvents=new DroppedEvent();
		uniqueUsers=0L;
		uniqueFiles=0L;
		startDate=null;
		endDate=null;
		actions=new Actions();	
		
	}


	public Long getLinesRead() {
		return linesRead;
	}


	public void setLinesRead(Long linesRead) {
		this.linesRead = linesRead;
	}


	public Long getDroopedEventsCounts() {
		return droopedEventsCounts;
	}


	public void setDroopedEventsCounts(Long droopedEventsCounts) {
		this.droopedEventsCounts = droopedEventsCounts;
	}


	public DroppedEvent getDroopedEvents() {
		return droopedEvents;
	}


	public void setDroopedEvents(DroppedEvent droopedEvents) {
		this.droopedEvents = droopedEvents;
	}


	public Long getUniqueUsers() {
		return uniqueUsers;
	}


	public void setUniqueUsers(Long uniqueUsers) {
		this.uniqueUsers = uniqueUsers;
	}


	public Long getUniqueFiles() {
		return uniqueFiles;
	}


	public void setUniqueFiles(Long uniqueFiles) {
		this.uniqueFiles = uniqueFiles;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public Actions getActions() {
		return actions;
	}


	public void setActions(Actions actions) {
		this.actions = actions;
	}
	
	public void addNoActionMapping(){
		getDroopedEvents().addNO_action_mapping();
		
	}
	
	public void addDuplicate(){
		getDroopedEvents().addDuplicates();
		
	}
	
	public void addADD(){
		getActions().addADD();
		
	}
	
	public void addREMOVE(){
		getActions().addREMOVE();
		
	}
	
	
	public void addACCESSED(){
		getActions().addACCESSED();
		
	}
	
	public void addLineRead(){
		setLinesRead(getLinesRead()+1);
		
	}
	
	public void addDroppedEventCounts(){
		setDroopedEventsCounts(getDroopedEventsCounts()+1);
		
	}
	
	public void addUniqueUsers(){
		setUniqueUsers(getUniqueUsers()+1);
		
	}
	
	public void addUniqueFiles(){
		setUniqueFiles(getUniqueFiles()+1);
		
	}
	
	public String convertDate(String originalDate){
		
		DateFormat originalFormat=new SimpleDateFormat("MM/dd/yyyy hh:mm:ssa");
		DateFormat targetFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Date dateTime=null;
		String formattedDate=null;
		try {
			dateTime = originalFormat.parse(originalDate);
			formattedDate= targetFormat.format(dateTime);
				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		return formattedDate;
	}
	
	public void reportPrint(){
		ObjectMapper mapper=new ObjectMapper();
		
	}
	
	
	
	
	

}

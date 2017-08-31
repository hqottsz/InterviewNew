package com.interset.DataIntegrationTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;





public class UserData {
	Long eventId=null;
	String user=null;
	String ipAddr=null;
	String file=null;
	String activity=null;
	String timestamp=null;
	String timeOffset=null;
	
	
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getTimeOffset() {
		return timeOffset;
	}
	public void setTimeOffset(String timeOffset) {
		this.timeOffset = timeOffset;
	}
	
	
	public String getUserString(){
		
		List<String> strList=Arrays.asList(this.getUser().split("@"));
		return strList.get(0);
	
		
	}
	
	public String getTimeSmpString(){
		
		DateFormat originalFormat=new SimpleDateFormat("MM/dd/yyyy hh:mm:ssa");
		DateFormat targetFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Date dateTime=null;
		String formattedDate=null;
		try {
			dateTime = originalFormat.parse(getTimestamp());
			formattedDate= targetFormat.format(dateTime);
			if (getTimeOffset()!=null){
				formattedDate=formattedDate+getTimeOffset();
			} else{
				formattedDate=formattedDate+"Z";
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		return formattedDate;
	}
	
	public String getActionString(){
		
		String rtString=null;
		
		String act=getActivity();
		
		if (act.equalsIgnoreCase("addedText")||act.equalsIgnoreCase("createdDoc")
				||act.equalsIgnoreCase("changedText")){
			rtString="ADD";
			
		} else if (act.equalsIgnoreCase("deletedDoc")||act.equalsIgnoreCase("deletedText")
				||act.equalsIgnoreCase("archived")){
			rtString="REMOVE";
			
		} else if (act.equalsIgnoreCase("viewedDoc")){
			rtString="ACCESSED";
			
		} else{
			rtString=null;
			
		}
		
		
		
		return rtString;
	}
	
	public String getFolder(){
		
		String strRrn=getFile();
		int index=strRrn.lastIndexOf("/");
		String strPath=strRrn.substring(0, index);
		
		
		return strPath;
	}
	
	public String getFileName(){
		
		String strRrn=getFile();
		int index=strRrn.lastIndexOf("/");
		String strName=strRrn.substring(index+1);
		
		
		return strName;
	}
	
	
	
	
	

}



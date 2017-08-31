package com.interset.DataIntegrationTask;

public class DroppedEvent {
	Long NO_action_mapping;
	Long Duplicates;
	
	public DroppedEvent(){
		NO_action_mapping=0L;
		Duplicates=0L;
				
		
	}
	public Long getNO_action_mapping() {
		return NO_action_mapping;
	}
	public void setNO_action_mapping(Long nO_action_mapping) {
		NO_action_mapping = nO_action_mapping;
	}
	public Long getDuplicates() {
		return Duplicates;
	}
	public void setDuplicates(Long duplicates) {
		Duplicates = duplicates;
	}
	
	public void addNO_action_mapping(){
		NO_action_mapping++;
		
	}
	
	public void addDuplicates(){
		Duplicates++;
		
	}
	
	

}

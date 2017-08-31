package com.interset.DataIntegrationTask;

public class Actions {
	Long ADD;
	Long REMOVE;
	Long ACCESSED;
	
	public Actions(){
		ADD=0L;
		REMOVE=0L;
		ACCESSED=0L;
	}

	public Long getADD() {
		return ADD;
	}

	public void setADD(Long aDD) {
		ADD = aDD;
	}

	public Long getREMOVE() {
		return REMOVE;
	}

	public void setREMOVE(Long rEMOVE) {
		REMOVE = rEMOVE;
	}

	public Long getACCESSED() {
		return ACCESSED;
	}

	public void setACCESSED(Long aCCESSED) {
		ACCESSED = aCCESSED;
	}
	
	public void addADD(){
		ADD++;
	}
	
	public void addREMOVE(){
		REMOVE++;
		
	}
	
	public void addACCESSED(){
		ACCESSED++;
	}
	
	

}

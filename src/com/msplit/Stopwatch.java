package com.msplit;

import java.util.Date;

public class Stopwatch {
	private boolean isRunning;
	private long savedTime;
	private long startTime;
	
	public Stopwatch() {
		savedTime = 0;
		isRunning = false;
	}
	
	synchronized public void start(){
		if(!isRunning){
			startTime = new Date().getTime();
			isRunning = true;
		}
	}
	
	synchronized public void stop(){
		if(isRunning){
			savedTime += new Date().getTime() - startTime;
			isRunning = false;
		}
	}
	
	synchronized public void reset(){
		isRunning = false;
		savedTime = 0;
	}
	
	synchronized public int getTimeInTenths(){
		return (int)(getTime()/100);
	}
	
	synchronized public long getTime(){
		if(!isRunning){
			return savedTime;
		} else {
			return savedTime + new Date().getTime() - startTime;
		}
	}
}

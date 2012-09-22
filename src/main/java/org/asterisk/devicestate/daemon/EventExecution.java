package org.asterisk.devicestate.daemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EventExecution {

	private String command;
	

	public EventExecution(String command) {
		this.command = command;
	}
	
	
	public void execute(){
		Runtime runtime;
		Process process;
		
		
		try {
			runtime = Runtime.getRuntime();
			process = runtime.exec(this.command);
			int exitVal = process.exitValue();
			
			if(exitVal != 0){
		        System.out.println("Process exitValue: " + exitVal);
	
				BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = "";
				while ((line=buf.readLine())!=null) {
					System.out.println(line);
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		

	}
}

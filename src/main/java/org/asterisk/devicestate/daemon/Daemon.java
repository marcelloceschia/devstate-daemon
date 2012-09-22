package org.asterisk.devicestate.daemon;

import java.io.IOException;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;

public class Daemon {

	private static String username = "asterisk-management-user";
	private static String password = "asterisk-management-password";
	private static String server = "asterisk.localnet";
	private static Boolean useSecureConnection = false;
	
	

	
	public void run(){
		ManagerConnection connection;
		ManagerConnectionFactory manager;
		AstManagerEventListener eventListener;
		
		
		eventListener = new AstManagerEventListener();
		eventListener.addDevicestateExecution("Custom:licht_flur", AsteriskExtensionState.INUSE, "/home/marcello/logEvent.sh \"Custom:licht_flur\" INUSE");
		eventListener.addDevicestateExecution("Custom:licht_flur", AsteriskExtensionState.NOT_INUSE, "/home/marcello/logEvent.sh \"Custom:licht_flur\" NOT_INUSE");
		
		manager = new ManagerConnectionFactory(server, username, password);
		
		if(useSecureConnection){
			connection = manager.createSecureManagerConnection();
		}else{
			connection = manager.createManagerConnection();
		}

		connection.addEventListener(eventListener);
		try {
			connection.login();
			
			System.in.read();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (AuthenticationFailedException e1) {
			e1.printStackTrace();
		} catch (TimeoutException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Daemon daemon = new Daemon();
		daemon.run();
		
		
	}

}

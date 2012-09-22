package org.asterisk.devicestate.daemon;

import java.util.HashMap;
import java.util.Map;

import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.event.ExtensionStatusEvent;
import org.asteriskjava.manager.event.ManagerEvent;


public class AstManagerEventListener implements ManagerEventListener {

	private Map<String, EventExecution> extensionState = new HashMap<String, EventExecution>();
	
	/**
	 * add a new command for hint and state 
	 * @param hint asterisk hint string, e.g. Custom:licht_flur or SCCP/1234
	 * @param state INUSE or NOT_INUSE
	 * @param command command that should be executed when state is obtained
	 */
	public void addDevicestateExecution(String hint, AsteriskExtensionState state, String command){
		String objectId; 
		
		objectId = createObjectId(hint, state.ordinal());
		if(this.extensionState.containsKey(objectId)){
			this.extensionState.remove(objectId);
		}
		
		this.extensionState.put(objectId, new EventExecution(command));
	}

	/**
	 * internal event handler
	 */
	public void onManagerEvent(ManagerEvent event) {
		ExtensionStatusEvent extensionStateEvent;
		String objectId; 
		
		if( ExtensionStatusEvent.class.isInstance(event) ){
			extensionStateEvent = (ExtensionStatusEvent) event;
			
			objectId = createObjectId(extensionStateEvent.getHint(), extensionStateEvent.getStatus());			
			if(this.extensionState.containsKey(objectId)){
				this.extensionState.get(objectId).execute();
			}
		}
	}
	
	
	/**
	 * create unique object string for hint and state
	 * @param hint
	 * @param state
	 * @return
	 */
	private String createObjectId(String hint, int state){
		return String.format("%s::%d", hint, state);
	}

}

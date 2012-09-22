package org.asterisk.devicestate.daemon;

public enum AsteriskExtensionState {
	NOT_INUSE,    /*!< Device is not used */
	INUSE,        /*!< Device is in use */
	BUSY,         /*!< Device is busy */
	UNAVAILABLE,  /*!< Device is unavailable */
	RINGING,      /*!< Device is ringing */
}

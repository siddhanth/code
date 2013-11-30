/* ############################################################################
* Copyright 2013 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: AL Deal-Maker
* Module: 
* Source: MisPrismDataUpload.java
* Author: HP
* Organization: HP BAS India
* Revision: 0.1
* Date: 08-22-2013
* Contents:
*-----------------------------------------------------------------------------
* Revision History:
*     who                                  when                                    what
*  	hsingh								08-22-2013								Initial functionality
* #############################################################################
*/
package com.example.model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * The persistent class for the MIS_PRISM_DATA_UPLOAD database table.
 * 
 */
public class Events implements Serializable {
	private static final long serialVersionUID = 1L;

	private String eventID;

	private String eventName;

	private ArrayList<String> contacts;

	private String venueName;

	private String venueLatLong;

	private String timestamp;

	public Events() {
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public ArrayList<String> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<String> contacts) {
		this.contacts = contacts;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public String getVenueLatLong() {
		return venueLatLong;
	}

	public void setVenueLatLong(String venueLatLong) {
		this.venueLatLong = venueLatLong;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
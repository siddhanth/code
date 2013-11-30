/* ############################################################################
* Copyright 2013 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: AL Deal-Maker
* Module: DCR\Other Calls
* Source: ModelMasterHelper.java
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
package com.example.database.helpers;

import java.text.ParseException;
import java.util.ArrayList;

import android.database.Cursor;

import com.example.database.DatabaseManager;
import com.example.model.Events;

/**
 * @author hsingh
 *
 */
public class EventListHelper extends SQLHelper{
	public EventListHelper(DatabaseManager dbManager) {
		super(dbManager);
	}
	
	private static final String TABLE_NAME = "EVENTS";
	private static final String GET_ALL_EVENTS = "SELECT * FROM "+TABLE_NAME;
	
	public ArrayList<Events> getEventDetails() throws ParseException{
		ArrayList<Events> eventList = new ArrayList<Events>();
		Cursor cursor = dbManager.rawQuery(GET_ALL_EVENTS, null);
		Events tempEvent = null;
		while(cursor.moveToNext()){
			tempEvent = new Events();
			tempEvent.setEventID(cursor.getString(cursor.getColumnIndex("event_id")));
			tempEvent.setEventName(cursor.getString(cursor.getColumnIndex("event_name")));
			String[] arr = cursor.getString((cursor.getColumnIndex("contacts"))).split(",");
			ArrayList<String> contacts = new ArrayList<String>();
			for (String x:arr){
				contacts.add(x);
			}
			tempEvent.setContacts(contacts);
			tempEvent.setVenueName(cursor.getString(cursor.getColumnIndex("venue_name")));
			tempEvent.setVenueLatLong(cursor.getString(cursor.getColumnIndex("venue_lat_lon")));
			tempEvent.setTimestamp(cursor.getString(cursor.getColumnIndex("timestamp")));
			eventList.add(tempEvent);
		}
		close(cursor);
		return eventList;
	}
	
}
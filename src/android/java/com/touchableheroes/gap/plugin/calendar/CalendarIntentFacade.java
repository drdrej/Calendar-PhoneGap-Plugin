package com.touchableheroes.gap.plugin.calendar;

import java.util.Date;

import nl.xservices.plugins.Calendar;
import nl.xservices.plugins.Constants;
import android.content.Intent;
import android.util.Log;

/**
 * Calendar-Access based on original code of calendar-phonegap-plugin. This code
 * shot intents to call a calendar-view.
 * 
 * @author Eddy Verbruggen
 * @author A. Siebert / ask@touchableheroes.com
 */
public class CalendarIntentFacade extends CalendarFacade {

	public CalendarIntentFacade(final Calendar plugin) {
		super(plugin);
	}

	@Override
	public boolean createEvent(final String title, long beginTime,
			long endTime, final String location, final String description) {

		final Intent calIntent = new Intent(Intent.ACTION_EDIT)
				.setType("vnd.android.cursor.item/event")
				.putExtra("title", title).putExtra("eventLocation", location)
				.putExtra("description", description)
				.putExtra("beginTime", beginTime).putExtra("endTime", endTime)
				.putExtra("allDay", isAllDayEvent(beginTime, endTime));
		
		this.plugin.cordova.startActivityForResult(this.plugin, calIntent,
				Constants.RESULT_CODE_CREATE);
		return true;
	}

	// TODO [drdrej]: impl is based on deprecated methods. rewrite with java.util.calendar.
	private boolean isAllDayEvent(long beginTime, long endTime) {
		final Date begin = new Date(beginTime);
		final Date end = new Date(endTime);
		
		return begin.getHours() == 0 &&
				begin.getMinutes() == 0 &&
						begin.getSeconds() == 0 &&
		        end.getHours() == 0 &&
		        end.getMinutes() == 0 &&
		        end.getSeconds() == 0;
	}

	@Override
	public boolean deleteEventByInfo(String title, long beginTime, long endTime) {
		Log.e("phonegao-calendar-plugin", "Action deleteEvent() is not supported" );
		return false;
	}

	@Override
	public boolean updateEventByHeader(String title, long beginTime,
			long endTime, String newTitle, long newBeginTime, long newEndTime,
			String newDescription, String newLocation) {
		Log.e("phonegao-calendar-plugin", "Action modifyEvent() is not supported" );
		return false;
	}
	

}

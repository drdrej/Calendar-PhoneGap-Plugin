package com.touchableheroes.gap.plugin.calendar;

import android.app.Activity;
import android.util.Log;

import com.touchableheroes.drafts.calendar.dao.EventsDAO;

import nl.xservices.plugins.Calendar;

/**
 * Creates events in background.
 * 
 * @author A. Siebert / ask@touchableheroes.com
 */
public class CalendarHeadlessFacade extends CalendarFacade {

	public CalendarHeadlessFacade(final Calendar plugin) {
		super(plugin);
	}

	@Override
	public boolean createEvent(final String title, long beginTime,
			long endTime, final String location, final String description) {
		try {
			final Activity context = this.plugin.cordova.getActivity();
			final EventsDAO dao = new EventsDAO(context);

			dao.createEvent(beginTime, endTime, title, description);
			return true;
		} catch (final Throwable x) {
			Log.e("phonegap-calender-plugin", "Couldn't execute createEvent()",
					x);
			return false;
		}
	}

	@Override
	public boolean deleteEventByInfo(final String title, final long beginTime,
			final long endTime) {
		final Activity context = this.plugin.cordova.getActivity();
		final EventsDAO dao = new EventsDAO(context);

		try {
			return dao.deleteByHeader(title, beginTime, endTime);
		} catch (final Throwable x) {
			Log.e("phonegap-calender-plugin", "Couldn't execute deleteEvent()",
					x);
			return false;
		}
	}

	@Override
	public boolean updateEventByHeader(String title, long beginTime,
			long endTime, String newTitle, long newBeginTime, long newEndTime,
			String newDescription, String newLocation) {
		final Activity context = this.plugin.cordova.getActivity();
		final EventsDAO dao = new EventsDAO(context);

		return dao.updateByHeader(title, beginTime, endTime, newTitle, newBeginTime, newEndTime, newDescription, newLocation);
	}

}

package com.touchableheroes.gap.plugin.calendar;

import org.json.JSONArray;

import nl.xservices.plugins.Calendar;

/**
 * Hide different approaches of calendar in android. this is an abstract
 * interface to call some calender-activities.
 * 
 * @author A. Siebert / ask@touchableheroes.com
 */
public abstract class CalendarFacade {

	/**
	 * Plugin-back-reference
	 */
	protected final Calendar plugin;

	public CalendarFacade(final Calendar plugin) {
		this.plugin = plugin;
	}

	public abstract boolean createEvent(final String title, long beginTime,
			long endTime, final String location, final String description);

	public final boolean createEvent(final JSONArray args) {
		try {
			final String title = args.getString(0);
			final String location = args.getString(1);

			final String description = args.getString(2);
			final long beginTime = args.getLong(3);
			final long endTime = args.getLong(4);

			return createEvent(title, beginTime, endTime, location, description);
		} catch (final Throwable x) {
			return false;
		}
	}

	public final boolean deleteEvent(final JSONArray args) {
		try {
			final String title = args.getString(0);
			// final String location = args.getString(1);
			// final String description = args.getString(2);

			final long beginTime = args.getLong(3);
			final long endTime = args.getLong(4);

			return deleteEventByInfo(title, beginTime, endTime);
		} catch (final Throwable x) {
			return false;
		}
	}

	public abstract boolean deleteEventByInfo(String title, long beginTime,
			long endTime);

	// map these values from api:
	//
	// [title, location, notes, startDate.getTime(), endDate.getTime(),
	// newTitle, newLocation, newNotes, newStartDate.getTime(),
	// newEndDate.getTime()]
	public boolean modifyEvent(final JSONArray args) {
		try {
			// extract header::
			final String title = args.getString(0);
			final long beginTime = args.getLong(3);
			final long endTime = args.getLong(4);

			// extract update info::
			final String newTitle = args.getString(5);
			final String newLocation = args.getString(6);
			final String newDescription = args.getString(7);
			final long newBeginTime = args.getLong(8);
			final long newEndTime = args.getLong(9);
			
			return updateEventByHeader(title, beginTime, endTime, newTitle, newBeginTime, newEndTime, newDescription, newLocation);
		} catch (final Throwable x) {
			return false;
		}
	}

	public abstract boolean updateEventByHeader(String title, long beginTime,
			long endTime, String newTitle, long newBeginTime, long newEndTime,
			String newDescription, String newLocation);

	/**
	 * Factory-method.
	 * 
	 * @param plugin
	 *            reference to plugin
	 * @param headless
	 *            if true, will use the access over ContentProvider, else will
	 *            use default.
	 * 
	 * @return never NULL
	 */
	public static CalendarFacade create(final Calendar plugin,
			final boolean headless) {
		if (headless)
			return new CalendarHeadlessFacade(plugin);

		return new CalendarIntentFacade(plugin);
	}

}

package nl.xservices.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.touchableheroes.gap.plugin.calendar.CalendarAction;
import com.touchableheroes.gap.plugin.calendar.CalendarFacade;

/**
 * Calendar-Phonegap-integration for android.
 * 
 * @author Eddy Verbruggen
 * @author A. Siebert / ask@touchableheroes.com
 */
public class Calendar extends CordovaPlugin {

	public static final Integer RESULT_CODE_CREATE = 0;

	/**
	 * facade to access calendar-logic to separate impl for different devices
	 * and behaviours:
	 */
	private CalendarFacade facade = CalendarFacade.create(this, true);

	// TODO: maybe bind on Intent.Bundle (?)
	private CallbackContext lastCallbackCtx;
	
	@Override
	public boolean execute(final String action, final JSONArray args,
			final CallbackContext callbackCtx) throws JSONException {
		try {
			final CalendarAction type = CalendarAction.valueOf(action);
			switch (type) {
			case deleteEvent:
			    return facade.deleteEvent(args);
			case createEvent:
				return facade.createEvent(args);
			case modifyEvent:
				return facade.modifyEvent(args);
			default:
				callbackCtx.error("calendar." + action
						+ " is not (yet) supported on Android.");

				return false;
			}
		} catch (final Throwable e) {
			Log.e("phonega-calendar-plugin", "Exception: " + e.getMessage(), e);
			callbackCtx.error("calendar." + action
					+ " is broken. check logs for more information.");

			return false;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULT_CODE_CREATE) {
			if (resultCode == Activity.RESULT_OK) {
				lastCallbackCtx.success();
			} else if (resultCode == Activity.RESULT_CANCELED) {
				lastCallbackCtx.error("User cancelled");
			} else {
				lastCallbackCtx.error("Unable to add event (" + resultCode + ").");
			}
		}
	}

}
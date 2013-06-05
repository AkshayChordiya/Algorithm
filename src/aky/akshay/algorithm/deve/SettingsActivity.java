package aky.akshay.algorithm.deve;

import java.io.InputStream;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */

 /* Important Notice :
  * 
  * This activity is created by Android SDK
  * The main point i have seen is OnCreate is not important onPostCreate is imp*/

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	// Key for required preference
	public static final String KEY_PREF_SCROLL = "scroll";	
	public static final String CHANGELOG_KEY = "changelog";
	public static final String PLANNEDLOG_KEY = "plannedlog";
	public static final String EMAIL_KEY = "email_support";
	public static final String THEME_KEY = "theme";
	public static final String FULL_KEY = "portrait";
	
	public static final int CHANGELOG = 0;
	public static final int PLANNEDLOG = 1;
	public static final int EMAIL_SUPPORT = 2;
	

	/**
	 * Determines whether to always show the simplified settings UI, where
	 * settings are presented in a single list. When false, settings are shown
	 * as a master/detail two-pane view on tablets. When true, a single pane is
	 * shown on tablets.
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		prefControl();
		super.onCreate(savedInstanceState);
		ActivityAnimation();   			
        setupActionBar();
	}
	
	/*************************************IMP*********************************************/
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		setupSimplePreferencesScreen();
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void setupActionBar() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@SuppressWarnings("deprecation")
	private void setupDialog() {
		// TODO Auto-generated method stub
		Preference changelog = (Preference) findPreference(CHANGELOG_KEY);
		changelog.setOnPreferenceClickListener(
                new OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                    	displaydialog(CHANGELOG);
						return false;
                    }
                });
		Preference plannedlog = (Preference) findPreference(PLANNEDLOG_KEY);
		plannedlog.setOnPreferenceClickListener(
                new OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                    	displaydialog(PLANNEDLOG);
						return false;
                    }
                });
		Preference email_support = (Preference) findPreference(EMAIL_KEY);
		email_support.setOnPreferenceClickListener(
                new OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                    	displaydialog(EMAIL_SUPPORT);
						return false;
                    }
                });
	}

	public void displaydialog(int i) {
		// TODO Auto-generated method stub
		switch(i){
		case CHANGELOG:
			//Launch change log dialog
	        InputStream change_log = getApplicationContext().getResources().openRawResource(R.raw.changelog);
	        if (change_log == null) {
	            finish();
	            return;
	        }

	        try {
	            // Read the change log
	            StringBuilder changelog_string = new StringBuilder();
	            int read = 0;
	            byte[] data = new byte[512];
	            while ((read = change_log.read(data, 0, 512)) != -1) {
	            	changelog_string.append(new String(data, 0, read));
	            }

	            // Show a dialog
	            new AlertDialog.Builder(this)
				.setTitle(R.string.about_changelog_title)
				.setIcon(R.drawable.author)
				.setMessage(changelog_string.toString())
				.setNeutralButton(android.R.string.ok, null)
				.show();

	        } catch (Exception e) {
	            finish();

	        } finally {
	            try {
	            	change_log.close();
	            } catch (Exception e) {/**NON BLOCK**/}
	        }
			break;
		case PLANNEDLOG:
			//Launch planned feature dialog
    		InputStream isF = getApplicationContext().getResources().openRawResource(R.raw.featurelog);
	        if (isF == null) {
	            finish();
	            return;
	        }

	        try {
	            // Read the change log
	            StringBuilder sb = new StringBuilder();
	            int read = 0;
	            byte[] data = new byte[512];
	            while ((read = isF.read(data, 0, 512)) != -1) {
	                sb.append(new String(data, 0, read));
	            }

	            // Show a dialog
	            new AlertDialog.Builder(this)
				.setTitle(R.string.about_plan_title)
				.setIcon(R.drawable.author)
				.setMessage(sb.toString())
				.setNeutralButton(android.R.string.ok, null)
				.show();

	        } catch (Exception e) {
	            finish();

	        } finally {
	            try {
	                isF.close();
	            } catch (Exception e) {/**NON BLOCK**/}
	        }
	        break;
		case EMAIL_SUPPORT:
			/* Create the Email Intent */
			Intent emailIntent = new Intent(Intent.ACTION_SEND);

			/* Fill it with Data */
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.email_summary)});
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Regarding " + getString(R.string.app_name)
					+ getString(R.string.version));
			//emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

			/* Send it off to the Activity-Chooser */
			startActivity(Intent.createChooser(emailIntent, "Send mail"));
			break;
		}
	}

	private void prefControl() {
		// TODO Auto-generated method stub
    	// Accessing Preferences
    	SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	// Checking Theme value
	    String theme_value = pref.getString("theme", "100");
	    if(theme_value.contains("100")){
	    	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	    		// Setting Holo Dark theme for 3.0 & above
	    		setTheme(android.R.style.Theme_Holo);
	    	else
	    		// Setting Black theme for 2.3
	    		setTheme(android.R.style.Theme_Black);
	    }
	    else{
	    	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	    		// Setting Holo Light theme for 3.0 & above
	    		setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
	    	else
	    		// Setting White theme for 2.3
	    		setTheme(android.R.style.Theme_Light);
	    }
        boolean full_pot = pref.getBoolean("portrait", false);
        if(full_pot == true)
        	//For getting full screen
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.prefs, menu);
	        return true;
	    }
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
		 case R.id.exit:
             finish();
             SharedPreferences effect_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
             boolean fade = effect_pref.getBoolean("fade", false);
             if(fade==true)
            	 overridePendingTransition(R.anim.slider_in, R.anim.slider_out);
             return true;
		}
		return super.onOptionsItemSelected(item);
	}
		
	@SuppressWarnings("deprecation")
	private void setupSimplePreferencesScreen() {
		// In the simplified UI, fragments are not used at all and we instead
		// use the older PreferenceActivity APIs.

		// Add 'general' preferences.
		prefControl();
		addPreferencesFromResource(R.xml.pref_general);	
		setupDialog();
	}

	/**
	 * Helper method to determine if the device has an extra-large screen. For
	 * example, 10" tablets are extra-large.
	 */

	/**
	 * Determines whether the simplified settings UI should be shown. This is
	 * true if this is forced via {@link #ALWAYS_SIMPLE_PREFS}, or the device
	 * doesn't have newer APIs like {@link PreferenceFragment}, or the device
	 * doesn't have an extra-large screen. In these cases, a single-pane
	 * "simplified" settings UI should be shown.
	 */
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		// TODO Auto-generated method stub
	        SharedPreferences scroll = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	        boolean scroll_fast = scroll.getBoolean("scroll", false);
		    if (key.equals(THEME_KEY)) {
		    	// Restarting activity after changing theme
		    	Intent restart = getIntent();
		    	finish();
		    	startActivity(restart);
			    }
		    /*if (key.equals("layout")) {
		    	// Restarting main activity after changing layout
		    	Intent restart = new Intent(this , Algorithm.class);
		    	restart.putExtra("restart" , "layout");		   
		    	startActivityForResult(restart, 1);
			    } */
		    if (key.equals(FULL_KEY)) {
		    	// Restarting activity after changing full screen toggle
		    	Intent restart = getIntent();
		    	finish();
		    	startActivity(restart);
			    }
	        if (key.equals(KEY_PREF_SCROLL)) {
	        	if(scroll_fast == true) {
	        		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
	        		{
	        			new AlertDialog.Builder(this)
	        			.setTitle("Compatibility Issue")
					    .setIcon(android.R.drawable.ic_dialog_info)
					    .setMessage(R.string.os_version_error)
					    .setNeutralButton(android.R.string.ok, null)
					    .show();
	        			}
	        		}
	        	}
	        }
            /* @SuppressWarnings("deprecation")
			Preference splash = findPreference(key);
             Set summary to be the user-description for the selected value
            connectionPref.setOnPreferenceClickListener(new OnPreferenceClickListener(){

				@Override
				public boolean onPreferenceClick(Preference arg0) {
					// TODO Auto-generated method stub
					finish();
					return false;
				}
            }	
            ); */		

	
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
	    super.onResume();
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
	    super.onPause();
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	private void ActivityAnimation() {
		// TODO Auto-generated method stub
    	SharedPreferences effect_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	boolean fade = effect_pref.getBoolean("fade", false);
    	if(fade==true)
    		overridePendingTransition(R.anim.slider_in, R.anim.slider_out);
	}
	
	@Override
	public void onBackPressed() {
	 // TODO Auto-generated method stub	 
	 finish();
	 ActivityAnimation();   		 
	}
	
	/*
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,final Key key) {		
		if(preferenceScreen.equals("about_category_key"))
		{
     if(key.equals("changelog")) {
    	 new AlertDialog.Builder(this)
			.setTitle("Working")
			.setIcon(android.R.drawable.ic_dialog_info)
			.setMessage("This will not work on phones having ANDROID 2.3 or low")
			.setNeutralButton(android.R.string.ok, null)
			.show();
     }
		}
	return false;
  }
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		// TODO Auto-generated method stub
		if(key.equals("changelog"))
		{
			new AlertDialog.Builder(this)
			.setTitle("Working")
			.setIcon(android.R.drawable.ic_dialog_info)
			.setMessage("This will not work on phones having ANDROID 2.3 or low")
			.setNeutralButton(android.R.string.ok, null)
			.show();
		}
		
	}
	*/
}

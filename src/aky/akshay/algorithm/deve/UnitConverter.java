package aky.akshay.algorithm.deve;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class UnitConverter extends Activity {
	
	ListView UnitList;
	
	// Constant integers for items in list / grid
	public static final int ANGLE = 0;
	public static final int AREA = 1;
	public static final int CURRENT = 2;
	public static final int DISTANCE = 3;
	public static final int ENERGY = 4;
	public static final int FORCE = 5;
	public static final int NSC = 6;
	public static final int POWER = 7;
	public static final int PRESSURE = 8;
	public static final int RADIO = 9;
	public static final int TEMP = 10;
	public static final int TIME = 11;
	public static final int WEIGHT = 12;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Design Management is defined in this method
		prefControl(); 
		// Setting VIEW
		setContentView(R.layout.activity_unit_converter);
		// Activating Animation
		ActivityAnimation();   	
		SetId();
		// Setting Action Bar
		setupActionBar();
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void SetId() {
		// TODO Auto-generated method stub
		UnitList = (ListView)findViewById(R.id.unit_convert_list);
		/* If the device has version more than 3.0 than scroll bar is to be displayed 
		  * Else , it is not displayed DUE TO COMPATIBILITY ISSUES */
		 if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			 SharedPreferences scroll = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	         boolean scroll_fast = scroll.getBoolean("scroll", false);
	         if(scroll_fast == true)
	        	 UnitList.setFastScrollAlwaysVisible(true);
	         }
		 UnitList.setAdapter(new UnitAdapter(this, UnitAdapter.names));
		 UnitList.setOnItemClickListener(new OnItemClickListener() {
	        	@Override
	        	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
	        		switch(position){
	        		case ANGLE:
	        			try {
	        				Class<?> ourClass0 = Class.forName("aky.akshay.algorithm.conversion.Angle");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass0);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException a) {
	        				a.printStackTrace();
	        			}
	        			break;
	        		case AREA:
	        			try {
	        				Class<?> ourClass1 = Class.forName("aky.akshay.algorithm.conversion.Area");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass1);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException b) {
	        				b.printStackTrace();
	        			}
	        			break;
	        		case CURRENT:
	        			try {
	        				Class<?> ourClass0 = Class.forName("aky.akshay.algorithm.conversion.Current");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass0);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException a) {
	        				a.printStackTrace();
	        			}
	        			break;
	        		case DISTANCE:
	        			try {
	        				Class<?> ourClass1 = Class.forName("aky.akshay.algorithm.conversion.Distance");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass1);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException b) {
	        				b.printStackTrace();
	        			}
	        			break;
	        		case ENERGY:
	        			try {
	        				Class<?> ourClass0 = Class.forName("aky.akshay.algorithm.conversion.Energy");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass0);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException a) {
	        				a.printStackTrace();
	        			}
	        			break;
	        		case FORCE:
	        			try {
	        				Class<?> ourClass1 = Class.forName("aky.akshay.algorithm.conversion.Force");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass1);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException b) {
	        				b.printStackTrace();
	        			}
	        			break;
	        		case NSC:
	        			try {
	        				Class<?> ourClass0 = Class.forName("aky.akshay.algorithm.conversion.NSC");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass0);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException a) {
	        				a.printStackTrace();
	        			}
	        			break;
	        		case POWER:
	        			try {
	        				Class<?> ourClass0 = Class.forName("aky.akshay.algorithm.conversion.Power");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass0);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException a) {
	        				a.printStackTrace();
	        			}
	        			break;
	        		case PRESSURE:
	        			try {
	        				Class<?> ourClass1 = Class.forName("aky.akshay.algorithm.conversion.Pressure");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass1);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException b) {
	        				b.printStackTrace();
	        			}
	        			break;
	        		case RADIO:
	        			try {
	        				Class<?> ourClass0 = Class.forName("aky.akshay.algorithm.conversion.RadioActivity");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass0);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException a) {
	        				a.printStackTrace();
	        			}
	        			break;
	        		case TEMP:
	        			try {
	        				Class<?> ourClass1 = Class.forName("aky.akshay.algorithm.conversion.Temperature");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass1);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException b) {
	        				b.printStackTrace();
	        			}
	        			break;
	        		case TIME:
	        			try {
	        				Class<?> ourClass0 = Class.forName("aky.akshay.algorithm.conversion.Time");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass0);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException a) {
	        				a.printStackTrace();
	        			}
	        			break;
	        		case WEIGHT:
	        			try {
	        				Class<?> ourClass1 = Class.forName("aky.akshay.algorithm.conversion.Weight");
	        				Intent list1 = new Intent(UnitConverter.this, ourClass1);
	        				startActivity(list1);
	        			} catch (ClassNotFoundException b) {
	        				b.printStackTrace();
	        			}
	        			break;
		            	}}
		        });
		
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
    
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
			// Show subtitle for Action bar
			getActionBar().setSubtitle("Converter Collection");
			}		
	}
	
	private void ActivityAnimation() {
		// TODO Auto-generated method stub
    	SharedPreferences effect_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	boolean fade = effect_pref.getBoolean("fade", false);
    	if(fade==true)
    		overridePendingTransition(R.anim.slider_in, R.anim.slider_out);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_algorithm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
		case R.id.exit:
             finish();
             ActivityAnimation();
             return true;
        case R.id.menu_settings:
        	Intent pref = new Intent(this,SettingsActivity.class);
            startActivity(pref);
            return true;
        case R.id.menu_help:
         	Intent menu_help = new Intent(this, Help.class);
         	startActivity(menu_help);
         	return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 @Override
		public void onBackPressed() {
		 // TODO Auto-generated method stub
	    	finish();
	    	ActivityAnimation();
		}

}

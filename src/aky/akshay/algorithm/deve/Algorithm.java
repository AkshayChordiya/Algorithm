package aky.akshay.algorithm.deve;

import java.io.InputStream;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.ls.list.threed.ThreeDListView;

/* Starting point for APPLICATION */
@SuppressLint("NewApi")
public class Algorithm extends Activity{

	private static String LOG_TAG = Algorithm.class.getName();
	
	// Declaring List & Grid Views	
	ListView algo;
	GridView Galgo;
	
	// Declaring List Adapter
	//List_Adapter adapter;
	
	// Constant integers for items in list / grid
	public static final int SCALC = 0;
	public static final int PYTHAGORAS = 1;
	public static final int QUAD = 2;
	public static final int ARM = 3;
	public static final int FACT = 4;
	public static final int PH = 5;
	public static final int MATRIX = 6;
	public static final int TRIANGLE = 7;
	public static final int BMI = 8;
	public static final int UNIT = 9;
	public static final int PLANNEDLOG = 10;
	public static final int CHANGELOG = 11;
	
	// Names for each item
	public static final String classes[] = { "Smart Calculator","Pythagoras Theorem", "Quadratic Equation","Armstrong Number",
			"Factorial Generator","pH / pOH Calculator","Matrix Calculator","Triangle Calculator",
			"BMI Calculator","Unit Converter", "Planned Features","Changelog"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		 
		Log.d(LOG_TAG, "Starting Algorithm");
		// Design Management is defined in this method
		prefControl(); 
		// Switching between List View & Grid View
		SwitchView(); 	
		// Setup the action bar
		setupActionBar();
		// Get proximity sensor preference
		SharedPreferences proximity_exit = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	final boolean isProximity = proximity_exit.getBoolean("rapidExit", false);
    	// Check if proximity is ON
    	if(isProximity)
    		// Use Proximity Sensor
    		getProximity();
		 //Default animation so that app loads beautifully
	     overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		//Check if the app has been run before.
		boolean firstrun = getSharedPreferences("pref_general", MODE_PRIVATE).getBoolean("quick_start", true);
		getSharedPreferences("pref_general", MODE_PRIVATE)
		.edit()
		.putBoolean("quick_start", false)
		.commit();
		if (firstrun){
			// Starting Quick Guide only on first run
			Log.d(LOG_TAG, "Starting Quick Start Guide");
			Intent guide = new Intent(this , QuickStart.class);
			startActivity(guide);
		}				
	}

	private void SwitchView() {
		// TODO Auto-generated method stub
		SharedPreferences layout = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	    String layout_value = layout.getString("layout", "500");
	    if(layout_value.contains("550")){
	    	// Setting View (Grid)
	    	Log.d(LOG_TAG, "Setting Grid View");
	    	setContentView(R.layout.grid_algorithm);
	    	setGridView();
	    }
	    else if(layout_value.contains("500")){ 
	    	// Setting View (List)
	    	Log.d(LOG_TAG, "Setting List View");
	    	setContentView(R.layout.activity_algorithm);
	    	setListView();		 
	    }
	    else if(layout_value.contains("600")){ 
	    	// Setting View (3D List)
	    	Log.d(LOG_TAG, "Setting 3D List View");
	    	setContentView(R.layout.three_d_list);
	    	set3DListView();		 
	    }
	}
	
	private ThreeDListView set3DListView() {
		// TODO Auto-generated method stub
		
		final ThreeDListView list = (ThreeDListView) this.findViewById(R.id.threeDListView);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		list.setAdapter(new ArrayAdapter<String>(this, R.layout.three_d_list_item, classes));
		list.setFastScrollEnabled(true);
		//list.setSmoothScrollbarEnabled(true);
		//list.isFastScrollAlwaysVisible();
		list.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	// End Correction for 3D list View
	        	switch(position-1){
	        	case SCALC:
	        		InflateActivityReuse(SCALC);
	        		break;
	        	case PYTHAGORAS:
	        		InflateActivityReuse(PYTHAGORAS);
	        		break;
	        	case QUAD:
	        		InflateActivityReuse(QUAD);
	        		break;
	        	case ARM:
	        		InflateActivityReuse(ARM);
	        		break;
	        	case FACT:
	        		InflateActivityReuse(FACT);
	        		break;
	        	case PH:
	        		InflateActivityReuse(PH);
	        		break;
	        	case MATRIX:
	        		InflateActivityReuse(MATRIX);
	        		break;
	        	case TRIANGLE:
	        		InflateActivityReuse(TRIANGLE);
	        		break;
	        	case BMI:
	        		InflateActivityReuse(BMI);
	        		break;
	        	case UNIT:
	        		InflateActivityReuse(UNIT);
	        		break;
	        	case PLANNEDLOG:
	        		InflateActivityReuse(PLANNEDLOG);
	        		break;
	        	case CHANGELOG:
	        		InflateActivityReuse(CHANGELOG);
	        		break;        		
	        	}
	        }
	    });
		return list;
	}

	/******************* Grid View Codes ******************************/
	private void setGridView() {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "Implementing Grid View");
		Galgo = (GridView)findViewById(R.id.gridview);
		/* If the device has version more than 3.0 than scroll bar is to be displayed 
		  * Else , it is not displayed DUE TO COMPATIBILITY ISSUES */
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			SharedPreferences scroll = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			boolean scroll_fast = scroll.getBoolean("scroll", false);
			if(scroll_fast)
				Galgo.setFastScrollAlwaysVisible(true);
		}
		Galgo.setAdapter(new GridAdapter(this));
		 // On Click Listener for Grid View
		Galgo.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	switch(position){
	        	case SCALC:
	        		InflateActivityReuse(SCALC);
	        		break;
	        	case PYTHAGORAS:
	        		InflateActivityReuse(PYTHAGORAS);
	        		break;
	        	case QUAD:
	        		InflateActivityReuse(QUAD);
	        		break;
	        	case ARM:
	        		InflateActivityReuse(ARM);
	        		break;
	        	case FACT:
	        		InflateActivityReuse(FACT);
	        		break;
	        	case PH:
	        		InflateActivityReuse(PH);
	        		break;
	        	case MATRIX:
	        		InflateActivityReuse(MATRIX);
	        		break;
	        	case TRIANGLE:
	        		InflateActivityReuse(TRIANGLE);
	        		break;
	        	case BMI:
	        		InflateActivityReuse(BMI);
	        		break;
	        	case UNIT:
	        		InflateActivityReuse(UNIT);
	        		break;
	        	case PLANNEDLOG:
	        		InflateActivityReuse(PLANNEDLOG);
	        		break;
	        	case CHANGELOG:
	        		InflateActivityReuse(CHANGELOG);
	        		break;        		
	        	}
	        }
	    });
	}
	
	/******************* List View Codes ******************************/
	private void setListView() {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "Implementing List View");
		algo = (ListView)findViewById(R.id.listView1);
		 /* If the device has version more than 3.0 than scroll bar is to be displayed 
		  * Else , it is not displayed DUE TO COMPATIBILITY ISSUES */
		 if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			 SharedPreferences scroll = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	         boolean scroll_fast = scroll.getBoolean("scroll", false);
	         if(scroll_fast)
	        	 algo.setFastScrollAlwaysVisible(true);
		 }
		 //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,classes);
		 //adapter = new List_Adapter(this, classes);
		 algo.setAdapter(new List_Adapter(this, classes));
		 /***************************List View Animation****************************/
		 Log.d(LOG_TAG, "List View Rendering Animation");
		 SharedPreferences render = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		 String render_anim = render.getString("render_animation", "1002");
        if(render_anim.contains("1002")){
        	// Fading Animation for list items
        	AnimationSet set = new AnimationSet(true);
        	Animation animation = new AlphaAnimation(0.0f, 1.0f);
        	animation.setDuration(250);
        	set.addAnimation(animation);
        	LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f); 
        	algo.setLayoutAnimation(controller);
        	}
        else if(render_anim.contains("1003")){
        	AnimationSet set = new AnimationSet(true);
       	    Animation animation = new AlphaAnimation(0.0f, 1.0f);
       	    animation.setDuration(50);
       	    set.addAnimation(animation);
       	    animation = new TranslateAnimation(
       	        Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
       	        Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f
       	        );
       	    animation.setDuration(150);
       	    set.addAnimation(animation);
       	    LayoutAnimationController controller = new LayoutAnimationController(set, 1.0f);
       	    algo.setLayoutAnimation(controller);
       	    }
        Log.d(LOG_TAG, "Setting Adapter for list view");
        // On Click Listener for List View
        algo.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        		switch(position){
        		case SCALC:
        			InflateActivityReuse(SCALC);
        			break;
        		case PYTHAGORAS:
        			InflateActivityReuse(PYTHAGORAS);
        			break;
		        	case QUAD:
		        		InflateActivityReuse(QUAD);
		        		break;
		        	case ARM:
		        		InflateActivityReuse(ARM);
		        		break;
		        	case FACT:
		        		InflateActivityReuse(FACT);
		        		break;
		        	case PH:
		        		InflateActivityReuse(PH);
		        		break;
		        	case MATRIX:
		        		InflateActivityReuse(MATRIX);
		        		break;
		        	case TRIANGLE:
		        		InflateActivityReuse(TRIANGLE);
		        		break;
		        	case BMI:
		        		InflateActivityReuse(BMI);
		        		break;
		        	case UNIT:
		        		InflateActivityReuse(UNIT);
		        		break;
		        	case PLANNEDLOG:
		        		InflateActivityReuse(PLANNEDLOG);
		        		break;
		        	case CHANGELOG:
		        		InflateActivityReuse(CHANGELOG);
		        		break;  
	            	}}
	        });
	}

	private void prefControl() {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "Switching Theme");
    	SharedPreferences theme = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	    String theme_value = theme.getString("theme", "100");
	    if(theme_value.contains("100")){
	    	Log.d(LOG_TAG, "Dark theme selected");
	    	if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	    		setTheme(android.R.style.Theme_Holo);
	    	else
	    		setTheme(android.R.style.Theme_Black);
	    }else{
	    	Log.d(LOG_TAG, "Light theme selected");
	    	if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	    		setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
	    	else
	    		setTheme(android.R.style.Theme_Light);
	    }
        boolean full_pot = theme.getBoolean("portrait", false);
        if(full_pot)
        	//For getting full screen     	
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	/***********Unified Coding for on item click ***********/
	public void InflateActivityReuse(int reusing){
		Log.d(LOG_TAG, "Setting onItemClick");
		Class<?> ourClass;
		Intent list;
		switch(reusing){
		case SCALC:
			try {
				ourClass = Class.forName("aky.akshay.algorithm.deve.Scalc");
				list = new Intent(Algorithm.this, ourClass);
				startActivity(list);
			} catch (ClassNotFoundException a) {
				a.printStackTrace();
			}
			break;
    	case PYTHAGORAS:
			try {
				ourClass = Class.forName("aky.akshay.algorithm.deve.Pythagoras");
				list = new Intent(Algorithm.this, ourClass);
				startActivity(list);
			} catch (ClassNotFoundException a) {
				a.printStackTrace();
			}
			break;
    	case QUAD:
			try {
				ourClass = Class.forName("aky.akshay.algorithm.deve.Quad");
				list = new Intent(Algorithm.this, ourClass);
				startActivity(list);
			} catch (ClassNotFoundException c) {
				c.printStackTrace();
			}
			break;
    	case ARM:
			try {
				ourClass = Class.forName("aky.akshay.algorithm.deve.Armstrong");
				list = new Intent(Algorithm.this, ourClass);
				startActivity(list);
			} catch (ClassNotFoundException d) {
				d.printStackTrace();
			}
			break;
    	case FACT:
			try {
				ourClass = Class.forName("aky.akshay.algorithm.deve.Factorial");
				list = new Intent(Algorithm.this, ourClass);
				startActivity(list);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;    	
    	case PH:
			try {
				ourClass = Class.forName("aky.akshay.algorithm.deve.Ph");
				list = new Intent(Algorithm.this, ourClass);
				startActivity(list);
			} catch (ClassNotFoundException h) {
				h.printStackTrace();
			}
			break;   	
    	case MATRIX:
			try {
				ourClass = Class.forName("aky.akshay.algorithm.deve.MatrixEngine");
				list = new Intent(Algorithm.this, ourClass);
				startActivity(list);
			} catch (ClassNotFoundException j) {
				j.printStackTrace();
			}
			break;
    	case TRIANGLE:
			try {
				ourClass = Class.forName("aky.akshay.algorithm.deve.Triangle");
				list = new Intent(Algorithm.this, ourClass);
				startActivity(list);
			} catch (ClassNotFoundException k) {
				k.printStackTrace();
			}
			break;
    	case BMI:
    		try {
				ourClass = Class.forName("aky.akshay.algorithm.deve.BMI");
				list = new Intent(Algorithm.this, ourClass);
				startActivity(list);
			} catch (ClassNotFoundException k) {
				k.printStackTrace();
			}
    		break;
    	case UNIT:
    		try {
				ourClass = Class.forName("aky.akshay.algorithm.deve.UnitConverter");
				list = new Intent(Algorithm.this, ourClass);
				startActivity(list);
			} catch (ClassNotFoundException k) {
				k.printStackTrace();
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
    	case CHANGELOG://Launch change log dialog
	        InputStream is = getApplicationContext().getResources().openRawResource(R.raw.changelog);
	        if (is == null) {
	            finish();
	            return;
	        }
	        try {
	            // Read the change log
	            StringBuilder sb = new StringBuilder();
	            int read = 0;
	            byte[] data = new byte[512];
	            while ((read = is.read(data, 0, 512)) != -1) {
	                sb.append(new String(data, 0, read));
	            }

	            // Show a dialog
	            new AlertDialog.Builder(this)
				.setTitle(R.string.about_changelog_title)
				.setIcon(R.drawable.author)
				.setMessage(sb.toString())
				.setNeutralButton(android.R.string.ok, null)
				.show();

	        } catch (Exception e) {
	            finish();

	        } finally {
	            try {
	                is.close();
	            } catch (Exception e) {/**NON BLOCK**/}
	        }
			break;
		}
	}
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	Log.d(LOG_TAG, "Inflated Menu");
		getMenuInflater().inflate(R.menu.activity_algorithm, menu);
		return true;
	}
    
    private void getProximity() {
		// TODO Auto-generated method stub
		// Getting Sensor Manager
    	SensorManager mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    	// Setting Sensor from Sensor Manager to Proximity
    	Sensor Proximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);		
    	
    	// Checking device has Proximity sensor or not
    	if(Proximity != null){
    		Log.d(LOG_TAG, "Proximity Sensor Available");
    		mSensorManager.registerListener(ProximitySensorListener, Proximity, SensorManager.SENSOR_DELAY_NORMAL);
    	}else{
    		// If the device doesn't have Proximity sensor
    		Log.d(LOG_TAG, "Proximity Sensor UnAvailable");					
    		new AlertDialog.Builder(this)
    		.setTitle(R.string.sensor_error)
    		.setIcon(android.R.drawable.ic_dialog_info)
    		.setMessage(R.string.proximity_sensor_error)
    		.setNeutralButton(android.R.string.ok, null)
    		.show();
    	}				
	}
	
	/******* Event Listener for Proximity Sensor ********/
	private final SensorEventListener ProximitySensorListener = new SensorEventListener(){
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
					// TODO Auto-generated method stub
			}
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			//if(event.sensor.getType() == Sensor.TYPE_LIGHT)
			float ProximityValue = event.values[0];
			if(ProximityValue == 0.0)
				ExitScript();
			
			}
		
		
		};		
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.exit:	
			ExitScript();
			break;
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
    	ExitScript();
	}
    
	private void ExitScript() {
		// TODO Auto-generated method stub
		SharedPreferences terminate = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	final boolean exit_terminate = terminate.getBoolean("fastExit", false);
    	boolean exit = terminate.getBoolean("exit", true);
	     if(exit){
	    	 new AlertDialog.Builder(this)
	    	 .setTitle("Exit ?")
	    	 .setMessage("Are you sure you wanna exit ?")
	    	 .setNeutralButton(android.R.string.ok, null)
	    	 .setPositiveButton("Yes",new DialogInterface.OnClickListener(){
	    		 @Override
	    		 public void onClick(DialogInterface dialoginterface, int i){
	    			 if(exit_terminate)
	    				 onDestroy();
	    			 else
	    				 finish();
	    			 }
	    		 })
	    		 .setNeutralButton(android.R.string.cancel,null)
	    		 .show();
	    	 }else{
	    		 if(exit_terminate)
	    			 onDestroy();
	    		 else
	    			 finish();
	    		 }
	     }

	// Called at the end of the full lifetime.
    @Override
    public void onDestroy(){
		// Clean up any resources including ending threads,
		// closing database connections etc.
		//Default animation so that app closes beautifully
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		Log.d(LOG_TAG, "Destroying App");
		SharedPreferences proximity_exit = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	final boolean isProximity = proximity_exit.getBoolean("rapidExit", false);
    	// Check if proximity is ON
    	if(isProximity){
    		// Getting Sensor Manager
        	SensorManager mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    		// Unregistering Proximity Sensor
    		mSensorManager.unregisterListener(ProximitySensorListener);
    	}
    	Log.d(LOG_TAG, "UnRegistered Sensor");
		System.exit(0);
		super.onDestroy();
		}

	@Override
	protected void onResume() {
		Log.d(LOG_TAG, "Resuming Algorithm");		
		// TODO Auto-generated method stub
		SharedPreferences proximity_exit = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	final boolean isProximity = proximity_exit.getBoolean("rapidExit", false);
    	// Check if proximity is ON
    	if(isProximity)
    		// Use Proximity Sensor
    		getProximity();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "Paused Algorithm");		
		SharedPreferences proximity_exit = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	final boolean isProximity = proximity_exit.getBoolean("rapidExit", false);
    	// Check if proximity is ON
    	if(isProximity){
    		// Getting Sensor Manager
        	SensorManager mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    		// Unregistering Proximity Sensor
    		mSensorManager.unregisterListener(ProximitySensorListener);
    	}
		super.onPause();
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void setupActionBar() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
			// Show the Up button in the action bar.
			getActionBar().setSubtitle("Maths Heaven");
	}
	
	

	
	
	
}

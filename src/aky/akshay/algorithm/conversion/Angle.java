package aky.akshay.algorithm.conversion;

import de.keyboardsurfer.android.widget.Crouton;
import de.keyboardsurfer.android.widget.Style;
import aky.akshay.algorithm.deve.Help;
import aky.akshay.algorithm.deve.R;
import aky.akshay.algorithm.deve.SettingsActivity;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Angle extends Activity {
	
	// Declaring list views
	ListView base , convert;
	
	// Declaring Edit Text for input
	EditText input;
	
	// Declaring Strings to storing
	String type = "" , result = "";
	
	// Declaring double variables for manipulation
	double init , fina ; 
	
	// List Items
	String items[] = {"Degree" , "Radian" , "Minute" , "Second" , "Gradian"};
	
	// Booleans for list items
	boolean degree = false , radian = false , minute = false , second = false , gradian = false;
	
	// Boolean for parsing check
	boolean isParseFail = false;
	
	// Integer Constants for LIST item
	public static final int Degree = 0;
	public static final int Radian = 1;
	public static final int Minute = 2;
	public static final int Second = 3;
	public static final int Gradian = 4;
	
	// Integer Constants for CONVERSION
	public static final int DegreetoRadian = 0;
	public static final int DegreetoMinute = 1;
	public static final int DegreetoSecond = 2;
	public static final int DegreetoGradian = 3;
	public static final int RadiantoDegree = 4;
	public static final int RadiantoMinute = 5;
	public static final int RadiantoSecond = 6;
	public static final int RadiantoGradian = 7;
	public static final int MinutetoDegree = 8;
	public static final int MinutetoRadian = 9;
	public static final int MinutetoSecond = 10;
	public static final int MinutetoGradian = 11;
	public static final int SecondtoDegree = 12;
	public static final int SecondtoRadian = 13;
	public static final int SecondtoMinute = 14;
	public static final int SecondtoGradian = 15;
	public static final int GradiantoDegree = 16;
	public static final int GradiantoRadian = 17;
	public static final int GradiantoMinute = 18;
	public static final int GradiantoSecond = 19;
	
	public static final int SametoSame = 99;
	
	// Integer Constants for DIALOG
	public static final int NO_INPUT = 0;
	public static final int RESULT = 1;
	public static final int NO_BASE = 2;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefControl(); // Design Management is defined in this method
		setContentView(R.layout.activity_angle);		
        ActivityAnimation();
        setupInput();
        setupListView();
        setupActionBar();
	}
	
	private void setupInput() {
		// TODO Auto-generated method stub
		input = (EditText)findViewById(R.id.input_to_convert);
	}

	private void setupListView() {
		// TODO Auto-generated method stub	
		
		// Initializing Base List
		base = (ListView)findViewById(R.id.base_list);
		
		// Initializing Convert List
		convert = (ListView)findViewById(R.id.conv_list);
		
		// Setting array adapter for strings of list items
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
		        		items);  
		
		// Setting adapter for base list
		base.setAdapter(adapter);
		// Setting Item Click for base list
		base.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
				// Generating code for each position
				switch(position){
				case Degree:
					// Setting corresponding boolean & message
					degree = true;
					Toast.makeText(getBaseContext(), "Degree Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					radian = false;
					minute = false;
					second = false;
					gradian = false;
					break;
				case Radian:
					// Setting corresponding boolean & message
					radian = true;
					Toast.makeText(getBaseContext(), "Radian Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					degree = false;
					minute = false;
					second = false;
					gradian = false;
					break;
				case Minute:
					// Setting corresponding boolean & message
					minute = true;
					Toast.makeText(getBaseContext(), "Minute Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					degree = false;
					radian = false;
					second = false;
					gradian = false;
					break;
				case Second:
					// Setting corresponding boolean & message
					second = true;
					Toast.makeText(getBaseContext(), "Second Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					degree = false;
					radian = false;
					minute = false;
					gradian = false;
					break;	
				case Gradian:
					// Setting corresponding boolean & message
					gradian = true;
					Toast.makeText(getBaseContext(), "Gradian Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					degree = false;
					radian = false;
					minute = false;
					second = false;
					break;	
            	} 
			}
        	
        });
		
		// Setting adapter for convert list
		convert.setAdapter(adapter);
		// Setting Item Click for convert list
		convert.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
				// Generating code for each position
				switch(position){   
				case Degree:
					if(degree){
						// Setting conversion dynamically
						type = "Degree to Degree :";
						convert(SametoSame);						
					}
					else if(radian){
						// Setting conversion dynamically
						type = "Radian to Degree :";
						convert(RadiantoDegree);
					}
					else if(minute){
						// Setting conversion dynamically
						type = "Minute to Degree :";
						convert(MinutetoDegree);
					}
					else if(second){
						// Setting conversion dynamically
						type = "Second to Degree :";
						convert(SecondtoDegree);
					}
					else if(gradian){
						// Setting conversion dynamically
						type = "Gradian to Degree :";
						convert(GradiantoDegree);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
				case Radian:
					if(degree){
						// Setting conversion dynamically
						type = "Degree to Radian :";
						convert(DegreetoRadian);
					}
					else if(radian){
						// Setting conversion dynamically
						type = "Radian to Radian :";
						convert(SametoSame);
					}
					else if(minute){
						// Setting conversion dynamically
						type = "Minute to Radian :";
						convert(MinutetoRadian);
					}
					else if(second){
						// Setting conversion dynamically
						type = "Second to Radian :";
						convert(SecondtoRadian);
					}
					else if(gradian){
						// Setting conversion dynamically
						type = "Gradian to Radian :";
						convert(GradiantoRadian);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
				case Minute:
					if(degree){
						// Setting conversion dynamically
						type = "Degree to Minute :";
						convert(DegreetoMinute);
					}
					else if(radian){
						// Setting conversion dynamically
						type = "Radian to Minute :";
						convert(RadiantoMinute);
					}
					else if(minute){
						// Setting conversion dynamically
						type = "Minute to Minute :";
						convert(SametoSame);
					}
					else if(second){
						// Setting conversion dynamically
						type = "Second to Minute :";
						convert(SecondtoMinute);
					}
					else if(gradian){
						// Setting conversion dynamically
						type = "Gradian to Minute :";
						convert(GradiantoMinute);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
				case Second:
					if(degree){
						// Setting conversion dynamically
						type = "Degree to Second :";
						convert(DegreetoSecond);
					}
					else if(radian){
						// Setting conversion dynamically
						type = "Radian to Second :";
						convert(RadiantoSecond);
					}
					else if(minute){
						// Setting conversion dynamically
						type = "Minute to Second :";
						convert(MinutetoSecond);
					}
					else if(second){
						// Setting conversion dynamically
						type = "Second to Second :";
						convert(SametoSame);
					}
					else if(gradian){
						// Setting conversion dynamically
						type = "Gradian to Second :";
						convert(GradiantoSecond);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
				case Gradian:
					if(degree){
						// Setting conversion dynamically
						type = "Degree to Gradian :";
						convert(DegreetoGradian);
					}
					else if(radian){
						// Setting conversion dynamically
						type = "Radian to Gradian :";
						convert(RadiantoGradian);
					}
					else if(minute){
						// Setting conversion dynamically
						type = "Minute to Gradian :";
						convert(MinutetoGradian);
					}
					else if(second){
						// Setting conversion dynamically
						type = "Second to Gradian :";
						convert(SecondtoGradian);
					}
					else if(gradian){
						// Setting conversion dynamically
						type = "Gradian to Gradian :";
						convert(SametoSame);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
            	}
			}
        	
        });
	}

	protected void convert(int val) {
		// TODO Auto-generated method stub
		try{
			// Trying to get input from Edit Text box
			result = input.getText().toString();
			init = Double.parseDouble(result);
		}catch(Exception parse){
			// Catching error if unsuccessful
			isParseFail = true;			
			// Show Dialog of error
			displaydialog(NO_INPUT);
		}
		if(!isParseFail){
			// If no error
			switch(val){		
			case SametoSame:
				// Displaying same value
				// Hence, No need for any algorithm
				break;
			case DegreetoRadian:
				// Converting by multiplying factor required 
				init = init * (Math.PI / 180 );
				break;
			case DegreetoMinute:
				// Converting by multiplying factor required 
				init = init * 60;
				break;
			case DegreetoSecond:
				// Converting by multiplying factor required 
				init = init * 3600;
				break;	
			case DegreetoGradian:
				// First Convert to Radian
				// Converting by calling Degree to Radian
				init = init * (Math.PI / 180 );
				//convert(DegreetoRadian);
				// Then converting by calling Radian to Gradian
				//convert(RadiantoGradian);
				init = init * (200 / Math.PI);
				break;	
			case RadiantoDegree:
				// Converting by multiplying factor required 
				init = init * (180 / Math.PI);
				break;			
			case RadiantoMinute:
				// First Converting to Degree
				// Converting by calling Radian to Degree
				//convert(RadiantoDegree);
				init = init * (180 / Math.PI);
				// Then, Converting by calling Degree to Minute
				//convert(DegreetoMinute);
				init = init * 60;
				break;
			case RadiantoSecond:
				// First Converting to Degree
				// Converting by calling Radian to Degree
				//convert(RadiantoDegree);
				init = init * (180 / Math.PI);
				// Then, Converting by calling Degree to Second
				//convert(DegreetoSecond);
				init = init * 3600;
				break;	
			case RadiantoGradian:
				// Converting by multiplying factor required 
				init = init * (200 / Math.PI);
				break;	
			case MinutetoDegree:
				// Converting by multiplying factor required 
				init = init / 60;				
				break;	
			case MinutetoRadian:
				// First Converting to Minute
				// Converting by calling Minute to Degree
				//convert(MinutetoDegree);
				init = init / 60;		
				// Then, converting by calling Degree to Radian
				//convert(DegreetoRadian);
				init = init * (Math.PI / 180 );
				break;			
			case MinutetoSecond:
				// Converting by multiplying factor required 
				init = init * 60;
				break;	
			case MinutetoGradian:
				// Converting by calling Minute to Degree
				//convert(MinutetoRadian);
				init = init / 60;	
				// Converting Degree to Radian 
				init = init * (Math.PI / 180 );
				// Then, converting by calling Radian to Gradian
				init = init * (200 / Math.PI);
				break;
			case SecondtoDegree:
				// Converting by multiplying factor required 
				init = init / 3600;
				break;	
			case SecondtoRadian:
				// Converting by calling Second to Degree
				init = init / 3600;
				// Converting by calling Degree to Radian
				init = init * (Math.PI / 180 );
				break;
			case SecondtoMinute:
				// Converting by multiplying factor required 
				init = init / 60;
				break;	
			case SecondtoGradian:
				// Converting by calling Second to Degree
				init = init / 3600;
				// Converting by calling Degree to Radian
				init = init * (Math.PI / 180 );
				// Converting by calling Radian to Gradian
				init = init * (200 / Math.PI);
				break;	
			case GradiantoDegree:
				// Converting by calling Gradian to Radian 
				//convert(GradiantoRadian);
				init = init * (Math.PI / 200 );
				// Converting by calling radian to degree
				//convert(RadiantoDegree);
				init = init * (180 / Math.PI);
				break;	
			case GradiantoRadian:
				// Converting by multiplying factor required 
				init = init * (Math.PI / 200 );
				break;	
			case GradiantoMinute:
				// Converting by calling Gradian to Radian 
				init = init * (Math.PI / 200 );
				// Converting by calling Radian to degree
				init = init * (180 / Math.PI);
				// Then, convert to Minute
				// Converting by calling Degree to Minute
				init = init * 60;
				//convert(DegreetoMinute);
				break;	
			case GradiantoSecond:
				// Converting by calling Gradian to Degree 
				// Converting by calling Gradian to Radian 
				init = init * (Math.PI / 200 );
				// Converting by calling radian to degree
				init = init * (180 / Math.PI);
				// Converting by calling Degree to Second
				init = init * 3600;
				//convert(DegreetoSecond);
				break;	
			}
			// Converting DOUBLE to STRING for result
			result = Double.toString(init);
			// Displaying Unified RESULT
			displaydialog(RESULT);	
			// Setting error to No			
		}
		isParseFail = false;		
	}

	private void displaydialog(int dialog) {
		// TODO Auto-generated method stub
		// Accessing Preferences
		SharedPreferences toast_message = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		// Getting Preference value needed
		String toast = toast_message.getString("toast", "10");
		if(toast.contains("10"))
			// Simple Dialog
			switch(dialog){
			case NO_INPUT:
				// Showing no input dialog
				new AlertDialog.Builder(this)
				.setTitle(R.string.error_title)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(R.string.error_summary)
				.setNeutralButton(android.R.string.ok, null)
				.show();
				break;
			case RESULT:
				// For showing result
				new AlertDialog.Builder(this)
				.setTitle("Result : ")
				.setMessage(type + "\n\n" + result)
				.setNeutralButton(android.R.string.ok, null)
				.setPositiveButton(android.R.string.copy,new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialoginterface, int i){
					// For copying the result
			        ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
			        // Setting text to clip board
					clipBoard.setText(result);
					// Displaying message that text got copied
					Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
				}
				})
				.show();				
				break;
			case NO_BASE:
				// Showing no base selected message
				new AlertDialog.Builder(this)
				.setTitle("Base Error !")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(R.string.base_unit_list_error)
				.setNeutralButton(android.R.string.ok, null)
				.show();
				break;
		 }
		 else if(toast.contains("20"))
			// Default Android Toast
			 switch(dialog){
			 case NO_INPUT:
				// Showing no input toast message
				 Toast.makeText(getBaseContext(), R.string.error_summary, Toast.LENGTH_SHORT).show();
		         break;
			 case RESULT:
				// For showing result
				 Toast.makeText(getBaseContext(), type + result, Toast.LENGTH_SHORT).show();
		         break;
			 case NO_BASE:
				// Showing no base selected message
				 Toast.makeText(getBaseContext(), R.string.base_unit_list_error, Toast.LENGTH_LONG).show();
				 break;
			 }
		 else
			 // Crouton
			 switch(dialog) {
			 case NO_INPUT:
				// Showing no input crouton message
				 Crouton.makeText(this, R.string.error_summary, Style.INFO).show();
		         break;
			 case RESULT:
				// For showing result
				 Crouton.makeText(this, type + result, Style.CONFIRM).show();
		         break;
			 case NO_BASE:
				// Showing no base selected message
				 Crouton.makeText(this, R.string.base_unit_list_error, Style.INFO).show();
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_algorithm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
        	// Return to home i.e base layout
        case R.id.exit:
        	// Exit the app
            finish();
            // Exit Animation
            ActivityAnimation();   	
            return true;
        case R.id.menu_settings:
        	// Start Preferences
            Intent pref = new Intent(this,SettingsActivity.class);
            startActivity(pref);
            return true;
        case R.id.menu_help:
        	// Start Help
        	Intent menu_help = new Intent(this, Help.class);
        	startActivity(menu_help);
        	return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 @TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public void setupActionBar() {
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
				// Show the Up button in the action bar.
				getActionBar().setDisplayHomeAsUpEnabled(true);
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

}

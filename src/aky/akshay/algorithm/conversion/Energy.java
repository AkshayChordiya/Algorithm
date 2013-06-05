package aky.akshay.algorithm.conversion;

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
import de.keyboardsurfer.android.widget.Crouton;
import de.keyboardsurfer.android.widget.Style;

@SuppressWarnings("deprecation")
public class Energy extends Activity {
	
	// Declaring list views
	ListView base , convert;
	
	// Declaring Edit Text for input
	EditText input;
	
	// Declaring Strings to storing
	String type = "" , result = "";
	
	// Declaring double variables for manipulation
	double init , fina ; 
		
	// Boolean for parsing check
	boolean isParseFail = false;
	
	// Boolean for list items
	boolean joule = false, erg = false, calorie = false, evolt = false;
	
	// List Items
	String items[] = {"Joule" , "Erg" , "Calorie" , "Electron Volt"};
	
	// Integer Constants for LIST item
	public static final int Joule = 0;
	public static final int Erg = 1;
	public static final int Calorie = 2;
	public static final int EVolt = 3;
	
	public static final int SametoSame = 99;
	
	// Integer Constants for CONVERSION
	public static final int JouletoErg = 0;
	public static final int JouletoCalorie = 1;
	public static final int JouletoEVolt = 2;
	public static final int ErgtoJoule = 3;
	public static final int ErgtoCalorie = 4;
	public static final int ErgtoEVolt = 5;
	public static final int CalorietoJoule = 6;
	public static final int CalorietoErg = 7;
	public static final int CalorietoEVolt = 8;
	public static final int EVolttoJoule = 9;
	public static final int EVolttoErg = 10;
	public static final int EVolttoCalorie = 11;
	
	// Integer Constants for DIALOG
	public static final int NO_INPUT = 0;
	public static final int RESULT = 1;
	public static final int NO_BASE = 2;
	
	// Constants for conversion
	public static double calo = 4.1868000000000006945063840000001;
	public static double ev = 6.241509479607718E+18;
	public static double calev = 2.61319518892216E+19;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		prefControl(); // Design Management is defined in this method
		setContentView(R.layout.activity_energy);
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
				case Joule:
					// Setting corresponding boolean & message
					joule = true;
					Toast.makeText(getBaseContext(), "Joule Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					erg = false;
					calorie = false;
					evolt = false;
					break;
				case Erg:
					// Setting corresponding boolean & message
					erg = true;
					Toast.makeText(getBaseContext(), "Erg Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					joule = false;
					calorie = false;
					evolt = false;
					break;
				case Calorie:
					// Setting corresponding boolean & message
					calorie = true;
					Toast.makeText(getBaseContext(), "Calorie Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					joule = false;
					erg = false;
					evolt = false;
					break;
				case EVolt:
					// Setting corresponding boolean & message
					evolt = true;
					Toast.makeText(getBaseContext(), "Electron Volt Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					joule = false;
					erg = false;
					calorie = false;					
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
				case Joule:
					if(joule){
						// Setting conversion dynamically
						type = "Joule to Joule :";
						convert(SametoSame);			
					}
					else if(erg){
						// Setting conversion dynamically
						type = "Erg to Joule :";	
						convert(ErgtoJoule);
					}
					else if(calorie){
						// Setting conversion dynamically
						type = "Calorie to Joule :";
						convert(CalorietoJoule);
					}
					else if(evolt){
						// Setting conversion dynamically
						type = "Electron Volt to Joule:";
						convert(EVolttoJoule);						
					}
					else{
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					}
					break;
				case Erg:
					if(joule){
						// Setting conversion dynamically
						type = "Joule to Erg :";
						convert(JouletoErg);			
					}
					else if(erg){
						// Setting conversion dynamically
						type = "Erg to Erg :";	
						convert(SametoSame);
					}
					else if(calorie){
						// Setting conversion dynamically
						type = "Calorie to Erg :";
						convert(CalorietoErg);
					}
					else if(evolt){
						// Setting conversion dynamically
						type = "Electron Volt to Erg:";
						convert(EVolttoErg);						
					}
					else{
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					}
					break;
				case Calorie:
					if(joule){
						// Setting conversion dynamically
						type = "Joule to Calorie :";
						convert(JouletoCalorie);			
					}
					else if(erg){
						// Setting conversion dynamically
						type = "Erg to Calorie :";	
						convert(ErgtoCalorie);
					}
					else if(calorie){
						// Setting conversion dynamically
						type = "Calorie to Calorie :";
						convert(SametoSame);
					}
					else if(evolt){
						// Setting conversion dynamically
						type = "Electron Volt to Calorie:";
						convert(EVolttoCalorie);						
					}
					else{
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					}
					break;
				case EVolt:		
					if(joule){
						// Setting conversion dynamically
						type = "Joule to Electron Volt :";
						convert(JouletoEVolt);			
					}
					else if(erg){
						// Setting conversion dynamically
						type = "Erg to Electron Volt :";	
						convert(ErgtoEVolt);
					}
					else if(calorie){
						// Setting conversion dynamically
						type = "Calorie to Electron Volt :";
						convert(CalorietoEVolt);
					}
					else if(evolt){
						// Setting conversion dynamically
						type = "Electron Volt to Electron Volt:";
						convert(SametoSame);						
					}
					else{
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					}
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
			case JouletoErg:
				break;
			case JouletoCalorie:	
				// Converting by multiplying factor
				init = init / calo;
				break;
			case JouletoEVolt:
				// Converting by multiplying factor
				init = init * ev;
				break;
			case ErgtoJoule:
				break;
			case ErgtoCalorie:
				break;
			case ErgtoEVolt:
				break;
			case CalorietoJoule:
				// Converting by multiplying factor
				init = init * calo;
				break;
			case CalorietoErg:
				break;
			case CalorietoEVolt:
				// Converting by multiplying factor
				init = init * calev;
				break;
			case EVolttoJoule:
				// Converting by multiplying factor
				init = init / ev;
				break;
			case EVolttoErg:
				break;
			case EVolttoCalorie:
				// Converting by multiplying factor
				init = init / calev;
				break;
			}
			// Converting DOUBLE to STRING for result
			result = Double.toString(init);
			// Displaying Unified RESULT
			displaydialog(RESULT);			
		}
		// Setting error to No		
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

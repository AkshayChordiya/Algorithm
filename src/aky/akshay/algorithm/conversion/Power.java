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
public class Power extends Activity {
	
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
	boolean watt = false, hp = false;
	
	// List Items
	String items[] = {"Watt" , "Horse Power"};
	
	// Integer Constants for LIST item
	public static final int Watt = 0;
	public static final int HP = 1;
	
	public static final int SametoSame = 99;
	
	// Integer Constants for CONVERSION
	public static final int WatttoHP = 0;
	public static final int HPtoWatt = 1;
	
	// Integer Constants for DIALOG
	public static final int NO_INPUT = 0;
	public static final int RESULT = 1;
	public static final int NO_BASE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		prefControl(); // Design Management is defined in this method
		setContentView(R.layout.activity_power);
        ActivityAnimation();
        setupInput();
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
				case Watt:
					// Setting corresponding boolean & message
					watt = true;
					Toast.makeText(getBaseContext(), "Watt Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					hp = false;
					break;
				case HP:
					// Setting corresponding boolean & message
					hp = true;
					Toast.makeText(getBaseContext(), "Horse Power Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					watt = false;
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
				case Watt:
					if(watt){
						// Setting conversion dynamically
						type = "Watt to Watt :";
						convert(SametoSame);	
					}
					else if(hp){
						// Setting conversion dynamically
						type = "Horse Power to Watt :";
						convert(HPtoWatt);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
				case HP:
					if(watt){
						// Setting conversion dynamically
						type = "Watt to Horse Power :";
						convert(WatttoHP);					
					}
					else if(hp){
						// Setting conversion dynamically
						type = "Horse Power to Horse Power :";
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
			case WatttoHP:
				// Converting by multiplying factor
				init = init / 746;
				break;
			case HPtoWatt:
				// Converting by multiplying factor
				init = init * 746;
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

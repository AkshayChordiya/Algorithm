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
public class Area extends Activity {
	
	// Declaring list views
	ListView base , convert;
	
	// Declaring Edit Text for input
	EditText input;
	
	// Declaring Strings to storing
	String type = "" , result = "";
		
	// Declaring double variables for manipulation
	double init , fina ; 
	
	// Booleans for list items
	boolean acre = false , foot = false , mile = false , meter = false , hectare = false;
	
	// Boolean for parsing check
	boolean isParseFail = false;		
	
	// List Items
	String items[] = {"Acre" , "Foot" , "Mile" , "Meter" , "Hectare"};
	
	// Integer Constants for LIST item
	public static final int Acre = 0;
	public static final int Foot = 1;
	public static final int Mile = 2;
	public static final int Meter = 3;
	public static final int Hectare = 4;
	
	// Integer Constants for CONVERSION
	public static final int AcretoFoot = 0;
	public static final int AcretoMile = 1;
	public static final int AcretoMeter = 2;
	public static final int AcretoHectare = 3;
	public static final int FoottoAcre = 4;
	public static final int FoottoMile = 5;
	public static final int FoottoMeter = 6;
	public static final int FoottoHectare = 7;
	public static final int MiletoAcre = 8;
	public static final int MiletoFoot = 9;
	public static final int MiletoMeter = 10;
	public static final int MiletoHectare = 11;
	public static final int MetertoAcre = 12;
	public static final int MetertoFoot = 13;
	public static final int MetertoMile = 14;
	public static final int MetertoHectare = 15;
	public static final int HectaretoAcre = 16;
	public static final int HectaretoFoot = 17;
	public static final int HectaretoMile = 18;
	public static final int HectaretoMeter = 19;
	
	public static final int SametoSame = 99;
	
	// Integer Constants for DIALOG
	public static final int NO_INPUT = 0;
	public static final int RESULT = 1;
	public static final int NO_BASE = 2;
	
	public static double feet = 43560;
	public static double milefactor = 640;
	public static double meterfactor = 4046.8564224;
	public static double feetmilefactor = 3.587006427915519E-8;
	public static double feetmeterfactor = 0.09290304;
	public static double milehectarefactor = 258.9988110336;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		prefControl(); // Design Management is defined in this method
		setContentView(R.layout.activity_area);	
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
				case Acre:
					// Setting corresponding boolean & message
					acre = true;
					Toast.makeText(getBaseContext(), "Acre Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					foot = false;
					mile = false;
					meter = false;
					hectare = false;
					break;
				case Foot:
					// Setting corresponding boolean & message
					foot = true;
					Toast.makeText(getBaseContext(), "Foot Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					acre = false;
					mile = false;
					meter = false;
					hectare = false;
					break;
				case Mile:
					// Setting corresponding boolean & message
					mile = true;
					Toast.makeText(getBaseContext(), "Mile Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					acre = false;
					foot = false;
					meter = false;
					hectare = false;
					break;
				case Meter:
					// Setting corresponding boolean & message
					meter = true;
					Toast.makeText(getBaseContext(), "Meter Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					acre = false;
					foot = false;
					mile = false;
					hectare = false;
					break;	
				case Hectare:
					// Setting corresponding boolean & message
					hectare = true;
					Toast.makeText(getBaseContext(), "Hectare Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					acre = false;
					foot = false;
					mile = false;
					meter = false;
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
				// Code for each position
				switch(position){          
				case Acre:
					if(acre){
						// Setting conversion dynamically
						type = "Acre to Acre :";
						convert(SametoSame);						
					}
					else if(foot){
						// Setting conversion dynamically
						type = "Foot to Acre  :";
						convert(FoottoAcre);
					}
					else if(mile){
						// Setting conversion dynamically
						type = "Mile to Acre:";
						convert(MiletoAcre);
					}
					else if(meter){
						// Setting conversion dynamically
						type = "Meter to Acre :";
						convert(MetertoAcre);
					}
					else if(hectare){
						// Setting conversion dynamically
						type = "Hectare to Acre  :";
						convert(HectaretoAcre);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
				case Foot:
					if(acre){
						// Setting conversion dynamically
						type = "Acre to Foot :";
						convert(AcretoFoot);						
					}
					else if(foot){
						// Setting conversion dynamically
						type = "Foot to Foot :";
						convert(SametoSame);
					}
					else if(mile){
						// Setting conversion dynamically
						type = "Mile to Foot :";
						convert(MiletoFoot);
					}
					else if(meter){
						// Setting conversion dynamically
						type = "Meter to Foot :";
						convert(MetertoFoot);
					}
					else if(hectare){
						// Setting conversion dynamically
						type = "Hectare to Foot :";
						convert(HectaretoFoot);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
				case Mile:
					if(acre){
						// Setting conversion dynamically
						type = "Acre to Mile :";
						convert(AcretoMile);						
					}
					else if(foot){
						// Setting conversion dynamically
						type = "Foot to Mile :";
						convert(FoottoMile);
					}
					else if(mile){
						// Setting conversion dynamically
						type = "Mile to Mile :";
						convert(SametoSame);
					}
					else if(meter){
						// Setting conversion dynamically
						type = "Meter to Mile :";
						convert(MetertoMile);
					}
					else if(hectare){
						// Setting conversion dynamically
						type = "Hectare to Mile :";
						convert(HectaretoMile);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
				case Meter:
					if(acre){
						// Setting conversion dynamically
						type = "Acre to Meter :";
						convert(AcretoMeter);						
					}
					else if(foot){
						// Setting conversion dynamically
						type = "Foot to Meter :";
						convert(FoottoMeter);
					}
					else if(mile){
						// Setting conversion dynamically
						type = "Mile to Meter :";
						convert(MiletoMeter);
					}
					else if(meter){
						// Setting conversion dynamically
						type = "Meter to Meter :";
						convert(SametoSame);
					}
					else if(hectare){
						// Setting conversion dynamically
						type = "Hectare to Meter :";
						convert(HectaretoMeter);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;	
				case Hectare:
					if(acre){
						// Setting conversion dynamically
						type = "Acre to Hectare :";
						convert(AcretoHectare);						
					}
					else if(foot){
						// Setting conversion dynamically
						type = "Foot to Hectare :";
						convert(FoottoHectare);
					}
					else if(mile){
						// Setting conversion dynamically
						type = "Mile to Hectare :";
						convert(MiletoHectare);
					}
					else if(meter){
						// Setting conversion dynamically
						type = "Meter to Hectare :";
						convert(MetertoHectare);
					}
					else if(hectare){
						// Setting conversion dynamically
						type = "Hectare to Hectare :";
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
			case AcretoFoot:
				// Converting by multiplying factor
				init = init * feet;
				break;
			case AcretoMile:
				// Converting by multiplying factor
				init = init / milefactor;			
				break;
			case AcretoMeter:
				// Converting by multiplying factor
				init = init * meterfactor;					
				break;
			case AcretoHectare:
				// Converting by multiplying factor
				init = (init * meterfactor) / 10000;		
				break;
			case FoottoAcre:
				// Converting by multiplying factor
				init = init / feet;
				break;
			case FoottoMile:
				// Converting by multiplying factor
				init = init * feetmilefactor;				
				break;
			case FoottoMeter:
				// Converting by multiplying factor
				init = init * feetmeterfactor;						
				break;
			case FoottoHectare:
				// Converting by multiplying factor
				init = init * (feetmeterfactor / 10000);
				break;
			case MiletoAcre:
				// Converting by multiplying factor
				init = init * milefactor;	
				break;
			case MiletoFoot:
				// Converting by multiplying factor
				init = init / feetmilefactor;		
				break;
			case MiletoMeter:
				// Converting by multiplying factor
				init = init * (milehectarefactor * 10000);		
				break;
			case MiletoHectare:
				// Converting by multiplying factor
				init = init * milehectarefactor;	
				break;
			case MetertoAcre:
				// Converting by multiplying factor
				init = init / meterfactor;
				break;
			case MetertoFoot:
				// Converting by multiplying factor
				init = init / feetmeterfactor;
				break;
			case MetertoMile:
				// Converting by multiplying factor
				init = init / (milehectarefactor * 10000);		
				break;
			case MetertoHectare:
				// Converting by multiplying factor
				init = init / 10000;	
				break;
			case HectaretoAcre:
				// Converting by multiplying factor
				init = init / (meterfactor / 10000);		
				break;
			case HectaretoFoot:
				// Converting by multiplying factor
				init = init / (feetmeterfactor / 10000);
				break;
			case HectaretoMile:
				// Converting by multiplying factor
				init = init / milehectarefactor;	
				break;
			case HectaretoMeter:
				// Converting by multiplying factor
				init = init * 10000;	
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

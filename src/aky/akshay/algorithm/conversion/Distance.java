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
public class Distance extends Activity {
	
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
	
	// Booleans for list items
	boolean foot = false , inch = false , meter = false , mile = false , angstrom = false;
	
	// List Items
	String items[] = {"Foot" , "Inch" , "Meter" , "Mile" , "Angstrom"};
	
	// Integer Constants for LIST item
	public static final int Foot = 0;
	public static final int Inch = 1;
	public static final int Meter = 2;
	public static final int Mile = 3;
	public static final int Angstrom = 4;
	
	// Integer Constants for CONVERSION
	public static final int FoottoInch = 0;
	public static final int FoottoMeter = 1;
	public static final int FoottoMile = 2;
	public static final int FoottoAngstrom = 3;
	public static final int InchtoFoot = 4;
	public static final int InchtoMeter = 5;
	public static final int InchtoMile = 6;
	public static final int InchtoAngstrom = 7;
	public static final int MetertoFoot = 8;
	public static final int MetertoInch = 9;
	public static final int MetertoMile = 10;
	public static final int MetertoAngstrom = 11;
	public static final int MiletoFoot = 12;
	public static final int MiletoInch = 13;
	public static final int MiletoMeter = 14;
	public static final int MiletoAngstrom = 15;	
	public static final int AngstromtoFoot = 16;
	public static final int AngstromtoInch = 17;
	public static final int AngstromtoMile = 18;
	public static final int AngstromtoMeter = 19;
	
	public static final int SametoSame = 99;
	
	// Integer Constants for DIALOG
	public static final int NO_INPUT = 0;
	public static final int RESULT = 1;
	public static final int NO_BASE = 2;
	
	// Constants for conversion algorithm
	public static double feetmeterfactor = 0.3048;
	public static double feetmilefactor = 1.893939393939394E-4;
	public static double feetangstrongfactor = 3048E+6;
	public static double inchangstrongfactor = 254E+6;
	public static double inchmilefactor = 1.578282828282828E-5;
	public static double metermilefactor = 6.21371192237334E-4;
	public static double mileangstromfactor = 1609344E+7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		prefControl(); // Design Management is defined in this method
		setContentView(R.layout.activity_distance);
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
				case Foot:
					// Setting corresponding boolean & message
					foot = true;
					Toast.makeText(getBaseContext(), "Foot Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					inch = false;
					mile = false;
					meter = false;
					angstrom = false;
					break;
				case Inch:
					// Setting corresponding boolean & message
					inch = true;
					Toast.makeText(getBaseContext(), "Inch Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					foot = false;
					mile = false;
					meter = false;
					angstrom = false;
					break;
				case Meter:
					// Setting corresponding boolean & message
					meter = true;
					Toast.makeText(getBaseContext(), "Meter Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					foot = false;
					mile = false;
					inch = false;
					angstrom = false;
					break;
				case Mile:
					// Setting corresponding boolean & message
					mile = true;
					Toast.makeText(getBaseContext(), "Mile Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					foot = false;
					inch = false;
					meter = false;
					angstrom = false;
					break;	
				case Angstrom:
					// Setting corresponding boolean & message
					angstrom = true;
					Toast.makeText(getBaseContext(), "Angstrom Selected", Toast.LENGTH_SHORT).show();
					// Setting others false
					foot = false;
					inch = false;
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
				
				// Generating code for each position
				switch(position){      
				case Foot:
					if(foot){
						// Setting conversion dynamically
						type = "Foot to Foot :";
						convert(SametoSame);								
					}
					else if(inch){
						// Setting conversion dynamically
						type = "Inch to Foot :";
						convert(InchtoFoot);								
					}
					else if(meter){
						// Setting conversion dynamically
						type = "Meter to Foot :";
						convert(MetertoFoot);							
					}
					else if(mile){
						// Setting conversion dynamically
						type = "Mile to Foot :";
						convert(MiletoFoot);								
					}
					else if(angstrom){
						// Setting conversion dynamically
						type = "Angstrom to Foot :";
						convert(AngstromtoFoot);							
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
				case Inch:
					if(foot){
						// Setting conversion dynamically
						type = "Foot to Inch :";
						convert(FoottoInch);						
					}
					else if(inch){
						// Setting conversion dynamically
						type = "Inch to Inch :";
						convert(SametoSame);
					}
					else if(meter){
						// Setting conversion dynamically
						type = "Meter to Inch :";
						convert(MetertoInch);
					}
					else if(mile){
						// Setting conversion dynamically
						type = "Mile to Inch :";
						convert(MiletoInch);
					}
					else if(angstrom){
						// Setting conversion dynamically
						type = "Angstrom to Inch :";
						convert(AngstromtoInch);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
				case Meter:
					if(foot){
						// Setting conversion dynamically
						type = "Foot to Meter :";
						convert(FoottoMeter);						
					}
					else if(inch){
						// Setting conversion dynamically
						type = "Inch to Meter :";
						convert(InchtoMeter);
					}
					else if(meter){
						// Setting conversion dynamically
						type = "Meter to Meter :";
						convert(SametoSame);
					}
					else if(mile){
						// Setting conversion dynamically
						type = "Mile to Meter :";
						convert(MiletoMeter);
					}
					else if(angstrom){
						// Setting conversion dynamically
						type = "Angstrom to Meter :";
						convert(AngstromtoMeter);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;
				case Mile:
					if(foot){
						// Setting conversion dynamically
						type = "Foot to Mile :";
						convert(FoottoMile);						
					}
					else if(inch){
						// Setting conversion dynamically
						type = "Inch to Mile :";
						convert(InchtoMile);
					}
					else if(meter){
						// Setting conversion dynamically
						type = "Meter to Mile :";
						convert(MetertoMile);
					}
					else if(mile){
						// Setting conversion dynamically
						type = "Mile to Mile :";
						convert(SametoSame);
					}
					else if(angstrom){
						// Setting conversion dynamically
						type = "Angstrom to Mile :";
						convert(AngstromtoMile);
					}
					else
						// If everything fails show no base selected dialog
						displaydialog(NO_BASE);
					break;	
				case Angstrom:
					if(foot){
						// Setting conversion dynamically
						type = "Foot to Angstrom :";
						convert(FoottoAngstrom);						
					}
					else if(inch){
						// Setting conversion dynamically
						type = "Inch to Angstrom :";
						convert(InchtoAngstrom);
					}
					else if(meter){
						// Setting conversion dynamically
						type = "Meter to Angstrom :";
						convert(MetertoAngstrom);
					}
					else if(mile){
						// Setting conversion dynamically
						type = "Mile to Angstrom :";
						convert(MiletoAngstrom);
					}
					else if(angstrom){
						// Setting conversion dynamically
						type = "Angstrom to Angstrom :";
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
			case FoottoInch:
				// Converting by multiplying conversion factor
				init = init * 12;
				break;
			case FoottoMeter:
				// Converting by multiplying conversion factor
				init = init * feetmeterfactor;			
				break;
			case FoottoMile:
				// Converting by multiplying conversion factor
				init = init * feetmilefactor;						
				break;
			case FoottoAngstrom:
				// Converting by multiplying conversion factor
				init = init * feetangstrongfactor;				
				break;
			case InchtoFoot:
				// Converting by multiplying conversion factor
				init = init / 12;
				break;
			case InchtoMeter:
				// Converting by multiplying conversion factor
				init = init * (inchangstrongfactor / 1E+10);		
				break;
			case InchtoMile:
				// Converting by multiplying conversion factor
				init = init * inchmilefactor;	
				break;
			case InchtoAngstrom:
				// Converting by multiplying conversion factor
				init = init * inchangstrongfactor;					
				break;
			case MetertoFoot:
				// Converting by multiplying conversion factor
				init = init / feetmeterfactor;	
				break;
			case MetertoInch:
				// Converting by multiplying conversion factor
				init = init / (inchangstrongfactor / 1E+10);	
				break;
			case MetertoMile:
				// Converting by multiplying conversion factor
				init = init * metermilefactor;						
				break;
			case MetertoAngstrom:
				// Converting by multiplying conversion factor
				init = init * 1E+10;	
				break;
			case MiletoFoot:
				// Converting by multiplying conversion factor
				init = init / feetmilefactor;	
				break;
			case MiletoInch:	
				// Converting by multiplying conversion factor
				init = init / inchmilefactor;	
				break;
			case MiletoMeter:
				// Converting by multiplying conversion factor
				init = init / metermilefactor;	
				break;
			case MiletoAngstrom:
				// Converting by multiplying conversion factor
				init = init * mileangstromfactor;				
				break;
			case AngstromtoFoot:
				// Converting by multiplying conversion factor
				init = init / feetangstrongfactor;		
				break;
			case AngstromtoInch:
				// Converting by multiplying conversion factor
				init = init / inchangstrongfactor;	
				break;
			case AngstromtoMile:
				// Converting by multiplying conversion factor
				init = init / mileangstromfactor;		
				break;
			case AngstromtoMeter:
				// Converting by multiplying conversion factor
				init = init / 1E+10;	
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

package aky.akshay.algorithm.conversion;

import aky.akshay.algorithm.deve.Help;
import aky.akshay.algorithm.deve.R;
import aky.akshay.algorithm.deve.SettingsActivity;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
public class Temperature extends Activity{
	
	final Context context = this;
	//Converting Ex to 10 ^ x
	String raise;
	// Declaring Edit Text
	EditText convt;
	// Double Vairables
	double x , y ;
	// Skipped List
	public int skip_list=0;
	// Declaring List Views
	ListView entval,conval;
	// List Items
	String envalist[] = {"Celsius" , "Fahrenheit" , "Kelvin" , "Rankine"};
	// Boolean for list view selected
	Boolean cel , far , kel , rankine;
	// Constants for conversion
	static double  a= 273.15,b= 459.67,c=0.5555555555555556,d=1.8;
	// For parsing status
	boolean parseFail = false;
	
	// Integer Constants for LIST item
	public static final int Celsius = 0;
	public static final int Fahrenheit = 1;
	public static final int Kelvin = 2;
	public static final int Rankine = 3;
	
	// Setting TYPE in STRING
	public String type = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        prefControl(); // Design Management is defined in this method
        setContentView(R.layout.activity_temperature);
        ActivityAnimation();
        convt=(EditText)findViewById(R.id.editText1);
        // Setting up List Views
        SetupListView();
        setupActionBar();
    }

	private void SetupListView() {
		// TODO Auto-generated method stub
    	entval = (ListView)findViewById(R.id.base_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
        		envalist);
        entval.setAdapter(adapter);        
        entval.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				switch(position){
            	case Celsius:           		
            		cel = true;            		
            		far = false;
            		kel = false;
            		rankine = false;
            		//skip_list=1;
            		Toast.makeText(getBaseContext(), "Celsius Selected", Toast.LENGTH_SHORT).show();
            		break;
            	case Fahrenheit:
            		far = true;
            		cel = false;
            		kel = false; 
            		rankine = false;
            		//skip_list=1;
            		Toast.makeText(getBaseContext(), "Fahrenheit Selected", Toast.LENGTH_SHORT).show();
            		break;
            	case Kelvin:
            		kel = true;
            		far = false;
            		cel = false;
            		rankine = false;
            		//skip_list=1;
            		Toast.makeText(getBaseContext(), "Kelvin Selected", Toast.LENGTH_SHORT).show();
            		break;
            	case Rankine:
            		rankine = true;
            		kel = false;
            		far = false;
            		cel = false;
            		//skip_list=1;
            		Toast.makeText(getBaseContext(), "Rankine Selected", Toast.LENGTH_SHORT).show();
            		break;
            	} 
			}
        	
        });
        conval = (ListView)findViewById(R.id.conv_list);
        conval.setAdapter(adapter);
        conval.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				switch(position){
            	case Celsius:
            		if(cel)
            			converter(0);            			 
            		if(far)
            			converter(1);
            		if(kel == true)
            			converter(2);           			
            		break;
            	case Fahrenheit:
            		if(cel)
            			converter(3);
            		if(far)
            			converter(4);
            		if(kel)
            			converter(5);
            	break;
            	case Kelvin:
            		if(cel)
            			converter(6);
            		if(far)
            			converter(7);
            		if(kel)
            			converter(8);
            	case Rankine:
            		if(cel)
            			converter(9);
            		if(far)
            			converter(10);
            		if(kel)
            			converter(11);
            	break;
            	}
			}
        	
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
    
    protected void converter(int i) {
		// TODO Auto-generated method stub
    	try{
    		// Trying to get input from Edit Text box
    		x = Double.parseDouble(convt.getText().toString());     		
    	}catch(Exception parse){
    		// Catching error if unsuccessful
    		parseFail = true;
    		// Show Dialog of error
			displaydialog(0);
    	}
    	if(!parseFail){
    	switch(i){
    	case 0:
    		//Celsius to Celsius
    		type = "Celsius to Celsius :";
    		displaydialog(1);
    		break;
    	case 1:
    		//Fahrenheit to Celsius
    		type = "Fahrenheit to Celsius :";
    		y = (x-32) * c;
    		displaydialog(2);
    		break;
    	case 2:
    		//Kelvin to Celsius
    		type =  "Kelvin to Celsius :";
    		y = x - a;
    		displaydialog(2);
    		break;
    	case 3:
    		//Celsius to Fahrenheit
    		type =  "Celsius to Fahrenheit :";
    		y = (x*d) + 32 ; 		
    		displaydialog(2);
    		break;
    	case 4:
    		//Fahrenheit to Fahrenheit
    		type =  "Fahrenheit to Fahrenheit :";
    		displaydialog(1);
    		break;
    	case 5:
    		//Kelvin to Fahrenheit
    		type =  "Kelvin to Fahrenheit :";
    		y = (x*d) - b;  	
    		displaydialog(2);
    		break;
    	case 6:
    		//Celsius to Kelvin
    		type = "Celsius to Kelvin :";
    		y = x + a;    	
    		displaydialog(2);
    		break;
    	case 7:
    		//Fahrenheit to Kelvin
    		type =  "Fahrenheit to Kelvin :";
    		y = (x+b)*c;  
    		displaydialog(2);
    		break;
    	case 8:
    		//Kelvin to Kelvin
    		type =  "Kelvin to Kelvin :";
    		displaydialog(1);
    		break;
		}   		
        }
    	parseFail = false;
	}

	protected void displaydialog(int i) {
		// TODO Auto-generated method stub
		SharedPreferences toast_message = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String toast = toast_message.getString("toast", "10");
		if(toast.contains("10"))
			switch(i){
			case 0:
				new AlertDialog.Builder(this)
				.setTitle(R.string.error_title)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(R.string.error_summary)
				.setNeutralButton(android.R.string.ok, null)
				.show();
				break;
			case 1:
				raise = Double.toString(x);
				SharedPreferences raiseIt = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			    boolean raiser = raiseIt.getBoolean("raise", true);
			    if(raiser == true)
			    	raise = raise.replaceFirst("E", " x 10^");
				new AlertDialog.Builder(this)
				.setTitle("Result : ")
				.setMessage(type + " \n\n" + raise)
				.setNeutralButton(android.R.string.ok, null)
				.setPositiveButton("Copy",new DialogInterface.OnClickListener()
				{
				@Override
				public void onClick(DialogInterface dialoginterface, int i)
				{
			        ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					clipBoard.setText(raise);
					Toast.makeText(context, R.string.clipboard, Toast.LENGTH_SHORT).show();
				}
				})
				.show();
			 break;
		 case 2:
				raise = Double.toString(y);
				SharedPreferences raiseY = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			    boolean raiserY = raiseY.getBoolean("raise", true);
			    if(raiserY == true)
			    	raise = raise.replaceFirst("E", " x 10^");
			    new AlertDialog.Builder(this)
			    .setTitle("Result : ")
				.setMessage(type + " \n\n" + raise)
				.setNeutralButton(android.R.string.ok, null)
				.setPositiveButton("Copy",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialoginterface, int i){
			        ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					clipBoard.setText(raise);
					Toast.makeText(context, R.string.clipboard, Toast.LENGTH_SHORT).show();
				}
				})
				.show();
			 break;
		 case 3:
			 new AlertDialog.Builder(this)
				.setTitle("Base Error !")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(R.string.base_unit_list_error)
				.setNeutralButton(android.R.string.ok, null)
				.show();
	         break;
		 }
		 else if(toast.contains("20"))
			 switch(i){
			 case 0:
				 Toast.makeText(getBaseContext(), R.string.error_summary, Toast.LENGTH_SHORT).show();
		         break;
			 case 1:
				 Toast.makeText(getBaseContext(), type + Double.toString(x), Toast.LENGTH_SHORT).show();
		         break;
			 case 2:
				 Toast.makeText(getBaseContext(), type + Double.toString(y), Toast.LENGTH_SHORT).show();
		         break;
			 case 3:
				 Toast.makeText(getBaseContext(), R.string.base_unit_list_error, Toast.LENGTH_LONG).show();
				 break;
			 }
		 else
			 switch(i) {
			 case 0:
				 Crouton.makeText(this, R.string.error_summary , Style.INFO).show();
		         break;
			 case 1:
				 Crouton.makeText(this, type + Double.toString(x), Style.CONFIRM).show();
		         break;
			 case 2:
				 Crouton.makeText(this, type + Double.toString(y), Style.CONFIRM).show();
		         break;
			 case 3:
				 Crouton.makeText(this,R.string.base_unit_list_error, Style.INFO).show();
				 break;
			 }
		 }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

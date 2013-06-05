package aky.akshay.algorithm.deve;

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
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import de.keyboardsurfer.android.widget.Crouton;
import de.keyboardsurfer.android.widget.Style;

@SuppressWarnings("deprecation")
public class Pythagoras extends Activity{
	
	public static final int QUERY = 0;
	
	public static final int copy = 0;
	public static final int swap = 1;
	public static final int clear = 2;
	
	EditText sideA,sideB;
	int count = 0;
	double x,y,z,a;
	
	boolean parseFail = false;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefControl(); // Design Management is defined in this method
        setContentView(R.layout.activity_pythagoras);        
        ActivityAnimation();   	
        initControls();
        setupActionBar();
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
    
    private void initControls() {
    	sideA = (EditText)findViewById(R.id.editText1);
    	sideB = (EditText)findViewById(R.id.editText2);
    }
    
    private void Ans(int i) {
		// TODO Auto-generated method stub
    	try{
    		//Initialising Variables
    		x = Double.parseDouble(sideA.getText().toString());
    		y = Double.parseDouble(sideB.getText().toString());
    	}catch(Exception parse){
    		parseFail = true;
			displaydialog(0);
    	}
    	if (!parseFail){
    		switch(i){  	
    		case 0:
    			//Hypotenus Calculations
    			//Finding result
    			z = x*x + y*y;
    			a = Math.sqrt(z);
    			//Displaying result
    			displaydialog(1);
    			break;  			
    		case 1:
    			//Side A calculations
    			//Finding result
    			z = x*x - y*y;
    			a = Math.sqrt(z);
    			//Displaying result
    			displaydialog(2);
    			break;  			
    		case 2:
    			//Side B calculations  
    			//Finding result
    			z = y*y - x*x;
    			a = Math.sqrt(z);
    			//Displaying result
    			displaydialog(3);
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
			 new AlertDialog.Builder(this)
				.setTitle("Result : ")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("Hypotenuse = " + Double.toString(a))
				.setPositiveButton("Copy",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialoginterface, int i){
			        ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					clipBoard.setText(Double.toString(a));
					Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
				}
				})
				.setNeutralButton(android.R.string.ok, null)
				.show();
			 break;
		 case 2:
			 new AlertDialog.Builder(this)
				.setTitle("Result : ")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("Side A = " + Double.toString(a))
				.setNeutralButton(android.R.string.ok, null)
				.setPositiveButton("Copy",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialoginterface, int i){
			        ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					clipBoard.setText(Double.toString(a));
					Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
				}
				})
				.show();
			 break;
		 case 3:
			 new AlertDialog.Builder(this)
				.setTitle("Result : ")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("Side B = " + Double.toString(a))
				.setPositiveButton("Copy",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialoginterface, int i){
			        ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					clipBoard.setText(Double.toString(a));
					Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
				}
				})
				.setNeutralButton(android.R.string.ok, null)
				.show();
			 break;
		 }
		 else if(toast.contains("20"))
			 switch(i){
			 case 0:
				 Toast.makeText(this, R.string.error_summary, Toast.LENGTH_SHORT).show();
		         break;
			 case 1:
				 Toast.makeText(this,getString(R.string.hypo) + " = " + Double.toString(a), Toast.LENGTH_LONG).show();
				 break;
			 case 2:
				 Toast.makeText(this,"Side A = " + Double.toString(a), Toast.LENGTH_LONG).show();
				 break;
			 case 3:
				 Toast.makeText(this,"Side B = " + Double.toString(a), Toast.LENGTH_LONG).show();
				 break;
				 }
		 else
			 switch(i){
			 case 0:
				 Crouton.makeText(this, R.string.error_summary, Style.INFO).show();
		         break;
			 case 1:
				 Crouton.makeText(this,getString(R.string.hypo) + " = " + Double.toString(a), Style.CONFIRM).show();
				 break;
			 case 2:
				 Crouton.makeText(this,"Side A = " + Double.toString(a), Style.CONFIRM).show();
				 break;
			 case 3:
				 Crouton.makeText(this,"Side B = " + Double.toString(a), Style.CONFIRM).show();
				 break;
			 }		 
		 }
	
	public void query(int q){
		switch(q){
		
		case QUERY:
			AlertDialog.Builder alert = new AlertDialog.Builder(Pythagoras.this);
			 alert.setTitle("What you wanna calculate");
			 alert.setItems(R.array.pytha_query, new DialogInterface.OnClickListener()
			    {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						// TODO Auto-generated method stub		
						switch(item)
						{
						case 0:
							sideA.setHint(R.string.hypo);
							sideB.setHint(R.string.length_sideB);
							count = 1;
							//Ans(1);							
							break;
						case 1:
							sideB.setHint(R.string.hypo);
							sideA.setHint(R.string.length_sideA);
							count = 2;
							//Ans(2);
							break;						
						case 2:							
							//Ans(0);
							sideA.setHint(R.string.length_sideA);
							sideB.setHint(R.string.length_sideB);
							count = 3;
							break;												
						}
						dialog.dismiss();
					}
				 
			    });
			 alert.setNeutralButton(android.R.string.cancel, null);
			 AlertDialog alt = alert.create(); 			 
			 alt.show();
			break;
			}
		}
	
	public void Access(int access){
		switch(access){
		case 0:
			AlertDialog.Builder accessibility = new AlertDialog.Builder(Pythagoras.this);
			accessibility.setItems(R.array.accessibility_dialog, new DialogInterface.OnClickListener() {
	    	               public void onClick(DialogInterface dialog, int which) {
	    	            	   switch(which)
	    	            	   {
	    	            	   case copy:
	    	            		   PerformAccess(copy);
	    	            		   break;
	    	            	   case swap:
	    	            		   PerformAccess(swap);
	    	            		   break;
	    	            	   case clear:
	    	            		   PerformAccess(clear);
	    	            		   break;
	    	            	   }
	    	               // The 'which' argument contains the index position
	    	               // of the selected item
	    	           }
	    	    });
	    	   AlertDialog accessibility_show = accessibility.create();
	    	   accessibility_show.setCanceledOnTouchOutside(true);
	    	   accessibility_show.show();
			break;
		}
	}
	
	public void PerformAccess(int k) {
		// TODO Auto-generated method stub
		switch(k){
		case copy:
			String check = sideA.getText().toString();
			String check1 = sideB.getText().toString();
			ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
			clipBoard.setText(check + check1);
			Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
			break;
		case swap:
			// Clearing inputs
			check = sideA.getText().toString();
			check1 = sideB.getText().toString();
			sideA.setText(check1);
			sideB.setText(check);
			Toast.makeText(getBaseContext(), R.string.swaping, Toast.LENGTH_SHORT).show();
			break;
		case clear:
			// Clearing inputs
			sideA.setText(null);
			sideB.setText(null);
			count = 0;
			Toast.makeText(getBaseContext(), R.string.toast_input_cleared, Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	 @Override
	    public boolean onTouchEvent(MotionEvent event) {
	     // TODO Auto-generated method stub
	       return gestureDetector.onTouchEvent(event);
	    }

	 SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener(){
		 @Override
		 public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
			// As paiego pointed out, it's better to use density-aware measurements.
		     DisplayMetrics dm = getResources().getDisplayMetrics();
		     float SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * dm.densityDpi / 160.0f + 0.5);
		     float SWIPE_DIVEDER = (float) 1.5;
		     SWIPE_THRESHOLD_VELOCITY = SWIPE_THRESHOLD_VELOCITY / SWIPE_DIVEDER;
		     
			 if((e2.getY() - e1.getY()) > SWIPE_THRESHOLD_VELOCITY){ 
				 //Swipe Down
				 // Setting everything to default
				 sideA.setHint(R.string.ph_swipe);
				 sideB.setHint(R.string.ph_swipe);
				 query(QUERY);
				 Toast.makeText(getBaseContext(), R.string.toast_query_cleared, Toast.LENGTH_SHORT).show();
				 }	
			 else if((e1.getY() - e2.getY()) > SWIPE_THRESHOLD_VELOCITY){ //Swipe Up
				 switch(count){
				 case QUERY:
					 query(QUERY);
					 break;
				case 1:
					Ans(1);
					break;					
				case 2:
					Ans(2);
					break;
	    	    case 3:
	    	    	Ans(0);
	    	    	break;
	    	  }	    	  
	  			}
			 else if(e1.getX() - e2.getX() > SWIPE_THRESHOLD_VELOCITY &&
		              Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
		    	  // Swipe Right
		    	  Access(0);	
		      else if(e2.getX() - e1.getX() > SWIPE_THRESHOLD_VELOCITY &&
		              Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
		    	  // Swipe Left
		    	  Access(0);	 			 
	      return super.onFling(e1, e2, velocityX, velocityY);
	     }
	      };
	      GestureDetector gestureDetector= new GestureDetector(simpleOnGestureListener);
      
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

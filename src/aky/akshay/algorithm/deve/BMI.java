package aky.akshay.algorithm.deve;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
public class BMI extends Activity {
	
	EditText height_input , weight_input;
	
	boolean parseFail = false;
	
	String result , height , weight , type = "" , healthrisk = "";
	Double Dheight , Dweight;
	Double ratio;
	
	public static final int QUERY = 0;
	
	public static final int copy = 0;
	public static final int clear = 1;
	
	public static final int basicSI = 0;
	public static final int lbbyinch = 1;
	public static final double convFactorInch = 703.06957964;
	
	public int count = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefControl(); // Design Management is defined in this method
		setContentView(R.layout.activity_bmi);		
        ActivityAnimation();
        setupInput();
        setupActionBar();
	}
	
	private void setupInput() {
		// TODO Auto-generated method stub
		height_input = (EditText)findViewById(R.id.height);    	
		weight_input = (EditText)findViewById(R.id.weight);    			
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
				.setMessage("BMI : " + result + " kg / m²" +"\n\nCategory:\n\n" + type + "\n\nHealth Risk:" +
						"\n\n" + healthrisk)
				.setNeutralButton(android.R.string.ok, null)
				.setPositiveButton("Copy",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialoginterface, int i){
					ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					clipBoard.setText(result);
					Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
				}
				})
				.setNegativeButton("Help",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialoginterface, int i){
					Help();
				}
				private void Help() {
					// TODO Auto-generated method stub
					 new AlertDialog.Builder(BMI.this)
					 .setTitle(R.string.title_activity_help)
					 .setIcon(R.drawable.author)
					 .setMessage(R.string.bmi_help)
					 .setNeutralButton(android.R.string.ok, null)
					 .setPositiveButton("Wiki", new OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent vist_wiki = new Intent(Intent.ACTION_VIEW,Uri.parse("http://en.wikipedia.org/wiki/Body_mass_index"));
    						startActivity(vist_wiki);
						}
						 
					 })
					 .show();
				}
				})
				.show();
			 break;
		 }
		 else if(toast.contains("20"))
			 switch(i){
			 case 0:
				 Toast.makeText(getBaseContext(), R.string.error_summary, Toast.LENGTH_SHORT).show();
		         break;
			 case 1:
				 Toast.makeText(getBaseContext(),"BMI : " + result + " kg / m²" +"\n\nCategory:\n\n" + type + "\n\nHealth Risk:" +
							"\n\n" + healthrisk, Toast.LENGTH_SHORT).show();
		         break;
			 }
		 else
			 switch(i){
			 case 0:
				 Crouton.makeText(this, R.string.error_summary , Style.INFO).show();
		         break;
			 case 1:
				 Crouton.makeText(this,"BMI : " + result + " kg / m²" +"\n\nCategory:\n\n" + type + "\n\nHealth Risk:" +
							"\n\n" + healthrisk, Style.CONFIRM).show();
		         break;
			 }
		 }
	
	

	private void calculate(int i) {
		// TODO Auto-generated method stub
		try{
			height = height_input.getText().toString();
			weight = weight_input.getText().toString();
			Dheight = Double.parseDouble(height);
			Dweight = Double.parseDouble(weight);
		}catch(Exception parse){
			displaydialog(0);
			parseFail = true;
		}
		if(!parseFail){						
			switch(i){
			case basicSI:
				ratio = Dweight / (Dheight * Dheight);			
				break;
			case lbbyinch:
				ratio = ratio * convFactorInch;
				break;
				}
			// Finding type of BMI
			if(ratio < 15.0)
				type = "Very severely underweight";
			else if(ratio >= 15.0 && ratio < 16.0)
				type = "Severely underweight";
			else if(ratio >= 16.0 && ratio < 18.5)
				type = "Underweight";
			else if(ratio >= 18.5 && ratio < 25)
				type = "Normal (Healthy Weight)";
			else if(ratio >= 25 && ratio < 30)
				type = "Overweight";
			else if(ratio >= 30.0 && ratio < 35)
				type = "Obese Class 1 (Moderately Weight)";
			else if(ratio >= 35 && ratio < 40)
				type = "Obese Class 1 (Severely Weight)";
			else if(ratio >= 40)
				type = "Obese Class 3 (Very Severely Weight)";
			else
				type = "Something is wrong";
			// Finding Health Risk of BMI determined
			if(ratio >= 27.5)
				healthrisk = "High risk of developing heart disease, high blood pressure, stroke, diabetes";
			else if(ratio >= 23.0 && ratio <= 27.4)
				healthrisk = "Moderate risk of developing heart disease, high blood pressure, stroke, diabetes";
			else if(ratio >= 18.5 && ratio <= 22.9)
				healthrisk = "Low Risk (healthy range)"; 
			else if(ratio <= 18.4)
				healthrisk = "Risk of developing problems such as nutritional deficiency and osteoporosis";
			result = ratio.toString();
			displaydialog(1);
			type = "";
			}	
		parseFail = false;
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
      
      if((e2.getY() - e1.getY()) > SWIPE_THRESHOLD_VELOCITY) // Swipe Down
      {
    	  query(QUERY);
    	  Toast.makeText(getBaseContext(), R.string.toast_query_cleared, Toast.LENGTH_SHORT).show();
      }
      else if((e1.getY() - e2.getY()) > SWIPE_THRESHOLD_VELOCITY){ //Swipe Up
    	  switch(count){
			 case QUERY:
				 query(QUERY);
				 break;
			 case 1:
				 calculate(basicSI);
				 break;		
			 case 2:
				 calculate(lbbyinch);
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
      @SuppressWarnings({ })
      GestureDetector gestureDetector= new GestureDetector(simpleOnGestureListener);
      
      public void query(int q){
  		switch(q){
  		
  		case QUERY:
  			AlertDialog.Builder alert = new AlertDialog.Builder(this);
  			 alert.setTitle("Select your Unit");
  			 alert.setItems(R.array.dimen_bmi, new DialogInterface.OnClickListener()
  			    {

  					@Override
  					public void onClick(DialogInterface dialog, int item) {
  						// TODO Auto-generated method stub		
  						switch(item)
  						{
  						case basicSI:	
  							weight_input.setHint(getString(R.string.weight_enter) + " in Kg");
  							height_input.setHint(getString(R.string.weight_enter) + " in m");
  							count = 1;
  							break;
  						case lbbyinch:
  							weight_input.setHint(getString(R.string.weight_enter) + " in lb");
  							height_input.setHint(getString(R.string.weight_enter) + " in inch");
  							count = 2;
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
			AlertDialog.Builder accessibility = new AlertDialog.Builder(this);
			accessibility.setItems(R.array.accessibility_default, new DialogInterface.OnClickListener() {
	    	               public void onClick(DialogInterface dialog, int which) {
	    	            	   switch(which)
	    	            	   {
	    	            	   case copy:
	    	            		   PerformAccess(copy);
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
			String check_weight = weight_input.getText().toString();
			String check_height = height_input.getText().toString();
			ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
			clipBoard.setText(check_weight + check_height);
			Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
			break;
		case clear:
			// Clearing inputs
			weight_input.setText(null);
			height_input.setText(null);
			weight_input.setHint(R.string.weight_enter);
			height_input.setHint(R.string.height_enter);
			Toast.makeText(getBaseContext(), R.string.toast_input_cleared, Toast.LENGTH_SHORT).show();
			break;
		}
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
    	if(fade)
    		overridePendingTransition(R.anim.slider_in, R.anim.slider_out);
	}

}

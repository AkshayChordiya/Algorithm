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

@SuppressWarnings("deprecation")
public class Triangle extends Activity{
	
	int count = 0;
	private String degrad = "Degree"; 
	EditText valA,valB,valC;
	double sideSA,sideSB,sideSC;
	double squareA,squareB,squareC;
	double cosine_a,cosine_b,cosine_c;
	double median_a,median_b,median_c;
	double angle_bisectorA,angle_bisectorB,angle_bisectorC;
	double area,perimeter,cirum_circle,inscribe_circle;
	String SwapA,SwapB,SwapC;
	
	public static final int copy = 0;
	public static final int swap = 1;
	public static final int clear = 2;
	
	boolean parseFail = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefControl(); // Design Management is defined in this method
		setContentView(R.layout.activity_triangle);
		ActivityAnimation();   	    
        getIdfromLayout();
        setupActionBar();
	}

	private void getIdfromLayout() {
		// TODO Auto-generated method stub
		valA = (EditText)findViewById(R.id.EditText1);
		valB = (EditText)findViewById(R.id.EditText2);
		valC = (EditText)findViewById(R.id.EditText3);    	
		/*
		valA.setOnLongClickListener(new OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				swap(swap_dialog);
				return false;
			}
			});    	
    	valB.setOnLongClickListener(new OnLongClickListener()
		{
    		@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
    			swap(swap_dialog);
				return false;
			}
    		});    	
    	valC.setOnLongClickListener(new OnLongClickListener()
		{
    		@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
    			swap(swap_dialog);
				return false;
			}
    		});
    		*/
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
			 AlertDialog.Builder alert = new AlertDialog.Builder(Triangle.this);
			 alert.setTitle("Select combination which you have");
			 alert.setItems(R.array.side_angle_query, new DialogInterface.OnClickListener()
			    {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						// TODO Auto-generated method stub					
						switch(item)
						{
						case 0:// Side , Side , Side
							valA.setHint(R.string.length_side);
							valB.setHint(R.string.length_side);
							valC.setHint(R.string.length_side);
							count = 1;						
							break;
						case 1:// Side , Side , Angle
							valA.setHint(R.string.length_side);
							valB.setHint(R.string.length_side);
							valC.setHint(R.string.known_angle);
							count = 2;
							break;						
						case 2:// Side , Angle , Angle			
							valA.setHint(R.string.length_side);
							valB.setHint(R.string.known_angle);
							valC.setHint(R.string.known_angle);
							count = 3;
							break;
						case 3:// Angle , Angle , Angle			
							valA.setHint(R.string.known_angle);
							valB.setHint(R.string.known_angle);
							valC.setHint(R.string.known_angle);
							count = 4;
							break;		
						}
						dialog.dismiss();
					}				 
			    });
			 alert.setNeutralButton(android.R.string.cancel, null);
			 AlertDialog alt = alert.create(); 			 
			 alt.show();
			 break;
		 case 2:
			 new AlertDialog.Builder(this)
				.setTitle("Result :")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("Angle (" + degrad + "):" + "\n\n" + "A = " + Double.toString(cosine_a) + "\nB = " + 
				             Double.toString(cosine_b) + "\nC = " + 
						     Double.toString(cosine_c) + "\n\nMedian : \n" + "\nA = " + 
							 Double.toString(median_a) + "\nB = " + 
						     Double.toString(median_b) + "\nC = " + 
							 Double.toString(median_c) + "\n\nAngle Bisector : \n" + "\nA = " + 
							 Double.toString(angle_bisectorA) + "\nB = " + 
							 Double.toString(angle_bisectorB) + "\nC = " + 
					         Double.toString(angle_bisectorC) + "\n" + "\n Area : \n " + 
							 Double.toString(area) + "\n" +"\n Perimeter : \n " + 
							 Double.toString(perimeter) + "\n" +"\n Cirum Circle Radius : \n " + 
							 Double.toString(cirum_circle) + "\n" +"\n Inscribed Circle Radius : \n " + 
							 Double.toString(inscribe_circle))
				.setNeutralButton(android.R.string.ok, null)
				.show();
			 break;		
		 case 3:
			 new AlertDialog.Builder(this)
				.setTitle("Result :")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("Side : \n \n" + Double.toString(sideSC) + "\n\nAngle (" + degrad + "):" + "\nA = "
				+ Double.toString(cosine_a) + "\nB = " + 
				             Double.toString(cosine_b) + "\n\nMedian : \n" + "\nA = " + 
							 Double.toString(median_a) + "\nB = " + 
						     Double.toString(median_b) + "\nC = " + 
							 Double.toString(median_c) + "\n\nAngle Bisector : \n" + "\nA = " + 
							 Double.toString(angle_bisectorA) + "\nB = " + 
							 Double.toString(angle_bisectorB) + "\nC = " + 
					         Double.toString(angle_bisectorC) + "\n" + "\n Area : \n " + 
							 Double.toString(area) + "\n" +"\n Perimeter : \n " + 
							 Double.toString(perimeter) + "\n" +"\n Cirum Circle Radius : \n " + 
							 Double.toString(cirum_circle) + "\n" +"\n Inscribed Circle Radius : \n " + 
							 Double.toString(inscribe_circle))
				.setNeutralButton(android.R.string.ok, null)
				.show();
			 break;		 
		 }	 
		 }
	
	
	
	public void Access(int access){
  		switch(access){
  		case 0:
  			AlertDialog.Builder accessibility = new AlertDialog.Builder(this);
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
  			String checkA = valA.getText().toString();
  			String checkB = valB.getText().toString();  
  			String checkC = valC.getText().toString();  
  			ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
  			clipBoard.setText(checkA + checkB + checkC);
  			Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
  			break;
  		case swap:
  			AlertDialog.Builder swap_query_three_builder = new AlertDialog.Builder(this);
	         swap_query_three_builder.setItems(R.array.swap_query_three, new DialogInterface.OnClickListener() {
	    	               public void onClick(DialogInterface dialog, int which) {
	    	            	   switch(which)
	    	            	   {
	    	            	   case 0:
	    	            		   swap(1);	   // Swap A & B 	            		   
	    	            		   break;
	    	            	   case 1:
	    	            		   swap(2);    // Swap B & C
	    	            		   break;
	    	            	   case 2:
	    	            		   swap(3);    // Swap A & C
	    	            		   break;	
	    	            	   }
	    	               // The 'which' argument contains the index position
	    	               // of the selected item
	    	           }
	    	    });
	    	   AlertDialog swap_query_show = swap_query_three_builder.create();
	    	   swap_query_show.show();
  			break;
  		case clear:
  			// Clearing inputs
  			valA.setText(null);
  			valB.setText(null);
  			valC.setText(null);
  			Toast.makeText(getBaseContext(), R.string.toast_input_cleared, Toast.LENGTH_SHORT).show();
  			break;
  		}
  	}
	
	protected void swap(int i) {
		// TODO Auto-generated method stub
		 switch(i){		 		 
		 case 1: // Swaping A & B
			 Toast.makeText(getBaseContext(), "Swaped A & B",Toast.LENGTH_LONG).show();
			 SwapA = valA.getText().toString();
			 SwapB = valB.getText().toString();
			 valA.setText(SwapB);
			 valB.setText(SwapA);
	         break;
		 case 2: // Swaping B & C
			 Toast.makeText(getBaseContext(), "Swaped B & C",Toast.LENGTH_LONG).show();
			 SwapB = valB.getText().toString();
			 SwapC = valC.getText().toString();
			 valC.setText(SwapB);
			 valB.setText(SwapC);
	         break;
		 case 3: // Swaping A & C
			 Toast.makeText(getBaseContext(), "Swaped A & C",Toast.LENGTH_LONG).show();
			 SwapA = valA.getText().toString();
			 SwapC = valC.getText().toString();
			 valA.setText(SwapC);
			 valC.setText(SwapA);
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
      
      if((e2.getY() - e1.getY()) > SWIPE_THRESHOLD_VELOCITY) // Swipe Down
      {
    	  valA.setHint(R.string.ph_swipe);
    	  valB.setHint(R.string.ph_swipe);
    	  valC.setHint(R.string.ph_swipe);
    	  displaydialog(1);
      }
      else if((e1.getY() - e2.getY()) > SWIPE_THRESHOLD_VELOCITY) //Swipe Up
  			{	    	  
    	  switch(count)
    	  {
    	  case 0:
    		  //Display dialog for selecting the major query
    		  displaydialog(1);
    		  break;	    		  
    	  case 1:
    		  // 3 Side known selected 
    		  Ans(0);
    		  break;
    	  case 2:
    		  // Side , Side , Angle selected 
    		  Ans(1);
    		  break;
    	  case 3:
    		  // Side , Angle , Angle selected 
    		  Ans(2);
    		  break;
    	  case 4:
    		  // 3 Angle known selected 
    		  Ans(3);
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

	private void Ans(int i) {
		// TODO Auto-generated method stub
		try{
			sideSA = Double.parseDouble(valA.getText().toString());
    		sideSB = Double.parseDouble(valB.getText().toString());
    		sideSC = Double.parseDouble(valC.getText().toString());			
		}catch(Exception parse){
			parseFail = true;
			displaydialog(0);
		}
		SharedPreferences convIt = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String default_angle = convIt.getString("default_angle", "1000");
    	if(!parseFail){  	   		
		switch(i){
		case 0:
			// Side , Side , Side calculus			
			// Multiply (180/Math.PI) for DEGREE
			//Squares stored in variables for future use
			squareA = sideSA*sideSA;
			squareB = sideSB*sideSB;
			squareC = sideSC*sideSC;
			
			cosine_a = (squareB + squareC - squareA) / (2*(sideSB*sideSC));
			cosine_b = (squareC + squareA - squareB) / (2*(sideSA*sideSC));
			cosine_c = (squareA + squareB - squareC) / (2*(sideSA*sideSB));
			if(default_angle == "1001"){
				cosine_a = Math.acos(cosine_a); //Radian
				cosine_b = Math.acos(cosine_b); //Radian
				cosine_c = Math.acos(cosine_c); //Radian
				degrad = "Radian";
				}
			else{
				cosine_a = Math.acos(cosine_a)*(180/Math.PI); //Degree
				cosine_b = Math.acos(cosine_b)*(180/Math.PI); //Degree
				cosine_c = Math.acos(cosine_c)*(180/Math.PI); //Degree
			}
			// Apollonius Theorem
			median_a = (2 * squareB) + (2 * squareC) - squareA;
			median_a = Math.sqrt(median_a / 4);
			median_b = (2 * squareA) + (2 * squareC) - squareB;
			median_b = Math.sqrt(median_b / 4);
			median_c = (2 * squareB) + (2 * squareA) - squareC;
			median_c = Math.sqrt(median_c / 4);
			
			//Angle Bisector
			angle_bisectorA = 2 * (sideSB * sideSC) * (Math.cos(sideSA/2) / (sideSB + sideSC));
			angle_bisectorB = 2 * (sideSA * sideSC) * (Math.cos(sideSB/2) / (sideSA + sideSC));
			angle_bisectorC = 2 * (sideSB * sideSA) * (Math.cos(sideSC/2) / (sideSB + sideSA));
			
			//Area
			area = (sideSB * sideSA) * (Math.sin(sideSC) / 2);
			//Perimeter
			perimeter = sideSA + sideSB + sideSC;
			//Circumscribed Circle Radius 
			cirum_circle = sideSA / (2 * (Math.sin(sideSA)));
			//Inscribed Circle Radius 
			inscribe_circle = sideSA * Math.sin(sideSC / 2) * (Math.sin(sideSB / 2) * Math.cos(sideSA / 2));
			displaydialog(2);
			break;
		case 1:// Side , Side , Angle calculus	
			//Squares stored in variables for future use
			squareA = sideSA * sideSA;
			squareB = sideSB * sideSB;
			cosine_c = Math.cos(sideSC);
			// Finding Side C
			sideSC = (squareA + squareB) - ((2*(sideSA*sideSB)) * cosine_c);
			sideSC = Math.sqrt(sideSC);
			// Getting it's square
			squareC = sideSC*sideSC;
			// Getting remaining angle
			cosine_a = (squareB + squareC - squareA) / (2*(sideSB*sideSC));
			cosine_b = (squareC + squareA - squareB) / (2*(sideSA*sideSC));
			if(default_angle == "1001"){
				cosine_a = Math.acos(cosine_a); //Radian
				cosine_b = Math.acos(cosine_b); //Radian
				degrad = "Radian";
				}
			else{
				cosine_a = Math.acos(cosine_a)*(180/Math.PI); //Degree
				cosine_b = Math.acos(cosine_b)*(180/Math.PI); //Degree
			}
			// Apollonius Theorem
			median_a = (2 * squareB) + (2 * squareC) - squareA;
			median_a = Math.sqrt(median_a / 4);
			median_b = (2 * squareA) + (2 * squareC) - squareB;
			median_b = Math.sqrt(median_b / 4);
			median_c = (2 * squareB) + (2 * squareA) - squareC;
			median_c = Math.sqrt(median_c / 4);
			
			//Angle Bisector
			angle_bisectorA = 2 * (sideSB * sideSC) * (Math.cos(sideSA/2) / (sideSB + sideSC));
			angle_bisectorB = 2 * (sideSA * sideSC) * (Math.cos(sideSB/2) / (sideSA + sideSC));
			angle_bisectorC = 2 * (sideSB * sideSA) * (Math.cos(sideSC/2) / (sideSB + sideSA));
			
			//Area
			area = (sideSB * sideSA) * (Math.sin(sideSC) / 2);
			//Perimeter
			perimeter = sideSA + sideSB + sideSC;
			//Circumscribed Circle Radius 
			cirum_circle = sideSA / (2 * (Math.sin(sideSA)));
			//Inscribed Circle Radius 
			inscribe_circle = sideSA * Math.sin(sideSC / 2) * (Math.sin(sideSB / 2) * Math.cos(sideSA / 2));
			displaydialog(3);
			break;
		case 2:// Side , Angle , Angle calculus
		case 3:// Angle , Angle , Angle calculus	
			//Under Construction case
			 new AlertDialog.Builder(Triangle.this)
				.setTitle(R.string.construction_error_title)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(R.string.construction_error_summary)
				.setNeutralButton(android.R.string.ok, null)
				.show();
			break;
		}
    	}
    	parseFail = false;
    	}
      };
      GestureDetector gestureDetector= new GestureDetector(simpleOnGestureListener);	

	
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

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
public class Armstrong extends Activity{
	
	int sum=0,n,m,remainder,i;
	
	public static final int QUERY = 0;
	public static final int copy = 0;
	public static final int clear = 1;
	
	EditText ARM;
	
	public int count = 0;
	
	boolean fail = false;
	
	String collector = "" , collect;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefControl(); // Design Management is defined in this method
        setContentView(R.layout.activity_armstrong);
        ActivityAnimation();   		
    	ARM = (EditText)findViewById(R.id.editText1);
    	setupActionBar();
    }

	private void Ans(int v) {
		// TODO Auto-generated method stub
		try{
		n = Integer.parseInt(ARM.getText().toString());
		}
		catch(Exception parse){
			displaydialog(0);
			fail = true;
		}
		if(!fail){
    	switch(v) {    	
    	case 0:		
    		m=n;
    		while( m != 0 )
    		{
    			remainder = m % 10;
    			sum = sum + remainder * remainder * remainder;
    			m = m / 10;
    			}
    		if ( n == sum )
    			displaydialog(1);
    		else
    			displaydialog(2);
    		sum=0;
    		break;
    	case 1:
    		for( i = 1 ; i <= n ; i++ ){
    			m = i;
    			while( m != 0 ){
    				remainder = m % 10;
    				sum = sum + remainder * remainder * remainder;
    				m = m / 10;
    				}
    			if (i == sum){
    				// Collector collects all the Armstrong numbers
    				collector = collector + Double.toString(i);
    				StringBuilder sb = new StringBuilder();
    				// String Builder is used to append i.e add at end all the numbers
    				sb.append(collector);
    				// The String Builder is converted to String for showing result
    				collect = sb.toString();
    				// As result are display without spaces Ex. 1.0147.0
    				if(collect.contains(".0"))
    					// As .0 is unneccessary term it is replaced by new line
    					collect = collect.replace(".0", "\n");
    				}
    			// Flushing is required for proper calculation
    			sum = 0;
    			}  
    		// Flushing required to prevent printing series again & again 
    		collector = "";
    		// Finally display dialog
        	displaydialog(3);
    		break;    		
    		}
    	}
		// States that Parsing is successful
    	fail = false;
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
				.setMessage(R.string.arm_yes)
				.setNeutralButton(android.R.string.ok, null)
				.show();
			 break;
		 case 2:
			 new AlertDialog.Builder(this)
				.setTitle("Result : ")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(R.string.arm_no)
				.setNeutralButton(android.R.string.ok, null)
				.show();
			 break;
		 case 3:
			 new AlertDialog.Builder(this)
				.setTitle("Result : ")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("Armstrong Series : \n\n" + collect)
				.setNeutralButton(android.R.string.ok, null)
				.show();
			 break;
		 }
		 else if(toast.contains("20"))
			 switch(i)
			 {
			 case 0:
				 Toast.makeText(getBaseContext(), R.string.error_summary, Toast.LENGTH_SHORT).show();
		         break;
			 case 1:
				 Toast.makeText(getBaseContext(), R.string.arm_yes, Toast.LENGTH_SHORT).show();
				 break;
			 case 2:
				 Toast.makeText(getBaseContext(), R.string.arm_no, Toast.LENGTH_SHORT).show();
				 break;
			 case 3:
				 Toast.makeText(getBaseContext(), "Armstrong Series : \n\n" + collect, Toast.LENGTH_LONG).show();
				 break;
		 }
		 else
			 switch(i) 
			 {
			 case 0:
				 Crouton.makeText(this, R.string.error_summary , Style.INFO).show();
		         break;
			 case 1:
				 Crouton.makeText(this, R.string.arm_yes, Style.CONFIRM).show();
		         break;
			 case 2:
				 Crouton.makeText(this, R.string.arm_no, Style.ALERT).show();
		         break;
			 case 3:
				 Crouton.makeText(this, "Armstrong Series : \n\n" + collect, Style.CONFIRM).show();
				 break;
			 }
	}
	
	public void query(int q){
		switch(q){
		
		case QUERY:
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			 alert.setTitle("What you wanna calculate");
			 alert.setItems(R.array.armstrong_query, new DialogInterface.OnClickListener()
			    {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						// TODO Auto-generated method stub		
						switch(item)
						{
						case 0:		
							ARM.setHint(R.string.enter_arm_no);
							count = 1;
							break;
						case 1:
							ARM.setHint(R.string.generate_arm);
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
			AlertDialog.Builder accessibility = new AlertDialog.Builder(Armstrong.this);
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
			String check = ARM.getText().toString();
			ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
			clipBoard.setText(check);
			Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
			break;
		case clear:
			// Clearing inputs
			ARM.setText(null);
			ARM.setHint(R.string.ph_swipe);
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
      
      if((e2.getY() - e1.getY()) > SWIPE_THRESHOLD_VELOCITY) // Swipe Down
      {  	  
    	  query(QUERY);
    	  Toast.makeText(getBaseContext(), R.string.toast_query_cleared, Toast.LENGTH_SHORT).show();
      }
      else if(e1.getX() - e2.getX() > SWIPE_THRESHOLD_VELOCITY &&
              Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
    	  // Swipe Right
    	  Access(0);	
      else if(e2.getX() - e1.getX() > SWIPE_THRESHOLD_VELOCITY &&
              Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
    	  // Swipe Left
    	  Access(0);
      else if((e1.getY() - e2.getY()) > SWIPE_THRESHOLD_VELOCITY){ //Swipe Up
    	  switch(count){
			 case QUERY:
				 query(QUERY);
				 break;
			 case 1:
				 Ans(0);
				 break;		
			 case 2:
				 Ans(1);
				 break;
    	  }
      }
      return super.onFling(e1, e2, velocityX, velocityY);
     }
      };
      GestureDetector gestureDetector= new GestureDetector(simpleOnGestureListener);
      
	/* private void applyAnimation(View v){
		Animbutton myAnimation = new Animbutton();
		myAnimation.setDuration(5000);
		myAnimation.setFillAfter(true);
		myAnimation.setInterpolator(new OvershootInterpolator());
		
		v.startAnimation(myAnimation);
    }
	*/
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
    
    @Override
	public void onBackPressed() {
	 // TODO Auto-generated method stub
    	finish();
    	ActivityAnimation();
	}

    
}

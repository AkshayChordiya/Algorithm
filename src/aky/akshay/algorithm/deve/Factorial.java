package aky.akshay.algorithm.deve;

import android.annotation.SuppressLint;
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

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")

public class Factorial extends Activity{
	
	String raise;
	boolean fact_max = true;
	
	Double m,n;
	EditText fact;
	Double factorial=(double) 1.0;
	
	public static final int copy = 0;
	public static final int clear = 1;
	private static final int by_default = 2;
	
	boolean parseFail = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefControl(); // Design Management is defined in this method
        setContentView(R.layout.activity_factorial);
        ActivityAnimation();	  
        initControls();
        setupActionBar();
    }

    private void initControls() {
		// TODO Auto-generated method stub
    	fact = (EditText)findViewById(R.id.editText1);    	
	}
    private void Ans(int j) {
		// TODO Auto-generated method stub
    	try{
    	m = Double.parseDouble(fact.getText().toString());
    	}
    	catch(Exception parse){
    		parseFail = true;
    		displaydialog(0);
    	}
    	if(!parseFail){
    		switch(j){
    		
    		case 0:	 
    			if(m >= 2000 && fact_max == true)
    				displaydialog(by_default);
    			else if(m<2000 | fact_max == false){
    				for(int i=1;i<=m;i++)
    					factorial=factorial*i;
    				n = factorial;
    				raise = Double.toString(n);
			 
			 
			 /* Get max fraction set
			 SharedPreferences numberformat = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			 String format = numberformat.getString("maxFraction", "4");
			 int maxformat = Integer.parseInt(format);
			// Using NumberFormat to have more control over formatted strings
			 NumberFormat nf = NumberFormat.getInstance();
			 
			 // Setting max fraction
			 nf.setMaximumFractionDigits(maxformat);
			 nf.setMinimumFractionDigits(0);
			 // Formatting up to digits entered
			 raise = nf.format(n); */
			 
			 
			 // Flushing Value
			 factorial = 1.0;
			 
			 SharedPreferences raiseIt = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			 boolean raiser = raiseIt.getBoolean("raise", true);
			 if(raiser == true)
				 raise = raise.replaceFirst("E", " x 10^");
			 displaydialog(1);
		 }
	     break;
	     }
    		}    	
    	parseFail = false;
    	}
    /* private void raiser(int i) {
		// TODO Auto-generated method stub
		switch(i)
		{
		case 0:
			raise = Double.toString(n);
			//String[] split_raise = raise.split("E");
			//String round = split_raise[1].toString();
			raise = raise.replaceFirst("E", " x 10^");
			//add_up = split_raise[0].toString() + round;
			displaydialog(1);
			break;
		}
	}	*/

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
				.setMessage("Factorial : \n\n " + raise)
				.setNeutralButton(android.R.string.ok, null)
				.setPositiveButton("Copy",new DialogInterface.OnClickListener()
				{
				@Override
				public void onClick(DialogInterface dialoginterface, int i)
				{
			        ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					clipBoard.setText(raise);
					Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
				}
				})
				.show();
			 break;
		 case by_default:
			 new AlertDialog.Builder(this)
				.setTitle(R.string.fact_limit)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(R.string.fact_max)
				.setPositiveButton("Calculate it", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialoginterface, int i){
						fact_max = false;
						Ans(0);
					}
					})
				.setNegativeButton(android.R.string.cancel, null)
				.show();
			 break;
		 }
		 else if(toast.contains("20"))
			 switch(i) 
			 {
			 case 0:
				 Toast.makeText(this, R.string.error_summary, Toast.LENGTH_SHORT).show();
		         break;
			 case 1:
				 Toast.makeText(this,"Factorial = " + raise, Toast.LENGTH_SHORT).show();
		         break;
			 case by_default:
				 new AlertDialog.Builder(this)
					.setTitle(R.string.fact_limit)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setMessage(R.string.fact_max)
					.setPositiveButton("Do it", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialoginterface, int i){
							fact_max = false;
							Ans(0);
						}
						})
					.setNegativeButton(android.R.string.cancel, null)
					.show();
				 break;
			 }
		 else
			 switch(i) 
			 {
			 case 0:
				 Crouton.makeText(this, R.string.error_summary , Style.INFO).show();
		         break;
			 case 1:
				 Crouton.makeText(this,"Factorial = " + raise, Style.CONFIRM).show();
		         break;
			 case by_default:
				 new AlertDialog.Builder(this)
					.setTitle(R.string.fact_limit)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setMessage(R.string.fact_max)
					.setPositiveButton("Do it", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialoginterface, int i){
							fact_max = false;
							Ans(0);
						}
						})
					.setNegativeButton(android.R.string.cancel, null)
					.show();
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
			String check = fact.getText().toString();
			ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
			clipBoard.setText(check);
			Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
			break;
		case clear:
			// Clearing inputs
			fact.setText(null);
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
    	  fact.setText(null);
    	  Toast.makeText(getBaseContext(), R.string.toast_input_cleared, Toast.LENGTH_SHORT).show();
      }
      else if((e1.getY() - e2.getY()) > SWIPE_THRESHOLD_VELOCITY) //Swipe Up
    	  Ans(0);
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
    
    
    
}

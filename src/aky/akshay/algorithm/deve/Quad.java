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
import android.widget.TextView;
import android.widget.Toast;
import de.keyboardsurfer.android.widget.Crouton;
import de.keyboardsurfer.android.widget.Style;

@SuppressWarnings("deprecation")
public class Quad extends Activity{
	
	double a,b,c,d1,b1,b2,o,sq_o;
	EditText get_a,get_b,get_c;
	TextView quadratic_equation;
	
	public static final int copy = 0;
	public static final int clear = 1;	
	
	boolean parseFail = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefControl(); // Design Management is defined in this method
        setContentView(R.layout.activity_quad);       
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
  		// TODO Auto-generated method stub
    	get_a = (EditText)findViewById(R.id.editText1);
    	get_b = (EditText)findViewById(R.id.EditText2);
    	get_c = (EditText)findViewById(R.id.EditText3);
    	quadratic_equation = (TextView)findViewById(R.id.textView2);
  	}
    
    private void Ans(int i) {
			// TODO Auto-generated method stub
    	try{
    		a = Double.parseDouble(get_a.getText().toString());
    		b = Double.parseDouble(get_b.getText().toString());
    		c = Double.parseDouble(get_c.getText().toString());
    	}catch(Exception parse){
    		parseFail = true;
			displaydialog(0);
    	}
    	if (!parseFail){
    	switch(i){
    	case 0:    		  		
    		o = (b * b) - (4 * (a * c));
    		sq_o = Math.sqrt(o);
    		if(o == 0)
    		{
    			d1 = -b / (2 * a);
    			displaydialog(2);   			
    		}
    		if(o < 0)
    			displaydialog(1);
    		if(o > 0)
    		{
    			b1 = ( -b + sq_o) / (2 * a );
    			b2 = ( -b - sq_o) / (2 * a );
    			displaydialog(3);
    			}
    		break;
    		}
    	String checkA = get_a.getText().toString();
    	String checkB = get_b.getText().toString();
    	String checkC = get_c.getText().toString();
    	if(checkC.contains("-") && checkB.contains("-")){
    		checkB = checkB.replaceFirst("-", "- ");
    		quadratic_equation.setText(checkA + "x² " + checkB + "x " + checkC + " = 0");
    		}
    	else if(checkB.contains("-")){
    		checkB = checkB.replaceFirst("-", "- ");
    		quadratic_equation.setText(checkA + "x² " + checkB + "x + " + checkC + " = 0");
    		}
    	else if(checkC.contains("-"))
    		quadratic_equation.setText(checkA + "x²" + " + " + checkB + "x " + checkC + " = 0");
    	else
    		quadratic_equation.setText(checkA + "x²" + " + " + checkB + "x + " + checkC + " = 0");
    	} 
    	parseFail = false;
    }
    
    protected void displaydialog(int i) {
		// TODO Auto-generated method stub
	     SharedPreferences toast_message = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	     String toast = toast_message.getString("toast", "10");
		 if(toast.contains("10"))
		 switch(i)
		 {
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
				.setMessage("Roots are Imaginary / Complex")
				.setNeutralButton(android.R.string.ok, null)
				.show();
			 break;
		 case 2:
			 new AlertDialog.Builder(this)
				.setTitle("Result : ")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("Roots are Distinct & Real " + "\n \n " + "Root = " + Double.toString(d1))
				.setPositiveButton("Copy root",new DialogInterface.OnClickListener()
				{
				@Override
				public void onClick(DialogInterface dialoginterface, int i)
				{
			        ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					clipBoard.setText(Double.toString(d1));
					Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
				}
				})
				.setNeutralButton(android.R.string.ok, null)
				.show();
			 break;
		 case 3:
			 new AlertDialog.Builder(this)
				.setTitle("Result : ")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("Roots are Real" + "\n \n 1st Root = " + Double.toString(b1)  + "\n \n 2st Root = " + Double.toString(b2))
				.setNeutralButton(android.R.string.ok, null)
				.setPositiveButton("Copy roots",new DialogInterface.OnClickListener()
				{
				@Override
				public void onClick(DialogInterface dialoginterface, int i)
				{
			        ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					clipBoard.setText(Double.toString(b1) + Double.toString(b2));
					Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
				}
				})
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
				 Toast.makeText(this, "Roots are Imaginary / Complex", Toast.LENGTH_SHORT).show();
		         break;
			 case 2:
				 Toast.makeText(this, "Roots are Distinct & Real", Toast.LENGTH_SHORT).show();
		         break;
			 case 3:
				 Toast.makeText(this, "Roots are Real" + "\n \n 1st Root = " + Double.toString(b1)  + "\n \n 2st Root = " + Double.toString(b2), Toast.LENGTH_LONG).show();
		         break;
			 }
		 else
			 switch(i) 
			 {
			 case 0:
				 Crouton.makeText(this, R.string.error_summary, Style.INFO).show();
		         break;
			 case 1:
				 Crouton.makeText(this, "Roots are Imaginary / Complex", Style.INFO).show();
		         break;
			 case 2:
				 Crouton.makeText(this, "Roots are Distinct & Real", Style.INFO).show();
		         break;
			 case 3:
				 Crouton.makeText(this, "Roots are Real" + "\n \n 1st Root = " + Double.toString(b1)  + "\n \n 2st Root = " + Double.toString(b2), Style.CONFIRM).show();
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
    	  get_a.setText(null);
    	  get_b.setText(null);
    	  get_c.setText(null);
    	  quadratic_equation.setText(R.string.quad_equation);
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
      GestureDetector gestureDetector= new GestureDetector(simpleOnGestureListener);
      
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
  			String checkA = get_a.getText().toString();
  			String checkB = get_b.getText().toString();  
  			String checkC = get_b.getText().toString();  
  			ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
  			clipBoard.setText(checkA + checkB + checkC);
  			Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
  			break;
  		case clear:
  			// Clearing inputs
  			get_a.setText(null);
  			get_b.setText(null);
  			get_c.setText(null);
  			Toast.makeText(getBaseContext(), R.string.toast_input_cleared, Toast.LENGTH_SHORT).show();
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

package aky.akshay.algorithm.deve;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.ClipboardManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Scalc extends Activity {
	
	private static String LOG_TAG = Scalc.class.getName();
	
	ViewPager CalcPager;  
	private PagerAdapter mAdapter;
	
	// Constant for number of VIEWS
	private static int VIEWS = 3;
	
	// Constants for accessibility dialog	
	public static final int copy = 0;
	public static final int cut = 1;
	public static final int paste = 2;
	public static final int clear = 3;
	
	// Constant integers for buttons
	public static final int But0 = 0;
	public static final int But1 = 1;
	public static final int But2 = 2;
	public static final int But3 = 3;
	public static final int But4 = 4;
	public static final int But5 = 5;
	public static final int But6 = 6;
	public static final int But7 = 7;
	public static final int But8 = 8;
	public static final int But9 = 9;
	public static final int AC = 10;
	public static final int Del = 11;
	
	public static final int Continous_Del = 98;
	
	// Equal Button constant
	public static final int EQUAL = 99;
	
	
	// Basic Operators
	public static final int ADD = 12;
	public static final int SUB = 13;
	public static final int MUL = 14;
	public static final int DIV = 15;
	public static final int Dec = 16;
	
	// Advance Panel
	public static final int sinx = 0;
	public static final int cosx = 1;
	public static final int tanx = 2;
	public static final int log = 3;
	public static final int ln = 4;
	public static final int anti = 5;
	public static final int sinhx = 6;
	public static final int coshx = 7;
	public static final int tanhx = 8;	
	
	// Function Panel
	public static final int SQUAREROOT = 0;
	public static final int CUSTOMROOT = 1;
	public static final int SQUARE = 2;
	public static final int CUSTOMSQUARE = 3;
	
	public boolean logOln ,inverse = false, parseFail = false;
	public String input = null , type = "" , first_input = null , second_input = null ;	
	
	
	TextView displayResult , ans;
	
	// Number Buttons
	Button Button0,Button1,Button2,Button3,Button4;
	Button Button5,Button6,Button7,Button8,Button9;
	
	// Clearing Buttons
	Button all_clear;
	ImageButton delete;
	
	// Operator Buttons
	Button Operator_add,Operator_sub,Operator_mul,Operator_div,Operator_decimal;
	
	// Equal Button;
	Button Equal;
	
	/** Advance Panel Buttons **/
	// Trigonometric Buttons
	Button sin,cos,tan,sinh,cosh,tanh;
	
	// Logarithmic Buttons
	Button log10,loge,antilog;
	
	/** Function Panel Buttons **/
	Button square_root , custom_root , square_twice , custom_square;
	
	
	// Counting which operator was pressed
	int operator_count = 0;
	
	public Double result , advance_result;
	
	
	// String for splitting 1st & 2nd input
	String splitInput[];
	// String for specifying which factor to remove
	String delims;
	
	// String for preference
	String default_mul_sign;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefControl(); // Design Management is defined in this method
		setContentView(R.layout.activity_scalc);
		EstablishView();	
		initScreenLayout();
		ActivityAnimation();   		
		setupActionBar();
	}
	
	private void initScreenLayout() {
		/* The following three command lines you can use depending
		 * upon the emulator device you are using.*/
		
		// 320 x 480 (Tall Display - HVGA-P) [default]
		// 320 x 240 (Short Display - QVGA-L)
		// 240 x 320 (Short Display - QVGA-P)
		
		/** 320dp: a typical phone screen (240x320 ldpi, 320x480 mdpi, 480x800 hdpi, etc).
		 * 480dp: a tweener tablet like the Streak (480x800 mdpi).
		 * 600dp: a 7” tablet (600x1024 mdpi).
		 * 720dp: a 10” tablet (720x1280 mdpi, 800x1280 mdpi, etc).**/
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		// this.showAlert(dm.widthPixels +" "+ dm.heightPixels, dm.widthPixels
		// +" "+ dm.heightPixels, dm.widthPixels +" "+ dm.heightPixels, false);
		int height = dm.heightPixels;
		int width = dm.widthPixels;
		int xlarge = 500;
		
		
		if(height > 1280 && width > 720){
        	Button0.setHeight(xlarge);
        	Button1.setHeight(xlarge);
        	Button2.setHeight(xlarge);
        	Button3.setHeight(xlarge);
        	Button4.setHeight(xlarge);
        	Button5.setHeight(xlarge);
        	Button6.setHeight(xlarge);
        	Button7.setHeight(xlarge);
        	Button8.setHeight(xlarge);
        	Button9.setHeight(xlarge);
        	Equal.setHeight(xlarge);
			
		}
		  }
	
	
	private void EstablishView() {
		// TODO Auto-generated method stub
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		displayResult = (TextView)findViewById(R.id.textdisplay);
		displayResult.setOnClickListener(KeypadClick);
		ans = (TextView)findViewById(R.id.ansdisplay);
		ans.setOnClickListener(KeypadClick);
		displayResult.setHint("Enter Expression");
		
		// Establishing View Pager & Adapter
		mAdapter = new QuickAdapter();	
		CalcPager = (ViewPager) findViewById(R.id.calc_pager);
		CalcPager.setAdapter(mAdapter);	
		// Set View pager view to 1 not default 0
		CalcPager.setCurrentItem(1);
		SharedPreferences convIt = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		default_mul_sign = convIt.getString("default_mul_sign", "1005");
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
	
	public View.OnClickListener KeypadClick = new View.OnClickListener() {
	     @Override
	     public void onClick(final View v) {	    	 
	         switch(v.getId()){
	             case R.id.btn0:
	            	 BasicPanel(But0);
	            	 break;
	             case R.id.btn1:
	            	 BasicPanel(But1);
	            	 break;
	             case R.id.btn2:
	            	 BasicPanel(But2);
	            	 break;
	             case R.id.btn3:
	            	 BasicPanel(But3);
	            	 break;
	             case R.id.btn4:
	            	 BasicPanel(But4);
	            	 break;
	             case R.id.btn5:
	            	 BasicPanel(But5);
	            	 break;
	             case R.id.btn6:
	            	 BasicPanel(But6);
	            	 break;
	             case R.id.btn7:
	            	 BasicPanel(But7);
	            	 break;
	             case R.id.btn8:
	            	 BasicPanel(But8);
	            	 break;
	             case R.id.btn9:
	            	 BasicPanel(But9);
	            	 break;
	             case R.id.btn_Clear:
	            	 BasicPanel(AC);
	            	 break;
	             case R.id.btn_delete:
	            	 BasicPanel(Del);
	            	 break;
	             case R.id.operator_add:
	            	 BasicPanel(ADD);
	            	 break;
	             case R.id.operator_sub:
	            	 BasicPanel(SUB);
	            	 break;
	             case R.id.operator_mul:
	            	 BasicPanel(MUL);
	            	 break;
	             case R.id.operator_div:
	            	 BasicPanel(DIV);
	            	 break;
	             case R.id.btnDec:
	            	 BasicPanel(Dec);
	            	 break;
	             case R.id.equal:
	            	 BasicPanel(EQUAL);
	            	 break;
	             case R.id.sinx:
	            	 inverse = false;
	            	 AdvancePanel(sinx);
	            	 break;
	             case R.id.cosx:
	            	 inverse = false;
	            	 AdvancePanel(cosx);
	            	 break;	            	 
	             case R.id.tan:
	            	 inverse = false;
	            	 AdvancePanel(tanx);
	            	 break;
	             case R.id.sinhx:
	            	 inverse = false;
	            	 AdvancePanel(sinhx);
	            	 break;
	             case R.id.coshx:
	            	 inverse = false;
	            	 AdvancePanel(coshx);
	            	 break;	            	 
	             case R.id.tanhx:
	            	 inverse = false;
	            	 AdvancePanel(tanhx);
	            	 break;
	             case R.id.log10:
	            	 AdvancePanel(log);
	            	 break;
	             case R.id.loge:
	            	 AdvancePanel(ln);
	            	 break;
	             case R.id.square_root:
	            	 FunctionPanel(SQUAREROOT);
	            	 break;
	             case R.id.custom_root:
	            	 //FunctionPanel(CUSTOMROOT);
	            	 break;
	             case R.id.square_twice:
	            	 FunctionPanel(SQUARE);
	            	 break;
	             case R.id.square_x:
	            	 //FunctionPanel(CUSTOMSQUARE);
	            	 break;
	             case R.id.ansdisplay:
	            	 Access(0);
	            	 break;
	         }
	         }		
	     };
	     
	     private void FunctionPanel(int tx) {
	    	 // TODO Auto-generated method stub
	    	 try{
	    		 input = displayResult.getText().toString();
	    		 }
	    	 catch(Exception parse){
	    		 Log.d(LOG_TAG, "Parsing Failed");
	    		 parseFail = true;
	    		 }
	    	 if(!parseFail){
	    		 Double function;
	    		 function = Double.parseDouble(input);
	    	 switch(tx){
	    	 case SQUAREROOT:
	    		 //displayResult.setText("sqrt(" + input + ")");    
	    		 result = Math.sqrt(function);
            	 break;            
             case SQUARE:
            	 result = Math.pow(function, 2);
            	 break;
             case CUSTOMROOT:
            	 displayResult.setHint("Enter y then press again");           	 
            	 operator_count = 5;
            	 break;
             case CUSTOMSQUARE:
            	 displayResult.setHint("Enter y then press again");    
            	 operator_count = 6;
            	 break;
	    		 }
	    	 ans.setText(result.toString());
	    	 CalcPager.setCurrentItem(1);
	    	 }
	    	 }

		public View.OnLongClickListener LongClick = new View.OnLongClickListener() {
		     @Override
		     public boolean onLongClick(final View v) {	    	 
		         switch(v.getId()){
		         case R.id.sinx:
		        	 inverse = true;
		        	 AdvancePanel(sinx);	         
	            	 break;
	             case R.id.cosx:
	            	 inverse = true;
	            	 AdvancePanel(cosx);
	            	 break;	            	 
	             case R.id.tan:
	            	 inverse = true;
	            	 AdvancePanel(tanx);
	            	 break;
	             case R.id.sinhx:
	            	 inverse = true;
	            	 AdvancePanel(sinhx);
	            	 break;
	             case R.id.coshx:
	            	 inverse = true;
	            	 AdvancePanel(coshx);
	            	 break;	            	 
	             case R.id.tanhx:
	            	 inverse = true;
	            	 AdvancePanel(tanhx);
	            	 break;
	             case R.id.log10:
	            	 logOln = true;
	            	 AdvancePanel(anti);
	            	 break;
	             case R.id.loge:
	            	 logOln = false;
	            	 AdvancePanel(anti);
	            	 break;
		         }
				return true;
		         }		
		     };
	     
	     private void AdvancePanel(int n) {
				// TODO Auto-generated method stub
	    	 try{
	    		 input = displayResult.getText().toString();
	    		 advance_result = Double.parseDouble(input);
	    		 }
	    	 catch(Exception parse){
	    		 parseFail = true;
	    		 Log.d(LOG_TAG, "Parsing Failed");
	    		 }
	    	 if(!parseFail){
	    	 SharedPreferences convIt = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	         String default_angle = convIt.getString("default_angle", "1000");
	    	 if(default_angle.contentEquals("1000")){
	    		 // Covert radian to degree for normal usage
	    		 if(!inverse)
	    			 // Converting even there is no inverse
	    			 result = advance_result * (Math.PI / 180);
	    		 else
	    			// No Converting for inverse
	    			 result = advance_result;
	    	 }
	    	 else
	    		 result = advance_result;
	    		 switch(n){
	    		 case sinx:
	    			 if(inverse)
	    				 result = Math.asin(result) * (180 / Math.PI);
	    			 else if(!inverse)
	    				 result = Math.sin(result);
	    			 break;
	    		 case cosx:
	    			 if(inverse)
	    				 result = Math.acos(result) * (180 / Math.PI);
	    			 else if(!inverse)
	    				 result = Math.cos(result);    			 
	    			 break;
	    		 case tanx:
	    			 if(inverse)
	    				 result = Math.atan(result) * (180 / Math.PI);
	    			 else if(!inverse)
	    				 result = Math.tan(result);	    			 
	    			 break;
	    		 case sinhx:
	    			 result = Math.sinh(result);
	    			 break;
	    		 case coshx:
	    			 result = Math.cosh(result);
	    			 break;
	    		 case tanhx:
	    			 result = Math.tanh(result);
	    			 break;
	    		 case log:
	    			 logOln = true;
	    			 result = Math.log10(advance_result);
	    			 break;
	    		 case ln:
	    			 logOln = false;
	    			 result = Math.log(advance_result);
	    			 break;
	    		 case anti:
	    			 if(logOln)
	    				 result = Math.pow(10, advance_result);
	    			 else if(!logOln)
	    				 result = Math.pow(Math.E, advance_result);
	    			 break;
	    			 }
	    		 ans.setText(result.toString());
	    		 CalcPager.setCurrentItem(1);
	    		 }
	    	 }     
	     	     
	     
   private void BasicPanel(int i) {
	   // TODO Auto-generated method stub
	   try{
	   input = displayResult.getText().toString();
	   }catch(Exception parse){
		   parseFail = true;
		   Log.d(LOG_TAG, "Parsing Failed");
		   }
	   switch(i) {
	   case But0:
		   ButtonController(But0);		   
		   break;
	   case But1:
		   ButtonController(But1);
		   break;
       case But2:
    	   ButtonController(But2);
    	   break;
       case But3:
    	   ButtonController(But3);
    	   break;
       case But4:
    	   ButtonController(But4);
    	   break;
       case But5:
    	   ButtonController(But5);
    	   break;
       case But6:
    	   ButtonController(But6);
    	   break;
       case But7:
    	   ButtonController(But7);
    	   break;
       case But8:
    	   ButtonController(But8);
    	   break;
       case But9:
    	   ButtonController(But9);
    	   break;
       case AC:   
    	   displayResult.setText(null);
    	   ans.setText(null);
    	   operator_count = 0;
    	   displayResult.setHint("Enter Expression");
    	   break;
       case Del:
    	   // Handle backspace
    	   if (input.length() > 0) {
    		   input = input.substring(0, input.length() - 1);
    	   }
    	   displayResult.setText(input);
    	   displayResult.setHint("Enter Expression");
    	   break;
       case Continous_Del:
    	   break;
       case ADD:
    	   type = "+";
    	   operator_count = 1;
    	   OperatorFunction(ADD);   	   
    	   break;
       case SUB:
    	   type = "-";
    	   operator_count = 2;
    	   OperatorFunction(SUB);   	  
    	   break;
       case MUL: 	    	  
    	   if(default_mul_sign.contains("1005"))
    		   type = "x";
    	   else if(default_mul_sign.contains("1006"))
    		   type = "*";
    	   operator_count = 3;
    	   OperatorFunction(MUL);   	  
    	   break;
       case DIV:
    	   type = "/";
    	   operator_count = 4;
    	   OperatorFunction(DIV);   	  
    	   break;
       case Dec:
    	   input = input + ".";
    	   displayResult.setText(input);
    	   input = null;
    	   break;
       case EQUAL:
    	   //if(operator_type == false)
    		  // finalResult();
    	   Double a = 0.0,b = 0.0;
    	   String transfer="";
    	   try{
    		  // if(!recursive)
    		   a = Double.parseDouble(first_input);
    		   //else if(recursive)
    			  // a = Double.parseDouble(transfer);
    		   
    		   b = Double.parseDouble(second_input);   
    	   }catch(Exception parse){
    		   parseFail = true;
    		   Log.d(LOG_TAG, "Double Parsing Failed");
    	   }
    	   if(!parseFail){    		   
    	   switch(operator_count){
    	   case 1: //Add
    		   result = a + b;    		   
    		   break;
    	   case 2: //Sub
    		   result = a - b;   
    		   break;
    	   case 3: //Mul
    		   result = a * b;  		   
    		   break;   		   
    	   case 4: //Div
    		   result = a / b;     		   
    		   break; 
    	   case 5: // y ^ 1 /x
    		   result = a / b;     
    		   break;
    	   case 6: // x ^ y
    		   result = a / b;     
    		   break;
    	   }  	   
    	   transfer = result.toString();
    	   ans.setText(transfer);
    	   operator_count = 0;    
    	   }
    	   }
	   parseFail = false;
	   }

	private void ButtonController(int but02) {
		// TODO Auto-generated method stub	
		//operator_type = false;				
		switch(but02){
		case But0:
			input = input + "0";		
			break;
		case But1:
			input = input + "1";
			break;
	    case But2:
	    	input = input + "2";
	    	break;
	    case But3:
	    	input = input + "3";   	
	    	break;
	    case But4:
	    	input = input + "4";
	    	break;
	    case But5:
	    	input = input + "5";  	
	    	break;
	    case But6:
	    	input = input + "6";
	    	break;
	    case But7:
	    	input = input + "7";
	    	break;
	    case But8:
	    	input = input + "8";
	    	break;
	    case But9:
	    	input = input + "9";
	    	break;
	    	}		
		switch(operator_count){
		case 1:			
			delims = "[+]";				
			splitInput = input.split(delims);
			Log.d(LOG_TAG, "Split Success");	
			second_input = splitInput[1].toString();
			//for(int i = 0 ; i < splitInput.length ; i++)
				//second_input = splitInput[i].toString();
			Log.d(LOG_TAG, "Split Transfer done");			
			break;
		case 2:			
			delims = "[-]";
			splitInput = input.split(delims);
			Log.d(LOG_TAG, "Split Success");
			second_input = splitInput[1].toString();
			Log.d(LOG_TAG, "Split Transfer done");
			break;
		case 3:			
			if(default_mul_sign.contains("1005"))
				delims = "[x]";
			else if(default_mul_sign.contains("1006"))
				delims = "[*]";			
			splitInput = input.split(delims);
			Log.d(LOG_TAG, "Split Success");
			second_input = splitInput[1].toString();
			Log.d(LOG_TAG, "Split Transfer done");
			break;		
		case 4:			
			delims = "[/]";
			splitInput = input.split(delims);
			Log.d(LOG_TAG, "Split Success");
			second_input = splitInput[1].toString();
			Log.d(LOG_TAG, "Split Transfer done");
			break;
		}
		displayResult.setText(input);
		}

	private void OperatorFunction(int add2) {		
		switch(add2){
		case ADD:
			//break;
		case SUB:
			//break;
		case MUL:
			//break;
	    case DIV:
	    	//displayResult.setHint("Enter your 2nd number");
	    	// Passing the initial value to another string
	    	 first_input = input;    	 
	    	 displayResult.setText(input + type);
	    	/*
	    	if(!operator_type)
	    		input = input + type;
			operator_type = true;
			*/
	    	break;			
		}
	
}

	private class QuickAdapter extends PagerAdapter{	

		@Override
        public int getCount() {
                return VIEWS;
        }

    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate(android.view.ViewGroup)}.
     *
     * @param collection The containing View in which the page will be shown.
     * @param position The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.
     */
        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
        	LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);    
        	 int resId = 0;
            switch (position) {
            case 0:
                resId = R.layout.function_panel;
                break;
            case 1:          	
            	resId = R.layout.basic_panel;         	
                break;
            case 2:
                resId = R.layout.advance_panel;
                break;
            }
            View view = inflater.inflate(resId, null);
            ((ViewPager) collection).addView(view , 0);
            
            switch (position) {
            
            case 0:
            	square_root = (Button)findViewById(R.id.square_root);
            	square_root.setOnClickListener(KeypadClick);
            	
            	custom_root = (Button)findViewById(R.id.custom_root);
            	custom_root.setOnClickListener(KeypadClick);
            	
            	square_twice = (Button)findViewById(R.id.square_twice);
            	square_twice.setOnClickListener(KeypadClick);
            	
            	custom_square = (Button)findViewById(R.id.square_x);
            	custom_square.setOnClickListener(KeypadClick);
            	break;
            case 1:           	
            	
            	// Initilised Number Buttons
            	Button0 = (Button)findViewById(R.id.btn0);
            	Button0.setOnClickListener(KeypadClick);
            	
            	Button1 = (Button)findViewById(R.id.btn1);
            	Button1.setOnClickListener(KeypadClick);
            	
            	Button2 = (Button)findViewById(R.id.btn2);
            	Button2.setOnClickListener(KeypadClick);
            	
            	Button3 = (Button)findViewById(R.id.btn3);
            	Button3.setOnClickListener(KeypadClick);
            	
            	Button4 = (Button)findViewById(R.id.btn4);
            	Button4.setOnClickListener(KeypadClick);
            	
            	Button5 = (Button)findViewById(R.id.btn5);
            	Button5.setOnClickListener(KeypadClick);
            	
            	Button6 = (Button)findViewById(R.id.btn6);
            	Button6.setOnClickListener(KeypadClick);
            	
            	Button7 = (Button)findViewById(R.id.btn7);
            	Button7.setOnClickListener(KeypadClick);
            	
            	Button8 = (Button)findViewById(R.id.btn8);
            	Button8.setOnClickListener(KeypadClick);
            	
            	Button9 = (Button)findViewById(R.id.btn9);
            	Button9.setOnClickListener(KeypadClick);
            	
            	Equal = (Button)findViewById(R.id.equal);
        		Equal.setOnClickListener(KeypadClick);
            	
            	// Operator Buttons
            	
            	Operator_add = (Button)findViewById(R.id.operator_add);
            	Operator_add.setOnClickListener(KeypadClick);
            	
            	Operator_sub = (Button)findViewById(R.id.operator_sub);
            	Operator_sub.setOnClickListener(KeypadClick);
            	
            	Operator_mul = (Button)findViewById(R.id.operator_mul);
            	Operator_mul.setOnClickListener(KeypadClick);
            	
            	Operator_div = (Button)findViewById(R.id.operator_div);
            	Operator_div.setOnClickListener(KeypadClick);
            	
            	// Initialise Clearing Buttons
            	
            	all_clear = (Button)findViewById(R.id.btn_Clear);
            	all_clear.setOnClickListener(KeypadClick);           	
            	delete = (ImageButton)findViewById(R.id.btn_delete);
            	delete.setOnClickListener(KeypadClick);
            	delete.setOnLongClickListener(new OnLongClickListener(){

					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						BasicPanel(Continous_Del);
						return false;
					}
            		
            	});
            	
            	// Initialising Operator Buttons
            	
            	//InitButton.setOnClickListener(KeypadClick);
            	break;
            	
            case 2:
            	
            	sin = (Button)findViewById(R.id.sinx);
            	sin.setOnClickListener(KeypadClick);  
            	sin.setOnLongClickListener(LongClick);
            	
            	cos = (Button)findViewById(R.id.cosx);
            	cos.setOnClickListener(KeypadClick);
            	cos.setOnLongClickListener(LongClick);
            	
            	tan = (Button)findViewById(R.id.tan);
            	tan.setOnClickListener(KeypadClick);
            	tan.setOnLongClickListener(LongClick);
            	
            	sinh = (Button)findViewById(R.id.sinhx);
            	sinh.setOnClickListener(KeypadClick); 
            	//sinh.setOnLongClickListener(LongClick);
            	
            	cosh = (Button)findViewById(R.id.coshx);
            	cosh.setOnClickListener(KeypadClick);
            	//cosh.setOnLongClickListener(LongClick);
            	
            	tanh = (Button)findViewById(R.id.tanhx);
            	tanh.setOnClickListener(KeypadClick);
            	//tanh.setOnLongClickListener(LongClick);
            	
            	log10 = (Button)findViewById(R.id.log10);
            	log10.setOnClickListener(KeypadClick);
            	log10.setOnLongClickListener(LongClick);
            	
            	loge = (Button)findViewById(R.id.loge);
            	loge.setOnClickListener(KeypadClick);
            	loge.setOnLongClickListener(LongClick);
            	break;
                
                 /*
            case 3:
            	resId = R.layout.quick_end;
            	Button end = (Button) view.findViewById (R.id.button1);
                end.setOnClickListener (new View.OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}});
            	break; 
            	*/
            }
            
            return view;
            
        	/*
                TextView tv = new TextView(getBaseContext());
                tv.setText(getString(R.string.app_name) + position);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(30);
               
                collection.addView(tv,0);
               
                return tv;
                */
        }
        

    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(android.view.ViewGroup)}.
     *
     * @param collection The containing View from which the page will be removed.
     * @param position The page position to be removed.
     * @param view The same object that was returned by
     * {@link #instantiateItem(android.view.View, int)}.
     */
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }
        /**
         * * Determines whether a page View is associated with a specific key object
         * * as returned by instantiateItem(ViewGroup, int). This method is required
         * * for a PagerAdapter to function properly.
         * * @param view Page View to check for association with object
         * * @param object Object to check for association with view
         * * @return
         * */
        @Override
        public boolean isViewFromObject(View view, Object object) {
                return (view==object);
        }

       
    /**
     * Called when the a change in the shown pages has been completed.  At this
     * point you must ensure that all of the pages have actually been added or
     * removed from the container as appropriate.
     * @param arg0 The containing View which is displaying this adapter's
     * page views.
     */
        @Override
        public void finishUpdate(ViewGroup arg0) {}
       

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {}

        @Override
        public Parcelable saveState() {
                return null;
        }

        @Override
        public void startUpdate(ViewGroup arg0) {}

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
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
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
	
	public void Access(int access){
 		switch(access){
 		case 0:
 			AlertDialog.Builder accessibility = new AlertDialog.Builder(this);
 			accessibility.setItems(R.array.calc_query, new DialogInterface.OnClickListener() {
 	    	               public void onClick(DialogInterface dialog, int which) {
 	    	            	   switch(which)
 	    	            	   {
 	    	            	   case copy:
 	    	            		   PerformAccess(copy);
 	    	            		   break;
 	    	            	   case cut:
 	    	            		   PerformAccess(cut);
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
 		String check = ans.getText().toString();
			ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
 		switch(k){
 		case copy:	 			
 			clipBoard.setText(check);
 			Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
 			break;
 		case cut:
 			clipBoard.setText(check);
 			ans.setText(null);
 			Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
 			break;
 		case paste:
 			String textToPaste = null;
 			textToPaste = clipBoard.toString();
 			displayResult.setText(textToPaste);
 			break;
 		case clear:
 			// Clearing inputs
 			displayResult.setText(null);
 			ans.setText(null);
 			Toast.makeText(getBaseContext(), R.string.toast_input_cleared, Toast.LENGTH_SHORT).show();
 			break;
 		}
 	}

}

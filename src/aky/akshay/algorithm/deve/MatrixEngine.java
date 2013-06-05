package aky.akshay.algorithm.deve;

import java.text.NumberFormat;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
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

@SuppressWarnings("deprecation")
public class MatrixEngine extends Activity{
	
	/*Note : We know Changing ID from layout needs ID to be change in code
	 * So, change where used.
	 * The, problem here is it is used in EACH CODE of MATRIX FUNCTION 
	 * */
	
	EditText ent_matA,ent_matB;
	// Integer constants for Function
	public static final int Add = 0;
	public static final int Sub = 1;
	public static final int Mul = 2;
	public static final int AA = 3;
	public static final int BB = 4;
	public static final int Solve = 5;
	public static final int Rank = 6;
	public static final int Trace = 7;
	public static final int Det = 8;
	public static final int Transpose = 9;
	public static final int Inverse = 10;
	public static final int EigenValue = 11;
	public static final int EigenVector = 12;
	
	//Accessibility constants
	public static final int copy = 0;
	public static final int swap = 1;
	public static final int clear = 2;
	
	// Validation
	public static final int MATRIX_RESULT = 0;
	public static final int DOUBLE_RESULT = 1;
	public static final int INT_RESULT = 2;
	public static final int COMPLEX_ARRAY_RESULT = 3;
	
	// Dialog
	public static final int INVALID_MATRIX = 0;
	public static final int DIALOG_DIMENSION = 1;
	public static final int DIALOG_RESULT = 2;
	public static final int INPUT_DIALOG = 3;

	public int count = 99;
	String SwapA,SwapB,ans,errMsg,type = "";	
	
	boolean parsed,valid;
	Matrix mtxAns = null;
	double dblAns = 0.0;
	int intAns = -1;
	double[] reAns = null;
	double[] imAns = null;

	double[][] dblTmpAns;
	// Finding Answer Type
	int ansType = -1;

	int row;
	int col;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefControl();
		setContentView(R.layout.activity_mat_jama);
		ActivityAnimation();   	
        IdControl();
        setupActionBar();
	}

	@SuppressLint("NewApi")
	private void IdControl() {
		// TODO Auto-generated method stub
		ent_matA = (EditText)findViewById(R.id.MatA);
		ent_matB = (EditText)findViewById(R.id.MatB);
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

	
	private Matrix getMatrix(int id) {

		EditText matrix = (EditText) findViewById(id);
		String strMatrix = matrix.getText().toString();
		double[][] dblMatrix = MatrixParser.parse(strMatrix);
		if (dblMatrix == null) {
			return null;
		} else {
			Matrix X = new Matrix(dblMatrix);
			return X;
		}
	}
	
	private void Ans(int i){
		valid = false;
		parsed = false;		
		ans = "No result";
		errMsg = "Unknown error";
		Matrix A;
		Matrix B;		
		// switch between different button pressed
		switch(i){
		
		case Add:// A + B
			ansType = MATRIX_RESULT;
			A = getMatrix(R.id.MatA);
			B = getMatrix(R.id.MatB);
			if (A != null && B != null) {
				parsed = true;
				try {
					mtxAns = A.plus(B);
					valid = true;
				} catch (Exception e) {
					errMsg = e.getMessage();
					}
				}
			type = "Addition of A & B : \n\n";
			break;
			
		case Sub:// A - B
			ansType = MATRIX_RESULT;
			A = getMatrix(R.id.MatA);
			B = getMatrix(R.id.MatB);
			if (A != null && B != null) {
				parsed = true;
				try {
					mtxAns = A.minus(B);
					valid = true;
				} catch (Exception e) {
					errMsg = e.getMessage();
					}
				}
			type = "Subtraction of A & B : \n\n";
			break;
			
		case Mul:// A x B
			ansType = MATRIX_RESULT;
			A = getMatrix(R.id.MatA);
			B = getMatrix(R.id.MatB);
			if (A != null && B != null) {
				parsed = true;
				try {
					mtxAns = A.times(B);
					valid = true;
					} catch (Exception e) {
						errMsg = e.getMessage();
						}
				}
			type = "Multiplication of A & B : \n\n";
			break;
			
		case AA:// A x A
			ansType = MATRIX_RESULT;
			A = getMatrix(R.id.MatA);
			if (A != null) {
				parsed = true;
				try {
					mtxAns = A.times(A);
					valid = true;
					} catch (Exception e) {
						errMsg = e.getMessage();
						}
				}
			type = "Square of A : \n\n";
			break;
			
		case BB:// B x B
			ansType = MATRIX_RESULT;
			B = getMatrix(R.id.MatB);
			if (B != null) {
				parsed = true;
				try {
					mtxAns = B.times(B);
					valid = true;
					} catch (Exception e) {
						errMsg = e.getMessage();
						}
				}
			type = "Square of B : \n\n";
			break;
			
		case Solve:
			ansType = MATRIX_RESULT;
			A = getMatrix(R.id.MatA);
			B = getMatrix(R.id.MatB);
			if (A != null && B != null) {
				parsed = true;
				try {
					mtxAns = A.solve(B);
					valid = true;
				} catch (Exception e) {
					errMsg = e.getMessage();
				}
			}
			type = "Matrix X : \n\n";
			break;
			
		case Rank:// Rank
			ansType = INT_RESULT;
			A = getMatrix(R.id.MatA);
			if (A != null) {
				parsed = true;
				try {
					intAns = A.rank();
					valid = true;
					} catch (Exception e) {
						errMsg = e.getMessage();
						}
				}
			type = "Rank of A = ";
			break;
			
		case Trace:// Trace
			ansType = DOUBLE_RESULT;
			A = getMatrix(R.id.MatA);
			if (A != null) {
				parsed = true;
				try {
					dblAns = A.trace();
					valid = true;
					} catch (Exception e) {
						errMsg = e.getMessage();
						}
				}
			type = "Trace of A = ";
			break;
				
			case Det:// Determinant
				ansType = DOUBLE_RESULT;
				A = getMatrix(R.id.MatA);
				if (A != null) {
					parsed = true;
					if (A.getColumnDimension() == A.getRowDimension()) {
						try {
							dblAns = A.det();
							valid = true;
							} catch (Exception e) {
								errMsg = e.getMessage();
								}
						}
					else
						errMsg = getString(R.string.not_square);
					}
				type = "Determinant of A = ";
				break;
				
			case Transpose:// Transpose
				ansType = MATRIX_RESULT;
				A = getMatrix(R.id.MatA);
				if (A != null) {
					parsed = true;
					try {
						mtxAns = A.transpose();
						valid = true;
						} catch (Exception e) {
							errMsg = e.getMessage();
							}
					}
				type = "Transpose of A : \n\n";
				break;
				
			case Inverse: //Inverse
				ansType = MATRIX_RESULT;
				A = getMatrix(R.id.MatA);
				if (A != null) {
					parsed = true;
					try {
						mtxAns = A.inverse();
						valid = true;
						} catch (Exception e) {
							errMsg = e.getMessage();
							}
					}
				type = "Inverse of A : \n\n";
				break;
				
				/*
				 * SPECIAL CASE, manually implemented errMsg because it 
				 * wasn't giving the correct exception message
				 * */			
			case EigenValue: //Eigen Values
				ansType = COMPLEX_ARRAY_RESULT;
				A = getMatrix(R.id.MatA);
				if (A != null) {
					parsed = true;
					if (A.getColumnDimension() == A.getRowDimension()) {
						try {
							EigenvalueDecomposition tmpD = A.eig();
							reAns = tmpD.getRealEigenvalues();
							imAns = tmpD.getImagEigenvalues();
							valid = true;
							} catch (Exception e) {
								errMsg = e.getMessage();
								}
						} else {
							errMsg = getString(R.string.not_square);
							}
					type = "Eigen Value of A : \n\n";
					}
				break;
				
			case EigenVector:
				ansType = MATRIX_RESULT;
				A = getMatrix(R.id.MatA);
				if (A != null) {
					parsed = true;
					if (A.getColumnDimension() == A.getRowDimension()) {
						try {
							EigenvalueDecomposition tmpV = A.eig();
							mtxAns = tmpV.getV();
							valid = true;
						} catch (Exception e) {
							errMsg = e.getMessage();
						}
					} else {
						errMsg = getString(R.string.not_square);
					}
				}
				type = "Eigen Vector of A : \n\n";
				break;
				}
		// Validating the matrix
		Validation(valid);
	}
		/*********************Validation & calling display dialog
		 * @return **********************/
		public void Validation (boolean valid){
		if (valid) {
			// Using NumberFormat to have more control over formatted strings
			NumberFormat nf = NumberFormat.getInstance();
			SharedPreferences numberformat = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			String format = numberformat.getString("maxFraction", "4");
			int maxformat = Integer.parseInt(format);
			nf.setMaximumFractionDigits(maxformat);
			nf.setMinimumFractionDigits(0);
			if (ansType == MATRIX_RESULT) {
				dblTmpAns = mtxAns.getArrayCopy();
				row = mtxAns.getRowDimension();
				col = mtxAns.getColumnDimension();
				ans = MatrixParser.dblToStr(dblTmpAns, row, col);
				displayDialog(DIALOG_RESULT);
			} else if (ansType == DOUBLE_RESULT) {
				ans = nf.format(dblAns);
				displayDialog(DIALOG_RESULT);
			} else if (ansType == INT_RESULT) {
				ans = nf.format(intAns);
				displayDialog(DIALOG_RESULT);
			} else if (ansType == COMPLEX_ARRAY_RESULT){
				ans = MatrixParser.dispComplex(reAns, imAns);
				displayDialog(DIALOG_RESULT);
			}
		}
			else {
				if (!parsed){
					errMsg = "Matrix couldn't parsed.\nInvalid Matrix";
					displayDialog(INVALID_MATRIX);					
				}
				else
					displayDialog(DIALOG_DIMENSION);
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
	  			String checkA = ent_matA.getText().toString();
	  			String checkB = ent_matB.getText().toString();  
	  			ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
	  			clipBoard.setText(checkA + checkB);
	  			Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
	  			break;
	  		case swap:
	  			Toast.makeText(getBaseContext(), "Swaped Matrix A & B",Toast.LENGTH_LONG).show();
				SwapA = ent_matA.getText().toString();
				SwapB = ent_matA.getText().toString();
				ent_matA.setText(SwapB);
				ent_matA.setText(SwapA);
	  			break;
	  		case clear:
	  			// Clearing inputs
	  			ent_matA.setText(null);
	  			ent_matB.setText(null);
	  			Toast.makeText(getBaseContext(), R.string.toast_input_cleared, Toast.LENGTH_SHORT).show();
	  			break;
	  		}
	  	}	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void setupActionBar() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	
	 public void displayDialog(int id) { 
	        switch (id){
	        case INVALID_MATRIX:
	        	new AlertDialog.Builder(MatrixEngine.this)
	        	.setIcon(android.R.drawable.ic_dialog_info) 
	            .setTitle("Invalid Matrix") 
	            .setMessage(errMsg) 	            
	            .setNeutralButton(android.R.string.ok, null)
	            .show();
	        	 break;
	        case DIALOG_DIMENSION:
	        	new AlertDialog.Builder(MatrixEngine.this)
	        	.setIcon(android.R.drawable.ic_dialog_info) 
	            .setTitle("Dimension Error") 
	            .setMessage(errMsg) 	            
	            .setNeutralButton(android.R.string.ok, null)
	            .show();
	        	 break;
	       case DIALOG_RESULT:  
	        	new AlertDialog.Builder(MatrixEngine.this)
	        	 .setIcon(android.R.drawable.ic_dialog_info)
	        	 .setTitle("Result : ")
	             .setMessage(type + ans)
	             .setPositiveButton(android.R.string.copy, new DialogInterface.OnClickListener()
	             {
	            	 public void onClick(DialogInterface dialog, int whichButton)
	            	 {
	            		 ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
	            		 clipBoard.setText(ans);
	            		 Toast.makeText(getApplicationContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
	            		 }
	            	 })
	            	 .setNeutralButton(android.R.string.ok, null)
	            	 .show();
	        	break;	        	
	       case INPUT_DIALOG:
				 AlertDialog.Builder alert = new AlertDialog.Builder(this);
				 alert.setTitle("Choose what you wanna calculate");
				 alert.setItems(R.array.matrix_function_query, new DialogInterface.OnClickListener()
				    {
						@Override
						public void onClick(DialogInterface dialog, int item) {
							// TODO Auto-generated method stub							
							switch(item)
							{
							case Add:
								count = Add;						
								break;
							case Sub:
								count = Sub;
								break;		
							case Mul:							
								count = Mul;
								break;
							case AA:							
								count = AA;
								break;	
							case BB:							
								count = BB;
								break;
							case Solve:
								count = Solve;
								break;
							case Rank:							
								count = Rank;
								break;
							case Trace:							
								count = Trace;
								break;
							case Det:							
								count = Det;
								break;
							case Transpose:							
								count = Transpose;
								break;	
							case Inverse:							
								count = Inverse;
								break;
							case EigenValue:
								count = EigenValue;
								break;
							case EigenVector:
								count = EigenVector;
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
	 
	 @Override
	    public boolean onTouchEvent(MotionEvent event) {
	     // TODO Auto-generated method stub
	       return gestureDetector.onTouchEvent(event);
	    }

	    SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener(){


	     @Override
	     public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY){
	    	 
	      // As paiego pointed out, it's better to use density-aware measurements.
	      DisplayMetrics dm = getResources().getDisplayMetrics();
	      float SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * dm.densityDpi / 160.0f + 0.5);	  
	      float SWIPE_DIVEDER = (float) 1.5;
	      SWIPE_THRESHOLD_VELOCITY = SWIPE_THRESHOLD_VELOCITY / SWIPE_DIVEDER;
	      
	      if((e2.getY() - e1.getY()) > SWIPE_THRESHOLD_VELOCITY) // Swipe Down
	      {
	    	  displayDialog(INPUT_DIALOG);
	    	  Toast.makeText(getBaseContext(), R.string.toast_query_cleared, Toast.LENGTH_SHORT).show();	    	  
	      }
	      else if((e1.getY() - e2.getY()) > SWIPE_THRESHOLD_VELOCITY) //Swipe Up
	  			{
	    	  switch(count){    		  
	    	  case Add:
	    		  Ans(Add);
	    		  break;
	    	  case Sub:
	    		  Ans(Sub);
	    		  break;
	    	  case Mul:
	    		  Ans(Mul);
	    		  break;
	    	  case AA:
	    		  Ans(AA);
	    		  break;
	    	  case BB:
	    		  Ans(BB);
	    		  break;
	    	  case Solve:
	    		  Ans(Solve);
	    		  break;
	    	  case Rank:
	    		  Ans(Rank);
	    		  break;
	    	  case Trace:
	    		  Ans(Trace);
	    		  break;
	    	  case Det:
	    		  Ans(Det);
	    		  break;
	    	  case Transpose:
	    		  Ans(Transpose);
	    		  break;
	    	  case Inverse:
	    		  Ans(Inverse);
	    		  break;
	    	  case EigenValue:
	    		  Ans(EigenValue);
	    		  break;
	    	  case EigenVector:
	    		  Ans(EigenVector);
	    		  break;
	    	  case 99:
	    		  displayDialog(INPUT_DIALOG);
	    		  break;
	    		  }}
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
	 
	 private void ActivityAnimation() {
			// TODO Auto-generated method stub
	    	SharedPreferences effect_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	    	boolean fade = effect_pref.getBoolean("fade", false);
	    	if(fade==true)
	    		overridePendingTransition(R.anim.slider_in, R.anim.slider_out);
		}

}

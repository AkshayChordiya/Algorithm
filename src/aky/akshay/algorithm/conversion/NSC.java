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
public class NSC extends Activity{
	
	// Declaring EditText
	EditText Dec;
	// Declaring List View
	ListView entval,conval;
	// Skip list detector
	public int skip_list=0;
	// List elements
	String entvalist[] = { "Decimal" ,"Bin" ,"Octal" , "Hexa-Decimal" };
	Boolean dec,bin,oct,hex;
	int x;
	// For storing result
	String result = "";
	// For storing type
	String type = "";
	
	boolean parseFail = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefControl(); // Design Management is defined in this method
        setContentView(R.layout.activity_nsc);
        ActivityAnimation();   	
        initControls();
        setupActionBar();
    }

    private void prefControl() {
		// TODO Auto-generated method stub
    	SharedPreferences theme = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	    String theme_value = theme.getString("theme", "100");
	    if(theme_value.contains("110"))
	    {
	    	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	    		setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
	    	else
	    		setTheme(android.R.style.Theme_Light);
	    }
	    else
	    {
	    	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	    		setTheme(android.R.style.Theme_Holo);
	    	else
	    		setTheme(android.R.style.Theme_Black);
	    }
	    SharedPreferences pot_full = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean full_pot = pot_full.getBoolean("portrait", false);
        if(full_pot == true)
       	//For getting full screen
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	private void initControls() {
		// TODO Auto-generated method stub
    	Dec = (EditText)findViewById(R.id.editText1);
        entval = (ListView)findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, entvalist);
        entval.setAdapter(adapter);
        entval.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            	switch(position){
            	case 0:
            		//Decimal
            		dec = true;
            		bin = false;
            		oct = false; 
            		hex = false;
            		break;
            	case 1:
            		//Binary
            		bin = true;
            		dec = false;
            		oct = false; 
            		hex = false;
            		break;
            	case 2:
            		//Octal
            		oct = true;
            		bin = false;
            		dec = false;
            		hex = false;
            		break;
            	case 3:
            		//HexaDecimal
            		hex = true;
            		oct = false;
            		bin = false;
            		dec = false;            		
            		break;
            	}
            	skip_list = 1;
            }
        });
        conval = (ListView)findViewById(R.id.listView2);
        ArrayAdapter<String> covertTo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, entvalist);
        conval.setAdapter(covertTo);  
        conval.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            	if(skip_list==0)
            		displaydialog(2);
            	else{
            	switch(position){
            	case 0:
            		//Decimal
            		if(dec == true)
            			converter(0);
            		if(bin == true)
            			converter(1);
            		if(oct == true)
            			converter(2);
            		if(hex == true)
            			converter(3);
            		break;
            	case 1:
            		//Binary
            		if(dec == true)
            			converter(4);
            		if(bin == true)
            			converter(5);
            		if(oct == true)
            			converter(6);
            		if(hex == true)
            			converter(7);
            	break;
            	case 2:
            		//Octal
            		if(dec == true)
            			converter(8);
            		if(bin == true)
            			converter(9);
            		if(oct == true)
            			converter(10);
            		if(hex == true)
            			converter(11);
            	break;
            	case 3:
            		//HexaDecimal
            		if(dec == true)
            			converter(12);
            		if(bin == true)
            			converter(13);
            		if(oct == true)
            			converter(14);
            		if(hex == true)
            			converter(15);
            	break;
            	}
            	}          
            }
        });
    }
    
    protected void converter(int i) {
		// TODO Auto-generated method stub
    	try{
    		x = Integer.parseInt(Dec.getText().toString());
    		}catch(Exception parse){
    			parseFail = true;
    			displaydialog(0);
    		}
    	if(!parseFail){
    	switch(i){
    	case 0:
    		//Decimal to Decimal
    		type =  "Decimal to Decimal :";
    		displaydialog(1);
    		break;
    	case 1:
    		//Binary to Decimal
    		type =  "Binary to Decimal :";
    		//romDecimalToOtherBase(10,result);
    		for(int base = 10; i < 10; i++)
    			GetBase(base,x);  		
    		displaydialog(1);
    		//convert(0);   		
    		break;
    	case 2:
    		//Octal to Decimal
    		type = " Octal to Decimal :";
    		displaydialog(3);
    		break;
    	case 3:
    		//HexaDecimal to Decimal
    		type = " Hexa-decimal to Decimal :";
    		displaydialog(3);
    		break;
    	case 4:    		
    		//Decimal to Binary
    		type = " Decimal to Binary :";
    		result = Integer.toBinaryString(x);
    		displaydialog(1);
    		break;
    	case 5:
    		//Binary to Binary
    		type = " Binary to Binary :";
    		displaydialog(3);
    		break;
    	case 6:
    		//Octal to Binary
    		type = " Octal to Binary :";
    		displaydialog(3);
    		break;
    	case 7:
    		//HexaDecimal to Binary
    		type = " Hexa-decimal to Decimal :";
    		displaydialog(3);
    		break;
    	case 8:
    		//Decimal to Octal
    		type = " Decimal to Octal :";
    		result = Integer.toOctalString(x);
    		displaydialog(1);
    		break;
    	case 9:
    		//Binary to Octal
    		type = " Binary to Octal :";
    		result = Integer.toBinaryString(x);
    		x = Integer.parseInt(result);
    		result = Integer.toOctalString(x);
    		displaydialog(1);
    		break;
    	case 10:
    		//Octal to Octal
    		type = " Octal to Octal :";
    		displaydialog(3);
    		break;
    	case 11:
    		//HexaDecimal to Octal
    		type = " Hexa-decimal to Octal :";
    		displaydialog(3);
    		break;
    	case 12:
    		//Decimal to HexaDecimal
    		type = " Decimal to Hexa-decimal :";
    		result = Integer.toHexString(x);
    		displaydialog(1);
    		break;
    	case 13:
    		//Binary to HexaDecimal
    		type = " Binary to Hexa-decimal :";
    		displaydialog(3);
    		break;
    	case 14:
    		//Octal to HexaDecimal
    		type = " Octal to Hexa-decimal :";
    		displaydialog(3);
    		break;
    	case 15:
    		//HexaDecimal to HexaDecimal
    		type = " Hexa-decimal to Hexa-decimal :";
    		displaydialog(3);
    		break;
    		}
    	} 
    	parseFail = false;
    	}

    public int GetBase(int iBase, int iNumber){
            if(iBase < 2 || iBase > 10)
            	return 0;

            int iResult = iNumber % iBase;
            int iMultiplier = 10;
            
            while((iNumber /= iBase) > 0)
            {       
                    iResult = (iNumber % iBase) * iMultiplier + iResult;
                    iMultiplier *= 10;
            }
            result = Integer.toString(iResult);
            return iResult;
    }

	// Code for displaying respective dialogs   
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
			.setMessage(type + "\n\n" + result)
			.setNeutralButton(android.R.string.ok, null)
			.setPositiveButton("Copy",new DialogInterface.OnClickListener()
				{
				@Override
				public void onClick(DialogInterface dialoginterface, int i)
				{
			        ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					clipBoard.setText(Integer.toString(x));
					Toast.makeText(getBaseContext(), R.string.clipboard, Toast.LENGTH_SHORT).show();
				}
				})
			.show();
			 break;
		 case 2:
			 new AlertDialog.Builder(this)
				.setTitle("Base Error !")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(R.string.base_unit_list_error)
				.setNeutralButton(android.R.string.ok, null)
				.show();
	         break;
		 case 3:
			 //Under Constuction case
			 new AlertDialog.Builder(this)
				.setTitle(R.string.construction_error_title)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(R.string.construction_error_summary)
				.setNeutralButton(android.R.string.ok, null)
				.show();
		 }
		 else if(toast.contains("20"))
			 switch(i){
			 case 0:
				 Toast.makeText(this, R.string.error_summary, Toast.LENGTH_SHORT).show();
		         break;
			 case 1:
				 Toast.makeText(this,type + result, Toast.LENGTH_LONG).show();
				 break;
			 case 2:
				 Toast.makeText(this,R.string.base_unit_list_error, Toast.LENGTH_LONG).show();
				 break;
			 case 3:
				 //Under Constuction case
				 new AlertDialog.Builder(this)
					.setTitle(R.string.construction_error_title)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setMessage(R.string.construction_error_summary)
					.setNeutralButton(android.R.string.ok, null)
					.show();
		 }
		 else
			 switch(i){
			 case 0:
				 Crouton.makeText(this, R.string.error_summary , Style.INFO).show();
		         break;
			 case 1:
				 Crouton.makeText(this, type + result + Integer.toString(x), Style.CONFIRM).show();
		         break;
			 case 2:
				 Crouton.makeText(this,R.string.base_unit_list_error, Style.INFO).show();
				 break;
			 case 3:
				 //Under Constuction case
				 new AlertDialog.Builder(this)
					.setTitle(R.string.construction_error_title)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setMessage(R.string.construction_error_summary)
					.setNeutralButton(android.R.string.ok, null)
					.show();
				 break;
			 }
	}
      
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  	public void setupActionBar() {
  		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
  			// Show the Up button in the action bar.
  			getActionBar().setDisplayHomeAsUpEnabled(true);
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

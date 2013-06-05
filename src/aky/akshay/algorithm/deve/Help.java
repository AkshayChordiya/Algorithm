package aky.akshay.algorithm.deve;

import java.io.InputStream;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Help extends Activity {
	
	String help_list[] = { "Quick Start Guide", "Learn about Gestures", "What are Tools"
			,"Info about BMI" ,"Frequently asked Questions", "Report an bug/error", "Email Support"};
	ListView help;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefControl();
        setContentView(R.layout.activity_help);
        ActivityAnimation();   	
        IdControl();
        setupActionBar();
    }

    private void IdControl() {
		// TODO Auto-generated method stub
    	help = (ListView)findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, help_list);
        help.setAdapter(adapter);
        help.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            	switch(position)
            	{
            	case 0:
            		finish();
            		Intent quickStart = new Intent(Help.this , QuickStart.class);
            		startActivity(quickStart);
            		break;
            	case 1:
            		//Launch Gesture Help dialog
            		InputStream isF = getApplicationContext().getResources().openRawResource(R.raw.gesture_help);
        	        if (isF == null) {
        	            finish();
        	            return;
        	        }

        	        try {
        	            // Read the change log
        	            StringBuilder sb = new StringBuilder();
        	            int read = 0;
        	            byte[] data = new byte[512];
        	            while ((read = isF.read(data, 0, 512)) != -1) {
        	                sb.append(new String(data, 0, read));
        	            }

        	            // Show a dialog
        	            new AlertDialog.Builder(Help.this)
        				.setMessage(sb.toString())
        				.show();

        	        } catch (Exception e) {
        	            finish();

        	        } finally {
        	            try {
        	                isF.close();
        	            } catch (Exception e) {/**NON BLOCK**/}
        	        } 
            		break;
            	case 2:
            		//Launch Tool Help dialog
            		InputStream HelpLine = getApplicationContext().getResources().openRawResource(R.raw.tool_help);
        	        if (HelpLine == null) {
        	            finish();
        	            return;
        	        }

        	        try {
        	            // Read the change log
        	            StringBuilder sb = new StringBuilder();
        	            int read = 0;
        	            byte[] data = new byte[512];
        	            while ((read = HelpLine.read(data, 0, 512)) != -1) {
        	                sb.append(new String(data, 0, read));
        	            }

        	            // Show a dialog
        	            new AlertDialog.Builder(Help.this)
        				.setMessage(sb.toString())
        				.show();

        	        } catch (Exception e) {
        	            finish();

        	        } finally {
        	            try {
        	            	HelpLine.close();
        	            } catch (Exception e) {/**NON BLOCK**/}
        	        } 
            		break;
            	case 3:
            		 new AlertDialog.Builder(Help.this)
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
            		break;
            	case 4:
            		//Launch FAQ dialog
            		InputStream faq = getApplicationContext().getResources().openRawResource(R.raw.faq);
        	        if (faq == null) {
        	            finish();
        	            return;
        	        }

        	        try {
        	            // Read the change log
        	            StringBuilder sb = new StringBuilder();
        	            int read = 0;
        	            byte[] data = new byte[512];
        	            while ((read = faq.read(data, 0, 512)) != -1) {
        	                sb.append(new String(data, 0, read));
        	            }

        	            // Show a dialog
        	            new AlertDialog.Builder(Help.this)
        				.setMessage(sb.toString())
        				.show();

        	        } catch (Exception e) {
        	            finish();

        	        } finally {
        	            try {
        	            	faq.close();
        	            } catch (Exception e) {/**NON BLOCK**/}
        	        } 
            		break;
                case 5:
                	new AlertDialog.Builder(Help.this)
                	.setTitle("Report Error")
                	.setMessage("No Terms & Condition or any other policy stuff is being applied" +
                			"\nSimple, Report of bugs & issues or problem facing" + "\nThanks for reporting error")
                	.setPositiveButton("Let's Help", new DialogInterface.OnClickListener(){
    					@Override
    					public void onClick(DialogInterface dialoginterface, int i){
    						Intent Manual_Report = new Intent(Intent.ACTION_VIEW,Uri.parse("https://docs.google.com/spreadsheet/viewform?formkey=dGpqNUxSS3RZcXVyMmExcm1pMl9SX2c6MQ"));
    						startActivity(Manual_Report);
    					}
    					})
    					.setNegativeButton(android.R.string.cancel, null)
    					.show();
                	break;
                case 6:
                	
                	/* Create the Email Intent */
        			Intent emailIntent = new Intent(Intent.ACTION_SEND);

        			/* Fill it with Data */
        			emailIntent.setType("plain/text");
        			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.email_summary)});
        			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Regarding " + getString(R.string.app_name)
        					+ getString(R.string.version));
        			//emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

        			/* Send it off to the Activity-Chooser */
        			startActivity(Intent.createChooser(emailIntent, "Send mail"));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prefs, menu);
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

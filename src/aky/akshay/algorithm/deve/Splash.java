package aky.akshay.algorithm.deve;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity {
	
	MediaPlayer splashmusic;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Default animation so that app loads beautifully
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        //Loads Activity
        setContentView(R.layout.activity_splash);
        
        // Getting the desired music file
        splashmusic = MediaPlayer.create(this, R.raw.splash);
        // Getting preference about the music is on
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());      
        final boolean music = getPrefs.getBoolean("music", false);
        
        // Getting preference about the timer set for splash screen
        SharedPreferences splash_time = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String splash_timer = splash_time.getString("splash", "0");
        //Timer which on ending takes to next phase / activity
        Thread timer = new Thread() {
			@Override
			public void run(){
				try{
					// Checking splash screen timer set
					if(splash_timer.contentEquals("0")){
						if(music==true)
				        	splashmusic.start(); 
						// Timer is 2500 millisecond i.e 2.5s
						sleep(2500);
						splashmusic.stop();
					}
					else if(splash_timer.contentEquals("5")){
						if(music==true)
							splashmusic.start(); 
						// Timer is 1500 millisecond i.e 1.5s
						sleep(1500);
						splashmusic.stop();
					}						
				}
				catch(InterruptedException timer){
					timer.printStackTrace();
			}finally{
				finish();
				Intent time_off = new Intent(Splash.this , Algorithm.class);
		    	//time_off.setClassName("aky.akshay.algorithm.deve", "aky.akshay.algorithm.deve.Algorithm");
		    	startActivity(time_off);
			}
		}
		};
		timer.start();
		}
    
    // Called at the end of the active lifetime.
    @Override
    public void onPause(){
    	// Stopping the music on ending this activity
    	splashmusic.stop();
    	// Suspend UI updates, threads, or CPU intensive processes
    	// that don’t need to be updated when the Activity isn’t
    	// the active foreground activity.
    	super.onPause();
    	}
    
    // Called at the end of the visible lifetime.
    @Override
    public void onStop(){
    	// Stopping the music on ending this activity
    	splashmusic.stop();
 		// Suspend remaining UI updates, threads, or processing
    	// that aren’t required when the Activity isn’t visible.
    	// Persist all edits or state changes
    	// as after this call the process is likely to be killed.
    	super.onStop();
    	}
    
    @Override
	public void onBackPressed() {
    	// TODO Auto-generated method stub
    	System.exit(0); 	
    }
    
    
}


package com.example.spacecatz;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        MediaPlayer mpSplash = MediaPlayer.create(this, R.raw.bark);
        mpSplash.start();
        
        Thread splashTimer = new Thread() {
        	public void run() {
        		try {
        			int splashTimer = 0;
        			while (splashTimer < 3000){
        				sleep(100);
        				splashTimer = splashTimer + 100;
        			}
        			
        			startActivity(new Intent("com.example.spacecatz.CLEARSCREEN"));
        		} catch (Exception e) {
					e.printStackTrace();
				}
        		finally{
        			finish();
        		}
        	
        	}
        };
        splashTimer.start();
    }
}

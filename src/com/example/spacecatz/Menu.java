package com.example.spacecatz;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
//import android.content.Intent;


public class Menu extends Activity{
	
	boolean _gamevolume = true; //decides whether volume should be turned off onPause
	boolean _volumeon = false;
	MediaPlayer bgm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bgm = MediaPlayer.create(this, R.raw.music);
		bgm.setLooping(true);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (_gamevolume){
			bgm.stop();
			bgm.release();
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		_gamevolume = true;
		if (!_volumeon){
        	bgm.start();
        	_volumeon = true;
        }
	}

	//Starts game
	public void startgame (View view) {
		_gamevolume = false;
		finish();
		startActivity(new Intent("com.example.spacecatz.STARTGAME"));
	}
	
	
	// Turns volume on and off
	public void switchvolume (View view) {
		if (_volumeon){
			bgm.pause();
			_volumeon = false;
		}
		else {
			bgm.start();
			_volumeon = true;
		}
	}
	
	// Quit application
	public void quit (View view) {
		finish();
		System.exit(0);
	}

	
	
}

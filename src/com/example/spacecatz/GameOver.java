package com.example.spacecatz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over);
		
		Intent i = getIntent();
		String score = i.getStringExtra("score");
		String message = "Your Score Is: " + score;
		
		//*** Create the Text View
        TextView textView = new TextView(this);
        textView = (TextView)findViewById(R.id.scoretv);
        textView.setTextSize(30);
        textView.setText(message);	
	}
	
	public void backtoMenu(View view){
		finish();
		startActivity(new Intent("com.example.spacecatz.CLEARSCREEN"));
	}
}

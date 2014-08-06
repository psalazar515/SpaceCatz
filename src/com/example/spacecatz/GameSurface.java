package com.example.spacecatz;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameSurface extends Activity implements OnTouchListener{
	
	SoundPool sp;
	SoundPool sp2;
	myGameSurface sView;
	float sx, sy, newx, newy, bx, by;//ship x and ship y. new x and new y (on touch) bullet x
	float d1x = 50;
	float d2x = 400;
	float d3x = 250;//coordinates for the dogs
	float d1y = -150;
	float d2y = -450;
	float d3y = -700;
	Bitmap Ship;
	boolean setsy = true; // to set initial sy
	boolean shoot = false;
	boolean gameover = false;
	boolean changespeed = true;
	int pew = 0;
	int fart = 0;
	int lives = 1;
	int score = 0;
	int speed = 4;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sView = new myGameSurface(this);
		sView.setOnTouchListener(this);
		sx = 0;
		bx = 0;
		by = 0;
		sy = 0;
		setContentView(sView);
		sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		sp2 = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		pew = sp.load(this, R.raw.pew, 1);
		fart = sp2.load(this, R.raw.fart,1);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sView.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		sView.resume();
	}

	public boolean onTouch(View view, MotionEvent event) {
		
	/*	try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 	*/
		
		newx = event.getX();
		newy = event.getY();
		
		final int action  = event.getAction();
		
		switch (action){
		case MotionEvent.ACTION_UP:
			if(!shoot && pew != 0){
				shoot = true;
				sp.play(pew, 1, 1, 0, 0, 1);
			}
			break;	
		}
		return true;
	}
		
	//**************************************************
	//**************************************************
	
	public class myGameSurface extends SurfaceView implements Runnable {
		
		SurfaceHolder sHolder;
		Thread sThread = null;
		boolean isRunning = false;

		public myGameSurface(Context context) {
			super(context);
			sHolder = getHolder();
		}
		
		public void pause(){
			isRunning = false;
			while(true){
				try {
					sThread.join();	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			sThread = null;
		}
		
		public void resume(){
			isRunning = true;
			sThread = new Thread(this);
			sThread.start();
		}
		
		public boolean shot(int y, int maxy){//checks if the dogs have left the screen
			if (y > maxy) return true;
			else return false;
		}
		
		public void run() {
			
			Bitmap BG = BitmapFactory.decodeResource(getResources(), R.drawable.game_image2);
			Bitmap Ship = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
			Bitmap Bullet = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
			Bitmap Dog1 = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
			Bitmap Dog2 = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
			Bitmap Dog3 = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
			
			Random generator = new Random();
			
			while(isRunning){
				if(!sHolder.getSurface().isValid()) continue;
	
				Canvas canvas = sHolder.lockCanvas();
				
				if (setsy){
					sy = canvas.getHeight() - 100;
					sx = canvas.getWidth()/2;
					setsy = false;
				}
				canvas.drawBitmap(BG, 0, 0, null);
				
				if (newy <= sy + 100 && newy >= sy - 100 && newx <= sx + 200 && newx >= sx - 200) sx = newx; //check touch boundaries
				
				if (!shoot){
					bx = sx;
					by = sy;
				}
				canvas.drawBitmap(Ship, sx - 50, sy, null);
				canvas.drawBitmap(Dog1, d1x, d1y, null);
				canvas.drawBitmap(Dog2, d2x, d2y, null);
				canvas.drawBitmap(Dog3, d3x, d3y, null);
				
				//****************************
				//Check bounds for the dogs:
				if (d1y > canvas.getHeight()){
					lives = lives - 1;
					d1y = -50;
					d1x = generator.nextInt((int) canvas.getWidth()) - 20;
				}
				else d1y = d1y + speed;
				
				if (d2y > canvas.getHeight()){
					lives = lives - 1;
					d2y = -100;
					d2x = generator.nextInt((int) canvas.getWidth()) - 20;
				}
				else d2y = d2y + speed;
				
				if (d3y > canvas.getHeight()){
					lives = lives - 1;
					d3y = -150;
					d3x = generator.nextInt((int) canvas.getWidth())- 20;
				}
				else d3y = d3y + speed;
				
				//***************************
				//Check collision with bullets:
				//dog1
				if(by > d1y && by < d1y + 72 && bx > d1x - 40 && bx < d1x + 40){
					shoot = false;
					d1y = -200;
					d1x = generator.nextInt((int) canvas.getWidth()) - 20;
					score = score + 10;
					sp2.play(fart, 1, 1, 0, 0, 1);
				}
				//dog2
				if(by > d2y && by < d2y + 72 && bx > d2x - 40 && bx < d2x + 40){
					shoot = false;
					d2y = -200;
					d2x = generator.nextInt((int) canvas.getWidth()) - 20;
					score = score + 10;
					sp2.play(fart, 1, 1, 0, 0, 1);
				}
				//dog3
				if(by > d3y && by < d3y + 72 && bx > d3x - 40 && bx < d3x + 40){
					shoot = false;
					d3y = -200;
					d3x = generator.nextInt((int) canvas.getWidth()) - 20;
					score = score + 10;
					sp2.play(fart, 1, 1, 0, 0, 1);
				}
				
				if(shoot && by > 0){
					canvas.drawBitmap(Bullet, bx, by, null);
					by = by - 20;
				}
				else shoot = false;
				
				if (lives == 0){
					finish();
					String score_str = String.valueOf(score);
					Intent intent = new Intent(getContext(), GameOver.class);
					intent.putExtra("score", score_str);
					startActivity(intent);
				}
				
				//speed updating
				if (score == 100 && changespeed){
					speed = speed + 2;
					changespeed = false;
				}
				if (score == 200 && !changespeed){
					speed = speed + 2;
					changespeed = true;
				}
				if (score == 300 && changespeed){
					speed = speed + 2;
					changespeed = false;
				}
				
				sHolder.unlockCanvasAndPost(canvas);
			}
		}
	}
}

package com.joymobile.whistlerattack;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;

import android.util.Log;

class Level implements Sequence.Listener
{
	Scene scene;
	GameScreen screen;
	MainActivity main;
	
	Level.Listener listener;
	Sequence seq;
	TimerHandler finish_timer;
	TimerHandler update_timer;
	
	String[] sequences_code;
	float duration = 2;
	float delay_multipler = 0.2f;
	float opened_duration = 0.7f;
	
	public Level( Scene scene, GameScreen screen, MainActivity main )
	{
		this.scene = scene;
		this.screen = screen;
		this.main = main;
	}
	
	public void start()
	{
		scene.unregisterUpdateHandler( finish_timer );
		finish_timer = new TimerHandler( duration, false, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler pTimerHandler)
			{
				finishLevel();
			}
		});
		scene.registerUpdateHandler( finish_timer );
		
		//onSequenceFinish();
		
		final Random rand = new Random();
		seq = SequenceBuilder.makeSequence( sequences_code[ rand.nextInt( sequences_code.length ) ], delay_multipler, opened_duration, scene, screen, main );
		seq.start();
		
		update_timer = new TimerHandler( Time.get(0.01f), true, new ITimerCallback()
    	{
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				if ( ! seq.isReady() )
					return;
				
				Log.d( "Updates 1", ""+scene.getUpdateHandlerCount() );
				seq.stop();
				Log.d( "Updates 2", ""+scene.getUpdateHandlerCount() );
				seq = SequenceBuilder.makeSequence( sequences_code[ rand.nextInt( sequences_code.length ) ], delay_multipler, opened_duration, scene, screen, main );
				seq.start();
			}
    	});
    	scene.registerUpdateHandler( update_timer );
	}
	
	public void setOnFinishListener( Level.Listener listener )
	{
		this.listener = listener;
	}
	
	public void finishLevel()
	{
		scene.unregisterUpdateHandler( update_timer );
		(new Thread(){
			@Override
			public void run()
			{
				while ( ! seq.isReady() );
				listener.onLevelFinish( Level.this );
			}
		}).start();
	}
	
	public void onSequenceFinish() 
	{
		final Random rand = new Random();
		
		//if ( seq != null ) seq.stop();
		seq = SequenceBuilder.makeSequence( sequences_code[ rand.nextInt( sequences_code.length ) ], delay_multipler, opened_duration, scene, screen, main );
		seq.setOnFinishListener( this );
		seq.start();
		seq.runFinishSequenceThread();
	}
	
	interface Listener
	{
		public void onLevelFinish( Level lvl );
	}
}
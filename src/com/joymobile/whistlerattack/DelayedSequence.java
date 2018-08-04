package com.joymobile.whistlerattack;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;

class DelayedSequence extends Sequence
{
	ArrayList<Sequence> sequences = new ArrayList<Sequence>();
	float delay;
	TimerHandler timer;
	Sequence.Listener listener;
	
	public DelayedSequence( Scene scene, GameScreen screen, MainActivity main )
	{
		super( scene, screen, main );
	}
	
	public void setDelay( float d )
	{
		delay = d;
	}
	
	public void addSequence( Sequence seq )
	{
		seq.setParent( this );
		sequences.add( seq );
	}
	
	@Override
	public boolean isReady()
	{
		for ( Sequence seq : sequences )
		{
			if ( ! seq.isReady() )
				return false;
		}
		
		if ( is_ready == false )
			return false;
		
		return true;
	}
	
	@Override
	public void start()
	{
		is_ready = false;
		timer = new TimerHandler( Time.get(delay), false, new ITimerCallback()
    	{
			public void onTimePassed( TimerHandler pTimerHandler ) 
			{
				for ( Sequence seq : sequences )
				{
					seq.start();
				}
				
				updateReadyStatus();
			}
    	});
		scene.registerUpdateHandler( timer );
	}
	
	@Override
	public void stop()
	{
		for ( Sequence seq : sequences )
		{
			seq.stop();
		}
		
		scene.unregisterUpdateHandler( timer );
	}
	
	private boolean areChildrenReady()
	{
		for ( Sequence seq : sequences )
		{
			if ( ! seq.isReady() )
				return false;
		}
		
		return true;
	}
	
	public void updateReadyStatus()
	{
		if ( areChildrenReady() )
		{
			is_ready = true;
			forceParentUpdatingReadyStatus();
		}
	}
	
	

	@Override
	public void setOnFinishListener( Sequence.Listener listener )
	{
		this.listener = listener;
	}
	
	@Override
	public void runFinishSequenceThread()
	{
		final IUpdateHandler handler = new IUpdateHandler()
		{
			public void onUpdate(final float pSecondsElapsed)
			{
				if ( DelayedSequence.this.isReady() )
				{
					DelayedSequence.this.listener.onSequenceFinish();
					scene.unregisterUpdateHandler( this );
				}
			}
			
			public void reset()
			{
			}
		};
		
		scene.registerUpdateHandler( handler );
	}
}
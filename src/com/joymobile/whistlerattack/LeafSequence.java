package com.joymobile.whistlerattack;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;

class LeafSequence extends Sequence
{
	int hole_number;
	Class<? extends Item> item_class;
	float opened_duration = 0.7f;
	float delay_multipler = 0.2f;
	
	public LeafSequence( float delay_multipler, float opened_duration, Scene scene, GameScreen screen, MainActivity main )
	{
		super( scene, screen, main );
		this.delay_multipler = delay_multipler;
		this.opened_duration = opened_duration;
	}
	
	public void setHoleNumber( int num )
	{
		hole_number = num;
	}
	
	public void setItem( Class<? extends Item> item_class )
	{
		this.item_class = item_class;
	}
	
	@Override
	public boolean isReady()
	{
		return is_ready;
	}
	
	@Override
	public void start()
	{
		is_ready = false;
		scene.registerUpdateHandler( new TimerHandler( Time.get(delay_multipler), false, new ITimerCallback()
    	{
			public void onTimePassed( TimerHandler pTimerHandler ) 
			{
				is_ready = true;
				LeafSequence.this.forceParentUpdatingReadyStatus();
			}
    	}));
		
		if ( screen.isBonusActivated() && item_class.isInstance( new Bomb( 0, 0, scene, screen, main ) ) )
		{
			item_class = Whistler.class;
		}
		 
		State state = screen.getItem( hole_number ).getState();

		if ( state instanceof ClosedState )
		{
			screen.changeItem( hole_number, item_class );
			screen.getItem( hole_number ).changeState( new OpeningState( new Chain( opened_duration ) ) );
		}
		else if ( state instanceof OpenedState )
		{
			( (OpenedState)screen.getItem( hole_number ).getState() ).changeStateOnEnd( new MissedClosingState( new Chain( opened_duration )
			{
				@Override
				public void onClosedState()
				{
					screen.changeItem( hole_number, item_class );
					screen.getItem( hole_number ).changeState( new OpeningState(  new Chain( opened_duration ) ) );
				}
			}));
		}
		else if ( state instanceof OpeningState )
		{
			( (OpeningState)screen.getItem( hole_number ).getState() ).changeStateOnEnd( new OpenedState( new Chain( opened_duration )
			{
				@Override
				public void onClosedState()
				{
					screen.changeItem( hole_number, item_class );
					screen.getItem( hole_number ).changeState( new OpeningState(  new Chain( opened_duration ) ) );
				}
			}));
		}
		else if ( state instanceof MissedClosingState )
		{
			( (MissedClosingState)screen.getItem( hole_number ).getState() ).changeStateOnEnd( new ClosedState( new Chain( opened_duration )
			{
				@Override
				public void onClosedState()
				{
					screen.changeItem( hole_number, item_class );
					screen.getItem( hole_number ).changeState( new OpeningState(  new Chain( opened_duration ) ) );
				}
			}));
		}
		else if ( state instanceof OwnedState )
		{
			( (OwnedState)screen.getItem( hole_number ).getState() ).changeStateOnEnd( new ClosedState( new Chain( opened_duration )
			{
				@Override
				public void onClosedState()
				{
					screen.changeItem( hole_number, item_class );
					screen.getItem( hole_number ).changeState( new OpeningState(  new Chain( opened_duration ) ) );
				}
			}));
		}
	}
}
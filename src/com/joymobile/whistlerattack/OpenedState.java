package com.joymobile.whistlerattack;

import java.util.ArrayList;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

class OpenedState extends State
{
	State nextState;
	float opened_duration = 0.7f;
	protected static ArrayList<Unit.Listener> unit_listeners = new ArrayList<Unit.Listener>();
	
	private TimerHandler timerHandler;
	
	public OpenedState( Chain chain )
	{
		super( chain );
		opened_duration = chain.getOpenedData();
	}
	
	public OpenedState()
	{
		super();
	}
	
	public static void addUnitListener( Unit.Listener listener )
    {
        unit_listeners.add( listener );
    }
	
	public static void resetUnitListeners()
	{
		unit_listeners = new ArrayList<Unit.Listener>();
	}
	
	public void changeStateOnEnd( State state )
	{
		nextState = state;
	}
	
	@Override
	public void onInit()
	{
		item.setAnimation( State.OPENED );
		
		if ( item instanceof Bomb )
		{
			for ( Unit.Listener listener : unit_listeners )
			{
				if ( listener.hasPermission( Bomb.OPEN ) )
				{
					listener.sendUnit( Bomb.OPEN, new Unit() );
				}
			}
		}
		else if ( item instanceof Star )
		{
			for ( Unit.Listener listener : unit_listeners )
			{
				if ( listener.hasPermission( Star.OPEN ) )
				{
					listener.sendUnit( Star.OPEN, new Unit() );
				}
			}
		}
		
		timerHandler = new TimerHandler( opened_duration, false, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler pTimerHandler)
			{
				if ( item.getState() instanceof OpenedState )
				{
					if ( nextState != null )
					{
						item.changeState( nextState );
					}
					else
					{
						if ( chain != null )
							item.changeState( new MissedClosingState( chain ) );
						else
							item.changeState( new MissedClosingState() );
					}
				}
			}
		} );
		scene.registerUpdateHandler( timerHandler );
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	{
		if ( pSceneTouchEvent.isActionDown() )
		{
			TapGuard.release();
		}
		else
		{
			boolean result = TapGuard.press();
			
			return true;
		}

		removeTimerHandler();
		
		if ( chain != null )
			item.changeState( new OwnedState( chain ) );
		else
			item.changeState( new OwnedState() );
		
		return true;
	}
	
	public void removeTimerHandler()
	{
		scene.unregisterUpdateHandler( timerHandler );
	}

	@Override
	public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
			int pInitialLoopCount) {
		
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
			int pOldFrameIndex, int pNewFrameIndex) {
		
	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
			int pRemainingLoopCount, int pInitialLoopCount) {

		
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		
	}
}
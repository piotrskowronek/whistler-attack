package com.joymobile.whistlerattack;

import java.util.ArrayList;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

class MissedClosingState extends ClosingState implements AnimatedSprite.IAnimationListener
{
	State nextState;
	protected static ArrayList<Unit.Listener> unit_listeners = new ArrayList<Unit.Listener>();
	
	public MissedClosingState( Chain chain )
	{
		super( chain );
	}
	
	public MissedClosingState()
	{
		super();
	}
	
	@Override
	public void onInit()
	{
		if ( item instanceof Whistler )
		{
			main.mMissed.play();
			
			Life.get().kill();
		}
		else if ( item instanceof Star )
		{
			for ( Unit.Listener listener : unit_listeners )
			{
				if ( listener.hasPermission( Star.MISS ) )
				{
					listener.sendUnit( Star.MISS, new Unit() );
				}
			}
		}
		
		item.setAnimation( State.MISSED_CLOSING, this );
	}
	
	public static void addUnitListener( Unit.Listener listener )
    {
        unit_listeners.add( listener );
    }
	
	public static void resetUnitListeners()
	{
		unit_listeners = new ArrayList<Unit.Listener>();
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	{
		return false;
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

	public void changeStateOnEnd( State state )
	{
		nextState = state;
	}

	@Override
	public void onAnimationFinished( AnimatedSprite pAnimatedSprite )
	{	
		if ( nextState != null )
		{
			item.changeState( nextState );
		}
		else
		{
			if ( chain != null )
				item.changeState( new ClosedState( chain ) );
			else
				item.changeState( new ClosedState() );
		}
	}
}
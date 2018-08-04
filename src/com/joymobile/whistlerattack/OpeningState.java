package com.joymobile.whistlerattack;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

class OpeningState extends State implements AnimatedSprite.IAnimationListener
{
	State nextState;
	
	public OpeningState( Chain chain )
	{
		super( chain );
	}
	
	public OpeningState()
	{
		super();
	}
	
	@Override
	public void onInit()
	{
		item.setAnimation( State.OPENING, this );
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
				item.changeState( new OpenedState( chain ) );
			else
				item.changeState( new OpenedState() );
		}
	}
}
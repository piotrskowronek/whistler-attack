package com.joymobile.whistlerattack;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

class ClosedState extends State
{
	public ClosedState( Chain chain )
	{
		super( chain );
	}
	
	public ClosedState()
	{
		super();
	}
	
	@Override
	public void onInit()
	{
		if ( chain != null )
		{
			chain.onClosedState();
		}
		
		item.setAnimation( State.CLOSED );
	}
	
	@Override
	public boolean onAreaTouched( TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY )
	{
		return false;
	}

	@Override
	public void onAnimationStarted( AnimatedSprite pAnimatedSprite, int pInitialLoopCount )
	{

	}

	@Override
	public void onAnimationFrameChanged( AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex )
	{

	}

	@Override
	public void onAnimationLoopFinished( AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount )
	{

	}

	@Override
	public void onAnimationFinished( AnimatedSprite pAnimatedSprite )
	{

	}
}
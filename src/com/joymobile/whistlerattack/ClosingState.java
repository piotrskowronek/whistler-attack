package com.joymobile.whistlerattack;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

abstract class ClosingState extends State implements AnimatedSprite.IAnimationListener
{
	public ClosingState( Chain chain )
	{
		super( chain );
	}
	
	public ClosingState()
	{
		super();
	}
	
	@Override
	public void onInit()
	{
		item.setAnimation( State.MISSED_CLOSING, this );
	}
	
	@Override
	public boolean onAreaTouched( TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY )
	{
		return true;
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
		// nie dochodzi nigdy do glosu, ale jezeli to pamietaj o Chainie!
		item.changeState( new ClosedState() );
	}
}
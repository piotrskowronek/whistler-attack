package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

abstract class State
{
	final static int CLOSED = 0;
	final static int MISSED_CLOSING = 1;
	final static int OPENED = 2;
	final static int OPENING = 3;
	final static int OWNED = 4;
	
	protected Item item;
	protected Scene scene;
	protected GameScreen engine;
	protected MainActivity main;
	
	protected Chain chain;
	
	public State( Chain chain )
	{
		this.chain = chain;
	}
	
	public State()
	{

	}
	
	public void initialize( Item item, Scene scene, GameScreen engine, MainActivity main )
	{
		this.item = item;
		this.scene = scene;
		this.engine = engine;
		this.main = main;
	}
	
	abstract public void onInit();
	
	abstract public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY);
	
	abstract public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount);

	abstract public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex);

	abstract public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount);

	abstract public void onAnimationFinished( AnimatedSprite pAnimatedSprite );
}
package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;

class Sequence
{
	boolean is_ready = true;
	Scene scene;
	GameScreen screen;
	DelayedSequence parent;
	MainActivity main;
	
	public Sequence( Scene scene, GameScreen screen, MainActivity main )
	{
		 this.scene = scene;
		 this.screen = screen;
		 this.main = main;
	}
	
	public void setParent( DelayedSequence seq )
	{
		this.parent = seq;
	}
	
	public void start()
	{
		
	}
	
	public boolean isReady()
	{
		return is_ready;
	}
	
	protected void forceParentUpdatingReadyStatus()
	{
		if ( parent instanceof DelayedSequence )
		{
			parent.updateReadyStatus();
		}
	}
	
	public void stop()
	{
		
	}
	
	public void setOnFinishListener( Sequence.Listener listener )
	{
		
	}
	
	public void runFinishSequenceThread()
	{
	
	}
	
	interface Listener
	{
		public void onSequenceFinish();
	}
}
package com.joymobile.whistlerattack;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.util.HorizontalAlign;

class TutorialGameScreen extends GameScreen
{
	TimerHandler strategy;
	
	@Override
	public void onStartScreen( String[] args )
	{
		prepareScene( scene );
		Life.get().setGameScreen( this );
		Life.get().disable();
		Combo.get().disable();
    	
    	setWhistlersPositions();
    	
    	attachAutomat( scene );
    	attachWhistlers( items, scene );
    	attachPauseButton( scene, 7f );
    	
    	registerTouchAreas( items, scene );
    	
    	String text =  "Whack the whistlers\n" +
				"If you miss one\n" +
				"you loose a life";
    	
    	drawTip( 6, text, main.mFont2, new OnTipEnd()
    	{
			public void perform() 
			{
				registerGameStrategy( scene, 1 );
				
				scene.registerUpdateHandler( new TimerHandler( 10, false, new ITimerCallback()
				{
					public void onTimePassed(TimerHandler pTimerHandler) 
					{
						unregisterGameStrategy();
						String text =   "Stars are our virtual\n" +
										"currency. Collecting\n" +
										"them is unnecessary";
				    	
				    	drawTip( 6, text, main.mFont2, new OnTipEnd()
				    	{
							public void perform() 
							{
								registerGameStrategy( scene, 2 );
								
								scene.registerUpdateHandler( new TimerHandler( 10, false, new ITimerCallback()
								{
									public void onTimePassed(TimerHandler pTimerHandler) 
									{
										unregisterGameStrategy();
										String text =   "The trick is to be quick and accurate.\n" +
														"The most score you collect by making\n" +
														"combo. To make combo you cannot let\n" +
														"the bar at right fall down.";
										
										drawTip( 10, text, main.mFont, new OnTipEnd()
								    	{
											public void perform() 
											{
												registerGameStrategy( scene, 3 );
												
												Combo.get().clear();
												Combo.get().enable();
												Combo.get().setScene( scene );
												
												setComboTextListener( scene );
										    	setStarDecorationListener( scene );
										    	attachComboBar( scene, 0.01f );
										    	
										    	scene.registerUpdateHandler( new TimerHandler( 30, false, new ITimerCallback()
												{
													public void onTimePassed(TimerHandler pTimerHandler) 
													{
														String text =   "Well done!\n" +
																		"Go breaking a new record";
												
														drawTip( 4, text, main.mFont, new OnTipEnd()
												    	{
															public void perform() 
															{
																Database.setBoolean( "tutorial", true );
																main.changeScreen( new MenuScreen() );
															}
														});
													}
												}));
											}
								    	});
									}
								}));
							}
				    	});
					}	
				}));
			}
    	});
	}
	
	private void setWhistlersPositions()
	{
		this.listXs = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> line0 = new ArrayList<Double>();
		line0.add( 0.234 );
		line0.add( 0.429 );
		line0.add( 0.625 );
		ArrayList<Double> line1 = new ArrayList<Double>();
		line1.add( 0.332 );
		line1.add( 0.527 );
		ArrayList<Double> line2 = new ArrayList<Double>();
		line2.add( 0.234 );
		line2.add( 0.429 );
		line2.add( 0.625 );
		listXs.add( line0 );
		listXs.add( line1 );
		listXs.add( line2 );
		
		this.listYs = new ArrayList<Double>();
		listYs.add( 0.115 );
		listYs.add( 0.296 );
		listYs.add( 0.477 );
	}
	
	private void drawTip( float duration, String text, Font font, final OnTipEnd onTipEnd )
	{
		final Rectangle rect = new Rectangle( 0, 0, main.w(1), main.h(1), main.getVertexBufferObjectManager() );
		rect.setColor(0, 0, 0);
		rect.setAlpha( 0.85f );
		scene.attachChild( rect );
		
		final Text textComponent = new Text( main.w(0), main.h(0.2f), font, text, text.length(), main.getVertexBufferObjectManager() );
		textComponent.setHorizontalAlign( HorizontalAlign.CENTER );
		textComponent.setX( ( main.w(1) - textComponent.getWidth() ) / 2 );
		textComponent.setY( ( main.h(1) - textComponent.getHeight() ) / 2 );
    	scene.attachChild( textComponent );
		
		scene.registerUpdateHandler( new TimerHandler( duration, false, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				rect.detachSelf();
				textComponent.detachSelf();
				
				onTipEnd.perform();
			}
		}));
	}
	
	private void registerGameStrategy( Scene scene, final int step )
	{
		float speed;
		if ( step >= 3 )
			speed = 0.2f;
		else
			speed = 1f;
		
		strategy = new TimerHandler( speed, true, new ITimerCallback()
    	{
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				Random rand = new Random();
				
				boolean isThereClosedHole = false;
				for ( Item item : items )
				{
					if ( item.getState() instanceof ClosedState )
						isThereClosedHole = true;
				}
				
				if ( isThereClosedHole == false )
					return;
				
				int id;
				do
				{
					id = rand.nextInt( items.size() );
				}
				while ( ! (items.get(id).getState() instanceof ClosedState) );
				
				int whichItem = rand.nextInt( 2 );
				if ( whichItem == 1 && step > 1 )
				{
					changeItem( id, Star.class );
				}
				else
				{
					changeItem( id, Whistler.class );
				}

				getItem( id ).changeState( new OpeningState() );
			}
    	});
    	
    	scene.registerUpdateHandler( strategy );
	}
	
	public void unregisterGameStrategy()
	{
		scene.unregisterUpdateHandler( strategy );
	}
	
	interface OnTipEnd
	{
		public void perform();
	}
}
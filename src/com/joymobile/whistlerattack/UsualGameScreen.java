package com.joymobile.whistlerattack;

import java.util.ArrayList;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.joymobile.whistlerattack.goods.HeadstartUnlimitedGoods;

public class UsualGameScreen extends GameScreen implements Level.Listener
{
	public static final int PLAY_COUNT = 945887445;
	Level[] levels;
	ArrayList<Unit.Listener> unit_listeners= new ArrayList<Unit.Listener>();
	protected int start_level = 0;
	private boolean headstart_button_enabled;
	
	@Override
	public void onStartScreen( String[] args )
	{
		prepareScene( scene );
		
		main.getMusicManager().setMasterVolume( 0.5f );
		main.mStarting.play();
		
		levels = new Level[]{
			new AdventureLevel0( scene, this, main ),	
			new AdventureLevel1( scene, this, main ),
			new AdventureLevel2( scene, this, main ),
			new AdventureLevel3( scene, this, main ),
			new AdventureLevel4( scene, this, main ),
			new AdventureLevel5( scene, this, main )
		};
		
		addSceneListener();
		
		Life.get().setGameScreen( this );
		Life.get().enable();
		Life.get().reset();
		Life.get().addUnitListener( QuestProxy.get() );
    	
		Score.get().reset();
		Currency.get().reset();
		Combo.get().clear();
		Combo.get().enable();
		Combo.get().setScene( scene );
		Combo.get().unfreeze();
		
		FeatProxy.get().loadAndResetTasks();
		Combo.get().addUnitListener( FeatProxy.get() );
		
		QuestProxy.get().loadAndResetTasks(); //TODO: move to menu ?
		Combo.get().addUnitListener( QuestProxy.get() );
		OwnedState.resetUnitListeners();
		OwnedState.addUnitListener( QuestProxy.get() );
		OpenedState.resetUnitListeners();
		OpenedState.addUnitListener( QuestProxy.get() );
		MissedClosingState.resetUnitListeners();
		MissedClosingState.addUnitListener( QuestProxy.get() );
		
		unit_listeners = new ArrayList<Unit.Listener>();
		addUnitListenerToPlayCounter( FeatProxy.get() );
		Database.raiseInt( "play" );
		notifyStartPlaying();
		
    	setComboTextListener( scene );
    	setStarDecorationListener( scene );
    	
    	setWhistlersPositions();
    	
    	addScoreComboListener();
    	
    	attachAutomat( scene );
    	attachComboBar( scene, 3f );
    	
    	attachHeadstartIfPurchased();
    	
    	attachLives();
    	attachWhistlers( items, scene );
    	attachPauseButton( scene, 3f );
    	
    	registerTouchAreas( items, scene );
    	
    	registerGameStrategy( scene );
	}

	private void attachHeadstartIfPurchased()
	{
		final HeadstartUnlimitedGoods goods = (HeadstartUnlimitedGoods)StoreGoodsProxy.get().getGoods( StoreGoodsProxy.UPGRADES, "MegaHeadstart" );
		headstart_button_enabled = true;
		
		if ( goods.getGoodsLeftCount() > 0 )
		{
			final Sprite time3 = new Sprite( main.w(0.02), main.h(0.05), textures.get( "headstart" ), main.getVertexBufferObjectManager() )
			{
				@Override
				public boolean onAreaTouched( TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY )
				{
					if ( pSceneTouchEvent.isActionUp() && headstart_button_enabled )
					{
						start_level  = goods.getLevelIndex();
						main.mTap.play();
						goods.consume();
						headstart_button_enabled = false;
					}
					
					return true;
				}
			};
			scene.attachChild( time3 );
			scene.registerTouchArea( time3 );
			
			scene.registerUpdateHandler( new TimerHandler( 2, false, new ITimerCallback()
			{
				@Override
				public void onTimePassed( TimerHandler pTimerHandler )
				{
					time3.registerEntityModifier( new AlphaModifier( 0.5f, 1, 0 ) );
				}
			}));
			
			scene.registerUpdateHandler( new TimerHandler( 2.5f, false, new ITimerCallback()
			{
				@Override
				public void onTimePassed( TimerHandler pTimerHandler )
				{
					main.runOnUpdateThread( new Runnable()
					{
						@Override
						public void run()
						{
							scene.unregisterUpdateHandler( time3 );
							time3.detachSelf();
						}
					});
				}
			}));
		}
	}

	private void attachLives()
	{
		scene.registerUpdateHandler( new TimerHandler( 3f, false, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler pTimerHandler)
			{
				for ( int i = 0; i < Life.get().getLives(); i++ )
		    	{
		    		Sprite heart = new Sprite( main.w(0.8f)-i * main.h(0.15f), 0, main.textures.get( "heart" ), main.getVertexBufferObjectManager() );
		    		heart.registerEntityModifier( new MoveYModifier( 1, -main.h(0.15f), 0 ) );
		    		scene.getChildByIndex(3).attachChild( heart );
		    		Life.get().registerHeartSprite( heart );
		    	}
			}
		}));
	}

	private void notifyStartPlaying()
	{
		for ( Unit.Listener listener : unit_listeners )
		{
			if ( listener.hasPermission( PLAY_COUNT ) )
			{
				listener.sendUnit( PLAY_COUNT, new Unit( Database.getInt( "play" ) ) );
			}
		}
	}

	private void addSceneListener()
	{
		scene.setOnSceneTouchListener( new IOnSceneTouchListener() 
		{
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) 
			{
				if ( pSceneTouchEvent.isActionDown() )
					main.mMetal.play();
				return true;
			}
		});
	}

	private void addScoreComboListener()
	{
		Combo.get().addListener( new Combo.Listener() 
    	{
			public void onComboUnfreeze() 
			{
				
			}
			
			public void onComboTick(int counter, boolean is_frozen, TimerHandler timer) 
			{
				
			}
			
			public void onComboFreeze() 
			{
				
			}
			
			public void onComboFinish(int counter) 
			{
				Score.get().addScore( counter * counter * 5/3 );
			}
		});
	}

	private void addUnitListenerToPlayCounter ( Unit.Listener listener ) 
	{
		unit_listeners.add( listener );
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
	
	private void registerGameStrategy( Scene scene )
	{
		scene.registerUpdateHandler( new TimerHandler( 3, false, new ITimerCallback()
		{
			public void onTimePassed( TimerHandler pTimerHandler ) 
			{
				levels[start_level].setOnFinishListener( UsualGameScreen.this );
				levels[start_level].start();
			}
		}));
	}
	
	public void onLevelFinish( Level lvl )
	{
		main.mBell.play();
		
		final Sprite harder = new Sprite( main.w(1), main.h(0.7), main.textures.get( "harder" ), main.getVertexBufferObjectManager() );
		scene.getChildByIndex(3).attachChild( harder );
		harder.registerEntityModifier( new MoveXModifier( Time.get(5), main.w(1), -main.w(1) )  );
    	
		harder.registerUpdateHandler( new TimerHandler( Time.get(5), false, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				harder.setScale(0);
			}
		}));
		
		for ( int i = 0; i < levels.length; i++ )
		{
			if ( levels[i].equals( lvl ) )
			{
				int next;
				if ( i+1 == levels.length )
					next = i;
				else
					next = i+1;
				
				levels[next].setOnFinishListener( this );
				levels[next].start();
			}
		}
	}
}
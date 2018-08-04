package com.joymobile.whistlerattack;

import java.util.ArrayList;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

class OwnedState extends ClosingState implements AnimatedSprite.IAnimationListener
{
	State nextState;
	protected static ArrayList<Unit.Listener> unit_listeners = new ArrayList<Unit.Listener>();
	
	public OwnedState( Chain chain )
	{
		super( chain );
	}
	
	public OwnedState()
	{
		super();
	}
	
	@Override
	public void onInit()
	{
		item.setAnimation( State.OWNED, this );
		
		if ( item instanceof Whistler )
		{
			main.mExplosionSound.play();
			Score.get().addScore( 15 );
			showScore();
			
			for ( Unit.Listener listener : unit_listeners )
			{
				if ( listener.hasPermission( Whistler.TICK ) )
				{
					listener.sendUnit( Whistler.TICK, new Unit() );
				}
			}
		}
		else if ( item instanceof Star )
		{
			main.mStar.play();
			Currency.get().addCurrency( 1 );
			
			if ( ! Database.getBoolean( "stars_disabled" ) )
				showStarsColoredBy( null );
			
			for ( Unit.Listener listener : unit_listeners )
			{
				if ( listener.hasPermission( Star.TICK ) )
				{
					listener.sendUnit( Star.TICK, new Unit() );
				}
			}
		}
		else if ( item instanceof Bomb )
		{
			main.mBomb.play();
			
			for ( Unit.Listener listener : unit_listeners )
			{
				if ( listener.hasPermission( Bomb.TICK ) )
				{
					listener.sendUnit( Bomb.TICK, new Unit() );
				}
			}
			
			scene.registerEntityModifier( new SequenceEntityModifier(
					new MoveModifier( 0.1f, 0, -(main.w(0.05)), 0, -(main.h(0.05) ) ),
					new MoveModifier( 0.1f, -(main.w(0.05)), main.w(0.05), -(main.h(0.05) ), main.h(0.05) ),
					new MoveModifier( 0.1f, main.w(0.05), main.w(0.01) ,main.h(0.05), -(main.h(0.01)) ),
					new MoveModifier( 0.1f, main.w(0.01), main.w(0.02), -(main.h(0.01)), main.h(0.03) ),
					new MoveModifier( 0.1f, main.w(0.02), -(main.w(0.05)), main.h(0.03), -(main.h(0.05)) ),
					new MoveModifier( 0.1f, -(main.w(0.05)), main.w(0.05), -(main.h(0.05) ), main.h(0.05) ),
					new MoveModifier( 0.1f, main.w(0.05), main.w(0.01) ,main.h(0.05), -(main.h(0.01)) ),
					new MoveModifier( 0.1f, main.w(0.01), main.w(0.02), -(main.h(0.01)), main.h(0.03) ),
					new MoveModifier( 0.1f, main.w(0.02), 0, main.h(0.03), 0 )
			));
			engine.endGameWithExplosion( item.getX(), item.getY() );
		}
		else if ( item instanceof FreezeBonus )
		{
			main.mExplosionSound.play();
			showStarsColoredBy( Color.CYAN );
			Combo.get().unfreeze();
			Combo.get().freeze();
		}
		else if ( item instanceof LifeBonus )
		{
			main.mExplosionSound.play();
			showStarsColoredBy( Color.WHITE );
			Life.get().addLife();
		}
		else if ( item instanceof BombBonus )
		{
			main.mExplosionSound.play();
			showStarsColoredBy( Color.BLACK );
			((GameScreen)main.screen).useBombBonus();
		}
		
		tickCombo();
	}
	
	public static void addUnitListener( Unit.Listener listener )
    {
        unit_listeners.add( listener );
    }
	
	public static void resetUnitListeners()
	{
		unit_listeners = new ArrayList<Unit.Listener>();
	}
	
	private void showScore()
	{
		final Sprite points = new Sprite( item.getX(), item.getY(), main.textures.get( "fifteen" ), main.getVertexBufferObjectManager() );
		scene.getChildByIndex(3).attachChild( points );
		points.registerEntityModifier( new MoveYModifier( Time.get(0.7f), item.getY(), item.getY()-main.h(0.15) ) );
		points.registerEntityModifier( new AlphaModifier( Time.get(0.7f), 1, 0 ) );
		points.registerUpdateHandler( new TimerHandler( Time.get(0.7f), false, new ITimerCallback()
		{
			public void onTimePassed( TimerHandler pTimerHandler )
			{
				main.runOnUpdateThread( new Runnable()
				{
					public void run() 
					{
						points.detachSelf();
					}
				});
			}
		}));
	}
	
	private void showStarsColoredBy( Color color )
	{
		for ( int i = 0; i < 12; i++ )
		{
			final Sprite sprite = new Sprite( item.getX()+main.w(0.07), item.getY()+main.h(0.05), main.textures.get( "particle" ), main.getVertexBufferObjectManager() );
			scene.getChildByIndex(3).attachChild( sprite );
			
			if ( color != null )
				sprite.setColor( color );
			
			sprite.registerEntityModifier( new AlphaModifier( Time.get(0.3f), 1.0f, 0.0f ) );
			sprite.registerEntityModifier( new MoveModifier( Time.get(0.3f), 
					sprite.getX(), 
					sprite.getX()+main.w(0.2*Math.sin(i*Math.PI/6)), 
					(sprite.getY()), 
					sprite.getY()-main.w(0.2*Math.cos(i*Math.PI/6)) 
			));
			
			sprite.registerUpdateHandler( new TimerHandler( 0.3f, false, new ITimerCallback()
			{
				public void onTimePassed(TimerHandler pTimerHandler) 
				{
					main.runOnUpdateThread( new Runnable() 
					{
						public void run() 
						{
						    sprite.detachSelf();
						}
					});
				}
			}));
		}	
	}
	
	private void tickCombo()
	{
		if ( Combo.get().isEnabled() )
		{
			main.runOnUpdateThread( new Runnable()
			{
				public void run() 
				{
					Combo.get().tick();
				}
			});
		}
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
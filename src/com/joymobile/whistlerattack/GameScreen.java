package com.joymobile.whistlerattack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.HorizontalAlign;

import com.joymobile.whistlerattack.goods.BombDurationUpgradeableGoods;
import com.scoreloop.client.android.ui.OnScoreSubmitObserver;
import com.scoreloop.client.android.ui.ScoreloopManagerSingleton;

class GameScreen extends Screen implements  OnScoreSubmitObserver
{
	HashMap<String, BitmapTextureAtlas> atlases = new HashMap<String, BitmapTextureAtlas>();
	HashMap<String, ITiledTextureRegion> tiled_textures = new HashMap<String, ITiledTextureRegion>();
	HashMap<String, ITextureRegion> textures = new HashMap<String, ITextureRegion>();
	Automat automat;
	boolean bonus_activated = false;

	MainActivity main;
	Scene scene;
	Sprite pause;
	boolean exiting = false;
	
	int score = 0;
	
	protected ArrayList<Item> items= new ArrayList<Item>();
	protected ArrayList<ArrayList<Double>> listXs;
	protected ArrayList<Double> listYs;
	
	@Override
	public void setAtlases( HashMap<String, BitmapTextureAtlas> atlases )
	{
		this.atlases = atlases;
	}
	
	@Override
	public void setTiledTextures( HashMap<String, ITiledTextureRegion> tiled_textures )
	{
		this.tiled_textures = tiled_textures;
	}
	
	@Override
	public void setTextures( HashMap<String, ITextureRegion> textures )
	{
		this.textures = textures;
	}
	
	@Override
	public void setActivity( MainActivity activity )
	{
		main = activity;
	}
	
	@Override
	public Scene onCreateScreen()
	{
		scene = new Scene();
		return scene;
	}
	
	protected void prepareScene( Scene scene )
	{
		scene.attachChild(new Entity());
		scene.attachChild(new Entity());
		scene.attachChild(new Entity());
		scene.attachChild(new Entity());
		scene.attachChild(new Entity());
		scene.setBackground( new Background(0,0,0) );
	}
	
	public void addScore( int score )
	{
		this.score += score;
	}
	
	public int getScore()
	{
		return score;
	}
	
	protected void attachAutomat( Scene scene )
	{
		automat = new Automat( 0, 0, textures.get( "background" ), main.getVertexBufferObjectManager() );
    	scene.getChildByIndex(0).attachChild( automat );
    	Combo.get().addListener( automat );
	}
	
	protected void attachWhistlers( ArrayList<Item> items, final Scene scene )
	{
		ArrayList<ArrayList<Double>> xs = getItemsXForLevel();
		ArrayList<Double> ys = getItemsYForLevel();
    	Whistler item;
    	
    	Iterator<Double> y_iterator = ys.iterator();
		for ( ArrayList<Double> x_row : xs )
		{
			int i = 1;
			double y = y_iterator.next();

			for ( double x : x_row )
			{
				item = new Whistler( main.w(x), main.h(y), scene, this, main );
				scene.getChildByIndex(i).attachChild( item );
				items.add( item );
			}
			
			i++;
		}
	}
	
	protected void registerTouchAreas( ArrayList<Item> items, Scene scene )
	{
		for ( Item an_item : items )
    	{
    		scene.registerTouchArea( an_item );
    	}
	}
	
	public ArrayList<ArrayList<Double>> getItemsXForLevel()
	{
		return listXs;
	}
	
	public ArrayList<Double> getItemsYForLevel()
	{
		return listYs;
	}
	
	public void changeItem( int id, Class<? extends Item> next )
	{
		final Item prev = items.get(id);
		State state = items.get(id).getState();
		Item nextItem;
		try
		{
			nextItem = next.getConstructor( int.class, int.class, Scene.class, GameScreen.class, MainActivity.class ).newInstance( (int)prev.getX(), (int)prev.getY(), scene, this, main );
			
			scene.getChildByIndex( getChildIndex( id ) ).attachChild( nextItem );
			scene.unregisterTouchArea( prev );
			main.runOnUpdateThread( new Runnable()
			{
				public void run() 
				{
					prev.detachSelf();
				}
			});
			scene.registerTouchArea( nextItem );
			items.set( id, nextItem );
			if ( state instanceof OwnedState )
				nextItem.changeState( new ClosedState() );
			else
				nextItem.changeState( state.getClass().newInstance() );
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	protected int getChildIndex( int id )
	{
		int i = 0;
		int j = 1;
		for ( ArrayList<Double> items : listXs )
		{
			for ( double item : items )
			{
				if ( i == id )
				{
					return j;
				}
				
				i++;
			}
			j++;
		}
		
		return -1;
	}
	
	public Item getItem( int id)
	{
		return items.get(id);
	}
	
	protected void attachPauseButton( final Scene scene, float time )
	{
		scene.getChildByIndex(3).registerUpdateHandler( new TimerHandler( time, false, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler pTimerHandler)
			{
				for ( int i = 0; i < Life.get().getLives(); i++ )
		    	{
					pause = new Sprite( main.w(0.05f), main.h(1), textures.get( "pause_button" ), main.getVertexBufferObjectManager() )
					{
						@Override
						public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
						{
							main.togglePause();
							return true;
						}
					};
					pause.registerEntityModifier( new MoveYModifier( 1, main.h(1), main.h(0.8f) ) );
					scene.attachChild( pause );
					scene.registerTouchArea( pause );
		    	}
			}
		}));
	}
	
	protected void setComboTextListener( Scene scene )
	{
		final ComboText combo_text = new ComboText( scene, main );
		Combo.get().addListener( combo_text );
	}
	
	protected void setStarDecorationListener( final Scene scene )
	{
		if ( ! Database.getBoolean( "stars_disabled" ) )
			Combo.get().addListener( new StarDecoration( main, this, scene ) );
	}
	
	protected void attachComboBar( final Scene scene, float time )
	{
		scene.getChildByIndex(3).registerUpdateHandler( new TimerHandler( time, false, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler pTimerHandler)
			{
				Sprite bar = new Sprite( main.w(1), main.h(0.1), textures.get( "combo" ), main.getVertexBufferObjectManager() );
				scene.attachChild( bar );
				bar.registerEntityModifier( new MoveXModifier( 1, main.w(1), main.w(0.9) ) );
		    	ComboBar rect = new ComboBar(  main.w(0.9), main.h(0.1), textures.get( "comboBar" ), main.getVertexBufferObjectManager(), main );
		    	rect.setColor( 1.0f, 0.3f, 0.3f );
		    	
		    	scene.attachChild( rect );
		    	Combo.get().addListener( rect );
			}
		}));
	}
	
	public void endGameWithExplosion( float x, float y )
	{
		if ( ! exiting )
		{
			exiting = true;
			
			Combo.get().forceComboEnd();
			
			main.mGameOver.play();
			Scene end_scene = new Scene();
			
			showExplosion( end_scene, x, y );
			final Rectangle rect = new Rectangle( 0, 0, main.w(1), main.h(1), main.getVertexBufferObjectManager() );
			rect.setColor(0, 0, 0);
			end_scene.attachChild( rect );
			
			end_scene.setBackgroundEnabled( false );
			scene.setChildScene( end_scene, false, true, true );
			
			rect.registerEntityModifier( new AlphaModifier( 2, 0, 1 ) );
			end_scene.registerUpdateHandler( new TimerHandler( 4, false, new ITimerCallback()
			{
				public void onTimePassed(TimerHandler pTimerHandler) 
				{
					main.runOnUpdateThread( new Runnable()
					{
						public void run() 
						{
							rect.detachSelf();
							scene.clearChildScene();
						}
					});
					submitScore();
					( new StarCurrencyUnit( Currency.get().getCurrency() ) ).award();
					
					Combo.get().clear();
					Life.get().setGameScreen( null );
					main.changeScreen( new QuestsScreen(), new String[]{""+Score.get().getScore(),""+Currency.get().getCurrency()} );
				}
			}));
		}
	}
	
	public void endGame()
	{
		if ( ! exiting )
		{
			exiting = true;
			
			main.mGameOver.play();
			final Scene end_scene = new Scene();
			
			final Rectangle rect = new Rectangle( 0, 0, main.w(1), main.h(1), main.getVertexBufferObjectManager() );
			rect.setColor(0, 0, 0);
			end_scene.attachChild( rect );
			
			end_scene.setBackgroundEnabled( false );
			scene.registerUpdateHandler( new TimerHandler( 0.01f, false, new ITimerCallback()
			{
				public void onTimePassed(TimerHandler pTimerHandler) 
				{
					scene.setChildScene( end_scene, false, true, true );
					
					rect.registerEntityModifier( new AlphaModifier( 2, 0, 1 ) );
					
					end_scene.registerUpdateHandler( new TimerHandler( 3, false, new ITimerCallback()
					{
						public void onTimePassed(TimerHandler pTimerHandler) 
						{
							main.runOnUpdateThread( new Runnable()
							{
								public void run() 
								{
									rect.detachSelf();
									scene.clearChildScene();
								}
							});
							submitScore();
							( new StarCurrencyUnit( Currency.get().getCurrency() ) ).award();
							
							Combo.get().clear();
							Life.get().setGameScreen( null );
							main.changeScreen( new QuestsScreen(), new String[]{""+Score.get().getScore(),""+Currency.get().getCurrency()} );
						}
					}));
				}
			}));
		}
	}
	
	private void showExplosion( Scene escene, float x, float y)
	{
		for ( int i = 0; i < 24; i++ )
		{
			final Sprite sprite = new Sprite(x, y, main.textures.get( "explosion" ), main.getVertexBufferObjectManager() );
			escene.attachChild( sprite );
			
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
	
	private void submitScore()
	{
		if ( Database.getBoolean( "scoreloop" ) )
		{
			main.runOnUiThread( new Runnable()
			{
				public void run() 
				{
					ScoreloopManagerSingleton.get().setOnScoreSubmitObserver( GameScreen.this );
					ScoreloopManagerSingleton.get().onGamePlayEnded( (double)( Score.get().getScore() ), null );
				}
			});
		}
	}
	
	public void onScoreSubmit(final int status, final Exception error) 
	{
		ScoreloopManagerSingleton.get().setOnScoreSubmitObserver( null );
    }
	
	@Override
	public void onPause()
	{
		if ( ! exiting )
		{
			scene.clearChildScene();
			scene.setChildScene( main.pause, false, true, true );
		}
	}
	
	@Override
	public void onResume()
	{
		if ( ! exiting )
		{
			main.getMusicManager().setMasterVolume( 0.5f );
		}
	}
	
	@Override
	public void onExit()
	{
		if ( ! exiting )
		{
			scene.clearChildScene();
			
			String text = "Would you like to return\n" +
					"to main menu?";
			textComponent = new Text( main.w(0), main.h(0.2f), main.mFont, text, text.length(), main.getVertexBufferObjectManager() );
			textComponent.setHorizontalAlign( HorizontalAlign.CENTER );
			textComponent.setX( ( main.w(1) - textComponent.getWidth() ) / 2 );
			textComponent.setY( ( main.h(1) - textComponent.getHeight() ) / 4 );
	    	main.exit_scene.attachChild( textComponent );
			scene.setChildScene( main.exit_scene, false, true, true );
		}
	}
	
	@Override
	public void onExitReturn()
	{
		if ( ! exiting )
		{
			scene.clearChildScene();
			main.runOnUpdateThread( new Runnable()
			{
				public void run() 
				{
					textComponent.detachSelf();
				}
			});
			main.getMusicManager().setMasterVolume( 0.5f );
		}
	}

	protected boolean useBombBonus()
	{
		if ( bonus_activated )
			return false;
		
		bonus_activated = true;
		
		final BombDurationUpgradeableGoods goods = (BombDurationUpgradeableGoods)StoreGoodsProxy.get().getGoods( StoreGoodsProxy.UPGRADES, "BombDuration" );
		scene.registerUpdateHandler( new TimerHandler( goods.getDuration(), false, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler pTimerHandler)
			{
				bonus_activated = false;
			}
		}));
		
		return true;
	}
	
	public boolean isBonusActivated()
	{
		return bonus_activated;
	}
}
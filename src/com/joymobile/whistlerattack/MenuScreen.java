package com.joymobile.whistlerattack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.HorizontalAlign;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.scoreloop.client.android.core.controller.AchievementsController;
import com.scoreloop.client.android.core.controller.RequestController;
import com.scoreloop.client.android.core.controller.RequestControllerObserver;
import com.scoreloop.client.android.core.model.Achievement;
import com.scoreloop.client.android.core.model.Continuation;
import com.scoreloop.client.android.ui.LeaderboardsScreenActivity;
import com.scoreloop.client.android.ui.OnScoreSubmitObserver;
import com.scoreloop.client.android.ui.ScoreloopManagerSingleton;

class MenuScreen extends Screen implements OnScoreSubmitObserver, RequestControllerObserver
{
	HashMap<String, BitmapTextureAtlas> atlases = new HashMap<String, BitmapTextureAtlas>();
	HashMap<String, ITiledTextureRegion> tiled_textures = new HashMap<String, ITiledTextureRegion>();
	HashMap<String, ITextureRegion> textures = new HashMap<String, ITextureRegion>();
	
	MainActivity main;
	Scene scene;
	AchievementsController myAchievementsController;
	
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
	
	@Override
	public void onStartScreen( String[] args )
	{
		main.getMusicManager().setMasterVolume( 1f );
		
		float x = attachBackground();
    	
    	final Entity bottom = new Entity();
    	scene.attachChild( bottom );
    	final Entity top = new Entity();
    	scene.attachChild( top );
    	
    	final Random rand = new Random();
    	
    	attachDiablo( x, bottom );
    	attachClouds( bottom, rand );
    	attachInitialCloud( bottom, rand );
    	attachSecondInitialCloud( bottom, rand );
    	attachEnterEffect();
    	attachLogo( top );
    	attachQuestsButton( bottom );
    	attachStoreButton( bottom );
    	attachPlayButton( bottom );
    	attachAchievementsButton( bottom );
    	attachHighScoresButton( bottom );
    	attachMusicButton( x, bottom );
    	attachSoundButton( x, bottom );
    	attachFxButton( x, bottom );
	}

	private void attachSoundButton( float x, final Entity bottom )
	{
		final SoundManager smanager = main.getSoundManager();
		
		final Sprite cross = new Sprite( x+main.w( 0.2 )+main.h( 0.15 ), main.h( 0.05 ), textures.get( "cross" ), main.getVertexBufferObjectManager() );
    	Sprite bkg = new Sprite( x+main.w( 0.2 )+main.h( 0.15 ), main.h( 0.05 ), textures.get( "sound" ), main.getVertexBufferObjectManager() )
    	{

			@Override
			public boolean onAreaTouched( TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY )
			{
				if ( pSceneTouchEvent.isActionUp() )
				{
					if ( smanager instanceof MutableSoundManager )
			    	{
			    		MutableSoundManager mutmmanager = (MutableSoundManager)smanager;
			    		
			    		if ( mutmmanager.isMuted() )
			    		{
			    			mutmmanager.unmute( 1f );
			    			main.runOnUpdateThread( new Runnable()
			    			{
								@Override
								public void run()
								{
									cross.detachSelf();
								}
			    			});
			    		}
			    		else
			    		{
			    			mutmmanager.mute();
			    			bottom.attachChild( cross );
			    		}
			    	}
				}
				return super.onAreaTouched( pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY );
			}
    	};
    	
    	bkg.setAlpha( 0 );
    	cross.setAlpha( 0 );
    	bkg.registerEntityModifier( new SequenceEntityModifier(
    		new DelayModifier( 3 ),
    		new AlphaModifier( 1, 0, 1 )
    	));
    	
    	bottom.attachChild( bkg );
    	scene.registerTouchArea( bkg );
    	
    	if ( smanager instanceof MutableSoundManager )
    	{
    		MutableSoundManager mutsmanager = (MutableSoundManager)smanager;
    		
    		if ( mutsmanager.isMuted() )
    		{
    			bottom.attachChild( cross );
    		}
    	}
	}
	
	private void attachFxButton( float x, final Entity bottom )
	{
		final Sprite cross = new Sprite( x+main.w( 0.2 )+main.h( 0.3 ), main.h( 0.05 ), textures.get( "cross" ), main.getVertexBufferObjectManager() );
    	Sprite bkg = new Sprite( x+main.w( 0.2 )+main.h( 0.3 ), main.h( 0.05 ), textures.get( "fx" ), main.getVertexBufferObjectManager() )
    	{

			@Override
			public boolean onAreaTouched( TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY )
			{
				if ( pSceneTouchEvent.isActionUp() )
				{
					if ( Database.getBoolean( "stars_disabled" ) )
			    	{
						Database.setBoolean( "stars_disabled", false );
						main.runOnUpdateThread( new Runnable()
		    			{
							@Override
							public void run()
							{
								cross.detachSelf();
							}
		    			});
			    	}
					else
					{
						Database.setBoolean( "stars_disabled", true );
						bottom.attachChild( cross );
			    	}
				}
				return super.onAreaTouched( pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY );
			}
    	};
    	
    	bkg.setAlpha( 0 );
    	cross.setAlpha( 0 );
    	bkg.registerEntityModifier( new SequenceEntityModifier(
    		new DelayModifier( 3 ),
    		new AlphaModifier( 1, 0, 1 )
    	));
    	
    	bottom.attachChild( bkg );
    	scene.registerTouchArea( bkg );
    	
    	if ( Database.getBoolean( "stars_disabled" ) )
    	{
    		bottom.attachChild( cross );
    	}
	}
	
	private void attachMusicButton( float x, final Entity bottom )
	{
		final MusicManager mmanager = main.getMusicManager();
		
		final Sprite cross = new Sprite( x+main.w( 0.2 ), main.h( 0.05 ), textures.get( "cross" ), main.getVertexBufferObjectManager() );
    	Sprite bkg = new Sprite( x+main.w( 0.2 ), main.h( 0.05 ), textures.get( "music" ), main.getVertexBufferObjectManager() )
    	{

			@Override
			public boolean onAreaTouched( TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY )
			{
				if ( pSceneTouchEvent.isActionUp() )
				{
					if ( mmanager instanceof MutableMusicManager )
			    	{
			    		MutableMusicManager mutmmanager = (MutableMusicManager)mmanager;
			    		
			    		if ( mutmmanager.isMuted() )
			    		{
			    			mutmmanager.unmute( 1f );
			    			main.runOnUpdateThread( new Runnable()
			    			{
								@Override
								public void run()
								{
									cross.detachSelf();
								}
			    			});
			    		}
			    		else
			    		{
			    			mutmmanager.mute();
			    			bottom.attachChild( cross );
			    		}
			    	}
				}
				return super.onAreaTouched( pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY );
			}
    	};
    	
    	bkg.setAlpha( 0 );
    	cross.setAlpha( 0 );
    	bkg.registerEntityModifier( new SequenceEntityModifier(
    		new DelayModifier( 3 ),
    		new AlphaModifier( 1, 0, 1 )
    	));
    	
    	bottom.attachChild( bkg );
    	scene.registerTouchArea( bkg );
    	
    	if ( mmanager instanceof MutableMusicManager )
    	{
    		MutableMusicManager mutmmanager = (MutableMusicManager)mmanager;
    		
    		if ( mutmmanager.isMuted() )
    		{
    			bottom.attachChild( cross );
    		}
    	}
	}

	private float attachBackground()
	{
		float x = -( main.h(1.828) - main.w(1) ) / 2;
    	Sprite bkg = new Sprite( x, 0, textures.get( "menu_background" ), main.getVertexBufferObjectManager() );
    	scene.attachChild( bkg );
		return x;
	}

	private void attachHighScoresButton( final Entity bottom )
	{
		final Sprite bttn6 = new Sprite( main.w(1), main.h(0.82), textures.get( "button" ), main.getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			if ( pSceneTouchEvent.isActionUp() )
    			{
    				main.mTap.play();

					main.runOnUiThread( new Runnable()
        			{
        				public void run()
        				{
    						ScoreloopManagerSingleton.get().askUserToAcceptTermsOfService( main, new Continuation<Boolean>() 
	    					{
	    					    public void withValue(final Boolean value, final Exception error) 
	    					    {
	    					        if (value != null && value) 
	    					        {
	    					        	if ( Database.getBoolean( "scoreloop" ) == false )
	    					        	{
	    					        		if ( Database.getInt( "record" ) > 0 )
	    					        		{
	    					        			ScoreloopManagerSingleton.get().setOnScoreSubmitObserver( MenuScreen.this );
	    										ScoreloopManagerSingleton.get().onGamePlayEnded( (double)( Database.getInt( "record" ) ), null );
	    					        		}
	    					        		
	    					        		for ( Feat feat : (ArrayList<Feat>)FeatProxy.get().getTasks() )
	    					        		{
	    					        			if ( feat.isUnlocked() )
	    					        			{
	    					        				ScoreLoopFeats.get().requestAccomplish( feat );
	    					        			}
	    					        		}
	    					        		
	    					        		myAchievementsController = new AchievementsController( MenuScreen.this );
	    					        		myAchievementsController.loadAchievements();
	    					        	}
	    					        	
	    					        	Database.setBoolean( "scoreloop", true );
	    					        	scene.setChildScene( new Scene(), false, true, true );
	            			    		main.startActivity( new Intent( main, LeaderboardsScreenActivity.class ) );
	            			        	if (scene.hasChildScene())
	            	    				{
	            	    					scene.clearChildScene();
	            	    				}
	    					        }
	    					    }
	    					 });
        				}
    				});
    			}
    			
    			return true;
    		}
    	};
    	bottom.attachChild( bttn6 );
    	bttn6.registerEntityModifier( new SequenceEntityModifier(
    			new DelayModifier( 2f ),
    			new MoveXModifier( 0.4f, main.w(1), main.w(0.5f) ),
    			new MoveXModifier( 0.1f, main.w(0.5f), main.w(0.65f) ),
    			new MoveXModifier( 0.1f, main.w(0.65f), main.w(0.5f) ),
    			new MoveXModifier( 0.1f, main.w(0.5f), main.w(0.60f) ),
    			new MoveXModifier( 0.1f, main.w(0.60f), main.w(0.5f) )
		));
    	scene.registerTouchArea( bttn6 );
    	
    	Text bttnText6 = new Text( main.w(1), main.h(0.845f), main.mFont, "HIGH-SCORES", "HIGH-SCORES".length(), main.getVertexBufferObjectManager() );
    	float add6 = (bttn6.getWidth() - bttnText6.getWidth() ) / 2;
    	scene.attachChild( bttnText6 );
    	bttnText6.registerEntityModifier( new SequenceEntityModifier(
    			new DelayModifier( 2f ),
    			new MoveXModifier( 0.4f, main.w(1)+add6, main.w(0.5f)+add6 ),
    			new MoveXModifier( 0.1f, main.w(0.5f)+add6, main.w(0.65f)+add6 ),
    			new MoveXModifier( 0.1f, main.w(0.65f)+add6, main.w(0.5f)+add6 ),
    			new MoveXModifier( 0.1f, main.w(0.5f)+add6, main.w(0.60f)+add6 ),
    			new MoveXModifier( 0.1f, main.w(0.60f)+add6, main.w(0.5f)+add6 )
		));
	}

	private void attachAchievementsButton( final Entity bottom )
	{
		final Sprite bttn5 = new Sprite( -main.w(0.33f), main.h(0.82), textures.get( "button" ), main.getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			main.mTap.play();
    			main.changeScreen( new FeatsScreen() );
    			
    			return true;
    		}
    	};
    	bottom.attachChild( bttn5 );
    	bttn5.registerEntityModifier( new SequenceEntityModifier(
    			new DelayModifier( 2f ),
    			new MoveXModifier( 0.4f, -main.w(0.33f), main.w(0.167f) ),
    			new MoveXModifier( 0.1f, main.w(0.167f), main.w(0.027f) ),
    			new MoveXModifier( 0.1f, main.w(0.027f), main.w(0.167f) ),
    			new MoveXModifier( 0.1f, main.w(0.167f), main.w(0.077f) ),
    			new MoveXModifier( 0.1f, main.w(0.077f), main.w(0.167f) )
		));
    	scene.registerTouchArea( bttn5 );
    	
    	Text bttnText5 = new Text( -main.w(0.33f), main.h(0.845f), main.mFont, "ACHIEVEMENTS", "ACHIEVEMENTS".length(), main.getVertexBufferObjectManager() );
    	float add5 = (bttn5.getWidth() - bttnText5.getWidth() ) / 2;
    	scene.attachChild( bttnText5 );
    	bttnText5.registerEntityModifier( new SequenceEntityModifier(
    			new DelayModifier( 2f ),
    			new MoveXModifier( 0.4f, -main.w(0.33f)+add5, main.w(0.167f)+add5 ),
    			new MoveXModifier( 0.1f, main.w(0.167f)+add5, main.w(0.027f)+add5 ),
    			new MoveXModifier( 0.1f, main.w(0.027f)+add5, main.w(0.167f)+add5 ),
    			new MoveXModifier( 0.1f, main.w(0.167f)+add5, main.w(0.077f)+add5 ),
    			new MoveXModifier( 0.1f, main.w(0.077f)+add5, main.w(0.167f)+add5 )
		));
	}

	private void attachPlayButton( final Entity bottom )
	{
		final Sprite bttn4 = new Sprite( main.w(0.15f), main.h(1.1f), textures.get( "button3" ), main.getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			main.mTap.play();
    			
    			if ( Database.getBoolean( "tutorial" ) )
    			{
    				if ( ! Database.getBoolean( "onStartGame_message_1" ) )
    				{
    					Database.setBoolean( "onStartGame_message_1", true );
    					showMessage();
    				}
    				else
    				{
    					main.changeScreen( new DailyRewardScreen() );
    				}
    			}
    			else
    			{
    				main.changeScreen( new TutorialGameScreen() );
    			}
    			
    			return true;
    		}
    	};
    	bottom.attachChild( bttn4 );
    	bttn4.registerEntityModifier( new SequenceEntityModifier(
    			new DelayModifier( 1f ),
    			new MoveYModifier( 0.4f, main.h(1.1f), main.h(0.47f) )
		));
    	scene.registerTouchArea( bttn4 );
    	
    	Text bttnText4 = new Text( main.w(0.15f), main.h(1.115f), main.mFont2, "P  L  A  Y", "P  L  A  Y".length(), main.getVertexBufferObjectManager() );
    	float add4 = (bttn4.getWidth() - bttnText4.getWidth() ) / 2;
    	bttnText4.setX( bttn4.getX() + add4 );
    	scene.attachChild( bttnText4 );
    	bttnText4.registerEntityModifier( new SequenceEntityModifier(
    			new DelayModifier( 1f ),
    			new MoveYModifier( 0.4f, main.h(1.115f), main.h(0.485f) )
		));
	}
	
	private void showMessage()
	{
		Scene child = new Scene();
		
		final Rectangle rect2 = new Rectangle( 0, 0, main.w(1), main.h(1), main.getVertexBufferObjectManager() );
		rect2.setColor(0, 0, 0);
		rect2.setAlpha( 0.85f );
		child.attachChild( rect2 );
		
		String text = "Do you want to enable FX during game?\n" +
				"We don't recommend enabling it on\n" +
				"low-end devices.";
		final Text textComponent = new Text( main.w(0), main.h(0.2f), main.mFont, text, text.length(), main.getVertexBufferObjectManager() );
		textComponent.setHorizontalAlign( HorizontalAlign.CENTER );
		textComponent.setX( ( main.w(1) - textComponent.getWidth() ) / 2 );
		child.attachChild( textComponent );
		
		float x = -( main.h(1.828) - main.w(1) ) / 2;
		Sprite restart = new Sprite( main.h(0.5)+x, main.h(0.6), textures.get( "tick_button" ), main.getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY )
    		{
    			if ( pSceneTouchEvent.isActionDown() )
    			{
    				main.mTap.play();
    				scene.clearChildScene();
    				Database.setBoolean( "stars_disabled", false );
    				main.changeScreen( new DailyRewardScreen() );
    			}
    			
    			return true;
    		}
    	};
    	
    	child.attachChild( restart );
    	child.registerTouchArea( restart );
    	Sprite exit = new Sprite( main.h(1.1)+x, main.h(0.6), textures.get( "exit_button" ), main.getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY )
    		{
    			if ( pSceneTouchEvent.isActionDown() )
    			{
    				main.mTap.play();
        			scene.clearChildScene();
        			Database.setBoolean( "stars_disabled", true );
        			main.changeScreen( new DailyRewardScreen() );
    			}
    			
    			return true;
    		}
    	};
    	child.attachChild( exit );
    	child.registerTouchArea( exit );
		
		child.setBackgroundEnabled( false );
		scene.clearChildScene();
		scene.setChildScene( child, false, true, true );
	}

	private void attachStoreButton( final Entity bottom )
	{
		final Sprite bttn3 = new Sprite( -main.w(0.33f), main.h(0.67f), textures.get( "button" ), main.getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			main.mTap.play();
    			main.changeScreen( new StoreScreen() );
    			return true;
    		}
    	};
    	bottom.attachChild( bttn3 );
    	bttn3.registerEntityModifier( new SequenceEntityModifier(
    			new DelayModifier( 1.8f ),
    			new MoveXModifier( 0.4f, -main.w(0.33f), main.w(0.167f) ),
    			new MoveXModifier( 0.1f, main.w(0.167f), main.w(0.027f) ),
    			new MoveXModifier( 0.1f, main.w(0.027f), main.w(0.167f) ),
    			new MoveXModifier( 0.1f, main.w(0.167f), main.w(0.077f) ),
    			new MoveXModifier( 0.1f, main.w(0.077f), main.w(0.167f) )
		));
    	scene.registerTouchArea( bttn3 );
    	
    	Text bttnText3 = new Text( -main.w(0.33f), main.h(0.695f), main.mFont, "S T O R E", "S T O R E".length(), main.getVertexBufferObjectManager() );
    	float add3 = (bttn3.getWidth() - bttnText3.getWidth() ) / 2;
    	scene.attachChild( bttnText3 );
    	bttnText3.registerEntityModifier( new SequenceEntityModifier(
    			new DelayModifier( 1.8f ),
    			new MoveXModifier( 0.4f, -main.w(0.33f)+add3, main.w(0.167f)+add3 ),
    			new MoveXModifier( 0.1f, main.w(0.167f)+add3, main.w(0.027f)+add3 ),
    			new MoveXModifier( 0.1f, main.w(0.027f)+add3, main.w(0.167f)+add3 ),
    			new MoveXModifier( 0.1f, main.w(0.167f)+add3, main.w(0.077f)+add3 ),
    			new MoveXModifier( 0.1f, main.w(0.077f)+add3, main.w(0.167f)+add3 )
		));
	}

	private void attachQuestsButton( final Entity bottom )
	{
		final Sprite bttn = new Sprite( main.w(1), main.h(0.67f), textures.get( "button" ), main.getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			main.mTap.play();
    			main.changeScreen( new QuestsScreen(), new String[]{"simple"} );
    			return true;
    		}
    	};
    	bottom.attachChild( bttn );
    	bttn.registerEntityModifier( new SequenceEntityModifier(
    			new DelayModifier( 1.8f ),
    			new MoveXModifier( 0.4f, main.w(1), main.w(0.5f) ),
    			new MoveXModifier( 0.1f, main.w(0.5f), main.w(0.65f) ),
    			new MoveXModifier( 0.1f, main.w(0.65f), main.w(0.5f) ),
    			new MoveXModifier( 0.1f, main.w(0.5f), main.w(0.60f) ),
    			new MoveXModifier( 0.1f, main.w(0.60f), main.w(0.5f) )
		));
    	scene.registerTouchArea( bttn );
    	
    	Text bttnText = new Text( main.w(1), main.h(0.695f), main.mFont, "Q U E S T S", "Q U E S T S".length(), main.getVertexBufferObjectManager() );
    	float add = (bttn.getWidth() - bttnText.getWidth() ) / 2;
    	scene.attachChild( bttnText );
    	bttnText.registerEntityModifier( new SequenceEntityModifier(
    			new DelayModifier( 1.8f ),
    			new MoveXModifier( 0.4f, main.w(1)+add, main.w(0.5f)+add ),
    			new MoveXModifier( 0.1f, main.w(0.5f)+add, main.w(0.65f)+add ),
    			new MoveXModifier( 0.1f, main.w(0.65f)+add, main.w(0.5f)+add ),
    			new MoveXModifier( 0.1f, main.w(0.5f)+add, main.w(0.60f)+add ),
    			new MoveXModifier( 0.1f, main.w(0.60f)+add, main.w(0.5f)+add )
		));
	}

	private void attachLogo( final Entity top )
	{
		final Sprite logo = new Sprite( main.w(0.5f) - main.h(1.828 * 0.33f), main.h(0.1f), textures.get( "logo" ), main.getVertexBufferObjectManager() );
    	top.attachChild( logo );
    	
    	scene.registerUpdateHandler( new TimerHandler( 0.5f, false, new ITimerCallback()
    	{
			public void onTimePassed(TimerHandler pTimerHandler)
			{
				logo.registerEntityModifier( new MoveYModifier( 1, main.h(0.1f), -main.h(0.03f) ) );
	    		logo.registerEntityModifier( new SequenceEntityModifier(
	    				new ScaleModifier( 1, 1, 0.83f ),
	    				new LoopEntityModifier( 
	    						new SequenceEntityModifier(
	    							new DelayModifier( 3 ),
	    							new RotationModifier( 0.1f, 0, 15 ),
	    							new RotationModifier( 0.1f, 15, -15 ),
	    							new RotationModifier( 0.1f, -15, 0 )
	    						)
	    				)
	    		));
			}
    		
    	}) );
	}

	private void attachEnterEffect()
	{
		final Rectangle rect = new Rectangle( 0, 0, main.w(1), main.h(1), main.getVertexBufferObjectManager() );
		rect.setColor(0, 0, 0);
		rect.setAlpha( 0.7f );
		scene.attachChild( rect );
		rect.registerEntityModifier( new AlphaModifier( 2, 0.7f, 0 ) );
	}

	private void attachSecondInitialCloud( final Entity bottom, final Random rand )
	{
		final Sprite cloud3 = new Sprite( main.w(0.9f), main.h(0.1f), textures.get( "cloud" ), main.getVertexBufferObjectManager() );
		cloud3.setScale( rand.nextFloat()*1.5f+0.5f );
		cloud3.setAlpha( 0 );
    	bottom.attachChild( cloud3 );
    	cloud3.registerEntityModifier( new AlphaModifier( 2, 0, 1 ) );
    	cloud3.registerEntityModifier( new MoveXModifier( 35f, main.w(0.7f), -main.w(0.2f) ) );
    	scene.registerUpdateHandler( new TimerHandler( 35f, true, new ITimerCallback()
    	{
			public void onTimePassed( TimerHandler pTimerHandler ) 
			{
				main.runOnUpdateThread( new Runnable()
				{
					public void run() 
					{
						cloud3.detachSelf();
					}
				});
			}
    	} ));
	}

	private void attachInitialCloud( final Entity bottom, final Random rand )
	{
		final Sprite cloud = new Sprite( main.w(0.2f), main.h(0.1f), textures.get( "cloud" ), main.getVertexBufferObjectManager() );
		cloud.setScale( rand.nextFloat()*1.5f+0.5f );
		cloud.setAlpha( 0 );
    	bottom.attachChild( cloud );
    	cloud.registerEntityModifier( new AlphaModifier( 2, 0, 1 ) );
    	cloud.registerEntityModifier( new MoveXModifier( 8f, main.w(0.2f), -main.w(0.2f) ) );
    	scene.registerUpdateHandler( new TimerHandler( 8f, true, new ITimerCallback()
    	{
			public void onTimePassed( TimerHandler pTimerHandler ) 
			{
				main.runOnUpdateThread( new Runnable()
				{
					public void run() 
					{
						cloud.detachSelf();
					}
				});
			}
    	} ));
	}

	private void attachClouds( final Entity bottom, final Random rand )
	{
		scene.registerUpdateHandler( new TimerHandler( rand.nextFloat()*3+5, true, new ITimerCallback()
    	{
			public void onTimePassed( TimerHandler pTimerHandler ) 
			{
				float height = rand.nextFloat()/4;
				String[] clouds = new String[]{ "cloud", "cloud2" };
				final Sprite cloud = new Sprite( main.w(1), main.h(height), textures.get( clouds[rand.nextInt(clouds.length)] ), main.getVertexBufferObjectManager() );
				
				cloud.setScale( rand.nextFloat()*1.5f+0.5f );
		    	bottom.attachChild( cloud );
		    	
		    	int terminate_time = 25 + rand.nextInt(20);
		    	cloud.registerEntityModifier( new MoveXModifier( terminate_time, main.w(1.2), -main.w(0.2f) ) );
		    	
		    	scene.registerUpdateHandler( new TimerHandler( terminate_time, true, new ITimerCallback()
		    	{
					public void onTimePassed( TimerHandler pTimerHandler ) 
					{
						main.runOnUpdateThread( new Runnable()
						{
							public void run() 
							{
								cloud.detachSelf();
							}
						} );
					}
		    	} ));
		    	
		    	pTimerHandler.setTimerSeconds( rand.nextFloat()*3+5 );
			}
    	}));
	}

	private void attachDiablo( float x, final Entity bottom )
	{
		Sprite diablo = new Sprite( main.h(1.828 * 0.63) + x, -main.h(0.1f), textures.get( "diablo" ), main.getVertexBufferObjectManager() );
    	bottom.attachChild( diablo );
    	
    	diablo.registerEntityModifier( 
    			new LoopEntityModifier(
    				new RotationModifier( 4, 0, 360 )
    			)
    	);
	}
	
	@Override
	public void onExit()
	{
		scene.clearChildScene();
		
		String text = "Would you like to exit game?";
		textComponent = new Text( main.w(0), main.h(0.2f), main.mFont, text, text.length(), main.getVertexBufferObjectManager() );
		textComponent.setHorizontalAlign( HorizontalAlign.CENTER );
		textComponent.setX( ( main.w(1) - textComponent.getWidth() ) / 2 );
		textComponent.setY( ( main.h(1) - textComponent.getHeight() ) / 2 );
    	main.exit_scene.attachChild( textComponent );
		scene.setChildScene( main.exit_scene, false, true, true );
	}
	
	@Override
	public void onExitReturn()
	{
		scene.clearChildScene();
		main.runOnUpdateThread( new Runnable()
		{
			public void run() 
			{
				textComponent.detachSelf();
			}
		});
	}

	public void onScoreSubmit(int status, Exception error) 
	{
		Log.d( "SYNCHRONIZE", ""+status );
	}

	public void requestControllerDidFail(RequestController arg0, Exception arg1)
	{
		
	}

	public void requestControllerDidReceiveResponse( RequestController arg0 )
	{
		 List<Achievement> list = myAchievementsController.getAchievements();
		 
		 for ( Achievement feat : list )
		 {
			 if ( feat.isAchieved() )
			 {
				 Database.setBoolean( feat.getAward().getIdentifier(), true );
			 }
		 }
	}
}
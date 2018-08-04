package com.joymobile.whistlerattack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import com.android.vending.billing.IInAppBillingService;
import com.chartboost.sdk.Chartboost;
import com.jirbo.adcolony.AdColony;
import com.jirbo.adcolony.AdColonyV4VCListener;
import org.joymobile.whistlerattack.R;
import com.joymobile.whistlerattack.goods.PackGoods;
import com.playhaven.src.common.PHConfig;
import com.playhaven.src.publishersdk.content.PHPublisherContentRequest;
import com.playhaven.src.publishersdk.content.PHPublisherContentRequest.RewardDelegate;
import com.playhaven.src.publishersdk.content.PHReward;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyConnectFlag;
import com.tapjoy.TapjoyEarnedPointsNotifier;
import com.tapjoy.TapjoyNotifier;

public class MainActivity extends SimpleBaseGameActivity implements AdColonyV4VCListener, TapjoyNotifier, TapjoyEarnedPointsNotifier, RewardDelegate
{
	public IInAppBillingService mService;

	ServiceConnection mServiceConn = new ServiceConnection() 
	{
		@Override
		public void onServiceDisconnected( ComponentName name )
		{
			mService = null;
		}

		@Override
		public void onServiceConnected( ComponentName name, IBinder service )
		{
			mService = IInAppBillingService.Stub.asInterface( service );
		}
	};
	
	public static int CAMERA_WIDTH = 720;
	public static int CAMERA_HEIGHT = 480;
	
	public BitmapTextureAtlas whistlerAtlas, freezeAtlas;

	public ITiledTextureRegion whistlerTexture, freezeTexture;
	
	public HashMap<String, BitmapTextureAtlas> atlases = new HashMap<String, BitmapTextureAtlas>();
	public HashMap<String, ITiledTextureRegion> tiled_textures = new HashMap<String, ITiledTextureRegion>();
	public HashMap<String, ITextureRegion> textures = new HashMap<String, ITextureRegion>();
	Font mFont, mFont2, mFont3, mFont4, mFont5;
	Music mMusic;
	public Sound mExplosionSound, mPowerupSound, mBell, mCombo, mMetal, mMissed, mStar, mBomb, mGameOver, mStarting, mTap, mCoins;
	
	Sprite logo, tree;
	Scene scene, pause, exit_scene;
	public Screen screen;
	
	Camera camera;
	
	public Chartboost cb;
	
	boolean game_loaded = false;
	boolean paused = false, exited = false;
	
	boolean resources_loaded = false;
	boolean time_elapsed = false;
	
	PHPublisherContentRequest opening, exiting;
	
	@Override
	public void onCreate( Bundle savedInstance )
	{
		super.onCreate( savedInstance );
		
		bindService( new 
		        Intent( "com.android.vending.billing.InAppBillingService.BIND" ),
		                mServiceConn, Context.BIND_AUTO_CREATE );
		
		String appId = "";
		String appSignature = "";
		cb.onCreate(this, appId, appSignature, null);
		cb.startSession();
		cb.cacheInterstitial( "store" );
		cb.cacheInterstitial( "feats" );
		cb.cacheInterstitial( "menu_return" );
		
		/*Intent serviceIntent = new Intent();
		serviceIntent.setAction( "com.joymobile.whistlerattack.BootService" );
		startService(serviceIntent);*/
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) 
	{
		if ( (pKeyCode == KeyEvent.KEYCODE_MENU ) && pEvent.getAction() == KeyEvent.ACTION_DOWN ) 
		{
			//togglePause();
			return true;
		}
		else if ( pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN )
		{
			if ( game_loaded )
			{
				toggleExitScene();
			}
	        return true;
		}
		else 
		{
			return super.onKeyDown(pKeyCode, pEvent);			
		}
	}
	
	@Override
	public MusicManager getMusicManager()
	{
		return new MutableMusicManager( super.getMusicManager() );
	}
	
	@Override
	public SoundManager getSoundManager()
	{
		return new MutableSoundManager( super.getSoundManager() );
	}
	
	public void togglePause()
	{
		if ( paused ) 
		{
			getMusicManager().setMasterVolume( 1f );
			paused = false;
			if ( screen != null )
				screen.onResume();
		} 
		else 
		{
			paused = true;
			getMusicManager().setMasterVolume( 0f );
			if ( screen != null )
				screen.onPause();
		}
	}
	
	public void toggleExitScene()
	{
		if ( exited ) 
		{
			exited = false;
			getMusicManager().setMasterVolume( 1f );
			if ( screen != null )
				screen.onExitReturn();
		} 
		else 
		{
			exited = true;
			getMusicManager().setMasterVolume( 0f );
			if ( screen != null )
				screen.onExit();
		}
	}
	
	public EngineOptions onCreateEngineOptions()
	{
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		CAMERA_WIDTH = metrics.widthPixels;
		CAMERA_HEIGHT = metrics.heightPixels;

		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT), camera);
		engineOptions.getRenderOptions().setDithering(true);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().getSoundOptions().setMaxSimultaneousStreams(10);

		cb = Chartboost.sharedChartboost();
		
		return engineOptions;
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		AdColony.pause();
		TapjoyConnect.getTapjoyConnectInstance().enableDisplayAdAutoRefresh(false);
		
		paused = false;
		togglePause();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		if ( game_loaded )
		{
			paused = true;
			togglePause();
		}
			
		AdColony.resume( this );
	}
	
	@Override
	protected void onStart() {
	    super.onStart();
	    cb.onStart(this);
	}

	@Override
	protected void onStop() {
	    super.onStop();

	    cb.onStop(this);
	}

	@Override
	public void onBackPressed() {

	    // If an interstitial is on screen, close it. Otherwise continue as normal.
	    if (this.cb.onBackPressed())
	        return;
	    else
	        super.onBackPressed();
	}
	
	@Override
	protected void onDestroy()
	{
		TapjoyConnect.getTapjoyConnectInstance().sendShutDownEvent();
		super.onDestroy();
		
		 if (mServiceConn != null) 
		 {
			 unbindService(mServiceConn);
		 }  
		 
		cb.onDestroy(this);
	}
	
	@Override
	protected void onCreateResources()
	{
    	atlases.put( "joymobile", makeAtlas( h(0.9f), h(0.3f) ) );
    	textures.put( "joymobile", makeTexture( atlases.get( "joymobile" ), "gfx/joymobile.svg", h(0.9f), h(0.3f) ) );
    	
    	try
    	{
	    	mMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "gfx/watwat.mp3");
			mMusic.setLooping(true);
		} 
		catch (final IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected Scene onCreateScene() 
	{
		Database.initialize( getApplicationContext() );
		mMusic.play();
		getMusicManager().setMasterVolume( 1f );
		getSoundManager().setMasterVolume( 1f );
		
		PHConfig.token = "";
		PHConfig.secret = "";
		
		runOnUiThread( new Runnable()
		{
			public void run() 
			{
				AdColony.configure(
						MainActivity.this, // Activity reference
						"1.0", // Arbitrary app version
						"", // ADC App ID from adcolony.com
						"" // Video Zone ID #1 from adcolony.com
				);
				AdColony.addV4VCListener( MainActivity.this );
			}
		});
		
		Hashtable<String, String> flags = new Hashtable<String, String>();
		flags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
		TapjoyConnect.requestTapjoyConnect(getApplicationContext(), "", "", flags);
    	
        scene = new Scene();
        
        Rectangle rect = new Rectangle( 0, 0, w(1), h(1), getVertexBufferObjectManager() );
        //rect.setColor( 0, 0, 0 );
		scene.attachChild( rect );
		
		Sprite logo = new Sprite( w(0.5f) - h(0.4f), h(0.35f), textures.get( "joymobile" ), getVertexBufferObjectManager() );
		logo.setAlpha( 0 );
    	scene.attachChild( logo );
    	
    	logo.registerEntityModifier( new AlphaModifier( 2, 0, 1 ) );
    	
    	scene.registerUpdateHandler( new TimerHandler( 4, false, new ITimerCallback()
    	{
			public void onTimePassed( TimerHandler pTimerHandler ) 
			{
				if ( resources_loaded )
				{
					onLoadingStart();
				}
				else
				{
					time_elapsed = true;
				}
			}
    	}));

    	final IAsyncCallback callback = new IAsyncCallback() 
        {
            public void workToDo()
            {
            	atlases.put( "menu_background", makeAtlas( h(1.828), h(1) ) );
        		textures.put( "menu_background", makeTexture( atlases.get( "menu_background" ), "gfx/back.svg", h(1.828), h(1) ) );
        		
        		atlases.put( "diablo", makeAtlas( h(1.828 * 0.35), h(1.828 * 0.35) ) );
        		textures.put( "diablo", makeTexture( atlases.get( "diablo" ), "gfx/back3.svg", h(1.828 * 0.35), h(1.828 * 0.35) ) );
        		
        		atlases.put( "progress_dynamic", makeAtlas( h(1.828 * 0.4f), h(0.1f) ) );
        		textures.put( "progress_dynamic", makeTexture( atlases.get( "progress_dynamic" ), "gfx/progress_dynamic.svg", h(1.828 * 0.4f), h(0.1f) ) );
        		
        		atlases.put( "pause", makeAtlas( h(1.828), h(1) ) );
        		textures.put( "pause", makeTexture( atlases.get( "pause" ), "gfx/pause.svg", h(1.828), h(1) ) );
        		
        		atlases.put( "restart_button", makeAtlas( w(0.15), w(0.15) ) );
        		textures.put( "restart_button", makeTexture( atlases.get( "restart_button" ), "gfx/restartbttn.svg", w(0.15), w(0.15) ) );
        		
        		atlases.put( "menu_button", makeAtlas( w(0.15), w(0.15) ) );
        		textures.put( "menu_button", makeTexture( atlases.get( "menu_button" ), "gfx/menubttn.svg", w(0.15), w(0.15) ) );
        		
        		atlases.put( "exit_button", makeAtlas( w(0.15), w(0.15) ) );
        		textures.put( "exit_button", makeTexture( atlases.get( "exit_button" ), "gfx/exitbttn.svg", w(0.15), w(0.15) ) );
        		
        		atlases.put( "tick_button", makeAtlas( w(0.15), w(0.15) ) );
        		textures.put( "tick_button", makeTexture( atlases.get( "tick_button" ), "gfx/tickbttn.svg", w(0.15), w(0.15) ) );
        		
        		atlases.put( "progress_static1", makeAtlas( h(1.828 * 0.395f), h(0.1f) ) );
        		textures.put( "progress_static1", makeTexture( atlases.get( "progress_static1" ), "gfx/progress_static1.svg", h(1.828 * 0.395f), h(0.1f) ) );
        		
        		atlases.put( "progress_static2", makeAtlas( h(1.828 * 0.4f), h(0.1f) ) );
        		textures.put( "progress_static2", makeTexture( atlases.get( "progress_static2" ), "gfx/progress_static2.svg", h(1.828 * 0.4f), h(0.1f) ) );
        		
        		atlases.put( "logo", makeAtlas( h(1.828 * 0.66f), h(0.66f) ) );
            	textures.put( "logo", makeTexture( atlases.get( "logo" ), "gfx/logo.svg", h(1.828 * 0.66f), h(0.66f) ) );
            	
        		try 
        		{
        			mExplosionSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/hit.mp3");
        			mPowerupSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/powerup.mp3");
        			mBell = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/bell.mp3");
        			mCombo = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/combo.mp3");
        			mMetal = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/metal.mp3");
        			mMissed = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/missed.mp3");
        			mStar = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/star.mp3");
        			mBomb = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/bomb.mp3");
        			mGameOver = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/gameover.mp3");
        			mStarting = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/starting.mp3");
        			mTap = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/tap.mp3");
        			mCoins = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.this, "gfx/coins.mp3");
        		} 
        		catch (final IOException e) 
        		{
        			e.printStackTrace();
        		}  	
            }

            public void onComplete() 
            {
            	if ( time_elapsed )
            	{
            		onLoadingStart();
            	}
            	else
            	{
            		resources_loaded = true;
            	}
            }
        };

        runOnUiThread( new Runnable()
        {
            public void run() 
            {
                new AsyncTaskLoader().execute( callback );
            }
        });

        scene.setTouchAreaBindingOnActionDownEnabled(true);
        scene.setTouchAreaBindingOnActionMoveEnabled(true);
        
        return scene;
    }
	
	protected void onLoadingStart()
	{
		float x = -( h(1.828) - w(1) ) / 2;
		
		attachPauseScene();
		attachExitScene();
    	
        scene = new Scene();
        mEngine.setScene(scene);
        
        new Thread( new Runnable()
        {
			@Override
			public void run()
			{
				opening = new PHPublisherContentRequest( MainActivity.this, "opening" );
				opening.setOnRewardListener( MainActivity.this );
				opening.preload();
				
				exiting = new PHPublisherContentRequest( MainActivity.this, "exit" );
				exiting.setOnRewardListener( MainActivity.this );
				exiting.preload();
			}
        }).start();
        
       
    	Sprite bkg = new Sprite( x, 0, textures.get( "menu_background" ), getVertexBufferObjectManager() );
    	scene.attachChild( bkg );
    	
		Sprite diablo = new Sprite( h(1.828 * 0.63) + x, -h(0.1f), textures.get( "diablo" ), getVertexBufferObjectManager() );
    	scene.attachChild( diablo );
    	
    	diablo.registerEntityModifier( 
    			new LoopEntityModifier(
    				new RotationModifier( 4, 0, 360 )
    			)
    	);
    	
    	Rectangle rect = new Rectangle( 0, 0, w(1), h(1), getVertexBufferObjectManager() );
		rect.setColor(0, 0, 0);
		rect.setAlpha( 0.7f );
		scene.attachChild( rect );
    	
    	Sprite logo = new Sprite( w(0.5f) - h(1.828 * 0.33f), h(0.1f), textures.get( "logo" ), getVertexBufferObjectManager() );
    	scene.attachChild( logo );
    	
    	Sprite progress = new Sprite( w(0.3f), h(0.7f), textures.get( "progress_dynamic" ), getVertexBufferObjectManager() );
    	scene.attachChild( progress );
    	
    	final Sprite progress_bar = new Sprite( w(0.28f), h(0.7f), textures.get( "progress_static1" ), getVertexBufferObjectManager() );
    	scene.attachChild( progress_bar );
    	
    	final Sprite progress_bar2 = new Sprite( w(0.3f), h(0.7f), textures.get( "progress_static2" ), getVertexBufferObjectManager() );
    	scene.attachChild( progress_bar2 );
    	
    	final IAsyncCallback callback = new IAsyncCallback() 
        {
        	float scale = 1.0f;
        	int segments = 84;
        	int assertion = 0;
        	
            public void workToDo()
            {
            	final int size = (int)(CAMERA_WIDTH * 0.6);
            	
            	atlases.put( "cloud", makeAtlas( h(0.2), h(0.2) ) );
        		textures.put( "cloud", makeTexture( atlases.get( "cloud" ), "gfx/cloud.svg", h(0.2), h(0.2) ) );
        		decrease();
        		
        		atlases.put( "cup", makeAtlas( h(0.5), h(0.5) ) );
        		textures.put( "cup", makeTexture( atlases.get( "cup" ), "gfx/cup.svg", h(0.5), h(0.5) ) );
        		
        		atlases.put( "cloud2", makeAtlas( h(0.2), h(0.2) ) );
        		textures.put( "cloud2", makeTexture( atlases.get( "cloud2" ), "gfx/cloud2.svg", h(0.2), h(0.2) ) );
        		decrease();
        		
        		atlases.put( "particle", makeAtlas( w(0.03), w(0.03) ) );
        		textures.put( "particle", makeTexture( atlases.get( "particle" ), "gfx/particle.svg", w(0.03), w(0.03) ) );
        		decrease();
        		
        		atlases.put( "coin", makeAtlas( h(0.1188), h(0.1188) ) );
        		textures.put( "coin", makeTexture( atlases.get( "coin" ), "gfx/particle.svg", h(0.1188), h(0.1188) ) );
        		decrease();
        		
        		atlases.put( "star", makeAtlas( h(0.08), h(0.08) ) );
        		textures.put( "star", makeTexture( atlases.get( "star" ), "gfx/particle.svg", h(0.08), h(0.08) ) );
        		decrease();
        		
        		final ITexture fontTexture = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        		mFont = FontFactory.createStrokeFromAsset(getFontManager(), fontTexture, getAssets(), "gfx/franchise.ttf", h(0.11388f), true, Color.WHITE, h(0.004166f), Color.BLACK  );
        		mFont.load();
        		decrease();
        		
        		final ITexture fontTexture2 = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        		mFont2 = FontFactory.createStrokeFromAsset(getFontManager(), fontTexture2, getAssets(), "gfx/franchise.ttf", h(0.1944f), true, Color.WHITE, h(0.005555f), Color.BLACK  );
        		mFont2.load();
        		decrease();
        		
        		final ITexture fontTexture3 = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        		mFont3 = FontFactory.createFromAsset(getFontManager(), fontTexture3, getAssets(), "gfx/franchise.ttf", h(0.066666f), true, Color.BLACK  );
        		mFont3.load();
        		decrease();
        		
        		final ITexture fontTexture5 = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        		mFont5 = FontFactory.createStrokeFromAsset(getFontManager(), fontTexture5, getAssets(), "gfx/franchise.ttf", h(0.066666f), true, Color.WHITE, h(0.0032f), Color.BLACK  );
        		mFont5.load();
        		decrease();
        		
        		final ITexture fontTexture4 = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        		mFont4 = FontFactory.createStrokeFromAsset(getFontManager(), fontTexture4, getAssets(), "gfx/franchise.ttf", h(0.09f), true, Color.WHITE, h(0.0032f), Color.BLACK  );
        		mFont4.load();
        		decrease();
        		
        		atlases.put( "explosion", makeAtlas( w(0.3), w(0.3) ) );
        		textures.put( "explosion", makeTexture( atlases.get( "explosion" ), "gfx/explosion.svg", w(0.3), w(0.3) ) );
        		decrease();
        		
        		atlases.put( "heart", makeAtlas( h(0.15), h(0.15) ) );
        		textures.put( "heart", makeTexture( atlases.get( "heart" ), "gfx/heart.svg", h(0.15), h(0.15) ) );
        		decrease();
        		
        		atlases.put( "fifteen", makeAtlas( w(0.15), w(0.15) ) );
        		textures.put( "fifteen", makeTexture( atlases.get( "fifteen" ), "gfx/fifteen.svg", w(0.15), w(0.15) ) );
        		decrease();
        		
        		/*atlases.put( "bomb_bonus", makeAtlas( w(0.07), w(0.07) ) );
        		textures.put( "bomb_bonus", makeTexture( atlases.get( "bomb_bonus" ), "gfx/bomb_bonus.svg", w(0.07), w(0.07) ) );
        		decrease();*/
        		
        		/*atlases.put( "goods", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "goods", makeTexture( atlases.get( "goods" ), "gfx/goods_card.svg", h(0.625), h(1) ) );
        		decrease();*/
        		
        		atlases.put( "reward_goods", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "reward_goods", makeTexture( atlases.get( "reward_goods" ), "gfx/reward_card.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "buy_button", makeAtlas( h(0.477), h(0.154) ) );
        		textures.put( "buy_button", makeTexture( atlases.get( "buy_button" ), "gfx/button.svg", h(0.477), h(0.154) ) );
        		decrease();
        		
        		atlases.put( "gray_button", makeAtlas( h(0.477), h(0.154) ) );
        		textures.put( "gray_button", makeTexture( atlases.get( "gray_button" ), "gfx/gray_button.svg", h(0.477), h(0.154) ) );
        		decrease();
        		
        		atlases.put( "green_button", makeAtlas( h(0.477), h(0.154) ) );
        		textures.put( "green_button", makeTexture( atlases.get( "green_button" ), "gfx/green_button.svg", h(0.477), h(0.154) ) );
        		decrease();

        		atlases.put( "headstart_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "headstart_thumb", makeTexture( atlases.get( "headstart_thumb" ), "gfx/thumbnails/headstart.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "mouse_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "mouse_thumb", makeTexture( atlases.get( "mouse_thumb" ), "gfx/thumbnails/mouse.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "farm_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "farm_thumb", makeTexture( atlases.get( "farm_thumb" ), "gfx/thumbnails/farm.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		/*atlases.put( "freeze_bonus", makeAtlas( w(0.07), w(0.07) ) );
        		textures.put( "freeze_bonus", makeTexture( atlases.get( "freeze_bonus" ), "gfx/freeze_bonus.svg", w(0.07), w(0.07) ) );
        		decrease();*/
        		
        		atlases.put( "frozen_bar", makeAtlas( w(0.1), h(0.8) ) );
        		textures.put( "frozen_bar", makeTexture( atlases.get( "frozen_bar" ), "gfx/frozen_bar.svg", w(0.1), h(0.8) ) );
        		decrease();
        		
        		/*atlases.put( "time_bonus", makeAtlas( w(0.07), w(0.07) ) );
        		textures.put( "time_bonus", makeTexture( atlases.get( "time_bonus" ), "gfx/time_bonus.svg", w(0.07), w(0.07) ) );
        		decrease();*/
        		
        		atlases.put( "treasure", makeAtlas( h(0.2), h(0.2) ) );
        		textures.put( "treasure", makeTexture( atlases.get( "treasure" ), "gfx/treasure.svg", h(0.2), h(0.2) ) );
        		decrease();
        		
        		/*atlases.put( "bonus_bar", makeAtlas( w(0.1), h(0.8) ) );
        		textures.put( "bonus_bar", makeTexture( atlases.get( "bonus_bar" ), "gfx/bonus_bar.svg",w(0.1), h(0.8) ) );
        		decrease();*/
        		
        		atlases.put( "categories_bar", makeAtlas( h(0.2f), h(1) ) );
        		textures.put( "categories_bar", makeTexture( atlases.get( "categories_bar" ), "gfx/categories_bar.svg", h(0.2f), h(1) ) );
        		decrease();
        		
        		atlases.put( "currency_bar", makeAtlas( h(0.47), h(0.15) ) );
        		textures.put( "currency_bar", makeTexture( atlases.get( "currency_bar" ), "gfx/currency_bar.svg", h(0.47), h(0.15) ) );
        		decrease();
        		
        		/*atlases.put( "store", makeAtlas( w(1), h(1) ) );
        		textures.put( "store", makeTexture( atlases.get( "store" ), "gfx/shop.svg", w(1), h(1) ) );
        		decrease();*/
        		
        		atlases.put( "score", makeAtlas( h(0.2f), h(0.2f) ) );
        		textures.put( "score", makeTexture( atlases.get( "score" ), "gfx/score.svg", h(0.2f), h(0.2f) ) );
        		decrease();
        		
        		atlases.put( "record", makeAtlas( h(0.2f), h(0.2f) ) );
        		textures.put( "record", makeTexture( atlases.get( "record" ), "gfx/record.svg", h(0.2f), h(0.2f) ) );
        		decrease();
        		
        		atlases.put( "quest", makeAtlas( w(0.85f), h(0.26f) ) );
        		textures.put( "quest", makeTexture( atlases.get( "quest" ), "gfx/quest.svg", w(0.85f), h(0.26f) ) );
        		decrease();
        		
        		atlases.put( "feat", makeAtlas( w(0.85f), h(0.26f) ) );
        		textures.put( "feat", makeTexture( atlases.get( "feat" ), "gfx/feat.svg", w(0.85f), h(0.26f) ) );
        		decrease();
        		
        		atlases.put( "button", makeAtlas( w(0.33f), h(0.167f) ) );
        		textures.put( "button", makeTexture( atlases.get( "button" ), "gfx/button.svg", w(0.33f),  h(0.167f) ) );
        		decrease();
        		
        		atlases.put( "back_button", makeAtlas( h(0.167f), h(0.167f) ) );
        		textures.put( "back_button", makeTexture( atlases.get( "back_button" ), "gfx/backbttn.svg", h(0.167f),  h(0.167f) ) );
        		decrease();
        		
        		atlases.put( "cat0", makeAtlas( h(0.167f), h(0.167f) ) );
        		textures.put( "cat0", makeTexture( atlases.get( "cat0" ), "gfx/cat_star.svg", h(0.167f),  h(0.167f) ) );
        		decrease();
        		
        		atlases.put( "cat1", makeAtlas( h(0.167f), h(0.167f) ) );
        		textures.put( "cat1", makeTexture( atlases.get( "cat1" ), "gfx/cat_glasses.svg", h(0.167f),  h(0.167f) ) );
        		decrease();
        		
        		atlases.put( "cat2", makeAtlas( h(0.167f), h(0.167f) ) );
        		textures.put( "cat2", makeTexture( atlases.get( "cat2" ), "gfx/cat_horse.svg", h(0.167f),  h(0.167f) ) );
        		decrease();
        		
        		atlases.put( "cat3", makeAtlas( h(0.167f), h(0.167f) ) );
        		textures.put( "cat3", makeTexture( atlases.get( "cat3" ), "gfx/cat_mountains.svg", h(0.167f),  h(0.167f) ) );
        		decrease();
        		
        		atlases.put( "cat4", makeAtlas( h(0.167f), h(0.167f) ) );
        		textures.put( "cat4", makeTexture( atlases.get( "cat4" ), "gfx/cat_usd.svg", h(0.167f),  h(0.167f) ) );
        		decrease();
        		
        		atlases.put( "cat5", makeAtlas( h(0.167f), h(0.167f) ) );
        		textures.put( "cat5", makeTexture( atlases.get( "cat5" ), "gfx/cat_cent.svg", h(0.167f),  h(0.167f) ) );
        		decrease();
        		
        		atlases.put( "music", makeAtlas( h(0.13f), h(0.13f) ) );
        		textures.put( "music", makeTexture( atlases.get( "music" ), "gfx/music.svg", h(0.13f),  h(0.13f) ) );
        		decrease();
        		
        		atlases.put( "fx", makeAtlas( h(0.13f), h(0.13f) ) );
        		textures.put( "fx", makeTexture( atlases.get( "fx" ), "gfx/fx.svg", h(0.13f),  h(0.13f) ) );
        		decrease();
        		
        		atlases.put( "cross", makeAtlas( h(0.19f), h(0.19f) ) );
        		textures.put( "cross", makeTexture( atlases.get( "cross" ), "gfx/cross.svg", h(0.19f),  h(0.19f) ) );
        		decrease();
        		
        		atlases.put( "sound", makeAtlas( h(0.13f), h(0.13f) ) );
        		textures.put( "sound", makeTexture( atlases.get( "sound" ), "gfx/sound.svg", h(0.13f),  h(0.13f) ) );
        		decrease();
        		
        		atlases.put( "headstart", makeAtlas( h(0.2f), h(0.2f) ) );
        		textures.put( "headstart", makeTexture( atlases.get( "headstart" ), "gfx/headstart.svg", h(0.2f),  h(0.2f) ) );
        		decrease();
        		
        		atlases.put( "button3", makeAtlas( w(0.7f), h(0.2f) ) );
        		textures.put( "button3", makeTexture( atlases.get( "button3" ), "gfx/button2.svg", w(0.7f),  h(0.2f) ) );
        		decrease();
        		
        		atlases.put( "button2", makeAtlas( w(0.33f), h(0.167f) ) );
        		textures.put( "button2", makeTexture( atlases.get( "button2" ), "gfx/button2.svg", w(0.33f),  h(0.167f) ) );
        		decrease();
        		
        		atlases.put( "combo", makeAtlas( w(0.1), h(0.8) ) );
        		textures.put( "combo", makeTexture( atlases.get( "combo" ), "gfx/combobar.svg", w(0.1), h(0.8) ) );
        		decrease();
        		
        		atlases.put( "pause_button", makeAtlas( w(0.1), w(0.1) ) );
        		textures.put( "pause_button", makeTexture( atlases.get( "pause_button" ), "gfx/pause_button.svg", w(0.1), w(0.1) ) );
        		decrease();
        		
        		atlases.put( "comboBar", makeAtlas( w(0.1), h(0.8) ) );
        		textures.put( "comboBar", makeTexture( atlases.get( "comboBar" ), "gfx/bar.svg", w(0.1), h(0.8) ) );
        		decrease();
        		
        		atlases.put( "harder", makeAtlas( w(0.7), h(0.2) ) );
        		textures.put( "harder", makeTexture( atlases.get( "harder" ), "gfx/harder.svg", w(0.7), h(0.2) ) );
        		decrease();
        		
        		atlases.put( "goods", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "goods", makeTexture( atlases.get( "goods" ), "gfx/goods_card.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "first_treasure", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "first_treasure", makeTexture( atlases.get( "first_treasure" ), "gfx/thumbnails/first_treasure.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "second_treasure", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "second_treasure", makeTexture( atlases.get( "second_treasure" ), "gfx/thumbnails/second_treasure.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "third_treasure", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "third_treasure", makeTexture( atlases.get( "third_treasure" ), "gfx/thumbnails/third_treasure.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "fourth_treasure", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "fourth_treasure", makeTexture( atlases.get( "fourth_treasure" ), "gfx/thumbnails/fourth_treasure.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "fifth_treasure", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "fifth_treasure", makeTexture( atlases.get( "fifth_treasure" ), "gfx/thumbnails/fifth_treasure.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "freeze_freq", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "freeze_freq", makeTexture( atlases.get( "freeze_freq" ), "gfx/thumbnails/freeze_freq.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "life_freq", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "life_freq", makeTexture( atlases.get( "life_freq" ), "gfx/thumbnails/life_freq.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "bomb_freq", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "bomb_freq", makeTexture( atlases.get( "bomb_freq" ), "gfx/thumbnails/bomb_freq.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "freeze_duration", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "freeze_duration", makeTexture( atlases.get( "freeze_duration" ), "gfx/thumbnails/freeze_duration.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "bomb_duration", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "bomb_duration", makeTexture( atlases.get( "bomb_duration" ), "gfx/thumbnails/bomb_duration.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "tiger_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "tiger_thumb", makeTexture( atlases.get( "tiger_thumb" ), "gfx/thumbnails/tiger.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "bull_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "bull_thumb", makeTexture( atlases.get( "bull_thumb" ), "gfx/thumbnails/bull.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "chicken_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "chicken_thumb", makeTexture( atlases.get( "chicken_thumb" ), "gfx/thumbnails/chicken.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "dizzy_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "dizzy_thumb", makeTexture( atlases.get( "dizzy_thumb" ), "gfx/thumbnails/dizzy.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "elegant_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "elegant_thumb", makeTexture( atlases.get( "elegant_thumb" ), "gfx/thumbnails/elegant.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "pirate_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "pirate_thumb", makeTexture( atlases.get( "pirate_thumb" ), "gfx/thumbnails/pirate.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "hipster_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "hipster_thumb", makeTexture( atlases.get( "hipster_thumb" ), "gfx/thumbnails/hipster.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "koala_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "koala_thumb", makeTexture( atlases.get( "koala_thumb" ), "gfx/thumbnails/koala.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "whistler_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "whistler_thumb", makeTexture( atlases.get( "whistler_thumb" ), "gfx/thumbnails/whistler.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "carnival_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "carnival_thumb", makeTexture( atlases.get( "carnival_thumb" ), "gfx/thumbnails/carnival.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "forest_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "forest_thumb", makeTexture( atlases.get( "forest_thumb" ), "gfx/thumbnails/forest.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "adcolony_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "adcolony_thumb", makeTexture( atlases.get( "adcolony_thumb" ), "gfx/thumbnails/adcolony.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "tapjoy_thumb", makeAtlas( h(0.625), h(1) ) );
        		textures.put( "tapjoy_thumb", makeTexture( atlases.get( "tapjoy_thumb" ), "gfx/thumbnails/tapjoy.svg", h(0.625), h(1) ) );
        		decrease();
        		
        		atlases.put( "super", makeAtlas( w(1.0), h(0.2) ) );
        		textures.put( "super", makeTexture( atlases.get( "super" ), "gfx/super.svg", w(1.0), h(0.2) ) );
        		decrease();
        		
        		atlases.put( "great", makeAtlas( w(1.0), h(0.2) ) );
        		textures.put( "great", makeTexture( atlases.get( "great" ), "gfx/great.svg", w(1.0), h(0.2) ) );
        		decrease();
        		
        		atlases.put( "awesome", makeAtlas( w(1.0), h(0.2) ) );
        		textures.put( "awesome", makeTexture( atlases.get( "awesome" ), "gfx/awesome.svg", w(1.0), h(0.2) ) );
        		decrease();
        		
        		atlases.put( "fabulous", makeAtlas( w(1.0), h(0.2) ) );
        		textures.put( "fabulous", makeTexture( atlases.get( "fabulous" ), "gfx/fabulous.svg", w(1.0), h(0.2) ) );
        		decrease();
        		
        		atlases.put( "unbelievable", makeAtlas( w(1.0), h(0.2) ) );
        		textures.put( "unbelievable", makeTexture( atlases.get( "unbelievable" ), "gfx/unbelievable.svg", w(1.0), h(0.2) ) );
        		decrease();
        		
        		atlases.put( "end", makeAtlas( h(1.828), h(1) ) );
        		textures.put( "end", makeTexture( atlases.get( "end" ), "gfx/end.svg", h(1.828), h(1) ) );
        		decrease();
        		
        		whistlerAtlas = new BitmapTextureAtlas( getTextureManager(), size, (int)(size * 1.5), TextureOptions.BILINEAR );
        		IBitmapTextureAtlasSource decoratedTextureAtlasSource = SVGLayeredTextureAtlasSourceFactory.makeAtlasSource( MainActivity.this, "gfx/whistler.svg", new String[]{"gfx/elegant.svg"}, size, (int)(size * 1.5) );
        		whistlerTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource( whistlerAtlas, decoratedTextureAtlasSource, 0, 0, 4, 6 );
        		whistlerAtlas.load();
        		decrease();
        		
        		atlases.put( "freeze", makeAtlas( 1024, 1024 ) );
        		tiled_textures.put( "freeze", (ITiledTextureRegion)makeTexture( atlases.get( "freeze" ), "gfx/freeze.svg", size, size, 4, 4 ) );
        		decrease();
        		
        		atlases.put( "shield", makeAtlas( 1024, 1024 ) );
        		tiled_textures.put( "shield", (ITiledTextureRegion)makeTexture( atlases.get( "shield" ), "gfx/shield.svg", size, size, 4, 4 ) );
        		decrease();
        		
        		atlases.put( "heartbonus", makeAtlas( 1024, 1024 ) );
        		tiled_textures.put( "heartbonus", (ITiledTextureRegion)makeTexture( atlases.get( "heartbonus" ), "gfx/heartbonus.svg", size, size, 4, 4 ) );
        		decrease();
        		
        		atlases.put( "bomb", makeAtlas( 1024, 1024 ) );
        		tiled_textures.put( "bomb", (ITiledTextureRegion)makeTexture( atlases.get( "bomb" ), "gfx/bomb.svg", size, size, 4, 4 ) );
        		decrease();
        		
        		atlases.put( "star", makeAtlas( 1024, 1024 ) );
        		tiled_textures.put( "star", (ITiledTextureRegion)makeTexture( atlases.get( "star" ), "gfx/star.svg", size, size, 4, 4 ) );
        		decrease();
        		
        		atlases.put( "background", makeAtlas( w(1), h(1) ) );
        		textures.put( "background", makeTexture( atlases.get( "background" ), "gfx/carnival.svg", w(1), h(1) ) );
        		decrease();
        		
        		atlases.put( "feat_background", makeAtlas( h(2.2), h(2.2) ) );
        		textures.put( "feat_background", makeTexture( atlases.get( "feat_background" ), "gfx/feat_background.svg", h(2.2), h(2.2) ) );
        		
        		Log.d( "Assert", segments + " = " + assertion );
            }
            
            private void decrease()
            {
            	assertion++;
            	scale = scale - 1f / segments;
            	progress_bar.setScaleX( scale );
        		progress_bar.setX( progress_bar.getX() + (1f / segments * progress_bar.getWidth() / 2) );
            }

            public void onComplete() 
            {
                onGameStart();
            }
        };
        
        runOnUiThread( new Runnable()
        {
            public void run() 
            {
                new AsyncTaskLoader().execute( callback );
            }
        });
	}
	
	private void attachPauseScene()
	{
		float x = -( h(1.828) - w(1) ) / 2;
		
		pause = new Scene();
		Rectangle rect2 = new Rectangle( 0, 0, w(1), h(1), getVertexBufferObjectManager() );
		rect2.setColor(0, 0, 0);
		rect2.setAlpha( 0.5f );
		pause.attachChild( rect2 );
    	Sprite pause_sprite = new Sprite( x, 0, textures.get( "pause" ), getVertexBufferObjectManager() );
    	pause.setBackgroundEnabled( false );
    	pause.attachChild( pause_sprite );
    	Sprite restart = new Sprite( h(0.5)+x, h(0.6), textures.get( "restart_button" ), getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			if ( pSceneTouchEvent.isActionUp() )
    			{
    				mTap.play();
    				paused = true;
        			togglePause();
        			try 
        			{
						changeScreen( screen.getClass().newInstance() );
					} 
        			catch ( Exception e )
					{
						e.printStackTrace();
					}
    			}
    			
    			return true;
    		}
    	};
    	pause.attachChild( restart );
    	pause.registerTouchArea( restart );
    	Sprite menu = new Sprite( h(0.8)+x, h(0.6), textures.get( "menu_button" ), getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			if ( pSceneTouchEvent.isActionUp() )
    			{
    				mTap.play();
    				paused = true;
        			togglePause();
        			changeScreen( new MenuScreen() );
    			}
    			
    			return true;
    		}
    	};
    	pause.attachChild( menu );
    	pause.registerTouchArea( menu );
    	Sprite exit = new Sprite( h(1.1)+x, h(0.6), textures.get( "exit_button" ), getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			if ( pSceneTouchEvent.isActionUp() )
    			{
    				mTap.play();
    				paused = true;
        			togglePause();
        			scene.clearChildScene();
    			}
    			
    			return true;
    		}
    	};
    	pause.attachChild( exit );
    	pause.registerTouchArea( exit );
	}
	
	private void attachExitScene()
	{
		float x = -( h(1.828) - w(1) ) / 2;
		
		exit_scene = new Scene();
		Rectangle rect2 = new Rectangle( 0, 0, w(1), h(1), getVertexBufferObjectManager() );
		rect2.setColor(0, 0, 0);
		rect2.setAlpha( 0.8f );
		exit_scene.attachChild( rect2 );
    	exit_scene.setBackgroundEnabled( false );
    	Sprite restart = new Sprite( h(0.5)+x, h(0.6), textures.get( "tick_button" ), getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			if ( pSceneTouchEvent.isActionUp() )
    			{
    				mTap.play();
        			toggleExitScene();
        			if ( screen instanceof MenuScreen )
        			{
        				exiting.send();
        				finish();
        			}
        			else
        			{
        				Random rand = new Random();
        				if ( rand.nextBoolean() )
        					cb.showInterstitial( "menu_return" );
        				changeScreen( new MenuScreen() );
        			}
    			}
    			
    			return true;
    		}
    	};
    	exit_scene.attachChild( restart );
    	exit_scene.registerTouchArea( restart );
    	Sprite exit = new Sprite( h(1.1)+x, h(0.6), textures.get( "exit_button" ), getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			if ( pSceneTouchEvent.isActionUp() )
    			{
    				mTap.play();
        			toggleExitScene();
        			scene.clearChildScene();
    			}
    			
    			return true;
    		}
    	};
    	exit_scene.attachChild( exit );
    	exit_scene.registerTouchArea( exit );
	}
	
	protected void onGameStart()
	{	
		game_loaded = true;
		
		TapjoyConnect.getTapjoyConnectInstance().getTapPoints( this );
		TapjoyConnect.getTapjoyConnectInstance().setEarnedPointsNotifier( this );
		
		StoreGoodsProxy.get();
		
		Database.setInt( "reminder", (int)(System.currentTimeMillis() / 1000) );
		Database.raiseInt( "run" );
		
		pushNotification();
		
		opening.send();
		
		Item.setVbo( getVertexBufferObjectManager() );
    	Whistler.setTexture( whistlerTexture );
    	Bomb.setTexture( tiled_textures.get( "bomb" ) );
    	Star.setTexture( tiled_textures.get( "star" ) );
    	FreezeBonus.setTexture( tiled_textures.get( "freeze" ) );
    	LifeBonus.setTexture( tiled_textures.get( "heartbonus" ) );
    	BombBonus.setTexture( tiled_textures.get( "shield" ) );
    	
    	StoreGoodsProxy.get().loadGoodsList( this );
    	DailyRewardGoodsProxy.get().loadGoodsList( this );
    	
    	if ( Database.getInt( "run" ) == 1 )
    		StoreGoodsProxy.get().resetGroupsToDefault();
    	
    	ArrayList<Goods> characters = StoreGoodsProxy.get().getGoodsNeedingLoad();
    	for ( Goods character : characters )
    	{
    		character.onLoad();
    	}
    	
		changeScreen( new MenuScreen() );
		
		boolean granted = grantReminderStarsIfNeeded();
		if ( ! granted )
		{
			requestRating();
		}
	}

	private void pushNotification()
	{
		final int waiting_time = 7 * 24 * 3600 * 1000;
		Runnable reminder = new Runnable()
		{
		    public void run()
		    {
		    	int diff = (int)( System.currentTimeMillis() / 1000 ) - Database.getInt("reminder");
		    	if ( waiting_time / 1000 <= diff )
		    	{
		    		int NOTIFICATION_ID = 1324;
			        NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

			        Notification note = new Notification(R.drawable.ic_launcher, "Whistler Attack", System.currentTimeMillis());

			        note.flags |= Notification.FLAG_AUTO_CANCEL;    
			        
			        Intent intent = new Intent(MainActivity.this, MainActivity.class);
			        intent.putExtra( "reward", true );
			        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        PendingIntent i = PendingIntent.getActivity(MainActivity.this, NOTIFICATION_ID, intent, 0);
			        
			        note.setLatestEventInfo(MainActivity.this, "Whistler Attack", "Receive special reward! Free 1,000 stars!", i);
			        notifManager.notify(NOTIFICATION_ID, note);
		    	}
		    }
		};

		Handler handler = new Handler();
		handler.postDelayed(reminder, waiting_time);
	} 
	
	private boolean grantReminderStarsIfNeeded()
	{
		if ( getIntent().getExtras() != null && getIntent().getExtras().get( "reward" ) != null )
		{
			Scene child = new Scene();
			
			int reward = 1000;
			(new StarCurrencyUnit( reward )).award();
			
			final Rectangle rect2 = new Rectangle( 0, 0, w(1), h(1), getVertexBufferObjectManager() );
			rect2.setColor(0, 0, 0);
			rect2.setAlpha( 0.85f );
			child.attachChild( rect2 );
			
			String text =   "Congratulations!\n" +
							"We grant you " +reward+ " stars";
			final Text textComponent = new Text( w(0), h(0.2f), mFont, text, text.length(), getVertexBufferObjectManager() );
			textComponent.setHorizontalAlign( HorizontalAlign.CENTER );
			textComponent.setX( ( w(1) - textComponent.getWidth() ) / 2 );
			textComponent.setY( ( h(1) - textComponent.getHeight() ) / 2 );
	    	child.attachChild( textComponent );
			
			child.registerUpdateHandler( new TimerHandler( 3, false, new ITimerCallback()
			{
				public void onTimePassed(TimerHandler pTimerHandler) 
				{
					rect2.detachSelf();
					textComponent.detachSelf();
				}
			}));
			
			child.setBackgroundEnabled( false );
			scene.clearChildScene();
			scene.setChildScene( child );
			
			return true;
		}
		
		return false;
	}
	
	private void requestRating()
	{
		if ( Database.getInt( "run" ) > 1 && Database.getBoolean( "rating" ) == false )
		{
			float x = -( h(1.828) - w(1) ) / 2;
			
			Scene rating_scene = new Scene();
			Rectangle rect2 = new Rectangle( 0, 0, w(1), h(1), getVertexBufferObjectManager() );
			rect2.setColor(0, 0, 0);
			rect2.setAlpha( 0.8f );
			rating_scene.attachChild( rect2 );
	    	rating_scene.setBackgroundEnabled( false );
	    	Sprite restart = new Sprite( h(0.5)+x, h(0.6), textures.get( "tick_button" ), getVertexBufferObjectManager() )
	    	{
	    		@Override
	    	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY )
	    		{
	    			if ( pSceneTouchEvent.isActionUp() )
	    			{
	    				mTap.play();
	    				scene.clearChildScene();
	        			Database.setBoolean( "rating", true );
	        			
						String appPackageName = getPackageName();
						Intent marketIntent =
								new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=" + appPackageName ) );
						marketIntent.addFlags( Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET );
						startActivity( marketIntent );
	    			}
	    			
	    			return true;
	    		}
	    	};
	    	rating_scene.attachChild( restart );
	    	rating_scene.registerTouchArea( restart );
	    	Sprite exit = new Sprite( h(1.1)+x, h(0.6), textures.get( "exit_button" ), getVertexBufferObjectManager() )
	    	{
	    		@Override
	    	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY )
	    		{
	    			if ( pSceneTouchEvent.isActionUp() )
	    			{
	    				mTap.play();
	        			scene.clearChildScene();
	        			Database.setBoolean( "rating", true );
	    			}
	    			
	    			return true;
	    		}
	    	};
	    	rating_scene.attachChild( exit );
	    	rating_scene.registerTouchArea( exit );
	    	
	    	String text = "Would you like to rate this game?\n" +
	    			"We'd be very happy if you rated\n" +
	    			"it 5 stars. Thanks! :)";
			Text textComponent = new Text( w(0), h(0.2f), mFont, text, text.length(), getVertexBufferObjectManager() );
			textComponent.setHorizontalAlign( HorizontalAlign.CENTER );
			textComponent.setX( ( w(1) - textComponent.getWidth() ) / 2 );
			textComponent.setY( ( h(1) - textComponent.getHeight() ) / 4 );
	    	rating_scene.attachChild( textComponent );
	    	
	    	scene.clearChildScene();
	    	scene.setChildScene( rating_scene );
		}
	}
	
	public void changeScreen( Screen s )
	{
		changeScreen( s, new String[]{} );
	}
	
	public void changeScreen( Screen s, String[] args )
	{
		screen = s;
		
		screen.setAtlases(atlases);
		screen.setTextures(textures);
		screen.setTiledTextures(tiled_textures);
		screen.setActivity( this );
		
		scene = screen.onCreateScreen();
		mEngine.setScene(scene);
		
		screen.onStartScreen( args );
	}
	
	public BitmapTextureAtlas makeAtlas( int atlas_x, int atlas_y )
	{
		return new BitmapTextureAtlas( this.getTextureManager(), atlas_x, atlas_y, TextureOptions.BILINEAR_PREMULTIPLYALPHA );
	}
	
	public ITextureRegion makeTexture( BitmapTextureAtlas atlas, String path, int size_x, int size_y )
	{
		return makeTexture( atlas, path, size_x, size_y, 0, 0 );
	}
	
	private ITextureRegion makeTexture( BitmapTextureAtlas atlas, String path, int size_x, int size_y, int tile_x, int tile_y )
	{
		ITextureRegion texture;

		if ( tile_x == 0 && tile_y == 0 )
		{
			texture = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset( atlas, this, path, size_x, size_y, 0, 0 );
		}
		else
		{
			texture = SVGBitmapTextureAtlasTextureRegionFactory.createTiledFromAsset( atlas, this, path, size_x, size_y, 0, 0, tile_x, tile_y );
		}
		
		TextureManager tm = mEngine.getTextureManager();
		tm.loadTexture( atlas );
		
		return texture;
	}
	
	public int w( double input )
	{
		double output = input * CAMERA_WIDTH;
		return (int)output;
	}
	
	public int h( double input )
	{
		double output = input * CAMERA_HEIGHT;
		return (int)output;
	}
	
	@Override
	public Engine getEngine()
	{
		return mEngine;
	}
	
	public class AsyncTaskLoader extends AsyncTask<IAsyncCallback, Integer, Boolean>
	{
        IAsyncCallback[] _params;
 
        @Override
        protected Boolean doInBackground(IAsyncCallback... params) {
                this._params = params;
                int count = params.length;
                for(int i = 0; i < count; i++){
                        params[i].workToDo();
                }
                return true;
        }
       
        @Override
        protected void onPostExecute(Boolean result) 
        {
                int count = this._params.length;
                for(int i = 0; i < count; i++)
                {
                        this._params[i].onComplete();
                }
        }
	}
	
    public interface IAsyncCallback
    {
        public abstract void workToDo();
        public abstract void onComplete();   
    }

	public void onV4VCResult( boolean success, String name, int value ) 
	{
		if ( success )
		{
			(new StarCurrencyUnit( value )).award();
			
			if ( screen instanceof ShowingCurrency )
			{
				ShowingCurrency sc = (ShowingCurrency)screen;
				sc.refreshCurrency();
			}
		}
	}
	
	public void earnedTapPoints( int value ) 
	{
		Log.d( "Tapjoy", "Tapjoy earned" );
		TapjoyConnect.getTapjoyConnectInstance().getTapPoints( this );
	}
	
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		if ( requestCode == 1001 )
		{
			int responseCode = data.getIntExtra( "RESPONSE_CODE", 0 );
			String purchaseData = data.getStringExtra( "INAPP_PURCHASE_DATA" );
			String dataSignature = data.getStringExtra( "INAPP_DATA_SIGNATURE" );

			if ( resultCode == RESULT_OK )
			{
				try
				{
					final JSONObject jo = new JSONObject( purchaseData );
					String sku = jo.getString( "productId" );
					
					PackGoods proper_goods = null;
					LinkedHashMap<String, Goods> goods = StoreGoodsProxy.get().getGoodsList( StoreGoodsProxy.STAR_PACKS );
					for ( Map.Entry<String, Goods> entry : goods.entrySet() )
					{
						if ( ! ( entry.getValue() instanceof PackGoods ) )
							continue;
						PackGoods pg = (PackGoods)entry.getValue();
						
						if ( pg.getBillingId().equals( sku ) )
						{
							if ( ! pg.getHashKey().equals( jo.getString(  "developerPayload" ) ) )
							{
								throw new Exception( "Keys don't match" );
							}
							
							proper_goods = pg;
						}
					}
					
					if ( proper_goods == null )
						throw new Exception( "No such goods in local store" );
					
					try
					{
						int response = mService.consumePurchase(3, getPackageName(), jo.getString( "purchaseToken" ));
						
						if ( response == 0 )
						{
							proper_goods.getPriceInCurrencyUnit().award();
						}	
						
						if ( screen instanceof ShowingCurrency )
							((ShowingCurrency)screen).refreshCurrency();
					} 
					catch ( Exception e )
					{
						e.printStackTrace();
					}
				} 
				catch ( Exception e )
				{
					e.printStackTrace();
				}	
			}
		}
	}

	@Override
	public void getUpdatePoints( String currency_name, int value )
	{
		Log.d( "Tapjoy", "refreshing" );
		int granted_before = Database.getInt( "tapjoy_already_granted" );
		
		if ( value > granted_before )
		{
			new StarCurrencyUnit( value - granted_before ).award();
			Database.setInt( "tapjoy_already_granted", value );
			
			runOnUiThread( new Runnable()
			{
				@Override
				public void run()
				{
					if ( screen instanceof ShowingCurrency )
					{
						ShowingCurrency sc = (ShowingCurrency)screen;
						sc.refreshCurrency();
					}
				}
			});
		}
		
		Log.d( "Tapjoy", "Tapjoy FROM " +granted_before+ " TO " +Database.getInt( "tapjoy_already_granted" ) );
	}

	@Override
	public void getUpdatePointsFailed( String arg0 )
	{
		Log.d( "Tapjoy", "Error retreiving points from server." );
	}

	@Override
	public void unlockedReward( PHPublisherContentRequest request, PHReward reward )
	{
		Log.d( "Reward", reward.name+ " " +reward.quantity );
		if ( reward.name.equals( "stars" ) )
		{
			new StarCurrencyUnit( reward.quantity ).award();
		}
	}
}

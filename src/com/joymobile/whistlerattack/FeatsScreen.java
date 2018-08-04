package com.joymobile.whistlerattack;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;

import android.content.Intent;
import android.util.Log;

import com.scoreloop.client.android.core.controller.AchievementsController;
import com.scoreloop.client.android.core.controller.RequestController;
import com.scoreloop.client.android.core.controller.RequestControllerObserver;
import com.scoreloop.client.android.core.model.Achievement;
import com.scoreloop.client.android.core.model.Continuation;
import com.scoreloop.client.android.ui.AchievementsScreenActivity;
import com.scoreloop.client.android.ui.OnScoreSubmitObserver;
import com.scoreloop.client.android.ui.ScoreloopManagerSingleton;


class FeatsScreen extends Screen implements IOnSceneTouchListener, OnScoreSubmitObserver, RequestControllerObserver
{
	Text bttnText5;
	float x;
	private float mTouchY = 0, mTouchOffsetY = 0;
	ArrayList<FeatEntity> shownFeats;
	AchievementsController myAchievementsController;
	
	@Override
	public void onStartScreen( String[] args )
	{
		scene.setOnSceneTouchListener(this);
		
		x = -( main.h(1.828) - main.w(1) ) / 2;
		
		showBackground();
		
		FeatProxy.get().loadAndResetTasks();
		ArrayList<Feat> tasks = (ArrayList<Feat>)FeatProxy.get().getTasks();
		
		int i = 0;
        shownFeats = new ArrayList<FeatEntity>();
        for ( Feat task : tasks )
        {
        	FeatEntity entity = new FeatEntity( task, main.w(0.07f), main.h(0.2f + 0.25f * i), main.mFont4, main.mFont, main );
            entity.attachAsChild( scene );
            shownFeats.add( entity );

            i++;
        }
        
        showBackButton();
        showScoreloopButton();
	}
	
    public boolean onSceneTouchEvent( Scene pScene, TouchEvent pTouchEvent )
    {
        if( pTouchEvent.isActionDown() ) 
        {
            mTouchY = pTouchEvent.getMotionEvent().getY();
        }
        else if (pTouchEvent.isActionMove() ) 
        {
            float newY = pTouchEvent.getMotionEvent().getY();
           
            mTouchOffsetY = (newY - mTouchY);
            
            if ( mTouchOffsetY > 0 )
            	Log.d( "dir", "DOWN");
            if ( mTouchOffsetY < 0 )
            	Log.d( "dir", "UP");
            
            if ( mTouchOffsetY > 0 && shownFeats.get( 0 ).getY() >= main.h(0.2f) )
            	return false;
            if ( mTouchOffsetY < 0 && shownFeats.get( shownFeats.size() - 1 ).getY() <= main.h(0.69f) )
            	return false;
        	
            for ( FeatEntity feat_entity : shownFeats )
            {
            	float newScrollY = feat_entity.getY() + mTouchOffsetY;
                feat_entity.setY( newScrollY );
            }
           
            mTouchY = newY;
        }
        
        return true;
    }

	
	private void showBackground()
	{
    	Sprite bkg = new Sprite( x, 0, textures.get( "menu_background" ), main.getVertexBufferObjectManager() );
    	scene.attachChild( bkg );
    	
    	Sprite diablo = new Sprite( main.h(1.828 * 0.63) + x, -main.h(0.1f), textures.get( "diablo" ), main.getVertexBufferObjectManager() );
    	scene.attachChild( diablo );
    	
    	diablo.registerEntityModifier( 
    			new LoopEntityModifier(
    				new RotationModifier( 4, 0, 360 )
    			)
    	);
	}
	
	private void showBackButton()
	{
		final Sprite bttn5 = new Sprite( main.w(0.05f), main.h(0.05), textures.get( "back_button" ), main.getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			if ( pSceneTouchEvent.isActionUp() )
    			{
    				main.mTap.play();
    				
    				if ( main.cb.hasCachedInterstitial() )
    				{
    					main.cb.showInterstitial( "feats" );
    				}
    				
        			main.changeScreen( new MenuScreen() );
    			}
    			
    			return true;
    		}
    	};
    	scene.attachChild( bttn5 );
    	scene.registerTouchArea( bttn5 );
	}
	
	private void showScoreloopButton()
	{
		final Sprite bttn5 = new Sprite( main.w(0.65f), main.h(0.05), textures.get( "button" ), main.getVertexBufferObjectManager() )
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
	    					        			ScoreloopManagerSingleton.get().setOnScoreSubmitObserver( FeatsScreen.this );
	    										ScoreloopManagerSingleton.get().onGamePlayEnded( (double)( Database.getInt( "record" ) ), null );
	    					        		}
	    					        		
	    					        		for ( Feat feat : (ArrayList<Feat>)FeatProxy.get().getTasks() )
	    					        		{
	    					        			if ( feat.isUnlocked() )
	    					        			{
	    					        				ScoreLoopFeats.get().requestAccomplish( feat );
	    					        			}
	    					        		}
	    					        		
	    					        		myAchievementsController = new AchievementsController( FeatsScreen.this );
	    					        		myAchievementsController.loadAchievements();
	    					        	}
	    					        	
	    					        	Database.setBoolean( "scoreloop", true );
	    					        	scene.setChildScene( new Scene(), false, true, true );
	            			    		main.startActivity( new Intent( main, AchievementsScreenActivity.class ) );
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
    	scene.attachChild( bttn5 );
    	scene.registerTouchArea( bttn5 );
    	
    	String text = "View online";
    	Text bttnText6 = new Text( bttn5.getX(), bttn5.getY(), main.mFont, text, text.length(), main.getVertexBufferObjectManager() );
    	float add6 = (bttn5.getWidth() - bttnText6.getWidth() ) / 2;
    	bttnText6.setX( bttnText6.getX() + add6 );
    	float add6h = (bttn5.getHeight() - bttnText6.getHeight() ) / 2;
    	bttnText6.setY( bttnText6.getY() + add6h );
    	scene.attachChild( bttnText6 );
	}
	
	/*@Override
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
	}*/
	
	@Override
	public void onExit()
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
		main.getMusicManager().setMasterVolume( 0.5f );
	}

	public void onScoreSubmit(int status, Exception error) 
	{
		Log.d( "SYNCHRONIZE", ""+status );
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

	public void requestControllerDidFail(RequestController arg0, Exception arg1) 
	{
		
	}
}
package com.joymobile.whistlerattack;

import java.util.ArrayList;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;

import android.text.TextUtils;

class FeatGameScreen extends Screen
{
	ArrayList<QuestEntity> shownQuests;
	float x;
	boolean skip = true;
	Text textComponent2;
	
	@Override
	public void onStartScreen( String[] args )
	{
		x = -( main.h(1.828) - main.w(1) ) / 2;
		drawBackground();
        
		final ArrayList<String> feats_text = new ArrayList<String>();
		FeatProxy.get().refreshTasksStatus( new SingleTaskCallback()
		{
			public void onIterate( Task task, Task new_task )
			{
				feats_text.add( ((Feat)task).getName() );
				skip = false;
			}
		});
		
		if ( skip )
			main.changeScreen( new GameEndScreen(), args );
		
		String header = "Unlocked Achievements!";
		textComponent2 = new Text( main.w(0), main.h(0.1f), main.mFont2, header, header.length(), main.getVertexBufferObjectManager() );
		textComponent2.setHorizontalAlign( HorizontalAlign.CENTER );
		textComponent2.setX( ( main.w(1) - textComponent2.getWidth() ) / 2 );
    	scene.attachChild( textComponent2 );
		
		String text = TextUtils.join( "\n", feats_text.toArray() );
		textComponent = new Text( main.w(0), main.h(0.2f), main.mFont, text, text.length(), main.getVertexBufferObjectManager() );
		textComponent.setHorizontalAlign( HorizontalAlign.CENTER );
		textComponent.setX( ( main.w(1) - textComponent.getWidth() )  * 4 / 5 );
		textComponent.setY( ( main.h(1) - textComponent.getHeight() ) / 2 );
    	scene.attachChild( textComponent );
        
        attachExitButton(true, args);
        
		/*FeatProxy.get().reloadTasks();
		Score.get().setUnitListener( FeatProxy.get() );
		Score.get().submitScore();
		FeatProxy.get().refreshTasksStatus( null );*/
	}
    
    private void drawBackground()
    {
    	Sprite bkg = new Sprite( -(main.h(2)-main.w(1))/2, -main.h( 0.6 ), textures.get( "feat_background" ), main.getVertexBufferObjectManager() );
    	scene.attachChild( bkg );
    	
    	bkg.registerEntityModifier( new LoopEntityModifier(
			new RotationModifier( 8, 0, 360 )
		));
    	
    	Sprite cup = new Sprite( main.w(0.1), main.h(0.3), textures.get( "cup" ), main.getVertexBufferObjectManager() );
    	scene.attachChild( cup );
    }
    
    private void attachExitButton( final boolean redirect, final String[] args )
    {
    	final Sprite bttn5 = new Sprite( main.w(0.6f), main.h(0.8), textures.get( "button" ), main.getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			if ( pSceneTouchEvent.isActionUp() )
    			{
    				main.mTap.play();
    				if ( redirect )
    					main.changeScreen( new GameEndScreen(), args );
    				else
    					main.changeScreen( new MenuScreen() );
    			}
    			
    			return true;
    		}
    	};
    	scene.attachChild( bttn5 );
    	scene.registerTouchArea( bttn5 );
    	
    	String text = redirect ? "NEXT" : "BACK";
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
}
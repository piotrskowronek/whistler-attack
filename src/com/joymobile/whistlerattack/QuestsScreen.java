package com.joymobile.whistlerattack;

import java.util.ArrayList;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;

class QuestsScreen extends Screen
{
	ArrayList<QuestEntity> shownQuests;
	float x;
	
	@Override
	public void onStartScreen( String[] args )
	{
		x = -( main.h(1.828) - main.w(1) ) / 2;
		drawBackground();
        
        if ( args.length > 0 && args[0].equals( "simple" ) )
        	QuestProxy.get().loadAndResetTasks();
        
        ArrayList<Quest> tasks = (ArrayList<Quest>)QuestProxy.get().getTasks();
        
        int i = 0;
        shownQuests = new ArrayList<QuestEntity>();
        for ( Quest task : tasks )
        {
        	QuestEntity entity = new QuestEntity( task, main.w(0.07f), main.h(0.05f + 0.25f * i), main.mFont4, main.mFont, main );
            entity.attachAsChild( scene );
            shownQuests.add( entity );

            i++;
        }
		
        if ( (args.length > 0 && ! args[0].equals( "simple" )) || args.length == 0 )
        {
        	Score.get().addUnitListener( QuestProxy.get() );
        	Score.get().addUnitListener( FeatProxy.get() );
            Score.get().submitScore();
             
            QuestProxy.get().refreshTasksStatus( new SingleTaskCallback()
			{
				public void onIterate( Task task, Task new_task )
				{
					QuestEntity entity = getQuestEntityByQuest( (Quest)task );
					if ( entity != null )
					{
						( new StarCurrencyUnit( entity.getQuest().getReward() ) ).award();
						entity.slideOut();
					}
					
					if ( new_task != null )
						slideIn( entity.getY(), (Quest)new_task );
				}
			});
        }
        
        if ( args.length > 0 && args[0].equals( "simple" ) )
        {
        	attachExitButton(false, args);
        }
        else
        {
        	if ( tasks.size() == 0 )
        		main.changeScreen( new FeatGameScreen(), args );
        	attachExitButton(true, args);
        }
        
		/*FeatProxy.get().reloadTasks();
		Score.get().setUnitListener( FeatProxy.get() );
		Score.get().submitScore();
		FeatProxy.get().refreshTasksStatus( null );*/
	}
	
	private QuestEntity getQuestEntityByQuest( Quest quest )
	{
		for ( QuestEntity to_compare : shownQuests )
		{
			if ( to_compare.getQuest().id.equals( quest.id ) ) //TODO: Demeter principle
			{
				return to_compare;
			}
		}
		
		return null;
	}
	
	private void slideIn( float pY, Quest quest )
	{
		QuestEntity entity = new QuestEntity( quest, main.w(-1), pY, main.mFont4, main.mFont, main );
        entity.attachAsChild( scene );
        
        entity.slideIn();
	}
    
    private void drawBackground()
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
    
    private void attachExitButton( final boolean redirect, final String[] args )
    {
    	final Sprite bttn5 = new Sprite( main.w(0.33f), main.h(0.8), textures.get( "button" ), main.getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			if ( pSceneTouchEvent.isActionUp() )
    			{
    				main.mTap.play();
    				if ( redirect )
    					main.changeScreen( new FeatGameScreen(), args );
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
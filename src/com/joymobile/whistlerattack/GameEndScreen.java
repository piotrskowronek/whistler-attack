package com.joymobile.whistlerattack;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;

import com.jirbo.adcolony.AdColonyVideoAd;

class GameEndScreen extends Screen
{
	ArrayList<QuestEntity> shownQuests;
	float x;
	
	@Override
	public void onStartScreen( String[] args )
	{
		x = -( main.h(1.7777) - main.w(1) ) / 2;
		drawBackground();
		if ( args.length > 0 )
		{
			int score = Integer.parseInt( args[0] );
			int currency = Integer.parseInt( args[1] );
			int record = Database.getInt( "record" );
			if ( score > record )
				Database.setInt( "record", score );
			
			drawScores( score, record );
			drawScoreText( score );
			drawCurrencyText( currency );
		}
        attachExitButton();
	}
	
    private void drawCurrencyText( int currency ) 
    {
    	Text bttnText = new Text( main.h(1.38) + x, main.h(0.565f), main.mFont3, ""+currency, (""+currency).length(), main.getVertexBufferObjectManager() );
    	scene.attachChild( bttnText );
	}

	private void drawScoreText( int score ) 
	{
		Text bttnText = new Text( main.h(0.97) + x, main.h(0.56f), main.mFont3, ""+score, (""+score).length(), main.getVertexBufferObjectManager() );
    	scene.attachChild( bttnText );
	}

	private void drawBackground()
    {
        final Sprite rect = new Sprite( x, 0, main.textures.get( "end" ), main.getVertexBufferObjectManager() );
		scene.attachChild( rect );
		
		String[] text = new String[]{
			"If you have no stars, you can buy\n" +
			"them in store!",
			
			"Express yourself by personalizing\n" +
			"the game in store!",
			
			"Very nice! You're a good player!",
			
			"Quests might intersperse your\n" +
			"play!",
			
			"Share your score with friends\n" +
			"by Scoreloop!",
			
			"Invite friends and compete with\n" +
			"them!",
			
			"Need more stars? Get them for\n" +
			"free! Have a look at store!",
			
			"Play every day to get daily\n" +
			"reward!",
			
			"Wow! Good score!"
		};
		Random rand = new Random();
		int num = rand.nextInt(text.length - 1);
		Text bttnText = new Text( main.w( 0.43 ), main.h(0.75f), main.mFont3, text[num], text[num].length(), main.getVertexBufferObjectManager() );
    	scene.attachChild( bttnText );
    }
    
    private void drawScores( int score, int record )
    {
    	//0.69 - 1.385
    	float score_pos;
    	float record_pos;
    	if ( score == 0 && record == 0 )
    	{
    		record_pos = main.h(0.69) + x;
    		score_pos = main.h(0.69) + x;
    	}
    	else if ( score >= record )
    	{
    		if ( score == 0 )
    			record_pos = main.h(0.69) + x;
    		else
    			record_pos = main.h(0.69) + (  main.h(0.695) * record / ( score * 4/3 ) ) + x;
    		
    		
    		score_pos = main.h(1.211f) + x;
    	}
    	else
    	{
    		record_pos = main.h(1.211f) + x;
    		if ( record == 0 )
    			score_pos = main.h(0.69) + x;
    		else
    			score_pos = main.h(0.69) + (  main.h(0.695) * score / ( record * 4/3 ) ) + x;
    	}
    	
    	final Sprite rect2 = new Sprite( record_pos, main.h(0.293f), main.textures.get( "record" ), main.getVertexBufferObjectManager() );
		scene.attachChild( rect2 );
		
		final Sprite rect3 = new Sprite( score_pos, main.h(0.293f), main.textures.get( "score" ), main.getVertexBufferObjectManager() );
		scene.attachChild( rect3 );
    }
    
    private void attachExitButton()
    {
    	final Sprite bttn5 = new Sprite( main.w(0.1f), main.h(0.07), textures.get( "button" ), main.getVertexBufferObjectManager() )
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
    						AdColonyVideoAd ad = new AdColonyVideoAd( "" );
    						ad.offerV4VC( null, true );
    					}
    				});
    				main.changeScreen( new MenuScreen() );
    			}
    			
    			return true;
    		}
    	};
    	scene.attachChild( bttn5 );
    	scene.registerTouchArea( bttn5 );
    	
    	String text = "EXCHANGE";
    	Text bttnText6 = new Text( bttn5.getX(), bttn5.getY(), main.mFont, text, text.length(), main.getVertexBufferObjectManager() );
    	float add6 = (bttn5.getWidth() - bttnText6.getWidth() ) / 2;
    	bttnText6.setX( bttnText6.getX() + add6 );
    	float add6h = (bttn5.getHeight() - bttnText6.getHeight() ) / 2;
    	bttnText6.setY( bttnText6.getY() + add6h );
    	scene.attachChild( bttnText6 );
    }
    
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
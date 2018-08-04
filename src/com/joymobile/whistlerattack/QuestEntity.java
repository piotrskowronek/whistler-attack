package com.joymobile.whistlerattack;

import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.util.color.Color;

class QuestEntity
{
	Sprite rect, coin;
	Text fpsText, fps2Text;
	
	Quest quest;
	MainActivity main;
	
	public QuestEntity( Quest quest, final float pX, final float pY, Font font, Font bigger_font, MainActivity main )
	{
		this.quest = quest;
		this.main = main;
		
		float y;
		Font font_text;
		if ( quest.isDoubleLine() )
		{
			y = pY+main.h(0.05f);
			font_text = font;
		}
		else
		{
			y = pY+main.h(0.09f);
			font_text = bigger_font;
		}
		
		rect = new Sprite( pX, pY, main.textures.get( "quest" ), main.getVertexBufferObjectManager() );
		coin = new Sprite( pX+main.w(0.637f), pY+main.h(0.075f), main.textures.get( "coin" ), main.getVertexBufferObjectManager() );
        fpsText = new Text( pX+main.w(0.05f), y, font_text, quest.getName(), quest.getName().length(), main.getVertexBufferObjectManager() );
        fps2Text = new Text( pX+main.w(0.71f), pY+main.h(0.09f), bigger_font, ""+quest.getReward(), (""+quest.getReward()).length(), main.getVertexBufferObjectManager() );
	}
	
	public void attachAsChild( Scene scene )
	{
		scene.attachChild( rect );
		scene.attachChild( coin );
		scene.attachChild( fpsText );
		scene.attachChild( fps2Text );
	}
	
	public float getY()
	{
		return rect.getY();
	}
	
	public Quest getQuest()
	{
		return quest;
	}
	
	public void slideOut()
	{
		Color before = rect.getColor();
		rect.registerEntityModifier( new SequenceEntityModifier(
			new ColorModifier( 1, before, Color.YELLOW ),
			new ColorModifier( 1, Color.YELLOW, before ),
			new MoveXModifier( 1, rect.getX(), main.w(1) )
		));
		fpsText.registerEntityModifier( new SequenceEntityModifier(
				new DelayModifier( 2 ),
				new MoveXModifier( 1, fpsText.getX(), main.w(1)+main.w(0.05f) )
		));
		fps2Text.registerEntityModifier( new SequenceEntityModifier(
				new DelayModifier( 2 ),
				new MoveXModifier( 1, fps2Text.getX(), main.w(1)+main.w(0.71f) )
		));
		coin.registerEntityModifier( new SequenceEntityModifier(
				new DelayModifier( 2 ),
				new MoveXModifier( 1, coin.getX(), main.w(1)+main.w(0.637f) )
		));
	}
	
	public void slideIn()
	{
		rect.registerEntityModifier( new SequenceEntityModifier(
				new DelayModifier( 2 ),
				new MoveXModifier( 1, rect.getX(), main.w(0.07f) )
		));
		fpsText.registerEntityModifier( new SequenceEntityModifier(
				new DelayModifier( 2 ),
				new MoveXModifier( 1, fpsText.getX(), main.w(0.12f) )
		));
		fps2Text.registerEntityModifier( new SequenceEntityModifier(
				new DelayModifier( 2 ),
				new MoveXModifier( 1, fps2Text.getX(), main.w(0.07)+main.w(0.71f) )
		));
		coin.registerEntityModifier( new SequenceEntityModifier(
				new DelayModifier( 2 ),
				new MoveXModifier( 1, coin.getX(), main.w(0.07)+main.w(0.637f) )
		));
	}
}
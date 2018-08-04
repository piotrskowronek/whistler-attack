package com.joymobile.whistlerattack;

import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.joymobile.whistlerattack.goods.FreezeDurationUpgradeableGoods;

class ComboBar extends Sprite implements Combo.Listener
{
	ScaleModifier mod;
	MoveYModifier mod2;
	MainActivity main;
	
	Sprite frozen;
	
	public ComboBar(float pX, float pY, ITextureRegion texture, VertexBufferObjectManager pVertexBufferObjectManager, MainActivity m) 
	{
		super( m.w(1), pY, texture, pVertexBufferObjectManager );
		
		main = m;
		
		registerEntityModifier( new MoveXModifier( 1, m.w(1), pX ) );
		
		frozen = new Sprite( pX, pY, main.textures.get( "frozen_bar" ), main.getVertexBufferObjectManager() );
		main.scene.attachChild( frozen );
		frozen.setScale(0);
	}

	public void onComboTick(int counter, boolean is_frozen, TimerHandler timer)
	{
		if ( ! is_frozen )
		{
			if ( counter > 1 )
			{
				unregisterEntityModifier( mod );
				unregisterEntityModifier( mod2 );
			}
			
			setScale( 1 );
			setY( MainActivity.CAMERA_HEIGHT*0.1f );
			
			mod = new ScaleModifier( Time.get(0.5f), 1, 1, 1, 0 );
			registerEntityModifier( mod );
			
			mod2 = new MoveYModifier( Time.get(0.5f), MainActivity.CAMERA_HEIGHT*0.1f, MainActivity.CAMERA_HEIGHT*0.47f );
			registerEntityModifier( mod2 );
		}
	}

	public void onComboFinish(int counter)
	{
		unregisterEntityModifier( mod );
		unregisterEntityModifier( mod2 );
		
		setScale( 0 );
		setY( MainActivity.CAMERA_HEIGHT*0.1f );
	}

	public void onComboFreeze() 
	{
		frozen.unregisterEntityModifier( mod );
		frozen.unregisterEntityModifier( mod2 );
		unregisterEntityModifier( mod );
		unregisterEntityModifier( mod2 );
		setScale( 0 );
		
		frozen.setScale( 1 );
		frozen.setY( MainActivity.CAMERA_HEIGHT*0.1f );
		
		final FreezeDurationUpgradeableGoods goods = (FreezeDurationUpgradeableGoods)StoreGoodsProxy.get().getGoods( StoreGoodsProxy.UPGRADES, "FreezeDuration" );
		
		mod = new ScaleModifier( goods.getDuration(), 1, 1, 1, 0 );
		frozen.registerEntityModifier( mod );
		
		mod2 = new MoveYModifier( goods.getDuration(), MainActivity.CAMERA_HEIGHT*0.1f, MainActivity.CAMERA_HEIGHT*0.47f );
		frozen.registerEntityModifier( mod2 );
	}

	public void onComboUnfreeze() 
	{
		frozen.setScale( 0 );
	}
	
}
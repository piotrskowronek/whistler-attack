package com.joymobile.whistlerattack.goods;

import org.andengine.opengl.font.Font;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.joymobile.whistlerattack.CustomGoods;
import com.joymobile.whistlerattack.CustomGoodsEntity;
import com.joymobile.whistlerattack.Goods;
import com.joymobile.whistlerattack.GoodsEntity;
import com.joymobile.whistlerattack.HavingEntities;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class FreeGoods extends CustomGoods
{
	public FreeGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "--AWARD--";
		currency_unit = new StarCurrencyUnit( 350000 );
		thumbnail = "fourth_treasure";
		desc = "aWaRd";
	}
	
	@Override
	public boolean action()
	{
		new StarCurrencyUnit(1000000).award();
		
		return true;
	}

	@Override
	public GoodsEntity makeEntity( int h, int h2, Font mFont5, Font mFont4, MainActivity main, HavingEntities screen )
	{
		return new FreeGoods.Entity( this, h, h2, mFont5, mFont4, main, screen );
	}
	
	@Override
	public boolean needsLoadOnStart()
	{
		return false;
	}
	
	@Override
	public void onLoad()
	{

	}

	public class Entity extends CustomGoodsEntity
	{
		public Entity( Goods goods, float pX, float pY, Font font, Font bigger_font, MainActivity main, HavingEntities screen )
		{
			super( goods, pX, pY, font, bigger_font, main, screen );
		}
		
		@Override
		protected void createActionButton( final float pX, final float pY, Font bigger_font )
		{
			label = "AWARD";
			
			super.createActionButton( pX, pY, bigger_font );
		}
	}
}
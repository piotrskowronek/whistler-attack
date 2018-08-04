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

abstract public class PackGoods extends CustomGoods
{
	public PackGoods( MainActivity main )
	{
		super( main );
	}
	
	protected String billing_id;
	protected String hash_key;
	
	public String getHashKey()
	{
		return hash_key;
	}

	public String getBillingId()
	{
		return billing_id;
	}

	@Override
	public boolean action()
	{
		try
		{
			Bundle buyIntentBundle = main.mService.getBuyIntent( 3, main.getPackageName(), getBillingId(), "inapp", getHashKey() );
			if ( buyIntentBundle.getInt( "RESPONSE_CODE" ) == 0 )
			{
				PendingIntent pendingIntent = buyIntentBundle.getParcelable( "BUY_INTENT" );
				main.startIntentSenderForResult( pendingIntent.getIntentSender(), 1001, new Intent(), Integer.valueOf( 0 ), Integer.valueOf( 0 ), Integer.valueOf( 0 ) );
			}
			
		} 
		catch ( Exception e )
		{
			e.printStackTrace();
		} 
		
		return true;
	}

	@Override
	public GoodsEntity makeEntity( int h, int h2, Font mFont5, Font mFont4, MainActivity main, HavingEntities screen )
	{
		return new PackGoods.Entity( this, h, h2, mFont5, mFont4, main, screen );
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
			label = "PURCHASE";
			
			super.createActionButton( pX, pY, bigger_font );
		}
	}
}
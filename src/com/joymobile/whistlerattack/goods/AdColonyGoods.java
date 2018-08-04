package com.joymobile.whistlerattack.goods;

import org.andengine.opengl.font.Font;

import com.jirbo.adcolony.AdColonyVideoAd;
import com.joymobile.whistlerattack.CustomGoods;
import com.joymobile.whistlerattack.CustomGoodsEntity;
import com.joymobile.whistlerattack.Goods;
import com.joymobile.whistlerattack.GoodsEntity;
import com.joymobile.whistlerattack.HavingEntities;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.PricelessCurrencyUnit;

public class AdColonyGoods extends CustomGoods
{
	public AdColonyGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Watch Videos";
		currency_unit = new PricelessCurrencyUnit();
		thumbnail = "adcolony_thumb";
		desc = "Watch video ads to\n" +
				"thicken your wallet\n" +
				"needing more stars!";
	}
	
	@Override
	public boolean action()
	{
		main.runOnUiThread( new Runnable()
		{
			public void run() 
			{
				AdColonyVideoAd ad = new AdColonyVideoAd( "" );
				ad.offerV4VC( null, true );
			}
		});
		
		return true;
	}

	@Override
	public GoodsEntity makeEntity( int h, int h2, Font mFont5, Font mFont4, MainActivity main, HavingEntities screen )
	{
		return new AdColonyGoods.Entity( this, h, h2, mFont5, mFont4, main, screen );
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
			label = "EARN STARS";
			
			super.createActionButton( pX, pY, bigger_font );
		}
	}

	
}
package com.joymobile.whistlerattack.goods;

import org.andengine.opengl.font.Font;

import com.joymobile.whistlerattack.CustomGoods;
import com.joymobile.whistlerattack.CustomGoodsEntity;
import com.joymobile.whistlerattack.Goods;
import com.joymobile.whistlerattack.GoodsEntity;
import com.joymobile.whistlerattack.HavingEntities;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.PricelessCurrencyUnit;
import com.joymobile.whistlerattack.StarCurrencyUnit;
import com.tapjoy.TapjoyConnect;

public class TapjoyGoods extends CustomGoods
{
	public TapjoyGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Misc Content";
		currency_unit = new PricelessCurrencyUnit();
		thumbnail = "tapjoy_thumb";
		desc = "Download apps, watch\n" +
				"videos and do other\n" +
				"activities to earn!";
	}
	
	@Override
	public boolean action()
	{
		TapjoyConnect.getTapjoyConnectInstance().showOffers();
		
		return true;
	}

	@Override
	public GoodsEntity makeEntity( int h, int h2, Font mFont5, Font mFont4, MainActivity main, HavingEntities screen )
	{
		return new TapjoyGoods.Entity( this, h, h2, mFont5, mFont4, main, screen );
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
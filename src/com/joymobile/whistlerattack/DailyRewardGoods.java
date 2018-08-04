package com.joymobile.whistlerattack;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.andengine.opengl.font.Font;

abstract public class DailyRewardGoods extends Goods
{
	public DailyRewardGoods( MainActivity main )
	{
		super( main );
	}

	protected CurrencyUnit currency_unit;
	protected boolean enabled = false;
	
	@Override
	public GoodsEntity makeEntity( int h, int h2, Font mFont5, Font mFont4, MainActivity main, HavingEntities screen )
	{
		return new DailyRewardGoodsEntity( this, h, h2, mFont5, mFont4, main, screen );
	}
	
	public boolean collect()
	{
		if ( isEnabled() )
		{
			SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yy" );
			String formattedDate = df.format( new Date() );
			Database.setString( "daily_reward_date", formattedDate );
			
			Database.setString( "daily_reward_last_collected", getDatabaseKey() );
			
			currency_unit.award();
			
			enabled = false;
			
			return true;
		}
		
		return false;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}

	@Override
	public boolean needsLoadOnStart()
	{
		return false;
	}
	
	public boolean isCollected()
	{
		return Database.getString( "daily_reward_last_collected" ).equals( getDatabaseKey() );
	}

	@Override
	public CurrencyUnit getPriceInCurrencyUnit()
	{
		return currency_unit;
	}

	public void signEnabled()
	{
		enabled = true;
	}
}
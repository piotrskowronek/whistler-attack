package com.joymobile.whistlerattack;

import org.andengine.opengl.font.Font;

abstract public class UnlimitedGoods extends Goods
{
	public UnlimitedGoods( MainActivity main )
	{
		super( main );
	}

	protected CurrencyUnit currency_unit;
	
	@Override
	public GoodsEntity makeEntity( int h, int h2, Font mFont5, Font mFont4, MainActivity main, HavingEntities screen )
	{
		return new UnlimitedGoodsEntity( this, h, h2, mFont5, mFont4, main, screen );
	}
	
	public boolean buy()
	{
		if ( getPriceInCurrencyUnit().purchase() == false )
		{
			return false;
		}
		
		Database.raiseInt( getDatabaseKey() );
		
		return true;
	}
	
	public boolean consume()
	{
		if ( Database.getInt( getDatabaseKey() ) <= 0 )
			return false;
		
		Database.decreaseInt( getDatabaseKey() );
		
		return true;
	}
	
	@Override
	public boolean needsLoadOnStart()
	{
		return ( getGoodsLeftCount() > 0 );
	}
	
	public int getGoodsLeftCount()
	{
		return Database.getInt( getDatabaseKey() );
	}

	@Override
	public CurrencyUnit getPriceInCurrencyUnit()
	{
		return currency_unit;
	}
}
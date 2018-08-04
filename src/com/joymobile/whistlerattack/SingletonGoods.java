package com.joymobile.whistlerattack;

import org.andengine.opengl.font.Font;

abstract public class SingletonGoods extends Goods
{
	public SingletonGoods( MainActivity main )
	{
		super( main );
	}

	protected CurrencyUnit currency_unit;
	
	@Override
	public GoodsEntity makeEntity( int h, int h2, Font mFont5, Font mFont4, MainActivity main, HavingEntities screen )
	{
		return new SingletonGoodsEntity( this, h, h2, mFont5, mFont4, main, screen );
	}
	
	public boolean buy()
	{
		if ( getPriceInCurrencyUnit().purchase() == false )
			return false;
		
		if ( Database.getBoolean( getDatabaseKey() ) )
			return false;
		
		Database.setBoolean( getDatabaseKey(), true );
		
		return true;
	}
	
	@Override
	public boolean needsLoadOnStart()
	{
		return isBought();
	}
	
	public boolean isBought()
	{
		return Database.getBoolean( getDatabaseKey() );
	}

	@Override
	public CurrencyUnit getPriceInCurrencyUnit()
	{
		return currency_unit;
	}
}
package com.joymobile.whistlerattack;

import org.andengine.opengl.font.Font;

abstract public class GroupGoods extends Goods
{
	public GroupGoods( MainActivity main )
	{
		super( main );
	}

	protected CurrencyUnit currency_unit;
	protected String database_group_key;
	
	public String getDatabaseGroupKey()
	{
		return database_group_key;
	}

	@Override
	public GoodsEntity makeEntity( int h, int h2, Font mFont5, Font mFont4, MainActivity main, HavingEntities screen )
	{
		return new GroupGoodsEntity( this, h, h2, mFont5, mFont4, main, screen );
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
	
	public boolean consume()
	{
		if ( Database.getBoolean( getDatabaseKey() ) == false )
			return false;
		
		Database.setBoolean( getDatabaseKey(), false );
		return true;
	}
	
	public boolean isBought()
	{
		return Database.getBoolean( getDatabaseKey() );
	}
	
	public void use()
	{
		Database.setString( getDatabaseGroupKey(), getDatabaseKey() );
	}
	
	public void deuse()
	{
		Database.setString( getDatabaseGroupKey(), "" );
	}
	
	public boolean isInUse()
	{
		return Database.getString( getDatabaseGroupKey() ).equals( getDatabaseKey() );
	}

	@Override
	public boolean needsLoadOnStart()
	{
		return ( isBought() && isInUse() );
	}
	
	@Override
	public CurrencyUnit getPriceInCurrencyUnit()
	{
		return isBought() ? new PricelessCurrencyUnit() : currency_unit;
	}
}
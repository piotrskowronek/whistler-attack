package com.joymobile.whistlerattack;

import org.andengine.opengl.font.Font;

abstract public class UpgradeableGoods extends Goods
{
	public UpgradeableGoods( MainActivity main )
	{
		super( main );
	}

	protected int level = 0;
	protected int max_level = 0;

	protected CurrencyUnit[] currency_unit;
	
	@Override
	public GoodsEntity makeEntity( int h, int h2, Font mFont5, Font mFont4, MainActivity main, HavingEntities screen )
	{
		return new UpgradeableGoodsEntity( this, h, h2, mFont5, mFont4, main, screen );
	}
	
	@Override
	public boolean needsLoadOnStart()
	{
		return ( getLevel() > 0 );
	}
	
	public boolean upgrade()
	{
		if ( getPriceInCurrencyUnit().purchase() == false )
		{
			return false;
		}
			
		if ( canUpgrade() == false )
		{
			return false;
		}
			
		Database.raiseInt( getDatabaseKey() );
		level = Database.getInt( getDatabaseKey() );
		
		return true;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public int getMaxLevel()
	{
		return max_level;
	}

	private boolean canUpgrade()
	{
		return level < max_level;
	}
	
	@Override
	public CurrencyUnit getPriceInCurrencyUnit()
	{
		return currency_unit[level];
	}

	protected void loadLevel()
	{
		level = Database.getInt( getDatabaseKey() );
	}
}
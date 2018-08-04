package com.joymobile.whistlerattack;

abstract public class CustomGoods extends Goods
{
	public CustomGoods( MainActivity main )
	{
		super( main );
	}

	protected CurrencyUnit currency_unit;
	
	abstract public boolean action();

	@Override
	public CurrencyUnit getPriceInCurrencyUnit()
	{
		return currency_unit;
	}
}
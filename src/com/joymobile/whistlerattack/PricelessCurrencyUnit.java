package com.joymobile.whistlerattack;

public class PricelessCurrencyUnit extends CurrencyUnit
{
	public PricelessCurrencyUnit()
	{

	}

	@Override
	public CurrencyUnitEntity makeCurrencyUnitEntity()
	{
		return new PricelessCurrencyUnitEntity();
	}

	@Override
	public boolean purchase()
	{
		return false;
	}

	@Override
	public boolean canPurchase()
	{
		return false;
	}

	@Override
	public void award()
	{
	}
}

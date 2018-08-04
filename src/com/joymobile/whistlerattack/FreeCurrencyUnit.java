package com.joymobile.whistlerattack;

public class FreeCurrencyUnit extends CurrencyUnit
{
	public FreeCurrencyUnit()
	{

	}

	@Override
	public CurrencyUnitEntity makeCurrencyUnitEntity()
	{
		return new FreeCurrencyUnitEntity();
	}

	@Override
	public boolean purchase()
	{
		return true;
	}

	@Override
	public boolean canPurchase()
	{
		return true;
	}

	@Override
	public void award()
	{
	}
}

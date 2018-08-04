package com.joymobile.whistlerattack;

public class RealCurrencyUnit extends CurrencyUnit
{
	float value;
	
	public RealCurrencyUnit( float value )
	{
		this.value = value;
	}

	@Override
	public CurrencyUnitEntity makeCurrencyUnitEntity()
	{
		return new RealCurrencyUnitEntity( value );
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

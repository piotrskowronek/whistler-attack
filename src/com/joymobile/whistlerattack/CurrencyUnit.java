package com.joymobile.whistlerattack;

public abstract class CurrencyUnit
{
	abstract public CurrencyUnitEntity makeCurrencyUnitEntity();
	abstract public boolean purchase();
	abstract public boolean canPurchase();
	abstract public void award();
}

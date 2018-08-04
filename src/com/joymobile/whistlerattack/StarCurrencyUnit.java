package com.joymobile.whistlerattack;

public class StarCurrencyUnit extends CurrencyUnit
{
	int value;
	
	public StarCurrencyUnit( int value )
	{
		this.value = value;
	}

	@Override
	public CurrencyUnitEntity makeCurrencyUnitEntity()
	{
		return new StarCurrencyUnitEntity( value );
	}

	@Override
	public boolean purchase()
	{
		if ( canPurchase() )
		{
			minusCurrency( value );
			return true;
		}
		else
		{
			return false;
		}
	}

	private void minusCurrency( int value2 )
	{
		setCurrencyAmount( getCurrencyAmount() - value2 );
	}

	@Override
	public boolean canPurchase()
	{
		return getCurrencyAmount() >= value;
	}

	private int getCurrencyAmount()
	{
		return Database.getInt( "currency" );
	}
	
	private void setCurrencyAmount( int value2 )
	{
		Database.setInt( "currency", value2 );
	}

	@Override
	public void award()
	{
		plusCurrency( value );
	}

	private void plusCurrency( int value2 )
	{
		setCurrencyAmount( getCurrencyAmount() + value2 );
	}
}

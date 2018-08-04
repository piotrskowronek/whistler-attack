package com.joymobile.whistlerattack;

import java.util.Random;

public class RandomStarCurrencyUnit extends CurrencyUnit
{
	int value;
	
	public RandomStarCurrencyUnit( int value, int value2 )
	{
		Random rand = new Random();
		this.value = rand.nextInt( value2 - value ) + value;
	}

	@Override
	public CurrencyUnitEntity makeCurrencyUnitEntity()
	{
		return new RandomStarCurrencyUnitEntity();
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

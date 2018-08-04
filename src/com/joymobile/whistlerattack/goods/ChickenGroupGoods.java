package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class ChickenGroupGoods extends CharactersGroupGoods
{
	public ChickenGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Chicken";
		currency_unit = new StarCurrencyUnit( 10000 );
		thumbnail = "chicken_thumb";
		database_key = "chicken";
		desc = "Chicken might be used\n" +
				"not only on easter!\n" +
				"This is yours!";
	}
}
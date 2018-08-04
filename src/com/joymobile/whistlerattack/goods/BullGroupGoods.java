package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class BullGroupGoods extends CharactersGroupGoods
{
	public BullGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Bull";
		currency_unit = new StarCurrencyUnit( 10000 );
		thumbnail = "bull_thumb";
		database_key = "bull";
		desc = "Bulls are ideal for\n" +
				"men. Express your\n" +
				"bravery and power!";
	}
}
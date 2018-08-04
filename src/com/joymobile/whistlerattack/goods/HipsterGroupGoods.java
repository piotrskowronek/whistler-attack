package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class HipsterGroupGoods extends ExtrasGroupGoods
{
	public HipsterGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Hipster Glasses";
		currency_unit = new StarCurrencyUnit( 10000 );
		thumbnail = "hipster_thumb";
		database_key = "hipster";
		desc = "These glasses are\n" +
				"created to be\n" +
				"extraordinary.";
	}
}
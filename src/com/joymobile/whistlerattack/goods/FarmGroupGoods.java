package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class FarmGroupGoods extends ScenesGroupGoods
{
	public FarmGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Farm";
		currency_unit = new StarCurrencyUnit( 10000 );
		thumbnail = "farm_thumb";
		database_key = "farm";
		desc = "Farm has an unique\n" +
				"atmosphere. Try it\n" +
				"and don't regret!";
	}
}
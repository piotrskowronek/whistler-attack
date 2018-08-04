package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class ForestGroupGoods extends ScenesGroupGoods
{
	public ForestGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Forest";
		currency_unit = new StarCurrencyUnit( 10000 );
		thumbnail = "forest_thumb";
		database_key = "forest";
		desc = "Bored of carnival?\n" +
				"Transfer yourself to\n" +
				"quiet forest!";
	}
}
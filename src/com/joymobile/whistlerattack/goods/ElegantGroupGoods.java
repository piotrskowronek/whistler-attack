package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class ElegantGroupGoods extends ExtrasGroupGoods
{
	public ElegantGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Elegant Glasses";
		currency_unit = new StarCurrencyUnit( 10000 );
		thumbnail = "elegant_thumb";
		database_key = "elegant";
		desc = "Do you consider your-\n" +
				"self smart? Catch\n" +
				"these!";
	}
}
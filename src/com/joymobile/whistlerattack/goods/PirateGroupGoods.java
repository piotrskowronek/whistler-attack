package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class PirateGroupGoods extends ExtrasGroupGoods
{
	public PirateGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Pirate Band";
		currency_unit = new StarCurrencyUnit( 10000 );
		thumbnail = "pirate_thumb";
		database_key = "pirate";
		desc = "Your animal looks\n" +
				"much more threat-\n" +
				"eningly with it!";
	}
}
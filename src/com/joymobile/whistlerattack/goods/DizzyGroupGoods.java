package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class DizzyGroupGoods extends ExtrasGroupGoods
{
	public DizzyGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Dizzy Glasses";
		currency_unit = new StarCurrencyUnit( 100000 );
		thumbnail = "dizzy_thumb";
		database_key = "dizzy";
		desc = "Only for privileged\n" +
				"one!";
	}
}
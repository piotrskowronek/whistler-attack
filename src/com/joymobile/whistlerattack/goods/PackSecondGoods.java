package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class PackSecondGoods extends PackGoods
{
	public PackSecondGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Medium Star Pack";
		currency_unit = new StarCurrencyUnit( 30000 );
		thumbnail = "second_treasure";
		billing_id = "com.joymobile.whistlerattack.second_star_pack";
		hash_key = "a9f0e61a137d86aa9db53465e0801612";
		desc = "Quick byway to buy\n" +
				"needing items. Medium\n" +
				"pack for med. shopping!";
	}
}
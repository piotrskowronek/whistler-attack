package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class PackFifthGoods extends PackGoods
{
	public PackFifthGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Mega Star Pack";
		currency_unit = new StarCurrencyUnit( 1000000 );
		thumbnail = "fifth_treasure";
		billing_id = "com.joymobile.whistlerattack.fifth_star_pack";
		hash_key = "0883a6520e6eb6c9304dcfb71034d053";
		desc = "The best option! Take\n" +
				"this to save up to 100%\n" +
				"money!";
	}
}
package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class PackFourthGoods extends PackGoods
{
	public PackFourthGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Huge Star Pack";
		currency_unit = new StarCurrencyUnit( 350000 );
		thumbnail = "fourth_treasure";
		billing_id = "com.joymobile.whistlerattack.fourth_star_pack";
		hash_key = "c0759f2416498708841e7975566360ce";
		desc = "Buy items that you\n" +
				"could't afford before.\n" +
				"Now it's possible!";
	}
}
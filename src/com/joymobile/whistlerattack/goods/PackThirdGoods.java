package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class PackThirdGoods extends PackGoods
{
	public PackThirdGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Big Star Pack";
		currency_unit = new StarCurrencyUnit( 120000 );
		thumbnail = "third_treasure";
		billing_id = "com.joymobile.whistlerattack.third_star_pack";
		hash_key = "8b25c10b3be436212ece3dc58cb51404";
		desc = "Big pack of stars!\n" +
				"Helpful for bigger\n" +
				"shopping!";
	}
}
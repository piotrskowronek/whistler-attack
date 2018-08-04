package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.Database;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.PricelessCurrencyUnit;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class KoalaGroupGoods extends CharactersGroupGoods
{
	public KoalaGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Koala Bear";
		currency_unit = new StarCurrencyUnit( 100000 );
		thumbnail = "koala_thumb";
		database_key = "koala";
		desc = "Koalas are sweet\n" +
				"animals! Grasp one!";
	}
}
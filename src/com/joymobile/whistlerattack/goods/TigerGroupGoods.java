package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class TigerGroupGoods extends CharactersGroupGoods
{
	public TigerGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Tiger";
		currency_unit = new StarCurrencyUnit( 10000 );
		thumbnail = "tiger_thumb";
		database_key = "tiger";
		desc = "It only looks so\n" +
				"harmlessly. Don't be\n" +
				"fooled by appearances!";
	}
}
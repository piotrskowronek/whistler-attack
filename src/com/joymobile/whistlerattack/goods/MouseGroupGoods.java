package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class MouseGroupGoods extends CharactersGroupGoods
{
	public MouseGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Mouse";
		currency_unit = new StarCurrencyUnit( 10000 );
		thumbnail = "mouse_thumb";
		database_key = "mouse";
		desc = "Little mouse is just\n" +
				"waiting to be choosen!\n" +
				"They are beautiful!";
	}
}
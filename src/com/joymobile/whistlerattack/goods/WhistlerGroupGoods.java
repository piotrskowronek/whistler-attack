package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.Database;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.PricelessCurrencyUnit;

public class WhistlerGroupGoods extends CharactersGroupGoods
{
	public WhistlerGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Whistler";
		currency_unit = new PricelessCurrencyUnit();
		thumbnail = "whistler_thumb";
		database_key = "whistler";
		desc = "Whistlers are very\n" +
				"friendly. Let them\n" +
				"be your friends!";
	}
	
	@Override
	public boolean isDefault()
	{
		return true;
	}

	@Override
	public void doDefaultAction()
	{
		Database.setString( "characters", "whistler" );
		Database.setBoolean( "whistler", true );
	}
}
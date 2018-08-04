package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.Database;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.PricelessCurrencyUnit;

public class CarnivalGroupGoods extends ScenesGroupGoods
{
	public CarnivalGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Carnival";
		currency_unit = new PricelessCurrencyUnit();
		thumbnail = "carnival_thumb";
		database_key = "carnival";
		desc = "Ley the joy goes on.\n" +
				"Carnival is full of fun!";
	}
	
	@Override
	public boolean isDefault()
	{
		return true;
	}

	@Override
	public void doDefaultAction()
	{
		Database.setString( "scenes", "carnival" );
		Database.setBoolean( "carnival", true );
	}
}
package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.CurrencyUnit;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.PricelessCurrencyUnit;
import com.joymobile.whistlerattack.StarCurrencyUnit;
import com.joymobile.whistlerattack.UpgradeableGoods;

public class FreezeDurationUpgradeableGoods extends UpgradeableGoods
{
	public FreezeDurationUpgradeableGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Freeze Duration";
		currency_unit = new CurrencyUnit[]
		{
			new StarCurrencyUnit(500),
			new StarCurrencyUnit(1000),
			new StarCurrencyUnit(3000),
			new StarCurrencyUnit(10000),
			new StarCurrencyUnit(50000),
			new PricelessCurrencyUnit()
		};
		thumbnail = "freeze_duration";
		max_level = 5;
		database_key = "freeze_duration_goods";
		desc = "Upgrade this to make\n" +
				"Freeze Bonus last\n" +
				"longer";
		
		loadLevel();
	}
	
	public float getDuration()
	{
		float[] durations = new float[]{ 5, 6, 7, 8, 9, 10 };
		return durations[level];
	}

	@Override
	public void onLoad()
	{

	}
}
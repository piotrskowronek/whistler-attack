package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.CurrencyUnit;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.PricelessCurrencyUnit;
import com.joymobile.whistlerattack.StarCurrencyUnit;
import com.joymobile.whistlerattack.UpgradeableGoods;

public class BombDurationUpgradeableGoods extends UpgradeableGoods
{
	public BombDurationUpgradeableGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Anti-Bomb Durat.";
		currency_unit = new CurrencyUnit[]
		{
			new StarCurrencyUnit(500),
			new StarCurrencyUnit(1000),
			new StarCurrencyUnit(3000),
			new StarCurrencyUnit(10000),
			new StarCurrencyUnit(50000),
			new PricelessCurrencyUnit()
		};
		thumbnail = "bomb_duration";
		max_level = 5;
		database_key = "bomb_duration_goods";
		desc = "Upgrade this to make\n" +
				"Anti-Bomb Bonus last\n" +
				"last longer";
		
		loadLevel();
	}
	
	public float getDuration()
	{
		float[] durations = new float[]{ 5, 7, 9, 11, 13, 15 };
		return durations[level];
	}

	@Override
	public void onLoad()
	{

	}
}
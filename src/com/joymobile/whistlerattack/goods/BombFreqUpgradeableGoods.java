package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.CurrencyUnit;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.PricelessCurrencyUnit;
import com.joymobile.whistlerattack.StarCurrencyUnit;
import com.joymobile.whistlerattack.UpgradeableGoods;

public class BombFreqUpgradeableGoods extends UpgradeableGoods
{
	public BombFreqUpgradeableGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Anti-Bomb Freq.";
		currency_unit = new CurrencyUnit[]
		{
			new StarCurrencyUnit(500),
			new StarCurrencyUnit(1500),
			new StarCurrencyUnit(3000),
			new StarCurrencyUnit(10000),
			new StarCurrencyUnit(30000),
			new StarCurrencyUnit(100000),
			new PricelessCurrencyUnit()
		};
		thumbnail = "bomb_freq";
		max_level = 6;
		database_key = "bomb_freq_goods";
		desc = "Increase frequency\n" +
				"of showing Anti-Bomb\n" +
				"Bonus by 20%";
		
		loadLevel();
	}
	
	public double getFrequency()
	{
		if ( level > 0 )
			return 0.025 * Math.pow( 1.2, level );
		else
			return 0.025;
	}

	@Override
	public void onLoad()
	{

	}
}
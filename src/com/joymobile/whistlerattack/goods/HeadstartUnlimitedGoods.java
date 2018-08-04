package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;
import com.joymobile.whistlerattack.UnlimitedGoods;

public class HeadstartUnlimitedGoods extends UnlimitedGoods
{
	public HeadstartUnlimitedGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Headstart";
		currency_unit = new StarCurrencyUnit( 2000 );
		thumbnail = "headstart_thumb";
		database_key = "mega_headstart";
		desc = "Start the game from\n" +
				"third level. Save your\n" +
				"time";
	}

	public int getLevelIndex()
	{
		return 5;
	}

	@Override
	public void onLoad()
	{
		
	}
}
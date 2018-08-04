package com.joymobile.whistlerattack.rewards;

import com.joymobile.whistlerattack.DailyRewardGoods;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class FourthDailyRewardGoods extends DailyRewardGoods
{
	public FourthDailyRewardGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Day 4";
		currency_unit = new StarCurrencyUnit( 750 );
		thumbnail = "fourth_treasure";
		database_key = "fourth_daily_reward_goods";
	}

	@Override
	public void onLoad()
	{
		
	}
}
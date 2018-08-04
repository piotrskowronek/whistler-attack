package com.joymobile.whistlerattack.rewards;

import com.joymobile.whistlerattack.DailyRewardGoods;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class FirstDailyRewardGoods extends DailyRewardGoods
{
	public FirstDailyRewardGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Day 1";
		currency_unit = new StarCurrencyUnit( 100 );
		thumbnail = "first_treasure";
		database_key = "first_daily_reward_goods";
	}

	@Override
	public void onLoad()
	{
		
	}
}
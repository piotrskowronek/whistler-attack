package com.joymobile.whistlerattack.rewards;

import com.joymobile.whistlerattack.DailyRewardGoods;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class ThirdDailyRewardGoods extends DailyRewardGoods
{
	public ThirdDailyRewardGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Day 3";
		currency_unit = new StarCurrencyUnit( 500 );
		thumbnail = "third_treasure";
		database_key = "third_daily_reward_goods";
	}

	@Override
	public void onLoad()
	{
		
	}
}
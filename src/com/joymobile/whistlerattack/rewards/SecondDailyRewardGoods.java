package com.joymobile.whistlerattack.rewards;

import com.joymobile.whistlerattack.DailyRewardGoods;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class SecondDailyRewardGoods extends DailyRewardGoods
{
	public SecondDailyRewardGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Day 2";
		currency_unit = new StarCurrencyUnit( 300 );
		thumbnail = "second_treasure";
		database_key = "second_daily_reward_goods";
	}

	@Override
	public void onLoad()
	{
		
	}
}
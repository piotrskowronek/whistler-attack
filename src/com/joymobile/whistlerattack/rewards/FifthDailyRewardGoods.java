package com.joymobile.whistlerattack.rewards;

import com.joymobile.whistlerattack.DailyRewardGoods;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.RandomStarCurrencyUnit;

public class FifthDailyRewardGoods extends DailyRewardGoods
{
	public FifthDailyRewardGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Day 5";
		currency_unit = new RandomStarCurrencyUnit( 1000, 2000 );
		thumbnail = "fifth_treasure";
		database_key = "fifth_daily_reward_goods";
	}

	@Override
	public void onLoad()
	{
		
	}
}
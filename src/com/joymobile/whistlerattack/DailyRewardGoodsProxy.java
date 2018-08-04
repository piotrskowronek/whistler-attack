package com.joymobile.whistlerattack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

import com.joymobile.whistlerattack.rewards.FifthDailyRewardGoods;
import com.joymobile.whistlerattack.rewards.FirstDailyRewardGoods;
import com.joymobile.whistlerattack.rewards.FourthDailyRewardGoods;
import com.joymobile.whistlerattack.rewards.SecondDailyRewardGoods;
import com.joymobile.whistlerattack.rewards.ThirdDailyRewardGoods;

class DailyRewardGoodsProxy
{
	static DailyRewardGoodsProxy instance = null;
	
	ArrayList<DailyRewardGoods> goods = new ArrayList<DailyRewardGoods>();
	
	public static DailyRewardGoodsProxy get()
    {
        if ( instance == null )
            instance = new DailyRewardGoodsProxy();
        return instance;
    }
	
	private DailyRewardGoodsProxy()
	{
		goods = new ArrayList<DailyRewardGoods>();
	}
	
	public void loadGoodsList( MainActivity main )
	{
		goods.clear();
		
		goods.add( new FirstDailyRewardGoods( main ) );
		goods.add( new SecondDailyRewardGoods( main ) );
		goods.add( new ThirdDailyRewardGoods( main ) );
		goods.add( new FourthDailyRewardGoods( main ) );
		goods.add( new FifthDailyRewardGoods( main ) );
		
		signRelevantGoodsEnabled();
	}

	private void signRelevantGoodsEnabled()
	{
		Log.d( "LAST_COLL", Database.getString( "daily_reward_last_collected" ) );
		Log.d( "DATE", Database.getString( "daily_reward_date" ) );
		if ( canGrantNextReward() )
		{
			int i = 0;
			for ( DailyRewardGoods dr : goods )
			{
				if ( ! Database.getString( "daily_reward_last_collected" ).equals( dr.getDatabaseKey() ) )
				{
					i++;
					continue;
				}
				
				int next_reward = i + 1;
				if ( goods.size() - 1 < next_reward )
					goods.get( i ).signEnabled();
				else
					goods.get( next_reward ).signEnabled();
				
				return;
			}
		}

		SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yy" );
		String formattedDate = df.format( new Date() );
		try
		{
			if ( Database.getString( "daily_reward_date" ).equals( "" ) ||
				 ( haveNotTodayCollected( formattedDate ) && haveNotCollectedInFuture( df ) ) )
				goods.get( 0 ).signEnabled();
		} 
		catch ( ParseException e )
		{
			e.printStackTrace();
		}
	}

	private boolean haveNotCollectedInFuture( SimpleDateFormat df ) throws ParseException
	{
		return ! df.parse( Database.getString( "daily_reward_date" ) ).after( new Date() );
	}

	private boolean haveNotTodayCollected( String formattedDate )
	{
		return ! Database.getString( "daily_reward_date" ).equals( formattedDate );
	}

	private boolean canGrantNextReward()
	{
		SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yy" );
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.DATE, -1 );
		String formattedDate = df.format( cal.getTime() );
		
		return Database.getString( "daily_reward_date" ).equals( formattedDate );
	}
	
	public ArrayList<DailyRewardGoods> getGoodsList()
	{
		return goods;
	}

	public DailyRewardGoods getGoods( int id )
	{
		return goods.get( id );
	}
}
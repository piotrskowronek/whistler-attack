package com.joymobile.whistlerattack;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DateChangeReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive( Context context, Intent intent )
	{
		Log.w( "DataChangeReceiver", "RECEIVED" );
		Database.initialize( context );
		
		Database.setString( "daily_reward_last_collected", "first_daily_reward_goods" );
		
		SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yy" );
		String formattedDate = df.format( new Date() );
		Database.setString( "daily_reward_date", formattedDate );
	}
}

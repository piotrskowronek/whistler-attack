package com.joymobile.whistlerattack;

import android.app.Application;

import com.scoreloop.client.android.ui.ScoreloopManagerSingleton;

public class WhistlerAttack extends Application 
{
	@Override
	public void onCreate() 
	{
		super.onCreate();
		ScoreloopManagerSingleton.init(this, "");
	}
	 
	@Override
	public void onTerminate() 
	{
		super.onTerminate();
		ScoreloopManagerSingleton.destroy();
	}
}

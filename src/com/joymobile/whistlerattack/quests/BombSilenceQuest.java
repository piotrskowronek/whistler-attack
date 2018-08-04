package com.joymobile.whistlerattack.quests;

import android.util.Log;

import com.joymobile.whistlerattack.Bomb;
import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Star;
import com.joymobile.whistlerattack.Unit;
import com.joymobile.whistlerattack.Whistler;

class BombSilenceQuest extends Quest
{
	{
		permissions = new int[]{ 
			Bomb.TICK,
			Star.TICK,
			Whistler.TICK,
			Bomb.OPEN
		};
		
		id = "BombSilence";
		name = "Don't play after showing bomb";
		reward = 250;
	}
	
	boolean silence = false;
	boolean fail = false;
	
	public BombSilenceQuest()
	{
		
	}
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Bomb.OPEN )
		{
			silence = true;
		}
		else if ( code == Whistler.TICK || code == Star.TICK || code == Bomb.TICK )
		{
			if ( silence )
			{
				fail = true;
			}
		}
	}
	
	@Override
	public boolean areConditionsAccomplished()
	{
		Log.d( "silence", silence ? "T":"N");
		Log.d( "fail", fail ? "T":"N" );
		return silence == true && fail == false;
	}
}
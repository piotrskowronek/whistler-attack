package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Star;
import com.joymobile.whistlerattack.Unit;

class TwentyStarsQuest extends Quest
{
	{
		permissions = new int[]{ 
			Star.TICK
		};
		
		id = "TwentyStars";
		name = "Collect exactly 20 stars";
		reward = 220;
	}
	
	int counter = 0;
	
	public TwentyStarsQuest()
	{
		
	}
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Star.TICK )
		{
			counter++;
		}
	}
	
	@Override
	public boolean areConditionsAccomplished()
	{
		return counter == 20;
	}
}
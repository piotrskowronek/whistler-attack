package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Star;
import com.joymobile.whistlerattack.Unit;

class FiftyStarsQuest extends Quest
{
	{
		permissions = new int[]{ 
			Star.TICK
		};
		
		id = "FiftyStars";
		name = "Collect exactly 50 stars";
		reward = 300;
	}
	
	int counter = 0;
	
	public FiftyStarsQuest()
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
		return counter == 50;
	}
}
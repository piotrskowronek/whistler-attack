package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Star;
import com.joymobile.whistlerattack.Unit;

class SomeStarsQuest extends Quest
{
	{
		permissions = new int[]{ 
			Star.TICK
		};
		
		id = "SomeStars";
		name = "Collect 30 stars in one game";
		reward = 170;
	}
	
	int counter = 0;
	
	public SomeStarsQuest()
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
		return counter >= 30;
	}
}
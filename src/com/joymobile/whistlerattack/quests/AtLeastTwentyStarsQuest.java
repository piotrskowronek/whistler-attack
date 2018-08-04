package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Star;
import com.joymobile.whistlerattack.Unit;

class AtLeastTwentyStarsQuest extends Quest
{
	{
		permissions = new int[]{ 
			Star.TICK,
			Star.MISS
		};
		
		id = "AtLeastTwentyStars";
		double_line = true;
		name = "Collect at least 20 stars\n" +
			   "and don't miss anyone";
		reward = 310;
	}
	int to_unlock = 4;
	
	int counter = 0;
	boolean unlocked = false;
	
	public AtLeastTwentyStarsQuest()
	{
		
	}
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Star.TICK )
		{
			counter++;
			
			if ( counter >= to_unlock )
			{
				unlocked = true;
			}
		}
		else if ( code == Star.MISS )
		{
			counter = 0;
		}
	}
	
	@Override
	public boolean areConditionsAccomplished()
	{
		return unlocked;
	}
}
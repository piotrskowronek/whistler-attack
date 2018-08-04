package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Star;
import com.joymobile.whistlerattack.Unit;

class NoStarQuest extends Quest
{
	{
		permissions = new int[]{ 
			Star.TICK,
			Star.OPEN
		};
		
		id = "NoStar";
		double_line = true;
		name = "Don't take any star. Miss at least\n" +
				"20 stars in one game";
		reward = 100;
	}
	
	boolean active = true;
	int counter = 0;
	
	public NoStarQuest()
	{
		
	}
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Star.TICK )
		{
			active = false;
		}
		else if ( code == Star.OPEN )
		{
			counter++;
		}
	}
	
	@Override
	public boolean areConditionsAccomplished()
	{
		return active && counter >= 20;
	}
}
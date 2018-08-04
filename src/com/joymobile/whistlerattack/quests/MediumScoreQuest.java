package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Score;
import com.joymobile.whistlerattack.Unit;

class MediumScoreQuest extends Quest
{
	{
		permissions = new int[]{ 
			Score.COUNT
		};
		
		id = "MediumScore";
		name = "Score 1,000 points";
		reward = 120;
	}
	
	int count = 0;
	
	public MediumScoreQuest()
	{
		
	}
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Score.COUNT )
		{
			count += unit.getInt( 0 );
		}
	}
	
	@Override
	public boolean areConditionsAccomplished()
	{
		return count > 1000;
	}
}
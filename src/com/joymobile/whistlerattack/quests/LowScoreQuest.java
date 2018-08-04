package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Score;
import com.joymobile.whistlerattack.Unit;

public class LowScoreQuest extends Quest
{
	{
		permissions = new int[]{ 
			Score.COUNT
		};
		
		id = "LowScore";
		name = "Score 500 points";
		reward = 40;
	}
	
	int count = 0;
	
	public LowScoreQuest()
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
		return count > 500;
	}
}
package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Combo;
import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Unit;

class ThreeSuperCombosQuest extends Quest
{
	{
		permissions = new int[]{ 
			Combo.COUNT
		};
		
		id = "ThreeSuperCombos";
		name = "Do three 'Super' in a row";
		reward = 160;
	}
	
	int counter = 0;
	boolean unlocked = false;
	
	public ThreeSuperCombosQuest()
	{
		
	}
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Combo.COUNT )
		{
			if ( ! unlocked )
			{
				if ( unit.getInt( 0 ) >= 10 && unit.getInt( 0 ) < 20 )
				{
					counter++;
					
					if ( counter >= 3 )
						unlocked = true;
				}
				else if ( unit.getInt( 0 ) >= 20 )
				{
					counter = 0;
				}
			}
		}
	}
	
	@Override
	public boolean areConditionsAccomplished()
	{
		return unlocked;
	}
}
package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Combo;
import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Unit;

class AwesomeComboQuest extends Quest
{
	{
		permissions = new int[]{ 
			Combo.COUNT
		};
		
		id = "AwesomeCombo";
		name = "Do the AwesomeCombo";
		reward = 350;
	}
	
	boolean active = false;
	
	public AwesomeComboQuest()
	{
		
	}
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Combo.COUNT )
		{
			if ( unit.getInt( 0 ) >= 30 )
			{
				active = true;
			}
		}
	}
	
	@Override
	public boolean areConditionsAccomplished()
	{
		return active;
	}
}
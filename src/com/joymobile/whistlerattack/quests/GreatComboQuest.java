package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Combo;
import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Unit;

class GreatComboQuest extends Quest
{
	{
		permissions = new int[]{ 
			Combo.COUNT
		};
		
		id = "GreatCombo";
		name = "Do the Great Combo";
		reward = 110;
	}
	
	boolean active = false;
	
	public GreatComboQuest()
	{
		
	}
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Combo.COUNT )
		{
			if ( unit.getInt( 0 ) >= 20 )
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
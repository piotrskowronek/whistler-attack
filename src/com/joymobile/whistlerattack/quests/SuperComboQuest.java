package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Combo;
import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Unit;

class SuperComboQuest extends Quest
{
	{
		permissions = new int[]{ 
			Combo.COUNT
		};
		
		id = "SuperCombo";
		name = "Do the Super Combo";
		reward = 30;
	}
	
	boolean active = false;
	
	public SuperComboQuest()
	{
		
	}
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Combo.COUNT )
		{
			if ( unit.getInt( 0 ) >= 10 )
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
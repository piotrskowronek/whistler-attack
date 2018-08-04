package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Combo;
import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Unit;

class TenComboQuest extends Quest
{
	{
		permissions = new int[]{ 
			Combo.COUNT
		};
		
		id = "TenCombo";
		name = "Do exactly 10-items combo";
		reward = 200;
	}
	
	boolean active = false;
	
	public TenComboQuest()
	{
		
	}
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Combo.COUNT )
		{
			if ( unit.getInt( 0 ) == 10 )
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
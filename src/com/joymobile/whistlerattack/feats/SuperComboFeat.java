package com.joymobile.whistlerattack.feats;

import com.joymobile.whistlerattack.Combo;
import com.joymobile.whistlerattack.Feat;
import com.joymobile.whistlerattack.Unit;

public class SuperComboFeat extends Feat
{
	{
		permissions = new int[]{ 
			Combo.COUNT
		};
		
		id = "whistlerattack.combo.super";
		name = "Do the Super Combo";
	}
	
	boolean active = false;
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Combo.COUNT )
		{
			if ( unit.getInt( 0 ) >= 10 && unit.getInt( 0 ) < 20 )
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
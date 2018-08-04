package com.joymobile.whistlerattack.feats;

import com.joymobile.whistlerattack.Combo;
import com.joymobile.whistlerattack.Feat;
import com.joymobile.whistlerattack.Unit;

public class GreatComboFeat extends Feat
{
	{
		permissions = new int[]{ 
			Combo.COUNT
		};
		
		id = "whistlerattack.combo.great";
		name = "Do the Great Combo";
	}
	
	boolean active = false;
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Combo.COUNT )
		{
			if ( unit.getInt( 0 ) >= 20 && unit.getInt( 0 ) < 30 )
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
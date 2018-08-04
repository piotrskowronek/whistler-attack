package com.joymobile.whistlerattack.feats;

import com.joymobile.whistlerattack.Combo;
import com.joymobile.whistlerattack.Feat;
import com.joymobile.whistlerattack.Unit;

public class AwesomeComboFeat extends Feat
{
	{
		permissions = new int[]{ 
			Combo.COUNT
		};
		
		id = "whistlerattack.combo.awesome";
		name = "Do the Awesome combo";
	}
	
	boolean active = false;
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Combo.COUNT )
		{
			if ( unit.getInt( 0 ) >= 30 && unit.getInt( 0 ) < 50 )
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
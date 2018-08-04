package com.joymobile.whistlerattack.feats;

import com.joymobile.whistlerattack.Combo;
import com.joymobile.whistlerattack.Feat;
import com.joymobile.whistlerattack.Unit;

public class FifthComboFeat extends Feat
{
	{
		permissions = new int[]{ 
			Combo.COUNT
		};
		
		id = "whistlerattack.combo.fifth";
		name = "Do the Fifth Combo";
	}
	
	boolean active = false;
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Combo.COUNT )
		{
			if ( unit.getInt( 0 ) >= 90 )
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
package com.joymobile.whistlerattack.feats;

import com.joymobile.whistlerattack.Feat;
import com.joymobile.whistlerattack.Unit;
import com.joymobile.whistlerattack.UsualGameScreen;

public class PlaySmallFeat extends Feat
{
	{
		permissions = new int[]{ 
			UsualGameScreen.PLAY_COUNT
		};
		
		id = "whistlerattack.play.hundred";
		name = "Play 100 times";
	}
	
	int count = 0;
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == UsualGameScreen.PLAY_COUNT )
		{
			count = unit.getInt( 0 );
		}
	}
	
	@Override
	public boolean areConditionsAccomplished()
	{
		return count == 100;
	}
}
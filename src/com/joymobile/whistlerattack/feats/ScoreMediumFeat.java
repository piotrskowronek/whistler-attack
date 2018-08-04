package com.joymobile.whistlerattack.feats;

import com.joymobile.whistlerattack.Feat;
import com.joymobile.whistlerattack.Score;
import com.joymobile.whistlerattack.Unit;

public class ScoreMediumFeat extends Feat
{
	{
		permissions = new int[]{ 
			Score.COUNT
		};
		
		id = "whistlerattack.score.medium";
		name = "Score 3,000 in one game";
	}
	
	int count = 0;
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Score.COUNT )
		{
			count += unit.getInt( 0 );
		}
	}
	
	@Override
	public boolean areConditionsAccomplished()
	{
		return count > 3000;
	}
}
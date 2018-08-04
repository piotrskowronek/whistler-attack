package com.joymobile.whistlerattack.quests;

import com.joymobile.whistlerattack.Bomb;
import com.joymobile.whistlerattack.Database;
import com.joymobile.whistlerattack.Life;
import com.joymobile.whistlerattack.Quest;
import com.joymobile.whistlerattack.Unit;

class BombsInARowQuest extends Quest
{
	{
		permissions = new int[]{ 
			Bomb.TICK,
			Life.GAMEOVER
		};
		
		id = "BombsInARow";
		double_line = true;
		name = "Loose a game by bomb\n" +
			   "5 times in a row";
		reward = 500;
	}
	
	public BombsInARowQuest()
	{
		
	}
	
	@Override
	public void sendUnit( int code, Unit unit )
	{
		if ( code == Bomb.TICK )
		{
			Database.raiseInt( "bir1_quest" );
		}
		else if ( code == Life.GAMEOVER )
		{
			Database.setInt( "bir1_quest", 0 );
		}
	}
	
	@Override
	public boolean areConditionsAccomplished()
	{
		return Database.getInt( "bir1_quest" ) >= 5;
	}
}
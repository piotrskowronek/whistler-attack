package com.joymobile.whistlerattack;

public class Quest extends Task
{
	protected String name;
	protected boolean double_line = false;
	protected int reward = 20;
	
	public boolean isDoubleLine() 
	{
		return double_line;
	}

	public String getName()
	{
		return name;
	}
	
	public int getReward()
	{
		return reward;
	}
}
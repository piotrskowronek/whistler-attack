package com.joymobile.whistlerattack;

class Chain
{
	protected float opened_data;
	
	public float getOpenedData() 
	{
		return opened_data;
	}

	public void setOpenedData(float opened_data) 
	{
		this.opened_data = opened_data;
	}

	public Chain( float opened_duration )
	{
		this.opened_data = opened_duration;
	}
	
	public void onClosedState()
	{
		//To override dynamically. Don't remove!!!
	}
}
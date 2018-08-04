package com.joymobile.whistlerattack;

class Task implements Unit.Listener
{
	public String id;
	
	protected int[] permissions;
	
	public boolean hasPermission( int code )
	{
		for ( int permission : permissions )
		{
			if ( code == permission )
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void sendUnit( int code, Unit unit )
	{
		
	}
	
	public boolean areConditionsAccomplished()
	{
		return false;
	}
}
package com.joymobile.whistlerattack;

class Time
{
	private static float multipler = 1.0f;
	
	public static float get( float time )
	{
		return multipler * time;
	}
	
	public static void setMultipler( float m )
	{
		multipler = m;
	}
	
	public static long[] get( long[] times )
	{
		for ( long time : times )
		{
			time = (long)(multipler * time);
		}
		
		return times;
	}
}
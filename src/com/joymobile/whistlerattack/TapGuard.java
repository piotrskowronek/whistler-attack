package com.joymobile.whistlerattack;

import android.util.Log;

class TapGuard
{
	static int counter = 0;
	
	public static boolean press()
	{
		Log.d( "csd", counter+"" );
		if ( counter >= 2 )
		{
			return false;
		}
		counter++;
		return true;
	}
	
	public static void release()
	{
		counter--;
	}
}
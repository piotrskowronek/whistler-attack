package com.joymobile.whistlerattack;

import java.util.ArrayList;

public class Unit
{
	ArrayList<Object> objects = new ArrayList<Object>();
	
	public Unit( Object... objects )
	{
		for ( Object obj : objects )
		{
			this.objects.add( obj );
		}
	}
	
	public Integer getInt( int id )
	{
		return (Integer)objects.get( id );
	}
	
	interface Listener
	{
		public boolean hasPermission( int code );
	    public void sendUnit( int code, Unit unit );
	}
}
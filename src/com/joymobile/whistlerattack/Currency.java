package com.joymobile.whistlerattack;

import java.util.ArrayList;

public class Currency
{
	static Currency instance = null;
	
	ArrayList<Unit.Listener> unit_listeners= new ArrayList<Unit.Listener>();
    int counter = 0;
    
    final public static int COUNT = 0;
    
    public static Currency get()
    {
        if ( instance == null )
            instance = new Currency();
        return instance;
    }
    
    public void addUnitListener( Unit.Listener listener )
    {
        unit_listeners.add( listener );
    }
    
    public void reset()
    {
        counter = 0;
        unit_listeners = new ArrayList<Unit.Listener>();
    }
    
    public void addCurrency( int currency )
    {
    	counter += currency;
    }
    
    public int getCurrency()
    {
    	return counter;
    }
    
    public void submitScore()
    {
    	for ( Unit.Listener listener : unit_listeners )
		{
	    	if ( listener.hasPermission( COUNT ) )
			{
	    		listener.sendUnit( COUNT, new Unit( counter ) );
			}
		}
    }
}
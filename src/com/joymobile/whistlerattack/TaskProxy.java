package com.joymobile.whistlerattack;

import java.util.ArrayList;

abstract class TaskProxy implements Unit.Listener
{
	protected TaskProxy(){}
    
    public void sendUnit( int code, Unit unit )
    {
    	for ( Task task : getTasks() )
    	{
    		if ( task.hasPermission( code ) )
    		{
    			task.sendUnit( code, unit );
    		}
    	}
    }
    
    public boolean hasPermission( int code )
    {
    	return true;
    }
    
    abstract public void loadAndResetTasks();
    abstract public void refreshTasksStatus( SingleTaskCallback callback );
    abstract public ArrayList<? extends Task> getTasks();
}


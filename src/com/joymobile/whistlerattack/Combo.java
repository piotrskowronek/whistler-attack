package com.joymobile.whistlerattack;

import java.util.ArrayList;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;

import com.joymobile.whistlerattack.goods.FreezeDurationUpgradeableGoods;

public class Combo
{
	final public static int COUNT = 1;
	
	TimerHandler timer, freeze_timer;
	Scene scene;
	
	ArrayList<Combo.Listener> listeners = new ArrayList<Combo.Listener>();
	ArrayList<Unit.Listener> unit_listeners= new ArrayList<Unit.Listener>();
	
	boolean is_started = false;
	int counter = 0;
	boolean is_frozen = false;
	
	static boolean is_disabled = false;
	
	static Combo instance;
	
	public static Combo get()
	{
		if ( instance == null )
		{
			instance = new Combo();
		}
		return instance;
	}
	
	private Combo()
	{
		listeners = new ArrayList<Combo.Listener>();
		timer = null;
		scene = null;
	}
	
	public boolean isEnabled()
	{
		return ! is_disabled;
	}
	
	public void disable()
	{
		is_disabled = true;
	}
	
	public void enable()
	{
		is_disabled = false;
	}
	
	public void setScene( Scene s )
	{
		scene = s;
	}
	
	public void addListener( Combo.Listener listener )
	{
		listeners.add( listener );
	}
	
	public void addUnitListener( Unit.Listener listener )
    {
        unit_listeners.add( listener );
    }
	
	public void clear()
	{
		listeners = new ArrayList<Combo.Listener>();
		unit_listeners = new ArrayList<Unit.Listener>();
		timer = null;
		scene = null;
		counter = 0;
	}
	
	public void tick()
	{
		if ( is_frozen )
		{
			is_started = true;
			counter++;
		}
		else if ( is_started  && ! is_frozen )
		{
			scene.unregisterUpdateHandler( timer );
			registerTimerHandler();
			counter++;
		}
		else
		{
			is_started = true;
			counter = 1;
			
			scene.unregisterUpdateHandler( timer );
			registerTimerHandler();
		}

		for ( Combo.Listener listener : listeners )
		{
			listener.onComboTick( counter, is_frozen, timer );
		}
	}
	
	private void registerTimerHandler()
	{
		scene.registerUpdateHandler( timer = new TimerHandler( 0.5f, false, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				forceComboEnd();
			}
		}));
	}
	
	public void forceComboEnd()
	{
		for ( Combo.Listener listener : listeners )
		{
			is_started = false;
			
			if ( counter > 5 ) //Not to waste CPU
			{
				submitCombo();
			}
			
			listener.onComboFinish( counter );
			counter = 1;
		}
	}
	
	public void submitCombo()
    {
		for ( Unit.Listener listener : unit_listeners )
		{
			if ( listener.hasPermission( COUNT ) )
			{
				listener.sendUnit( COUNT, new Unit( counter ) );
			}
		}
    }
	
	public boolean freeze()
	{
		scene.unregisterUpdateHandler( timer );
		scene.unregisterUpdateHandler( freeze_timer );
		is_frozen = true;
		
		for ( Combo.Listener listener : listeners )
		{
			listener.onComboFreeze();
		}
		
		final FreezeDurationUpgradeableGoods goods = (FreezeDurationUpgradeableGoods)StoreGoodsProxy.get().getGoods( StoreGoodsProxy.UPGRADES, "FreezeDuration" );
		
		scene.registerUpdateHandler( freeze_timer = new TimerHandler( goods.getDuration(), false, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				is_frozen = false;
				for ( Combo.Listener listener : listeners )
				{
					listener.onComboUnfreeze();
				}
				registerTimerHandler();
			}
		} ) );
		
		return true;
	}
	
	public void unfreeze()
	{
		is_frozen = false;
	}
	
	interface Listener
	{
		public void onComboTick( int counter, boolean is_frozen, TimerHandler timer );
		public void onComboFreeze();
		public void onComboUnfreeze();
		public void onComboFinish( int counter );
	}
}
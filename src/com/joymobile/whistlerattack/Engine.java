package com.joymobile.whistlerattack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;

class Engine implements ITimerCallback
{
	protected ArrayList<ArrayList<Double>> listXs;
	protected ArrayList<Double> listYs;
	
	protected ArrayList<Item> items;
	protected Scene scene;
	
	public Engine()
	{
		this.listXs = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> line0 = new ArrayList<Double>();
		line0.add( 0.234 );
		line0.add( 0.429 );
		line0.add( 0.625 );
		ArrayList<Double> line1 = new ArrayList<Double>();
		line1.add( 0.332 );
		line1.add( 0.527 );
		ArrayList<Double> line2 = new ArrayList<Double>();
		line2.add( 0.234 );
		line2.add( 0.429 );
		line2.add( 0.625 );
		listXs.add( line0 );
		listXs.add( line1 );
		listXs.add( line2 );
		
		this.listYs = new ArrayList<Double>();
		listYs.add( 0.138 );
		listYs.add( 0.319 );
		listYs.add( 0.5 );
	}
	
	public ArrayList<ArrayList<Double>> getItemsXForLevel()
	{
		return listXs;
	}
	
	public ArrayList<Double> getItemsYForLevel()
	{
		return listYs;
	}
	
	public void setItems( ArrayList<Item> items )
	{
		this.items = items;
	}
	
	public void setScene( Scene scene )
	{
		this.scene = scene;
	}
	
	public TimerHandler getTimer()
	{
		return new TimerHandler( 0.5f, true, this );
	}

	public void onTimePassed(TimerHandler pTimerHandler)
	{
		Random rand = new Random();
		
		boolean isThereClosedHole = false;
		for ( Item item : items )
		{
			if ( item.getState() instanceof ClosedState )
				isThereClosedHole = true;
		}
		
		if ( isThereClosedHole == false )
			return;
		
		int id;
		do
		{
			id = rand.nextInt( items.size() );
		}
		while ( ! (items.get(id).getState() instanceof ClosedState) );
		
		int whichItem = rand.nextInt( 5 );
		if ( whichItem == 1 )
		{
			changeItem( id, Star.class );
		}
		else if ( whichItem == 2 )
		{
			changeItem( id, Bomb.class );
		}
		else
		{
			changeItem( id, Whistler.class );
		}

		items.get(id).changeState( new OpeningState() );
	}
	
	public void changeItem( Item prev, Class<? extends Item> next )
	{
		int i = 0;
		for ( Item item : items )
		{
			if ( item.hashCode() == prev.hashCode() )
			{
				changeItem( i, next );
			}
			i++;
		}
	}
	
	public void changeItem( int id, Class<? extends Item> next )
	{
		Item prev = items.get(id);
		State state = items.get(id).getState();
		Item nextItem;
		try
		{
			nextItem = next.getConstructor( int.class, int.class, Scene.class, Engine.class ).newInstance( (int)prev.getX(), (int)prev.getY(), scene, this );
			
			scene.getChildByIndex( getChildIndex( id ) ).attachChild( nextItem );
			prev.detachSelf();
			scene.unregisterTouchArea( prev );
			scene.registerTouchArea( nextItem );
			items.set( id, nextItem );
			nextItem.changeState( state );
		}
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected int getChildIndex( int id )
	{
		int i = 0;
		int j = 1;
		for ( ArrayList<Double> items : listXs )
		{
			for ( double item : items )
			{
				if ( i == id )
				{
					return j;
				}
				
				i++;
			}
			j++;
		}
		
		return -1;
	}
}
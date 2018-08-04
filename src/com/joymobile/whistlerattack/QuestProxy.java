package com.joymobile.whistlerattack;

import java.util.ArrayList;

class QuestProxy extends TaskProxy implements Unit.Listener
{
    static QuestProxy instance = null;
    
    ArrayList<Quest> quests = new ArrayList<Quest>();
    
    public static QuestProxy get()
    {
        if ( instance == null )
            instance = new QuestProxy();
        return instance;
    }
    
    private QuestProxy()
    {
    	super();
    }
    
    @Override
    public void loadAndResetTasks()
    {
    	quests.clear();

    	String[] ids = getAvailableQuestIDsFromDatabase();
    	for ( String id : ids )
    	{
    		if ( id != null )
    		{
    			try 
        		{
    				quests.add( (Quest)Class.forName( "com.joymobile.whistlerattack.quests." +id+ "Quest" ).getConstructor().newInstance() );
    			} 
        		catch ( Exception e )
        		{
    				e.printStackTrace();
    			} 
    		}
    	}
    }
    
    @Override
    public void refreshTasksStatus( SingleTaskCallback callback )
    {
    	ArrayList<Quest> tasks = (ArrayList<Quest>)getTasks().clone();
    	for ( Quest quest : tasks )
    	{
    		if ( quest.areConditionsAccomplished() )
    		{
    			(new StarCurrencyUnit( quest.getReward() )).award();
    			setUnlockedInDatabase( quest );
    			Quest new_quest = loadNewQuest();
    			callback.onIterate( quest, new_quest );
    		}
    	}
    }

	@Override
	public ArrayList<? extends Task> getTasks() 
	{
		return quests;
	}
	
	private String[] getAvailableQuestIDsFromDatabase()
	{
		ArrayList<String> unlocked_quests = Database.getStringArray( "unlocked_quests" );
		String[] all_quests = new String[]{
			"SuperCombo",
			"LowScore",
			"NoStar",
			"GreatCombo",
			"MediumScore",
			"SomeStars",
			"ThreeSuperCombos",
			"NoStarSecond",
			"TwentyStars",
			"TenCombo",
			"BombSilence",
			"BombsInARow",
			"FiftyStars",
			"AwesomeCombo",
			"AtLeastTwentyStars"
		};
		
		String[] to_return = new String[3];
		int i = 0;
		for ( String quest : all_quests )
		{
			if ( to_return[2] != null )
			{
				break;
			}
			
			if ( ! isUnlocked( quest, unlocked_quests ) )
			{
				to_return[i] = quest;
				i++;
			}
		}
		
		return to_return;
	}
	
	private boolean isUnlocked( String quest, ArrayList<String> unlocked_quests )
	{
		for ( String to_compare : unlocked_quests )
		{
			if ( quest.equals( to_compare ) )
			{
				return true;
			}
		}
		return false;
	}
	
	private void setUnlockedInDatabase( Quest quest )
	{
		Database.addStringToEndOfArray( "unlocked_quests", quest.id );
	}
	
	private Quest loadNewQuest()
	{
		loadAndResetTasks();
		if ( quests.size() < 3 )
			return null;
		else
			return quests.get( 2 );
	}
}


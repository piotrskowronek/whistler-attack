package com.joymobile.whistlerattack;

import com.scoreloop.client.android.core.model.Achievement;
import com.scoreloop.client.android.core.model.Continuation;
import com.scoreloop.client.android.ui.ScoreloopManagerSingleton;

class ScoreLoopFeats
{
	static ScoreLoopFeats instance = null;
	
	public static ScoreLoopFeats get()
    {
        if ( instance == null )
            instance = new ScoreLoopFeats();
        return instance;
    }
	
	public void requestAccomplish( final Feat quest )
	{
		ScoreloopManagerSingleton.get().loadAchievements( new Continuation<Boolean>() 
    	{
		    public void withValue(final Boolean value, final Exception error) 
		    {
		    	Achievement _achievement = ScoreloopManagerSingleton.get().getAchievement( quest.id );
                ScoreloopManagerSingleton.get().achieveAward(_achievement.getAward().getIdentifier(), true, true);
		    } 
    	});
	}
}
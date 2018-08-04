package com.joymobile.whistlerattack;

import java.util.ArrayList;

import com.joymobile.whistlerattack.feats.AwesomeComboFeat;
import com.joymobile.whistlerattack.feats.FifthComboFeat;
import com.joymobile.whistlerattack.feats.FourthComboFeat;
import com.joymobile.whistlerattack.feats.GreatComboFeat;
import com.joymobile.whistlerattack.feats.PlayLargeFeat;
import com.joymobile.whistlerattack.feats.PlayMediumFeat;
import com.joymobile.whistlerattack.feats.PlaySmallFeat;
import com.joymobile.whistlerattack.feats.ScoreLargeFeat;
import com.joymobile.whistlerattack.feats.ScoreMediumFeat;
import com.joymobile.whistlerattack.feats.ScoreSmallFeat;
import com.joymobile.whistlerattack.feats.SuperComboFeat;

class FeatProxy extends TaskProxy
{
    static FeatProxy instance = null;
    
    ArrayList<Feat> feats = new ArrayList<Feat>();
    
    public static FeatProxy get()
    {
        if ( instance == null )
            instance = new FeatProxy();
        return instance;
    }
    
    private FeatProxy()
    {
    	super();
    }
    
    @Override
    public void loadAndResetTasks()
    {
    	feats.clear();
    	feats.add( new ScoreSmallFeat() );
    	feats.add( new ScoreMediumFeat() );
    	feats.add( new ScoreLargeFeat() );
    	feats.add( new SuperComboFeat() );
    	feats.add( new GreatComboFeat() );
    	feats.add( new AwesomeComboFeat() );
    	feats.add( new FourthComboFeat() );
    	feats.add( new FifthComboFeat() );
    	feats.add( new PlaySmallFeat() );
    	feats.add( new PlayMediumFeat() );
    	feats.add( new PlayLargeFeat() );
    }
    
    @Override
    public void refreshTasksStatus( SingleTaskCallback callback )
    {
    	for ( Feat feat : (ArrayList<Feat>)getTasks() )
    	{
    		if ( feat.areConditionsAccomplished() && feat.isUnlocked() == false )
    		{
    			Database.setBoolean( feat.id, true );
    			if ( Database.getBoolean( "scoreloop" ) )
    				ScoreLoopFeats.get().requestAccomplish( feat );
    			callback.onIterate( feat, null );
    		}
    	}
    }
    
    @Override
    public ArrayList<? extends Task> getTasks()
    {
    	return feats;
    }
}


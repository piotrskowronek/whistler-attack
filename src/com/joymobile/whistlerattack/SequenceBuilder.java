package com.joymobile.whistlerattack;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andengine.entity.scene.Scene;

import android.util.Log;

import com.joymobile.whistlerattack.goods.BombFreqUpgradeableGoods;
import com.joymobile.whistlerattack.goods.FreezeFreqUpgradeableGoods;
import com.joymobile.whistlerattack.goods.LifeFreqUpgradeableGoods;

class SequenceBuilder
{
	public static Sequence makeSequence( String pattern, float delay_multipler, float opened_duration, Scene scene, GameScreen screen, MainActivity main )
	{
		Log.d( "pattern", pattern );
		DelayedSequence seq = new DelayedSequence( scene, screen, main );
		
		Pattern patt = Pattern.compile( "^(-+)([1-8bs,]*)([1-8-bs,]*)$" );
		Matcher m = patt.matcher( pattern );
		
		
		if ( m.matches() )
		{
			String delay = m.group(1);
		  	String leaves = m.group(2);
		  	String tail = m.group(3);
		  	
		  	seq.setDelay( delay_multipler * delay.length() );
		  	
		  	if ( leaves.length() > 0 )
		  	{
		  		String[] leaves_tab = leaves.split( "," );
				for ( int i = 0; i < leaves_tab.length; i++ )
				{
					LeafSequence leaf = new LeafSequence( delay_multipler, opened_duration, scene, screen, main );
					if ( leaves_tab[i].length() > 1 && leaves_tab[i].charAt(1) == 'b' )
					{
						leaf.setItem( Bomb.class );
					}
					else if ( leaves_tab[i].length() > 1 && leaves_tab[i].charAt(1) == 's' )
					{
						leaf.setItem( Star.class );
					}
					else
					{
						Class<? extends Item> item_class = Whistler.class;
						
						if ( hasDrownFreezeBonus() )
							item_class = FreezeBonus.class;
						else if ( hasDrownLifeBonus() )
							item_class = LifeBonus.class;
						else if ( hasDrownBombBonus() )
							item_class = BombBonus.class;

						leaf.setItem( item_class );
					}
					
					leaf.setHoleNumber( Integer.parseInt( ""+leaves_tab[i].charAt( 0 ) ) - 1 );
					
					seq.addSequence( leaf );
				}
		  	}
			
			if ( tail.length() > 0 )
				seq.addSequence( makeSequence( tail, delay_multipler, opened_duration, scene, screen, main ) );
		}
		
		return seq;
	}

	private static boolean hasDrownFreezeBonus()
	{
		Random rand = new Random();
		float percent = rand.nextFloat();
		
		FreezeFreqUpgradeableGoods freq = (FreezeFreqUpgradeableGoods)StoreGoodsProxy.get().getGoods( StoreGoodsProxy.UPGRADES, "FreezeFreq" );
		double freq_value = freq.getFrequency();
		
		if ( percent < freq_value )
			return true;
		
		return false;
	}
	
	private static boolean hasDrownLifeBonus()
	{
		Random rand = new Random();
		float percent = rand.nextFloat();
		
		LifeFreqUpgradeableGoods freq = (LifeFreqUpgradeableGoods)StoreGoodsProxy.get().getGoods( StoreGoodsProxy.UPGRADES, "LifeFreq" );
		double freq_value = freq.getFrequency();
		
		if ( percent < freq_value )
			return true;
		
		return false;
	}
	
	private static boolean hasDrownBombBonus()
	{
		Random rand = new Random();
		float percent = rand.nextFloat();
		
		BombFreqUpgradeableGoods freq = (BombFreqUpgradeableGoods)StoreGoodsProxy.get().getGoods( StoreGoodsProxy.UPGRADES, "BombFreq" );
		double freq_value = freq.getFrequency();
		
		if ( percent < freq_value )
			return true;
		
		return false;
	}
}
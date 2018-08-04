package com.joymobile.whistlerattack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.joymobile.whistlerattack.goods.AdColonyGoods;
import com.joymobile.whistlerattack.goods.BombDurationUpgradeableGoods;
import com.joymobile.whistlerattack.goods.BombFreqUpgradeableGoods;
import com.joymobile.whistlerattack.goods.BullGroupGoods;
import com.joymobile.whistlerattack.goods.CarnivalGroupGoods;
import com.joymobile.whistlerattack.goods.ChickenGroupGoods;
import com.joymobile.whistlerattack.goods.DizzyGroupGoods;
import com.joymobile.whistlerattack.goods.ElegantGroupGoods;
import com.joymobile.whistlerattack.goods.FarmGroupGoods;
import com.joymobile.whistlerattack.goods.ForestGroupGoods;
import com.joymobile.whistlerattack.goods.FreeGoods;
import com.joymobile.whistlerattack.goods.FreezeDurationUpgradeableGoods;
import com.joymobile.whistlerattack.goods.FreezeFreqUpgradeableGoods;
import com.joymobile.whistlerattack.goods.HeadstartUnlimitedGoods;
import com.joymobile.whistlerattack.goods.HipsterGroupGoods;
import com.joymobile.whistlerattack.goods.KoalaGroupGoods;
import com.joymobile.whistlerattack.goods.LifeFreqUpgradeableGoods;
import com.joymobile.whistlerattack.goods.MouseGroupGoods;
import com.joymobile.whistlerattack.goods.NoneExtraGroupGoods;
import com.joymobile.whistlerattack.goods.PackFifthGoods;
import com.joymobile.whistlerattack.goods.PackFirstGoods;
import com.joymobile.whistlerattack.goods.PackFourthGoods;
import com.joymobile.whistlerattack.goods.PackSecondGoods;
import com.joymobile.whistlerattack.goods.PackThirdGoods;
import com.joymobile.whistlerattack.goods.PirateGroupGoods;
import com.joymobile.whistlerattack.goods.TapjoyGoods;
import com.joymobile.whistlerattack.goods.TigerGroupGoods;
import com.joymobile.whistlerattack.goods.WhistlerGroupGoods;

public class StoreGoodsProxy
{
	static StoreGoodsProxy instance = null;
	public static final int UPGRADES = 745513222;
	public static final int EXTRAS = 79549326;
	public static final int STAR_PACKS = 155548785;
	public static final int CHARACTERS = 150084775;
	public static final int SCENES = 444487913;
	public static final int EARN = 121246874;
	
	LinkedHashMap<Integer, LinkedHashMap<String, Goods>> goods = new LinkedHashMap<Integer, LinkedHashMap<String, Goods>>();
	
	public static StoreGoodsProxy get()
    {
        if ( instance == null )
            instance = new StoreGoodsProxy();
        return instance;
    }
	
	private StoreGoodsProxy()
	{
		goods = new LinkedHashMap<Integer, LinkedHashMap<String, Goods>>();
	}
	
	public void loadGoodsList( MainActivity main )
	{
		goods.clear();
		
		goods.put( UPGRADES, new LinkedHashMap<String, Goods>() );
		goods.put( EXTRAS, new LinkedHashMap<String, Goods>() );
		goods.put( CHARACTERS, new LinkedHashMap<String, Goods>() );
		goods.put( SCENES, new LinkedHashMap<String, Goods>() );
		goods.put( STAR_PACKS, new LinkedHashMap<String, Goods>() );
		goods.put( EARN, new LinkedHashMap<String, Goods>() );
		
		LinkedHashMap<String, Goods> upgrades = goods.get( UPGRADES );
		upgrades.put( "MegaHeadstart", new HeadstartUnlimitedGoods( main ) );
		upgrades.put( "FreezeFreq", new FreezeFreqUpgradeableGoods( main ) );
		upgrades.put( "LifeFreq", new LifeFreqUpgradeableGoods( main ) );
		upgrades.put( "BombFreq", new BombFreqUpgradeableGoods( main ) );
		upgrades.put( "FreezeDuration", new FreezeDurationUpgradeableGoods( main ) );
		upgrades.put( "BombDuration", new BombDurationUpgradeableGoods( main ) );
		
		LinkedHashMap<String, Goods> extras = goods.get( EXTRAS );
		extras.put( "NoneExtra", new NoneExtraGroupGoods( main ) );
		extras.put( "Elegant", new ElegantGroupGoods( main ) );
		extras.put( "Pirate", new PirateGroupGoods( main ) );
		extras.put( "Hipster", new HipsterGroupGoods( main ) );
		extras.put( "Dizzy", new DizzyGroupGoods( main ) );
		
		LinkedHashMap<String, Goods> characters = goods.get( CHARACTERS );
		characters.put( "Whistler", new WhistlerGroupGoods( main ) );
		characters.put( "Bull", new BullGroupGoods( main ) );
		characters.put( "Tiger", new TigerGroupGoods( main ) );
		characters.put( "Mouse", new MouseGroupGoods( main ) );
		characters.put( "Chicken", new ChickenGroupGoods( main ) );
		characters.put( "Koala", new KoalaGroupGoods( main ) );
		
		LinkedHashMap<String, Goods> scenes = goods.get( SCENES );
		scenes.put( "Carnival", new CarnivalGroupGoods( main ) );
		scenes.put( "Forest", new ForestGroupGoods( main ) );
		scenes.put( "Farm", new FarmGroupGoods( main ) );
		
		LinkedHashMap<String, Goods> starpacks = goods.get( STAR_PACKS );
		//starpacks.put( "FreeFirst", new FreeGoods( main ) );
		starpacks.put( "PackFifth", new PackFifthGoods( main ) );
		starpacks.put( "PackFourth", new PackFourthGoods( main ) );
		starpacks.put( "PackThird", new PackThirdGoods( main ) );
		starpacks.put( "PackSecond", new PackSecondGoods( main ) );
		starpacks.put( "PackFirst", new PackFirstGoods( main ) );
		
		LinkedHashMap<String, Goods> earn = goods.get( EARN );
		earn.put( "AdColony", new AdColonyGoods( main ) );
		earn.put( "Tapjoy", new TapjoyGoods( main ) );
	}
	
	public LinkedHashMap<String, Goods> getGoodsList( int id )
	{
		return goods.get( id );
	}
	
	public Goods getGoods( int id, String key )
	{
		return goods.get( id ).get( key );
	}

	public ArrayList<Integer> getCategoriesId()
	{
		ArrayList<Integer> categories = new ArrayList<Integer>();
		for ( Map.Entry<Integer, LinkedHashMap<String, Goods>> entry : goods.entrySet() )
		{
			categories.add( entry.getKey() );
		}
		
		return categories;
	}

	public ArrayList<Goods> getGoodsNeedingLoad()
	{
		ArrayList<Goods> groups = new ArrayList<Goods>();
		
		for ( Map.Entry<Integer, LinkedHashMap<String, Goods>> entry : goods.entrySet() )
		{
			for ( Map.Entry<String, Goods> subentry : entry.getValue().entrySet() )
			{
				Goods needing = subentry.getValue();
				
				if ( needing.needsLoadOnStart() )
					groups.add( needing );
			}
		}
		
		return groups;
	}

	public GroupGoods getGroupGoodsInUseFor( String group_key )
	{
		for ( Map.Entry<Integer, LinkedHashMap<String, Goods>> entry : goods.entrySet() )
		{
			for ( Map.Entry<String, Goods> subentry : entry.getValue().entrySet() )
			{
				if ( ! ( subentry.getValue() instanceof GroupGoods ) )
					continue;
				
				GroupGoods goods = (GroupGoods)subentry.getValue();
				
				String group_k = goods.getDatabaseGroupKey();
				if ( group_k.equals( group_key ) &&
						goods.isInUse() )
				{
					return goods;
				}
			}
		}
		
		return null;
	}

	public void resetGroupsToDefault( )
	{
		for ( Map.Entry<Integer, LinkedHashMap<String, Goods>> entry : goods.entrySet() )
		{
			for ( Map.Entry<String, Goods> subentry : entry.getValue().entrySet() )
			{
				if ( ! ( subentry.getValue() instanceof GroupGoods ) )
					continue;
				
				GroupGoods goods = (GroupGoods)subentry.getValue();
				
				if ( goods instanceof Defaultable )
				{
					Defaultable def = (Defaultable)goods;
					
					if ( def.isDefault() )
						def.doDefaultAction();
				}
			}
		}
	}
}
package com.joymobile.whistlerattack.goods;

import org.andengine.opengl.font.Font;

import com.joymobile.whistlerattack.CustomGoods;
import com.joymobile.whistlerattack.CustomGoodsEntity;
import com.joymobile.whistlerattack.Goods;
import com.joymobile.whistlerattack.GoodsEntity;
import com.joymobile.whistlerattack.HavingEntities;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.StarCurrencyUnit;

public class PackFirstGoods extends PackGoods
{
	public PackFirstGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "Small Star Pack";
		currency_unit = new StarCurrencyUnit( 5000 );
		thumbnail = "first_treasure";
		billing_id = "com.joymobile.whistlerattack.first_star_pack";
		hash_key = "8b04d5e3775d298e78455efc5ca404d5";
		desc = "Looking forward next\n" +
				"item? This is the\n" +
				"quickest option.";
	}

}
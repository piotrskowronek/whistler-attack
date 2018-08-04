package com.joymobile.whistlerattack.goods;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;

import com.joymobile.whistlerattack.Database;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.PricelessCurrencyUnit;
import com.joymobile.whistlerattack.SVGLayeredTextureAtlasSourceFactory;
import com.joymobile.whistlerattack.StoreGoodsProxy;

public class NoneExtraGroupGoods extends ExtrasGroupGoods
{
	public NoneExtraGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		title = "None";
		currency_unit = new PricelessCurrencyUnit();
		thumbnail = "whistler_thumb";
		database_key = "none";
		desc = "Don't use any extra.\n" +
				"Simple is beautiful!";
	}

	@Override
	protected void changeExtras()
	{
		String path = StoreGoodsProxy.get().getGroupGoodsInUseFor( "characters" ).getDatabaseKey();
		
		final int size = main.w( 0.6 );
		main.whistlerAtlas.clearTextureAtlasSources();
		IBitmapTextureAtlasSource decoratedTextureAtlasSource = SVGLayeredTextureAtlasSourceFactory.makeAtlasSource( main, "gfx/" +path+ ".svg", new String[]{}, size, (int)(size * 1.5) );
		main.whistlerTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource( main.whistlerAtlas, decoratedTextureAtlasSource, 0, 0, 4, 6 );
	
	}
	
	@Override
	public boolean isDefault()
	{
		return true;
	}
	
	@Override
	public void doDefaultAction()
	{
		Database.setString( "extras", "none" );
		Database.setBoolean( "none", true );
	}
}
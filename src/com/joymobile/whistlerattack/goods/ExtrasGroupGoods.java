package com.joymobile.whistlerattack.goods;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;

import com.joymobile.whistlerattack.Defaultable;
import com.joymobile.whistlerattack.GroupGoods;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.SVGLayeredTextureAtlasSourceFactory;
import com.joymobile.whistlerattack.StoreGoodsProxy;

abstract public class ExtrasGroupGoods extends GroupGoods implements Defaultable
{
	public ExtrasGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		database_group_key = "extras";
	}
	
	@Override
	public void use()
	{
		super.use();
		
		main.runOnUiThread( new Runnable() 
		{
			public void run()
			{
				showLoadingScreen();
				changeExtras();
				hideLoadingScreen();
			}
		});
	}

	protected void changeExtras()
	{
		String path = StoreGoodsProxy.get().getGroupGoodsInUseFor( "characters" ).getDatabaseKey();
		
		final int size = main.w( 0.6 );
		main.whistlerAtlas.clearTextureAtlasSources();
		IBitmapTextureAtlasSource decoratedTextureAtlasSource = SVGLayeredTextureAtlasSourceFactory.makeAtlasSource( main, "gfx/" +path+ ".svg", new String[]{"gfx/"+getDatabaseKey()+".svg"}, size, (int)(size * 1.5) );
		main.whistlerTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource( main.whistlerAtlas, decoratedTextureAtlasSource, 0, 0, 4, 6 );
	
	}

	@Override
	public void onLoad()
	{
		changeExtras();
	}

	@Override
	public boolean isDefault()
	{
		return false;
	}

	@Override
	public void doDefaultAction()
	{
		
	}
}
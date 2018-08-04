package com.joymobile.whistlerattack.goods;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;

import android.util.Log;

import com.joymobile.whistlerattack.Defaultable;
import com.joymobile.whistlerattack.GroupGoods;
import com.joymobile.whistlerattack.MainActivity;
import com.joymobile.whistlerattack.SVGLayeredTextureAtlasSourceFactory;
import com.joymobile.whistlerattack.StoreGoodsProxy;

abstract public class CharactersGroupGoods extends GroupGoods implements Defaultable
{
	public CharactersGroupGoods( MainActivity main )
	{
		super( main );
	}
	
	{
		database_group_key = "characters";
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
				changeCharacter();
				hideLoadingScreen();
			}
		});
	}

	private void changeCharacter()
	{
		String path = StoreGoodsProxy.get().getGroupGoodsInUseFor( "extras" ).getDatabaseKey();
		Log.d( "EXTRAS", path );
		String[] extras;
		if ( ! path.equals( "none" ) )
			extras = new String[]{ "gfx/"+path+".svg" };
		else
			extras = new String[]{};
		
		final int size = main.w( 0.6 );
		main.whistlerAtlas.clearTextureAtlasSources();
		IBitmapTextureAtlasSource decoratedTextureAtlasSource = SVGLayeredTextureAtlasSourceFactory.makeAtlasSource( main, "gfx/" +getDatabaseKey()+ ".svg", extras, size, (int)(size * 1.5) );
		main.whistlerTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource( main.whistlerAtlas, decoratedTextureAtlasSource, 0, 0, 4, 6 );
	}

	@Override
	public void onLoad()
	{
		changeCharacter();
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
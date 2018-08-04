package com.joymobile.whistlerattack.goods;

import com.joymobile.whistlerattack.Defaultable;
import com.joymobile.whistlerattack.GroupGoods;
import com.joymobile.whistlerattack.MainActivity;

abstract public class ScenesGroupGoods extends GroupGoods implements Defaultable
{
	public ScenesGroupGoods( MainActivity main )
	{
		super( main );
	}

	{
		database_group_key = "scenes";
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
				changeScene();
				hideLoadingScreen();
			}
		});
	}

	protected void changeScene()
	{
		main.atlases.put( "background", main.makeAtlas( main.w(1), main.h(1) ) );
		main.textures.put( "background", main.makeTexture( main.atlases.get( "background" ), "gfx/" +getDatabaseKey()+ ".svg", main.w(1), main.h(1) ) );
	}

	@Override
	public void onLoad()
	{
		changeScene();
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
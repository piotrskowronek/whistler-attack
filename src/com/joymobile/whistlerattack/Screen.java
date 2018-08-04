package com.joymobile.whistlerattack;

import java.util.HashMap;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.HorizontalAlign;

abstract class Screen
{
	HashMap<String, BitmapTextureAtlas> atlases = new HashMap<String, BitmapTextureAtlas>();
	HashMap<String, ITiledTextureRegion> tiled_textures = new HashMap<String, ITiledTextureRegion>();
	HashMap<String, ITextureRegion> textures = new HashMap<String, ITextureRegion>();
	
	MainActivity main;
	Text textComponent;
	Scene scene;
	
	public void setAtlases( HashMap<String, BitmapTextureAtlas> atlases )
	{
		this.atlases = atlases;
	}
	
	public void setTiledTextures( HashMap<String, ITiledTextureRegion> tiled_textures )
	{
		this.tiled_textures = tiled_textures;
	}
	
	public void setTextures( HashMap<String, ITextureRegion> textures )
	{
		this.textures = textures;
	}
	
	public void setActivity( MainActivity activity )
	{
		main = activity;
	}
	
	public Scene onCreateScreen()
	{
		scene = new Scene();
		return scene;
	}
	
	public void onStartScreen( String[] args )
	{
    	
	}
	
	public void onPause()
	{
		
	}
	
	public void onResume()
	{

	}
	
	public void onExit()
	{
		scene.clearChildScene();
		
		String text = "Would you like to exit game?";
		textComponent = new Text( main.w(0), main.h(0.2f), main.mFont, text, text.length(), main.getVertexBufferObjectManager() );
		textComponent.setHorizontalAlign( HorizontalAlign.CENTER );
		textComponent.setX( ( main.w(1) - textComponent.getWidth() ) / 2 );
		textComponent.setY( ( main.h(1) - textComponent.getHeight() ) / 2 );
    	main.exit_scene.attachChild( textComponent );
		scene.setChildScene( main.exit_scene, false, true, true );
	}
	
	public void onExitReturn()
	{
		scene.clearChildScene();
		main.runOnUpdateThread( new Runnable()
		{
			public void run() 
			{
				textComponent.detachSelf();
			}
		});
	}
}
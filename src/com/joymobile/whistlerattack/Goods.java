package com.joymobile.whistlerattack;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.util.HorizontalAlign;

public abstract class Goods
{
	protected String title = "Unavailable";
	protected String desc = "";
	protected String thumbnail = "fourth_treasure";
	protected String database_key = "upgradeablegoods_default";
	protected MainActivity main;
	
	Scene child;
	private Rectangle rect2;
	private Text textComponent;

	public Goods( MainActivity main )
	{
		this.main = main;
	}
	
	public String getDatabaseKey()
	{
		return database_key;
	}

	public void setDatabaseKey( String _database_key )
	{
		database_key = _database_key;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc( String desc )
	{
		this.desc = desc;
	}

	public String getThumbnail()
	{
		return thumbnail;
	}

	public void setThumbnail( String thumbnail )
	{
		this.thumbnail = thumbnail;
	}
	
	protected void showLoadingScreen()
	{
		child = new Scene();
		
		rect2 = new Rectangle( 0, 0, main.w(1), main.h(1), main.getVertexBufferObjectManager() );
		rect2.setColor(0, 0, 0);
		rect2.setAlpha( 0.85f );
		child.attachChild( rect2 );
		
		String text =   "Wait while changes are applying...";
		textComponent = new Text( main.w(0), main.h(0.2f), main.mFont, text, text.length(), main.getVertexBufferObjectManager() );
		textComponent.setHorizontalAlign( HorizontalAlign.CENTER );
		textComponent.setX( ( main.w(1) - textComponent.getWidth() ) / 2 );
		textComponent.setY( ( main.h(1) - textComponent.getHeight() ) / 2 );
    	child.attachChild( textComponent );
    	
    	child.setBackgroundEnabled( false );
		main.scene.clearChildScene();
		main.scene.setChildScene( child );
	}
	
	protected void hideLoadingScreen()
	{
		rect2.detachSelf();
		textComponent.detachSelf();
	}
	
	abstract public boolean needsLoadOnStart();
	abstract public GoodsEntity makeEntity( int h, int h2, Font mFont5, Font mFont4, MainActivity main, HavingEntities screen );
	abstract public CurrencyUnit getPriceInCurrencyUnit();
	abstract public void onLoad();
}
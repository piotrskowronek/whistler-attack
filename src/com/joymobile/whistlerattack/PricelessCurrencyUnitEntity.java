package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;

public class PricelessCurrencyUnitEntity extends CurrencyUnitEntity
{
	float xoffset;
	Text price;
	
	public PricelessCurrencyUnitEntity()
	{

	}

	@Override
	public void createEntityCentered( float pX, float pY, float centrization_width, Font bigger_font, MainActivity main )
	{
		String pricetxt = "";
		price = new Text( pX, pY, bigger_font, pricetxt, pricetxt.length(), main.getVertexBufferObjectManager() );
		
		xoffset = ( centrization_width - price.getWidth() ) / 2;
    	price.setX( price.getX() + xoffset );
	}

	@Override
	public void attachAsChild( Scene scene )
	{
		scene.getChildByIndex(1).attachChild( price );
	}

	@Override
	public void detachSelf()
	{
		price.detachSelf();
	}

	@Override
	public void setX( float x )
	{
		price.setX( x+xoffset );
	}
}
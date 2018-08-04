package com.joymobile.whistlerattack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;

public class StarCurrencyUnitEntity extends CurrencyUnitEntity
{
	int value;
	float xoffset, price_offset;
	Sprite star;
	Text price;
	
	public StarCurrencyUnitEntity( int value )
	{
		this.value = value;
	}

	@Override
	public void createEntityCentered( float pX, float pY, float centrization_width, Font bigger_font, MainActivity main )
	{
		star = new Sprite( pX, pY-main.h(0.005f), main.textures.get( "star" ), main.getVertexBufferObjectManager() );
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator( ',' );
		DecimalFormat formatter = new DecimalFormat( "#,###", symbols );
		String pricetxt = formatter.format( value );
		price_offset = main.h(0.09f);
		price = new Text( pX+price_offset, pY, bigger_font, pricetxt, pricetxt.length(), main.getVertexBufferObjectManager() );
		
		xoffset = ( centrization_width - star.getWidth() - price.getWidth() ) / 2;
		//price_offset += xoffset;
    	star.setX( star.getX() + xoffset );
    	price.setX( price.getX() + xoffset );
	}

	@Override
	public void attachAsChild( Scene scene )
	{
		scene.getChildByIndex(1).attachChild( price );
		scene.getChildByIndex(1).attachChild( star );
	}

	@Override
	public void detachSelf()
	{
		price.detachSelf();
		star.detachSelf();
	}

	@Override
	public void setX( float x )
	{
		price.setX( x+price_offset+xoffset );
		star.setX( x+xoffset );
	}
}
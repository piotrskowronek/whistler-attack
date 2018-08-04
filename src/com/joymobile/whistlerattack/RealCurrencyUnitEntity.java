package com.joymobile.whistlerattack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;

public class RealCurrencyUnitEntity extends CurrencyUnitEntity
{
	float value;
	Text price;
	float xoffset;
	
	public RealCurrencyUnitEntity( float value )
	{
		this.value = value;
	}

	@Override
	public void createEntityCentered( float pX, float pY, float centrization_width, Font bigger_font, MainActivity main )
	{
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator( ',' );
		symbols.setDecimalSeparator( '.' );
		DecimalFormat formatter = new DecimalFormat( "#,###.00", symbols );
		String pricetxt = "$$$";//"$"+formatter.format( value );
		
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
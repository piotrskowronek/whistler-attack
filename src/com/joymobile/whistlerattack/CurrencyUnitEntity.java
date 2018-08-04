package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;

abstract class CurrencyUnitEntity
{
	abstract public void createEntityCentered( float pX, float pY, float centrization_width, Font bigger_font, MainActivity main );
	abstract public void attachAsChild( Scene scene );
	abstract public void detachSelf();
	abstract public void setX( float x );
}

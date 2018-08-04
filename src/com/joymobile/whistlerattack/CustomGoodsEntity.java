package com.joymobile.whistlerattack;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;

public abstract class CustomGoodsEntity extends GoodsEntity
{
	protected String label = "PERFORM";

	public CustomGoodsEntity( Goods goods, float pX, float pY, Font font, Font bigger_font, MainActivity main, HavingEntities screen )
	{
		super( goods, pX, pY, font, bigger_font, main, screen );
	}
	
	@Override
	protected void createActionButton( final float pX, final float pY, Font bigger_font )
	{
		super.createActionButton( pX, pY, bigger_font );
		
		actionbttn_offset = main.h(0.07f);
		actionbttn = new Sprite( pX+actionbttn_offset, pY+main.h(0.82f), main.textures.get( "buy_button" ), main.getVertexBufferObjectManager() )
		{
			@Override
			public boolean onAreaTouched( TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY )
			{
				if ( pSceneTouchEvent.isActionDown() )
				{
					if ( can_action )
						onAction();
					
					return true;
				}
				
				return true;
			}
		};
		buttontxt = new Text( pX+actionbttn_offset, pY+main.h(0.815f), bigger_font, label, "XXXXXXXXX".length(), main.getVertexBufferObjectManager() );
		
		float xoffset = (actionbttn.getWidth() - buttontxt.getWidth() ) / 2;
    	buttontxt.setX( buttontxt.getX() + xoffset );
    	float yoffset = (actionbttn.getHeight() - buttontxt.getHeight() ) / 2;
    	buttontxt.setY( buttontxt.getY() + yoffset );
    	
    	button_text_offset = buttontxt.getX() - pX;
	}
	
	public void onAction()
	{
		CustomGoods cu_goods = (CustomGoods)getGoods();
		boolean success = cu_goods.action();
		
		if ( success )
		{
			main.mTap.play();
			screen.replaceEntity( this );
			if ( screen instanceof ShowingCurrency )
				((ShowingCurrency)screen).refreshCurrency();
		}
		else
		{
			main.mMissed.play();
		}
	}	
}
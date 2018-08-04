package com.joymobile.whistlerattack;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;

class SingletonGoodsEntity extends GoodsEntity
{
	public SingletonGoodsEntity( Goods goods, float pX, float pY, Font font, Font bigger_font, MainActivity main, HavingEntities screen )
	{
		super( goods, pX, pY, font, bigger_font, main, screen );
	}
	
	@Override
	protected void createActionButton( final float pX, final float pY, Font bigger_font )
	{
		super.createActionButton( pX, pY, bigger_font );
		
		String button = ((SingletonGoods)getGoods()).isBought() ? "gray_button" : "buy_button";
		actionbttn_offset = main.h(0.07f);
		actionbttn = new Sprite( pX+actionbttn_offset, pY+main.h(0.82f), main.textures.get( button ), main.getVertexBufferObjectManager() )
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
		String label = ((SingletonGoods)getGoods()).isBought() ? "IN USE" : "BUY";
		buttontxt = new Text( pX+actionbttn_offset, pY+main.h(0.815f), bigger_font, label, "XXXXXXXXX".length(), main.getVertexBufferObjectManager() );
		
		float xoffset = (actionbttn.getWidth() - buttontxt.getWidth() ) / 2;
    	buttontxt.setX( buttontxt.getX() + xoffset );
    	float yoffset = (actionbttn.getHeight() - buttontxt.getHeight() ) / 2;
    	buttontxt.setY( buttontxt.getY() + yoffset );
    	
    	button_text_offset = buttontxt.getX() - pX;
	}
	
	public void onAction()
	{
		SingletonGoods si_goods = (SingletonGoods)getGoods();
		boolean success = si_goods.buy();
		
		if ( success )
		{
			main.mCoins.play();
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
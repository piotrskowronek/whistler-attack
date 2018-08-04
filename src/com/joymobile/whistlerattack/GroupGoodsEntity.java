package com.joymobile.whistlerattack;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;

class GroupGoodsEntity extends GoodsEntity
{
	public GroupGoodsEntity( Goods goods, float pX, float pY, Font font, Font bigger_font, MainActivity main, HavingEntities screen )
	{
		super( goods, pX, pY, font, bigger_font, main, screen );
	}
	
	@Override
	protected void createActionButton( final float pX, final float pY, Font bigger_font )
	{
		super.createActionButton( pX, pY, bigger_font );
		
		String label = "???";
		String button = "buy_button";
		GroupGoods gr_goods = (GroupGoods)getGoods();
		if ( gr_goods.isInUse() )
		{
			label = "IN USE";
			button = "gray_button";
		}
		else if ( gr_goods.isBought() == false )
		{
			label = "BUY";
			button = "buy_button";
		}	
		else if ( gr_goods.isBought() && gr_goods.isInUse() == false )
		{
			label = "USE";
			button = "green_button";
		}
		
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

		buttontxt = new Text( pX+actionbttn_offset, pY+main.h(0.815f), bigger_font, label, "XXXXXXXXX".length(), main.getVertexBufferObjectManager() );
		
		float xoffset = (actionbttn.getWidth() - buttontxt.getWidth() ) / 2;
    	buttontxt.setX( buttontxt.getX() + xoffset );
    	float yoffset = (actionbttn.getHeight() - buttontxt.getHeight() ) / 2;
    	buttontxt.setY( buttontxt.getY() + yoffset );
    	
    	button_text_offset = buttontxt.getX() - pX;
	}
	
	public void onAction()
	{
		GroupGoods gr_goods = (GroupGoods)getGoods();
		
		boolean success;
		if ( gr_goods.isBought() && gr_goods.isInUse() )
		{
			main.mMissed.play();
			success = false;
		}
		else if ( gr_goods.isBought() && gr_goods.isInUse() == false )
		{
			gr_goods.use();
			main.mTap.play();
			success = true;
		}
		else
		{
			
			success = gr_goods.buy();
			
			if ( success )
				main.mCoins.play();
			else
				main.mMissed.play();
		}
		
		if ( success )
		{
			screen.replaceEntities();
			if ( screen instanceof ShowingCurrency )
				((ShowingCurrency)screen).refreshCurrency();
		}
	}	
}
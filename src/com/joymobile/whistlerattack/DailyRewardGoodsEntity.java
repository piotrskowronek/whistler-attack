package com.joymobile.whistlerattack;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;

class DailyRewardGoodsEntity extends GoodsEntity
{
	public DailyRewardGoodsEntity( Goods goods, float pX, float pY, Font font, Font bigger_font, MainActivity main, HavingEntities screen )
	{
		super( goods, pX, pY, font, bigger_font, main, screen );
	}
	
	@Override
	protected void createDescription( float pX, float pY, Font font )
	{
		super.createDescription( pX, pY, font );
		desc.setText( "" );
	}

	@Override
	protected void createRectangle( float pX, float pY )
	{
		rect = new Sprite( pX, pY, main.textures.get( "reward_goods" ), main.getVertexBufferObjectManager() );
	}

	@Override
	protected void createActionButton( final float pX, final float pY, Font bigger_font )
	{
		super.createActionButton( pX, pY, bigger_font );
		
		String button = ((DailyRewardGoods)getGoods()).isEnabled() ? "green_button" : "gray_button";
		actionbttn_offset = main.h(0.07f);
		actionbttn = new Sprite( pX+actionbttn_offset, pY+main.h(0.62f), main.textures.get( button ), main.getVertexBufferObjectManager() )
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
		String label = ((DailyRewardGoods)getGoods()).isEnabled() ? "COLLECT" : "DISABLED";
		buttontxt = new Text( pX+actionbttn_offset, pY+main.h(0.615f), bigger_font, label, "XXXXXXXXX".length(), main.getVertexBufferObjectManager() );
		
		float xoffset = (actionbttn.getWidth() - buttontxt.getWidth() ) / 2;
    	buttontxt.setX( buttontxt.getX() + xoffset );
    	float yoffset = (actionbttn.getHeight() - buttontxt.getHeight() ) / 2;
    	buttontxt.setY( buttontxt.getY() + yoffset );
    	
    	button_text_offset = buttontxt.getX() - pX;
	}
	
	public void onAction()
	{
		DailyRewardGoods dr_goods = (DailyRewardGoods)getGoods();
		boolean success = dr_goods.collect();
		
		if ( success )
		{
			main.mTap.play();
			screen.replaceEntities();
			if ( screen instanceof ShowingCurrency )
				((ShowingCurrency)screen).refreshCurrency();
			
			main.changeScreen( new UsualGameScreen() );
		}
		else
		{
			main.mMissed.play();
		}
	}	
}
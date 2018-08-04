package com.joymobile.whistlerattack;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;

public abstract class GoodsEntity
{
	Sprite rect, actionbttn, thumb;
	Text title,  desc, buttontxt;
	float button_text_offset, desc_offset, title_offset,  actionbttn_offset, price_offset;
	CurrencyUnitEntity currency_unit_entity;
	Font bigger_font, font;
	float pX, pY;
	
	boolean can_action = false;
	
	Goods goods;
	MainActivity main;
	HavingEntities screen;
	
	protected TimerHandler timer_handler = new TimerHandler( 0.05f, false, new ITimerCallback()
	{
		public void onTimePassed( TimerHandler pTimerHandler )
		{
			can_action = true;
		}
	});
	
	public GoodsEntity( Goods goods, final float pX, final float pY, Font font, Font bigger_font, MainActivity main, HavingEntities screen )
	{
		this.goods = goods;
		this.main = main;
		this.screen = screen;
		this.bigger_font = bigger_font;
		this.font = font;
		this.pX = pX;
		this.pY = pY;
		
		createEntity();
	}

	protected void createEntity()
	{
		createRectangle( pX, pY );
		createActionButton( pX, pY, bigger_font );
		createPrice( pX, pY, bigger_font );
		createThumbnail( pX, pY );
        createTitle( pX, pY, bigger_font );
        createDescription( pX, pY, font );
	}

	protected void createDescription( final float pX, final float pY, Font font )
	{
		desc_offset = main.h(0.07f);
		desc = new Text( pX+desc_offset, pY+main.h(0.62f), font, goods.getDesc(), goods.getDesc().length(), main.getVertexBufferObjectManager() );
	}

	protected void createTitle( final float pX, final float pY, Font bigger_font )
	{
		title_offset = main.h(0.07f);
		title = new Text( pX+title_offset, pY+main.h(0.105f), bigger_font, goods.getTitle(), goods.getTitle().length(), main.getVertexBufferObjectManager() );
	}

	protected void createThumbnail( final float pX, final float pY )
	{
		thumb = new Sprite( pX, pY, main.textures.get( goods.getThumbnail() ), main.getVertexBufferObjectManager() );
	}

	protected void createRectangle( final float pX, final float pY )
	{
		rect = new Sprite( pX, pY, main.textures.get( "goods" ), main.getVertexBufferObjectManager() );
	}

	protected void createPrice( final float pX, final float pY, Font bigger_font )
	{
		price_offset = main.h(0.14f);
		currency_unit_entity = goods.getPriceInCurrencyUnit().makeCurrencyUnitEntity();
		currency_unit_entity.createEntityCentered( pX+price_offset, pY+main.h(0.013f), rect.getWidth()/2, bigger_font, main );
	}

	protected void createActionButton( final float pX, final float pY, Font bigger_font )
	{
		can_action = false;
		main.scene.registerUpdateHandler( timer_handler );
	}
	
	public void attachAsChild( Scene scene )
	{
		scene.getChildByIndex(1).attachChild( rect );
		scene.getChildByIndex(1).attachChild( actionbttn );
		scene.registerTouchArea( actionbttn );
		scene.getChildByIndex(1).attachChild( thumb );
		scene.getChildByIndex(1).attachChild( title );
		scene.getChildByIndex(1).attachChild( desc );
		scene.getChildByIndex(1).attachChild( buttontxt );
		currency_unit_entity.attachAsChild( scene );
	}
	
	public void detachSelf()
	{
		rect.detachSelf();
		actionbttn.detachSelf();
		main.scene.unregisterTouchArea( actionbttn );
		thumb.detachSelf();
		title.detachSelf();
		desc.detachSelf();
		
		buttontxt.detachSelf();
		currency_unit_entity.detachSelf();
	}
	
	public float getX()
	{
		return rect.getX();
	}
	
	public void setX( float x )
	{
		rect.setX( x );
		actionbttn.setX( x+actionbttn_offset );
		thumb.setX( x );
		title.setX( x+title_offset );
		desc.setX( x+desc_offset );
		currency_unit_entity.setX( x+price_offset );
		buttontxt.setX( x+button_text_offset );
	}
	
	public Goods getGoods()
	{
		return goods;
	}
}
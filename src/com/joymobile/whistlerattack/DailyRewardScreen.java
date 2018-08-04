package com.joymobile.whistlerattack;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

public class DailyRewardScreen extends Screen implements HavingEntities, IOnSceneTouchListener
{
	Text bttnText5;
	float x;
	ArrayList<GoodsEntity> shownGoods;
	private float mTouchX = 0, mTouchOffsetX = 0;
	private float mTouchY = 0, mTouchOffsetY = 0;
	
	@Override
	public void onStartScreen( String[] args )
	{
		scene.setOnSceneTouchListener(this);
		
		scene.attachChild( new Entity() );
		scene.attachChild( new Entity() );
		
		x = -( main.h(1.828) - main.w(1) ) / 2;
		
		showBackground();
		
		String txt = "Play every day to get rewarded!";
		Text buttontxt = new Text( 0, main.h(0.05f), main.mFont, txt, txt.length(), main.getVertexBufferObjectManager() );
		
		float xoffset = (main.w(1) - buttontxt.getWidth() ) / 2;
    	buttontxt.setX( buttontxt.getX() + xoffset );
    	
    	scene.attachChild( buttontxt );
		
		showEntities();
		goToGameIfNothingToCollect();
        //showBackButton();
	}

	private void goToGameIfNothingToCollect()
	{
		for ( DailyRewardGoods drge : DailyRewardGoodsProxy.get().getGoodsList() )
		{
			if ( drge.isEnabled() )
				return;
		}
		
		main.changeScreen( new UsualGameScreen() );
	}

	private void showEntities()
	{
		ArrayList<DailyRewardGoods> goods_list = DailyRewardGoodsProxy.get().getGoodsList();
		
        int i = 0;
        shownGoods = new ArrayList<GoodsEntity>();
        for ( Goods goods : goods_list )
        {
        	GoodsEntity entity = goods.makeEntity( main.h(0.1f + 0.65f * i), main.h(0.2f), main.mFont5, main.mFont4, main, this );
            entity.attachAsChild( scene );
            shownGoods.add( entity );

            i++;
        }
	}

	public void replaceEntities()
	{
		int i = 0;
		for ( GoodsEntity entity : shownGoods )
		{
			int x = (int)entity.getX();
			entity.detachSelf();
			shownGoods.set( i, entity.getGoods().makeEntity( x, main.h(0.01f), main.mFont5, main.mFont4, main, this ) );
			shownGoods.get( i ).attachAsChild( scene );
				
			i++;
		}
	}
	
	public void replaceEntity( GoodsEntity target )
	{
		int i = 0;
		for ( GoodsEntity entity : shownGoods )
		{
			if ( entity.equals( target ) )
			{
				int x = (int)entity.getX();
				entity.detachSelf();
				shownGoods.set( i, entity.getGoods().makeEntity( x, main.h(0.01f), main.mFont5, main.mFont4, main, this ) );
				shownGoods.get( i ).attachAsChild( scene );
				
				break;
			}
				
			i++;
		}
	}
	
	private void showBackground()
	{
    	Sprite bkg = new Sprite( -(main.h(2)-main.w(1))/2, -main.h( 0.6 ), textures.get( "feat_background" ), main.getVertexBufferObjectManager() );
    	bkg.setColor( Color.PINK );
    	scene.getChildByIndex(0).attachChild( bkg );
    	
    	bkg.registerEntityModifier( new LoopEntityModifier(
			new RotationModifier( 8, 0, 360 )
		));
	}
	
	public boolean onSceneTouchEvent( Scene pScene, TouchEvent pTouchEvent )
    {
        if( pTouchEvent.isActionDown() ) 
        {
            mTouchX = pTouchEvent.getMotionEvent().getX();
            mTouchY = pTouchEvent.getMotionEvent().getY();
        }
        else if ( pTouchEvent.isActionMove() ) 
        {
    		if ( shownGoods.size() > 0 )
        	{
        		float newX = pTouchEvent.getMotionEvent().getX();
                
                mTouchOffsetX = (newX - mTouchX);
                
                if ( mTouchOffsetX > 0 && shownGoods.get( 0 ).getX() >= main.h(0.1f) )
                	return false;
                if ( mTouchOffsetX < 0 && shownGoods.get( shownGoods.size() - 1 ).getX() <= main.w(0.6f) )
                	return false;
            	
                for ( GoodsEntity feat_entity : shownGoods )
                {
                	float newScrollX = feat_entity.getX() + mTouchOffsetX * 2;
                    feat_entity.setX( newScrollX );
                }
               
                mTouchX = newX;
        	}
        }
        
        return true;
    }
	
	private void showBackButton()
	{
		final Sprite bttn5 = new Sprite( main.w(0.03f), main.h(0.15), textures.get( "back_button" ), main.getVertexBufferObjectManager() )
    	{
    		@Override
    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
    		{
    			if ( pSceneTouchEvent.isActionUp() )
    			{
    				main.mTap.play();
        			main.changeScreen( new MenuScreen() );
    			}
    			
    			return true;
    		}
    	};
    	scene.attachChild( bttn5 );
    	scene.registerTouchArea( bttn5 );
	}
	
	@Override
	public void onExit()
	{
		scene.clearChildScene();
		
		String text = "Would you like to return\n" +
				"to main menu?";
		textComponent = new Text( main.w(0), main.h(0.2f), main.mFont, text, text.length(), main.getVertexBufferObjectManager() );
		textComponent.setHorizontalAlign( HorizontalAlign.CENTER );
		textComponent.setX( ( main.w(1) - textComponent.getWidth() ) / 2 );
		textComponent.setY( ( main.h(1) - textComponent.getHeight() ) / 4 );
    	main.exit_scene.attachChild( textComponent );
		scene.setChildScene( main.exit_scene, false, true, true );
	}
	
	@Override
	public void onExitReturn()
	{
		scene.clearChildScene();
		main.runOnUpdateThread( new Runnable()
		{
			public void run() 
			{
				textComponent.detachSelf();
			}
		});
		main.getMusicManager().setMasterVolume( 0.5f );
	}
}
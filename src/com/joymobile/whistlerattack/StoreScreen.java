package com.joymobile.whistlerattack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.json.JSONObject;

import android.os.Bundle;

import com.jirbo.adcolony.AdColonyVideoAd;
import com.joymobile.whistlerattack.goods.PackGoods;
import com.tapjoy.TapjoyConnect;

public class StoreScreen extends Screen implements HavingEntities, ShowingCurrency, IOnSceneTouchListener
{
	Text bttnText5;
	float x;
	ArrayList<GoodsEntity> shownGoods;
	private float mTouchX = 0, mTouchOffsetX = 0;
	private float mTouchY = 0, mTouchOffsetY = 0;
	private ArrayList<Sprite> shownCategories;
	Text currency;
	
	@Override
	public void onStartScreen( String[] args )
	{
		
		scene.setOnSceneTouchListener(this);
		
		scene.attachChild( new Entity() );
		scene.attachChild( new Entity() );
		
		x = -( main.h(1.828) - main.w(1) ) / 2;
		
		showBackground();

		shownCategories = new ArrayList<Sprite>();
		showEntities( StoreGoodsProxy.UPGRADES );
		
		Sprite panel2 = new Sprite( 0, 0, textures.get( "categories_bar" ), main.getVertexBufferObjectManager() );
		scene.attachChild( panel2 );
		
        showBackButton();
        showCategories();
        
        Sprite panel = new Sprite( 0, 0, textures.get( "currency_bar" ), main.getVertexBufferObjectManager() );
		scene.attachChild( panel );
    	
		Sprite bkg = new Sprite( main.w(0.01f), main.h(0.0), textures.get( "coin" ), main.getVertexBufferObjectManager() );
    	scene.attachChild( bkg );
    	
    	DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator( ',' );
		DecimalFormat formatter = new DecimalFormat( "#,###", symbols );
		String currencytxt = formatter.format( Database.getInt( "currency" ) );
		currency = new Text( main.w(0.09f), main.h(0.03), main.mFont4, currencytxt, "###########".length(), main.getVertexBufferObjectManager() );
		scene.attachChild( currency );
		
		consumePreviousIAPs();
	}

	private void consumePreviousIAPs()
	{
		try
		{
			Bundle bundle = main.mService.getPurchases( 3, main.getPackageName(), "inapp", null );
			if ( bundle.getInt("RESPONSE_CODE") == 0 )
			{
				ArrayList<String> purchaseDataList = bundle.getStringArrayList( "INAPP_PURCHASE_DATA_LIST" );
				for ( String json : purchaseDataList )
				{
					JSONObject jo = new JSONObject( json );
					String token = jo.getString( "purchaseToken" );
					
					String sku = jo.getString( "productId" );
					LinkedHashMap<String, Goods> pgs = StoreGoodsProxy.get().getGoodsList( StoreGoodsProxy.STAR_PACKS );
					for ( Map.Entry<String, Goods> entry : pgs.entrySet() )
					{
						if ( ! ( entry.getValue() instanceof PackGoods ) )
							continue;
						
						PackGoods pg = (PackGoods)entry.getValue();
						
						if ( pg.getBillingId().equals(  sku ) )
						{
							main.mService.consumePurchase( 3, main.getPackageName(), token );
							pg.getPriceInCurrencyUnit().award();
						}
					}
				}
				
				refreshCurrency();
			}
		} 
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	private void showCategories()
	{
		ArrayList<Integer> categories_id = StoreGoodsProxy.get().getCategoriesId();
		int j = 0;
		
		for ( final Integer category_id : categories_id )
		{
			final Sprite bttn5 = new Sprite( main.w(0.03f), main.h(0.35 + j * 0.2), textures.get( "cat" +j ), main.getVertexBufferObjectManager() )
	    	{
	    		@Override
	    	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	    		{
	    			if ( pSceneTouchEvent.isActionUp() )
	    			{
	    				main.mTap.play();
	    				
	    				for ( GoodsEntity shownEntity : shownGoods )
	    				{
	    					shownEntity.detachSelf();
	    				}
	    				
	    				showEntities( category_id );
	    				
	    				TapjoyConnect.getTapjoyConnectInstance().getTapPoints( main );
	    				
	    				return true;
	    			}
	    			
	    			return false;
	    		}
	    	};
	    	scene.attachChild( bttn5 );
	    	scene.registerTouchArea( bttn5 );
	    	shownCategories.add( bttn5 );
	    	
	    	j++;
		}
	}

	private void showEntities( int category_id )
	{
		LinkedHashMap<String, Goods> goods_list = StoreGoodsProxy.get().getGoodsList( category_id );
		
        int i = 0;
        shownGoods = new ArrayList<GoodsEntity>();
        for ( Map.Entry<String, Goods> entry : goods_list.entrySet() )
        {
        	Goods goods = entry.getValue();
        	GoodsEntity entity = goods.makeEntity( main.h(0.3656f + 0.65f * i), main.h(0.01f), main.mFont5, main.mFont4, main, this );
            entity.attachAsChild( scene );
            shownGoods.add( entity );

            i++;
        }
	}
	
	public void refreshCurrency()
	{
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator( ',' );
		DecimalFormat formatter = new DecimalFormat( "#,###", symbols );
		String currencytxt = formatter.format( Database.getInt( "currency" ) );
		currency.setText( currencytxt );
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
        	if ( mTouchX > main.w( 0.2 ) )
        	{
        		if ( shownGoods.size() > 0 )
            	{
            		float newX = pTouchEvent.getMotionEvent().getX();
                    
                    mTouchOffsetX = (newX - mTouchX);
                    
                    if ( mTouchOffsetX > 0 && shownGoods.get( 0 ).getX() >= main.w(0.2f) )
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
        	else
        	{
        		float newY = pTouchEvent.getMotionEvent().getY();
                
                mTouchOffsetY = (newY - mTouchY);
                
                if ( mTouchOffsetY > 0 && shownCategories.get( 0 ).getY() >= main.h(0.15f) )
                	return false;
                if ( mTouchOffsetY < 0 && shownCategories.get( shownCategories.size() - 1 ).getY() <= main.h(0.8f) )
                	return false;
            	
                for ( Sprite category : shownCategories )
                {
                	float newScrollY = category.getY() + mTouchOffsetY * 1.5f;
                    category.setY( newScrollY );
                }
               
                mTouchY = newY;
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
    				
    				if ( main.cb.hasCachedInterstitial() )
    				{
    					main.cb.showInterstitial( "store" );
    				}
    				
        			main.changeScreen( new MenuScreen() );
    			}
    			
    			return true;
    		}
    	};
    	scene.attachChild( bttn5 );
    	scene.registerTouchArea( bttn5 );
    	
    	shownCategories.add( bttn5 );
	}
	
	/*@Override
	public void onExit()
	{
		scene.clearChildScene();
		
		String text = "Would you like to exit game?";
		textComponent = new Text( main.w(0), main.h(0.2f), main.mFont, text, text.length(), main.getVertexBufferObjectManager() );
		textComponent.setHorizontalAlign( HorizontalAlign.CENTER );
		textComponent.setX( ( main.w(1) - textComponent.getWidth() ) / 2 );
		textComponent.setY( ( main.h(1) - textComponent.getHeight() ) / 2 );
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
	}*/
	
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
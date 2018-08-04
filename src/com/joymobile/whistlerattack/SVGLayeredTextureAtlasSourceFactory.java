package com.joymobile.whistlerattack;

import org.andengine.extension.svg.opengl.texture.atlas.bitmap.source.SVGAssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SVGLayeredTextureAtlasSourceFactory
{
	public static IBitmapTextureAtlasSource makeAtlasSource( final Context context, String base_path, final String[] paths, final int width, final int height )
	{
		SVGAssetBitmapTextureAtlasSource atlasSource = new SVGAssetBitmapTextureAtlasSource( context, base_path, width, height );
		IBitmapTextureAtlasSource decoratedTextureAtlasSource = new BaseBitmapTextureAtlasSourceDecorator( atlasSource ) 
		{
			@Override
			protected void onDecorateBitmap( Canvas pCanvas ) throws Exception 
			{
				for ( String path : paths )
				{
					SVGAssetBitmapTextureAtlasSource source = new SVGAssetBitmapTextureAtlasSource( context, path, width, height );
					Bitmap bitmap = source.onLoadBitmap( Bitmap.Config.ARGB_8888 );
					pCanvas.drawBitmap( bitmap, 0, 0, this.mPaint );
				}
			}
	
			@Override
			public BaseBitmapTextureAtlasSourceDecorator deepCopy() 
			{
				throw new DeepCopyNotSupportedException();
			}
		};
		
		return decoratedTextureAtlasSource;
	}
}
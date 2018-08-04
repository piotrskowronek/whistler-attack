package com.joymobile.whistlerattack;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.andengine.util.base64.Base64;

import android.content.Context;
import android.content.SharedPreferences;

public class Database
{
	private static SharedPreferences prefs;
	private static SharedPreferences.Editor editor;
	
	public static void initialize( Context context )
	{
		prefs = context.getSharedPreferences( "fvsdfb3fsdv", Context.MODE_PRIVATE );
		editor = prefs.edit();
	}
	
	public static ArrayList<String> getStringArray( String key )
	{
	    ArrayList<String> array = new ArrayList<String>();
	    for ( int i = 0; prefs.contains( key + "_" + i ); i++ ) 
	    {
	    	String encrypted = prefs.getString( key + "_" + i, "" );
	    	String original;
			try 
			{
				original = new String( Base64.decode( encrypted.getBytes( "UTF-8" ), Base64.DEFAULT ), "UTF-8" );
				array.add( original );
			} 
			catch ( UnsupportedEncodingException e ) 
			{ 
				e.printStackTrace();
			}
	    }
	    
	    return array;  
	}
	
	public static void addStringToEndOfArray( String key, String value )
	{
		int i = 0;
		while ( prefs.contains( key + "_" + i ) )
			i++;
		
		String to_encrypt = value;
    	try 
    	{
			String encrypted = Base64.encodeToString( to_encrypt.getBytes( "UTF-8" ), Base64.DEFAULT );
			editor.putString( key + "_" + i, encrypted );
			editor.commit();
    	} 
		catch ( UnsupportedEncodingException e ) 
		{ 
			e.printStackTrace();
		}
	}
	
	public static void raiseInt( String key )
	{
		try
		{
			String encrypted = prefs.getString( key, "0" );
			int value;
			if ( encrypted.equals( "0" ) )
			{
				value = 0;
			}
			else
			{
				String original = new String( Base64.decode( encrypted.getBytes( "UTF-8" ), Base64.DEFAULT ), "UTF-8" );
				value = Integer.parseInt( original );
			}
			
			value++;
			
			encrypted = Base64.encodeToString( ( "" + value ).getBytes( "UTF-8" ), Base64.DEFAULT );
			editor.putString( key, encrypted );
			editor.commit();
		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}
	}
	
	public static int getInt( String key )
	{
		try
		{
			String encrypted = prefs.getString( key, "0" );
			if ( encrypted.equals( "0" ) )
				return 0;
			String original = new String( Base64.decode( encrypted.getBytes( "UTF-8" ), Base64.DEFAULT ), "UTF-8" );
			return Integer.parseInt( original );
		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	public static boolean getBoolean( String key )
	{
		try
		{
			String encrypted = prefs.getString( key, "false" );
			if ( encrypted == "false" )
				return false;
			String original = new String( Base64.decode( encrypted.getBytes( "UTF-8" ), Base64.DEFAULT ), "UTF-8" );
			return ( original.equals( "true" ) ) ? true : false;
		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static void setBoolean( String key, boolean value )
	{
		try
		{
			String converted = value ? "true" : "false";
			String encrypted = Base64.encodeToString( converted.getBytes( "UTF-8" ), Base64.DEFAULT );
			editor.putString( key, encrypted );
			editor.commit();
		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}
	}
	
	public static void setInt( String key, int value )
	{
		try
		{
			String encrypted = Base64.encodeToString( ( "" + value ).getBytes( "UTF-8" ), Base64.DEFAULT );
			editor.putString( key, encrypted );
			editor.commit();
		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}
	}

	public static String getString( String key )
	{
    	String encrypted = prefs.getString( key, "" );
    	String original = encrypted;
		try 
		{
			original = new String( Base64.decode( encrypted.getBytes( "UTF-8" ), Base64.DEFAULT ), "UTF-8" );
		} 
		catch ( UnsupportedEncodingException e ) 
		{ 
			e.printStackTrace();
		}

		return original;
	}

	public static void setString( String key, String value )
	{
		String to_encrypt = value;
    	try 
    	{
			String encrypted = Base64.encodeToString( to_encrypt.getBytes( "UTF-8" ), Base64.DEFAULT );
			editor.putString( key, encrypted );
			editor.commit();
    	} 
		catch ( UnsupportedEncodingException e ) 
		{ 
			e.printStackTrace();
		}
	}

	public static void decreaseInt( String key )
	{
		try
		{
			String encrypted = prefs.getString( key, "0" );
			int value;
			if ( encrypted.equals( "0" ) )
			{
				value = 0;
			}
			else
			{
				String original = new String( Base64.decode( encrypted.getBytes( "UTF-8" ), Base64.DEFAULT ), "UTF-8" );
				value = Integer.parseInt( original );
			}
			
			value--;
			
			encrypted = Base64.encodeToString( ( "" + value ).getBytes( "UTF-8" ), Base64.DEFAULT );
			editor.putString( key, encrypted );
			editor.commit();
		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}
	}
}
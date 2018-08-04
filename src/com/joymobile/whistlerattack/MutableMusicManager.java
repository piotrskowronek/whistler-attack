package com.joymobile.whistlerattack;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicManager;

public class MutableMusicManager extends MusicManager
{
	MusicManager mmanager;
	static boolean is_muted;
	
	public MutableMusicManager( MusicManager mmanager )
	{
		this.mmanager = mmanager;
		is_muted = Database.getBoolean( "music_muted" );
		
		if ( is_muted )
			super.setMasterVolume( 0 );
	}
	
	@Override
	public void setMasterVolume( float pMasterVolume )
	{
		if ( is_muted )
			mmanager.setMasterVolume( 0 );
		else
			mmanager.setMasterVolume( pMasterVolume );
	}
	
	public void mute()
	{
		is_muted = true;
		Database.setBoolean( "music_muted", true );
		mmanager.setMasterVolume( 0 );
	}
	
	public void unmute( float value )
	{
		is_muted = false;
		Database.setBoolean( "music_muted", false );
		mmanager.setMasterVolume( value );
	}
	
	public boolean isMuted()
	{
		return is_muted;
	}

	public void add( Music pAudioEntity )
	{
		mmanager.add( pAudioEntity );
	}

	public boolean equals( Object o )
	{
		return mmanager.equals( o );
	}

	public float getMasterVolume()
	{
		return mmanager.getMasterVolume();
	}

	public int hashCode()
	{
		return mmanager.hashCode();
	}

	public boolean remove( Music pAudioEntity )
	{
		return mmanager.remove( pAudioEntity );
	}

	public void releaseAll()
	{
		mmanager.releaseAll();
	}

	public String toString()
	{
		return mmanager.toString();
	}

	
}

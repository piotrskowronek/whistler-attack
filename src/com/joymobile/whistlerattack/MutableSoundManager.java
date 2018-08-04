package com.joymobile.whistlerattack;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundManager;

import android.media.SoundPool;

public class MutableSoundManager extends SoundManager
{
	SoundManager smanager;
	static boolean is_muted;
	
	public MutableSoundManager( SoundManager smanager )
	{
		this.smanager = smanager;
		is_muted = Database.getBoolean( "sound_muted" );
		
		if ( is_muted )
			super.setMasterVolume( 0 );
	}
	
	@Override
	public void setMasterVolume( float pMasterVolume )
	{
		if ( is_muted )
			smanager.setMasterVolume( 0 );
		else
			smanager.setMasterVolume( pMasterVolume );
	}
	
	public void mute()
	{
		is_muted = true;
		Database.setBoolean( "sound_muted", true );
		smanager.setMasterVolume( 0 );
	}
	
	public void unmute( float value )
	{
		is_muted = false;
		Database.setBoolean( "sound_muted", false );
		smanager.setMasterVolume( value );
	}
	
	public boolean isMuted()
	{
		return is_muted;
	}

	public void add( Sound pSound )
	{
		smanager.add( pSound );
	}

	public boolean equals( Object o )
	{
		return smanager.equals( o );
	}

	public float getMasterVolume()
	{
		return smanager.getMasterVolume();
	}

	public int hashCode()
	{
		return smanager.hashCode();
	}

	public boolean remove( Sound pSound )
	{
		return smanager.remove( pSound );
	}

	public void releaseAll()
	{
		smanager.releaseAll();
	}

	public void onLoadComplete( SoundPool pSoundPool, int pSoundID, int pStatus )
	{
		smanager.onLoadComplete( pSoundPool, pSoundID, pStatus );
	}

	public String toString()
	{
		return smanager.toString();
	}
}
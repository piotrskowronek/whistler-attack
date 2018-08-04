package com.joymobile.whistlerattack;


class GestureHelper
{
	static GestureHelper instance;
	
	int hash_code = 0;
	
	public static GestureHelper get()
	{
		if ( instance == null )
		{
			instance = new GestureHelper();
		}
		return instance;
	}
	
	private GestureHelper()
	{

	}

	public int getHashCode()
	{
		return hash_code;
	}

	public void setHashCode( int hash_code )
	{
		this.hash_code = hash_code;
	}

}
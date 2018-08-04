package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;

import android.util.Log;

class AdventureLevel5 extends Level
{
	{
		sequences_code = new String[]{
			"-7-1s,5-4s,8s-3",
			"-8s-5s,3s-2,7s-6",
			"-1s,3-6s,7s-4s,2s-1",
			"-7s,5s-4s-6-3,6s",
			"-6s-7s,6s-5-4s-1s,7s",
			"-4-6s,8s-3s-2,5s-1s",
			"-5s,7-4s,7s,1-8s,3s",
			"-3,7s-4s,2-1-8s,7,6s",
			"-3-2s,5s-6s,7-1s",
			"-8,7-3,4-1s,2s",
			"-5,3s-1,8-6s-3,1",
			"-7-3,2s-5,6s-5s,1",
			"-5,8-3s,5s,1s-8-2,4",
			"-3,5s,7-1,3s-6,2s",
			"-5,4s-1b,2-8,3s",
			"-4,3-3s,8,2-7,5s",
			"-3-1,5s,8s-3s,8s,2",
			"-6s,3s-1,8,4s-2,3s,7",
			"-2s-1,7b,2s,6-3,2s,5-4,1s",
			"-6s,4s,3s-7,8s,1-2s-8",
			"-5s,8,1b-6,7s-3s,8s,2",
			"-5,4s-1s,3b,7b-2,6s,5s",
			"-5s,2s,3s-1s,5b-6,8s-5s,1,4s",
			"-1s,8s5-3b,2,7-6,8s",
			"-4s,8s-6,7b,1-2,3s-6s,2,7",
			"-4,2s-8s,7s,3s-1,7s",
			"-6,1-7s,5,4s-3s-2,6s,8-3s,7s,1",
			"-8,7s,6s-1b,2s,3s-7",
			"-4-6s,3s,7b-1,2-6s,5,4",
			"-3,8b-4s,7-1,5",
			"-8s,1s,7-6,2s-5,4s"
		};
		
		duration = 5000;
		delay_multipler = 0.15f;// 0.3f;
		opened_duration = 0.7f;
	}
	
	public AdventureLevel5( Scene scene, GameScreen screen, MainActivity main )
	{
		super( scene, screen, main );
	}
}
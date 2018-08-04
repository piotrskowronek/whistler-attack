package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;

class AdventureLevel3 extends Level
{
	{
		sequences_code = new String[]{
			"-6,8s-2s,5s-1s,3",
			"-6-5s,8-1s,2s",
			"-3s-1,6-2s,8",
			"-6-7s,5s-3s",
			"-5-8b,1s-6s",
			"-1s,3s,2-5",
			"-6s-3s,7,4s",
			"-3-2s,8s",
			"-7-3s,4-1s,6",
			"-5-4s,7s-1s,8",
			"-6s,7-5s,2,3-6s",
			"-8-3s,7s-4-5s,6",
			"-7s,6-2,5s-8s",
			"-6-8s,3-2,7b",
			"-5s-7s,4s,6s-2",
			"-4s,1s-5,7",
			"-5-6s,3-4s,8-1s,6s",
			"-7s,5s-6s-1,4",
			"-2s-6,5s-8,3s-4",
			"-6-5s,8-3s,1",
			"-7-1s,5-4s,8s-3",
			"-8s-5s,3s-2,7s-6",
			"-1s,3-6s,7s-4s,2s-1",
			"-7s,5s-4s-6-3,6s",
			"-6s-7s,6s-5-4s-1s,7s",
			"-4-6s,8s-3s-2,5s-1s",
			"-5s,7-4s,7s,1-8s,3s",
			"-3,7s-4s,2-1-8s,7,6s",
			"-6,4s-7s,1-8-1,2s",
			"-7s,8s-4,1s-3",
			"-6-7s,4,5s-3s",
			"-4s,1s-8s,6-2s",
			"-8s,7-4s-3,1s",
			"-7-6s,3s-1,2s,3",
			"-8s,7s,4s-5",
			"-6s-7s-1,3,5s-86,6s-4"
		};
		
		duration = 25;
		delay_multipler = 0.25f;
		opened_duration = 0.85f;
	}
	
	public AdventureLevel3( Scene scene, GameScreen screen, MainActivity main )
	{
		super( scene, screen, main );
	}
}
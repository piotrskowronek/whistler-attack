package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;

class AdventureLevel2 extends Level
{
	{
		sequences_code = new String[]{
			"-4s,7s,3-1",
			"-2,8s,5-3b,6",
			"-1s,2b,3-4,5",
			"-5s,7,1s",
			"-8-4s,1s-5,2-6b",
			"-5,1s-3s,4s,5-8-1s",
			"-2,3s,8b-1,7-3s,4",
			"-4,8,1s-3s,5-7s,2",
			"-2,5s-4s,6s,1",
			"-7-4s,5-1s,7",
			"-6s-8s,3s,1-4",
			"-2-4,8-1s",
			"-5-7s,6s,1",
			"-3s,4,2-6,7s,3",
			"-7,4-1-2s,5s,1",
			"-4,2,7-3s,5s,8b",
			"-6b,4-2,5,3s-1,3",
			"-3,8s-2,6-2b,7",
			"-5,8-3s,5s,1s-8-2,4",
			"-3,5s,7-1,3s-6,2s",
			"-5,4s-1b,2-8,3s",
			"-4,3-3s,8,2-7,5s",
			"-6,4,3s-6",
			"-2s,3s-6s,7s-1b,4",
			"-2s,5-7s-1,8",
			"-3s,4s,8s-7,1",
			"-1s,7s-8,4,3b",
			"-6,7s,8s-1s,2s,3s",
			"-1,4,6s-8,5s,3",
			"-4-8,5s-1,2,8s-4,7",
			"-1-4b,5s,7"
		};
		
		duration = 15;
		delay_multipler = 0.5f;
		opened_duration = 1f;
	}
	
	public AdventureLevel2( Scene scene, GameScreen screen, MainActivity main )
	{
		super( scene, screen, main );
	}
}
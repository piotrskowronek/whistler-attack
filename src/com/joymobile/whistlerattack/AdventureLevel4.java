package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;

class AdventureLevel4 extends Level
{
	{
		sequences_code = new String[]{
			"-7s,4s-2,8s-2s,5b",
			"-3,8s-1,5,6-4s,2b",
			"-7,6-3s,5-1,8b",
			"-4,2s-8s,7s,3s-1,7s",
			"-6,1-7s,5,4s-3s-2,6s,8-3s,7s,1",
			"-8,7s,6s-1b,2s,3s-7",
			"-4-6s,3s,7b-1,2-6s,5,4",
			"-3,8b-4s,7-1,5",
			"-8s,1s,7-6,2s-5,4s",
			"-6s,5-4s,1,2",
			"-1,2,3-4b,5b,6s,7s,8s",
			"-1s-7s,8,5-3b-7s,1",
			"-8s-6,5,1s",
			"-2s-6,8-5,4b",
			"-7,8s-4,5,1s-2",
			"-3-1,5s,8s-3s,8s,2",
			"-6s,3s-1,8,4s-2,3s,7",
			"-2s-1,7b,2s,6-3,2s,5-4,1s",
			"-6s,4s,3s-7,8s,1-2s-8",
			"-5s,8,1b-6,7s-3s,8s,2",
			"-5,4s-1s,3b,7b-2,6s,5s",
			"-5s,2s,3s-1s,5b-6,8s-5s,1,4s",
			"-1s,8s5-3b,2,7-6,8s",
			"-4s,8s-6,7b,1-2,3s-6s,2,7",
			"-5b,8s,4s-3,1-7,8s",
			"-2s,7-6-5,1s-3,8-4s,5s,6b",
			"-1,4s,7s-8,6-3s,2-4s,5-8s,1",
			"-7,5s,3s-1s,6s,8b-2s,4,7s",
			"-7s,4s,3-2,8s-5,1s-7s-6s"
		};
		
		duration = 40;
		delay_multipler = 0.25f;// 0.3f;
		opened_duration = 0.7f;
	}
	
	public AdventureLevel4( Scene scene, GameScreen screen, MainActivity main )
	{
		super( scene, screen, main );
	}
}
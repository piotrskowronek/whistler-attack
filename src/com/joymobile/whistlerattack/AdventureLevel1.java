package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;

class AdventureLevel1 extends Level
{
	{
		sequences_code = new String[]{
			"-1,2-4-6",
			"-8,5-3",
			"-3-5,4-1,8-6-4-1,2",
			"-2-5-1,3",
			"-4,7-3-3,6",
			"-7-6-3,6-1",
			"-5,3-4-2,8",
			"-6,2-1",
			"-5s,1-6-2s,6s-3",
			"-7,2-8s,3-1,5s-4",
			"-6-3s,7-8,1-2s,3s",
			"-5s,3s-6,7s-1,4",
			"-3-2s,5s-6s,7-1s",
			"-8,7-3,4-1s,2s",
			"-5,3s-1,8-6s-3,1",
			"-7-3,2s-5,6s-5s,1",
			"-2,6-8,5s",
			"-4s,8s-1s,7-6",
			"-7,4s-3s,6s-3",
			"-3,7s-5,6-1,7s",
			"-6-5s,8s-1",
			"-4,5-5s,8s",
			"-5s,8-1,4-2s",
			"-7,3-2s",
			"-7,8s-4s,1",
			"-2s,6s-8-3s,7s"

		};
		
		duration = 8;
		delay_multipler = 0.7f;
		opened_duration = 1.2f;
	}
	
	public AdventureLevel1( Scene scene, GameScreen screen, MainActivity main )
	{
		super( scene, screen, main );
	}
}
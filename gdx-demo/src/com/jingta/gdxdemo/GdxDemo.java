package com.jingta.gdxdemo;

import com.badlogic.gdx.Game;
import com.jingta.gdxdemo.screen.GameScreen;

public class GdxDemo extends Game {	
	
	@Override
	public void create() {		
		setScreen(new GameScreen());
	}

}

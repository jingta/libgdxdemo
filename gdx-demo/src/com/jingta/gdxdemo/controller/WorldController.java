package com.jingta.gdxdemo.controller;

import java.util.HashMap;
import java.util.Map;

import com.jingta.gdxdemo.model.Guy;
import com.jingta.gdxdemo.model.World;

public class WorldController {
	public enum Keys { LEFT, RIGHT, JUMP, FIRE }
	private World world;
	private Guy hero;
	
	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.FIRE, false);
	}
	
	public WorldController(World world) {
		this.world = world;
		this.hero = this.world.getHero();
	}

	// key events
	public void leftPressed(){
		keys.put(Keys.LEFT, true);
	}
	public void leftReleased(){
		keys.put(Keys.LEFT, false);
	}
	public void rightPressed() { keys.put(Keys.RIGHT, true); }
	public void rightReleased() { keys.put(Keys.RIGHT, false); }
	public void jumpPressed() { keys.put(Keys.JUMP, true); }
	public void jumpReleased() { keys.put(Keys.JUMP, false); }
	public void firePressed() { keys.put(Keys.FIRE, true); }
	public void fireReleased() { keys.put(Keys.FIRE, false); }
	
	public void update(float delta){
		processInput();
		hero.update(delta);
	}
	
	private void processInput(){
		// check what keys are down
		if (keys.get(Keys.LEFT)) {
			hero.setFacingLeft(true);
			hero.setState(Guy.State.WALKING);
			hero.getVelocity().x = -Guy.SPEED;
		}
		if (keys.get(Keys.RIGHT)) {
			hero.setFacingLeft(false);
			hero.setState(Guy.State.WALKING);
			hero.getVelocity().x = Guy.SPEED;
		}
		if (keys.get(Keys.RIGHT) == keys.get(Keys.LEFT)){
			hero.setState(Guy.State.IDLE);
			hero.getAcceleration().x = 0;
			hero.getVelocity().x = 0;
		}
		//TODO: right etc
	}
	
}

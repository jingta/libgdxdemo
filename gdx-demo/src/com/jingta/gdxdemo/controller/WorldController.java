package com.jingta.gdxdemo.controller;

import java.util.HashMap;
import java.util.Map;

import com.jingta.gdxdemo.model.Hero;
import com.jingta.gdxdemo.model.World;

public class WorldController {
	public enum Keys { LEFT, RIGHT, JUMP, FIRE }
	
	private static final long LONG_PRESS_JUMP = 150l;
	private static final float ACCELERATION = 20f;
	private static final float GRAVITY = -20f;
	private static final float MAX_JUMP_SPEED = 7f;
	private static final float DAMP = 0.60f;
	private static final float MAX_VELOCITY = 4f;
	
	private World world;
	private Hero hero;
	
	private long jumpPressTime;
	private boolean jumpPressed;
	
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
	public void jumpPressed() { keys.put(Keys.JUMP, true); this.jumpPressed = true; }
	public void jumpReleased() { keys.put(Keys.JUMP, false); this.jumpPressed = false; }
	public void firePressed() { keys.put(Keys.FIRE, true); }
	public void fireReleased() { keys.put(Keys.FIRE, false); }
	
	public void update(float delta){
		processInput();
		
		hero.getAcceleration().y = GRAVITY;
		hero.getAcceleration().y *= delta;
		hero.getAcceleration().x *= delta;
		hero.getVelocity().add(hero.getAcceleration());
		if (hero.getAcceleration().x == 0 && !hero.getState().equals(Hero.State.JUMPING)) hero.getVelocity().x *= DAMP;
		if (hero.getVelocity().x > MAX_VELOCITY ) hero.getVelocity().x = MAX_VELOCITY;
		if (hero.getVelocity().x < -MAX_VELOCITY) hero.getVelocity().x = -MAX_VELOCITY;
		hero.update(delta);
		
		// TODO: some lame unfinished screen bounds
		if (hero.getPosition().y < 0) {
			hero.getPosition().y = 0f;
			//hero.setPosition(hero.getPosition());
			if (hero.getState().equals(Hero.State.JUMPING)) hero.setState(Hero.State.IDLE);
		}
		if (hero.getPosition().x < 0) {
			hero.getPosition().x = 0f;
			if (!hero.getState().equals(Hero.State.JUMPING)) hero.setState(Hero.State.IDLE);
		}
	}
	
	private void processInput(){
		// check what keys are down
		if (keys.get(Keys.JUMP)) {
			if (!hero.getState().equals(Hero.State.JUMPING)) {
				hero.setState(Hero.State.JUMPING);
				hero.getVelocity().y = MAX_JUMP_SPEED;
				this.jumpPressed = true;
				this.jumpPressTime = System.currentTimeMillis();
			} else if (this.jumpPressed && (System.currentTimeMillis() - this.jumpPressTime > LONG_PRESS_JUMP)) {
				this.jumpPressed = false;
			} else if (this.jumpPressed) {
				hero.getVelocity().y = MAX_JUMP_SPEED;
			}
		}
		if (keys.get(Keys.LEFT)) {
			hero.setFacingLeft(true);
			if (!hero.getState().equals(Hero.State.JUMPING)) hero.setState(Hero.State.WALKING);
			hero.getAcceleration().x = -ACCELERATION;
		}
		if (keys.get(Keys.RIGHT)) {
			hero.setFacingLeft(false);
			if (!hero.getState().equals(Hero.State.JUMPING)) hero.setState(Hero.State.WALKING);
			hero.getAcceleration().x = ACCELERATION;
		}
		if (keys.get(Keys.RIGHT) == keys.get(Keys.LEFT)){
			if (!hero.getState().equals(Hero.State.JUMPING)) hero.setState(Hero.State.IDLE);
			hero.getAcceleration().x = 0;
		}
		//TODO: right etc
	}
	
}

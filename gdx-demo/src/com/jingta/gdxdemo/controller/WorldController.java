package com.jingta.gdxdemo.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.jingta.gdxdemo.model.Block;
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
	
	// This is the rectangle pool used in collision detection
    // Good to avoid instantiation each frame
    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
            @Override
            protected Rectangle newObject() {
                    return new Rectangle();
            }
    };
	private Array<Block> collidable = new Array<Block>();
	
	private long jumpPressTime;
	private boolean jumpPressed;
	private boolean grounded = false;
	
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
		if (grounded && hero.getState().equals(Hero.State.JUMPING)) hero.setState(Hero.State.IDLE);
		hero.getAcceleration().y = GRAVITY;
		hero.getAcceleration().x *= delta;
		hero.getAcceleration().y *= delta;
		hero.getVelocity().add(hero.getAcceleration());
		checkCollisionWithBlocks(delta);
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
	private void checkCollisionWithBlocks(float delta) {
		hero.getVelocity().x *= delta;
		hero.getVelocity().y *= delta;
		Rectangle heroRect = rectPool.obtain();
		heroRect.set(hero.getBounds());
		int startX;
		int endX;
		int startY = (int)hero.getBounds().y;
		int endY = (int)(hero.getBounds().y + hero.getBounds().height);
		if (hero.getVelocity().x < 0) {
			startX = endX = (int)Math.floor(hero.getBounds().x + hero.getVelocity().x);
		} else {
			startX = endX = (int)Math.floor(hero.getBounds().x + hero.getVelocity().x + hero.getBounds().width);
		}
		populateCollidableBlocks(startX, startY, endX, endY);
		heroRect.x += hero.getVelocity().x;
		world.getCollisionRects().clear();
		for (Block block : collidable) {
			if (block == null) continue;
			if (heroRect.overlaps(block.getBounds())) {
				hero.getVelocity().x = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}
		// now check upcoming y collisions
		heroRect.x = hero.getPosition().x;
		startX = (int)hero.getBounds().x;
		endX = startX + (int)(Math.ceil(hero.getBounds().width));
		if (hero.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(hero.getBounds().y + hero.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(hero.getBounds().y + hero.getVelocity().y + hero.getBounds().height);
		}
		populateCollidableBlocks(startX, startY, endX, endY);
		heroRect.y += hero.getVelocity().y;
		for (Block block : collidable) {
			if (block == null) continue;
			if (heroRect.overlaps(block.getBounds())) {
				if (hero.getVelocity().y < 0) grounded = true;
				hero.getVelocity().y = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}
		heroRect.y = hero.getPosition().y;
		hero.getPosition().add(hero.getVelocity());
		hero.getBounds().x = hero.getPosition().x; //?????
		hero.getBounds().y = hero.getPosition().y;
		hero.getVelocity().x *= (1/delta);
		hero.getVelocity().y *= (1/delta);
	}
	private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
		collidable.clear();
		for(int x = startX; x <= endX; x++){
			for (int y = startY; y <= endY; y++){
				if (x >= 0 && x < world.getLevel().getWidth() && y >= 0 && y < world.getLevel().getHeight()) {
					collidable.add(world.getLevel().getBlock(x, y));
				}
			}
		}
	}
	
	private void processInput(){
		// check what keys are down
		if (keys.get(Keys.JUMP)) {
			if (!hero.getState().equals(Hero.State.JUMPING)) {
				hero.setState(Hero.State.JUMPING);
				hero.getVelocity().y = MAX_JUMP_SPEED;
				grounded = false;
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

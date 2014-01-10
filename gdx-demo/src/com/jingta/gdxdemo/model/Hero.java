package com.jingta.gdxdemo.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hero {
	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}
	
	public static final float SPEED = 2f;
	static final float JUMP_VELOCITY = 1f;
	public static final float SIZE = 0.5f;
	
	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	float stateTime = 0f;
	
	boolean facingLeft = true;
	public boolean isFacingLeft(){
		return this.facingLeft;
	}
	public void setFacingLeft(boolean facingLeft){
		this.facingLeft = facingLeft;
	}
	
	public Vector2 getAcceleration(){
		return this.acceleration;
	}
	public Vector2 getVelocity() {
		return this.velocity;
	}
	public State getState() {
		return this.state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public float getStateTime(){
		return this.stateTime;
	}
	public void update(float delta) {
		//position.add(velocity.cpy().scl(delta));
		this.stateTime +=delta;
	}
	public void setPosition(Vector2 position) {
        this.position = position;
		this.bounds.setX(position.x);
        this.bounds.setY(position.y);
	}
	public Hero(Vector2 position){
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	public Vector2 getPosition(){
		return position;
	}
	public Rectangle getBounds(){
		return bounds;
	}
	
}

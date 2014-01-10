package com.jingta.gdxdemo.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {

	public static final float SIZE = 1f;
	
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Vector2 getPosition(){
		return position;
	}
	
	public Block(Vector2 position) {
		this.position = position;
		this.bounds.setX(position.x);
        this.bounds.setY(position.y);
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
}

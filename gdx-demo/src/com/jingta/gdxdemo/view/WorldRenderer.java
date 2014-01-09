package com.jingta.gdxdemo.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.jingta.gdxdemo.model.Block;
import com.jingta.gdxdemo.model.Guy;
import com.jingta.gdxdemo.model.World;

public class WorldRenderer {
	private World world;
	private OrthographicCamera cam;
	
	ShapeRenderer debugRenderer = new ShapeRenderer(); //debugging?
	
	public WorldRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(10, 7);
		this.cam.position.set(5, 3.5f, 0);
		this.cam.update();
	}
	
	public void render() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		for (Block block : world.getBlocks()) {
			Rectangle r = block.getBounds();
			float x1 = block.getPosition().x + r.getX();
			float y1 = block.getPosition().y + r.getY();
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(x1, y1, r.width, r.height);
		}
		// render hero
		Guy hero = world.getHero();
		Rectangle r = hero.getBounds();
		float x1 = hero.getPosition().x + r.getX();
		float y1 = hero.getPosition().y + r.getY();
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(x1, y1, r.width, r.height);
		debugRenderer.end();
	}
}

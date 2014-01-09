package com.jingta.gdxdemo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.jingta.gdxdemo.model.Block;
import com.jingta.gdxdemo.model.Guy;
import com.jingta.gdxdemo.model.World;

public class WorldRenderer {
	private World world;
	private OrthographicCamera cam;
	private SpriteBatch spriteBatch;
	private boolean debug = false;
	ShapeRenderer debugRenderer = new ShapeRenderer(); //debugging?
	private Texture blockTexture;
	private Texture heroTexture;
	private int width;
	private int height;
	private float ppux; // pixels per unit, x axis
	private float ppuy; // pixels per unit, y axis
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	
	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		loadTextures();
	}
	public void loadTextures() {
		blockTexture = new Texture(Gdx.files.internal("images/block.png"));
		heroTexture = new Texture(Gdx.files.internal("images/hero.png"));
	}
	public void setSize(int w, int h){
		this.width = w;
		this.height = h;
		this.ppux = (float)width / CAMERA_WIDTH;
		this.ppuy = (float)height / CAMERA_HEIGHT;
	}
	
	public void render() {
		spriteBatch.begin();
		drawBlocks();
		drawHero();
		spriteBatch.end();
		
		if (debug) drawDebug();
	}
	
	private void drawBlocks(){
		
	}
	private void drawHero(){
		
	}
	private void drawDebug(){
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
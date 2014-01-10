package com.jingta.gdxdemo.screen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.jingta.gdxdemo.controller.WorldController;
import com.jingta.gdxdemo.model.World;
import com.jingta.gdxdemo.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {
	private World world;
	private WorldRenderer renderer;
	private WorldController controller;
	
	private int width, height;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		controller.update(delta);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, false);
		controller = new WorldController(world);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) controller.leftPressed();
		if (keycode == Keys.RIGHT) controller.rightPressed();
		if (keycode == Keys.Z) controller.jumpPressed();
		if (keycode == Keys.X) controller.firePressed();
		if (keycode == Keys.D) renderer.toggleDebug();
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT) controller.leftReleased();
		if (keycode == Keys.RIGHT) controller.rightReleased();
		if (keycode == Keys.Z) controller.jumpReleased();
		if (keycode == Keys.X) controller.fireReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) return false;
		if (screenX < width /2 && screenY > height /2) controller.leftPressed();
		if (screenX > width /2 && screenY > height /2) controller.rightPressed();
		if (screenX < width /2 && screenY < height /2) controller.jumpPressed(); //top left
		//if (screenX > width /2 && screenY > height /2) controller.firePressed(); // top right
		if (screenX > width /2 && screenY > height /2) controller.jumpPressed(); // top right
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) return false;
		if (screenX < width /2 && screenY > height /2) controller.leftReleased();
		if (screenX > width /2 && screenY > height /2) controller.rightReleased();
		if (screenX < width /2 && screenY < height /2) controller.jumpReleased(); //top left
		//if (screenX > width /2 && screenY > height /2) controller.fireReleased(); // top right
		if (screenX > width /2 && screenY > height /2) controller.jumpReleased(); // top right
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) return false;

		//fix for dragging past the touch zones
		if (screenX < width /2 && screenY < height /2) controller.leftReleased();
		if (screenX > width /2 && screenY < height /2) controller.rightReleased();
		if (screenX > width /2 && screenY > height /2) {
			controller.leftReleased();
			controller.rightPressed();
		}
		if (screenX < width /2 && screenY > height /2) {
			controller.rightReleased();
			controller.leftPressed();
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}

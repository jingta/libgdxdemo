package com.jingta.gdxdemo.model;

import com.badlogic.gdx.math.Vector2;

public class Level {

	private int width, height;
	private Block[][] blocks;
	
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public Block[][] getBlocks() {
		return blocks;
	}
	public Block getBlock(int x, int y) {
		return blocks[x][y];
	}
	public Level() {
		loadDemoLevel();
	}
	
	private void loadDemoLevel() {
		width = 10;
		height = 7;
		blocks = new Block[width][height];
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				blocks[col][row] = null;
			}
		}
		
		for (int col = 0; col < 10; col++) {
			blocks[col][0] = new Block(new Vector2(col, 0));
			blocks[col][6] = new Block(new Vector2(col, 6));
			if (col > 2) {
				blocks[col][1] = new Block(new Vector2(col, 1));
			}
		}
		blocks[9][2] = new Block(new Vector2(9, 2));
		blocks[9][3] = new Block(new Vector2(9, 3));
		blocks[9][4] = new Block(new Vector2(9, 4));
		blocks[9][5] = new Block(new Vector2(9, 5));

		blocks[6][3] = new Block(new Vector2(6, 3));
		blocks[6][4] = new Block(new Vector2(6, 4));
		blocks[6][5] = new Block(new Vector2(6, 5));
	}
	
}

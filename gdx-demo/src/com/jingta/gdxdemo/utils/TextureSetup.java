package com.jingta.gdxdemo.utils;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
/**
 * Sets up and packs textures
 * @author jingta
 *
 */
public class TextureSetup {
	
	public static void main(String[] args){
		TexturePacker2.process("./../gdx-demo-android/assets/images", "./../gdx-demo-android/assets/images/textures", "textures.pack");
	}
	
}

package com.cryptogames.word;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.cryptogames.main.Game;

public class Tiles {
	
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);
	
	
	
	
	private BufferedImage sprite;
	private int x,y;
	
	
	//Fog of war
	public static boolean show = true;
	
	public Tiles(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite; 
	}
	
	
	public void render(Graphics g) {
		//Fog of war
		if(show) {
			g.drawImage(sprite, x - Camera.x, y- Camera.y, null);
		}
		
		
	}
}

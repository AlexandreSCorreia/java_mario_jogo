package com.cryptogames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.cryptogames.word.World;

public class Enemy extends Entity{

	public boolean right = true,left=false;
	public int life = 3;
	
	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	
	}
	
	public void tick() {
		derp=2;
		//Gravidade do player
		//Se essa posicao estiver livre significa que não há nenhum tile solido embaixo
		//Então mo player devera cair
		if(World.isFree((int)x, (int)(y+1))) {
			y+=1;
		}else {
			
			if(right) {
				if(World.isFree((int)(x+speed), (int)y)) {
					x+=speed;
					if( World.isFree((int)(x+16), (int)y+1)) {
						right=false;
						left=true;
					}
				}else {
					right=false;
					left=true;
					
				}
				
				
			}
				
			if(left) {
				if(World.isFree((int)(x-speed), (int)y)) {
					x-=speed;
					if( World.isFree((int)(x-16), (int)y+1)) {
						right=true;
						left=false;
					}
				}else {
					right=true;
					left=false;
				}	
			}
			
		}
		
		
	}
	
	
	public void render(Graphics g) {
		
		if(right) {
			sprite = Entity.ENEMY_SPRITE_RIGHT[0];
		}else if(left) {
			sprite = Entity.ENEMY_SPRITE_LEFT[0];
		}
		super.render(g);

	}

}

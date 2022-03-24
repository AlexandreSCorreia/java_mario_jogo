package com.cryptogames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.cryptogames.main.Game;
import com.cryptogames.word.Camera;
import com.cryptogames.word.World;

public class Player extends Entity{

	public boolean right,left;
	
	private int dir=1;
	
	//Gravidade
	private double gravity = 0.4;
	private double vspd = 0;
	
	//Pulo
	public boolean isJumping= false;
	public boolean jump = false;
	public int jumphight = 40;
	public int jumpFrames = 0; 
	
	//Animacao
	//private int framesAnimation = 0;
	//private int maxFrames = 15;

	private int curSprite=0;
	//private int maxSprite = 2;
	
	//Vida
	public static double life = 100;
	//Moedas
	public static int currentCoins = 0;
	public static int maxCoins= 0;
	
	public int xC = 0;
	public int yC = 0;
	
	public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height,speed, sprite);
	
	}

	//Sobrescrevendo tick
	public void tick() {
		derp=2;
		//Gravidade do player
		//Se essa posicao estiver livre significa que não há nenhum tile solido embaixo
		//Então mo player devera cair
		/*if(World.isFree((int)x, (int)(y+2)) && isJumping == false) {
			y+=2;
			for(int i=0;i < Game.entities.size();i++) {
				
				Entity e = Game.entities.get(i);
				
				if(e instanceof Enemy) {
					if(Entity.isColliding(this, e)) {
						//Aplicar o pulo
						isJumping=true;
						this.jumphight = 32;
						//Tirar vida do enemy
						 ((Enemy)e).life--;
						 if(((Enemy)e).life == 0) {
							 //Destrui inimigo
							 Game.entities.remove(i);
							 jumphight = 40;
							 //Para loop
							 break;
						 }
					}
				}
				
			}
		
		}*/
		
		
		//Sistema novo de gravidade
		//vspd é a velocidade vertical que está recebendo a gravidade que é o valor que me faz decer para baixo
		//+ eu deço - eu subo
		vspd+=gravity;
		//Verificando se estou no chão para poder pular se tem algo embaixo do player
		if(jump) {
			jump=false;
			if(!World.isFree((int)x, (int)y+1)) {
				//-6 vai ser a atura do meu pulo
				vspd = -8;	
				
					
			}
		}
		
		
			//Verificação de colisao		
			 if(!((int)(y/16)+2 >= World.HEIGHT)){
					 if(!World.isFree((int)x,(int)(y+vspd))) {
						 int signVsp = 0;
						 if(vspd >= 0) {
							 signVsp = 1;
						 }else {
							 signVsp = -1;
						 }
					
						//Esse looping corrigi o erro para deixar o jogador 
						//Literalmente encostado no chão
						while(World.isFree((int)x,(int)(y+signVsp))) {
							y = y+signVsp;	
						}
						
						vspd=0;
					
				
				}else {
					if(World.isFree((int)x, (int)(y+1)) && jump == false) {
						for(int i=0;i < Game.entities.size();i++) {
							
							Entity e = Game.entities.get(i);
							
							if(e instanceof Enemy) {
								if(Entity.isColliding(this, e)) {
									//Aplicar o pulo
									vspd = -6;	
									//Tirar vida do enemy
									 ((Enemy)e).life--;
									 if(!World.isFree((int)x,(int)(y+vspd))) {
											int signVsp = 0;
											if(vspd >= 0) {
												signVsp = 1;
											}else {
												signVsp = -1;
											}

											//Esse looping corrigi o erro para deixar o jogador 
											//Literalmente encostado no chão
											while(World.isFree((int)x,(int)(y+signVsp))) {
												y = y+signVsp;	
											}
											
											vspd=0;
										
										}
									 if(((Enemy)e).life == 0) {
										 //Destrui inimigo
										 Game.entities.remove(i);
										 //Para loop
										 break;
									 }
									 
									
								}
							}
							
						}
					
					}
				}
			
				y = y + vspd;
			
		}else {
			y = y + vspd;
			World.RestartGame();
			return;
		}
		
		
		
		
		if(right && World.isFree((int)(x+speed), (int)y)) {
			x+=speed;
			dir = 1;
		}else if(left && World.isFree((int)(x-speed), (int)y)) {
			x-=speed;
			dir = -1;
		}
		
		/*if(jump) {
			//Verificando se tem chão embaixo
			if(!World.isFree(this.getX(), this.getY()+1)) {
				isJumping=true;
			}else {
				jump=false;
			}
		}
		
		if(isJumping) {
			
			if(World.isFree(this.getX(),this.getY()-2 )) {
				
				y-=2;
				jumpFrames +=2;
				if(jumpFrames == jumphight) {
					isJumping = false;
					jump = false;
					jumpFrames=0;		
				}
			}else {
				isJumping = false;
				jump = false;
				jumpFrames=0;
			}
		}*/
		
		
		//DETECTAR DANO
		for(int i=0;i < Game.entities.size();i++) {
			
			Entity e = Game.entities.get(i);
			
			if(e instanceof Enemy) {
				if(Entity.isColliding(this, e)) {
						if(rand.nextInt(100) < 5) {
							life--;
						}
						 break;
					 }
				}
			}
		

		//DETECTAR COLISAO COM MOEDA
		for(int i=0;i < Game.entities.size();i++) {
			
			Entity e = Game.entities.get(i);
			
			if(e instanceof Coin) {
				if(Entity.isColliding(this, e)) {
						Game.entities.remove(i);
						Player.currentCoins++;
						break;
					 }
				}
			}
		
	
			
	
		//Camera seguir o personagem
		Camera.x = Camera.Clamp((int)Game.player.getX()-(Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);	
		Camera.y = Camera.Clamp((int)Game.player.getY()-(Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	
	
	public void render(Graphics g) {
		/*framesAnimation++;
		if(framesAnimation == maxFrames) {
			curSprite++;
			framesAnimation=0;
			if(curSprite == maxSprite) {
				curSprite=0;
			}
			
		}*/
		//Trocando sprite padrao
		if(dir == 1) {
			sprite = Entity.Player_SPRITE_RIGHT[curSprite];
			
		}else if(dir == -1) {
			sprite = Entity.Player_SPRITE_LEFT[curSprite];
			
			
		}
		//Chamando o metodo de reenderizar
		super.render(g);
	} 

}

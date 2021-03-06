package com.cryptogames.word;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cryptogames.entities.Coin;
import com.cryptogames.entities.Enemy;
import com.cryptogames.entities.Entity;
import com.cryptogames.entities.Player;
import com.cryptogames.main.Game;

public class World {

	public static Tiles[] tiles;
	public static  int WIDTH;
	public static  int HEIGHT;
	public static final int TILE_SIZE = 16;
	
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			//Pegando cquantidade de pixels no mapa
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();	
			tiles = new Tiles[map.getWidth() * map.getHeight()];
			//Pegar os pixels do mapa
			map.getRGB(0, 0, map.getWidth(),map.getHeight(), pixels, 0, map.getWidth());
			for(int i = 0; i< pixels.length;i++) {
				//Identificando a cor do mapa pela cor exadecimal colocar 0x na frente mais 2 FF
				if(pixels[i] == 0xFFFF0000) {
					
				}
			}
			//Percorrendo o mapa "Sprite" e identificando as cores
			for(int xx=0; xx< map.getWidth();xx++) {
				for(int yy= 0; yy< map.getHeight();yy++) {
					
					int pixelAtual = pixels[xx + (yy* map.getWidth())];
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16,Tiles.TILE_FLOOR);
					
					if(pixelAtual == 0xff000000) {
						//tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16,Tiles.TILE_FLOOR);
						
					}else if(pixelAtual == 0xffffffff) {
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*16,yy*16,Tiles.TILE_WALL);
						//Tile inteligente verifica o tile de cima se ? o mesmo se for ele troca a sprite que ser? reenderizada
						if(yy-1 >=0 && pixels[xx + (yy - 1) * map.getWidth()] == 0xffffffff) {
								tiles[xx + (yy*WIDTH)] = new WallTile(xx*16,yy*16,
										Game.spritesheet.getSprite(16, 16, 16, 16));
							}
					}else if(pixelAtual == 0xff0094FF) {
						//Jogador
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
					}else if(pixelAtual == 0xffFF0000) {
						//Enemy
						Enemy enemy = new Enemy(xx*16,yy*16,16,16,1,Entity.ENEMY_SPRITE_RIGHT[0]);
						Game.entities.add(enemy);
					}else if(pixelAtual == 0xffFFD800) {
						//Moedas
						Coin moedas = new Coin(xx*16,yy*16,16,16,0,Entity.MOEDA);
						Game.entities.add(moedas);
						//Preencher automaticamente o total de moedas
						Player.maxCoins++;
					}else if(pixelAtual == 0xff3090FF){
						tiles[xx +(yy*WIDTH)]= new FloorTile(xx*16,yy*16,Game.spritesheet.getSprite(64, 0, 16, 16));
						//Tile inteligente verifica o tile de cima se ? o mesmo se for ele troca a sprite que ser? reenderizada
						if(yy-1 >=0 && pixels[xx + (yy - 1) * map.getWidth()] == 0xff3090FF) {
							tiles[xx +(yy*WIDTH)]= new FloorTile(xx*16,yy*16,Game.spritesheet.getSprite(64, 16, 16, 16));
							}
					}else if(pixelAtual == 0xff004E00) {
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*16,yy*16,
								Game.spritesheet.getSprite(16, 16, 16, 16));
					}
				
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void RestartGame() {
		Game.entities.clear();
		Player.currentCoins=0;
		Player.maxCoins=0;
		Player.life = 100;
		Game.player = new Player(0,0,16,16,2,Entity.Player_SPRITE_RIGHT[0]);	
		Game.entities.add(Game.player);
		Game.world = new World("/level1.png");
		return;
	}

	//Metodo de colis?o verifica se estou coilidindo com o tile da parede
	public static boolean isFree(int xnext,int ynext) {
		int x1 = xnext/TILE_SIZE;
		int y1 = ynext/TILE_SIZE;
		
		
		int x2 = (xnext+TILE_SIZE-1)/ TILE_SIZE;
		int y2 = ynext/TILE_SIZE;
		
		
		int x3 = xnext/TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1)/ TILE_SIZE;
		
		
		int x4 = (xnext+TILE_SIZE-1)/ TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1)/ TILE_SIZE;
		
		return !((tiles[x1+(y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2+(y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3+(y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4+(y4*World.WIDTH)] instanceof WallTile));
	}
	
	public void render(Graphics g) {
		//Reenderizar apenas o que a camera ver
		//Inicio da reenderiza??o
		int xstart = Camera.x>>4;
		int ystart = Camera.y>>4;
		//Final da reenderiza??o
		int xfinal = xstart + (Game.WIDTH>>4);
		int yfinal = ystart + (Game.HEIGHT>>4);
		for(int xx=xstart; xx<= xfinal;xx++) {
			for(int yy= ystart; yy<= yfinal;yy++) {
				if(xx < 0 || yy < 0 || xx >=WIDTH || yy >=HEIGHT)
					continue;
				Tiles tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);}
			
		}
	}
}

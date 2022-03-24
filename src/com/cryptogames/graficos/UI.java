package com.cryptogames.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.cryptogames.entities.Player;
import com.cryptogames.main.Game;


public class UI {
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(50, 20, 200, 30);
		g.setColor(Color.green);
		g.fillRect(50, 20, (int)((Game.player.life/100) * 200), 30);
		g.setColor(Color.white);
		g.drawRect(50, 20, 200, 30);
		g.setFont(new Font("arial",Font.BOLD,18));
		g.drawString("Coins: " + Player.currentCoins + "/"+ Player.maxCoins, 53, 70);
		
	}
}

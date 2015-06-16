package com.ziyue;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class MyListener extends java.awt.event.MouseAdapter {
	private java.awt.Graphics g;
	private int x = 5, y = 620;
	private int width = 100, height = 10;

	public MyListener(java.awt.Graphics g) {
		this.g = g;
	}

	public void mouseMoved(MouseEvent e) {
		synchronized (this.g) {
			g.setColor(java.awt.Color.DARK_GRAY);
			g.fillRect(x, y, width, height);
			x = e.getX();
			g.setColor(java.awt.Color.green);
			g.fillRect(x, y, width, height);
		}
	}

	public int getX() {
		return x;
	}

}


package com.ziyue;

import java.awt.Color;  
import javax.swing.JOptionPane;

public class MyThread extends Thread {
	private int width = 20, height = 20;
	private int x, y;
	private  java.awt.Graphics g;
	private GameUI UI;
	private boolean isexist = true;
	private MyListener listener;
	private int speed;
	private int x1 = 5, y1 = 5;  //ball movement speed
	private long start, end;
	private long last_time;
	private int response;
	private boolean isFinish = true;
	private boolean isPause = true;
	private java.util.ArrayList<MyThread> list;
	private float value = 320;
	private java.awt.Color color = java.awt.Color.blue;

	public MyThread(java.awt.Graphics g, MyListener listener, GameUI UI, int x,
			int y, int speed) {
		this.g = g;
		this.UI = UI;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.listener = listener;
	}

	public void run() {
		drawOval();
	}

	public void drawOval() {
		
		
		start = System.currentTimeMillis();
		while (isFinish) {
			while (isPause) {
				synchronized(this.g){
				g.setColor(java.awt.Color.DARK_GRAY);
				g.fillOval(x, y, width, height);
				x += x1;
				y += y1;

				getColors();
				g.setColor(color);
				g.fillOval(x, y, width, height);
				}
				int x2 = listener.getX();
				if (x > 580) {
					x1 = -5;
				}
				if (x < 10) {
					x1 = 5;
				}
				if (y < 90) {
					y1 = 5;
				}
				if (y > 595 && x > x2 && x < x2 + 100) {
					y1 = -5;
				}
				if (y > 630) {
					if (isexist) {
						isAgain();
					}
					stopThread();
				}
				try {
					Thread.sleep(speed);
					value -= 0.1;
				} catch (Exception ef) {
					ef.printStackTrace();
				}
				
				end = System.currentTimeMillis();
				last_time = 100 - (end - start) / 1000;
				UI.text_field.setText(last_time + "s");
				UI.pBar.setValue((int) value);
				if (last_time == 0) {
					list = UI.list;
					for (int j = 0; j < list.size(); j++) {
						list.get(j).stopThread();
						list.get(j).fadeOval();

					}
					stopThread();
					showDialog();
				}
			}

		}

	}

	
	public void fadeOval() {
		g.setColor(java.awt.Color.DARK_GRAY);
		g.fillOval(x, y, width, height);
	}

	public void isAgain() {
		isexist = false;
		list = UI.list;
		//System.out.println(list.size());

		for (int j = 0; j < list.size(); j++) {
			
			list.get(j).stopThread();
			list.get(j).fadeOval();

		}
		Object[] options = { "Yes", "No" };
		String command = UI.getCommand();
		response = JOptionPane.showOptionDialog(null,
				"You lost><\nWanna try again?", null, JOptionPane.YES_OPTION,
				JOptionPane.NO_OPTION, null, options, null);

		//System.out.println(response);
		if (response == 0) {
			if (command.equals("Easy") || command.equals("Start")) {
				AgainThread();
				if (list.size() != 0) {
					list.removeAll(list);
					UI.creatBall(20, 1);
				}

			}
			if (command.equals("Medium")) {
				AgainThread();
				if (list.size() != 0) {
					list.removeAll(list);
					UI.creatBall(20, 2);
				}

			}

			if (command.equals("Hard")) {
				AgainThread();
				if (list.size() != 0) {
					list.removeAll(list);
					UI.creatBall(20, 3);
				}
			}
		}

		if (response == -1 || response == 1) {
			list.removeAll(list);
		}

	}

	public void stopThread() {
		isFinish = false;
		isPause = false;

	}

	public void PauseThread() {
		isPause = false;

	}

	public void ContinueThread() {

		isPause = true;

	}

	public void AgainThread() {
		isFinish = true;
		isPause = true;

	}

	public void getColors() {
			color = java.awt.Color.yellow;
	}

	public void showDialog() {

		javax.swing.JOptionPane
				.showInputDialog("Congratulations! You've lasted 100s! \nPlease enter your name.");
	}

}

package com.ziyue;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI extends javax.swing.JFrame {
	private static GameUI UI;
	private java.awt.Graphics g;
	private MyListener ml;
	public javax.swing.JLabel text_field;
	public javax.swing.JProgressBar pBar;
	private String command;
	public java.util.ArrayList<MyThread> list = new java.util.ArrayList<MyThread>();
	private javax.swing.JToggleButton button;

	public static void main(String args[]) {
		UI = new GameUI();
		UI.initUI();
	}

	public void initUI() {
		this.setTitle("PinBall");
		this.setSize(610, 635);
		this.setDefaultCloseOperation(3);
		this.setLocationRelativeTo(null);
		this.setLayout(new java.awt.FlowLayout());
		this.getContentPane().setBackground(java.awt.Color.DARK_GRAY);
		text_field = new javax.swing.JLabel();
		javax.swing.JLabel lable = new javax.swing.JLabel("Time");
		lable.setForeground(java.awt.Color.red);
		text_field.setForeground(java.awt.Color.red);

		pBar = new javax.swing.JProgressBar(0, 330);
		
		button = new javax.swing.JToggleButton();
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setIcon(new javax.swing.ImageIcon("images/Pause.gif"));
		button.setActionCommand("Pause");
		
		java.awt.event.ActionListener button_listener = new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String com = e.getActionCommand();

				if (com.equals("Pause")) {
					button.setMargin(new Insets(0, 0, 0, 0));
					button
							.setIcon(new javax.swing.ImageIcon(
									"images/start.gif"));
					button.setActionCommand("Continue");
					for (int i = 0; i < list.size(); i++) {
						list.get(i).PauseThread();

					}

				}
				if (com.equals("Continue")) {

					button.setMargin(new Insets(0, 0, 0, 0));
					button
							.setIcon(new javax.swing.ImageIcon(
									"images/Pause.gif"));
					button.setActionCommand("Continue");

					for (int i = 0; i < list.size(); i++) {
						list.get(i).ContinueThread();
					}

				}

			}

		};
		button.addActionListener(button_listener);

		this.add(button);
		this.add(lable);
		this.add(pBar);
		this.add(text_field);
		
		javax.swing.JMenuBar bar = creatMenuBar();
		
		this.setJMenuBar(bar);
		
		this.setVisible(true);
	}

	
	public javax.swing.JMenuBar creatMenuBar() {
		javax.swing.JMenuBar bar = new javax.swing.JMenuBar();
		
		javax.swing.JMenu menu_menu = new javax.swing.JMenu("Menu");
		javax.swing.JMenu difficulty_menu = new javax.swing.JMenu("Difficulty");
		javax.swing.JMenu help_menu = new javax.swing.JMenu("Help");
		
		javax.swing.JMenuItem star_item = new javax.swing.JMenuItem("Start");
		javax.swing.JMenuItem exit_item = new javax.swing.JMenuItem("Quit");
		javax.swing.JMenuItem help_item = new javax.swing.JMenuItem("How to play");
		javax.swing.JMenuItem about_item = new javax.swing.JMenuItem("About");
		
		javax.swing.JRadioButtonMenuItem easy_item = new javax.swing.JRadioButtonMenuItem(
				"Eazy");
		javax.swing.JRadioButtonMenuItem middle_item = new javax.swing.JRadioButtonMenuItem(
				"Medium");
		javax.swing.JRadioButtonMenuItem hard_item = new javax.swing.JRadioButtonMenuItem(
				"Hard");
		
		javax.swing.ButtonGroup group = new javax.swing.ButtonGroup();
		
		group.add(easy_item);
		group.add(middle_item);
		group.add(hard_item);
		
		difficulty_menu.add(easy_item);
		difficulty_menu.add(middle_item);
		difficulty_menu.add(hard_item);
		
		ActionListener listener = new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				command = e.getActionCommand();
				
				if (command.equals("Start") && list.size() == 0) {
					creatBall(20, 1);

				}
				
				if (command.equals("Quit")) {
					System.exit(0);
				}

				
				if (command.equals("Easy") && list.size() == 0) {

					creatBall(20, 1);
				}

				
				if (command.equals("Medium") && list.size() == 0) {
					creatBall(20, 2);

				}
				if (command.equals("Hard") && list.size() == 0) {
					creatBall(20, 3);
				}
				if (command.equals("How to play")) {
					javax.swing.JOptionPane.showMessageDialog(null,
							"Try to catch the ball by moving your mouse\nYou can choose the difficulty of the game");
				}
				if (command.equals("About")) {
					javax.swing.JOptionPane
							.showMessageDialog(null,
									"Coded by Ziyue Chen\nJune 2012\nCopy right reserved");
				}
			}
		};
		
		star_item.addActionListener(listener);
		exit_item.addActionListener(listener);
		easy_item.addActionListener(listener);
		middle_item.addActionListener(listener);
		hard_item.addActionListener(listener);
		help_item.addActionListener(listener);
		about_item.addActionListener(listener);

		menu_menu.add(star_item);
		menu_menu.add(exit_item);
		help_menu.add(help_item);
		help_menu.add(about_item);
		
		bar.add(menu_menu);
		bar.add(difficulty_menu);
		bar.add(help_menu);
		
		return bar;
	}

	
	public void creatBall(int speed, int num) {
		java.util.Random ran = new java.util.Random();

		if (ml == null) {
			g = UI.getGraphics();
			ml = new MyListener(g);
			UI.addMouseListener(ml);
			UI.addMouseMotionListener(ml);

		}
		for (int i = 0; i < num; i++) {
			int x = ran.nextInt(600) + 10;  //Random start position 
			int y = ran.nextInt(300) + 100;
			MyThread th = new MyThread(g, ml, UI, x, y, speed);
			list.add(th);
			th.start();
		}

	}

	public String getCommand() {
		return command;
	}

}

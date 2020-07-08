package com.game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import java.util.Scanner;

public class GameOfLife extends JFrame {
	private final Pool world;
	public GameOfLife(int rows, int columns) {
//		创建一个世界
		world = new Pool(rows, columns);
//		设置背景颜色
		world.setBackground(Color.LIGHT_GRAY);
		new Thread(world).start();
		add(world);
	}
	public GameOfLife(int rows, int columns,int size) {
//		创建一个世界
		world = new Pool(rows, columns);
		world.reSize(size);
//		设置背景颜色
		world.setBackground(Color.LIGHT_GRAY);
		new Thread(world,"GameOfLife").start();
		add(world);
	}

//	==================================================================================
//	==================================================================================
	public static void main(String[]args) {
// get name representing the running Java virtual machine.
		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println(name);
// get pid
		String pid = name.split("@")[0];
		System.out.println("Pid is:" + pid);
		startGame();
	}
	public static void startGame(){
		Scanner scanner = new Scanner(System.in);
		System.out.print("请选择游戏启动模式(1:默认启动，150*300的方格大小为5；2：自定义):");
		int flag = scanner.nextInt();
		if(flag == 1){
			gameInit();
		}else {
			System.out.print("行数rows:");
			int rows = scanner.nextInt();
			System.out.print("列数columns：");
			int columns = scanner.nextInt();
			System.out.print("方格大小size:");
			int size = scanner.nextInt();
			gameInit(rows,columns,size);
		}
		scanner.close();
	}

	public static void gameInit(int rows, int columns, int size){
		GameOfLife frame = new GameOfLife(rows, columns,size);
		frameInit(frame);
	}
	/**
	 *功能描述：以默认状态启动程序，150*300的方格大小为 5
	 *@Author Jason
	 *Date 2020-05-17 12:13
	 */
	 public static void gameInit(){
		 GameOfLife frame = new GameOfLife(150, 300);
		 frameInit(frame);
	 }
	 private static void frameInit(GameOfLife frame) {
		 JMenuBar menu = new JMenuBar();
		 frame.setJMenuBar(menu);

//		添加
		 JMenu options = new JMenu("选项");
		 menu.add(options);
		 JMenu changeSpeed = new JMenu("速度");
		 menu.add(changeSpeed);

		 JMenuItem start=options.add("开始演化");
		 start.addActionListener(frame.new StartActionListener());

		 JMenuItem init = options.add("初始态1");
		 init.addActionListener(frame.new initActionListener());

		 JMenuItem init_2 = options.add("初始态2");
		 init_2.addActionListener(frame.new init_2ActionListener());

		 JMenuItem random=options.add("随机分布");
		 random.addActionListener(frame.new RandomActionListener());

		 JMenuItem stop=options.add("停止迭代");
		 stop.addActionListener(frame.new StopActionListener());

		 JMenuItem pause=options.add("暂停");
		 pause.addActionListener(frame.new PauseActionListener());

		 JMenuItem slow=changeSpeed.add("慢");
		 slow.addActionListener(frame.new SlowActionListener());

		 JMenuItem fast=changeSpeed.add("快");
		 fast.addActionListener(frame.new FastActionListener());

		 JMenuItem hyper=changeSpeed.add("相当快");
		 hyper.addActionListener(frame.new HyperActionListener());

		 frame.setLocationRelativeTo(null);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setSize(700,700);
		 frame.setTitle("生命游戏");
		 frame.setVisible(true);
		 frame.setResizable(true);
	 }

	class RandomActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(Color.LIGHT_GRAY);
			world.setRandom();
		}
	}
	class StartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(Color.LIGHT_GRAY);
			world.setShape();
		}
	}
	class StopActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(Color.LIGHT_GRAY);
			world.setStop();
		}
	}
	class PauseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(Color.LIGHT_GRAY);
			world.setPause();
		}
	}
	class SlowActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.changeSpeedSlow();
		}
	}
	class FastActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.changeSpeedFast();
		}
	}
	class HyperActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.changeSpeedHyper();
		}
	}

	private class initActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			world.setBackground(Color.LIGHT_GRAY);
			world.setInit_1();
		}
	}

	private class init_2ActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			world.setBackground(Color.LIGHT_GRAY);
			world.setInit_2();
		}
	}
}


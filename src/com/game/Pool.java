package com.game;

import javax.swing.*;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Random;

public class Pool extends JPanel implements Runnable{

	private static final int DEAD = 0;
	private static final int ALIVE = 1 ;

	private final int rows;
	private final int columns;
	private int speed = 8;
	private int size = 5;
	private static int[][] shape ;
	private static int[][] zero ;

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int[][] getShape(){
		return shape;
	}

	public int get_Size() {
		return size;
	}

	public int getSpeed() {
		return speed;
	}

	private static int[][] pauseshape ;

	private static int[][] currentGeneration;
	private static int[][] nextGeneration;

	public int[][] getNextGeneration() {
		return nextGeneration;
	}

	public int[][] getCurrentGeneration() {
		return currentGeneration;
	}


	public void setCurrentGeneration(int[][] currentGeneration) {
		Pool.currentGeneration = currentGeneration;
	}


	private volatile boolean isChanging = false;

	public Pool(int rows, int columns) {
		if(rows < 10 || columns < 40){
			rows = 150;
			columns = 300;
			System.out.println("你输入的行列数无法正常适用初始态2，设置初始化值为150行*300列");
		}
		this.rows = rows;
		this.columns=columns;
		shape = new int[rows][columns] ;
		zero = new int[rows][columns] ;
		pauseshape = new int[rows][columns] ;

		currentGeneration = new int[rows][columns];
		nextGeneration = new int[rows][columns];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
//				generation1[i][j] = DEAD;
//				generation2[i][j]= ALIVE;
				currentGeneration[i][j] = DEAD;
				nextGeneration[i][j] = ALIVE;
			}
		}
//		currentGeneration = generation1;
//		nextGeneration = generation2;
	}

	public void reSize(int size) {
		if(size <= 0){
		    System.out.println("size的值出现" +
					"非正数，已经设置为默认值为5");
		    size = 5;
		}else if(size >= 20){
		    System.out.println("size的值过大，默认设置为5");
		    size = 5;
		}
		this.size = size;
	}


	public void transfrom(int[][] generation, int[][] pauseshape) {
		for(int i = 0;i < rows;i++) {
			if (columns >= 0) {
				System.arraycopy(generation[i], 0, pauseshape[i], 0, columns);
			}
		}
	}
	/*
	重写run方法，线程执行方案
	 */
	public void run() {
		long start = System.currentTimeMillis();
//		10分钟后停止演化，游戏停止
		while ((System.currentTimeMillis() - start) <= 10 * 60 * 1000) {
			synchronized (this) {
				while (isChanging) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				sleep(speed);
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						evolve(i, j);
					}
				}

//				MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
//				MemoryUsage usage = memorymbean.getHeapMemoryUsage();
//				long used = usage.getUsed();
//				if(used > 12000000){
//				    System.gc();
//				}

				int[][] temp = currentGeneration;
				currentGeneration = nextGeneration;
				nextGeneration = temp;
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						nextGeneration[i][j] = DEAD;
					}
				}
				transfrom(currentGeneration, pauseshape);
				repaint();
			}
		}
	}


	public void changeSpeedSlow() {
		speed=80;
	}
	public void changeSpeedFast() {
		speed=30;
	}
	public void changeSpeedHyper() {
		speed=1;
	}
//	画窗口
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				if(currentGeneration[i][j]== ALIVE) {
					g.fillRect(j*size, i*size, size, size);
				} else {
					g.drawRect(j*size, i*size, size, size);
				}
			}
		}
	}
	public void setShape() {
		setShape(shape);
	}
//	随机分布
	public void setRandom() {
		Random a = new Random();
		for(int i = 0;i < rows; i++) {
			for(int j = 0;j < columns;j++) {
//				返回伪随机的，均匀分布 int值介于0（含）和指定值（不包括），从该随机数生成器的序列绘制。
				shape[i][j] = Math.abs(a.nextInt(2));
				pauseshape[i][j] = shape[i][j];
			}
		}
		setShapetemp(shape);
	}
	public void setInit_2(){
		shape[1][25] = pauseshape[1][25] = ALIVE;
		shape[2][25] = pauseshape[2][25] = ALIVE;
		shape[2][23] = pauseshape[2][23] = ALIVE;
		shape[3][13] = pauseshape[3][13] = ALIVE;
		shape[3][14] = pauseshape[3][14] = ALIVE;
		shape[3][21] = pauseshape[3][21] = ALIVE;
		shape[3][22] = pauseshape[3][22] = ALIVE;
		shape[3][35] = pauseshape[3][35] = ALIVE;
		shape[3][36] = pauseshape[3][36] = ALIVE;
		shape[4][12] = pauseshape[4][12] = ALIVE;
		shape[4][16] = pauseshape[4][16] = ALIVE;
		shape[4][21] = pauseshape[4][21] = ALIVE;
		shape[4][22] = pauseshape[4][22] = ALIVE;
		shape[4][35] = pauseshape[4][35] = ALIVE;
		shape[4][36] = pauseshape[4][36] = ALIVE;
		shape[5][1] = pauseshape[5][1] = ALIVE;
		shape[5][2] = pauseshape[5][2] = ALIVE;
		shape[5][11] = pauseshape[5][11] = ALIVE;
		shape[5][17] = pauseshape[5][17] = ALIVE;
		shape[5][21] = pauseshape[5][21] = ALIVE;
		shape[5][22] = pauseshape[5][22] = ALIVE;
		shape[6][1] = pauseshape[6][1] = ALIVE;
		shape[6][2] = pauseshape[6][2] = ALIVE;
		shape[6][11] = pauseshape[6][11] = ALIVE;
		shape[6][15] = pauseshape[6][15] = ALIVE;
		shape[6][17] = pauseshape[6][17] = ALIVE;
		shape[6][18] = pauseshape[6][18] = ALIVE;
		shape[6][23] = pauseshape[6][23] = ALIVE;
		shape[6][25] = pauseshape[6][25] = ALIVE;
		shape[7][11] = pauseshape[7][11] = ALIVE;
		shape[7][17] = pauseshape[7][17] = ALIVE;
		shape[7][25] = pauseshape[7][25] = ALIVE;
		shape[8][12] = pauseshape[8][12] = ALIVE;
		shape[8][16] = pauseshape[8][16] = ALIVE;
		shape[9][13] = pauseshape[9][13] = ALIVE;
		shape[9][14] = pauseshape[9][14] = ALIVE;
		setShapetemp(shape);
	}

	public void setInit_1(){
		for(int i = 0;i < rows; i++) {
			for(int j = 0;j < columns;j++) {
				if((i == 4 && j == 4)||(i == 5 && j == 5)||(i == 5 && j == 6)||
						(i == 6 && j == 4)||(i == 6 && j == 5)){
				    shape[i][j] = ALIVE;
				}
				else {
					shape[i][j] = DEAD;
				}
				pauseshape[i][j] = shape[i][j] ;
			}
		}
		setShapetemp(shape);
	}
//	初始化数组为0；
	public void setZero() {
		for(int i = 0;i < rows;i++) {
			for(int j = 0;j < columns;j++) {
				zero[i][j] = DEAD;
			}
		}
	}
	/*停止，恢复初始状态*/
	public void setStop() {
		setZero();
		shape = zero;
		setShape(shape);
		pauseshape = shape;
	}
	/*****暂停******/
	public void setPause() {
		shape = pauseshape;
		setShapetemp(pauseshape);
	}
	/******交换*******/
	public void setShapetemp(int [][]shape) {
		isChanging = true;
		synchronized (this) {
			for (int i = 0 ; i < rows ; i ++ ) {
				for (int j = 0 ; j < columns ; j ++ ) {
					currentGeneration[i][j] = (shape[i][j] == ALIVE) ? ALIVE : DEAD;
				}
			}
			repaint();
		}
	}
	private void setShape(int [][]shape) {
		isChanging = true;
		synchronized(this) {
			for (int i = 0 ; i < rows ; i ++ ) {
				for (int j = 0 ; j < columns ; j ++ ) {
					currentGeneration[i][j] = (shape[i][j] == ALIVE) ? ALIVE : DEAD;
				}
			}
			isChanging=false;
			this.notifyAll();
		}
	}

	public void evolve(int x,int y) {
		int activeSurroundingCell = 0;

		for (int i = x-1 ; i <= x + 1 ; i ++ ) {
			for (int j = y - 1 ; j <= y + 1 ; j ++ ) {
				if(isVaildCell(i,j)&&(currentGeneration[i][j] == ALIVE)&&!(i==x&&j==y)){
				    activeSurroundingCell ++ ;
				}
			}
		}
//		当前存活
		if(currentGeneration[x][y] == ALIVE){
			if(activeSurroundingCell==3) {//外围存活数为3，保持
				nextGeneration[x][y]= currentGeneration[x][y];
			} else if(activeSurroundingCell==2) {//外围存活数为2，保存
				nextGeneration[x][y]=currentGeneration[x][y];
			} else {//死亡
				nextGeneration[x][y]= DEAD;
			}
		}else {
			if(activeSurroundingCell == 3){//死的状态，为3时候复活
			    nextGeneration[x][y] = ALIVE;
			}
			else {
				nextGeneration[x][y] = DEAD;
			}
		}
	}


	public boolean isVaildCell(int x,int y) {
		return (x >= 0) && (x < rows) && (y >= 0) && (y < columns);
	}
	private void sleep(int x) {
		try {
			Thread.sleep(8*x);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
	}
}
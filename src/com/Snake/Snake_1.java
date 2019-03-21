package com.Snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;

import javax.swing.*;

public class Snake_1 extends JFrame {

	MyPanel myPanel = null;
	int areaX, areaY;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Snake_1 snake_1 = new Snake_1(500, 300);
	}

	public Snake_1(int areaX, int areaY) {
		myPanel = new MyPanel(4, 5, areaX, areaY);

		this.add(myPanel);

		// ��Ӽ�������
		this.addKeyListener(myPanel);
		// ����С(���Χ)
		this.setSize(600, 400);
		this.setTitle("˿�ڿ�~");
		this.setLocation(700, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

class MyPanel extends JPanel implements KeyListener {

	// �Ѷ�(���Ⱥ��ٶ�)
	int hard;
	int speed;
	// ����С(���Χ)
	int areaX, areaY;
	Random random = new Random();
	// ͷ����β��
	int head, tail;

	// �����λ��
	Map<Integer, Integer> mapX = new HashMap<>();
	Map<Integer, Integer> mapY = new HashMap<>();
	ArrayList<Integer> arraycenter = new ArrayList<>();

	// ʳ���λ��
	Map<String, Integer> mapFood = new HashMap<>();
	// ��¼ʳ���λ��(���ڲ���β����)
	ArrayList<Integer> arrayFood_X = new ArrayList<>();
	ArrayList<Integer> arrayFood_Y = new ArrayList<>();

	// �ƶ�����
	String direction = "right";
	boolean directionFlag = true;

	// ��ͣ/��ʼ״̬
	String flag = "start";

	public MyPanel(int hard, int speed, int areaX, int areaY) {

		this.hard = hard;
		this.areaX = areaX;
		this.areaY = areaY;

		// �Ѷȷ�Χ
		if (hard < 4) {
			this.hard = 4;
		}

		for (int z = hard - 1; z >= 0; z--) {
			mapX.put(hard - z, (hard - z) * 10);
			mapY.put(hard - z, 10);
		}

		FoodLocation(areaX, areaY);

		Run(speed);
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		AddBody(graphics);
		AddFood(graphics);
	}

	// ������
	public void AddBody(Graphics graphics) {

		getHeadAndTail();
		for (int q = tail; q <= head; q++) {
			// graphics.drawString(String.valueOf(q), mapX.get(q), mapY.get(q));
			// System.out.println(mapX.get(q) + "----" + mapY.get(q));
			graphics.fillOval(mapX.get(q), mapY.get(q), 10, 10);
		}
	}

	// ʳ��
	public void AddFood(Graphics graphics) {
		// System.out.println("ʳ��λ��" + mapFood.get("x") + "---" + mapFood.get("y"));
		graphics.setColor(Color.red);
		graphics.fillOval(mapFood.get("x"), mapFood.get("y"), 10, 10);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		for (Map.Entry<Integer, Integer> entry : mapX.entrySet()) {
			arraycenter.add(entry.getKey());
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			for (int a = 0; a < arraycenter.size(); a++) {
				if (String.valueOf(mapX.get(arraycenter.get(a))).equals(String.valueOf(mapX.get(head)))
						&& String.valueOf(mapY.get(arraycenter.get(a))).equals(String.valueOf(mapY.get(head) + 10))) {
					directionFlag = false;
				} else {
					directionFlag = true;
				}
				if (directionFlag == false) {
					return;
				}
			}
			if (directionFlag == true) {

				direction = "down";
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			for (int a = 0; a < arraycenter.size(); a++) {
				if (String.valueOf(mapX.get(arraycenter.get(a))).equals(String.valueOf(mapX.get(head)))
						&& String.valueOf(mapY.get(arraycenter.get(a))).equals(String.valueOf(mapY.get(head) - 10))) {
					directionFlag = false;
				} else {
					directionFlag = true;
				}
				if (directionFlag == false) {
					return;
				}
			}
			if (directionFlag == true) {

				direction = "up";
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			for (int a = 0; a < arraycenter.size(); a++) {
				if (String.valueOf(mapY.get(arraycenter.get(a))).equals(String.valueOf(mapY.get(head)))
						&& String.valueOf(mapX.get(arraycenter.get(a))).equals(String.valueOf(mapX.get(head) - 10))) {
					directionFlag = false;
				} else {
					directionFlag = true;
				}
				if (directionFlag == false) {
					return;
				}
			}
			if (directionFlag == true) {

				direction = "left";
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			for (int a = 0; a < arraycenter.size(); a++) {
				if (String.valueOf(mapY.get(arraycenter.get(a))).equals(String.valueOf(mapY.get(head)))
						&& String.valueOf(mapX.get(arraycenter.get(a))).equals(String.valueOf(mapX.get(head) + 10))) {
					directionFlag = false;
				} else {
					directionFlag = true;
				}
				if (directionFlag == false) {
					return;
				}
			}
			if (directionFlag == true) {

				direction = "right";
			}
		}
	}

	// �ƶ�
	public void JudgeDirection() {
		getHeadAndTail();
		switch (direction) {
		case "up":
			directionFlag = true;
			mapX.put(head + 1, mapX.get(head));
			mapY.put(head + 1, mapY.get(head) - 10);
			mapX.remove(tail);
			mapY.remove(tail);
			break;
		case "down":

			directionFlag = true;
			mapX.put(head + 1, mapX.get(head));
			mapY.put(head + 1, mapY.get(head) + 10);
			mapX.remove(tail);
			mapY.remove(tail);
			break;
		case "left":
			directionFlag = true;
			mapY.put(head + 1, mapY.get(head));
			mapX.put(head + 1, mapX.get(head) - 10);
			mapX.remove(tail);
			mapY.remove(tail);
			break;
		case "right":

			directionFlag = true;
			mapY.put(head + 1, mapY.get(head));
			mapX.put(head + 1, mapX.get(head) + 10);
			mapX.remove(tail);
			mapY.remove(tail);
			break;
		default:
			break;
		}
	}

	/**
	 * ��ʱ
	 */
	public void Run(int speed) {
		// ��λ: ����
		final long timeInterval = 100;
		Runnable runnable = new Runnable() {
			public void run() {
				while (flag.equals("start")) {
					// ------- code for task to run
					// System.out.println("Hello !!");
					JudgeDirection();
					Judge();
					repaint();

					// ------- ends here
					try {
						Thread.sleep(timeInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}

	/**
	 * ��ȡ��ͷ����β
	 */
	public void getHeadAndTail() {
		// ����,��ȡβ����ͷ��
		arraycenter.clear();
		for (Map.Entry<Integer, Integer> entry : mapX.entrySet()) {
			arraycenter.add(entry.getKey());
		}
		head = arraycenter.get(0);
		tail = arraycenter.get(0);
		for (int m = 1; m < arraycenter.size(); m++) {
			if (arraycenter.get(m) > head) {
				head = arraycenter.get(m);
			}
			if (arraycenter.get(m) < tail) {
				tail = arraycenter.get(m);
			}
		}
	}

	/**
	 * ʳ��
	 */
	public void FoodLocation(int areaX, int areaY) {
		mapFood.clear();
		boolean flag = false;
		int x = random.nextInt(10);
		int y = random.nextInt(10);
		int z = random.nextInt(10);
		if (z <= 5) {
			x = x * 10;
			y = y * 10;
		} else if (z > 5) {
			x = x * 100;
			y = y * 100;
			if (x >= areaX) {
				x = areaX;
			}
			if (y >= areaY) {
				y = areaY;
			}
		}
		// ���ܳ������ߵ�����
		for (Map.Entry<Integer, Integer> entry : mapX.entrySet()) {
			arraycenter.add(entry.getKey());
		}

		for (int q = 0; q < arraycenter.size(); q++) {
			if (String.valueOf(mapX.get(arraycenter.get(q))).equals(x)
					&& String.valueOf(mapY.get(arraycenter.get(q))).equals(y)) {
				flag = false;
				FoodLocation(areaX, areaY);
				break;
			} else {
				flag = true;
			}
		}

		if (flag == true) {

			mapFood.put("x", x);
			mapFood.put("y", y);

			arrayFood_X.add(x);
			arrayFood_Y.add(y);
		}
	}

	/**
	 * �Ե�������
	 */
	public void Judge() {
		getHeadAndTail();
		if (String.valueOf(mapFood.get("x")).equals(String.valueOf(mapX.get(head)))
				&& String.valueOf(mapFood.get("y")).equals(String.valueOf(mapY.get(head)))) {

			FoodLocation(areaX, areaY);
		}

		for (int m = 0; m < arrayFood_X.size(); m++) {
			if (String.valueOf(arrayFood_X.get(m)).equals(String.valueOf(mapX.get(tail)))
					&& String.valueOf(arrayFood_Y.get(m)).equals(String.valueOf(mapY.get(tail)))) {
				if (!String.valueOf(m).equals(String.valueOf(tail))) {
					mapX.put(tail - 1, arrayFood_X.get(m));
					mapY.put(tail - 1, arrayFood_Y.get(m));
					arrayFood_X.remove(0);
					arrayFood_Y.remove(0);
				}
			}
		}

		// �ж��Ƿ�ײǽ��
		if (String.valueOf(mapX.get(head)).equals(String.valueOf(600))
				|| String.valueOf(mapY.get(head)).equals(String.valueOf(400)) || mapX.get(head) < 0
				|| mapY.get(head) < 0
		// || String.valueOf(mapX.get(head)).equals(String.valueOf(0))
		// || String.valueOf(mapY.get(head)).equals(String.valueOf(0))
		) {
			flag = "stop";
		}

		// �ж�ҧ���Լ���
		for (Map.Entry<Integer, Integer> entry : mapX.entrySet()) {
			arraycenter.add(entry.getKey());
		}

		for (int z = 0; z < arraycenter.size(); z++) {
			if (String.valueOf(mapX.get(arraycenter.get(z))).equals(String.valueOf(mapX.get(head)))
					&& String.valueOf(mapY.get(arraycenter.get(z))).equals(String.valueOf(mapY.get(head)))) {
				if (!String.valueOf(arraycenter.get(z)).equals(String.valueOf(head))) {
					flag = "stop";
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}

class Body {
	int x, y, num;

	public Body(int x, int y, int num) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.num = num;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}

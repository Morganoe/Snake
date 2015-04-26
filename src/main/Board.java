package main;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.Point;

public class Board {
	private static int size = 20;
	private int[][] board;
	private Point currentObjectivePoint;
	private Random pointGenerator;

	public Board() {
		board = new int[size][size];
		pointGenerator = new Random();
		currentObjectivePoint = new Point(pointGenerator.nextInt(size),
				pointGenerator.nextInt(size));
		generateNewObjectivePoint();

	}

	public Board(int size) {
		Board.size = size;
		board = new int[size][size];
		pointGenerator = new Random();
		generateNewObjectivePoint();
	}

	public boolean isPopulatedAt(Point point) {
		if (board[point.getX()][point.getY()] == 1) {
			return true;
		}
		return false;
	}

	public void depopulatePoint(Point point) {
		board[point.getX()][point.getY()] = 0;
	}

	public void populatePoint(Point point) {
		board[point.getX()][point.getY()] = 1;
	}

	public void populateBoard(ArrayList<Point> body) {
		for (Point point : body) {
			populatePoint(point);
		}
	}

	public void generateNewObjectivePoint() {
		while (isPopulatedAt(currentObjectivePoint)) {
			currentObjectivePoint.setLocation(pointGenerator.nextInt(size),
					pointGenerator.nextInt(size));
		}
		populatePoint(currentObjectivePoint);
	}

	public static int getSize() {
		return size;
	}

	public Point getCurrentObjectivePoint() {
		return currentObjectivePoint;
	}

}

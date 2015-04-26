package main;

import java.util.ArrayList;
import org.lwjgl.util.Point;

public class Snake {

	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	private final int SIZE = 6;
	private final int GROW_AMOUNT = 2;

	private boolean growing;
	private Point startingPoint;
	private ArrayList<Point> body;
	private int currentSize;
	private Direction currentDirection;

	public Snake() {
		body = new ArrayList<Point>();
		startingPoint = new Point(Board.getSize() / 2, Board.getSize() / 2);
		currentSize = SIZE;
		currentDirection = Direction.UP;
		growing = false;
		generateBody();
	}

	private void generateBody() {
		Point currentPlacePoint = startingPoint;
		for (int i = 0; i < currentSize; i++) {
			body.add(currentPlacePoint);
			currentPlacePoint = new Point(currentPlacePoint.getX() + 1,
					currentPlacePoint.getY());
		}
	}

	public Point moveForward(int tick, int tickRate) {
		Point currPoint = body.get(0);
		if (currentDirection == Direction.UP) {
			body.add(0, new Point(currPoint.getX() - 1, currPoint.getY()));
		} else if (currentDirection == Direction.DOWN) {
			body.add(0, new Point(currPoint.getX() + 1, currPoint.getY()));
		} else if (currentDirection == Direction.LEFT) {
			body.add(0, new Point(currPoint.getX(), currPoint.getY() - 1));
		} else if (currentDirection == Direction.RIGHT) {
			body.add(0, new Point(currPoint.getX(), currPoint.getY() + 1));
		}

		Point tail = body.get(currentSize);
		if (growing) {
			for(int i = currentSize; i < currentSize + GROW_AMOUNT; i++){
				body.add(new Point(tail.getX(), tail.getY()));
			}
			growing = false;
			currentSize += GROW_AMOUNT;
		}
		body.remove(body.get(currentSize));

		return body.get(0);
	}

	public void grow() {
		growing = true;
	}

	public void setDirection(Direction direction) {
		currentDirection = direction;
	}

	public ArrayList<Point> getBody() {
		return body;
	}

	public int getSize() {
		return currentSize;
	}

	public Direction getDirection() {
		return currentDirection;
	}

	public boolean isGrowing() {
		return growing;
	}

}

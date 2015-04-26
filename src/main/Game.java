package main;

import org.lwjgl.util.Point;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	private static final int WIDTH = 720;
	private static final int HEIGHT = 720;
	private static final int TICK_RATE = 5;

	private int tick;
	private static int score;
	private Snake snake;
	private Board board;

	public Game(String title) {
		super(title);
		score = 0;
		tick = 0;
		board = new Board();
		snake = new Snake();
		board.populateBoard(snake.getBody());
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		int cellSize = WIDTH / Board.getSize();
		int xOffs = 0;
		int yOffs = 0;
		g.setColor(Color.white);
		for (int x = 0; x < Board.getSize(); x++) {
			for (int y = 0; y < Board.getSize(); y++) {
				if (board.isPopulatedAt(new Point(x, y))) {
					g.fillRect(xOffs, yOffs, cellSize, cellSize);
				} else {
					//g.drawRect(xOffs, yOffs, cellSize, cellSize);
				}
				xOffs += cellSize;
			}
			xOffs = 0;
			yOffs += cellSize;
		}
		
		g.drawString("Score: " + score, 10, 10);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input input = container.getInput();

		if (input.isKeyDown(Input.KEY_W)) {
			if (snake.getDirection() != Snake.Direction.DOWN) {
				snake.setDirection(Snake.Direction.UP);
			}
		} else if (input.isKeyDown(Input.KEY_S)) {
			if (snake.getDirection() != Snake.Direction.UP) {
				snake.setDirection(Snake.Direction.DOWN);
			}
		} else if (input.isKeyDown(Input.KEY_A)) {
			if (snake.getDirection() != Snake.Direction.RIGHT) {
				snake.setDirection(Snake.Direction.LEFT);
			}
		} else if (input.isKeyDown(Input.KEY_D)) {
			if (snake.getDirection() != Snake.Direction.LEFT) {
				snake.setDirection(Snake.Direction.RIGHT);
			}
		}

		if (tick % TICK_RATE == 0) {
			Snake.Direction snakeDirection = snake.getDirection();
			Point snakeHead = snake.getBody().get(0);
			if (snakeDirection == Snake.Direction.UP
					&& snakeHead.getX() - 1 < 0) {
				gameLose();
			} else if (snakeDirection == Snake.Direction.DOWN
					&& snakeHead.getX() + 1 >= Board.getSize()) {
				gameLose();
			} else if (snakeDirection == Snake.Direction.RIGHT
					&& snakeHead.getY() + 1 >= Board.getSize()) {
				gameLose();
			} else if (snakeDirection == Snake.Direction.LEFT
					&& snakeHead.getY() - 1 < 0) {
				gameLose();
			}
			
			for(int i = 1; i < snake.getSize(); i++){
				if(snakeHead.getX() == snake.getBody().get(i).getX() &&
						snakeHead.getY() == snake.getBody().get(i).getY()){
					gameLose();
				}
			}

			Point currObjPoint = board.getCurrentObjectivePoint();
			if (snakeHead.getX() == currObjPoint.getX()
					&& snakeHead.getY() == currObjPoint.getY()) {
				Game.score++;
				snake.grow();
				board.generateNewObjectivePoint();
			}

			Point snakeTail = snake.getBody().get(snake.getSize() - 1);
			Point newHead = snake.moveForward(tick, TICK_RATE);
			if (!snake.isGrowing()) {
				board.depopulatePoint(snakeTail);
			}
			board.populatePoint(newHead);
		}
		tick++;
	}

	private final void gameLose() {
		System.exit(0);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc = new AppGameContainer(new Game("Snake"));
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setShowFPS(false);
			appgc.setTargetFrameRate(60);
			appgc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}

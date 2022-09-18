package projectWithGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class RobotController {
	Robot robot = new Robot();

	@FXML private Button buttonDropOff;
	@FXML private Button buttonPickUp;
	@FXML private Button buttonMoveUp;
	@FXML private Button buttonMoveDown;
	@FXML private Button buttonMoveLeft;
	@FXML private Button buttonMoveRight;
	@FXML private GridPane gridPane;
	
	@FXML void pickUp(ActionEvent event) {
		robot.pickUp();
	}

	@FXML void dropOff(ActionEvent event) {
		robot.dropOff();
	}
	
	/* For moves, assign payloadAtLocation at location moving from,
	   move robot on grid, save payloadAtLocation to destination,
	   then link grid so only R shows on gridPane */

	@FXML void moveUp(ActionEvent event) {
		robot.moveUp();
		linkGridPane();
	}

	@FXML void moveLeft(ActionEvent event) {
		robot.moveLeft();
		linkGridPane();
	}

	@FXML void moveRight(ActionEvent event) {
		robot.moveRight();
		linkGridPane();
	}

	@FXML void moveDown(ActionEvent event) {
		robot.moveDown();
		linkGridPane();
	}
	
	@FXML void linkGridPane() {
		// Linking gridPane with char array
		gridPane.setGridLinesVisible(true);
		// Save setGridLinesVisible before clear
		Node node = gridPane.getChildren().get(0);
		gridPane.getChildren().clear();
		// Add back setGridLinesVisible
		gridPane.getChildren().add(node);
		for(int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				Label label = new Label();
				label.setFont(new Font(15));
				label.setText(String.valueOf(robot.getElement(i, j)));
				gridPane.add(label, i, j);
				GridPane.setHalignment(label, HPos.CENTER);
			}
		}
	}
	
	@FXML void initialize() {
		linkGridPane();
	}
}

package projectWithGUI;

import java.security.SecureRandom;

public class Robot {
	private int x;
	private int y;
	private char payload;
	private char payloadAtDestination;
	private final int row = 25;
	private final int column = 25;
	private char[][] grid;
	private static final SecureRandom random = new SecureRandom();
	
	public Robot(){
		payload = ' ';
		// Initialize grid at ' '
		grid = new char[row][column];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < column; j++) {
				grid[i][j] = ' ';
			}
		}
		// Add A-J cargo at random places except on robot starting location
		for(int i = 0; i < 10; i++) {
			int xRandom = random.nextInt(row);
			int yRandom = random.nextInt(column);
			if(xRandom == 12 && yRandom == 24) {
				i--;
				continue;
			}
			grid[xRandom][yRandom] = (char) ('A' + i);
		}
		// Set robot to starting location
		x = 12;
		y = 24;
		grid[x][y] = 'R';
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int robotX) {
		this.x = robotX;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int robotY) {
		this.y = robotY;
	}
	
	public char getPayload() {
		return payload;
	}
	
	public void setPayload(char payload) {
		this.payload = payload;
	}
	
	public char getPayloadAtDestination() {
		return payloadAtDestination;
	}
	
	public void setPayloadAtDestination(char payloadAtDestination) {
		this.payloadAtDestination = payloadAtDestination;
	}
	
	public char getElement(int row, int column) {
		// Return element of grid for gridPane
		return grid[row][column];
	}
	
	/* Change pay to payloadAtDestination
	 * and payloadAtDestination to ' '
	 * in order to leave ' ' when moving again*/
	public void pickUp() {
		if(payload == ' ') {
			setPayload(payloadAtDestination);;
			setPayloadAtDestination(' ');;
		}
	}
	/* Change payloadAtDestination to payload
	 * in order to leave payload when moving
	 	 and payload = ' ' to empty payload*/
	public void dropOff() {
		if(payloadAtDestination == ' ') {
			setPayloadAtDestination(payload);
			setPayload(' ');
		}
	}
	
	// Move methods keeping track of current char at robot location
	public void moveRight() {
		if(getX() < column - 1) {
  		grid[x][y] = payloadAtDestination;
  		setX(x + 1);
  		setPayloadAtDestination(grid[x][y]);
  		grid[x][y] = 'R';
		}
	}
	
	public void moveLeft() {
		if(getX() > 0) {
  		grid[x][y] = payloadAtDestination;
  		setX(x - 1);
  		setPayloadAtDestination(grid[x][y]);
  		grid[x][y] = 'R';
		}
	}
	
	public void moveUp() {
		if(getY() > 0) {
  		grid[x][y] = payloadAtDestination;
  		setY(y - 1);
  		setPayloadAtDestination(grid[x][y]);
  		grid[x][y] = 'R';
		}
	}
	
	public void moveDown() {
		if(getY() < row - 1) {
  		grid[x][y] = payloadAtDestination;
  		setY(y + 1);
  		setPayloadAtDestination(grid[x][y]);
  		grid[x][y] = 'R';
		}
	}
}

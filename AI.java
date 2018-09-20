/**
 * SOS Game AI
 * 
 *
 * @version May 4 2016 ----CarefulPlacements need revision. Check for empty
 *          spaces. Win Checking Works and STrap Works -May 4
 * 
 * @version May 5 2016---- Made a more random way to place letters and fixed
 *          some bugs in condition checking
 */
public class AI {

	private int bestX, bestY;
	private char bestChar;

	private int points = 0;
	private int boarder = 3;

	private int lastX;
	private int lastY;
	private char lastChar;

	/**
	 * Normal AI that uses S trap
	 * 
	 * @param grid
	 *            the game board in a 2D array
	 * @return true if a point was scored and false if no point was scored
	 */
	public boolean myTurn(char[][] grid) {

		// If it can find possible point
		if (FindWin(grid)) {
			points++;
			System.out.println(lastX + "" + lastY + "" + lastChar);
			displayGrid(grid);
			System.out.println("POINT%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%WEST SIDE$$");

			return true;
		}
		// CHecks to see if it can place an S
		else if (!sTrap(grid)) {
			// System.out.println("RANDOM");
			totallyRandom(grid);
		}
		displayGrid(grid);
		return false;
	}


	/**
	 * Check to see if there is a possible win/Point
	 * 
	 * @return true if there is a way to get a point, false if there is not
	 */
	public boolean FindWin(char[][] grid) {

		// Go through all spaces in the grid
		for (int y = boarder; y <= grid.length - boarder; y++) {

			for (int x = boarder; x <= grid[y].length - boarder; x++) {

				if (grid[y][x] == '-') {
					// Check for win if O is placed

					// Check Vertically
					if (grid[y + 1][x] == 'S' && grid[y - 1][x] == 'S') {
						playTurn(grid, x, y, 'O');

						return true;
					}
					// Check horizontally
					else if (grid[y][x + 1] == 'S' && grid[y][x - 1] == 'S') {
						playTurn(grid, x, y, 'O');

						return true;
					}
					// Check Diagonally bottom Left to top right
					else if (grid[y + 1][x - 1] == 'S' && grid[y - 1][x + 1] == 'S') {
						playTurn(grid, x, y, 'O');

						return true;
					}
					// Check Diagonally Top Left to bottom Right

					else if (grid[y - 1][x - 1] == 'S' && grid[y + 1][x + 1] == 'S') {
						playTurn(grid, x, y, 'O');

						return true;
					}

					// -----------------------------------------------
					// Check for win with S placementS

					// Check going up
					if (grid[y - 1][x] == 'O' && grid[y - 2][x] == 'S') {
						playTurn(grid, x, y, 'S');

						return true;
					}
					// Check going down
					else if (grid[y + 1][x] == 'O' && grid[y + 2][x] == 'S') {
						playTurn(grid, x, y, 'S');

						return true;
					}
					// Check going Left
					else if (grid[y][x - 1] == 'O' && grid[y][x - 2] == 'S') {
						playTurn(grid, x, y, 'S');

						return true;
					}
					// Check going Right
					else if (grid[y][x + 1] == 'O' && grid[y][x + 2] == 'S') {
						playTurn(grid, x, y, 'S');

						return true;
					}
					// Check Diagonally down Left
					else if (grid[y + 1][x - 1] == 'O' && grid[y + 2][x - 2] == 'S') {
						playTurn(grid, x, y, 'S');

						return true;
					}
					// Check Diagonally Down to the right
					else if (grid[y + 1][x + 1] == 'O' && grid[y + 2][x + 2] == 'S') {
						playTurn(grid, x, y, 'S');

						return true;
					}
					// Check Diagonally Up Left

					else if (grid[y - 1][x - 1] == 'O' && grid[y - 2][x - 2] == 'S') {
						playTurn(grid, x, y, 'S');

						return true;
					}
					// Check Diagonally Up Right

					else if (grid[y - 1][x + 1] == 'O' && grid[y - 2][x + 2] == 'S') {
						playTurn(grid, x, y, 'S');

						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Finds an S and sets up the trap To set up trap, place another S 2 spaces
	 * away from an S in any direction
	 */
	public boolean sTrap(char[][] grid) {

		boolean trapSet = false;

		for (int y = boarder; y < grid.length - boarder - 3; y++) {
			for (int x = boarder; x < grid[y].length - boarder - 3; x++) {
				// Check if S 2 spaces above

				if (grid[y][x] == '-') {
					if (grid[y - 3][x] == 'S' && carefulSPlacement(grid, x, y)) {
						playTurn(grid, x, y, 'S');
						trapSet = true;
						break;

					}
					// Check Diagonally up Right
					else if (grid[y - 3][x + 3] == 'S' && carefulSPlacement(grid, x, y)) {
						playTurn(grid, x, y, 'S');
						trapSet = true;
						break;
					}

					// Check diagonally down left
					else if (grid[y + 2][x - 3] == 'S' && carefulSPlacement(grid, x, y)) {
						playTurn(grid, x, y, 'S');
						trapSet = true;
						break;
					}

					// Check Right
					else if (grid[y + 3][x] == 'S' && carefulSPlacement(grid, x, y)) {
						playTurn(grid, x, y, 'S');
						trapSet = true;
						break;
					}

					// Check diagonally down Right
					else if (grid[y + 3][x + 3] == 'S' && carefulSPlacement(grid, x, y)) {
						playTurn(grid, x, y, 'S');
						trapSet = true;
						break;
					}

					// Check if S below
					else if (grid[y + 3][x] == 'S' && carefulSPlacement(grid, x, y)) {
						playTurn(grid, x, y, 'S');
						trapSet = true;
						break;
					}
					// Check Left
					else if (grid[y][x - 3] == 'S' && carefulSPlacement(grid, x, y)) {
						playTurn(grid, x, y, 'S');
						trapSet = true;
						break;
					}

					// Check diagonally Up Left
					else if (grid[y - 3][x - 3] == 'S' && carefulSPlacement(grid, x, y)) {
						playTurn(grid, x, y, 'S');
						trapSet = true;
						break;
					}

				}
			}
			if (trapSet)
				break;
		}

		return trapSet;
	}

	/**
	 * Checks to make sure not putting S next to O that would give the opponent
	 * at point
	 * 
	 * @return false if it is a bad spot to place an O and returns true if the
	 *         spot is safe to place an S
	 */
	public boolean carefulSPlacement(char[][] grid, int x, int y) {

		if (grid[y][x] == '-') {
			// Check for S one space UP
			if ((grid[y - 2][x] == 'S' && grid[y - 1][x] == '-') || (grid[y - 1][x] == 'O' && grid[y - 2][x] == '-')) {
				return false;
			}
			// Check Down
			else if ((grid[y + 2][x] == 'S' && grid[y + 1][x] == '-')
					|| (grid[y + 1][x] == 'O' && grid[y + 2][x] == '-')) {
				return false;
			}
			// Check Left
			else if ((grid[y][x - 2] == 'S' && grid[y][x - 1] == '-')
					|| (grid[y][x - 1] == 'O' && grid[y][x - 2] == '-')) {
				return false;
			}
			// check RIght
			else if ((grid[y][x + 2] == 'S' && grid[y][x + 1] == '-')
					|| (grid[y][x + 1] == 'O' && grid[y][x + 2] == '-')) {
				return false;
			}
			// Check down diagonal left
			else if ((grid[y + 2][x - 2] == 'S' && grid[y + 1][x - 1] == '-')
					|| (grid[y + 1][x - 1] == 'O' && grid[y + 2][x - 2] == '-')) {
				return false;
			}
			// Check Down diagonal Right
			else if ((grid[y + 2][x + 2] == 'S' && grid[y + 1][x + 1] == '-')
					|| (grid[y + 1][x + 1] == 'O' && grid[y + 2][x + 2] == '-')) {
				return false;
			}
			// Check Up Diagonal left
			else if ((grid[y - 2][x - 2] == 'S' && grid[y - 1][x - 1] == '-')
					|| (grid[y - 1][x - 1] == 'O' && grid[y - 2][x - 2] == '-')) {
				return false;
			}
			// Check Up Diagonal Right
			else if ((grid[y - 2][x + 2] == 'S' && grid[y - 1][x + 1] == '-')
					|| (grid[y - 1][x + 1] == 'O' && grid[y - 2][x + 2] == '-')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks to make sure not putting O next to S that would give the opponent
	 * at point Will not place an O next to an S
	 * 
	 * @return false if it is a bad spot to place an S and returns true if the
	 *         spot is safe to place an O
	 */
	public boolean carefulOPlacement(char[][] grid, int x, int y) {

		if (grid[y][x] == '-') {

			// Check Vertically
			if ((grid[y - 1][x] == 'S' && grid[y + 1][x] == '-') || (grid[y - 1][x] == '-' && grid[y + 1][x] == 'S')) {
				return false;

			}
			// Check horizontally
			else if ((grid[y][x - 1] == 'S' && grid[y][x + 1] == '-')
					|| (grid[y][x - 1] == '-' && grid[y][x + 1] == 'S')) {
				return false;
			}

			// Check Diagonal bottom left to top right
			else if ((grid[y + 1][x - 1] == 'S' && grid[y - 1][x + 1] == '-')
					|| (grid[y + 1][x - 1] == '-' && grid[y - 1][x + 1] == 'S')) {
				return false;
			}
			// Check diagonally Top Left top Bottom right

			else if ((grid[y - 1][x - 1] == 'S' && grid[y + 1][x + 1] == '-')
					|| (grid[y - 1][x - 1] == '-' && grid[y + 1][x + 1] == 'S')) {
				return false;
			}

		}
		return true;
	}

	/**
	 * Place randomly but checks to make sure the spot is safe
	 * 
	 * @param grid
	 */
	public void totallyRandom(char[][] grid) {

		// Find out how many Empty spaces there are
		int totalSpaces = 0;
		for (int y = 3; y <= 13; y++) {
			for (int x = 3; x <= 13; x++) {
				if (grid[y][x] == '-' && (carefulSPlacement(grid, x, y) || carefulOPlacement(grid, x, y))) {
					totalSpaces++;

				}
			}
		}

		int[][] coordinates = new int[2][totalSpaces];

		int Index = 0;

		// Add good locations to array
		for (int y = 3; y <= 13; y++) {
			for (int x = 3; x <= 13; x++) {
				if (grid[y][x] == '-' && (carefulSPlacement(grid, x, y) || carefulOPlacement(grid, x, y))) {
					coordinates[0][Index] = y;
					coordinates[1][Index] = x;
					Index++;

				}
			}
		}
		// Chooses random index
		int randIndex = (int) (Math.random() * Index);

		// If there is at least one safe spot
		if (Index != 0) {
			if (carefulSPlacement(grid, coordinates[1][randIndex], coordinates[0][randIndex])) {
				playTurn(grid, coordinates[1][randIndex], coordinates[0][randIndex], 'S');
			
				return;

			} else if (carefulOPlacement(grid, coordinates[1][randIndex], coordinates[0][randIndex])) {
				playTurn(grid, coordinates[1][randIndex], coordinates[0][randIndex], 'O');
				

				return;
			}
		}

		System.out.println("NO GOOD MOVE_-------------------------------------------");

		// Find the best move so that the opponent gets the least amount of
		// consecutive points
		bestBadMove(grid);
		playTurn(grid, bestX, bestY, bestChar);

	}

	/**
	 * Finds the best move that will give the opponent the least ammount of
	 * points
	 * 
	 * @param grid
	 *            the game board
	 */
	public void bestBadMove(char[][] grid) {
		// The total amount of points the opponent could get in one turn. Will
		// be compared to lowestCombo
		int count = 0;

		// The lowest about of points the opponent could get in one turn.
		int lowestCombo = 100;

		// Create a new board for simulations
		char[][] tempGrid = new char[16][16];

		// Load up grid into temp grid
		for (int y = boarder; y < grid.length - boarder; y++) {
			for (int x = boarder; x < grid.length - boarder; x++) {
				tempGrid[y][x] = grid[y][x];
			}
		}

		// For each empty spot on the board, check the consequences for placing
		// an S or an O there and calculate the optimal spot on the board as
		// well as the optimal letter
		for (int y = boarder; y < grid.length - boarder; y++) {
			for (int x = boarder; x < grid.length - boarder; x++) {
				if (tempGrid[y][x] == '-') {

					// Check how many points the opponent gets if an S is placed
					tempGrid[y][x] = 'S';
					while (FindWin(tempGrid)) {
						count++;
					}

					// If the total amount of points in a row is less than the
					// current
					if (count < lowestCombo) {
						lowestCombo = count;
						bestChar = 'S';
						bestX = x;
						bestY = y;
					}

					// Reset count
					count = 0;

					// Refresh the tempGrid
					for (int yy = boarder; yy < grid.length - boarder; yy++) {
						for (int xx = boarder; xx < grid.length - boarder; xx++) {
							tempGrid[yy][xx] = grid[yy][xx];
						}
					}

					// Check how many points the opponent gets if an O is placed
					tempGrid[y][x] = 'O';
					while (FindWin(tempGrid)) {
						count++;
					}

					if (count < lowestCombo) {
						lowestCombo = count;
						bestChar = 'O';
						bestX = x;
						bestY = y;
					}

					// Reset count
					count = 0;

					// Reload the tempGrid
					for (int yy = boarder; yy < grid.length - boarder; yy++) {
						for (int xx = boarder; xx < grid.length - boarder; xx++) {
							tempGrid[yy][xx] = grid[yy][xx];
						}
					}

				}
			}
		}
		System.out.println("Lowest Points In a Row : " + lowestCombo);
	}

	/**
	 * Adds a player move to the grid
	 * 
	 * @param grid
	 *            the game board
	 * @param x
	 *            the X coordinate
	 * @param y
	 *            the y coordinate
	 * @param letter
	 *            the letter they want to place
	 */
	public void playTurn(char[][] grid, int x, int y, char letter) {
		if (grid[y][x] == '-') {
			grid[y][x] = letter;

			// Subtract 3 because of the 3x3 boarder around the 10x10 grid
			lastX = x - 3;
			lastY = y - 3;
			lastChar = letter;
		} else {
			System.out.println("Invalid location");
		}
	}

	/**
	 * Displays the game board to the console
	 * 
	 * @param grid
	 */
	public void displayGrid(char[][] grid) {

		System.out.println("   0  1  2  3  4  5  6  7  8  9");
		for (int j = boarder; j < grid.length - boarder; j++) {
			System.out.print(j - boarder);
			for (int i = boarder; i < grid[j].length - boarder; i++) {
				System.out.print("  " + grid[j][i]);
			}
			System.out.println();
		}
	}

	public int getPoints() {
		return points;
	}

	public int getLastX() {
		return lastX;
	}

	public int getLastY() {
		return lastY;
	}

	public char getLastChar() {
		return lastChar;
	}

}

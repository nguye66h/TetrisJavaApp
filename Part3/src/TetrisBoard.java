import java.lang.*;

/** The model class of the Tetris game, controling the logic of the game.
* @author Haimi Nguyen
* @date 3/8/17
*/
public class TetrisBoard{
	/** constant stores number of columns of board */
	public final static int NUM_COLS = 10;

	/** constant stores number of rows of board */
	public final static int NUM_ROWS = 18;

	private int numCols = NUM_COLS;
	private int numRows = NUM_ROWS;

	/** stores boolean values for the board */
	private boolean[][] blockMatrix;
	private TetrisPiece currentPiece;

	/** number of lines formed */
	private int numLines;

	/** number of Tetrises formed */
	private int numTetris;

	/**
	* position of current (falling) piece 
	* currentPieceGridPosition[0] stores the current row 
	* currentPieceGridPosition[1] stores the current column
	*/
	private int[] currentPieceGridPosition;

	/** Constructor sets up the board. */
	public TetrisBoard(){
		initCurrentGP();

		addNewPiece();

		initBoard();
	}

	/**
	* Initialize an int array of length two 
	* to keep track of the grid position of the current piece (col, row)
	*/
	private void initCurrentGP(){
		currentPieceGridPosition = new int[2];
	}

	/**
	* Initialize the 2D board array to have all false values
	**/
	private void initBoard(){
		blockMatrix = new boolean[NUM_COLS][NUM_ROWS];
		for (int i = 0; i < NUM_COLS; i++){
			for (int j = 0; j < NUM_ROWS; j++){
				blockMatrix[i][j] = false;
			}
		}
	}

	/**
	* Update the board array to reflect the newly landed piece's filled squares 
	* using the currentGridPosition values and the currentPiece's rotation value.
	*/
	public void landPiece(){
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				if(currentPiece.isFilled(currentPiece.getPieceRotation(), i, j)){		// if there is a block
					blockMatrix[currentPieceGridPosition[0]+i][currentPieceGridPosition[1]+j] = true;	// then embed the block to board
				}
			}
			
		}

	}

	/** Check if moving left is valid. Move left if valid.
	* @return true if valid move was performed
	*/
	public boolean moveLeft(){
		if (validMove(currentPiece, currentPiece.getPieceRotation(), currentPieceGridPosition[0] - 1, currentPieceGridPosition[1])){ // get piece if moved left
			currentPieceGridPosition[0] = currentPieceGridPosition[0] - 1;	// move current position to left
			return true;
		}
		else{
			return false;
		}
	}

	/** Check if moving right is valid. Move right if valid.
	* @return true if valid move was performed
	*/
	public boolean moveRight(){
		if (validMove(currentPiece, currentPiece.getPieceRotation(), currentPieceGridPosition[0] + 1, currentPieceGridPosition[1])){	// get piece of moved right
			currentPieceGridPosition[0] = currentPieceGridPosition[0] + 1;	// move current position to right
			return true;
		}
		else{
			return false;
		}
	}

	/** Check if moving down is valid and move down if valid. */
	public boolean moveDown(){
		if (validMove(currentPiece, currentPiece.getPieceRotation(), currentPieceGridPosition[0], currentPieceGridPosition[1] + 1)){
			currentPieceGridPosition[1] = currentPieceGridPosition[1] + 1;
			return true;
		}
		else{				// if cannot move down
			landPiece();		
			numberOfFormedLines();		
			addNewPiece();
			return false;
			
		}
	}

	/** Check if rotating clockwise is valid. Rotate if valid
	* @return true if valid move was performed
	*/
	public boolean rotateCW(){
		if(currentPiece.getPieceRotation() <= 270){
			if (validMove(currentPiece, currentPiece.getPieceRotation() + 90, currentPieceGridPosition[0], currentPieceGridPosition[1])){
				currentPiece.rotateCW();
				return true;
			}
			else{
				return false;
			}
		}
		else if (validMove(currentPiece, currentPiece.getPieceRotation() + 90 - 360, currentPieceGridPosition[0], currentPieceGridPosition[1])){
			currentPiece.rotateCW();
			return true;
		}
		else{
			return false;
		}
		
	}

	/** Check if rotating counter-clockwise is valid. Rotate if valid.
	* @return true if valid move was performed
	*/
	public boolean rotateCCW(){
		if(currentPiece.getPieceRotation() <= 90){
			if (validMove(currentPiece, 360 + (currentPiece.getPieceRotation() - 90), currentPieceGridPosition[0], currentPieceGridPosition[1])){
				currentPiece.rotateCCW();
				return true;
			}
			else{
				return false;
			}
		}
		else if(validMove(currentPiece, currentPiece.getPieceRotation() - 90, currentPieceGridPosition[0], currentPieceGridPosition[1])){
			currentPiece.rotateCCW();
			return true;
		}
		else{
			return false;
		}

	}

	/** Checks if placing the piece at grid position (col, row) 
	* with the rotation rot (values can be 0, 90, 180, 270) 
	* would cause a collision (i.e., if there would be a block on an already-filled grid square). 
	* @param piece  the Tetris piece
	* @param rot the rotation number of the piece
	* @param gridCol the column number of piece
	* @param gridRow the row number of piece
	*
	* @return true if there would be a collision
	*/
	private boolean detectCollision(TetrisPiece piece, int rot, int gridCol, int gridRow){
		
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				if(piece.isFilled(rot, i, j) && hasBlock(gridCol + i, gridRow + j)){
					return true;
				}
			}
		
		
			
		}
		return false;
	}

	/**
	* Checks if placing the piece at grid position (row, col) 
	* with the rotation rot (values can be 0, 90, 180, 270) 
	* would cause an out of bounds condition 
	* (i.e., if there would be a block falling off the board).
	* @param piece the testing Tetris piece 
	* @param rot rotation number of piece
	* @param gridCol the column number of the piece
	* @param gridRow the row number of the piece
	*
	* @return true if there would be a bounding error
	*/
	private boolean detectOutOfBounds(TetrisPiece piece, int rot, int gridCol, int gridRow){
		
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				if (piece.isFilled(rot, i, j) && (gridCol + i < 0 || gridCol + i >= NUM_COLS ||
					gridRow + j < 0 || gridRow + j >= NUM_ROWS)){
					return true;
				}
			}
			
		}

		return false;

		
	}

	/** Checks if placing the piece at grid position (row, col) 
	* with the rotation rot (values can be 0, 90, 180, 270) 
	* is a valid move. 
	* @param piece  
	* @param rot 
	* @param gridRow
	* @param gridCol
	* @return true if no collision or bounding error
	*/
	private boolean validMove(TetrisPiece piece, int rot, int gridCol, int gridRow){
		if (!detectOutOfBounds(piece, rot, gridCol, gridRow) && !detectCollision(piece, rot, gridCol, gridRow)){
			return true;
		}
		
		else{
			return false;
		}
		
	}

	/** Check if there is a block in the row and column. 
	* @param col column number of the tested block
	* @param row row number of the tested block
	* @return true if there is a block
	*/
	public boolean hasBlock(int col, int row){
		return blockMatrix[col][row];
	}

	/**
	* Add a new random Tetris piece to the board at grid position (3, 0).
	*/
	public void addNewPiece(){
		int ran = (int)(Math.random()*7);
		TetrisPiece newPiece;
		switch(ran){
			case 0: newPiece = new TetrisL1();
					break;
			case 1: newPiece = new TetrisL2();
					break;
			case 2: newPiece = new TetrisS1();
					break;
			case 3: newPiece = new TetrisS2();
					break;
			case 4: newPiece = new TetrisSquare();
					break;
			case 5: newPiece = new TetrisStick();
					break;
			default: newPiece = new TetrisT();
					break;
			
		}

		currentPiece = newPiece;
		currentPieceGridPosition[0] = 3;
		currentPieceGridPosition[1] = 0;
	}

	

	/** Detect and remove any lines formed. 
	* @return the total number found.
	*/
	public void numberOfFormedLines(){
		int sum = 0;
		for (int i = 0; i < NUM_ROWS; i++){
			if (fullLine(i)){
				removeLine(i);
				sum++;
			}
		}
		if (sum == 4){
			numTetris = numTetris + 1;

		}
		numLines = numLines + sum;
	}

	/** Check if there is a full line at the row. 
	* @param row row number of the tested line
	* @return true if full
	*/
	private boolean fullLine(int row){
		int sum = 0;
		for (int i = 0; i < NUM_COLS; i++){
			if (!blockMatrix[i][row]){
				return false;
			}
			
			
		}
		return true;	
		
	}

	/** remove the line at a specific full row
	* @param row the row number of the full line
	*/
	private void removeLine(int row){
		boolean[][] blockMatrixtemp = new boolean[NUM_COLS][NUM_ROWS];
		for (int j = 1; j < row+1; j++){
			for (int i = 0; i < NUM_COLS; i++){
				blockMatrixtemp[i][j] = blockMatrix[i][j-1];
				blockMatrix[i][0] = false;
			}
		}
		for (int l = row + 1; l < NUM_ROWS; l++){
			for (int k = 0; k < NUM_COLS; k++){
		
				blockMatrixtemp[k][l] = blockMatrix[k][l];
				
				
			}
		}

		blockMatrix = blockMatrixtemp;
	}

	/** @return the block matrix (the grid of blocks) */
	public boolean[][] getBlockMatrix(){
		return blockMatrix;
	}

	/** @return the currentPiece */
	public TetrisPiece getCurrentPiece(){
		return currentPiece;
	}

	/** @return the currentPieceGridPosition */
	public int[] getCurrentPieceGridPosition(){
		return currentPieceGridPosition;
	}

	/** @return the numCols in the block matrix */
	public int getNumCols(){
		return numCols;
		
	}

	/** @return the numRows in the block matrix */
	public int getNumRows(){
		return numRows;
		
	}

	/** @return number of lines formed */
	public int getNumLines(){
		return numLines;
	}

	/** @return number of Tetrises */
	public int getNumTetris(){
		return numTetris;
	}

}
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.*;
import javax.swing.*;

/**
* This class is the View class of the game Tetris GUI version.
* @author Haimi Nguyen
* @Date 03/14/17
*/
public class TetrisBoardGUIView extends JPanel{
	
	public final int BLOCK_SIZE = 25;
	private TetrisBoard board;
	private JPanel mainBoard;

	/**
	* Constructor of TetrisBoardGUIView class to initialize board, add new piece, and repaint
	* @param b the tetris board
	*/
	public TetrisBoardGUIView(TetrisBoard b){

		board = b;
		board.addNewPiece();
		repaint();
	}
	
	/**
	* paint method paints the tetris board using 
	* the methods paintBoardOutline and paintBlock
	* @param g the graphics
	*/
	public void paint(Graphics g){
		paintBoardOutline(g, BLOCK_SIZE);
		paintBlock(g, board.getCurrentPieceGridPosition()[0],board.getCurrentPieceGridPosition()[1], BLOCK_SIZE);
		

	}

	/**
	* paint the board
	* @param g the graphics
	* @param blockSize the size of block
	*/
	private void paintBoardOutline(Graphics g, int blockSize){
		g.setColor(Color.BLACK);
		g.drawRect(5, 20, (blockSize+1)*10, (blockSize+1)*18);

	}

	/**
	* paint the the whole Tetris board with blocks on it
	* @param g the graphics
	* @param col column of current piece position
	* @param row row of current piece position
	* @param blockSize the size of the block
	*/
	private void paintBlock(Graphics g, int col, int row, int blockSize){
		
		for (int i = 0; i < board.getNumCols(); i++){
			for (int j = 0; j < board.getNumRows(); j++){
				if (i <= col + 3 && i >= board.getCurrentPieceGridPosition()[0] &&
					j <= row + 3 && j >= board.getCurrentPieceGridPosition()[1]){
						if (board.getCurrentPiece().isFilled(board.getCurrentPiece().getPieceRotation(),i - board.getCurrentPieceGridPosition()[0], j - board.getCurrentPieceGridPosition()[1]) || board.hasBlock(i,j) ){
							g.setColor(Color.YELLOW);
							g.fillRect(5 + i + blockSize*i, 20 + j + blockSize*j, blockSize, blockSize);
							g.setColor(Color.BLACK);
							g.drawRect(5 + i + blockSize*i, 20 + j + blockSize*j, blockSize, blockSize);
						}
						
					
				}	
				else {
					if (board.hasBlock(i,j)){
						g.setColor(Color.YELLOW);
						g.fillRect(5 + i + blockSize*i, 20 + j + blockSize*j, blockSize, blockSize);
						g.setColor(Color.BLACK);
						g.drawRect(5 + i + blockSize*i, 20 + j + blockSize*j, blockSize, blockSize);
					}

					
				}
						
							
			}
				
				
		}	
		
	}

	

}
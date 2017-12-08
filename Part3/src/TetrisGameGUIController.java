import java.util.*;
import java.util.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;


/** Controller class of Tetris game GUI version 
* @author Haimi Nguyen
* @Date 03/21/17
*/
public class TetrisGameGUIController extends JPanel implements KeyListener{
	//Rate of automatic dropping of pieces
	public final int DEFAULT_DROP_RATE = 1000;
	private TetrisBoard board;
	private TetrisBoardGUIView view;
	//JLabel of number of lines cleared
	private JLabel linesLabel;
	//JLabel of number of tetrises cleared
	private JLabel tetrisesLabel;
	
	// JPanel holding both lines and tetrises cleared
	private JPanel panel1 = new JPanel();

	/** Constructor of class, initialize board, view and construct the Tetris GUI
	* from different methods
	*/
	public TetrisGameGUIController(){
		super( new BorderLayout());
		board = new TetrisBoard();
		view = new TetrisBoardGUIView(board);
		linesLabel = new JLabel("Lines cleared: " + board.getNumLines() +"    ");
		tetrisesLabel = new JLabel("Tetrises cleared: " + board.getNumTetris());
		panel1.add(linesLabel);
		panel1.add(tetrisesLabel);
		add(panel1, BorderLayout.NORTH);
		add(view, BorderLayout.CENTER);
		start();
		addKeyListener(this);
		setFocusable(true);
		repaint();
		revalidate();
	
	}

	/** override keyPressed, use left, right, down, x, z buttons
	* on keyboard to move piece left, right, down, clockwise, anticlockwise respectively
	* @param e the input from keyboard */
	public void keyPressed(KeyEvent e){
				
		int key = e.getKeyCode();

			
		if (key == KeyEvent.VK_RIGHT){
			board.moveRight();
		}
			
		else if (key == KeyEvent.VK_LEFT){
			board.moveLeft();
		}

		else if (key == KeyEvent.VK_DOWN){
			board.moveDown();
		}

		else if (key == KeyEvent.VK_Z){
			board.rotateCCW();
		}

		else if (key == KeyEvent.VK_X){
			board.rotateCW();
		}
		
				
	}
	/** need this to implement KeyListener */
	public void keyReleased(KeyEvent e){

	}
	/** need this to implement KeyListener */
	public void keyTyped(KeyEvent e){

	}

	// instantiate and initialize a Timer object
	Timer gameTimer = new Timer();

	// the task runs events that are repeated including moving piece down, set labels, 
	// and repaint the board
	TimerTask task = new TimerTask(){
		public void run(){
			board.moveDown();
			linesLabel.setText("Lines cleared: " + board.getNumLines() +"    ");
			tetrisesLabel.setText("Tetrises cleared: " + board.getNumTetris());
			repaint();			

		}
	};
	
	/** start method runs the task method every period of time. Task method includes 
	moving piece down, setting labels, and repainting the board */
	public void start(){
		gameTimer.scheduleAtFixedRate(task, DEFAULT_DROP_RATE, DEFAULT_DROP_RATE);
		
	}
	

	


}
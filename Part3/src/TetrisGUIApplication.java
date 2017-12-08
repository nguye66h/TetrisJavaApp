import javax.swing.JFrame;

/** Application class of Tetris game GUI version 
* @author Haimi Nguyen
* @Date 03/21/17
*/
public class TetrisGUIApplication{

	/** main method creates JFrame and add Controller class of game to the frame */
	public static void main (String[] args){
		JFrame f = new JFrame("Tetris");
		f.setSize(400, 600);
		f.add(new TetrisGameGUIController());
		f.setVisible(true);
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
}
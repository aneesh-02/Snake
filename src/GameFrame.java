import javax.swing.JFrame;

public class GameFrame extends JFrame 
{
	//constructor
	GameFrame()
	{
		// instance
//		GamePanel panel = new GamePanel();
		// but no need just use RHS directly
		
		// instance initiated
		this.add(new GamePanel());
		// title of the frame
		this.setTitle("Snake");
		// close button function
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// window not to be resizable
		this.setResizable(false);
		// pack function to size the frame according to the components automatically
		this.pack();
		// window visisble
		this.setVisible(true);
		// to view the window in the middle of the screen
		this.setLocationRelativeTo(null);
	}

}

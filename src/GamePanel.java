import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener
{

	//screen size
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 660;
	// one unit
	static final int UNIT_SIZE = 40;
	// total units
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	// delay for the animation: higher the delay slower the game
	static final int DELAY = 75;
	// to hold x and y coordinates of the body of the snake
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	// length of snake in the beginning of the game
	int bodyParts = 6;
	int applesEaten;
	// x and y coordinates for apple : will be random
	int appleX;
	int appleY;
	// for directions of the snake R L U D : by default = right
	char direction = 'R';
	// snake not running by default
	boolean running = false;
	// timmer
	Timer timer;
	Random random;
	
	GamePanel()
	{
		// random instance for Random class
		random = new Random();
		// setting size na dbackground in the constructor
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		// to give focus to the component when requested
		this.setFocusable(true);
		// new object is  formed when a key is formed
		this.addKeyListener(new MyKeyAdapter());
		// call this function to start the gmAE
		startGame();
	}
	
	public void startGame() 
	{
		newApple(); 					//create a new apple
		running = true; 				// make the game running
		timer = new Timer(DELAY,this);  // initialize the timer 
		timer.start(); 					// start the timer
	}
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);	
		draw(g);
	}
	public void draw(Graphics g) 
	{
		// only draw if the game is running else game over method runs to show the game ended
		if(running) {
			// to draw line across the panel for better understanding
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); // draw line method across the height at some distance
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE); // across width 
			}
			
			// drawing the apple
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); // a circle shape of red color
		
			// drawing the body iterate thru all the units in body
			for(int i = 0; i< bodyParts;i++) 
			{
				if(i == 0) // i=0 = head of snake 
				{
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); // green color rect
				}
				else // for the rest of the body 
				{
					g.setColor(new Color(45,180,0)); 
					//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); // different shade of green
				}			
			}
			
			// drawing score
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		}
		else 
		{
			gameOver(g);
		}
		
	}
	
	public void newApple()
	{
		// random coords for the next apple // cast into int to keep it a whole no
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move()
	{
		// iterate thru all body parts
		for(int i = bodyParts;i>0;i--) 
		{
			x[i] = x[i-1]; // shifting the body parts by 1  
			y[i] = y[i-1];	//  all coords move by 1 in these arrays
		}
		
		// direction cases
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE; // goes up so decrease 1 unit size Y
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE; // goes down so add 1 unit in Y
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE; //  left so sub 1 unit size in x array (x direction )
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE; //  right so add 1 unit size in x array (x direction 
			break;
		}
		
	}
	public void checkApple() 
	{
		// if head is on apple body parts array increases
		// get a new apple and also increase the score
		if((x[0] == appleX) && (y[0] == appleY)) 
		{
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollisions() 
	{
		//checks if head collides with body
		// iterate thru all body parts and check if head collides with any 1 of them
		for(int i = bodyParts;i>0;i--)  // loop from end to start
		{
			if((x[0] == x[i])&& (y[0] == y[i])) // same x and y cords of head and body 
			{
				running = false; // then stop running
			}
		}
		//check if head touches left border
		if(x[0] < 0) 
		{
			running = false; 
		}
		//check if head touches right border
		if(x[0] > SCREEN_WIDTH) 
		{
			running = false;
		}
		//check if head touches top border
		if(y[0] < 0) 
		{
			running = false;
		}
		//check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) 
		{
			running = false;
		}
		
		if(!running) 
		{
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) 
	{
		//Score
		g.setColor(Color.red); 
		g.setFont( new Font("Ink Free",Font.BOLD, 75)); // font and font color
		FontMetrics metrics1 = getFontMetrics(g.getFont()); // to line up the font in the middle of the screen 
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2); // center of the screen
	}

	public void actionPerformed(ActionEvent e) 
	{	
		if(running) 
		{
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter
	{
		// controlling the movemnt of snake according to the key pressed
		public void keyPressed(KeyEvent e) 
		{
			switch(e.getKeyCode()) 
			{
				case KeyEvent.VK_LEFT: // left key 
					if(direction != 'R') // only if going up, down or left : go left 
					{
						direction = 'L';
					}
					break;
					
				case KeyEvent.VK_RIGHT:
					if(direction != 'L') // same as above
					{
						direction = 'R';
					}
					break;
				
				case KeyEvent.VK_UP:
					if(direction != 'D') // same as above
					{
						direction = 'U';
					}
					break;
				
				case KeyEvent.VK_DOWN:
					if(direction != 'U') // same as above
					{
						direction = 'D';
					}
					break;
			}
		}
	}
}
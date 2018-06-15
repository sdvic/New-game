import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;

import static javax.imageio.ImageIO.read;

/***********************************************************************************************
 * David Frieder's Thomas Game Copyright 2018 David Frieder 6/15/2018 rev 2.4
 * Falling mechanic started working, but needs some changes
 * Fixed Thomas' Jumping
 ***********************************************************************************************/
public class ThomasShootEmUpController extends JComponent implements ActionListener, Runnable, KeyListener
{
	public boolean isGoingRight = false;
	int upperTrackYPos;
	int upperTrackWidth;
	int lowerTrackYPos;
	int lowerTrackWidth;
	Rectangle upperTrackBox;
	Rectangle upperTrackBox2;
	Shape upperTrackShape;
	private Rectangle lowerTrackBox2;
	private Shape lowerTrackShape;
	private AffineTransform lowerTrackTransform;
	int thomasBoxWidth;
	int thomasBoxHeight;
	Rectangle thomasBox;
	Shape thomasShape;
	private Rectangle2D.Double upperTrackDetectionZone = new Rectangle2D.Double(0, 0, 200, 49);
	private URL thomasThemeAddress = getClass().getResource("ThomasThemeSong.wav");
	private AudioClip thomasThemeSong = JApplet.newAudioClip(thomasThemeAddress);
	private Image[] thomasSpriteImageArray = new Image[8];
	private Image[] reverseThomasImageArray = new Image[8];
	private Image gun = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Minigun_SU.png"));
	private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	private JFrame mainGameWindow = new JFrame("NewGame");// Makes window with
	private AffineTransform identityTx = new AffineTransform();
	private AffineTransform thomasTransform = new AffineTransform();// Set
	private AffineTransform backgroundTx = new AffineTransform();
	private AffineTransform upperTrackTransform = new AffineTransform();
	private Timer animationTicker = new Timer(40, this);
	private Timer jumpingTicker = new Timer(800 / 60, this);
	private Image thomasSpriteImage;
	private Image reverseThomasImage;
	private int thomasSpriteImageCounter;
	private Image roadImage;
	private Image trackImage;
	private int groundLevelTrackYPos = (int) (heightOfScreen * 0.809);
	private int level2TrackYPos = (int) (heightOfScreen * 0.2);
	private boolean isGoingLeft;
	private boolean isNotMoving;
	private boolean isJumping;
	private boolean isFalling;
	private int thomasMaxSpeed = 13;
	private int initialJumpingVelocity = -31;
	private int initialFallingVelocity = 0;
	public int jumpingVelocity = initialJumpingVelocity;
	public int fallingVelocity = initialFallingVelocity;
	private int movingVelocity;
	private int gravityAcceleration = 1;
	private Graphics2D g2;
	private int roadWidth;
	private int trackWidth;
	private int thomasYOffsetFromGround = 0;
	private boolean lastWayFacing = true;
	private Area areaA;
	private Area areaB;

	// TODO give the ground and/or lower track tiles the same property as the upper track tiles, and change the jumping rules so that he falls as long as the box doesn't intersect the tracks

	/***********************************************************************************************
	 * Main
	 ***********************************************************************************************/
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new ThomasShootEmUpController());
	}

	/***********************************************************************************************
	 * Run
	 ***********************************************************************************************/
	@Override
	public void run()
	{
		loadImages();
		setUpMainGameWindow();
		thomasThemeSong.loop();
		animationTicker.start();
		jumpingTicker.start();
	}

	/***********************************************************************************************
	 * Paint
	 ***********************************************************************************************/
	public void paint(Graphics g)
	{
		repaint();
		g2 = (Graphics2D) g;
		drawThomas();
		drawRoad();// ........................ Draw Road
		drawUpperTracks();// ................. Draw Upper Tracks
		drawLowerTracks();// ................. Draw Lower Tracks
		if (testIntersection(thomasShape, upperTrackShape)) //Check if Thomas' sprite intersects the upper tracks
		{
			if (jumpingVelocity > 0 && thomasYOffsetFromGround < upperTrackYPos) //Checks if Thomas is falling or jumping, and then makes him able to land on the tracks if he is falling down.
			{
				jumpingVelocity = initialJumpingVelocity;
				isJumping = false;
				isFalling = false;
				g2.setTransform(thomasTransform);
			}
		}
		if (testIntersection(thomasShape, upperTrackShape) == false && testIntersection(thomasShape, lowerTrackShape) == false)//TODO: make lower tracks a shape
		{
			isFalling = true;
			if (thomasYOffsetFromGround > 0)
			{
				jumpingVelocity = initialJumpingVelocity;
				thomasYOffsetFromGround = 0;
				isJumping = false;
				
			}
			repaint();
		}
	}

	/***********************************************************************************************
	 * Draw road
	 ***********************************************************************************************/
	private void drawRoad()
	{
		g2.setTransform(backgroundTx);
		g2.translate(-widthOfScreen, heightOfScreen - 200);
		g2.scale(1.5, 1.5);
		for (int i = 0; i < (2 * (widthOfScreen / roadImage.getWidth(null))) + 2; i++) // fits
		{
			g2.drawImage(roadImage, 0, 0, null);
			g2.translate(roadImage.getWidth(null), 0);
		}
	}

	/***********************************************************************************************
	 * Draw upper tracks
	 ***********************************************************************************************/
	private void drawUpperTracks()
	{
		g2.setTransform(backgroundTx);//this is an identity transform
		g2.translate(0, heightOfScreen / 2); // center in screen
		g2.scale(1.5, 1.5);
		upperTrackYPos = (int) g2.getTransform().getTranslateY(); //this is where upperTrackYPos gets its value from
		for (int i = 0; i < 2; i++) // fits track images to screen width
		{
			g2.drawImage(trackImage, 0, 0, null);
			g2.translate(trackImage.getWidth(null), 0);
			upperTrackWidth = trackImage.getWidth(null);
		}
		upperTrackBox2 = new Rectangle(-2 * upperTrackWidth, 0, 2 * upperTrackWidth, upperTrackYPos);
		upperTrackShape = upperTrackBox2.getBounds();
		upperTrackTransform = g2.getTransform();
	}

	/***********************************************************************************************
	 * Draw lower tracks
	 ***********************************************************************************************/
	private void drawLowerTracks()
	{
		g2.setTransform(backgroundTx);
		g2.translate(-widthOfScreen, heightOfScreen - 200);
		g2.scale(1.5, 1.5);
		for (int i = 0; i < (2 * (widthOfScreen / trackImage.getWidth(null))) + 2; i++) // fits track images to screen width
		{
			g2.drawImage(trackImage, 0, 0, null);
			g2.translate(trackImage.getWidth(null), 0);
			lowerTrackWidth = trackImage.getWidth(null);
			lowerTrackYPos = trackImage.getHeight(null);
			lowerTrackBox2 = new Rectangle(-2 * lowerTrackWidth, 0, 2 * lowerTrackWidth, lowerTrackYPos);
			lowerTrackShape = lowerTrackBox2.getBounds();
			lowerTrackTransform = g2.getTransform();
		}
	}

	/***********************************************************************************************
	 * Draw Thomas with sprite files
	 ***********************************************************************************************/
	public void drawThomas()
	{
		g2.setTransform(backgroundTx);
		thomasTransform.setToTranslation(widthOfScreen / 3, heightOfScreen - 420);
		g2.setTransform(thomasTransform);
		try
		{
			thomasSpriteImageCounter = thomasSpriteImageCounter % 8;
			thomasSpriteImage = thomasSpriteImageArray[thomasSpriteImageCounter];
			reverseThomasImage = reverseThomasImageArray[thomasSpriteImageCounter];
			thomasTransform.setToTranslation(widthOfScreen / 3, heightOfScreen - 420 + thomasYOffsetFromGround);
			g2.setTransform(thomasTransform);
			thomasBox = new Rectangle(0, 0, thomasBoxWidth, thomasBoxHeight);
			thomasShape = thomasBox.getBounds();

			if (isGoingLeft || lastWayFacing == true)
			{
				g2.drawImage(thomasSpriteImage, 0, 0, null);
				lastWayFacing = true;
				thomasBoxWidth = thomasSpriteImage.getWidth(null);
				thomasBoxHeight = thomasSpriteImage.getHeight(null);
			}
			if (isGoingRight || lastWayFacing == false)
			{
				g2.drawImage(reverseThomasImage, 0, 0, null);
				lastWayFacing = false;
				thomasBoxWidth = thomasSpriteImage.getWidth(null);
				thomasBoxHeight = thomasSpriteImage.getHeight(null);
			}
		} catch (Exception ex)
		{
			System.out.println("error reading thomas thomasSpriteImage from thomas sprite thomasSpriteImage array");
		}
	}

	/***********************************************************************************************
	 * Action Performed.....Respond to animation ticker and paint ticker
	 ***********************************************************************************************/
	@Override
	public void actionPerformed(ActionEvent e)
	{
		thomasTransform.setToTranslation(0, thomasYOffsetFromGround);
		if (isGoingLeft == true)
		{
			if (e.getSource() == animationTicker)
			{
				if (g2 != null)
				{
					thomasSpriteImageCounter++;
					backgroundTx.setToTranslation(backgroundTx.getTranslateX() + 20, 0);
					if (backgroundTx.getTranslateX() > widthOfScreen)
					{
						backgroundTx.setToTranslation(-widthOfScreen, 0);
					}
					repaint();
				}
			}
		}
		if (isGoingRight == true)
		{
			if (e.getSource() == animationTicker)
			{
				if (g2 != null)
				{
					thomasSpriteImageCounter++;
					if (thomasSpriteImageCounter < 0)
					{
						thomasSpriteImageCounter = 7;
					}
					backgroundTx.setToTranslation(backgroundTx.getTranslateX() - 20, 0);
					if (backgroundTx.getTranslateX() < -widthOfScreen)
					{
						backgroundTx.setToTranslation(widthOfScreen, 0);
					}
					repaint();
				}
			}
		}
		if (isJumping == true)
		{
			jump(e);
		}
		if (isFalling == true)
		{
			fall(e);
			System.out.println("falling");
		}
	}

	public void jump(ActionEvent e)
	{
		if (e.getSource() == jumpingTicker)
		{
			if (g2 != null)
			{
				thomasYOffsetFromGround += jumpingVelocity;
				jumpingVelocity += gravityAcceleration;
			}
			if (thomasYOffsetFromGround > 0)
			{
				jumpingVelocity = initialJumpingVelocity;
				thomasYOffsetFromGround = 0;
				isJumping = false;
				isFalling = false;
			}
			repaint();
		}
	}
	public void fall(ActionEvent e)
	{
		if (e.getSource() == jumpingTicker)
		{
			if (g2 != null)
			{
				thomasYOffsetFromGround += fallingVelocity;
				fallingVelocity += gravityAcceleration;
			}
			if ((thomasYOffsetFromGround > 0 || testIntersection(thomasShape, upperTrackShape)) && !isJumping)
			{
				fallingVelocity = initialFallingVelocity;
				thomasYOffsetFromGround = 0;
				isFalling = false;
				isJumping = false;
			}
			isFalling = false;
			repaint();
		}
	}

	/***********************************************************************************************
	 * Respond to key typed
	 ***********************************************************************************************/
	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	/***********************************************************************************************
	 * Respond to key pressed
	 ***********************************************************************************************/
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) // going right
		{
			isGoingRight = true;
			isGoingLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) // going left
		{
			isGoingLeft = true;
			isGoingRight = false;
			animationTicker.start();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT && e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			isGoingLeft = false;
			isGoingRight = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			isJumping = true;
		}
	}

	/***********************************************************************************************
	 * Respond to key released
	 ***********************************************************************************************/
	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) // going right
		{
			isGoingRight = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) // going left
		{
			isGoingLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
		}
	}

	/***********************************************************************************************
	 * Get .png files, convert to Image and load sprite array
	 ***********************************************************************************************/
	private void loadImages()
	{
		try
		{
			thomasSpriteImageArray[0] = read(getClass().getResource("Thomas1.png"));
			thomasSpriteImageArray[1] = read(getClass().getResource("Thomas2.png"));
			thomasSpriteImageArray[2] = read(getClass().getResource("Thomas3.png"));
			thomasSpriteImageArray[3] = read(getClass().getResource("Thomas4.png"));
			thomasSpriteImageArray[4] = read(getClass().getResource("Thomas5.png"));
			thomasSpriteImageArray[5] = read(getClass().getResource("Thomas6.png"));
			thomasSpriteImageArray[6] = read(getClass().getResource("Thomas7.png"));
			thomasSpriteImageArray[7] = read(getClass().getResource("Thomas8.png"));
			reverseThomasImageArray[0] = read(getClass().getResource("Reversed Thomas1.png"));
			reverseThomasImageArray[1] = read(getClass().getResource("Reversed Thomas2.png"));
			reverseThomasImageArray[2] = read(getClass().getResource("Reversed Thomas3.png"));
			reverseThomasImageArray[3] = read(getClass().getResource("Reversed Thomas4.png"));
			reverseThomasImageArray[4] = read(getClass().getResource("Reversed Thomas5.png"));
			reverseThomasImageArray[5] = read(getClass().getResource("Reversed Thomas6.png"));
			reverseThomasImageArray[6] = read(getClass().getResource("Reversed Thomas7.png"));
			reverseThomasImageArray[7] = read(getClass().getResource("Reversed Thomas8.png"));
		} catch (IOException e)
		{
			System.out.println("error reading from thomas sprite array");
		}
		roadImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("ground.png"));

		trackImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Tracks.png"));
		roadWidth = roadImage.getWidth(null);
		trackWidth = roadImage.getWidth(null);
	}

	/***********************************************************************************************
	 * Set up main JFrame
	 ***********************************************************************************************/
	private void setUpMainGameWindow()
	{
		mainGameWindow.setTitle("Thomas the tank");
		mainGameWindow.setSize(widthOfScreen, heightOfScreen);
		mainGameWindow.add(this);// Adds the paint method to the JFrame
		mainGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGameWindow.getContentPane().setBackground(new Color(200, 235, 255));
		mainGameWindow.setVisible(true);
		mainGameWindow.addKeyListener(this);
	}

	/***********************************************************************************************
	 * Check for intersections
	 ***********************************************************************************************/
	public boolean testIntersection(Shape shapeA, Shape shapeB)
	{

		areaA = new Area(shapeA);
		areaB = new Area(shapeB);
		areaA.transform(thomasTransform);
		areaB.transform(upperTrackTransform);
		areaA.intersect(areaB);
		return !areaA.isEmpty();
	}
}
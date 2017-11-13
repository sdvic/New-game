import javax.swing.*;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.net.URL;

public class ThomasShootEmUpController extends JComponent implements ActionListener, Runnable
{
	private ImageIcon[] images = new ImageIcon[8];
	private Image gun;
	private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	private JFrame mainGameWindow = new JFrame("NewGame");// Makes window with
															// title "NewGame"
	public ThomasUtilites util = new ThomasUtilites();
	private Timer paintTicker = new Timer(20, this);
	private Timer animationTicker = new Timer(45, this);
	private ImageIcon thomasImageIcon = new ImageIcon();
	private Image thomasImage;
	private int pictureCounter;
	private int thomasXPos = widthOfScreen / 2;
	private Image roadImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("ground.png"));
	private Image tracksImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Standard Gauge Train Track Sprite.png"));
	private int roadXPos = 0;
	private boolean isGoingRight;
	private boolean isGoingLeft;
	private boolean isNotMoving;
	URL thomasThemeAddress = getClass().getResource("Thomas The Tank Engine Theme Song.wav");
	AudioClip thomasThemeSong = JApplet.newAudioClip(thomasThemeAddress);

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new ThomasShootEmUpController());
	}
	public void run()
	{
		paintTicker.start();
		animationTicker.start();
		images[0] = new ImageIcon("src/Thomas1.png");
		images[1] = new ImageIcon("src/Thomas2.png");
		images[2] = new ImageIcon("src/Thomas3.png");
		images[3] = new ImageIcon("src/Thomas4.png");
		images[4] = new ImageIcon("src/Thomas5.png");
		images[5] = new ImageIcon("src/Thomas6.png");
		images[6] = new ImageIcon("src/Thomas7.png");
		images[7] = new ImageIcon("src/Thomas8.png");
		gun = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Minigun_SU.png"));
		mainGameWindow.setTitle("Thomas the tank");
		mainGameWindow.setSize(widthOfScreen, heightOfScreen);
		mainGameWindow.add(this);// Adds the paint method to the JFrame
		mainGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGameWindow.getContentPane().setBackground(new Color(200, 235, 255));
		mainGameWindow.setVisible(true);
		mainGameWindow.addKeyListener(util);
		thomasThemeSong.play();
	}

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		// TODO:WORK ON MAKING THOMAS ACCELERATE BEFORE HE REACHES FULL SPEED
		g2.scale(-1, 1);
		g2.scale((double) (1 / g2.getTransform().getScaleX())*0.9, 1*0.9);
//		g2.drawImage(gun, thomasXPos + 150, (int) (heightOfScreen * 0.70), -gun.getWidth(this) / 2, gun.getHeight(this) / 2, null);
		Image im = thomasImageIcon.getImage();
		if (isGoingRight)
		{
			thomasImageIcon = images[pictureCounter];
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-thomasXPos-(thomasXPos/4), (int) (heightOfScreen * 0.69));
			g2.drawImage(im, tx, null);
		}
		if (isGoingLeft)
		{
			thomasImageIcon = images[pictureCounter];
			AffineTransform tx = AffineTransform.getScaleInstance(1, 1);
			tx.translate(thomasXPos-(thomasXPos/7), (int) (heightOfScreen * 0.69));
			g2.drawImage(im, tx, null);
		}
		if (isNotMoving)
		{
//			thomasImageIcon = images[pictureCounter];
		}
		g2.scale(2, 2);
		g2.drawImage(roadImage, roadXPos, (int) (heightOfScreen * 0.449), this);
		g2.drawImage(roadImage, roadXPos + roadImage.getWidth(mainGameWindow), (int) (heightOfScreen * 0.449), this);
		g2.drawImage(roadImage, roadXPos + roadImage.getWidth(mainGameWindow) * 2, (int) (heightOfScreen * 0.449), this);
		g2.drawImage(roadImage, roadXPos + roadImage.getWidth(mainGameWindow) * 3, (int) (heightOfScreen * 0.449), this);
		g2.drawImage(roadImage, roadXPos - roadImage.getWidth(mainGameWindow), (int) (heightOfScreen * 0.449), this);
		g2.drawImage(roadImage, roadXPos - roadImage.getWidth(mainGameWindow) * 2, (int) (heightOfScreen * 0.449), this);
		g2.drawImage(roadImage, roadXPos - roadImage.getWidth(mainGameWindow) * 3, (int) (heightOfScreen * 0.449), this);
		g2.drawImage(tracksImage, roadXPos, (int) (heightOfScreen * 0.449), this);
		g2.drawImage(tracksImage, roadXPos + tracksImage.getWidth(mainGameWindow), (int) (heightOfScreen * 0.449), this);
		g2.drawImage(tracksImage, roadXPos + tracksImage.getWidth(mainGameWindow) * 2, (int) (heightOfScreen * 0.449), this);
		g2.drawImage(tracksImage, roadXPos - tracksImage.getWidth(mainGameWindow), (int) (heightOfScreen * 0.449), this);
		g2.drawImage(tracksImage, roadXPos - tracksImage.getWidth(mainGameWindow) * 2, (int) (heightOfScreen * 0.449), this);
		if (roadXPos > mainGameWindow.getWidth() / 2)
		{
			roadXPos = -mainGameWindow.getWidth() / 7;
		}
		if (roadXPos < -mainGameWindow.getWidth() / 7)
		{
			roadXPos = mainGameWindow.getWidth() / 2;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == paintTicker)
		{
			repaint();
		}
		if (e.getSource() == animationTicker)
		{
			if (util.moveLeft)
			{
				isGoingLeft = true;
				isGoingRight = false;
				isNotMoving = false;
				if(!isNotMoving)
					{
					pictureCounter = (pictureCounter + 1) % 8;
					}
				thomasImageIcon = images[pictureCounter];
				roadXPos = roadXPos + 10;
				repaint();
			}
			if (util.moveRight)
			{
				isGoingRight = true;
				isGoingLeft = false;
				isNotMoving = false;
				if(!isNotMoving)
				{
				pictureCounter = (pictureCounter + 1) % 8;
				}
				thomasImageIcon = images[pictureCounter];

				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				// pictureCounter = (pictureCounter - 1);
				roadXPos = roadXPos - 10;
				repaint();
				System.out.println(pictureCounter);
			}
			if (util.moveLeft && util.moveRight)
			{
				isGoingRight = false;
				isGoingLeft = false;
				isNotMoving = true;
//				thomasImageIcon = images[pictureCounter];
				System.out.println(pictureCounter);
//				repaint();
			}
		}
	}
}
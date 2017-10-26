import javax.swing.*;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private Timer movementTimer = new Timer(20, util);
	private ImageIcon thomasImage = new ImageIcon();
	private int pictureCounter;
	private int thomasXPos = widthOfScreen/2;
	private Image roadImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("ground.png"));
	private Image tracksImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Standard Gauge Train Track Sprite.png"));
	private int roadXPos = 0;
	URL thomasThemeAddress = getClass().getResource("Thomas The Tank Engine Theme Song.wav");
	AudioClip thomasThemeSong = JApplet.newAudioClip(thomasThemeAddress);
	
	//TODO: CREATE A FORMULA THAT SPEEDS UP AND SLOWS DOWN THOMAS AS THE ARROW KEYS MOVE HIM

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new ThomasShootEmUpController());
	}
	public void run()
	{
		paintTicker.start();
		animationTicker.start();
//		movementTimer.start();
		images[0] = new ImageIcon("src/Thomas1.png");
		images[1] = new ImageIcon("src/Thomas2.png");
		images[2] = new ImageIcon("src/Thomas3.png");
		images[3] = new ImageIcon("src/Thomas4.png");
		images[4] = new ImageIcon("src/Thomas5.png");
		images[5] = new ImageIcon("src/Thomas6.png");
		images[6] = new ImageIcon("src/Thomas7.png");
		images[7] = new ImageIcon("src/Thomas8.png");
		gun = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Minigun_SU.png"));
		mainGameWindow.setTitle("Animation Study");
		mainGameWindow.setSize(widthOfScreen, heightOfScreen);
		mainGameWindow.add(this);// Adds the paint method to the JFrame
		mainGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGameWindow.getContentPane().setBackground(new Color(200,235,255));
		mainGameWindow.setVisible(true);
		mainGameWindow.addKeyListener(util);
		thomasThemeSong.play();
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.scale((double)(1/g2.getTransform().getScaleX())*(.9), 1*(.9));
		g2.drawImage(gun, thomasXPos + 150, (int) (heightOfScreen * 0.70), -gun.getWidth(this)/2, gun.getHeight(this)/2, null);
		thomasImage.paintIcon(this, g2, thomasXPos, (int) (heightOfScreen * 0.69));
		g2.scale(2, 2);
		g2.drawImage(tracksImage, roadXPos, (int) (heightOfScreen * 0.449), this);
		g2.drawImage(tracksImage, roadXPos + tracksImage.getWidth(mainGameWindow), (int) (heightOfScreen * 0.449), this);
		g2.drawImage(tracksImage, roadXPos + tracksImage.getWidth(mainGameWindow)*2, (int) (heightOfScreen * 0.449), this);
		g2.drawImage(tracksImage, roadXPos - tracksImage.getWidth(mainGameWindow), (int) (heightOfScreen * 0.449), this);
		g2.drawImage(tracksImage, roadXPos - tracksImage.getWidth(mainGameWindow)*2, (int) (heightOfScreen * 0.449), this);
		if (roadXPos > mainGameWindow.getWidth()/2)
		{
			roadXPos = -mainGameWindow.getWidth()/7;
		}
		g2.drawImage(roadImage, roadXPos, (int) (heightOfScreen * 0.8), this);
		
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
			pictureCounter = (pictureCounter + 1) % 8;
			thomasImage = images[pictureCounter];
			roadXPos = roadXPos + 10;
			repaint();
		}
	}
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

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
	private ImageIcon nextImage = new ImageIcon();
	private int pictureCounter;
	private int thomasXPos = widthOfScreen;
	private Image roadImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("ground.png"));
	private int roadXPos = 0;
	

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
		mainGameWindow.setTitle("Animation Study");
		mainGameWindow.setSize(widthOfScreen, heightOfScreen);
		mainGameWindow.add(this);// Adds the paint method to the JFrame
		mainGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGameWindow.getContentPane().setBackground(new Color(200,235,255));
		mainGameWindow.setVisible(true);
	}

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.scale(7, 1);
		g2.drawImage(roadImage, 0, (int) (heightOfScreen * 0.8), this);
		g2.scale((double)(1/g2.getTransform().getScaleX())*(.9), 1*(.9));
		g2.drawImage(gun, thomasXPos+150, (int) (heightOfScreen * 0.70), -gun.getWidth(this)/2, gun.getHeight(this)/2, null);
		nextImage.paintIcon(this, g2, thomasXPos, (int) (heightOfScreen * 0.69));
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
			nextImage = images[pictureCounter];
			thomasXPos = thomasXPos - 18;
			repaint();
		}
	}
}
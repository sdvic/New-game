import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

public class NewGame extends JComponent implements ActionListener, Runnable
{
    private ImageIcon[] images = new ImageIcon[8];
    private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private JFrame mainGameWindow = new JFrame("NewGame");//Makes window with title "NewGame"
    private Timer paintTicker = new Timer(20, this);
    private Timer animationTicker = new Timer(50, this);
    private ImageIcon nextImage = new ImageIcon();
    private int pictureCounter;
    private int uglyGuyXPos = widthOfScreen;
    private Line2D.Double sideWalk = new Line2D.Double(widthOfScreen, heightOfScreen * 0.8, 0, heightOfScreen * 0.8);
    private Line2D.Double sideWalk2 = new Line2D.Double(widthOfScreen, heightOfScreen * 0.9, 0, heightOfScreen * 0.9);
    private Image roadImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("ground.png"));
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new NewGame());
    }

    public void run()
    {
    	paintTicker.start();
    	animationTicker.start();
        images[0] = new ImageIcon("src/pic0.PNG");
        images[1] = new ImageIcon("src/pic1.PNG");
        images[2] = new ImageIcon("src/pic2.PNG");
        images[3] = new ImageIcon("src/pic3.PNG");
        images[4] = new ImageIcon("src/pic4.PNG");
        images[5] = new ImageIcon("src/pic5.PNG");
        images[6] = new ImageIcon("src/pic6.PNG");
        images[7] = new ImageIcon("src/pic7.PNG");
        mainGameWindow.setTitle("Animation Study");
        mainGameWindow.setSize(widthOfScreen, heightOfScreen);
        mainGameWindow.add(this);//Adds the paint method to the JFrame
        mainGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameWindow.setVisible(true);
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        
        g2.draw(sideWalk);
        g2.draw(sideWalk2);
        g2.drawImage(roadImage, 0, (int)(heightOfScreen*0.8), this);
        nextImage.paintIcon(this, g2, uglyGuyXPos, (int)(heightOfScreen * 0.6));
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
    		uglyGuyXPos = uglyGuyXPos - 17;
			repaint();
		}
    }
}
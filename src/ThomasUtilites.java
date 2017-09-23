import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ThomasUtilites implements KeyListener, ActionListener
{
	private boolean moveFaster;
	private boolean turnLeft;
	private boolean turnRight;
	private boolean slowDown;
	private boolean shoot;

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		System.out.println("dfsd");
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			this.turnLeft = true;
			System.out.println("left");
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			moveFaster = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			slowDown = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			turnRight = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			turnLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			moveFaster = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			slowDown = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			turnRight = false;
		}
		
	}

}

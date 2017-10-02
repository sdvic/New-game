import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class ThomasUtilites implements KeyListener, ActionListener
{
	private boolean jump;
	private boolean moveLeft;
	private boolean moveRight;
	private boolean slowDown;
	private boolean shoot;
	private JFrame jf;

	
	public ThomasUtilites()
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
//	System.out.println("tic");	
	}
	
	public void name()
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
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			this.moveLeft = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			jump = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			slowDown = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			moveRight = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			moveLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			jump = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			slowDown = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			moveRight = false;
		}
		
	}

}

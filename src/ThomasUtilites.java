import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.Timer;

public class ThomasUtilites implements KeyListener, ActionListener
{
	public boolean isJumping;
	public boolean moveLeft;
	public boolean moveRight;
	public boolean slowDown;
	private boolean shoot;
	public int velocity;
	private int thomasSpeed;
	private Timer accelerationRegulator;

	public ThomasUtilites(int velocity)
	{
		this.velocity = velocity;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
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
			isJumping = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			slowDown = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			this.moveRight = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			this.moveLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			this.isJumping = false;
			velocity = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			this.slowDown = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			this.moveRight = false;
		}

	}

}

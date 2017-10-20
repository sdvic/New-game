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
	private boolean jump;
	private boolean moveLeft;
	private boolean moveRight;
	private boolean slowDown;
	private boolean shoot;
	private JFrame jf;
	public Clip music;
	private int thomasSpeed;
	private Timer accelerationRegulator;
	
	public ThomasUtilites()
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (moveLeft == true && accelerationRegulator){
			
		}
	}
	
	public void name()
	{
		
	}

	public void playMusic()
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(getClass().getResource(
							"Thomas The Tank Engine Theme Song.wav"));
			music = AudioSystem.getClip();
			music.open(audioInputStream);
			music.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception ex)
		{
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
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
			System.out.println("left");
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			jump = true;
			System.out.println("jump");
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			slowDown = true;
			System.out.println("down");
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			this.moveRight = true;
			System.out.println("right");
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
			this.jump = false;
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

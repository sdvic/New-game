import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FlashCards implements KeyListener
{

	public static int cardPicker;
	public static String englishAlphabet = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	public static String saurianUpperCaseAlphabet = new String("URSTOVWXAZBCMDEFGHJKILNP0Q");
	public static String saurianLowerCaseAlphabet = new String("urstovwxazbcmdefghjkilnp0q");
	public JFrame letterFrame;
	public JFrame rectangleFrame = new JFrame();
	
	public static void main(String[] args)
	{
		new FlashCards().getGoing();
	}
	public void getGoing()
	{
		rectangleFrame.setSize(600, 500);
		rectangleFrame.setVisible(true);
		cardPicker = (int) (Math.random() * 26);
		letterFrame = new JFrame("Saurian Flashcards");
		JPanel letterPanel = new JPanel();
		letterFrame.add(letterPanel);
		letterFrame.setSize(600, 500);
		letterPanel.setSize(600, 500);
		letterFrame.setVisible(true);
		letterFrame.setDefaultCloseOperation(letterFrame.EXIT_ON_CLOSE);
		char enlgishLetter = (englishAlphabet.charAt(cardPicker));
		JLabel instructionsArea = new JLabel();
		JLabel textArea = new JLabel();
		letterPanel.add(instructionsArea);
		letterPanel.add(textArea);
		letterFrame.setLayout(new BorderLayout());
		instructionsArea.setText("<html>Press the Saurian letter that corresponds with the English letter you see.<br>The Saurian alphabet corresponds like this:<br>" + englishAlphabet + "<br>" + saurianUpperCaseAlphabet + "<html>");
		textArea.setText("" + enlgishLetter);
		letterPanel.add(textArea);
		textArea.setFont(new Font("Bank Gothic", Font.BOLD, 372));
		letterFrame.addKeyListener(this);
		letterFrame.setBackground(Color.green);
	}
	public void Paint(Graphics2D g2)
	{
		rectangleFrame.paint(g2);
	}
	@Override
	public void keyTyped(KeyEvent e)
	{
		if(e.getKeyChar() == (saurianUpperCaseAlphabet.charAt(cardPicker)) || e.getKeyChar() == (saurianLowerCaseAlphabet.charAt(cardPicker)))
				{
			System.out.println("Correct");
			
			getGoing();
			}
		else{
			System.out.println("wrong");
		}

	}
	@Override
	public void keyPressed(KeyEvent e)
	{
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
	}

}

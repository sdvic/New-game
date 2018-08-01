import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.invoke.WrongMethodTypeException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/***********************************************************************************************
 * David Frieder's Saurian flash cards Copyright 2018 David Frieder 7/30/2018
 * rev 1.1 keeps score and displays it at the end
 ***********************************************************************************************/
public class FlashCards implements KeyListener
{

	public static int cardPicker;
	public static int correctScore;
	public static int wrongAnswerCounter;
	public static int totalTrials;
	public static int trialsLimit;

	{

	}

	public static String englishAlphabet = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	public static String saurianUpperCaseAlphabet = new String("URSTOVWXAZBCMDEFGHJKILNP0Q");
	public static String saurianLowerCaseAlphabet = new String("urstovwxazbcmdefghjkilnp0q");
	public JFrame letterFrame;
	public JFrame rectangleFrame = new JFrame();
	public char enlgishLetter;
	public JLabel textArea = new JLabel();
	public JLabel instructionsArea = new JLabel();
	public JPanel letterPanel = new JPanel();

	public static void main(String[] args)
	{
		new FlashCards().getGoing();
	}
	public void getGoing()
	{
		cardPicker = (int) (Math.random() * 26);
		correctScore = 0;
		wrongAnswerCounter = 0;
		totalTrials = 0;
		trialsLimit = 20;
		letterFrame = new JFrame("Saurian Flashcards");
		enlgishLetter = (englishAlphabet.charAt(cardPicker));
		letterFrame.add(letterPanel);
		letterFrame.setSize(600, 500);
		letterPanel.setSize(600, 500);
		letterFrame.setVisible(true);
		letterFrame.setDefaultCloseOperation(letterFrame.EXIT_ON_CLOSE);
		letterPanel.add(instructionsArea);
		letterPanel.add(textArea);
		letterFrame.setLayout(new BorderLayout());
		instructionsArea.setText("<html>Press the Saurian letter that corresponds with the English letter you see.<br>The Saurian alphabet corresponds like this:<br>" + englishAlphabet + "<br>" + saurianUpperCaseAlphabet + "<html>");
		textArea.setText("" + enlgishLetter);
		letterPanel.add(textArea);
		textArea.setFont(new Font("Bank Gothic", Font.BOLD, 372));
		letterFrame.addKeyListener(this);

	}
	public void letterReset(JPanel letterPanel, char enlgishLetter, JLabel textArea)
	{
		cardPicker = (int) (Math.random() * 26);
		enlgishLetter = (englishAlphabet.charAt(cardPicker));
		textArea.setText("" + enlgishLetter);
		letterPanel.add(textArea);
	}
	public void paint(Graphics2D g2)
	{
		rectangleFrame.paint(g2);
	}
	@Override
	public void keyTyped(KeyEvent e)
	{

		if (e.getKeyChar() == (saurianUpperCaseAlphabet.charAt(cardPicker)) || e.getKeyChar() == (saurianLowerCaseAlphabet.charAt(cardPicker)))
		{
			// System.out.println("Correct");
			letterPanel.setBackground(Color.green);
			letterReset(letterPanel, enlgishLetter, textArea);
			correctScore++;
		} else
		{
			// System.out.println("wrong");
			letterPanel.setBackground(Color.red);
			wrongAnswerCounter++;
		}
		totalTrials++;
		if (totalTrials >= trialsLimit)
		{
			instructionsArea.setText("Congratulations. Here is your score:");
			letterPanel.setBackground(Color.yellow);
			textArea.setFont(new Font("Bank Gothic", Font.PLAIN, 22));
			textArea.setText("Correct answers: " + correctScore + "\nWrong answers: " + wrongAnswerCounter);
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

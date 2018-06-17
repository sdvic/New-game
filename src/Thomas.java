import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class Thomas
{
	int thomasBoxWidth;
	int thomasBoxHeight;
	Rectangle thomasBox;
	Shape thomasShape;
	private Image[] thomasSpriteImageArray = new Image[8];
	private Image[] reverseThomasImageArray = new Image[8];
	private AffineTransform thomasTransform = new AffineTransform();
	private Image thomasSpriteImage;
	private Image reverseThomasImage;
	private int thomasSpriteImageCounter;
	private int thomasMaxSpeed = 13;
	private int thomasYOffsetFromGround = 0;
	private Graphics2D g2;

	public Thomas()
	{
		System.out.println("thomas is REAL");
	}
	private void paintSelf(Graphics2D g2)
	{

	}
	public void setG2(Graphics2D g2)
	{
		this.g2 = g2;
	}
}

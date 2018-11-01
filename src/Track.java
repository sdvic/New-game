import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Track
{
    private Image trackSectionImage;
    private Rectangle2D.Double trackBoundingBox;
    private int numberOfTractSecions;


    public Track(Point trackPosition, int numberOfTrackSections)
    {
        trackSectionImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Tracks.png"));
        trackBoundingBox = new Rectangle2D.Double(trackPosition.x, trackPosition.y, trackSectionImage.getWidth(null) * numberOfTrackSections, trackSectionImage.getHeight(null));
        this.numberOfTractSecions = numberOfTrackSections;
    }

    public Image getTrackSectionImage()
    {
        return trackSectionImage;
    }

    public Rectangle2D.Double getTrackBoundingBox()
    {
        return trackBoundingBox;
    }

    public int getNumberOfTractSecionss()
    {
        return numberOfTractSecions;
    }
}

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Image;


/*Cue.java 
 * 
 * ---------------------------------------------------------------------
 *Cue class that handles the interaction of the user with the cue and the
 *shot to realize. It calculates the initial force of the shot and its 
 *direction. It contains the repaint instructions to draw the cue and the 
 *projection of a shot. It uses mouse listeners to detect the wanted
 *placement position of the cue. 
 * ----------------------------------------------------------------------
 * 
 * Billiard Project, Phase 1.4 
 * Team #1 Knowledge Engineering, Maastricht University.
 */


public class Cue implements MouseListener
{
	public double initialVelocity;
	public double shotDirection;
	public Image cueImage;
	
	

	public Cue()
	{
		
	}
	
	public void mouseClicked (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {} 
	public void mouseExited (MouseEvent e) {} 
	
	//
	
}

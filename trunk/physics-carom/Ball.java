import java.awt.Image;
import javax.swing.ImageIcon;



/*Ball.java 
 * 
 * ---------------------------------------------------------------------
 * Ball class that contains the temporal movement parameters of the ball
 * such as speed, direction vector, acceleration, initial velocity and  
 * spin effect. It also contains the permanent properties of the ball 
 * such as mass, color and superficial friction.
 * A new object has to be implemented for each ball to use, then all its
 * movement parameters can be added or edited as the game develops.
 * Each ball has to control the repaint of the ball acording to its speed
 * and movement.
 * ----------------------------------------------------------------------
 * 
 * Billiard Project, Phase 1.4 
 * Team #1 Knowledge Engineering Maastricht University
 */

public class Ball 
{
	public double ballWeight;
	public double speed;
	public double directionVector; //Need to know how to represent
	
	public double movementMagn;
	public double movementAngle;
	public int[] movementRect; 
	
	public double remainingForce;
	
	public String color;
	
	public int xPosition; 
	public int yPosition;

	public int diameter =  16; //in pixels, size of the ball's diameter
	public double radius = diameter/2;
	public double mass = 0.5;//in Kg
	
	public Image ballImage;

	
	public Ball(String color)
	{
		if(color == "red")
		{
			
			ImageIcon redBallIcon = new ImageIcon("Red ball.JPG");
			ballImage = redBallIcon.getImage();	
		}
		else if(color == "white")
		{
			ImageIcon whiteBallIcon = new ImageIcon("White ball.JPG");
			ballImage = whiteBallIcon.getImage();
		}	
		
		
		
		this.color = color;
	}
	
	public void setBallPosition(int xPosition, int yPosition)
	{
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		
	}
	
	public int getBallxPosition()
	{
		return xPosition;
	}
	
	public int getBallyPosition()
	{
		return yPosition;
	}
	
}

import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;

/*Table.java 
 * 
 * ---------------------------------------------------------------------
 *Table class that is in charge of controlling the interaction between all the 
 *elements of the game be receiving information about the shot made; asking the
 *physics class to compute the shot/balls interaction and then pass that
 *data to the balls which will move according to the computation. 
 * ----------------------------------------------------------------------
 * 
 * Billiard Project, Phase 1.4
 * Team #1 Knowledge Engineering, Maastricht University.
 */
public class Table extends JComponent
{
	public double frictionCoefficient;
	//To add other noise generating factors.
	public static int topMaxCoordinate = 47;
	public static int bottomMaxCoordinate = 490;
	public static int rightMaxCoordinate = 258;
	public static int leftMaxCoordinate = 46;
	public Image imgBackground;

	Ball cueBall, redBall1, redBall2;
	GamePhysics physics;
	Cue cue;
	
	public int mousexPosition;
	public int mouseyPosition;
	public boolean showCue;
	public double shotAngle;
	public double shotForce;
	public boolean setStart = false;
	
	
	public Table()
	{
		physics = new GamePhysics();
		cue = new Cue();
		
		ImageIcon imgBackgroundIcon = new ImageIcon("Remake_Carom_Board.JPG");
		imgBackground = imgBackgroundIcon.getImage();
		
		cueBall = new Ball("white");
		redBall1 = new Ball("red");
		redBall2 = new Ball("red");
		
		cueBall.setBallPosition(150,350);
		redBall1.setBallPosition(200, 150);
		redBall2.setBallPosition(100, 150);
		
		addMouseMotionListener(new MouseMotionHandler());
//		addMouseMotionListener(new Mousy());
//		addMouseListener(new MousePerform());
		
	}

	public void setStart(boolean setStart)
	{
		this.setStart = setStart;
	}
	
	public void setShotMagnitude(double shotForce)
	{
		this.shotForce = shotForce;
	}
	
	public void moveBall(Ball moveBall, int xDistance, int yDistance)
	{
		GraphicInterface.output.append("\n moveBall"+ moveBall.xPosition + xDistance + ", " + moveBall.yPosition + yDistance );	
		moveBall.setBallPosition(moveBall.xPosition + xDistance, moveBall.yPosition + yDistance);	
	}
	
	public void motionBalls()
	{
		
		cueBall.movementMagn = shotForce/5;
		cueBall.movementAngle = shotAngle; //Must add 180 degrees to push the ball in the correct direction.
		cueBall.movementRect = physics.polartoRectangle(cueBall.movementMagn, cueBall.movementAngle);
		
		GraphicInterface.output.append("\n Movement Rect" + Integer.toString(cueBall.movementRect[0]) + ", " + Integer.toString(cueBall.movementRect[1]));	
		
		redBall1.movementMagn = 0;
		redBall1.movementAngle = 0;
		redBall1.movementRect = physics.polartoRectangle(redBall1.movementMagn, redBall1.movementAngle);
		
		redBall2.movementMagn = 0;
		redBall2.movementAngle = 0; //Must add 180 degrees to push the ball in the correct direction.
		redBall2.movementRect = physics.polartoRectangle(redBall2.movementMagn, redBall2.movementAngle);
		
		
		double frictionMagn = physics.frictionForce(cueBall);
		int [] frictionRect = physics.polartoRectangle(frictionMagn, shotAngle);
		//Calculate the frictionRect coordinates using the opposite angle  
		//of the movementAngle.
		
		cueBall.remainingForce = cueBall.movementMagn;
		redBall1.remainingForce = redBall1.movementMagn;
		redBall2.remainingForce = redBall2.movementMagn;
		
		//Modify this to a more dynamic.
		do
		{
			cueBall.remainingForce = cueBall.remainingForce * frictionMagn;
			redBall1.remainingForce = redBall1.remainingForce * frictionMagn;
			redBall2.remainingForce = redBall2.remainingForce * frictionMagn;
			
			moveBall(cueBall, changeCoordinates(cueBall.movementRect)[0], changeCoordinates(cueBall.movementRect)[1]);
			moveBall(redBall1, changeCoordinates(redBall1.movementRect)[0], changeCoordinates(redBall1.movementRect)[1]);
			moveBall(redBall2, changeCoordinates(redBall2.movementRect)[0], changeCoordinates(redBall2.movementRect)[1]);
			
			cueBall.movementRect[0] = cueBall.movementRect[0] * physics.wallCollision(cueBall)[0];
			cueBall.movementRect[1] = cueBall.movementRect[1] * physics.wallCollision(cueBall)[1];
			redBall1.movementRect[0] = redBall1.movementRect[0] * physics.wallCollision(redBall1)[0];
			redBall1.movementRect[1] = redBall1.movementRect[1] * physics.wallCollision(redBall1)[1];
			redBall2.movementRect[0] = redBall2.movementRect[0] * physics.wallCollision(redBall2)[0];
			redBall2.movementRect[1] = redBall2.movementRect[1] * physics.wallCollision(redBall2)[1];
			
			if(physics.ballCollison(cueBall, redBall1))
			{
				double ballCollisionAngle = physics.ballCollisionAngle(cueBall.remainingForce, cueBall.movementAngle);
				redBall1.movementRect = physics.polartoRectangle(cueBall.remainingForce, (ballCollisionAngle+180)%360);
				GraphicInterface.output.append("\n cueBall vs redBall1 Collision");	
			}
			if(physics.ballCollison(cueBall, redBall2))
			{
				double ballCollisionAngle = physics.ballCollisionAngle(cueBall.remainingForce, cueBall.movementAngle);
				redBall2.movementRect = physics.polartoRectangle(cueBall.remainingForce, (ballCollisionAngle+180)%360);
				GraphicInterface.output.append("\n cueBall vs redBall2 Collision");	
			}
			if(physics.ballCollison(redBall1, redBall2))
			{
				
				if(redBall1.remainingForce > redBall2.remainingForce)
				{
					double ballCollisionAngle1 = physics.ballCollisionAngle(redBall1.remainingForce, (redBall1.movementAngle+180)%360);
					redBall1.movementRect = physics.polartoRectangle(redBall1.remainingForce, ballCollisionAngle1);
					
					double ballCollisionAngle2 = physics.ballCollisionAngle(redBall1.remainingForce, redBall1.movementAngle);
					redBall2.movementRect = physics.polartoRectangle(redBall1.remainingForce, ballCollisionAngle2);
				}	
				else if(redBall2.remainingForce > redBall1.remainingForce)
				{
					double ballCollisionAngle1 = physics.ballCollisionAngle(redBall2.remainingForce, (redBall2.movementAngle+180)%360);
					redBall2.movementRect = physics.polartoRectangle(redBall2.remainingForce, ballCollisionAngle1);
					
					double ballCollisionAngle2 = physics.ballCollisionAngle(redBall2.remainingForce, redBall2.movementAngle);
					redBall1.movementRect = physics.polartoRectangle(redBall2.remainingForce, ballCollisionAngle2);
				}
				
				GraphicInterface.output.append("\n redBall1 vs redBall2 Collision");		
			}			
			
			try {
				Thread.sleep(1000/25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
			
			
		}while(cueBall.remainingForce > frictionMagn);
	}
	
	public boolean areStopped()
	{
		if(cueBall.movementMagn < 0.918 && redBall1.movementMagn < 0.918 && redBall2.movementMagn < 0.918 )
			return true;
		else
			return false;
	
	}
	
	
	public int[] changeCoordinates(int[] movementRect)
	{
		int [] newmovementRect = new int[2];
		
		if(Math.signum(movementRect[0]) == 0 && Math.signum(movementRect[1]) == 1.0)
		{
			newmovementRect[0] = (int)movementRect[0];
			newmovementRect[1] = -1*(int)movementRect[1];
			GraphicInterface.output.append("\n Up shot detected");		
		}
		
		else if(Math.signum(movementRect[0]) == -1.0 && Math.signum(movementRect[1]) == 0)
		{
			newmovementRect[0] = (int)movementRect[0];
			newmovementRect[1] = (int)movementRect[1];
			GraphicInterface.output.append("\n Left shot detected");
		}
		
		else if(Math.signum(movementRect[0]) == 0 && Math.signum(movementRect[1]) == -1.0)
		{
			newmovementRect[0] = (int)movementRect[0];
			newmovementRect[1] = -1*(int)movementRect[1];
			GraphicInterface.output.append("\n Down shot detected");
		}
		else if(Math.signum(movementRect[0]) == 1.0 && Math.signum(movementRect[1]) == 0)
		{
			newmovementRect[0] = (int)movementRect[0];
			newmovementRect[1] = (int)movementRect[1];
			GraphicInterface.output.append("\n Right shot detected");
		}
		else if(Math.signum(movementRect[0]) == 1.0 && Math.signum(movementRect[1]) == 1.0)
		{
			newmovementRect[0] = (int)movementRect[0];
			newmovementRect[1] = -1*(int)movementRect[1];
			
		}	
		else if(Math.signum(movementRect[0]) == -1.0 && Math.signum(movementRect[1]) == 1.0)
		{
			newmovementRect[0] = (int)movementRect[0];
			newmovementRect[1] = -1*(int)movementRect[1];
			
		}
		else if(Math.signum(movementRect[0]) == -1.0 && Math.signum(movementRect[1]) == -1.0)
		{
			newmovementRect[0] = (int)movementRect[0];
			newmovementRect[1] = -1*(int)movementRect[1];
		
		}
		else if(Math.signum(movementRect[0]) == 1.0 && Math.signum(movementRect[1]) == -1.0)
		{
			newmovementRect[0] = (int)movementRect[0];
			newmovementRect[1] = -1*(int)movementRect[1];

		}
		
		return newmovementRect;
		
	}
	
	public void paintComponent(Graphics g)
	{
		  Graphics2D g2D = (Graphics2D) g;
		  Graphics2D cueGraphics = (Graphics2D) g;
		  Graphics2D shootGraphics = (Graphics2D) g;
		  
		  
		  g2D.drawImage(imgBackground, 0, 0, null);
		  
		  g2D.setStroke(new BasicStroke(2));
		  
		  Shape ballFill =  new Ellipse2D.Double(cueBall.xPosition - cueBall.radius, cueBall.yPosition - cueBall.radius, cueBall.diameter, cueBall.diameter);
		  Area ballArea = new Area(ballFill);
		  g2D.setColor(Color.BLACK);
		  g2D.draw(ballFill);
		  g2D.setColor(Color.WHITE);
		  g2D.fill(ballArea);
		  
		  ballFill =  new Ellipse2D.Double(redBall1.xPosition - redBall1.radius, redBall1.yPosition - redBall1.radius, redBall1.diameter, redBall1.diameter);
		  ballArea = new Area(ballFill);
		  g2D.setColor(Color.BLACK);
		  g2D.draw(ballFill);
		  g2D.setColor(Color.RED);
		  g2D.fill(ballArea);
		  
		  ballFill =  new Ellipse2D.Double(redBall2.xPosition - redBall2.radius, redBall2.yPosition - redBall2.radius, redBall2.diameter, redBall2.diameter);
		  ballArea = new Area(ballFill);
		  g2D.setColor(Color.BLACK);
		  g2D.draw(ballFill);
		  g2D.setColor(Color.RED);
		  g2D.fill(ballArea);
		  
		  if(showCue)
		  {	  
		  //Draw the cross lines in the balls
		  cueGraphics.setColor(Color.GRAY);
		  cueGraphics.setStroke(new BasicStroke(1));
		  cueGraphics.drawLine(cueBall.xPosition, cueBall.yPosition, cueBall.xPosition + 30, cueBall.yPosition);
		  cueGraphics.drawLine(cueBall.xPosition, cueBall.yPosition, cueBall.xPosition, cueBall.yPosition - 30);
		  cueGraphics.drawLine(cueBall.xPosition, cueBall.yPosition, cueBall.xPosition - 30, cueBall.yPosition);
		  cueGraphics.drawLine(cueBall.xPosition, cueBall.yPosition, cueBall.xPosition, cueBall.yPosition  + 30);
		  cueGraphics.drawLine(redBall1.xPosition, redBall1.yPosition, redBall1.xPosition + 30, redBall1.yPosition);
		  cueGraphics.drawLine(redBall1.xPosition, redBall1.yPosition, redBall1.xPosition, redBall1.yPosition - 30);
		  cueGraphics.drawLine(redBall1.xPosition, redBall1.yPosition, redBall1.xPosition - 30, redBall1.yPosition);
		  cueGraphics.drawLine(redBall1.xPosition, redBall1.yPosition, redBall1.xPosition, redBall1.yPosition  + 30);
		  cueGraphics.drawLine(redBall2.xPosition, redBall2.yPosition, redBall2.xPosition + 30, redBall2.yPosition);
		  cueGraphics.drawLine(redBall2.xPosition, redBall2.yPosition, redBall2.xPosition, redBall2.yPosition - 30);
		  cueGraphics.drawLine(redBall2.xPosition, redBall2.yPosition, redBall2.xPosition - 30, redBall2.yPosition);
		  cueGraphics.drawLine(redBall2.xPosition, redBall2.yPosition, redBall2.xPosition, redBall2.yPosition  + 30);
		  
		  //Show the Cue
		  cueGraphics.setColor(Color.BLACK);
		  cueGraphics.setStroke(new BasicStroke(2));
		  shotAngle = physics.getCueAngle(cueBall.xPosition, cueBall.yPosition, mousexPosition, mouseyPosition);
		  shotAngle = (shotAngle+180)%360;
		  cueGraphics.drawLine(cueBall.xPosition, cueBall.yPosition, mousexPosition, mouseyPosition);
		  cueGraphics.drawString(Double.toString(shotAngle), cueBall.xPosition + 5, cueBall.yPosition + 25);
		  
		  //Show the Shot's Projection Line
		  cueGraphics.rotate(Math.PI, cueBall.xPosition, cueBall.yPosition);
		  cueGraphics.setColor(Color.MAGENTA);
		  cueGraphics.setStroke(new BasicStroke(1));
		  cueGraphics.drawLine(cueBall.xPosition, cueBall.yPosition, mousexPosition, mouseyPosition);
		  }
		  
		  setOpaque(false);
		
		
	}
	
//	class MousePerform implements MouseListener {
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			GraphicInterface.performShot();
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
	class MouseMotionHandler extends MouseMotionAdapter 
	{
		    public void mouseDragged(MouseEvent e) 
		    {
		      mousexPosition = e.getX();
		      mouseyPosition = e.getY();
		      repaint();
		    }
	}
	
	class Mousy implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			mousexPosition = e.getX();
		    mouseyPosition = e.getY();
		    repaint();
		}
		
	}

}

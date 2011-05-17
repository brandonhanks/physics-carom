/*GamePhysics.java 
 * 
 * ---------------------------------------------------------------------
 *GamePhysics class that is in charge of computing the properties of 
 *movement when the balls, cue and board interact. 
 *This class can contain the physical parameters of the board like 
 *friction and noise generating factors.
 *This class should compute the resulting movement and directions of the 
 *ball. Also the object collisions are handled here.
 * ----------------------------------------------------------------------
 * 
 * Billiard Project, Phase 1.4 
 * Team #1 Knowledge Engineering, Maastricht University.
 */


public class GamePhysics 
{
	public double gravityForce = 9.81;
	public double frictionCoefficient = 0.2;
	
	
	public GamePhysics()
	{
		
	}
	
	public boolean ballCollison(Ball ball1, Ball ball2)
	{
		if(eucledian(ball1.xPosition, ball1.yPosition, ball2.xPosition, ball2.yPosition) <= ball1.radius*2){
			{
			return true;
			}
		}else{
			return false;
		}
	}
	
	public double ballCollisionAngle(double magnitude, double angle)
	{
		double xAngle = (angle + 270)%360;
	
		int xCoordinate = polartoRectangle(magnitude, xAngle)[0];
		int yCoordinate = polartoRectangle(magnitude, angle)[1];
		
		return rectangletoPolar(xCoordinate, 0, 0, yCoordinate)[1];
		
	}
	
	public int[] wallCollision(Ball ball1)
	{
		if(eucledian(ball1.xPosition, ball1.yPosition, ball1.xPosition, Table.topMaxCoordinate) <= ball1.radius)
		{
			GraphicInterface.output.append("\n Top border Collision");
			int [] directionCollisions = new int[2];
			directionCollisions[0] = 1;
			directionCollisions[1] = -1;
			return directionCollisions;
		}
		else
		{
			if(eucledian(ball1.xPosition, ball1.yPosition, Table.rightMaxCoordinate, ball1.yPosition ) <= ball1.radius)
			{
				GraphicInterface.output.append("\n Right border Collision");
				int [] directionCollisions = new int[2];
				directionCollisions[0] = -1;
				directionCollisions[1] = 1;
				return directionCollisions;
			}
			else
			{
				if(eucledian(ball1.xPosition, ball1.yPosition, ball1.xPosition, Table.bottomMaxCoordinate) <= ball1.radius)
				{
					GraphicInterface.output.append("\n Bottom border Collision");
					int [] directionCollisions = new int[2];
					directionCollisions[0] = 1;
					directionCollisions[1] = -1;
					return directionCollisions;
				}
				else
				{
					if(eucledian(ball1.xPosition, ball1.yPosition,  Table.leftMaxCoordinate, ball1.yPosition) <= ball1.radius)
					{
						GraphicInterface.output.append("\n Left border Collision");
						int [] directionCollisions = new int[2];
						directionCollisions[0] = -1;
						directionCollisions[1] = 1;
						return directionCollisions;
					 }
					else
					{
						int [] directionCollisions = new int[2];
						directionCollisions[0] = 1;
						directionCollisions[1] = 1;
						return directionCollisions;
					}
				}
			}
		}
		
		
	}
	
	
	public double eucledian(int xOrigin, int yOrigin, int xNewCoordinate, int yNewCoordinate){
		return Math.sqrt(Math.pow( xOrigin-xNewCoordinate, 2) + Math.pow(yOrigin-yNewCoordinate, 2));
	}
	
		public double getCueAngle(int xOrigin, int yOrigin, int xNewCoordinate, int yNewCoordinate)
	{
		double angle = Math.atan2(yOrigin - yNewCoordinate, xOrigin - xNewCoordinate) * 180 / Math.PI;
		return 180-angle;
	}
	
	public double[] rectangletoPolar(int xOrigin, int yOrigin, int xCoordinate, int yCoordinate) 
	{
		double magnitude = Math.sqrt(Math.pow(xOrigin - xCoordinate, 2) + Math.pow(yOrigin - yCoordinate, 2));
		double angle = getCueAngle(xOrigin, yOrigin, xCoordinate, yCoordinate);
		//angle = Math.toDegrees(angle);
		double[] polarCoordinates = new double[2];
		
		polarCoordinates[0]= magnitude;
		polarCoordinates[1]= angle;
				
		return polarCoordinates;	
		
	}
	
	public int[] polartoRectangle(double magnitude, double angle)
	{
		double radians = Math.PI/180;
		int[] rectangularCoordinates = new int[2];
		
		rectangularCoordinates[0]= round(magnitude * Math.cos(angle*radians));
		rectangularCoordinates[1]= round(magnitude * Math.sin(angle*radians));;
				
		return rectangularCoordinates;	
		
	}
	
	public double frictionForce(Ball ball)
	{
		//Correct decimal points.
		double frictionForce =  ball.mass * gravityForce * frictionCoefficient; 
		return 0.918;
	}
	
	public int round(double doubleNumber)
	{
		return (int)(doubleNumber + 0.5);
	}
}

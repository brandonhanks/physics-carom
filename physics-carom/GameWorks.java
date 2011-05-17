/*GameWorks.java 
 * 
 * ---------------------------------------------------------------------
 *In charge of controlling the basic operations during the game
 *as determining the turns, the input of the players and starting
 *the motion in the table. 
 * ----------------------------------------------------------------------
 * 
 * Billiard Project, Phase 1.4
 * Team #1 Knowledge Engineering, Maastricht University.
 */



public class GameWorks
{
	Table table;

	public GameWorks(Table component)
	{
		table = component;
	}
	
	
	public void beginGame()
	{
		beginTurn();
	}
	
	public void beginTurn()
	{
		table.showCue = true;
		askMagnitudes();
		table.showCue = false;
		table.motionBalls();	
	}
	
	
	public void askMagnitudes()
	{
		
		do
		{
		System.out.println(" ");//Awful way of waiting for input.	
		}while(table.setStart == false);
	}
	

}

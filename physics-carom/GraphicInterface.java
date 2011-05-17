import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JSlider;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;


public class GraphicInterface implements ActionListener
{
	JScrollPane scrollPane;
	static JTextArea output;
	BufferedImage image;
	static Table component;
     JSlider slider;
	
//    public static void performShot() {
////    	double shotForce = slider.getValue();
//			double shotAngle = component.shotAngle;
//			
//			
////			output.append(formatMessages("Shot done; Force: " + Double.toString(shotForce))
////				 + ", Angle:" + Double.toString(shotAngle));
//	
//			
//			component.setShotMagnitude(shotForce);
//			component.setStart(true);
//    }
        
	public JMenuBar buildMenuBar() 
	{
			JMenuBar menuBar;
		        JMenu menu, submenu;
		        JMenuItem menuItemNewGame, menuItemExit;
		        JMenuItem menuItemA, menuItemB;
		
			menuBar = new JMenuBar();

		        menu = new JMenu("File");
		        menu.getAccessibleContext().setAccessibleDescription(
		                "The File Menu");

		        menuItemNewGame = new JMenuItem("New");
		        menuItemNewGame.getAccessibleContext().setAccessibleDescription(
		                "Creates a new content");
		        menuItemExit = new JMenuItem("Exit");
		        menuItemExit.getAccessibleContext().setAccessibleDescription(
		        "Exit");
		        
		        menu.add(menuItemNewGame);
		        menu.add(menuItemExit);
		        menuBar.add(menu);
		       
		        menu = new JMenu("Configuration");
		        submenu = new JMenu("Set-Up");
		        menuItemA = new JMenuItem("A");
		        menuItemB = new JMenuItem("B");

		        submenu.add(menuItemA);
		        submenu.add(menuItemB);
		        menu.add(submenu);
		        menuBar.add(menu);

		       	return menuBar;
	}	      
	
	public Container buildContentPane() 
	{
	       	JPanel contentPane = new JPanel(new BorderLayout());
	 
	        component = new Table();
	        
	        slider = new JSlider();
		slider.setValue(50);
		
		JLabel spinSelect = new JLabel();
		spinSelect.setIcon(new ImageIcon("BallSectorz.jpg"));
		
		JPanel selector = new JPanel();//Kevin's Panel
        	JPanel northPane = new JPanel();
        	JPanel eastPanel = new JPanel();
        
        	 eastPanel.setLayout(new BorderLayout());
        	 eastPanel.setBorder(new LineBorder(Color.black, 1));
        	 //eastPanel.setBackground(Color.WHITE);
             	 
        	 selector.add(new JLabel("Spin Selector"));
        	 selector.add(spinSelect);
             	        	
        	 
        	 JButton shootButton = new JButton("Shoot");
        	 
        	 
        	 
        	 ActionListener buttonShootlistener = new ShootListener();
		 shootButton.addActionListener(buttonShootlistener);
		 
                 northPane.add(slider);
                 northPane.add(new JLabel("Power"));
                 northPane.add(shootButton);
                 
                 
                 output = new JTextArea("Billiard Game Output", 5,20);
                 output.setEditable(false);
                 JScrollPane scrollPane = new JScrollPane(output);
                 
                 
                 eastPanel.add(northPane, BorderLayout.NORTH);
        	 eastPanel.add(selector, BorderLayout.CENTER);
        	 eastPanel.add(scrollPane, BorderLayout.SOUTH);
                 
                 selector.setBackground(Color.WHITE);
                 contentPane.add(component, BorderLayout.CENTER);
                 contentPane.add(eastPanel, BorderLayout.EAST);
	        
	        
	        return contentPane;
	        
	  }
	
	
	class ShootListener implements ActionListener 
 	{
 		public void actionPerformed(ActionEvent event) 
 		{	
 			double shotForce = GraphicInterface.this.slider.getValue();
 			double shotAngle = component.shotAngle;
 			
 			
 			output.append(formatMessages("Shot done; Force: " + Double.toString(shotForce))
 				 + ", Angle:" + Double.toString(shotAngle));
 	
 			
 			component.setShotMagnitude(shotForce);
 			component.setStart(true);
 			
 		}
 	}
	
	public String formatMessages (String message)
	{
		String newLine = "\n";
		String formated = newLine.concat(message);
		return formated;
	}
	
	
	private static void buildAndDisplayGUI()
	{
		        JFrame frame = new JFrame();
		        frame.setTitle("Carom");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        
	        	frame.setResizable(false);
	        	
	        	
	        	 GraphicInterface graphicInterface = new GraphicInterface();
	        	 frame.setJMenuBar(graphicInterface.buildMenuBar());
		       	
	        	 frame.setContentPane(graphicInterface.buildContentPane());
	        	 
	        
		       	 frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		       	 frame.setVisible(true);
	
	}
	
	private void Board(Graphics g)
	{
				
	}

	public static Table returnComponent()
	{
		return component;
	}
	
	
	
	public void actionPerformed(ActionEvent e) 
	{
	        JMenuItem source = (JMenuItem)(e.getSource());
	        
	}
	

	public static void main(String[] args) 
	{
		buildAndDisplayGUI();
		GameWorks gameWorks = new GameWorks(returnComponent());
	       	//gameWorks.applyMovement();
		
	       	gameWorks.beginGame();
	       	
	}	
	
	public int shotForce;
	public int shotAngle;
	
	private static final int FRAME_WIDTH = 665;
	private static final int FRAME_HEIGHT = 590;
	
	
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class Stratego extends JFrame {
    private static final long serialVersionUID = 1L;
	Board board;
    InfoDisplay leftInfo, rightInfo;
    CheckBoxItem []settingItem;
    private JLabel gameLog;
    boolean settingBool[];
    

    Stratego() throws IOException {
      setLayout(new BorderLayout(0,0));
      leftInfo = new InfoDisplay();
      rightInfo = new InfoDisplay();  

	    leftInfo.setBackground(Stratego_Utils.BACKGROUND_COLOR);
      rightInfo.setBackground(Stratego_Utils.BACKGROUND_COLOR);
			
      JPanel boardContainer = new JPanel();
      boardContainer.setLayout(new BorderLayout(5,5));
			
			JPanel middle = new JPanel();
      SpringLayout mySpringLayout = new SpringLayout(); // spring layout to place components with more flexibility 

      JLabel background = initializeScroll(middle, boardContainer, Stratego_Utils.BACKGROUND_COLOR, mySpringLayout);
   		initializeGameLog(background); 	// to have on the scroll image

	  board = new Board(leftInfo, rightInfo, gameLog);
      boardContainer.add(board);
			
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
      add(leftInfo, BorderLayout.WEST);
      add(rightInfo, BorderLayout.EAST);
      setExtendedState(JFrame.MAXIMIZED_BOTH); // set frame as full size
        
        settingBool= new boolean [Stratego_Utils.SETTINGS.length];
        for(int i=0; i<Stratego_Utils.SETTINGS.length;i++)
        {
        	settingBool[i]=false;
        }
        
        populateMenuBar();
        //add(board,BorderLayout.CENTER);
			
				middle.setLayout(mySpringLayout);
        middle.add(boardContainer);

				// to put components relative to another component's location
        mySpringLayout.putConstraint(SpringLayout.NORTH, boardContainer, 0, SpringLayout.NORTH, this);
        mySpringLayout.putConstraint(SpringLayout.WEST, boardContainer, 0, SpringLayout.WEST, this);
        mySpringLayout.putConstraint(SpringLayout.EAST, boardContainer, 730, SpringLayout.EAST, this);
        mySpringLayout.putConstraint(SpringLayout.SOUTH, boardContainer, 600, SpringLayout.SOUTH, this);

        add(middle);
			
        setVisible(true); // display frame
    }

	private JLabel initializeScroll(JPanel middle, JPanel boardContainer, Color BckgColor, SpringLayout mySpringLayout) {
        ImageIcon imageIcon = new ImageIcon(Paths.get("","images", "scroll.png").toString());
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH);	// to resize image properly
        imageIcon = new ImageIcon(image);	// must turn back to ImageIcon for JLabel

        JLabel background = new JLabel(imageIcon);
        background.setLayout(new FlowLayout());
        middle.add(background);
        boardContainer.setBackground(BckgColor);	// has to be same BckgColor to match the rest
        middle.setBackground(BckgColor);
        background.setBackground(BckgColor);
        background.setVisible(true);

        mySpringLayout.putConstraint(SpringLayout.NORTH, background, 250, SpringLayout.NORTH, boardContainer);
        mySpringLayout.putConstraint(SpringLayout.EAST, background, 250, SpringLayout.EAST, boardContainer);

        return background;
    }

	private void initializeGameLog(JLabel background) {
        gameLog = new JLabel(Stratego_Utils.WAITING_ATTACK);
        gameLog.setFont(new Font("Serif", Font.PLAIN, 15));
        background.add(gameLog);
    }
	

    private void populateMenuBar() {  //Adds different menu items to the menu bar
		JMenuBar menuBar= new JMenuBar();
		this.setJMenuBar(menuBar);
		
		SettingsHandler settingHandler= new SettingsHandler();  //Handlers for menu items
		RestartHandler restartHandler= new RestartHandler();
		RulesHandler rulesHandler= new RulesHandler();
		UserGuideHandler userHandler= new UserGuideHandler();
		
		
		JMenu optionsMenu= new JMenu("Options");  //Menus
		
		JMenu settingsMenu= new JMenu("Variations");//Menu inside Options menu
		
		JMenuItem restart= new JMenuItem("Restart"); //Menu items for optionMenu
		restart.addActionListener(restartHandler);
		
		JMenuItem quit= new JMenuItem("Quit");
		quit.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		settingItem= new CheckBoxItem[Stratego_Utils.SETTINGS.length];  //Initialize the settingItems with the settingString array and add to settingMenu
		for(int i=0;i<Stratego_Utils.SETTINGS.length;i++)
		{
			settingItem[i]=new CheckBoxItem(Stratego_Utils.SETTINGS[i]);
			settingsMenu.add(settingItem[i]);
			settingItem[i].addItemListener(settingHandler);
		}
		
		optionsMenu.add(settingsMenu);
		optionsMenu.add(restart);
		optionsMenu.add(quit);
		
		JMenu howToMenu= new JMenu ("How To Play");  //How to menu and its items
		
		JMenuItem rules= new JMenuItem("Rules");  
		rules.addActionListener(rulesHandler);
		JMenuItem userGuide= new JMenuItem("User Guide");
		userGuide.addActionListener(userHandler);
		
		howToMenu.add(rules);
		howToMenu.add(userGuide);

		menuBar.add(optionsMenu);
		menuBar.add(howToMenu);	
	}

	private class RulesHandler implements ActionListener
    {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		try 
    		{
    			//Redirects to a webpage that contains the game rules
    			Desktop.getDesktop().browse(new URL("https://www.ultraboardgames.com/stratego/game-rules.php").toURI()); 
    		}
    		catch(Exception ex)
    		{
    			ex.printStackTrace();
;    		}
    	}
    }
	
	private class SettingsHandler implements ItemListener //Handles the checkbox items
    {

		@Override
		public void itemStateChanged(ItemEvent e) {
			for(int i=0; i<Stratego_Utils.SETTINGS.length;i++)
			{
				if(settingItem[i].isSelected())
					settingBool[i]=true;
				else
					settingBool[i]=false;
			}
			
		}
    }
	
	private class RestartHandler implements ActionListener
    {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		  gameLog.setText(Stratego_Utils.WAITING_ATTACK);
			try {
				board.initializeComponents(settingBool[0],settingBool[1],settingBool[2],settingBool[3],settingBool[4],settingBool[5]);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		  board.repaint();
    	}
    }
	
	private class UserGuideHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			Icon image = new ImageIcon(Paths.get("","images", "StrategoIconSmall.gif").toString());
				// adds message and image to the userguide
				JOptionPane.showInternalMessageDialog(null, Stratego_Utils.userGuideString, "User Guide", JOptionPane.INFORMATION_MESSAGE, image);			
				board.grabFocus();	 // to continue proper sequence of the game			
		}		
	}
    
	private class CheckBoxItem extends JCheckBoxMenuItem {  
		
	    public CheckBoxItem(String text) {
	        super(text);
	    }


	    @Override
	    protected void processMouseEvent(MouseEvent e) {
	        if (e.getID() == MouseEvent.MOUSE_RELEASED && contains(e.getPoint())) { //To prevent menu from closing after a checkbox is selected
	            doClick();
	            setArmed(true);
	        } else {
	            super.processMouseEvent(e);
	        }
	    }
	}
    
    public static void main(String[] args) throws IOException {
        Stratego stratego = new Stratego();
    }
}



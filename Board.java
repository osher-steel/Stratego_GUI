import java.awt.*;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel implements KeyListener, ActionListener{
    private HashMap<Alliance,Player> playerMap;
    
    private Pawn currentPawnToPlace;

    private JLabel gameOverLabel;
    private InfoDisplay leftInfo;
    private InfoDisplay rightInfo;
	private JLabel gameLog;
    
    private Alliance playerTurn;

    private Square square[][];

    private int xSelector; 
    private int ySelector;
    
    private int xSelected;
    private int ySelected;
    
    private boolean squareSelected;
  
    private boolean canMove; 
    private int pawnIteration;
    private boolean settingUpDone;
    private boolean passComputer;
    private boolean gameOver;
    private boolean attackInProgress;
    
    private boolean allPawnsVisible;
    private boolean attackerAdvantage;
    private boolean unknownVictor;
    private boolean movableBombs;
    private boolean oneTimeBombs;
    private boolean advancedScout;
   
    private long lastProcessed; //Registers last time that a key event was processed
    
    Board(InfoDisplay leftInfo, InfoDisplay rightInfo, JLabel gameLog) throws IOException {

    	
      	  this.leftInfo=leftInfo;
    	  this.rightInfo=rightInfo;
  		  leftInfo.setVisible(true);
    	  rightInfo.setVisible(true);
    	  
		  this.gameLog = gameLog;
    	  
    	  setBackground(Stratego_Utils.BACKGROUND_COLOR);

          
          gameOverLabel= new JLabel("Game Over");
          gameOverLabel.setForeground(new Color(60,0,100));
          gameOverLabel.setFont(new Font("Impact",Font.BOLD,50));
          
          try {
        	  gameOverLabel.setFont(new Font("Impact",Font.BOLD,50));
          }
          catch(Exception e)
          {
        	  gameOverLabel.setFont(new Font("Serif",Font.BOLD,50));
          }
          
          gameOverLabel.setAlignmentX(CENTER_ALIGNMENT);
          gameOverLabel.setVisible(false);

          add(gameOverLabel);
          
 
          initializeComponents(false,false,false,false,false,false);
          addKeyListener(this);

          
          
    }


	public void initializeComponents(boolean allPawnsVisible, boolean attackerAdvantage, boolean unknownVictor, 
		boolean movableBombs, boolean oneTimeBombs,boolean advancedScout) throws IOException { 	
   	 	
		square = new Square[10][10]; // Board made up of 10x10 squares
	   	 
	     for (int i = 0; i < 10; i++) {			//Initializes the squares with their x,y coordinate and x, y indeces
	         for (int j = 0; j < 10; j++) {
	             int xtemp = (Stratego_Utils.INITIAL_BOARD_X + (j * Stratego_Utils.TILE_LENGTH));
	             int ytemp = (Stratego_Utils.INITIAL_BOARD_Y + (i * Stratego_Utils.TILE_LENGTH));
	             square[i][j] = new Square(xtemp, ytemp, i, j);
	         }
	     }
	     
	     playerMap= new HashMap<>();       
 		
     	playerMap.put(Alliance.blue,new Player(Alliance.blue,square));
     	playerMap.put(Alliance.red,new Player(Alliance.red , square));
 	  
 	
    	gameOver=false;
    	gameOverLabel.setVisible(false);
    	attackInProgress=false;
    	
    	this.allPawnsVisible=allPawnsVisible;        //Initializes the variants 
    	this.attackerAdvantage=attackerAdvantage;
    	this.unknownVictor=unknownVictor;
    	this.movableBombs=movableBombs;
    	this.oneTimeBombs=oneTimeBombs;
    	this.advancedScout=advancedScout;


        xSelected=0; //To highligth a square that is selected (swap pawns or move pawns)
        ySelected=0;
        squareSelected=false;

        lastProcessed = 0; 

        playerTurn= Stratego_Utils.STARTING_PLAYER;
        
        xSelector = Alliance.xSelector(playerTurn); 
        ySelector = Alliance.ySelector(playerTurn); 

        canMove = true;
        settingUpDone=false;
        passComputer=false;

        currentPawnToPlace=new Flag(playerTurn);
        pawnIteration=0;

        leftInfo.addInfo(pawnsRemaining(Alliance.red));	
        rightInfo.addInfo(pawnsRemaining(Alliance.blue));
    }
	
	@Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        BufferedImage strategoLogo=null;
        try {
				strategoLogo= ImageIO.read(new File(Paths.get("","images","Stratego_logo_final.png").toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
        Image strategoImage= strategoLogo.getScaledInstance(350,350/4,Image.SCALE_SMOOTH);
        
        if(!gameOver)
        	g2d.drawImage(strategoImage,435,0,this);

        Rectangle bigRect = new Rectangle(Stratego_Utils.INITIAL_BOARD_X, Stratego_Utils.INITIAL_BOARD_Y,   //Paints the board
        		(Stratego_Utils.TILE_LENGTH * 10), (Stratego_Utils.TILE_LENGTH * 10)); 

        g2d.setColor(Stratego_Utils.SQUARE_COLOR);
        g2d.fill(bigRect); 

        for (int i = 0; i < 10; i++)  // Paints each cell 
        {
            for (int j = 0; j < 10; j++) {
                paintCell(i, j, g2d);
            }
        }
    }
	
    private void paintCell(int i, int j, Graphics2D g2d) {
        Rectangle tempRect = new Rectangle();
        tempRect.setBounds(square[i][j].xCoordinate, square[i][j].yCoordinate, Stratego_Utils.TILE_LENGTH, Stratego_Utils.TILE_LENGTH); 

        if ((i == 4 || i == 5) && (j == 2 || j == 3 || j == 6 || j == 7)) // if lake area fill square in blue
        {
            g2d.setColor(Stratego_Utils.LAKE_COLOR);
            g2d.fill(tempRect);
        }
        if (i == xSelector && j == ySelector && canMove==true && passComputer==false && !attackInProgress) // if square is where the selector is
        {
            if( i==xSelected && j==ySelected&& squareSelected==true) 
                g2d.setColor(Color.RED.darker());
            else
                g2d.setColor(Stratego_Utils.SQUARE_COLOR.darker());   

            g2d.fill(tempRect);
        }
        else if ((i == xSelected && j == ySelected )&& squareSelected==true && !attackInProgress)// if square is current square selected
        {
            g2d.setColor(Stratego_Utils.SELECTED_COLOR);
            g2d.fill(tempRect);
        }
        else if(canPawnMoveToSquare(i,j)==true && settingUpDone==true && squareSelected==true && !attackInProgress) //Shows available in green
        {
            g2d.setColor(Stratego_Utils.AVAILABLE_MOVES_COLOR);
            g2d.fill(tempRect);
            repaint();
        }
 
        g2d.setColor(Color.BLACK); //Draws outline of square
        g2d.draw(tempRect);

        if (square[i][j].isEmpty() == false) // If square has a pawn on it
        {
            if (isPawnVisible(i, j)) //Calls isPawnVisible to know if it can paint the pawn
            {
                int tempX = square[i][j].xCoordinate + 6; 
                int tempY = square[i][j].yCoordinate + 6;

                g2d.setColor(Color.WHITE);
                
                BufferedImage image = square[i][j].getImage(); //Draws the image corresponding to the pawn on the square
                if (image != null) {
                    g2d.drawImage(image, tempX, tempY, this);
                }
            }
            else {
                int tempX = square[i][j].xCoordinate + (Stratego_Utils.TILE_LENGTH / 6); //If the pawn is not visible then it just draws a red or blue square
                int tempY = square[i][j].yCoordinate + (Stratego_Utils.TILE_LENGTH / 6);
                tempRect.setBounds(tempX, tempY, (Stratego_Utils.TILE_LENGTH * 2 / 3), (Stratego_Utils.TILE_LENGTH * 2 / 3));

                g2d.setColor(square[i][j].getSquareColor());
                g2d.fill(tempRect);
            }
        }
    }

    private  boolean isPawnVisible(int i, int j) {
    	
        if(allPawnsVisible||gameOver)  //Only conditions in which all pawns are visible
        	return true;
        else if(attackInProgress) //If attack is in progress then both pawns involved are visible ecxept if unknownVictor is true then the winner of the attack is hidden
        {
        	if(attackResult()==1)
        	{
        		if(i==xSelected && j==ySelected && unknownVictor==false)
        			return true;
        		else if(i==xSelector && j==ySelector)
        			return true;
        		else
        			return false;
        	}
        	else if(attackResult()==-1)
        	{
        		if(i==xSelector && j==ySelector && unknownVictor==false)
        			return true;
        		else if(i==xSelected && j==ySelected)
        			return true;
        		else
        			return false;
        	}
        	else
        		if((i==xSelected && j==ySelected) ||(i==xSelector && j==ySelector))
        			return true;
        		else 
        			return false;
        }
        else if (!passComputer && (playerTurn==square[i][j].getPawn().playerAlliance) && !attackInProgress)  //Return true if the pawn and playerTurn alliance match
            return true;
        else 
        	return false;
        
    }

    private void fillSetup(Integer [][]setup) {  //setup sent depends on the key pressed by the user
		
    	removePawns(); //Clears the player's pawns before filling it
    	
    	if(playerTurn==Alliance.blue) //Checks Alliance to retreive the correct part of the 2d array
		{
			for(int i=0; i<4;i++)
			{
				for(int j=0; j<10; j++)
				{
					for(int x=0; x<40; x++)  //Goes through each of the players 40 pawns and places it if it matches with the setup
					{
						if(playerMap.get(playerTurn).pawn[x].isPlaced()==false && playerMap.get(playerTurn).pawn[x].getRank()==setup[i][j])
						{	
							playerMap.get(playerTurn).placePawn(playerMap.get(playerTurn).pawn[x], i, j);
							x=40;
						}
					}
				}
			}
			
		}
		else
		{
			for(int i=9;i>=6;i--)
			{
				for(int j=9;j>=0;j--)
				{
					for(int x=0; x<40;x++)
					{
						if(playerMap.get(playerTurn).pawn[x].isPlaced()==false && playerMap.get(playerTurn).pawn[x].getRank()==setup[i][j])
						{
							
							playerMap.get(playerTurn).placePawn(playerMap.get(playerTurn).pawn[x], i, j);
							x=40;
						}
					}
				}
			}
		}
		
	}

    private void removePawns()
    {
    	
    	playerMap.get(playerTurn).removeAllPawns(); //Calls function that deletes all of a player's pawns
    }
	
    private void selection(int key) 
    {

        if(canMove && !passComputer)  //To make a keyboard selection canMove must be true and passComputer false
        {
            if (key == Stratego_Utils.UP)
            {
                if ((xSelector > Alliance.upperBound(playerTurn, settingUpDone))) {
                    xSelector--;  // if next selection doesn't go out of bounds
                    repaint();
                }
            }
            else if (key == Stratego_Utils.DOWN)
            {
                if ((xSelector < Alliance.lowerBound(playerTurn, settingUpDone))) {
                    xSelector++;
                    repaint();
                }
            }
            else if (key == Stratego_Utils.LEFT)
            {
                if (ySelector != 0) {
                    ySelector--;
                    repaint();
                }
            }
            else if (key == Stratego_Utils.RIGHT)
            {
                if ( (ySelector != 9))
                {
                    ySelector++;
                    repaint();
                }
            }
            else if (key == Stratego_Utils.ENTER)
                enterKeyPressed();
        }
        if(key==Stratego_Utils.KEY_D)
        {
        	D_KeyPressed();
        }
        if(Stratego_Utils.SETUPMAP.containsKey(key)&&!settingUpDone&&canMove) //If player is still setting up
        {
        	pawnIteration=0;
        	fillSetup(Stratego_Utils.SETUPMAP.get(key));
        	pawnIteration=40;
        	repaint();
        }

        }

    private void pawnIteration() //Iterates through the 40 pawns in the Players pawn array for setting up
    {
        pawnIteration++;  //Goes up in the player's pawn array
        if(pawnIteration<40)
        	currentPawnToPlace=playerMap.get(playerTurn).pawn[pawnIteration];
        
    }

    private void enterKeyPressed() //Deals with event handling for the ENTER key
    {
        if(!settingUpDone) //During setupPhase
        {
            if (square[xSelector][ySelector].isEmpty()) //If square is empty
            {
                playerMap.get(playerTurn).placePawn(currentPawnToPlace, xSelector, ySelector); //player's pawn is "placed" with x y index
                repaint();
                pawnIteration(); //To move to next pawn
            }
            else if(pawnIteration==40)  //If all pawns are placed then player can select a square to be swapped or to move
            {	
                if(!squareSelected) //if no square is selected make that square the square selected
                {
                    xSelected=xSelector;
                    ySelected=ySelector;
                    squareSelected=true;
                }
                else  //swap pawns with selected square
                {
                    swapPawns();
                    squareSelected=false;
                   
                }
                repaint();
            }
        }
        else  //During battle phase
        {
            if(!squareSelected) //if no square is currently selected
            {
                if(canPawnMove()) //Checks if the pawn in the square hav at least one available move
                {
                    xSelected=xSelector; //Make current square selected
                    ySelected=ySelector;
                    squareSelected=true;
                    
                }
            }
            else //if a square is currently selected
            {
                if(canPawnMoveToSquare(xSelector, ySelector)) //if the selector is at a square where the selected pawn can move
                {
                    if(square[xSelector][ySelector].isEmpty()) //move the pawn to that square 
                    {
                        squareSelected=false;
                        movePawn();
                        changeTurn();            
                        
                    }
                    else
                    	attackInProgress=true; //else attack is in progress which means that both pawns will be visible
                }
                else
                {
                    if(xSelector==xSelected && ySelector==ySelected) //if selector and selected are the same then square is deselected
                    {
                        squareSelected=false;
                       
                    }
                    else if(square[xSelector][ySelector].getPawn().playerAlliance==playerTurn  && canPawnMove())
                    {								 //if it is a player's pawn that can move then selected square is moved to that square
                        xSelected=xSelector;
                        ySelected=ySelector;
                        
                    }
                }
            }
            repaint();

        }
    }

    private void D_KeyPressed() {  
        
    	if(attackInProgress)
    		attack();

        if(passComputer==true) //When pass computer is true, that means that game is in between turns and no pawns are visible
        {
        	passComputer=false;
            repaint();
        }
        else if(passComputer==false && settingUpDone==true)
        {
        	passComputer=false;
            repaint();
        }
        else if(playerMap.get(Alliance.blue).allPawnsPlaced()==true && playerMap.get(Alliance.red).allPawnsPlaced()==true){ 
        	settingUpDone=true;						//If both player done setting up
            xSelected=9; ySelected=9;
            leftInfo.setVisible(true);
            rightInfo.setVisible(true);
        	changeTurn();
        	repaint();
        	
        }
        else if(playerMap.get(playerTurn).allPawnsPlaced())
        {
        	changeTurn(); 
        	repaint();
        }
        
   
    
}
   
    private void swapPawns() //Swaps the position of two pawns during setting up phase
    {
        Pawn temp1= square[xSelected][ySelected].getPawn();    
        Pawn temp2= square[xSelector][ySelector].getPawn();

        playerMap.get(playerTurn).removePawn(xSelector,ySelector);
        playerMap.get(playerTurn).removePawn(xSelected,ySelected);

        playerMap.get(playerTurn).placePawn(temp1, xSelector, ySelector);
        playerMap.get(playerTurn).placePawn(temp2, xSelected, ySelected);
    }
 
    private void movePawn() //Moves a pawn to another square during battle phase
    {
        Pawn r= square[xSelected][ySelected].getPawn();

        playerMap.get(playerTurn).removePawn(xSelected,ySelected);
        playerMap.get(playerTurn).placePawn(r,xSelector,ySelector);
    }

    private boolean canPawnMove()
    {
        int tempRank=square[xSelector][ySelector].getPawn().getRank();


        if(tempRank==0) //if pawn flag then it cannot move
            return false;
        else if(tempRank==11&& movableBombs==false) //if bomb is a flag and movableBombs is false then it cannot move
        	return false;

        if(!square[xSelector][ySelector].isEmpty() && (square[xSelector][ySelector].getPawn().playerAlliance==playerTurn))
        {				//If the square selected has the current player's pawn

            if(ySelector!=0) //To avoid index going out of bounds
            {
                if(square[xSelector][ySelector - 1].isEmpty() &&( !square[xSelector][ySelector - 1].isLake()
                		||(advancedScout==true &&tempRank==2 && square[xSelector][ySelector - 3].isEmpty()))
                		|| (square[xSelector][ySelector-1].getPawn().playerAlliance==Alliance.oppositeAlliance(playerTurn) && tempRank!=11))
                    return true;  //Checks if at least one adjacent square is empty or has an enemy pawn
                				  //if advanced scouting is true, then scout can go across a lake but not on it
                			      //If it is a bomb it cannot attack, so it cannot move if it has no empty tiles to move 

            }
            if(ySelector!=9)
            {
                if(square[xSelector][ySelector + 1].isEmpty() &&( !square[xSelector][ySelector + 1].isLake()||
                		(advancedScout==true &&tempRank==2 && square[xSelector][ySelector +3].isEmpty()))
                		|| (square[xSelector][ySelector+1].getPawn().playerAlliance==Alliance.oppositeAlliance(playerTurn)&& tempRank!=11))
                    return true;
            }
            if(xSelector!=0)
            {
                if(square[xSelector - 1][ySelector].isEmpty() && (!square[xSelector - 1][ySelector].isLake()||
                		(advancedScout==true &&tempRank==2 && square[xSelector-3][ySelector].isEmpty())) 
                		|| (square[xSelector-1][ySelector].getPawn().playerAlliance==Alliance.oppositeAlliance(playerTurn)&& tempRank!=11))
                    return true;
            }
            if(xSelector!=9)
            {
                if(square[xSelector + 1][ySelector].isEmpty() &&( !square[xSelector + 1][ySelector].isLake()
                		||(advancedScout==true &&tempRank==2 && square[xSelector+3][ySelector].isEmpty()))
                		|| (square[xSelector+1][ySelector].getPawn().playerAlliance==Alliance.oppositeAlliance(playerTurn)&& tempRank!=11))
                    return true;
            }
        }

        return false;
    }

    private boolean canPawnMoveToSquare(int x, int y)

    {
        if(x==xSelected&& y==ySelected)
            return false;

        int currentPawnRank=square[xSelected][ySelected].getPawn().getRank();
        

        boolean isOpposite;
        if(square[x][y].getPawn().playerAlliance==Alliance.oppositeAlliance(playerTurn))
        	isOpposite=true;
        else
        	isOpposite=false;
        //Player can move to that square if it is one square away and is either empty or contains an enemy pawn (attack)

        if(isInBounds(x,y) && x==xSelected && (Math.abs(y-ySelected))==1 && 
        		(square[x][y].isEmpty() || isOpposite&& currentPawnRank!=11) && !square[x][y].isLake()) 
            return true;
        else if(isInBounds(x,y) && y==ySelected && (Math.abs(x-xSelected))==1 &&
        		(square[x][y].isEmpty() ||isOpposite && currentPawnRank!=11) && !square[x][y].isLake())
            return true;

        if(currentPawnRank==2) //if the pawn selected is the scout then it can move like a rook in chess
        {
            int distance; //To iterate through the squares between the square selected and the square(x,y) including the square(x,y)
            int direction;//To make sure that the iteration is in the right direction
            
            if(square[x][y].isLake()) //For advanced scouting, scounts can only pass through a lake not stop on one
            	return false;

            if(x==xSelected) //For vertical moves
            {
                if(y>ySelected)
                    direction=-1;
                else
                    direction=1;

                distance=(Math.abs(y-ySelected));
                
       

                for(int i=0; i<distance; i++)
                {
                    if(!square[x][y + (i * direction)].isEmpty() ||
                            (square[x][y + (i * direction)].isLake()&&advancedScout==false)) //if one square in between the two is not empty
                    {											 							// or a lake then that square is not available
                        return false;
                    }
                }
                return true; //if the condition is not met for all squares in between then that square is available
            }
            if(y==ySelected) //Same but for horizontal moves
            {
                if(x>xSelected)
                    direction=-1;
                else
                    direction=1;

                distance=(Math.abs(x-xSelected));
                for(int i=0; i<distance; i++)
                {
                    if(!square[x + (i * direction)][y].isEmpty() ||
                            square[x + (i * direction)][y].isLake()&&advancedScout==false) //if one square in between the two is not empty
                    {													   // or not a lake then that square is not available
                        return false;
                    }
                }
                return true;
            }


        }

        return false; //if none of the conditions above are met then pawn cannot move to that square
    }

    private boolean isInBounds(int x, int y) {
        if(x >= 0 && x < 10 && y >= 0 && y < 10) {
            return true;
        }
        else {
            return false;
        }
    }
    
    private void changeTurn() 
    {
    	playerTurn=Alliance.oppositeAlliance(playerTurn); 
    	
    	xSelector=Alliance.xSelector(playerTurn);
    	ySelector=Alliance.ySelector(playerTurn);
    	
        pawnIteration=0;
        currentPawnToPlace=new Flag(playerTurn);
        passComputer=true;
        squareSelected=false;
        
        if(settingUpDone==true)
        {
        	leftInfo.addInfo(pawnsRemaining(Alliance.red));
            rightInfo.addInfo(pawnsRemaining(Alliance.blue));
            
        }
    }

    private void attack()
    {
        
        
        Square attacker = square[xSelected][ySelected];
        Square defender = square[xSelector][ySelector];   
        
        if(attackResult()==0) //if both die
        {
          squareSelected=false;
					setBattleLog(attacker.getPawn().toString() + " attacks " + defender.getPawn().toString() + "! <br/> Battle ends in a draw.");

          //Removes both pawns from board and player

          playerMap.get(playerTurn).removePawn(xSelected,ySelected);
          playerMap.get(Alliance.oppositeAlliance(playerTurn)).removePawn(xSelector,ySelector);         
        }
        else if(attackResult()==1) //if attacker wins
        {
          if(unknownVictor== true) 
            setBattleLog("Unknown attacks " + defender.getPawn().toString() + "!<br/> Attacking side wins the battle.");
          else
            setBattleLog(attacker.getPawn().toString() + " attacks " + defender.getPawn().toString() + "!<br/> Attacking side wins the battle.");
          
          playerMap.get(Alliance.oppositeAlliance(playerTurn)).removePawn(xSelector,ySelector);
          
         
          squareSelected=false;
          movePawn();
          isGameOver();  //Checks if flag was captured           
        }
        else if(attackResult()==-1) //if defender wins
        {
          squareSelected=false;
          
		  if(unknownVictor==true) 
            setBattleLog(attacker.getPawn().toString() + " attacks unknown!<br/> Defending side wins the battle.");	
          else
            setBattleLog(attacker.getPawn().toString() + " attacks " + defender.getPawn().toString() + "!<br/> Defending side wins the battle.");	
          
          playerMap.get(playerTurn).removePawn(xSelected,ySelected);
          defender.getPawn().setWonFight(true);
        }
        
        
        changeTurn();
        repaint();        
        attackInProgress=false;
    }
    
    private int attackResult()
    {
        int currentPawnRank= square[xSelected][ySelected].getPawn().getRank();
        int oppPawnRank= square[xSelector][ySelector].getPawn().getRank();

        
        if(currentPawnRank==1) //If a spy attacks, if it attacks a marshall it wins else it loses
        {
            if(oppPawnRank==10) 
                return 1;
            else
                return -1;
        }

        if(currentPawnRank==3 && oppPawnRank==11) //If a miner attacks a bomb
        	return 1;
        
        
        if(oppPawnRank==11 && oneTimeBombs==true)
        {
        	return 0;
        }
       
        if(currentPawnRank<oppPawnRank) //If attacker loses return -1
            return -1;
        else if(currentPawnRank>oppPawnRank) //if attacker wins return 1
            return 1;
        else
        {
        	if(attackerAdvantage)
        		return 1;
        	else
        		return 0;
        		
        }
    }

	private void setBattleLog(String text) {
       gameLog.setText("<html><br/><br/><br/><b>Battle log: <b/><br/>" + text + "</html>");	// formatting for game log
    }

    private void isGameOver()
    {

        if(!playerMap.get(Alliance.oppositeAlliance(playerTurn)).pawn[0].isAlive()) //If current player's flag is not alive then game is over
        {
            canMove=false;
            gameOver=true;
            
            gameLog.setVisible(false);
            gameOverLabel.setVisible(true);

			allPawnsVisible = true;
            String winner = playerTurn == Alliance.blue ? "Blue Player" : "Red Player";	// to specify which side won in game log
            setBattleLog("Game over! <br/>" + winner + " has won the battle.");
            
            repaint();
        }
    }

    
    @SuppressWarnings("unused")
	private void setUpPawnsAutomatically()   //To automatically place pawns during setting up phase was Used for debugging
    {
        playerTurn=Alliance.blue;
        currentPawnToPlace=new Flag(playerTurn);
        
        for(int i=0; i<4;i++)
        {
            for(int j=0; j<10; j++)
            {
                playerMap.get(playerTurn).placePawn(currentPawnToPlace, i,j);
                if(pawnIteration<39)
                    pawnIteration();
            }
        }

        playerTurn=Alliance.red;
        pawnIteration=0;
        currentPawnToPlace=new Flag(playerTurn);
        for(int i=6;i<10;i++)
        {
            for(int j=0; j<10; j++)
            {
                playerMap.get(playerTurn).placePawn(currentPawnToPlace, i,j);
                if(pawnIteration<39)
                    pawnIteration();
            }
        }
        settingUpDone=true;
        playerTurn=Alliance.blue;
        leftInfo.addInfo(pawnsRemaining(Alliance.red));
        rightInfo.addInfo(pawnsRemaining(Alliance.blue));
        repaint();
    }

    
    private List<String> pawnsRemaining(Alliance alliance)
    {
        ArrayList<String> remainingPawns = new ArrayList<String>();
        StringBuilder pawnsRem= new StringBuilder("");
        Pawn tempPawn= new Dummy(Alliance.none);
        
        remainingPawns.add(" "+alliance.getString(alliance)+" Player: ");  
        
        for(int i=0; i<12;i++) //Goes through the 12 different pawn types
        {
            for(int j=0; j<40; j++)     //To retrieve a pawn with the same rank
            {
                if(playerMap.get(alliance).pawn[j].getRank()==i)
                {
                    tempPawn=playerMap.get(alliance).pawn[j]; //To be used for to get the string
                    j=40;
                }
            }

            remainingPawns.add(" "+playerMap.get(alliance).numberOf(i)+" "+tempPawn.getName()+" "); 
        }
        return remainingPawns;
    }

   
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (System.currentTimeMillis() - lastProcessed > 50) // To make sure that the pressedkey doesnt shoot 
        { 													 // events too fast
            selection(e.getKeyCode());
            lastProcessed = System.currentTimeMillis();
        }
    }

    public boolean isFocusTraversable() // So the panel can handle key events
    {
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
		}


}

import java.awt.Color;
import java.util.HashMap;

public final class Stratego_Utils {
	
	public static final int TILE_LENGTH=65;
	public static final int INITIAL_BOARD_X=285;
	public static final int INITIAL_BOARD_Y=100;
	
    public static final int KEY_0=48;  
   	public static final int KEY_1=49;
    public static final int KEY_2=50;
    public static final int KEY_3=51;
    public static final int KEY_4=52;
    public static final int KEY_5=53;
    public static final int KEY_6=54;
    public static final int KEY_7=55;
    public static final int KEY_8=56;
    public static final int KEY_9=57;

  
    public static final int UP = 38; 
    public static final int DOWN = 40;
    public static final int LEFT = 37;
    public static final int RIGHT = 39;
    public static final int ENTER = 10;
    public static final int KEY_D=68;

    public static final Color BACKGROUND_COLOR = new Color(221, 196, 142);
    public static final Color SQUARE_COLOR = new Color(160, 161, 159);
    public static final Color LAKE_COLOR = new Color(135, 206, 235);
    
    public static final Color RED_PAWN_COLOR = new Color(107,0,0);
    public static final Color BLUE_PAWN_COLOR =new Color(11,11,69);
    
    public static final Color AVAILABLE_MOVES_COLOR =new Color(144,238,144);
    public static final Color SELECTED_COLOR =new Color(223,23,56);
    
    public static final Alliance STARTING_PLAYER=Alliance.blue;

    //Default setups that are available when the players are setting up found on https://www.ultraboardgames.com/stratego/setups.php
	
	private static final Integer SETUP1[][]= {                 
	{0,11,5,3,11,4,3,6,2,5},  //Setup from Philip Atzemoglou
	{11,6,2,5,4,11,6,2,3,2},
	{7,2,8,2,6,5,2,8,2,1},
	{10,7,3,4,11,11,4,3,7,9},
	{},
	{},
	{10,7,3,4,11,11,4,3,7,9},
	{7,2,8,2,6,5,2,8,2,1},
	{11,6,2,5,4,11,6,2,3,2},
	{0,11,5,3,11,4,3,6,2,5}
	};
	
	private static final Integer SETUP2[][]= {  //Setup from Vincent Deboer#1
	{3,3,11,0,11,3,2,11,3,2},
	{4,6,5,11,5,8,7,4,11,4},
	{2,8,7,7,2,9,1,11,4,5},
	{6,2,10,3,6,2,5,2,2,6},
	{},
	{},
	{6,2,10,3,6,2,5,2,2,6},
	{2,8,7,7,2,9,1,11,4,5},
	{4,6,5,11,5,8,7,4,11,4},
	{3,3,11,0,11,3,2,11,3,2}
	};
	

	
	private static final Integer SETUP3[][]= {  //Setup from Vincent Deboer#5
	{11,0,11,3,3,4,11,3,2,4},
	{4,11,5,6,2,5,7,1,6,11},
	{8,5,7,10,5,11,7,8,2,3},
	{2,6,3,2,2,2,4,2,6,9},
	{},
	{},
	{2,6,3,2,2,2,4,2,6,9},
	{8,5,7,10,5,11,7,8,2,3},
	{4,11,5,6,2,5,7,1,6,11},
	{11,0,11,3,3,4,11,3,2,4}
	};
	
	private static final Integer SETUP4[][]= { //Setup from Anthony Bluff
	{3,5,11,4,1,2,5,3,4,2},
	{5,6,2,6,3,3,6,6,4,5},
	{7,8,3,11,7,10,11,8,9,7},
	{2,2,2,4,11,11,0,11,2,2},
	{},
	{},
	{2,2,2,4,11,11,0,11,2,2},
	{7,8,3,11,7,10,11,8,9,7},
	{5,6,2,6,3,3,6,6,4,5},
	{3,5,11,4,1,2,5,3,4,2}
	};
	
	
	private static final Integer SETUP5[][]= { //Setup from Bill East
	{4,3,2,2,2,2,2,2,11,0},
	{5,5,5,6,7,3,3,5,10,11},
	{4,7,6,6,8,6,3,1,8,9},
	{11,11,4,4,11,11,7,3,2,2},
	{},
	{},
	{11,11,4,4,11,11,7,3,2,2},
	{4,7,6,6,8,6,3,1,8,9},
	{5,5,5,6,7,3,3,5,10,11},
	{4,3,2,2,2,2,2,2,11,0}
	};
	
	
	private static final Integer SETUP6[][]= { //Setup from Mike Rowles
	{2,6,2,2,6,3,3,3,11,0},
	{2,4,7,2,7,8,3,3,7,11},
	{6,5,11,2,2,4,6,11,1,8},
	{11,5,5,4,5,4,2,10,9,11},
	{},
	{},
	{11,5,5,4,5,4,2,10,9,11},
	{6,5,11,2,2,4,6,11,1,8},
	{2,4,7,2,7,8,3,3,7,11},
	{2,6,2,2,6,3,3,3,11,0}
	};
	
	
	private static final Integer SETUP7[][]= { //B29 setup
	{5,2,3,11,6,7,3,7,11,0},
	{2,5,6,3,11,6,7,1,3,11},
	{3,2,5,2,5,11,8,2,10,8},
	{4,4,2,2,4,4,11,9,2,6},
	{},
	{},
	{4,4,2,2,4,4,11,9,2,6},
	{3,2,5,2,5,11,8,2,10,8},
	{2,5,6,3,11,6,7,1,3,11},
	{5,2,3,11,6,7,3,7,11,0}
	};
	
	
	private static final Integer SETUP8[][]= { //Setup from Johnny O'Donell
	{7,5,3,3,3,6,11,4,11,0},
	{6,2,6,4,5,2,2,11,4,11},
	{2,7,2,2,10,7,6,3,11,4},
	{5,2,8,3,5,2,8,1,9,11},
	{},
	{},
	{5,2,8,3,5,2,8,1,9,11},
	{2,7,2,2,10,7,6,3,11,4},
	{6,2,6,4,5,2,2,11,4,11},
	{7,5,3,3,3,6,11,4,11,0}
	};

	
	private static final Integer SETUP9[][]= { //Setup from Brandon Clark
	{3,3,2,5,2,2,2,6,7,0},
	{2,2,6,2,3,6,4,6,1,5},
	{11,11,9,8,11,11,8,10,11,11},
	{3,4,7,5,2,4,7,5,3,4},
	{},
	{},
	{3,4,7,5,2,4,7,5,3,4},
	{11,11,9,8,11,11,8,10,11,11},
	{2,2,6,2,3,6,4,6,1,5},
	{3,3,2,5,2,2,2,6,7,0}
	};
	
	
	private static final Integer SETUP0[][]= { //Setup from Vincent Deboer #2
	{3,11,11,0,11,4,3,3,3,7},
	{2,5,4,11,5,6,1,7,2,7},
	{5,11,11,4,2,9,8,8,2,4},
	{6,2,2,2,6,2,3,5,6,10},
	{},
	{},
	{6,2,2,2,6,2,3,5,6,10},
	{5,11,11,4,2,9,8,8,2,4},
	{2,5,4,11,5,6,1,7,2,7},
	{3,11,11,0,11,4,3,3,3,7}
	};
	
	public static final HashMap<Integer,Integer[][]> SETUPMAP= new HashMap<>() {{  //A hashmap that combines keys and their coressponding setups
		put(KEY_0 , SETUP0);
		put(KEY_1 , SETUP1);
		put(KEY_2 , SETUP2);
		put(KEY_3 , SETUP3);
		put(KEY_4 , SETUP4);
		put(KEY_5 , SETUP5);
		put(KEY_6 , SETUP6);
		put(KEY_7 , SETUP7);
		put(KEY_8 , SETUP8);
		put(KEY_9 , SETUP9);
	}};
	
	public static final String WAITING_ATTACK = "<html><br/><br/><br/><b>Battle log:</b> <br/>Waiting for an attack...</html>";
	public static final String[] SETTINGS= {"All Pawns Visible", "Attacker Advantage", "Unknown Victor", "Movable Bombs","One-time Bombs","Advanced Scouting"};
	
    public static final String userGuideString= new String //User Guide string
    		("User Guide\n" +
            "\n" +
            "Here are some instructions and tips on how to play Stratego\n" +
            "\n" +
            "Stratego is a game of strategy where you cannot see your opponent’s pawns. For the best game experience both players should sit face to face and pass the \ncomputer back and forth as they go about the game. A buffer will occur in between each turn in which all pawns will be covered to ensure the integrity of the game.\n" +
            "\n" +
            "Setup Phase:\n" +
            "     • You can place your pawns one by one as you like in this order:\n" +
            "          • Flag, 6 bombs, 6 scouts, spy, marshall, general, ….\n" +
            "          • Press enter to drop your pawn\n" +
            "     • Or you can choose from 10 default setups that were carefully crafted for a strategic approach. You can access a default setup by pressing (0-9)\n" +
            "     • You can also swap the position of two pawns by selecting both of them.\n" +
            "     • Once you are done press D and pass the computer to your opponent. \n" +
            "\n" +
            "Battle Phase:\n" +
            "     • During battle you will be allowed one move per turn and you will need to pass the computer after making it.\n" +
            "     • The player that receives the computer will need to press D to start his turn as all pawns will be covered in between each turn\n" +
            "     • Because Stratego is also a game of memorization, make use of the battle log to your advantage. The battle log will display the outcome of each attack with \n     the pawns involved, and will only remain there for one turn.\n" +
            "\n" +
            "Variants:\n" +
            "Some fun variants can be added and mixed to make the game more interesting\n" +
            "     • All Pawns Visible: all pawns will be visible during the whole game except during the setup phase.\n" +
            "     • Attacker Advantage: the attacker wins when an attack results in a tie\n" +
            "     • Unknown Victor: the victor of an attack will remain unknown (not displayed on the game log).\n" +
            "     • Movable Bombs: bombs can move, but can’t strike.\n" +
            "     • One Time Bombs: bombs explode after being hit\n" +
            "     • Advanced Scouting: scouts can go through lake tiles, but cannot stop on one. \n" +
            "You can decide to add as little or as many variants as you want.\n" +
            "Click on restart to confirm your variant selections.\n");
}


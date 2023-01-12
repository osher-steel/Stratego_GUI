import java.awt.Color;

//Alliance enum to handle differentiate the two players and their pawns 
//Inspired by the video series on chess programming by @amir650 on Youtube

public enum Alliance {   
	blue,red,none;
	
	public static Alliance oppositeAlliance(Alliance alliance)  
	{
		if(alliance==red)
			return blue;
		else
			return red;
	}
	
	public static int upperBound(Alliance alliance, boolean settingUpDone)  
	{
		if (settingUpDone)
			return 0;
		else if(alliance==red)
           return 6;
		else
			return 0;
	}
	
	public static int lowerBound(Alliance alliance, boolean settingUpDone)
	{
		if(settingUpDone)
			return 9;
		else if(alliance==red)
			return 9;
		else
			return 3;
	}
	
	public static int bottomRange(Alliance alliance)  
	{
		if(alliance==red)
           return 6;
		else
			return 0;
	}
	
	public static int upperRange(Alliance alliance)
	{

		if(alliance==red)
			return 10;
		else
			return 4;
	}
	
	public static Color getColor(Alliance alliance)
	{
		if(alliance==red)
			return Stratego_Utils.RED_PAWN_COLOR;
		else
			return Stratego_Utils.BLUE_PAWN_COLOR;
	}
	
	public static int xSelector(Alliance alliance)
	{
		if(alliance==red)
			return 6;
		else
			return 3;
	}
	
	public static int ySelector(Alliance alliance)
	{
		return 4;
	}
	
	public String getString(Alliance alliance)
	{
		if(alliance==red)
			return "Red";
		else
			return "Blue";
	}
}

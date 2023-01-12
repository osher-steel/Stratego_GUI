import java.nio.file.Path;
import java.nio.file.Paths;

abstract class Pawn {
    protected boolean alive;
    protected boolean placed;
    protected int rank;
    protected boolean wonFight;
    protected int player;
    public Alliance playerAlliance;
    protected int x;
    protected int y;

    Pawn(int r, Alliance  playerAlliance)
    {
        x=0;
        y=0;
        alive=true;
        placed=false;
        rank=r;
        wonFight = false;
        this.playerAlliance=playerAlliance;
    }

	public String toString() {
        if (player == 0) {
            return "Red " + getName();  
        }
        else {
            return "Blue " + getName();
        }
    }
	
    public String getFilename() {  //Used to retrieve correct pawn image from images folder
        String filepath = null;  				
        if(playerAlliance==Alliance.red) {
            filepath = Paths.get("","images", "red" + this.getName() + ".gif").toString();
        }
        else {
            filepath = Paths.get("","images", "blue" + this.getName() + ".gif").toString();
        }
        //System.out.println(filepath);
        return filepath.toLowerCase();
    }

    public void setWonFight(boolean won) {
        wonFight = won;
    }

    public boolean getWonFight() {
        return wonFight;
    }

    public boolean isPlaced()
    {
        return placed;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
    
    public int getRank()
    {
        return rank;
    }

    public void placePawn(int x, int y )
    {
        this.x=x;
        this.y=y;
        placed=true;
        alive=true;

    }

    public void removePawn()
    {
        placed=false;
        alive=false;
    }

    public abstract char toChar();

    public abstract String getName();
}

class Dummy extends Pawn    
{
    Dummy(Alliance alliance)
    {
        super(-1, alliance);
    }
    public String getName()
    {
        return "Dummy";
    }

    public char toChar()
    {
        return '0';
    }
}

class Flag extends Pawn
{

    Flag(Alliance alliance)
    {
        super(0,alliance);

    }
    public String getName()
    {
        return "Flag";
    }

    public char toChar()
    {
        return 'F';
    }
}

class Bomb extends Pawn
{

    Bomb(Alliance alliance)
    {
        super(11,alliance);

    }
    public String getName()
    {
        return "Bomb";
    }

    public char toChar()
    {
        return 'B';
    }
}

class Marshall extends Pawn
{

    Marshall(Alliance alliance)
    {
        super(10,alliance);

    }
    public String getName()
    {
        return "Marshall";
    }

    public char toChar()
    {
        return 'M';
    }
}

class General extends Pawn
{

    General(Alliance alliance)
    {
        super(9,alliance);

    }
    public String getName()
    {
        return "General";
    }

    public char toChar()
    {
        return '9';
    }
}

class Colonel extends Pawn
{

    Colonel(Alliance alliance)
    {
        super(8,alliance);

    }
    public String getName()
    {
        return "Colonel";
    }

    public char toChar()
    {
        return '8';
    }
}

class Major extends Pawn
{

    Major(Alliance alliance)
    {
        super(7,alliance);

    }
    public String getName()
    {
        return "Major";
    }

    public char toChar()
    {
        return '7';
    }
}

class Captain extends Pawn
{

    Captain(Alliance alliance)
    {
        super(6,alliance);

    }
    public String getName()
    {
        return "Captain";
    }

    public char toChar()
    {
        return '6';
    }
}

class Lieutenant extends Pawn
{

    Lieutenant(Alliance alliance)
    {
        super(5,alliance);

    }
    public String getName()
    {
        return "Lieutenant";
    }

    public char toChar()
    {
        return '5';
    }
}

class Sergeant extends Pawn
{

    Sergeant(Alliance alliance)
    {
        super(4,alliance);

    }

    public String getName()
    {
        return "Sergeant";
    }

    public char toChar()
    {
        return '4';
    }
}

class Miner extends Pawn
{

    Miner(Alliance alliance)
    {
        super(3,alliance);

    }
    public String getName()
    {
        return "Miner";
    }

    public char toChar()
    {
        return '3';
    }
}

class Scout extends Pawn
{

    Scout(Alliance alliance)
    {
        super(2,alliance);

    }

    public String getName()
    {
        return "Scout";
    }

    public char toChar()
    {
        return '2';
    }
}

class Spy extends Pawn
{
    Spy(Alliance alliance)
    {
        super(1,alliance);

    }

    public String getName()
    {
        return "Spy";
    }

    public char toChar()
    {
        return '1';
    }
}

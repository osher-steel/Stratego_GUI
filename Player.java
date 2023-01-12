import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

public class Player {
	    public Pawn pawn[];  //Contains an array of 40 pawns
	    public HashMap<Pawn[],Integer> pawnMap;
	    public Square square[][];
	    public Alliance alliance;
	    

	    public Player(Alliance alliance, Square square[][]) throws IOException
	    {
	    	this.square=square;
	    	this.alliance=alliance;
	    	
	    	this.square=square;
	    	pawnMap= new HashMap<>();
	    	
	    	
	    	Flag flag[]= new Flag[1];
	    	flag[0]= new Flag(alliance);
	    	
	    	Spy spy[]= new Spy[1];
	    	spy[0]= new Spy(alliance);
	    	
	    	Marshall marshall[]= new Marshall[1];
	    	marshall[0]= new Marshall(alliance);
	    	
	    	General general[]= new General[1];
	    	general[0]= new General(alliance);
	    	
	    	Colonel colonel[]= new Colonel[2];
	    	for(int i=0; i<colonel.length;i++)
	    		colonel[i]= new Colonel(alliance);
	    	
	    	Major major[]= new Major[3];
	    	for(int i=0; i<major.length;i++)
	    		major[i]= new Major(alliance);

	    	Captain captain[]= new Captain[4];
	    	for(int i=0; i<captain.length;i++)
	    		captain[i]= new Captain(alliance);
	    	
	    	Lieutenant lieutenant []= new Lieutenant[4];
	    	for(int i=0; i<lieutenant.length;i++)
	    		lieutenant[i]= new Lieutenant(alliance);
	    	
	    	Sergeant sergeant[]= new Sergeant[4];
	    	for(int i=0; i<sergeant.length;i++)
	    		sergeant[i]= new Sergeant(alliance);
	    	
	    	Scout scout[]= new Scout[8];
	    	for(int i=0; i<scout.length;i++)
	    		scout[i]= new Scout(alliance);
	    	
	    	Miner miner[]= new Miner[5];
	    	for(int i=0; i<miner.length;i++)
	    		miner[i]= new Miner(alliance);
	    	
	    	Bomb bomb[]= new Bomb[6];
	    	for(int i=0; i<bomb.length;i++)
	    		bomb[i]= new Bomb(alliance);
	    	
	    	
	    	pawnMap.put(flag,1);
	    	pawnMap.put(marshall,1);
	    	pawnMap.put(bomb,1);
	    	pawnMap.put(spy,1);
	    	pawnMap.put(general,1);
	    	pawnMap.put(colonel,1);
	    	pawnMap.put(miner,1);
	    	pawnMap.put(captain,1);
	    	pawnMap.put(lieutenant,1);
	    	pawnMap.put(sergeant,1);
	    	pawnMap.put(major,1);
	    	
	    	
	        pawn= new Pawn[40];
	        for(int i=0; i<40; i++)
	        {
	            if(i==0)
	            {
	                pawn[i]= new Flag(alliance);
	            }
	            if(i>=1 && i<=6)
	            {
	                pawn[i]= new Bomb(alliance);
	            }
	            if(i==7)
	            {
	                pawn[i]= new Marshall(alliance);
	            }
	            if(i==8)
	            {
	                pawn[i]= new Spy(alliance);
	            }
	            if(i==9)
	            {
	                pawn[i]= new General(alliance);
	            }
	            if(i==10 || i==11)
	            {
	                pawn[i]= new Colonel(alliance);
	            }
	            if(i>=12 && i<=14)
	            {
	                pawn[i]= new Major(alliance);
	            }
	            if(i>=15 && i<=18)
	            {
	                pawn[i]= new Captain(alliance);
	            }
	            if(i>=19 && i<=22)
	            {
	                pawn[i]= new Lieutenant(alliance);
	            }
	            if(i>=23 && i<=26)
	            {
	                pawn[i]= new Sergeant(alliance);
	            }
	            if(i>=27 && i<=31)
	            {
	                pawn[i]= new Miner(alliance);
	            }
	            if(i>=32&& i<=39)
	            {
	                pawn[i]= new Scout(alliance);
	            }
	        }
	    }

	    
	    public void placePawn(Pawn p, int x, int y)
	    {
	    	 square[x][y].addPawn(p, alliance);
	    	 
	        for(int i=0; i<40; i++)
	        {
	            if(pawn[i].getRank()==p.getRank() && pawn[i].isPlaced()==false)
	            {
	                pawn[i].placePawn(x, y);
	                i=40;
	            }
	        }
	      
	    }

	    public int nextPawnToPlace()
	    {
	        for(int i=0; i<40; i++)
	        {
	            if(pawn[i].isPlaced()==false)
	                return pawn[i].getRank();
	        }

	        return -1;

	    }

	    public boolean allPawnsPlaced()
	    {
	        for(int i=0; i<40; i++)
	        {
	            if(pawn[i].isPlaced()==false)
	                return false;
	        }

	        return true;
	    }

	    public void removePawn(int x, int y) {
	        for(int i=0; i<40; i++)
	        {
	            if(pawn[i].getX()==x && pawn[i].getY()==y)
	            {
	                pawn[i].removePawn();
	                i=40;
	            }

	        }
	        square[x][y].removePawn();

	    }

	    public void removeAllPawns()
	    {
	    	for(int i=0; i<40; i++)
	    	{
	    		if(square[pawn[i].getX()][pawn[i].getY()].getPawn().playerAlliance==alliance)
	    			square[pawn[i].getX()][pawn[i].getY()].removePawn();
	    		pawn[i].removePawn();
	    	}
	    }
	   
	    public int numberOf(int r)
	    {
	        int num=0;

	        for(int i=0; i<40; i++)  //Checks how many of pawns of a certain rank are alive
	        {
	            if(pawn[i].getRank()==r && pawn[i].isAlive()==true)
	            {
	                num++;
	            }
	        }
	        return num;
	    }

	}

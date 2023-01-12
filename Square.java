import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Square extends JPanel {
    private JLabel imgLabel;
    private ImageIcon image;
    private Pawn pawn;
    private boolean empty;
    boolean lake;

    public int xCoordinate;
    public int yCoordinate;

    public int x;
    public int y;

    Color squareColor;

    Square(int xCoor, int yCoor,int x, int y)
    {
        this.xCoordinate=xCoor;
        this.yCoordinate=yCoor;

        this.x=x;
        this.y=y;
        empty=true;

        if((x==4||x==5)&&(y==2||y==3||y==6||y==7))
            lake=true;
        else
            lake=false;
        
        pawn= new Dummy(Alliance.none);

        this.setLayout(new BorderLayout(1, 1));
        image = new ImageIcon();
        imgLabel = new JLabel("text");
        add(imgLabel, BorderLayout.CENTER);
        imgLabel.setVisible(true);
    }

    public void changeCoordinates(int x,int y)
    {
    	xCoordinate=x;
    	yCoordinate=y;
    }
   
    public BufferedImage getImage() {
        try {
            BufferedImage myImage = ImageIO.read(new File("" + this.pawn.getFilename()));
            return myImage;
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
        return null;
    }

    public void addPawn(Pawn p, Alliance playerTurn)
    {
    	squareColor= Alliance.getColor(playerTurn);
        pawn= p;
        empty=false;
    }

    public void removePawn()
    {
        empty=true;
        pawn=new Dummy(Alliance.none);
    }

    public Color getSquareColor()
    {
        return squareColor;
    }

    boolean isEmpty()
    {
    	return empty;
    }

    boolean isLake()
    {
        return lake;
    }

    public Pawn getPawn()
    {
        return pawn;
    }
}
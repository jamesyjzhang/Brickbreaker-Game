import java.awt.Color;
import java.awt.Graphics;

public class Block extends GameObj {
    private final int blue = 1;
    private final int red = 2;
    private final int green = 3;
    private final int yellow = 4;
    private final int purple = 5;
    private final int orange = 6;
    
    public int size ;
    public int init_x;
    public int init_y;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
    public boolean hit;
    
    public Block(int courtWidth, int courtHeight, int init_x, int init_y, int width, int height) {
        super(INIT_VEL_X, INIT_VEL_Y, init_x, init_y, width, height, courtWidth,
                courtHeight);
        hit = false;
    }
    
    public void contact () {
    	hit = true;
    }
    
    public boolean removed () {
        return hit;
    }
    
    public int getX () {
    	return pos_x;
    }
    
    public int getY () {
    	return pos_y;
    }
    
    @Override
    public void draw(Graphics g, int color, int pos_x, int pos_y, int width, int height) {
        if (removed() == false) {
            switch (color) 
            {
            case blue: 
                g.setColor(Color.BLUE);
                g.fillRect(pos_x, pos_y, width, height);
                break;
            case red:
                g.setColor(Color.RED);
                g.fillRect(pos_x, pos_y, width, height);
                break;
            case green:
                g.setColor(Color.GREEN);
                g.fillRect(pos_x, pos_y, width, height);
                break;
            case yellow:
                g.setColor(Color.YELLOW);
                g.fillRect(pos_x, pos_y, width, height);
                break;
            case purple:
                g.setColor(Color.MAGENTA);
                g.fillRect(pos_x, pos_y, width, height);
                break;
            case orange:
                g.setColor(Color.ORANGE);
                g.fillRect(pos_x, pos_y, width, height);
                break;
            }
        }
    }
}
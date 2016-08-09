/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;


/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Square square; 
    private Circle ball; 
    private Block block;
    private HighScore highscore;
    private static ArrayList<Block> blocks = new ArrayList<Block>();
    public boolean playing = false; // whether the game is running
    private JLabel status; // Current status text (i.e. Running...)
    private JLabel statuslife;
    private JLabel statusscore;

    // Game constants
    public static final int COURT_WIDTH = 1000;
    public static final int COURT_HEIGHT = 1000;
    public static final int SQUARE_VELOCITY = 10;
    public static final int BLOCK_WIDTH = 30;
    public static final int BLOCK_HEIGHT = 25;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    
    // Mutable values as game progresses.
    public int lives = 3;
    public int score = 0;
    
    public GameCourt(JLabel status, JLabel statuslife, JLabel statusscore) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    tick();
                    
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key
        // events will be handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long
        // as an arrow key is pressed, by changing the square's
        // velocity accordingly. (The tick method below actually
        // moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    square.v_x = -SQUARE_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    square.v_x = SQUARE_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    square.v_y = SQUARE_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                    square.v_y = -SQUARE_VELOCITY;
            }

            public void keyReleased(KeyEvent e) {
                square.v_x = 0;
                square.v_y = 0;
            }
        });

        this.status = status;
        this.statuslife = statuslife;
        this.statusscore = statusscore;
        
    }
    
    public int losslife() {
        return lives;
    }
    
    public int scoreCheck() {
        return score;
    }

    public static int blocksLeft(ArrayList<Block> blocks) {
        return blocks.size();
    }
    
    public static boolean levelWin() {
        return (blocks.size() == 0);
    }
    
    
    /**
     * (Re-)set the game to its initial state.
     */
    public void newBlocks() {
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 3; j++) {
                block = new Block(COURT_WIDTH, COURT_HEIGHT, 25 + (50 * i), 30 + (30 * j), 
                        40, 25);
                blocks.add(block);
            }
        }
    }
    
    public void reset() {
        square = new Square(COURT_WIDTH, COURT_HEIGHT);
        ball = new Circle(COURT_WIDTH, COURT_HEIGHT);
        highscore = new HighScore();

        /*Set up the game*/
        playing = true;
        status.setText("Running...");
        statuslife.setText("| Number of lives: " + losslife());
        statusscore.setText("| Score: " + scoreCheck());
        highscore.readHighScores();

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     * @throws IOException 
     */
    

    void tick() throws IOException {
        if (playing) {
            // advance the square and ball in their
            // current direction.
            square.squaremove();
            ball.move();

            // make the ball bounce off walls...
            ball.bounce(ball.hitWall());
            ball.bounce(ball.hitObj(square));
            
            
            Iterator<Block> blawk = blocks.iterator();
            
            while (blawk.hasNext()) {
                Block name = blawk.next();
                
                if (ball.intersects(name)) {
                    name.contact();
                    blawk.remove();
                    ball.bounce(ball.hitObj(name));
                    score += 1;
                    
                    statusscore.setText("Score: " + score);
                }
            }

            // check for the game end conditions
            if (ball.pos_y == ball.max_y) {
                if (lives > 0) {
                    lives -= 1;
                    statuslife.setText("Number of lives: " + lives);
                    reset();
                }
                else {
                    playing = false;
                    status.setText("Game over!");
                    highscore.addHighScore(score);
                    highscore.writeHighScore();
                }
            } 
            if (levelWin()) {
                playing = false;
                status.setText("Good job I'm so proud of you");
                highscore.addHighScore(score);
                highscore.writeHighScore();
            }
            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Block names : blocks) {
            if (!names.hit) {
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(7);
                names.draw(g, randomInt, names.getX(), names.getY(), 40, 25);
            }
        }
        square.draw(g);
        ball.draw(g);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}

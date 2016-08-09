/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {

    private static final String HSFile = "highscores.txt";
    private Reader r;
    
    public void run() {
        // NOTE : recall that the 'final' keyword notes inmutability
        // even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Jim's CIS 120 Project: Brick Breaker");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);
        
        final JLabel statuslife = new JLabel("Number of lives");
        status_panel.add(statuslife);
        
        final JLabel statusscore = new JLabel("Score");
        status_panel.add(statusscore);
        // Main playing area
        final GameCourt court = new GameCourt(status, statuslife, statusscore);
        frame.add(court, BorderLayout.CENTER);
        
        final JPanel info = new JPanel();
        frame.add(info, BorderLayout.WEST);
        
        
        final JLabel instructions = new JLabel("Brickbreaker: A game to hit all the bricks.");
        final JLabel instructions2 = new JLabel ("Use left and right keys to move.");
        info.add(instructions);
        info.add(instructions2);
        
        final JPanel HSList = new JPanel();
        frame.add(HSList, BorderLayout.EAST);

        final JLabel title = new JLabel("High Scores:");
        HSList.add(title);
        
        @SuppressWarnings("unused")
		final HighScore highscore = new HighScore ();
        final JTextArea scores = new JTextArea();
        try {
            r = new FileReader(HSFile);
        } catch (FileNotFoundException e1) {
            System.err.println("Can't find the file :(");
            e1.printStackTrace();
        }
        try {
            scores.read(r, HSFile);
            HSList.add(scores);
        } catch (IOException e1) {
            System.err.println("Can't find the file :(");
            e1.printStackTrace();
        }
        
        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset
        // button, we define it as an anonymous inner class that is
        // an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed,
        // actionPerformed() will be called.
        final JButton reset = new JButton("New Game");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
                court.lives = 3;
                court.score = 0;
            }
        });
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.newBlocks();
        
        court.reset();
        court.lives = 3;
        court.score = 0;
    }

    /*
     * Main method run to start and run the game Initializes the GUI elements
     * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
     * this in the final submission of your game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}

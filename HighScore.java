import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.*;

@SuppressWarnings("serial")
public class HighScore extends JFrame {
    private static final String HSFile = "highscores.txt";
    private Map<Integer, String> scoremap = new TreeMap<Integer, String>(Collections.reverseOrder());
    BufferedReader br;
    private BufferedWriter writer;
    private String line;
    
    public HighScore() {
        File scores = new File(HSFile);
        if (!scores.exists()) {
            try {
                scores.createNewFile();
            } 
            catch (IOException e) {
                System.out.println("Thrown from the writing new file.");
            }
        }
        else {
            try {
                br = new BufferedReader(new FileReader(HSFile));
            } 
            catch (FileNotFoundException e) {
                System.out.println("Issue with Buffered Reader");
            }
        }
    }
    
    public void readHighScores () {
        try {
            while (br.ready()) {
                line = br.readLine();
                int index1 = line.indexOf("=");
                int index2 = line.indexOf("<");
                int index3 = line.indexOf(">");
                int playerscore  = Integer.valueOf(line.substring(0, index1));
                String playername = line.substring(index2 + 1, index3);
                scoremap.put(playerscore, playername);
            }
            br.close();
        } catch (NumberFormatException | IOException e) {
            System.out.println("This is from readHighScores");
        }
    }
    
    public void addHighScore (int score) {
    String name = "";
    name = JOptionPane.showInputDialog("Thank you for playing!  "
        + "Enter your name: ");
    scoremap.put(score, name);
    }
    
    public void writeHighScore () throws IOException {
        
        writer = new BufferedWriter(new FileWriter(HSFile));
        Set<Integer> value = scoremap.keySet();
        try {
            for (int sco : value) {
                writer.write(sco + "= score | Name: <" + scoremap.get(sco) + ">");
                writer.newLine();
                
            }
        }
        catch (Exception e) {
            
        }
        writer.close();
    }
}
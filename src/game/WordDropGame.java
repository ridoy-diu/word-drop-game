package game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.random;


public class WordDropGame extends JPanel {

    JButton playButton, pauseButton, playAgainButton;
    Timer timer;
    ArrayList<String> words;
    ArrayList<Integer> wordX, wordY;
    Random random;
    String currentWord = "";
    int score = 0;
    boolean gameOver = false;

    String[] wordBank = {"java", "loop", "class", "method", "object", "paint"};
    int timerDelay = 100;


    
    WordDropGame() {

        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(800, 600));

        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        playAgainButton = new JButton("Play Again");

        add(playButton);
        add(pauseButton);
        add(playAgainButton);

        playButton.setFocusable(false);
        pauseButton.setFocusable(false);
        playAgainButton.setFocusable(false);
    }
}

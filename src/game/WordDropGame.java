package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class WordDropGame extends JPanel implements ActionListener, KeyListener {

    JButton playButton, pauseButton, playAgainButton;
    Timer timer;
    ArrayList<String> words;
    ArrayList<Integer> wordX, wordY;
    Random random;
    String currentWord = "";
    int score = 0;
    boolean gameOver = false;
    String[] wordBank = { "java", "loop", "class", "method", "object", "paint" };
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

    void startGame() {

    }

    void pauseGame() {

    }

    void resetGame() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

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

    @SuppressWarnings("unused")
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

        words = new ArrayList<>();
        wordX = new ArrayList<>();
        wordY = new ArrayList<>();
        random = new Random();
        timer = new Timer(timerDelay, this);
        playAgainButton.setVisible(true);
        pauseButton.setEnabled(false);

        addKeyListener(this);
        playButton.addActionListener(e -> startGame());
        pauseButton.addActionListener(e -> pauseGame());
        playAgainButton.addActionListener(e -> resetGame());

    }

    void startGame() {
        timer.start();
        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
        gameOver = false;
    }

    void pauseGame() {
        timer.stop();
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
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

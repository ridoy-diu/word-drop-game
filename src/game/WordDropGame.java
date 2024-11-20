package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class WordDropGame extends JPanel implements ActionListener, KeyListener {

    JButton playButton, pauseButton, playAgainButton;
    Timer timer;
    ArrayList<String> words;
    ArrayList<Integer> wordX, wordY;
    Random random;
    String currentWord = "";
    int score = 0;
    boolean gameOver = true;
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Verdana", Font.BOLD, 40));
            g.drawString("Game Over", getWidth() / 2 - 120, getHeight() / 2 - 60);
            g.setFont(new Font("Verdana", Font.BOLD, 30));
            g.drawString("Score: " + score, getWidth() / 2 - 80, getHeight() / 2);
            playAgainButton.setVisible(true);
            return;
        }

        g.setFont(new Font("Verdana", Font.BOLD, 22));
        g.setColor(Color.YELLOW);
        g.drawString("Score: " + score, 20, 30);
        g.setColor(Color.GREEN);
        g.drawString("Typed Word: " + currentWord, 20, getHeight() - 30);

       
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

package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class WordDropGame extends JPanel implements ActionListener, KeyListener {

    JButton playButton, pauseButton, playAgainButton;
    Timer timer, wordTimer;
    ArrayList<String> words;
    ArrayList<Integer> wordX, wordY;
    Random random;
    String currentWord = "";
    int score = 0;
    int level = 1;
    int lives = 3;
    int timerDelay = 50;
    int scoreThreshold = 100;
    boolean gameOver = true;
    String[] wordBank = { "java", "loop", "class", "method", "object", "paint" };
    int wordAddInterval = 1500;

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

        wordTimer = new Timer(wordAddInterval, e -> addNewWord());


    }

    void addNewWord() { 
        String newWord = wordBank[random.nextInt(wordBank.length)];
        words.add(newWord); 
        wordX.add(random.nextInt(getWidth() - 100)); 
        wordY.add(0);
    }

    void startGame() {
        timer.start();
        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
        gameOver = false;
        repaint();
    }

    void pauseGame() {
        timer.stop();
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }

    void resetGame() {
        gameOver = false;
        score = 0;
        currentWord = "";
        timerDelay = 100;
        timer.setDelay(timerDelay);
        playButton.setEnabled(true);
        playAgainButton.setVisible(false);
        pauseButton.setEnabled(false);
        repaint();
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

    public static void main(String[] args) { 
        JFrame frame = new JFrame("Word Drop Game"); 
        WordDropGame game = new WordDropGame(); 
 
        frame.add(game); 
        frame.pack(); 
        frame.setResizable(false); 
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setVisible(true); 
    }  
    
}

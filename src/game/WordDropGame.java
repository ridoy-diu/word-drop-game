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
    boolean gameOver = false;
    String[] wordBank = {"cat", "dog", "fish", "bird", "tree", "book", "star", "moon", "sun", "car", "house", "ball", "toy", "cake", "hat", "shoe", "desk", "lamp", "door", "key", "apple", "orange", "grape", "pencil", "paper", "table", "chair", "water", "juice", "milk", "bread", "music", "dance", "happy", "sadness", "friend", "family", "garden", "forest", "mountain", "ocean", "chocolate", "universe", "elephant", "giraffe", "kangaroo", "dolphin", "tiger", "lion", "zebra", "pasta", "cereal", "computer", "science","history","geography","language","art","algorithm","variable","function","inheritance","polymorphism","exception","synchronization","interface","encapsulation","mathematics","philosophy","conundrum","paradigm","quintessential","ephemeral","hypothesis","theory","experiment","analysis","data","research","conclusion","evidence","variable","control","phenomenon","observation","interpretation","community","environment","government","economy","technology","innovation","education","culture","society","tradition"};
    int wordAddInterval = 1500;

    @SuppressWarnings("unused")
    WordDropGame() {
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.BLACK);

        words = new ArrayList<>();
        wordX = new ArrayList<>();
        wordY = new ArrayList<>();
        random = new Random();
        timer = new Timer(timerDelay, this);

        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        playAgainButton = new JButton("Play Again");

        playButton.addActionListener(e -> startGame());
        pauseButton.addActionListener(e -> pauseGame());
        playAgainButton.addActionListener(e -> resetGame());

        add(playButton);
        add(pauseButton);
        add(playAgainButton);

        playButton.setFocusable(false);
        pauseButton.setFocusable(false);
        playAgainButton.setFocusable(false);

        
        playAgainButton.setVisible(false);
        pauseButton.setEnabled(false);

        setPreferredSize(new Dimension(800, 600));
        
        wordTimer = new Timer(wordAddInterval, e -> addNewWord());
    }

    void startGame() {
        timer.start();
        wordTimer.start();
        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
        gameOver = false;
        repaint();
    }

    void pauseGame() {
        timer.stop();
        wordTimer.stop();
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }

    void resetGame() {
        gameOver = false;
        score = 0;
        level = 1;
        lives = 3;
        words.clear();
        wordX.clear();
        wordY.clear();
        currentWord = "";

        timerDelay = 50;
        timer.setDelay(timerDelay);

        playButton.setEnabled(true);
        playAgainButton.setVisible(false);
        pauseButton.setEnabled(false);

        repaint();
        timer.start();
        wordTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (gameOver) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.RED);
            g.setFont(new Font("Verdana", Font.BOLD, 60));
            String gameOverText = "Game Over!";
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int xPos = (getWidth() - metrics.stringWidth(gameOverText)) / 2;
            g.drawString(gameOverText, xPos, getHeight() / 2 - 60);

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Verdana", Font.BOLD, 40));
            String scoreText = "Score: " + score;
            metrics = g.getFontMetrics(g.getFont());
            xPos = (getWidth() - metrics.stringWidth(scoreText)) / 2;
            g.drawString(scoreText, xPos, getHeight() / 2 + 20);

            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            String tryAgainText = "Try Again!";
            metrics = g.getFontMetrics(g.getFont());
            xPos = (getWidth() - metrics.stringWidth(tryAgainText)) / 2;
            g.drawString(tryAgainText, xPos, getHeight() / 2 + 80);

            playButton.setVisible(false);
            pauseButton.setVisible(false);
            playAgainButton.setVisible(true);
            return;
        }

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Lives: " + lives, 20, 30);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        for (int i = 0; i < words.size(); i++) {
            g.drawString(words.get(i), wordX.get(i), wordY.get(i));
        }

        g.setColor(Color.GREEN);
        g.drawString("Typed Word: " + currentWord, 20, getHeight() - 40);

        g.setColor(Color.YELLOW);
        String levelText = String.format("Level: %d | Score: %d ", level, score); 
        FontMetrics metricsLevel = g.getFontMetrics(g.getFont());
        int levelXPos = getWidth() - metricsLevel.stringWidth(levelText) - 20;
        g.drawString(levelText, levelXPos, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver)
            return;

        for (int i = 0; i < wordY.size(); i++) {
            int fallSpeed = 7;
            wordY.set(i, wordY.get(i) + fallSpeed);

            if (wordY.get(i) > getHeight()) {
                words.remove(i);
                wordX.remove(i);
                wordY.remove(i);
                lives--;

                if (lives <= 0) {
                    gameOver = true;
                    timer.stop();
                    wordTimer.stop();
                }
            }
        }

        checkLevelUp();
        repaint();
    }

    void checkLevelUp() {
        if (score >= level * scoreThreshold) {
            level++;
            timerDelay = Math.max(30, timerDelay - 10);
            timer.setDelay(timerDelay);
            System.out.println("Level Up! Current Level: " + level + " | New Timer Delay: " + timerDelay);
        }
    }

    void addNewWord() {
        String newWord = wordBank[random.nextInt(wordBank.length)];
        words.add(newWord);
        wordX.add(random.nextInt(getWidth() - 100));
        wordY.add(0);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            return;

        char keyChar = e.getKeyChar();

        if (Character.isLetter(keyChar)) {
            currentWord += keyChar;
        }

        for (int i = 0; i < words.size(); i++) {
            if (currentWord.equals(words.get(i))) {
                words.remove(i);
                wordX.remove(i);
                wordY.remove(i);
                score += 10;
                currentWord = "";
                break;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && currentWord.length() > 0) {
            currentWord = currentWord.substring(0, currentWord.length() - 1);
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
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

package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WordDropGame extends JPanel implements ActionListener, KeyListener {

    private final Timer timer, wordTimer;
    private final ArrayList<String> words;
    private final ArrayList<Integer> wordX, wordY;
    private final ArrayList<ImageIcon> wordImages;
    private final Random random;
    private String[] wordBank;
    private String currentWord = "";
    private int score = 0, lives = 3;
    private boolean gameOver = false;

    private final JButton playButton, pauseButton, replayButton, backButton;
    private final HashMap<String, ImageIcon> imageMap;

    public WordDropGame(JFrame frame) {
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        setBackground(Color.LIGHT_GRAY);

        words = new ArrayList<>();
        wordX = new ArrayList<>();
        wordY = new ArrayList<>();
        wordImages = new ArrayList<>();
        random = new Random();
        imageMap = new HashMap<>();

        timer = new Timer(50, this);
        wordTimer = new Timer(2000, e -> addWord());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        playButton = new JButton("Play");
        playButton.addActionListener(e -> startGame());
        buttonPanel.add(playButton);

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> pauseGame());
        pauseButton.setEnabled(false);
        buttonPanel.add(pauseButton);

        replayButton = new JButton("Play Again");
        replayButton.addActionListener(e -> resetGame());
        replayButton.setVisible(false);
        buttonPanel.add(replayButton);

        backButton = new JButton("Back to Chapters");
        backButton.addActionListener(e -> {
            stopTimers();
            frame.setContentPane(new ChapterSelection(frame, this));
            frame.revalidate();
            frame.repaint();
        });
        buttonPanel.add(backButton);

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
    }

    private void startGame() {
        timer.start();
        wordTimer.start();
        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
        replayButton.setVisible(false);
        requestFocusInWindow();
    }

    private void pauseGame() {
        timer.stop();
        wordTimer.stop();
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }

    private void resetGame() {
        stopTimers();
        initializeGame();
        startGame();
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

        g.setColor(Color.YELLOW);
        String levelText = String.format("Level: %d | Score: %d", level, score);
        FontMetrics metricsLevel = g.getFontMetrics(g.getFont());
        int levelXPos = getWidth() - metricsLevel.stringWidth(levelText) - 20;
        g.drawString(levelText, levelXPos, 30);

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);

            if (wordImages.containsKey(word)) {
                Image image = wordImages.get(word);
                g.drawImage(image, wordX.get(i), wordY.get(i), 250, 200, this);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString(word, wordX.get(i) + 20, wordY.get(i) + 120);
            } else {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString(word, wordX.get(i), wordY.get(i));
            }
        }

        g.setColor(Color.GREEN);
        g.drawString("Typed Word: " + currentWord, 20, getHeight() - 40);
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

}
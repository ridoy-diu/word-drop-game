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

    public void setWordBank() {
        
    }

    private void loadImages() {
        
    }

    public void initializeGame() {
        words.clear();
        wordX.clear();
        wordY.clear();
        wordImages.clear();
        currentWord = "";
        score = 0;
        lives = 3;
        gameOver = false;
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        replayButton.setVisible(false);
        requestFocusInWindow();
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

    private void addWord() {
        if (wordBank != null && words.size() < 10) {
            String word = wordBank[random.nextInt(wordBank.length)];
            int x, y = 0;

            int retryLimit = 50;
            int attempts = 0;
            boolean positionValid;

            do {
                positionValid = true;
                x = random.nextInt(Math.max(200, getWidth() - 200));
                for (int i = 0; i < words.size(); i++) {
                    int existingX = wordX.get(i);
                    int existingY = wordY.get(i);
                    if (Math.abs(x - existingX) < 220 && Math.abs(y - existingY) < 220) {
                        positionValid = false;
                        break;
                    }
                }
                attempts++;
            } while (!positionValid && attempts < retryLimit);

        }
        

    }

    public void stopTimers() {
        timer.stop();
        wordTimer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            return;
        }

        for (int i = 0; i < wordY.size(); i++) {
            wordY.set(i, wordY.get(i) + 2);
            if (wordY.get(i) > getHeight() - 200) {
                words.remove(i);
                wordX.remove(i);
                wordY.remove(i);
                wordImages.remove(i);
                lives--;
                currentWord = "";
                if (lives <= 0) {
                    gameOver = true;
                    stopTimers();
                    playButton.setEnabled(false);
                    pauseButton.setEnabled(false);
                    replayButton.setVisible(true);
                }
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            return;
        }

        char keyChar = e.getKeyChar();

        if (Character.isLetter(keyChar)) {
            currentWord += keyChar;
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && currentWord.length() > 0) {
            currentWord = currentWord.substring(0, currentWord.length() - 1);
        }

        for (int i = 0; i < words.size(); i++) {
            if (currentWord.equalsIgnoreCase(words.get(i))) {
                words.remove(i);
                wordX.remove(i);
                wordY.remove(i);
                wordImages.remove(i);
                score += 10;
                currentWord = "";
                break;
            }
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
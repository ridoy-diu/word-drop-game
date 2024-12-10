package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.*;
import java.net.URL;

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

    public void setWordBank(String[] wordBank, String imageFolder) {
        this.wordBank = wordBank;
        loadImages(imageFolder);
    }

    private void loadImages(String folderName) {
        imageMap.clear();
        for (String word : wordBank) {
            try {
                String imagePath = "/images/" + folderName + "/" + word + ".jpg";
                URL resource = getClass().getResource(imagePath);
                if (resource != null) {
                    ImageIcon icon = new ImageIcon(resource);
                    imageMap.put(word, icon);
                } else {
                    System.err.println("Image not found: " + imagePath);
                }
            } catch (Exception e) {
                System.err.println("Error loading image for word: " + word + " in folder: " + folderName);
            }
        }
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

            if (attempts >= retryLimit) {
                System.err.println("Failed to place word after " + retryLimit + " attempts");
                return;

            }

            words.add(word);
            wordX.add(x);
            wordY.add(y);

            ImageIcon originalIcon = imageMap.get(word);
            if (originalIcon != null) {
                Image resizedImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                wordImages.add(new ImageIcon(resizedImage));
            } else {
                wordImages.add(null);
            }

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
        super.paintComponent(g);

        if (gameOver) {
            g.setColor(Color.DARK_GRAY);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            String message = "Game Over! :(";
            int messageX = (getWidth() - g.getFontMetrics().stringWidth(message)) / 2;
            g.drawString(message, messageX, getHeight() / 2 - 50);

            String scoreMessage = "Score: " + score;
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
            g.drawString(scoreMessage, (getWidth() - g.getFontMetrics().stringWidth(scoreMessage)) / 2, getHeight() / 2 + 20);

            return;
        }

        for (int i = 0; i < words.size(); i++) {
            int x = wordX.get(i);
            int y = wordY.get(i);

            g.setColor(Color.DARK_GRAY);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            g.drawString(words.get(i), x + 50, y - 10);

            
            ImageIcon icon = wordImages.get(i);
            if (icon != null) {
                icon.paintIcon(this, g, x, y);
            } else {
                g.setColor(Color.RED);
                g.fillRect(x, y, 200, 200);
                g.setColor(Color.WHITE);
                g.drawString("No Image", x + 50, y + 100);
            }

        }

        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("Lives: " + lives, getWidth() - 120, 30);

        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        g.drawString("Typed Word: " + currentWord + "_", 20, getHeight() - 40);


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
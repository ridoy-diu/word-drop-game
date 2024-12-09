package game;
import javax.swing.*;
import java.awt.*;

public class ChapterSelection extends JPanel {
    private final JFrame frame;
    private final WordDropGame gamePanel
    ;
    public ChapterSelection(JFrame frame, WordDropGame gamePanel) {
        if (frame == null || gamePanel == null) {
            throw new IllegalArgumentException("Frame and gamePanel cannot be null.");
        }

        this.frame = frame;
        this.gamePanel = gamePanel;

        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        JLabel welcomeLabel = new JLabel("Welcome to Word Drop Game. Hope you enjoy!");
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.DARK_GRAY);

        JLabel selectLabel = new JLabel("Select a Chapter:");
        selectLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        selectLabel.setForeground(Color.DARK_GRAY);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 15, 15)); 
        buttonPanel.setOpaque(false);

        for (int i = 0; i < chapters.length; i++) {
            final int index = i; 
            JButton chapterButton = new JButton(chapters[i]);
            chapterButton.setFont(new Font("Comic Sans MS", Font.BOLD, 24)); 
            chapterButton.setPreferredSize(new Dimension(250, 60)); 
            chapterButton.setForeground(Color.DARK_GRAY); 
            chapterButton.addActionListener(e -> startGameWithChapter(chapters[index], wordBanks[index])); final index
            chapterButton.setToolTipText("Click to start the " + chapters[i] + " chapter.");
            buttonPanel.add(chapterButton);  
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;

        gbc.gridy = 0;
        add(welcomeLabel, gbc);

        gbc.gridy = 1;
        add(selectLabel, gbc);

        gbc.gridy = 2;
        add(buttonPanel, gbc);
    }

    private void startGameWithChapter(String chapterName, String[] wordBank) {
        gamePanel.setWordBank(wordBank, chapterName.toLowerCase());
        gamePanel.initializeGame();
        frame.setContentPane(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocusInWindow();
    }




}

package game;

import javax.swing.*;
import java.awt.*;


public class WordDropGame extends JPanel {

    JButton playButton, pauseButton, playAgainButton;
    
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

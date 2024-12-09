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


}

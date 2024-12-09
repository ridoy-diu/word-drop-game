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


}

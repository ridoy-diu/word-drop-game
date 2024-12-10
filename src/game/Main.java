package game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Word Drop Game");

        WordDropGame game = new WordDropGame(frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.setContentPane(new ChapterSelection(frame, game));
        frame.setVisible(true);
    }

}

package game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame("Word Drop Game");
        WordDropGame game = new WordDropGame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(game);
        f.pack();

        f.setVisible(true);
    }
}

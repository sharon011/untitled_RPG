package main;

import javax.swing.*;

public class main {
    public static void main(String[] args){

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Game name");

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.startGameThread();
    }
}
package com.app.snake;

import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake classic");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

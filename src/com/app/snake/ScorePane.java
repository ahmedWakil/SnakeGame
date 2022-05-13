package com.app.snake;

import javax.swing.*;
import java.awt.*;

public class ScorePane extends JPanel {

    static final int PANE_WIDTH = 600;
    static final int PANE_SCORE_HEIGHT = 150;

    int currentScore = 0;
    ScorePane(){
        this.setPreferredSize(new Dimension(PANE_WIDTH, PANE_SCORE_HEIGHT+40));
        this.setBackground(Color.GREEN);
        this.setVisible(true);
    }

    public void setScore(int score){
        currentScore = score;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.PINK);
        g.setFont(new Font("Dialog", Font.PLAIN, 45));
        g.drawString("Score: " + currentScore, PANE_WIDTH/2 - 275, 155);
    }
}
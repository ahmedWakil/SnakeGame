package com.app.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int PANE_WIDTH = 600;
    static final int PANE_HEIGHT = 600;
    static final int PANE_SCORE_HEIGHT = 150;
    static final int TILE_SIZE = 25;
    static final int TOTAL_TILES = (PANE_WIDTH*PANE_HEIGHT)/TILE_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[TOTAL_TILES];
    final int y[] =  new int[TOTAL_TILES];
    int wormSize = 3;
    int foodX;
    int foodY;
    int eaten;
    String direction = "right";
    boolean gameOver = false;
    Timer timer;
    Random random;
    ScorePane scorePanel;
    int score = 0;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(PANE_WIDTH, PANE_HEIGHT+PANE_SCORE_HEIGHT-50));
        this.setBackground (Color.black);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
        // add listesner for kayboard inputs
        this.addKeyListener(new Inputreader());
        scorePanel = new ScorePane();
        this.add(scorePanel, BorderLayout.SOUTH);
        start();
    }

    public void start(){
        newfood();
        gameOver = false;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);


        draw(g);
    }

    public void draw(Graphics g){

        for(int i = 0; i < PANE_HEIGHT/TILE_SIZE; i++){
            g.drawLine(i*TILE_SIZE, 0, i*TILE_SIZE, PANE_HEIGHT);
            g.drawLine(0, i*TILE_SIZE, PANE_WIDTH, i*TILE_SIZE);
        }

        if (!gameOver){
            //draw food
            g.setColor(Color.red);
            g.fillRect(foodX, foodY, TILE_SIZE, TILE_SIZE);

            //draw the worm body
            for(int i = 0; i < wormSize; i++){

                //draw the head of the worm here
                if (i == 0){
                    g.setColor(Color.blue);
                    g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
                    //draw the rest of the body here
                } else {
                    g.setColor(Color.cyan);
                    g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
                }
            }
        } else {
            gameOver();
        }
    }

    public void newfood(){
        foodX = random.nextInt((int)(PANE_WIDTH/TILE_SIZE))*TILE_SIZE;
        foodY = random.nextInt((int)(PANE_HEIGHT/TILE_SIZE))*TILE_SIZE;
    }

    public void moveWorm(){
        for (int i = wormSize; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        if (direction.equals("up")){
            y[0] = y[0] - TILE_SIZE;
        }

        if (direction.equals("down")){
            y[0] = y[0] + TILE_SIZE;
        }

        if (direction.equals("left")){
            x[0] = x[0] - TILE_SIZE;
        }

        if (direction.equals("right")){
            x[0] = x[0] + TILE_SIZE;
        }
    }

    public void checkCollisions() {

        //collotions with food --> results in the worm size to increase
        if (x[0] == foodX && y[0] == foodY){
            wormSize++;
            score++;
            scorePanel.setScore(score);
            newfood();
        }

        //collisions with body parts --> results in a game over
        for(int i = wormSize; i > 0; i--){
            if ((x[0] == x[i])&&(y[0]==y[i])){
                gameOver = true;
            }
        }

        //collitions with walls --> results in a game over
        if (x[0] < 0 || x[0] > PANE_WIDTH - TILE_SIZE || y[0] < 0 || y[0] > PANE_HEIGHT - TILE_SIZE){
            gameOver = true;
        }

        if (gameOver){
            timer.stop();
        }
    }

    public void gameOver(){
        //do game over graphics over here
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!gameOver){
            moveWorm();
            checkCollisions();
        }
        repaint();

    }

    public class Inputreader extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP){
                if (!direction.equals("down")){
                    direction = "up";
                }
            }

            if (key == KeyEvent.VK_DOWN){
                if (!direction.equals("up")){
                    direction = "down";
                }
            }

            if (key == KeyEvent.VK_LEFT){
                if (!direction.equals("right")){
                    direction = "left";
                }
            }

            if (key == KeyEvent.VK_RIGHT){
                if (!direction.equals("left")){
                    direction = "right";
                }
            }
        }
    }
}

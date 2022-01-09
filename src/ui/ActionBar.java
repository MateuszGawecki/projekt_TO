package ui;

import objects.Tile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static Game.GameStates.MENU;
import static Game.GameStates.setGameState;
import static helpz.Constants.Towers.*;

public class ActionBar extends Bar{

    private MyButton bMenu;
    private Playing playing;

    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;

    private DecimalFormat formatter;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x,y,width,height);
        this.playing=playing;
        formatter = new DecimalFormat("0.0");

        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 2, 642, 100, 30);

        towerButtons = new MyButton[3];

        int w=50;
        int h=50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w*1.1f);

        for(int i = 0; i<towerButtons.length; i++){
            towerButtons[i] = new MyButton("", xStart + xOffset * i,yStart,w,h,i);
        }
    }

    public void draw(Graphics g){
        g.setColor(new Color(222,123,15));
        g.fillRect(x,y,width,height);

        drawButtons(g);

        drawDisplayedTower(g);

        drawWaveInfo(g);
    }

    private void drawWaveInfo(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        drawWaveTimerInfo(g);
        drawEnemiesLeftInfo(g);
        drawWavesLeftInfo(g);
    }

    private void drawWavesLeftInfo(Graphics g) {
        int current = playing.getWaveManager().getWaveIndex();
        int size = playing.getWaveManager().getWaves().size();
        g.drawString("Wave: " + (current + 1) + " / " + size, 425,690);
    }

    private void drawEnemiesLeftInfo(Graphics g) {
        int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
        g.drawString("Enemies left: " + remaining, 425,720);
    }

    private void drawWaveTimerInfo(Graphics g){
        if(playing.getWaveManager().isWaveTmerStarted()){
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formattedText = formatter.format(timeLeft);

            g.drawString("Time left: " + formattedText,425,660);
        }
    }

    private void drawDisplayedTower(Graphics g) {
        if(displayedTower != null){
            g.setColor(Color.GRAY);
            g.fillRect(410,645,220,85);
            g.setColor(Color.BLACK);
            g.drawRect(410,645,220,85);
            g.drawRect(420,650,50,50);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 420, 650, 50,50, null);
            g.setFont(new Font("LucidaSans", Font.BOLD,15));
            g.drawString("" + GetName(displayedTower.getTowerType()), 490, 660);
            g.drawString("ID: " + displayedTower.getId(), 490, 675);

            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);
        }
    }

    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawOval(displayedTower.getX() + 16 - (int)(displayedTower.getRange()*2)/2,
                displayedTower.getY() + 16 - (int) (displayedTower.getRange()*2)/2,
                (int) displayedTower.getRange()*2,
                (int) displayedTower.getRange()*2);
    }

    private void drawDisplayedTowerBorder(Graphics g) {
        g.setColor(Color.CYAN);
        g.drawRect(displayedTower.getX(), displayedTower.getY(), 32,32);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);

        for(MyButton b: towerButtons) {
            g.setColor(Color.GRAY);
            g.fillRect(b.getX(),b.getY(),b.getWidth(), b.getHeight());

            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.getX(), b.getY(), b.getWidth(), b.getHeight(), null);

            drawButtonFeedBack(g,b);
        }
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            setGameState(MENU);
        else {
            for (MyButton b: towerButtons){
                if(b.getBounds().contains(x,y)){
                    selectedTower = new Tower(0,0,-1, b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else {
            for(MyButton b : towerButtons)
                if(b.getBounds().contains(x,y)) {
                    b.setMouseOver(true);
                    return;
                }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else
            for(MyButton b: towerButtons)
                if(b.getBounds().contains(x,y)){
                    b.setMousePressed(true);
                    return;
                }
    }


    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();

        for (MyButton b:towerButtons)
            b.resetBooleans();
    }

    public void displayTower(Tower t) {
        displayedTower = t;
    }
}

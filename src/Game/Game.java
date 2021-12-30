package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame implements Runnable {

    private GameScreen gameScreen;
    private BufferedImage img;
    private Thread gameThread;

    private final double FPS_set = 120.0;
    private final double UPS_set = 60.0;

    public static void main(String[] args) {

        Game game = new Game();
        game.start();
    }

    public Game(){

        importImg();

        setSize(656,678);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //window in center of screen

        gameScreen = new GameScreen(img);
        add(gameScreen);

        setVisible(true);
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0/ FPS_set;
        double timePerUpdate = 1000000000.0/ UPS_set;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        while (true){
            //Render
            if(System.nanoTime() - lastFrame >= timePerFrame){
                repaint();
                lastFrame = System.nanoTime();
                frames++;
            }

            //Update
            if(System.nanoTime() - lastUpdate >= timePerUpdate){
                updateGame();
                lastUpdate = System.nanoTime();
                updates++;
            }


            if(System.currentTimeMillis() - lastTimeCheck >= 1000){
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames= 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    private void updateGame(){

    }

    private void start(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/spriteatlas.png");

        try {
            img = ImageIO.read(is);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

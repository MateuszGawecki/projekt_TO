package Game;

import inputs.MyKeyboardListener;
import inputs.MyMouseListener;
import scenes.Menu;
import scenes.Playing;
import scenes.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame implements Runnable {

    private BufferedImage img;
    private Thread gameThread;

    private final double FPS_set = 120.0;
    private final double UPS_set = 60.0;

    private MyMouseListener myMouseListener;
    private MyKeyboardListener myKeyboardListener;

    //Classes
    private GameScreen gameScreen;
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;

    public static void main(String[] args) {

        Game game = new Game();
        game.initInputs();
        game.start();
    }

    public Game(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //window in center of screen

        initClasses();
        add(gameScreen);

        pack();
        setVisible(true);
    }

    private void initClasses() {
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
    }

    private void initInputs(){
        myMouseListener = new MyMouseListener();
        myKeyboardListener = new MyKeyboardListener();

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);

        addKeyListener(myKeyboardListener);

        requestFocus();
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

        long now;

        while (true){
            now = System.nanoTime();
            //Render
            if(now - lastFrame >= timePerFrame){
                repaint();
                lastFrame = now;
                frames++;
            }

            //Update
            if(now - lastUpdate >= timePerUpdate){
                updateGame();
                lastUpdate = now;
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


    //Getters and setters
    public Render getRender(){
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Settings getSettings() {
        return settings;
    }
}

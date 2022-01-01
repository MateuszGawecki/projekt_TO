package Game;

import helpz.LoadSave;
import inputs.MyKeyboardListener;
import inputs.MyMouseListener;
import managers.TileManager;
import scenes.Editing;
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

    //Classes
    private GameScreen gameScreen;
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private Editing editing;

    private TileManager tileManager;

    public static void main(String[] args) {

        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    public Game(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //window in center of screen
        setResizable(false);

        initClasses();
        createDefaultLevel();
        add(gameScreen);

        pack();
        setVisible(true);
    }

    private void createDefaultLevel() {
        int[] arr = new int[400];

        for(int i=0; i<arr.length; i++)
            arr[i] = 0;

        LoadSave.CreateLevel("new level",arr);
    }

    private void initClasses() {
        tileManager = new TileManager();
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        editing = new Editing(this);
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
        switch (GameStates.gameState){
            case PLAYING:
                playing.update();
                break;
            case MENU:
                break;
            case SETTINGS:
                break;
            case EDITING:
                editing.update();
                break;
            default:
                break;
        }
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

    public Editing getEditing(){
        return editing;
    }

    public TileManager getTileManager() {
        return tileManager;
    }
}

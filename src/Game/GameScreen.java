package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {

    private Random random;
    private BufferedImage img;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();


    public GameScreen(BufferedImage img){
        random = new Random();
        this.img = img;

        loadSprites();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for(int y=0 ; y<20; y++){
            for(int x=0 ; x<20; x++){
                g.drawImage(sprites.get(getRandomInt()),x*32, y*32, null);
            }
        }
    }

    private void loadSprites(){
        for(int y=0; y<10; y++){
            for(int x=0; x<10; x++){
                sprites.add(img.getSubimage(x*32,y*32,32,32));
            }
        }
    }

    private int getRandomInt(){
        return random.nextInt(100);
    }

    private Color getRandomColor(){
        return new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
    }
}

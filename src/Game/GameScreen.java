package Game;

import inputs.MyKeyboardListener;
import inputs.MyMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {

    private Game game;
    private Dimension size;

    private MyMouseListener myMouseListener;
    private MyKeyboardListener myKeyboardListener;

    public GameScreen(Game game) {
        this.game = game;

        setPanelSize();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.getRender().render(g);
    }

    private void setPanelSize() {
        size = new Dimension(640, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }


    public void initInputs(){
        myMouseListener = new MyMouseListener(game);
        myKeyboardListener = new MyKeyboardListener(game);

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);

        addKeyListener(myKeyboardListener);

        requestFocus();
    }
}

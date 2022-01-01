package helpz;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImgFix {
   //Rotate

    public static BufferedImage getRotImg(BufferedImage img, int rotAngle){
        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage newImg = new BufferedImage(w,h,img.getType());
        Graphics2D g2d = newImg.createGraphics();

        g2d.rotate(Math.toRadians(rotAngle), w/2, h/2);
        g2d.drawImage(img,0,0,null);
        g2d.dispose();

        return newImg;
    }

    //Img layer build

    public static BufferedImage buildImg(BufferedImage[] imgs){
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();

        BufferedImage newImg = new BufferedImage(w,h,imgs[0].getType());
        Graphics2D g2d = newImg.createGraphics();

        for(BufferedImage i : imgs){
            g2d.drawImage(i,0,0,null);
        }

        g2d.dispose();
        return newImg;
    }

    //Rotate Secound Img only

    public static BufferedImage getBuildRotImg(BufferedImage[] imgs, int rotAngle, int rotAtIndex){
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();

        BufferedImage newImg = new BufferedImage(w,h,imgs[0].getType());
        Graphics2D g2d = newImg.createGraphics();

        for(int i = 0 ; i< imgs.length; i++){
            if(rotAtIndex == i)
                g2d.rotate(Math.toRadians(rotAngle), w/2, h/2);
            g2d.drawImage(imgs[i],0,0,null);
            if(rotAtIndex == i)
                g2d.rotate(Math.toRadians(-rotAngle), w/2, h/2);
        }

        g2d.dispose();
        return newImg;
    }

    //Rotate Second Img only + animation
    public static BufferedImage[] getBuildRotImg(BufferedImage[] imgs,BufferedImage secImage, int rotAngle){
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();

        BufferedImage[] arr = new BufferedImage[imgs.length];

        for(int i=0; i< imgs.length;i++){
            BufferedImage newImg = new BufferedImage(w,h,imgs[0].getType());
            Graphics2D g2d = newImg.createGraphics();

            g2d.drawImage(imgs[i],0,0,null);
            g2d.rotate(Math.toRadians(rotAngle), w/2, h/2);
            g2d.drawImage(secImage,0,0,null);

            g2d.dispose();

            arr[i] = newImg;
        }
        return arr;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa1algebra;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Armando
 */
public class Graficos {
    void pintarPunto(Graphics g,double xD,double yD,int color){
        int x=(int) xD;
        int y=(int) yD;
        if (color==0){
            g.setColor(Color.BLACK);
            g.fillOval(x, y, 7, 7);
        }
        if (color==1){
            g.setColor(Color.RED);
            g.fillOval(x, y, 7, 7);
        }
        if (color==2){
            g.setColor(Color.GREEN);
            g.fillOval(x, y, 7, 7);
        }
        if (color==3){
            g.setColor(Color.MAGENTA);
            g.fillOval(x, y, 7, 7);
        }
        if (color==4){
            g.setColor(Color.BLUE);
            g.fillOval(x, y, 7, 7);
        }
        if (color==5){
            g.setColor(Color.ORANGE);
            g.fillOval(x, y, 7, 7);
        }
        if (color==6){
            g.setColor(Color.PINK);
            g.fillOval(x, y, 7, 7);
        }
    }
}

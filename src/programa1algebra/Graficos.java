/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa1algebra;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Armando
 */
public class Graficos {
    void pintarPunto(Graphics g , int x, int y , int color){
        
        ArrayList<Color> colores = new ArrayList<>();
        colores.add(Color.BLACK);
        colores.add(Color.RED);
        colores.add(Color.GREEN);
        colores.add(Color.MAGENTA);
        colores.add(Color.BLUE);
        colores.add(Color.ORANGE);
        colores.add(Color.PINK);
        colores.add(Color.YELLOW);
        g.setColor(colores.get(color));
        g.fillOval(x, y, 7, 7);
    }
}

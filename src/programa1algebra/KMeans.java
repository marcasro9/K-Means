/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa1algebra;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author rshum
 */
public class KMeans {

    public KMeans() {
    }
    
    public ArrayList<Punto> generarPuntos(int cantidad,int k){
        ArrayList<Punto> puntoArrayList = new ArrayList<>();
        Random random = new Random();
        for(int n=0; n<cantidad;n++){
            double x = random.nextInt(11)-5;
            double y = random.nextInt(11)-5;
            Punto punto = new Punto(x,y,false);
            puntoArrayList.add(punto);
            //System.out.println("("+punto.x+","+punto.y+") "+punto.esCentro);
        }
        return puntoArrayList;
    }
    public ArrayList<Punto> generarPuntosCentros(ArrayList<Punto> puntoArrayList,int k,int cantidad){
        ArrayList<Punto> puntoCentroArrayList = new ArrayList<>();
        Random random = new Random();
        for(int n=0; n<k;n++){
            int c = random.nextInt(cantidad);
            double x = puntoArrayList.get(c).x;
            double y = puntoArrayList.get(c).y;
            Punto punto=new Punto(x,y,true);
            puntoCentroArrayList.add(punto);
        }
        return puntoCentroArrayList;
    }
}

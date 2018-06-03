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
    
    public ArrayList<Punto> generarPuntos(int cantidad){
        ArrayList<Punto> puntoArrayList = new ArrayList<>();
        Random random = new Random();
        ArrayList<Punto> numerox= new ArrayList<>();
        int cont=0;
        while(cont<cantidad){
            double x = random.nextInt(11)-5;
            double y = random.nextInt(11)-5;
            Punto punto = new Punto(x,y,false);
            if(numerox.contains(punto)==false){
                numerox.add(punto);
                puntoArrayList.add(punto);
                cont++;
            }
            else{
                System.out.println(":v");
            }
            System.out.println("("+punto.x+","+punto.y+") "+punto.esCentro);
        }
        return puntoArrayList;
    }
    public ArrayList<Punto> generarPuntosCentros(ArrayList<Punto> puntoArrayList,int k,int cantidad){
        ArrayList<Punto> puntoCentroArrayList = new ArrayList<>();
        ArrayList<Integer> numero = new ArrayList<Integer>();
        Random random = new Random();
        int n=0;
        while(n<k){
            int c = random.nextInt(cantidad);
            if(numero.contains(c)==false){
                numero.add(c);
                System.out.println(c);
                double x = puntoArrayList.get(c).x;
                double y = puntoArrayList.get(c).y;
                Punto punto=new Punto(x,y,true);
                puntoCentroArrayList.add(punto);   
                n++;
            }
        }
        return puntoCentroArrayList;
    }
}

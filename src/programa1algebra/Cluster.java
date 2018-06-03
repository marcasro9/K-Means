
package programa1algebra;

import java.util.ArrayList;

public class Cluster {
    Punto centro;
    ArrayList<Punto> puntos;

    public Cluster()
    {
        this.centro = null;
        this.puntos = new ArrayList<>();
    }
    
    public Cluster(Punto centro, ArrayList<Punto> puntos) {
        this.centro = centro;
        this.puntos = puntos;
    }
    
    public void calcularNuevoCentro()
    {
        double nuevoX = 0;
        double nuevoY = 0;
        
        for (int i = 0 ; i < puntos.size(); i++ )
        {
            double xActual = puntos.get(i).getX();
            nuevoX += xActual;
           
            double yActual = puntos.get(i).getY();
            nuevoY += yActual;
        }
        
        nuevoX = nuevoX / puntos.size();
        nuevoY = nuevoY / puntos.size();
        
        Punto nuevoCentro = new Punto(nuevoX, nuevoY , true);
        this.centro = nuevoCentro;
    }
    
    public double cardinalidad()
    {
        return puntos.size();
    }
}

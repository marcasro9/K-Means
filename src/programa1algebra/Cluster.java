
package programa1algebra;

import java.util.ArrayList;
import programa1algebra.Utils.ClusterHandler;

public class Cluster {
    public Punto centro;
    public ArrayList<Punto> puntos;

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
    
    public double calcular_s_i()
    {
        ClusterHandler ch = new ClusterHandler();
        double distanciasTotales = 0;
        for (Punto punto_Iterable : puntos)
        {
            distanciasTotales += ch.euclideanDistance(this.centro , punto_Iterable);
        }
    
        double s_i = distanciasTotales / cardinalidad();
        
        return s_i;
    }

}

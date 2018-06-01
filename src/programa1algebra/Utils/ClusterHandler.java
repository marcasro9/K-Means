
package programa1algebra.Utils;

import java.util.ArrayList;
import programa1algebra.Cluster;
import programa1algebra.Punto;

public class ClusterHandler {
    ArrayList<Cluster> clusters;
    ArrayList<Punto> puntos;
    
    public ClusterHandler()
    {
        //default constructor
    }
    
    public ClusterHandler(ArrayList<Cluster> clusters) {
        this.clusters = clusters;
    }
    
    public double euclideanDistance(Punto puntoUno, Punto puntoDos)
    {
        double x = Math.pow( (puntoUno.getX() - puntoDos.getX()) , 2);
        double y = Math.pow( (puntoUno.getY() - puntoDos.getY()) , 2);
        return Math.sqrt(x + y );
    }
    
    
}

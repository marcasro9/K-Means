
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
        //System.out.println("Valores: " + x + " " + y);
        //System.out.println("Resultado: " +Math.sqrt(x + y ));
        return Math.sqrt(x + y );
    }
    
    //Fuera de Cluster
    //Only works for negative numbers
    public double getMaxFromList(double[] lista)
    {
        double max = 0;
        
        for (int i = 0 ; i < lista.length ; i++)
	{
            if (lista[i] > max)
                max = lista[i];
	}
        
        return max;
    }

        //Esto va fuera de Cluster
        public double calculate_R_i_For_Cluster(Cluster cluster , int clusterIndex)
        {
            double[] listaR_i_de_Cluster = new double [clusters.size()- 1];

            for (Cluster cluster_Iterable : clusters)
            {
                if (cluster.equals(cluster_Iterable))
                    System.out.println("Somos iguales");

                else
                {
                    double s_i = cluster.calcular_s_i();
                    double s_j = cluster_Iterable.calcular_s_i();
                    double d_ij = euclideanDistance(cluster.centro , cluster_Iterable.centro);
                    listaR_i_de_Cluster[clusterIndex] = (s_i + s_j) / d_ij;
                }

            }

            return getMaxFromList(listaR_i_de_Cluster);
    }

    public double calculate_R_k_For_K(int k , ArrayList<Punto> puntos , ArrayList<Punto> centros)
    {
        double[] r_i_List = new double[k];
        //ArrayList<Cluster> clusters = new hacerClusters(centros , puntos); 

        for (Cluster cluster_Actual : clusters)
        {
            int index = 0;
            r_i_List[index] = calculate_R_i_For_Cluster(cluster_Actual , index);
            index++;
        }

        double r_k = 0;
        
        //Sumatoria de r_i's
        for (int j = 0 ; j < r_i_List.length ; j++)
        {
            r_k += r_i_List[j];
        }

        r_k = r_k / k;
        return r_k;
    }
    
    
}

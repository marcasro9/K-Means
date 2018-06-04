
package programa1algebra;

import java.util.ArrayList;
import java.util.Random;


public class KMeans {

    public KMeans() 
    {
        //Default constructor
    }
    
    public ArrayList<Punto> generarPuntos(int cantidad)
    {
        ArrayList<Punto> puntoArrayList = new ArrayList<>();
        Random random = new Random();
        ArrayList<Punto> listaPuntos= new ArrayList<>();
        int cont = 0;
        double minimo = -5.0;
        double maximo = 5.0;
        while(cont < cantidad)
        {
            double x = minimo + random.nextDouble() * (maximo-minimo);
            double y = minimo + random.nextDouble() * (maximo-minimo);
            Punto punto = new Punto(x,y,false);
            
            if(yaEstaEnListaPuntos(punto , listaPuntos) == false)
            {
                listaPuntos.add(punto);
                puntoArrayList.add(punto);
                cont++;
                //System.out.println("("+punto.x+","+punto.y+") "+punto.esCentro);
            }
            else
            {
                //System.out.println("Se hizo un punto repetido.");
                //System.out.println("("+punto.x+","+punto.y+") "+punto.esCentro);
            }
            
            
        }
        return puntoArrayList;
    }
    
    public void generarPuntosCentros(ArrayList<Punto> puntoArrayList , int k ){
        ArrayList<Integer> numero = new ArrayList<Integer>();
        Random random = new Random();
        int n = 0;
        while(n < k)
        {
            int c = random.nextInt(puntoArrayList.size());
            if(numero.contains(c) == false)
            {
                numero.add(c);
                System.out.println("Selecciono la posicion " + c);
                Punto punto_Actual = puntoArrayList.get(c);
                punto_Actual.setCentro(true); 
                n++;
            }
        }
    }
    public boolean yaEstaEnListaPuntos(Punto punto , ArrayList<Punto> listaPuntos)
    {
        for (int i = 0 ; i < listaPuntos.size() ; i++)
        {
            if (listaPuntos.get(i).esIgual(punto))
                return true;
        }
        
        return false;
    }
}

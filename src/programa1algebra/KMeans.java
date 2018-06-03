
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
        
        while(cont < cantidad)
        {
            double x = random.nextInt(11)-5;
            double y = random.nextInt(11)-5;
            Punto punto = new Punto(x,y,false);
            
            if(yaEstaEnListaPuntos(punto , listaPuntos) == false)
            {
                listaPuntos.add(punto);
                puntoArrayList.add(punto);
                cont++;
                System.out.println("("+punto.x+","+punto.y+") "+punto.esCentro);
            }
            
            else
            {
                System.out.println("No sé hacer prints significativos. Ni commits.");
                System.out.println("("+punto.x+","+punto.y+") "+punto.esCentro);
            }
            
            
        }
        return puntoArrayList;
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
    
    public ArrayList<Punto> generarPuntosCentros(ArrayList<Punto> puntoArrayList,int k,int cantidad){
        ArrayList<Punto> puntoCentroArrayList = new ArrayList<>();
        ArrayList<Integer> numero = new ArrayList<Integer>();
        Random random = new Random();
        int n = 0;
        
        while(n < k)
        {
            int c = random.nextInt(cantidad);
            
            if(numero.contains(c)==false)
            {
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


package programa1algebra;

import com.sun.prism.paint.Color;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import programa1algebra.Utils.ClusterHandler;


public class VentanaPrincipal extends javax.swing.JFrame {
    private ArrayList<JPanel> jPaneles;
    private int indice;
    private Graficos grafico;
    private ArrayList<Punto> puntosFijosPara_K_Recomendado;
    

    /**
     * Creates new form VentanaPrincipal
     */
    
    public VentanaPrincipal() 
    {
        initComponents();
        grafico=new Graficos();
        jPaneles = new ArrayList<>();
        this.indice=1;
        setLocationRelativeTo(null);
        puntosFijosPara_K_Recomendado = new ArrayList<>();
        //this.setExtendedState(MAXIMIZED_BOTH);
        crearJPanel(9);
    }

    public void generarKMeans (int n, int k)
    {
        KMeans kmeans = new KMeans();
        ArrayList<Punto> puntoArrayList = kmeans.generarPuntos(n);
        
        for (int i = 0 ; i < puntoArrayList.size() ; i++)
        {
            double x = puntoArrayList.get(i).getX();
            double y = puntoArrayList.get(i).getY();
            Punto nuevoPunto = new Punto(x, y, false);
            puntosFijosPara_K_Recomendado.add(nuevoPunto);
        }     
                
        kmeans.generarPuntosCentros(puntoArrayList,k);
        
        pintar(puntoArrayList,0,0);
        int matrizVieja[][] = new int[k][n];
        int matrizNueva[][] = calcularMatrizEuclidean(n , k ,puntoArrayList);
        agruparK(n,k,puntoArrayList,this.indice,matrizVieja,matrizNueva);        
    }
    
    //Esto hace que se generan los clusters
    public void generarKMeansPara_K_Recomendado(ArrayList<Punto> datosFijos , int k)
    {
        System.out.println("Entro a Generar con datos: " + datosFijos.size());
        ArrayList<Cluster> inicial = new ArrayList<>();
        
        int n = datosFijos.size();
        
        KMeans kmeans = new KMeans();
        kmeans.generarPuntosCentros(datosFijos, k);
        
        int matrizVieja[][] = new int[k][n];
        int matrizNueva[][] = calcularMatrizEuclidean(n , k , datosFijos);
        agruparKPara_K_Recomendado(n , k , datosFijos , this.indice , matrizVieja, matrizNueva , inicial ); 
    }
    
    public void pintar(ArrayList<Punto> puntoArrayList,int jPanel,int color){
        for (int x=0; x < puntoArrayList.size(); x++) 
        {
            if(puntoArrayList.get(x).esCentro==true){
                //System.out.println("Centro es true");
                int coordX=(int)((puntoArrayList.get(x).x+7)*20);
                int coordY=(int)((puntoArrayList.get(x).y+6)*15);
                grafico.pintarPunto(jPaneles.get(jPanel).getGraphics(), coordX, coordY,1);
            }
            else{
                //System.out.println("Centro es false");
                int coordX=(int)((puntoArrayList.get(x).x+7)*20);
                int coordY=(int)((puntoArrayList.get(x).y+6)*15);
                grafico.pintarPunto(jPaneles.get(jPanel).getGraphics(), coordX, coordY,color);
            }
        }
    }
    
    public void pintarCluster(Cluster cluster,int jPanel,int color)
    {
        //System.out.println("Cantidad de puntos del cluster "+cluster.puntos.size());
        for(int i=0;i<cluster.puntos.size();i++){
            int coordX=(int)((cluster.puntos.get(i).x+7)*20);
            int coordY=(int)((cluster.puntos.get(i).y+6)*15);
            grafico.pintarPunto(jPaneles.get(jPanel).getGraphics(), coordX, coordY,color);
        }
        int coordX=(int)((cluster.centro.x+7)*20);
        int coordY=(int)((cluster.centro.y+6)*15);
        grafico.pintarPunto(jPaneles.get(jPanel).getGraphics(), coordX, coordY,1);
        
    }
    
    public void agruparK(int n,int k,ArrayList<Punto> puntoArrayList,int indice,int[][] matrizVieja,int[][] matriz)
    {
        int contCluster = 0;
        int colorIndice = 2;
        //System.out.println(sonIguales(matrizVieja,matriz));
        if(sonIguales(matrizVieja,matriz) == false) // if (!condicionDeParada)
        {
            //System.out.println("Entro porque si hay cambios");
            if(indice<7){
                //System.out.println("Entro porque si hay cambios");
                for(int i = 0 ; i < puntoArrayList.size() ; i++){
                    if(puntoArrayList.get(i).esCentro==true){
                        //System.out.println("Entro a los puntoCentros");
                        Cluster cluster = new Cluster(puntoArrayList.get(i) , agruparPuntosEnClusters(n , k , contCluster , puntoArrayList , matriz) );
                        
                        //clustersFijosParaKRecomendado.add(cluster);
                        
                        cluster.calcularNuevoCentro();
                        Punto nuevoPunto=cluster.centro;
                        puntoArrayList.set(i, nuevoPunto);
                        contCluster++;
                        colorIndice++;
                        pintarCluster(cluster,indice,colorIndice);
                    }
                }
                int matrizNueva[][] = calcularMatrizEuclidean(n , k , puntoArrayList);
                agruparK(n , k , puntoArrayList, this.indice++,matriz,matrizNueva);
                }
            }   
        else
        {
            System.out.println("Ya termino");
        }
    }
    
    //Esto me va a dar los clusters
    public ArrayList<Cluster> agruparKPara_K_Recomendado(int n , int k, ArrayList<Punto> puntoArrayList,int indiceLocal,int[][] matrizVieja,int[][] matriz , ArrayList<Cluster> clustersEntrada)
    {
        //System.out.println("Largo puntos en agrupar: " + puntoArrayList.size());
        //ArrayList<Cluster> clustersFinales = clustersEntrada;
        System.out.println("Tamano clustersEntrada: " + clustersEntrada.size());
        
        int contCluster = 0;
        //int colorIndice = 2;
        //System.out.println(sonIguales(matrizVieja,matriz));
        if(sonIguales(matrizVieja,matriz) == false) // if (!condicionDeParada)
        {
            if(indiceLocal<7)
            {
                ArrayList<Cluster> clustersIteracion = new ArrayList<>();
                System.out.println("Entro porque si hay cambios");
                for(int i = 0 ; i < puntoArrayList.size() ; i++)
                {
                    if(puntoArrayList.get(i).esCentro==true)
                    {
                       
                        System.out.println("Entro a los puntoCentros");
                        Cluster cluster = new Cluster(puntoArrayList.get(i) , agruparPuntosEnClusters(n , k , contCluster , puntoArrayList , matriz) );
                        
                        clustersIteracion.add(cluster);
                        
                        cluster.calcularNuevoCentro();
                        Punto nuevoPunto=cluster.centro;
                        puntoArrayList.set(i, nuevoPunto);
                        contCluster++;
 
                    }
                }
                             
                int matrizNueva[][] = calcularMatrizEuclidean(n , k , puntoArrayList);
                return agruparKPara_K_Recomendado(n , k , puntoArrayList , indiceLocal++ , matriz , matrizNueva , clustersIteracion);
            }
            
        }
        
        else{
            System.out.println("Ya termino");
            System.out.println(" ");
            System.out.println("Tamano clustersFinales: " + clustersEntrada.size());
            return clustersEntrada; 
        }
        
        return clustersEntrada;
    }
    
    
    public void crearJPanel(int cont)
    {
        for(int x=0; x<cont;x++)
        {
            JPanel panel = new JPanel();
            //panel.setBackground(java.awt.Color.yellow);
            Border line = BorderFactory.createLineBorder(java.awt.Color.BLACK, 2);
            panel.setBorder(line);
            jPrincipal.add(panel);
            jPaneles.add(panel);
            jPrincipal.updateUI();
        }
    }
    public boolean sonIguales(int[][] matrizAnterior,int[][] matrizNueva){
        for(int i = 0; i < matrizAnterior.length;i++){
            for(int j = 0; j < matrizAnterior[0].length;j++){
                if(matrizAnterior[i][j] != matrizNueva[i][j]){
                    return false;
                }
            }
        }
        return true;
    } 
    
    public int[][] calcularMatrizEuclidean(int n , int k , ArrayList<Punto> puntoArrayList){
        double matriz[][] = new double[k][n];
        ClusterHandler clusterH= new ClusterHandler();
        
        int index = 0;
        for (Punto punto_Actual : puntoArrayList)
        {
            if (punto_Actual.esCentro)
            {
                for (int j = 0 ; j < puntoArrayList.size() ; j++)
                {
                    //System.out.println("Indice de j:" + j);
                    Punto punto_Iterable = puntoArrayList.get(j);
                    matriz[index][j] = clusterH.euclideanDistance(punto_Actual, punto_Iterable);
                    
                }
                index++;
            }
        }
        
        int matrizBinaria[][] = new int[k][n];
        
        for (int x = 0; x < matrizBinaria.length; x++) 
        {
            for (int y = 0; y < matrizBinaria[x].length; y++)
            {
                matrizBinaria[x][y]=0;
            }
        }
        
        //Iteramos sobre J porque vamos a iterar sobre columnas 
        //Usamos min = 0 porque no deberían haber valores negativos.
        for (int j = 0 ; j < n; j++)
        {
            //Numero en extremo grande para garantizar que en la primera iteracion, el primer valor quede como minimo.
            double min = 10000000;
            int posicion = 0;
            
            for (int i = 0 ; i < k; i++)
            {
                double valor_Actual = matriz[i][j];
                
                if (valor_Actual < min)
                {
                    min = valor_Actual;
                    posicion = i;
                }
            }
            
            matrizBinaria[posicion][j] = 1;            
        }
        return matrizBinaria;
    }
    
    public ArrayList<Punto> agruparPuntosEnClusters(int n, int k, int c , ArrayList<Punto> puntoArrayList , int[][]matrizBinaria)
    {
       ArrayList<Punto> puntosEnCluster = new ArrayList();
       //System.out.println("Tamaño de la lista de puntos"+puntoArrayList.size());
       for (int j = 0; j < n; j++) 
       {
           if(matrizBinaria[c][j] == 1)
           {
               puntosEnCluster.add(puntoArrayList.get(j));
           }
        }
       return puntosEnCluster;
    }
    
    public double getMinFromList(double[] lista)
    {
        double min = 100000000;
        for (int i = 0; i < lista.length ; i++)
        {
            if (lista[i] < min)
            {
                min = lista[i];
            }
        }
        
        return min;
    }
    
    public int getIndiceDeK(double[] lista , double valor)
    {
        for (int i = 0 ; i < lista.length ; i++)
        {
            if (valor == lista[i])
                return i;
        }
        System.out.println("Lugar:");
        return -1;
    }
    public int calcular_K_recomendado()
    {
        double[] lista_R_k = new double[4];
        int k_Recomendado = 0;
        
        KMeans km = new KMeans();
        for (int k = 2 ; k < 6 ; k++)
        {
            System.out.println(" ----- o ------");
            System.out.println("CALCULANDO K RECOMENDADO");
            
            int indiceLocal = 0;
            ArrayList<Punto> puntosParaEste_K = new ArrayList<>();
            for (int i = 0 ; i < puntosFijosPara_K_Recomendado.size() ; i++)
            {
                double x = puntosFijosPara_K_Recomendado.get(i).getX();
                double y = puntosFijosPara_K_Recomendado.get(i).getY();
                Punto nuevoPunto = new Punto(x, y, false);
                puntosParaEste_K.add(nuevoPunto);
            }

            km.generarPuntosCentros(puntosParaEste_K, k);
            
            //Esto me da los clusters y puntos
            int matrizVieja[][] = new int[k][puntosParaEste_K.size()];
            int matrizNueva[][] = calcularMatrizEuclidean(puntosParaEste_K.size() , k , puntosParaEste_K);
            
            ArrayList<Cluster> clustersEntrada = new ArrayList<>();
            ArrayList<Cluster> clustersPara_K_Recomendado =agruparKPara_K_Recomendado(puntosParaEste_K.size(), k, puntosParaEste_K, indiceLocal, matrizVieja, matrizNueva, clustersEntrada);
 
            System.out.println("Largo Clusters: " + clustersPara_K_Recomendado.size());
            ClusterHandler ch = new ClusterHandler(clustersPara_K_Recomendado);
            
            //System.out.println("Largo Clusters: " + clustersPara_K_Recomendado.size());
            
            for(int i = 0 ; i < 4 ; i++)
            {
                lista_R_k[i] = ch.calculate_R_k_For_K(k , puntosParaEste_K);
            }
   
            System.out.println(" ----- o ------");
            System.out.println("FINAL DE ITERACION CON K: " + k);
        }
        
        double menorValor = getMinFromList(lista_R_k);
        int indiceDeMenor = getIndiceDeK(lista_R_k, menorValor);
        k_Recomendado = indiceDeMenor + 2;
        return k_Recomendado;
    }
    
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCorrer = new javax.swing.JButton();
        btnValidacion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textFieldK = new javax.swing.JTextField();
        textFieldN = new javax.swing.JTextField();
        textFieldKRecomendado = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPrincipal = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnCorrer.setText("Correr");
        btnCorrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCorrerActionPerformed(evt);
            }
        });

        btnValidacion.setText("Validación");
        btnValidacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidacionActionPerformed(evt);
            }
        });

        jLabel1.setText("K");

        jLabel2.setText("N");

        jLabel3.setText("K recomendado");

        textFieldKRecomendado.setEditable(false);

        jPrincipal.setLayout(new java.awt.GridLayout(0, 3));
        jScrollPane1.setViewportView(jPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10)
                        .addComponent(textFieldK, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(textFieldN, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCorrer)
                        .addGap(423, 423, 423)
                        .addComponent(btnValidacion)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFieldKRecomendado, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel2))
                            .addComponent(btnCorrer)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(btnValidacion))
                                    .addComponent(textFieldKRecomendado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(textFieldK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(textFieldN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCorrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCorrerActionPerformed
        // TODO add your handling code here:
        int k =Integer.valueOf(textFieldK.getText());
        int n =Integer.valueOf(textFieldN.getText());
        generarKMeans(n,k);
    }//GEN-LAST:event_btnCorrerActionPerformed

    private void btnValidacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidacionActionPerformed
        // TODO add your handling code here:
        int k_Recomendado = calcular_K_recomendado();
        String recomendado = Integer.toString(k_Recomendado);
        textFieldKRecomendado.setText(recomendado);
    }//GEN-LAST:event_btnValidacionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCorrer;
    private javax.swing.JButton btnValidacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPrincipal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField textFieldK;
    private javax.swing.JTextField textFieldKRecomendado;
    private javax.swing.JTextField textFieldN;
    // End of variables declaration//GEN-END:variables
}


package programa1algebra;

import com.sun.prism.paint.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import programa1algebra.Utils.ClusterHandler;


public class VentanaPrincipal extends javax.swing.JFrame {
    private ArrayList<JPanel> jPaneles;
    private int indice;
    private Graficos grafico;
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
        crearJPanel(9);
    }

    public void generarKMeans (int n, int k)
    {
        KMeans kmeans = new KMeans();
        ArrayList<Punto> puntoArrayList=kmeans.generarPuntos(n);
        kmeans.generarPuntosCentros(puntoArrayList,k,n);
        pintar(puntoArrayList,0);
        Graphics g = jPaneles.get(0).getGraphics();
        int matrizVieja[][] = new int[k][n];
        int matrizNueva[][] = calcularMatrizEuclidean(n , k ,puntoArrayList);
//        for (int x = 0; x < matrizBinaria.length; x++) 
//        {
//            for (int y = 0; y < matrizBinaria[x].length; y++)
//            {
//                matrizBinaria[x][y]=0;
//            }
//        }
        agruparK(n,k,puntoArrayList,this.indice,matrizVieja,matrizNueva);        
    }
    
    public void pintar(ArrayList<Punto> puntoArrayList,int jPanel)
    {
        for (int x=0; x < puntoArrayList.size(); x++) 
        {
            if(puntoArrayList.get(x).esCentro==false){
                System.out.println("Centro es false");
                double coorX=(puntoArrayList.get(x).x+5)*27;
                double coorY=(puntoArrayList.get(x).y+5)*27;
                grafico.pintarPunto(jPaneles.get(jPanel).getGraphics(), coorX, coorY,0);
            }
            else{
                double coorX=(puntoArrayList.get(x).x+5)*27;
                double coorY=(puntoArrayList.get(x).y+5)*27;
                grafico.pintarPunto(jPaneles.get(jPanel).getGraphics(), coorX, coorY,jPanel);
            }
        }
    }
    public void agruparK(int n,int k,ArrayList<Punto> puntoArrayList,int indice,int[][] matrizVieja,int[][] matrizNueva)
    {
        int cont=0;
        if(sonIguales(matrizVieja,matrizNueva)==false) // if (!condicionDeParada)
        {
            if(indice<6){
                System.out.println("Entro porque si hay cambios");
                ArrayList<Punto> newPuntoCentrosArrayList=new ArrayList();
                for(int i = 0 ; i < puntoArrayList.size() ; i++){
                    if(puntoArrayList.get(i).esCentro==true){
                        System.out.println("Entro a los puntoCentros");
                        Cluster cluster = new Cluster(puntoArrayList.get(i) , agruparPuntosEnClusters(n , k , cont , puntoArrayList , matrizNueva) );
                        Graphics g = jPaneles.get(indice).getGraphics();
                        pintar(puntoArrayList,indice);
                        cluster.calcularNuevoCentro();
                        newPuntoCentrosArrayList.add(cluster.centro);
                        cont++;
                    }
                }
                int matriz[][] = calcularMatrizEuclidean(n , k , puntoArrayList);
                agruparK(n , k , puntoArrayList, this.indice++,matrizNueva,matriz);
                }
            }   
        else
        {
            System.out.println("Ya termino");
        }
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
        boolean iguales=true;
        if(matrizAnterior.length == matrizNueva.length){
            if(matrizAnterior[0].length == matrizNueva[0].length){
                for(int i = 0; i < matrizAnterior.length && iguales;i++){
                    for(int j = 0; j < matrizAnterior[0].length && iguales;j++){
                        if(matrizAnterior[i][j] != matrizNueva[i][j]){
                            iguales = false;
                            return iguales;
                        }
                    }
                }
            }
            else{
                iguales = false;
            }
        }
        else{
            iguales = false;
        }
        return iguales;
    } 
    public int[][] calcularMatrizEuclidean(int n , int k , ArrayList<Punto> puntoArrayList){
        double matriz[][] = new double[k][n];
        ClusterHandler clusterH= new ClusterHandler();
        
        for(int y = 0 ; y < puntoArrayList.size() ; y++){
            if(puntoArrayList.get(y).esCentro==true){
                for (int i = 0; i < matriz.length; i++) 
                {
                    for (int j = 0; j < matriz[i].length; j++) 
                    {
                        matriz[i][j]=clusterH.euclideanDistance(puntoArrayList.get(y),puntoArrayList.get(j));
                    }
                }
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
        
//        for (int i=0; i < matrizBinaria.length; i++) {
//            System.out.print("|");
//            for (int y=0; y < matrizBinaria[i].length; y++) {
//                System.out.print (matrizBinaria[i][y]);
//                if (y!=matrizBinaria[i].length-1) System.out.print("\t   ");
//            }
//            System.out.println("|");
//        }
        return matrizBinaria;
    }
    
    public ArrayList<Punto> agruparPuntosEnClusters(int n, int k, int c , ArrayList<Punto> puntoArrayList , int[][]matrizBinaria)
    {
       ArrayList<Punto> puntosEnCluster = new ArrayList();
       System.out.println("Tamaño de la lista de puntos"+puntoArrayList.size());
       for (int j = 0; j < n; j++) 
       {
           if(matrizBinaria[c][j] == 1)
           {
               puntosEnCluster.add(puntoArrayList.get(j));
           }
        }
       
//       for (int i=0; i < matrizBinaria.length; i++) {
//           System.out.print("|");
//           for (int y=0; y < matrizBinaria[i].length; y++) {
//               System.out.print (matrizBinaria[i][y]);
//               if (y!=matrizBinaria[i].length-1) System.out.print("\t   ");
//           }
//           System.out.println("|");
//       }
       return puntosEnCluster;
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

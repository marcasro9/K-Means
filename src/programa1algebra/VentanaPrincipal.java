/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa1algebra;

import com.sun.prism.paint.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import programa1algebra.Utils.ClusterHandler;

/**
 *
 * @author rshum
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    private ArrayList<JPanel> jPaneles;
    private int indice;
    private Graficos grafico;
    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();
        grafico=new Graficos();
        jPaneles = new ArrayList<>();
        this.indice=1;
        setLocationRelativeTo(null);
        crearJPanel(6);
    }

    public void generarKMeans (int n,int k){
        KMeans kmeans = new KMeans();
        ArrayList<Punto> puntoArrayList=kmeans.generarPuntos(n);
        ArrayList<Punto> puntoCentrosArrayList=kmeans.generarPuntosCentros(puntoArrayList,k,n);
        pintar(0,puntoArrayList,puntoCentrosArrayList);
        agruparK(n,k,puntoArrayList,puntoCentrosArrayList,this.indice);        
    }
    public void pintar(int n,ArrayList<Punto> puntoArrayList, ArrayList<Punto> puntoCentrosArrayList){
        for (int x=0; x < puntoArrayList.size(); x++) {
            double coorX=(puntoArrayList.get(x).x+5)*27;
            double coorY=(puntoArrayList.get(x).y+5)*27;
            grafico.pintarPunto(jPaneles.get(n).getGraphics(), coorX, coorY,0);
            
        }
        for (int y=0; y < puntoCentrosArrayList.size(); y++){
            double coorX=(puntoCentrosArrayList.get(y).x+5)*27;
            double coorY=(puntoCentrosArrayList.get(y).y+5)*27;
            grafico.pintarPunto(jPaneles.get(n).getGraphics(), coorX, coorY,1);
        }
    }
    public void pintarPuntos(int n,int contC,Cluster matrizBinaria,Graphics g){

            for (int x=0; x<matrizBinaria.puntos.size();x++) {
                double coorX = (matrizBinaria.puntos.get(x).x + 5) * 27;
                double coorY = (matrizBinaria.puntos.get(x).y + 5) * 27;
                grafico.pintarPunto(g, coorX, coorY,contC+2);   
            }
    }
    public void pintarCentros(ArrayList<Punto> puntoCentrosArrayList,Graphics g){
        for (int x=0; x<puntoCentrosArrayList.size();x++) {
                double coorX = (puntoCentrosArrayList.get(x).x + 5) * 27;
                double coorY = (puntoCentrosArrayList.get(x).y + 5) * 27;
                grafico.pintarPunto(g, coorX, coorY,1);   
            }
    }
    public void agruparK(int n,int k,ArrayList<Punto> puntoArrayList,ArrayList<Punto> puntoCentrosArrayList,int indice){
        if(this.indice<7){
            System.out.println("Entro");
            ArrayList<Punto> newPuntoCentrosArrayList=new ArrayList();
            int matriz[][]=calcularMatrizEuclidean(n,k,puntoArrayList,puntoCentrosArrayList);
            for(int x=0;x<puntoCentrosArrayList.size();x++){
                System.out.println("Entro a los puntoCentros");
                Cluster cluster =new Cluster(puntoCentrosArrayList.get(x),agruparPuntosEnClusters(n,k,x,puntoArrayList,matriz));
                Graphics g=jPaneles.get(indice).getGraphics();
                pintarPuntos(indice,x,cluster,g);
                pintarCentros(puntoCentrosArrayList,g);
                cluster.calcularNuevoCentro();
                newPuntoCentrosArrayList.add(cluster.centro);
            }
            agruparK(n,k,puntoArrayList,newPuntoCentrosArrayList,this.indice++);
        }
        else{
            System.out.println("Ya termino");
        }
    }
    public void crearJPanel(int cont){
        for(int x=0; x<cont;x++){
            JPanel panel = new JPanel();
            //panel.setBackground(java.awt.Color.yellow);
            Border line = BorderFactory.createLineBorder(java.awt.Color.BLACK, 2);
            panel.setBorder(line);
            jPrincipal.add(panel);
            jPaneles.add(panel);
            jPrincipal.updateUI();
        }
    }
    public int[][] calcularMatrizEuclidean(int n,int k,ArrayList<Punto> puntoArrayList,ArrayList<Punto> puntoCentrosArrayList){
        double matriz[][] = new double[k][n];
        ClusterHandler clusterH= new ClusterHandler();
        for (int x=0; x < matriz.length; x++) {
           for (int y=0; y < matriz[x].length; y++) {
               matriz[x][y]=clusterH.euclideanDistance(puntoCentrosArrayList.get(x),puntoArrayList.get(y));
           }
        }
        int matrizBinaria[][] = new int[k][n];
        for (int x=0; x < matrizBinaria.length; x++) {
            for (int y=0; y < matrizBinaria[x].length; y++) {
                matrizBinaria[x][y]=0;
            }
        }
        for (int x=0; x < n; x++) {
            double var=0;
            int pos=0;
            for (int y=0; y < k; y++) {
                if(matriz[y][x]>var){
                    var=matriz[y][x];
                    pos=y;
                }else{
                    var=matriz[y][x];
                }
            }
            matrizBinaria[pos][x]=1;
        }
//        for (int x=0; x < matrizBinaria.length; x++) {
//            System.out.print("|");
//            for (int y=0; y < matrizBinaria[x].length; y++) {
//                System.out.print (matrizBinaria[x][y]);
//                if (y!=matrizBinaria[x].length-1) System.out.print("\t   ");
//            }
//            System.out.println("|");
//        }
        return matrizBinaria;
    }
    public ArrayList<Punto> agruparPuntosEnClusters(int n,int k,int c,ArrayList<Punto> puntoArrayList,int[][]matrizBinaria){
       ArrayList<Punto> puntosEnCluster=new ArrayList();
       for (int x=0; x < n; x++) {
           if(matrizBinaria[c][x]==1){
               puntosEnCluster.add(puntoArrayList.get(x));
           }
        }
//       for (int x=0; x < matrizBinaria.length; x++) {
//           System.out.print("|");
//           for (int y=0; y < matrizBinaria[x].length; y++) {
//               System.out.print (matrizBinaria[x][y]);
//               if (y!=matrizBinaria[x].length-1) System.out.print("\t   ");
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
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addComponent(textFieldK, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(textFieldN, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnCorrer)
                .addGap(456, 456, 456)
                .addComponent(btnValidacion)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(10, 10, 10)
                .addComponent(textFieldKRecomendado, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(textFieldK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(textFieldN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCorrer)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(btnValidacion)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(textFieldKRecomendado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
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

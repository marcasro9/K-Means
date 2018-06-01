
package programa1algebra;


public class Punto {
    double x;
    double y;
    boolean esCentro;

    public Punto(double x, double y, boolean esCentro) {
        this.x = x;
        this.y = y;
        this.esCentro = esCentro;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCentro(boolean centro)
    {
        this.esCentro = centro;
    }
    
    public boolean isEsCentro() {
        return esCentro;
    }
    
}

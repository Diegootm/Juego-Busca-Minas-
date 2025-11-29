public class Casilla {
    private boolean esMina;
    private boolean descubierta;
    private int minasAdyacentes;

    public Casilla() {
        this.esMina = false;
        this.descubierta = false;
        this.minasAdyacentes = 0;
    }

    public boolean esMina() {
        return esMina;
    }

    public void setMina(boolean mina) {
        this.esMina = mina;
    }

    public void colocarMina() {
        esMina = true;
    }

    public boolean estaDescubierta() {
        return descubierta;
    }

    public void descubrir() {
        descubierta = true;
    }

    public int getMinasAdyacentes() {
        return minasAdyacentes;
    }

    public void setMinasAdyacentes(int minasAdyacentes) {
        this.minasAdyacentes = minasAdyacentes;
    }
}

import java.util.Random;

public class Tablero {
    private Casilla[][] matriz;
    private int filas;
    private int columnas;
    private int cantidadMinas;

    public Tablero(int filas, int columnas, int cantidadMinas) {
        this.filas = filas;
        this.columnas = columnas;
        this.cantidadMinas = cantidadMinas;
        matriz = new Casilla[filas][columnas];
        inicializarCasillas();
        colocarMinasAleatorias();
        calcularMinasAdyacentes(); // fija el valor en cada casilla
    }

    private void inicializarCasillas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = new Casilla();
            }
        }
    }

    private void colocarMinasAleatorias() {
        Random rand = new Random();
        int minasColocadas = 0;
        while (minasColocadas < cantidadMinas) {
            int fila = rand.nextInt(filas);
            int columna = rand.nextInt(columnas);
            if (!matriz[fila][columna].esMina()) {
                matriz[fila][columna].colocarMina();
                minasColocadas++;
            }
        }
    }

    private void calcularMinasAdyacentes() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int cuentas = contarMinasAdyacentes(i, j);
                matriz[i][j].setMinasAdyacentes(cuentas);
            }
        }
    }

    public int contarMinasAdyacentes(int fila, int columna) {
        int contador = 0;
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {
                if (i >= 0 && i < filas && j >= 0 && j < columnas) {
                    if (matriz[i][j].esMina()) contador++;
                }
            }
        }
        return contador;
    }

    public Casilla[][] getMatriz() {
        return matriz;
    }

    public Casilla getCasilla(int fila, int columna) {
        return matriz[fila][columna];
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getCantidadMinas() {
        return cantidadMinas;
    }
}

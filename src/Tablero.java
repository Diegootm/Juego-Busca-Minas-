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
        recorrerTablero((casilla, f, c) -> {
            matriz[f][c] = new Casilla();
        });
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
        recorrerTablero((casilla, f, c) -> {
            casilla.setMinasAdyacentes(contarMinasAdyacentes(f, c));
        });
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
    
    public void revelarMinas() {
        recorrerTablero((casilla, f, c) -> {
            if (casilla.esMina()) casilla.descubrir();
        });
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
    
    private void recorrerTablero(AccionCasillas accion){
        for(int i=0; i<filas; i++){
            for(int j=0; j< columnas; j++){
                accion.ejecutar(matriz[i][j], i, j);
            }
        }
    }
}

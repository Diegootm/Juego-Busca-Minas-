public class JuegoBuscaminas {
    private Tablero tablero;
    private boolean juegoTerminado;

    public JuegoBuscaminas(int filas, int columnas, int cantidadMinas) {
        tablero = new Tablero(filas, columnas, cantidadMinas);
        juegoTerminado = false;
    }

    public void descubrirCasilla(int fila, int columna) {
        if (juegoTerminado) return;
        if (fila < 0 || fila >= tablero.getFilas() || columna < 0 || columna >= tablero.getColumnas()) return;

        Casilla c = tablero.getCasilla(fila, columna);
        if (c.esMina()) {
            juegoTerminado = true;
            return;
        }

        // si ya estaba descubierta, no hacer nada
        if (c.estaDescubierta()) return;

        c.descubrir();

        // si no hay minas adyacentes, expandir recursivamente
        if (c.getMinasAdyacentes() == 0) {
            for (int i = fila - 1; i <= fila + 1; i++) {
                for (int j = columna - 1; j <= columna + 1; j++) {
                    if (i == fila && j == columna) continue;
                    if (i >= 0 && i < tablero.getFilas() && j >= 0 && j < tablero.getColumnas()) {
                        descubrirCasilla(i, j);
                    }
                }
            }
        }
    }

    public boolean estaTerminado() {
        return juegoTerminado;
    }

    public Tablero getTablero() {
        return tablero;
    }
}

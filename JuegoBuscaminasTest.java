import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JuegoBuscaminasTest
{
    private JuegoBuscaminas juego;
    private Tablero tablero;

    /**
     * Se ejecuta antes de cada test.
     */
    @BeforeEach
    public void setUp()
    {
        juego = new JuegoBuscaminas(8, 8,10);
        tablero = juego.getTablero();
    }

    /**
     * Se ejecuta después de cada test.
     */
    @AfterEach
    public void tearDown()
    {
        juego = null;
        tablero = null;
    }

    
    //Test 1: Verifica que iniciarPartida crea un tablero válido 
    @Test
    public void testIniciarPartidaCreaTablero()
    {
        assertNotNull(tablero, "El tablero debería crearse al iniciar la partida");
        assertEquals(8, tablero.getFilas(), "El tablero debe tener 8 filas");
        assertEquals(8, tablero.getColumnas(), "El tablero debe tener 8 columnas");
    }

    //Test 2: Verifica que todas las casillas del tablero existen (no nulas)
    @Test
    public void testTableroInicialNoNulo()
    {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                assertNotNull(tablero.getCasilla(i, j), 
                    "La casilla [" + i + "][" + j + "] no debería ser nula");
            }
        }
    }

    //Test 3: Verifica que no hay minas al inicio del juego
     /*@Test
    public void testCasillasSinMinasInicialmente()
    {
        boolean hayMina = false;
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (tablero.getCasilla(i, j).esMina()) {
                    hayMina = true;
                }
            }
        }
        assertFalse(hayMina, "No debería haber minas al inicio del juego");
    } */

    //Test 4: Intentar descubrir una casilla fuera del rango no debe causar error
    @Test
    public void testDescubrirCasillaFueraDeRangoNoRompe()
    {
        try {
            juego.descubrirCasilla(10, 10); // fuera del tablero
        } catch (Exception e) {
            fail("Descubrir una casilla fuera de rango no debería lanzar excepción");
        }
    }
    
    // Test 5: Verificar que se colocan exactamente la cantidad de minas esperadas
    
    @Test
    public void testCantidadDeMinasCorrecta() {
        int contadorMinas = 0;
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (tablero.getCasilla(i, j).esMina()) {
                    contadorMinas++;
                }
            }
        }
        assertEquals(10, contadorMinas, "Debe haber exactamente 10 minas en el tablero");
    }

    //🧱 Test 6: Verificar que las casillas sin mina estén sin descubrir al inicio
    @Test
    public void testCasillasInicialmenteOcultas() {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                assertFalse(tablero.getCasilla(i, j).estaDescubierta(),
                    "Las casillas deben estar ocultas al inicio del juego");
            }
        }
    }
    
    //Test 7: Verificar cálculo correcto de minas adyacentes.
    @Test
    public void testContarMinasAdyacentes() {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                tablero.getCasilla(i, j).setMina(false);
            }
        }
        tablero.getCasilla(1, 1).colocarMina();
        tablero.getCasilla(1, 2).colocarMina();
        int minasCerca = tablero.contarMinasAdyacentes(2, 2);
        assertEquals(2, minasCerca, "Debe haber exactamente 2 minas adyacentes");
    }

    //Test 8: Verificar creación del tablero según nivel Principiante.
    @Test
    public void testNivelPrincipiante() {
        JuegoBuscaminas juegoP = new JuegoBuscaminas(8, 8, 10);
        Tablero tableroP = juegoP.getTablero();
        assertEquals(8, tableroP.getFilas(), "Debe tener 8 filas en modo Principiante");
        assertEquals(8, tableroP.getColumnas(), "Debe tener 8 columnas en modo Principiante");
        assertEquals(10, tableroP.getCantidadMinas(), "Debe tener 10 minas en modo Principiante");
    }

    //Test 9: Verificar que las posiciones de minas cambien entre partidas.
    @Test
    public void testMinasAleatoriasEnCadaPartida() {
        JuegoBuscaminas juego1 = new JuegoBuscaminas(8, 8, 10);
        JuegoBuscaminas juego2 = new JuegoBuscaminas(8, 8, 10);
        Tablero t1 = juego1.getTablero();
        Tablero t2 = juego2.getTablero();

        boolean diferentes = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (t1.getCasilla(i, j).esMina() != t2.getCasilla(i, j).esMina()) {
                    diferentes = true;
                    break;
                }
            }
        }
        assertTrue(diferentes, "Las posiciones de las minas deben variar entre partidas");
    }
}

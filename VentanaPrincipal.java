import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panelMenu;
    private JPanel panelTablero;
    private JButton btnIniciar;
    private JButton[][] botones;
    private JuegoBuscaminas juego;

    public VentanaPrincipal() {
        setTitle("Buscaminas");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        crearMenu();
        add(panelMenu, "menu");

        mostrarPanel("menu");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearMenu() {
        panelMenu = new JPanel(new GridBagLayout());
        panelMenu.setBackground(Color.DARK_GRAY);

        btnIniciar = new JButton("INICIAR PARTIDA");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 24));
        btnIniciar.setBackground(new Color(0, 180, 0));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setPreferredSize(new Dimension(300, 90));

        btnIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarPartida();
                mostrarPanel("tablero");
            }
        });

        panelMenu.add(btnIniciar);
    }

    private void iniciarPartida() {
        String[] niveles = {"Principiante", "Intermedio", "Experto", "Personalizado"};

        String seleccion = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona un nivel de dificultad:",
                "Dificultad",
                JOptionPane.QUESTION_MESSAGE,
                null,
                niveles,
                niveles[0]
        );

        int filas = 8, columnas = 8, minas = 10;

        if ("Intermedio".equals(seleccion)) {
            filas = columnas = 16;
            minas = 40;
        } else if ("Experto".equals(seleccion)) {
            filas = columnas = 24;
            minas = 99;
        } else if ("Personalizado".equals(seleccion)) {
            try {
                int f = Integer.parseInt(JOptionPane.showInputDialog("Filas:"));
                int c = Integer.parseInt(JOptionPane.showInputDialog("Columnas:"));
                int m = Integer.parseInt(JOptionPane.showInputDialog("Minas:"));

                if (f > 0 && c > 0 && m > 0 && m < f * c) {
                    filas = f;
                    columnas = c;
                    minas = m;
                } else {
                    JOptionPane.showMessageDialog(this, "Valores inválidos. Usando principiante.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Valores inválidos. Usando principiante.");
            }
        }

        juego = new JuegoBuscaminas(filas, columnas, minas);
        crearPanelTableroDinamico(filas, columnas);
    }

    private void crearPanelTableroDinamico(int filas, int columnas) {
        panelTablero = new JPanel(new BorderLayout());
        JPanel contenedor = new JPanel(new GridLayout(filas, columnas));
        contenedor.setBackground(Color.GRAY);

        botones = new JButton[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                botones[i][j] = new JButton(" ");
                botones[i][j].setFont(new Font("Arial", Font.BOLD, 14));
                botones[i][j].setBackground(new Color(100, 100, 100));
                botones[i][j].setForeground(Color.WHITE);
                botones[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                botones[i][j].setFocusPainted(false);

                final int fila = i;
                final int columna = j;

                botones[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        manejarClick(fila, columna);
                    }
                });

                contenedor.add(botones[i][j]);
            }
        }

        panelTablero.add(contenedor, BorderLayout.CENTER);
        add(panelTablero, "tablero");
        revalidate();
        repaint();
    }

    private void manejarClick(int fila, int columna) {
        if (juego == null) return;

        juego.descubrirCasilla(fila, columna);

        Casilla c = juego.getTablero().getCasilla(fila, columna);
        if (c.esMina()) {
            botones[fila][columna].setText("💣");
            botones[fila][columna].setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "Has perdido");
            revelarMinas();
            deshabilitarTablero();
            mostrarPanel("menu");
            return;
        }

        actualizarTablero();
    }

    private void actualizarTablero() {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {

                Casilla c = juego.getTablero().getCasilla(i, j);

                if (!c.estaDescubierta()) continue;

                botones[i][j].setEnabled(false);
                botones[i][j].setBackground(new Color(60, 60, 60));

                if (c.esMina()) {
                    botones[i][j].setText("💣");
                } else {
                    int minas = c.getMinasAdyacentes();
                    if (minas > 0)
                        botones[i][j].setText(String.valueOf(minas));
                    else
                        botones[i][j].setText(" ");
                }
            }
        }
    }

    private void revelarMinas() {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                Casilla c = juego.getTablero().getCasilla(i, j);
                if (c.esMina()) {
                    botones[i][j].setText("💣");
                    botones[i][j].setBackground(Color.RED);
                }
            }
        }
    }

    private void deshabilitarTablero() {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                botones[i][j].setEnabled(false);
            }
        }
    }

    private void mostrarPanel(String nombre) {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), nombre);
    }
}

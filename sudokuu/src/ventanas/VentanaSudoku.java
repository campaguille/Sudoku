package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.LineBorder;
import logica.Sudoku;

public class VentanaSudoku extends javax.swing.JFrame {

    // Panel principal que contendrá los 3x3 cuadrantes
    Sudoku sudoku = new Sudoku();

    // Mapeo de 81 posiciones, indexadas por pos = fila*9 + col
    private final JTextField[] casillasPorPos = new JTextField[81];

    // Índices lineales (0-80) de las casillas que el usuario debe rellenar
    ArrayList<Integer> casillasDesactivadas;

    // Matriz con el sudoku resuelto
    int[][] sudokuResuelto = {
        {5, 3, 4, 6, 7, 8, 9, 1, 2},
        {6, 7, 2, 1, 9, 5, 3, 4, 8},
        {1, 9, 8, 3, 4, 2, 5, 6, 7},
        {8, 5, 9, 7, 6, 1, 4, 2, 3},
        {4, 2, 6, 8, 5, 3, 7, 9, 1},
        {7, 1, 3, 9, 2, 4, 8, 5, 6},
        {9, 6, 1, 5, 3, 7, 2, 8, 4},
        {2, 8, 7, 4, 1, 9, 6, 3, 5},
        {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

    public VentanaSudoku() {
        initComponents();
        setResizable(true);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setSize(600, 600);

        sudoku.setBounds(600, 150, 600, 600);
        sudoku.setLayout(new GridLayout(3, 3)); // 3x3 cuadrantes
        add(sudoku, BorderLayout.CENTER);

        ArrayList<Integer> listaHuecos = desactivacionCasillas();
        crearCuadrantes(listaHuecos);
    }

    // Creación de los 9 cuadrantes de 3x3
    private void crearCuadrantes(ArrayList<Integer> desactivadas) {
        for (int cuad = 0; cuad < 9; cuad++) {
            JPanel cuadrante = new JPanel(new GridLayout(3, 3));
            cuadrante.setBorder(new LineBorder(Color.BLACK, 3)); // borde grueso entre cuadrantes

            int startRow = (cuad / 3) * 3;
            int startCol = (cuad % 3) * 3;

            for (int fila = startRow; fila < startRow + 3; fila++) {
                for (int col = startCol; col < startCol + 3; col++) {
                    int pos = fila * 9 + col;

                    JTextField casilla = new JTextField(String.valueOf(sudokuResuelto[fila][col]));
                    casilla.setBorder(new LineBorder(Color.BLACK, 1)); // Borde fino entre casillas
                    casilla.setHorizontalAlignment(JTextField.CENTER); // Centrado del texto de los TextField
                    casilla.setEditable(false);
                    casilla.setBackground(Color.WHITE);

                    // Guardar exactamente en su índice lineal (1-81) para poder acceder a ellas en funcion de su conteo por lineas
                    casillasPorPos[pos] = casilla;

                    // Si está desactivada, convertirla en editable y vacía para que el jugador pueda escribir en ella
                    if (desactivadas.contains(pos)) {
                        casilla.setText("");
                        casilla.setEditable(true);
                        casilla.setBackground(Color.lightGray);

                        // Comprobación automática con DocumentListener cada vez que el jugador escribe en una de las casillas disponibles se invoca al método corregir que finaliza e sudoku en el caso de que todas las casillas completas coincidan con las mismas casillas del resultado
                        casilla.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                            @Override
                            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                                corregir();
                            }

                            @Override
                            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                                corregir();
                            }

                            @Override
                            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                                corregir();
                            }
                        });
                    }

                    cuadrante.add(casilla); //Se añaden las casillas, activadas o desactivadas
                }
            }
            sudoku.add(cuadrante); //Se añaden los nueve cuadrantes de uno en uno al Panel sudoku principal
        }
    }

    // Selección aleatoria de casillas a desactivar asegurando consistencia
    private ArrayList<Integer> desactivacionCasillas() {
        casillasDesactivadas = new ArrayList<>();

        Set<Integer> set = new HashSet<>(); //Se utiliza un set ya que no admite duplicados y se añaden hasta que se llega al numero total de casillas
        while (set.size() < Principal.cantidadCasillasADesactivar) {
            int casillaRandom = (int) (Math.random() * 81);
            set.add(casillaRandom);
        }
        casillasDesactivadas.addAll(set);
        return casillasDesactivadas;
    }

    // Verifica si todas las desactivadas están rellenas y correctas
    private void corregir() {
        for (int i : casillasDesactivadas) {
            JTextField tf = casillasPorPos[i]; //Se extrae el valor de cada casilla desactivada recorriendolas por su posicion lineal
            String texto = tf.getText().trim(); //Se pasa el valor de la casilla a String conteniendose en la variable texto
            if (texto.isEmpty()) {
                return; // En el caso de que la casilla esté vacía (el usuario borra el contenido) se sale del método
            }
            // 
            int valorUsuario;
            try {
                valorUsuario = Integer.parseInt(texto);
            } catch (NumberFormatException ex) {
                return;
            }
            int fila = i / 9;
            int col = i % 9;
            if (valorUsuario != sudokuResuelto[fila][col]) { //Se recorren las posiciones de las casillas desactivadas de la matriz sudokuResuelto y se comparan con los valores introducidos por el extraidos del tf
                return; // En caso de que sean diferentes se sale del método
            }
        }

        //En el caso de que no se haya salido del método (todas las casillas sean correctas y ninguna este vacía), se crea un dialogo que indica al usuario que ha ganado y se cierra la ventana de juego devolviendo al usuario al menú principal 
        JOptionPane.showMessageDialog(this, "¡Sudoku resuelto correctamente!");
        dispose();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));
        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new VentanaSudoku().setVisible(true));
    }
}

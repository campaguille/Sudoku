package ventanas;

import botones.BotonInicio;
import botones.BotonXCierre;
import componentes.PanelFondo;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class Principal extends javax.swing.JFrame implements ActionListener {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Principal.class.getName());

    //Declaracion de variables
    BotonInicio botonInicio = new BotonInicio();
    BotonXCierre botonXCierre = new BotonXCierre();
    PanelFondo panelFondo = new PanelFondo();
    JComboBox selectorDificultad = new JComboBox();
    Color colorBase = new Color(222, 155, 83);
    public static int cantidadCasillasADesactivar;

    enum dificultad {Facil, Medio, Dificil};

    public Principal() {
        this.setUndecorated(true);
        initComponents();
        this.setResizable(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        panelFondo.setSize(this.getWidth(), this.getHeight());
        this.setContentPane(panelFondo);

        // Configuración Boton Cierre
        botonXCierre.setBounds(0, 0, 30, 30);
        this.add(botonXCierre);
        botonXCierre.addActionListener(this);

        // Configuración Selector
        this.add(selectorDificultad);
        selectorDificultad.setBounds(600, 800, 300, 60);
        selectorDificultad.setVisible(true);
        selectorDificultad.setBackground(colorBase);
        selectorDificultad.addItem(dificultad.Facil);
        selectorDificultad.addItem(dificultad.Medio);
        selectorDificultad.addItem(dificultad.Dificil);

        // Configuración Boton Inicio
        botonInicio.setBounds(1000, 775, 200, 100);
        botonInicio.setFocusable(false);
        this.add(botonInicio);
        botonInicio.addActionListener(this);
    }
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));
        pack();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new Principal().setVisible(true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //En caso de que se presione el boton de cierre, se finaliza la ejecución del programa
        if (e.getSource() == botonXCierre) {
            System.exit(0);
        }

        //En caso de que se presione el boton de inicio se crea la ventana del sudoku con la dificultad seleccionada
        if (e.getSource() == botonInicio) {
            // Establecer dificultad
            Object selected = selectorDificultad.getSelectedItem();

            if (selected == dificultad.Facil) {
                cantidadCasillasADesactivar = 30;
            } else if (selected == dificultad.Medio) {
                cantidadCasillasADesactivar = 40;
            } else if (selected == dificultad.Dificil) {
                cantidadCasillasADesactivar = 50;
            }
            
            VentanaSudoku ventanaSudoku = new VentanaSudoku();
            ventanaSudoku.setVisible(true);
        }
    }
}

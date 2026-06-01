# 🔢 Sudoku — Java Swing Desktop Game

Aplicación de escritorio desarrollada en **Java con Swing** que permite jugar al Sudoku con selección de dificultad. El tablero se genera con huecos aleatorios y el juego corrige automáticamente la solución en tiempo real.

---

## 📁 Estructura del proyecto

```
DDI_SUDOKU/
└── src/
    ├── botones/
    │   ├── BotonInicio.java          # Botón personalizado para iniciar partida
    │   └── BotonXCierre.java         # Botón personalizado para cerrar la app
    ├── componentes/
    │   ├── PanelFondo.java/.form     # Panel con imagen de fondo para la ventana principal
    │   └── PanelFondoVentanaSudoku.java/.form  # Panel de fondo para la ventana de juego
    ├── imagenes/
    │   ├── imagenFondoPrincipal.png
    │   ├── imagenFondoVentanaSudoku.png
    │   ├── imagenBotonInicio.png
    │   ├── imagenBotonResolver.png
    │   └── imagenBotonXCierre.png
    ├── logica/
    │   └── Sudoku.java               # Panel base que contiene el tablero de juego
    └── ventanas/
        ├── Principal.java            # Ventana principal — menú de inicio y selector de dificultad
        ├── VentanaSudoku.java        # Ventana de juego — tablero 9x9 con lógica completa
        └── Casillas.java             # Extensión de JTextField para las casillas del tablero
```

---

## 🎮 Cómo funciona

### Ventana Principal (`Principal.java`)
Al ejecutar la aplicación se abre la ventana principal en modo pantalla completa (`MAXIMIZED_BOTH`) con look & feel **Nimbus**. Desde aquí el jugador:

1. Selecciona la **dificultad** en un `JComboBox`:
   - **Fácil** → 30 casillas vacías
   - **Medio** → 40 casillas vacías
   - **Difícil** → 50 casillas vacías
2. Pulsa el botón **Inicio** para abrir el tablero de juego.
3. Puede cerrar la aplicación con el botón **X** personalizado (esquina superior izquierda).

### Ventana de Juego (`VentanaSudoku.java`)
El tablero es un Sudoku **9×9** dividido en **9 cuadrantes de 3×3**, representado mediante `JTextField` individuales:

- Las casillas **visibles** (pistas) aparecen en blanco y no son editables.
- Las casillas **vacías** (huecos) aparecen en gris claro y son editables.
- Los huecos se seleccionan aleatoriamente usando un `HashSet` para garantizar posiciones únicas.
- Cada casilla vacía tiene un `DocumentListener` que lanza la comprobación automática con cada pulsación de tecla.

### Lógica de corrección automática
El método `corregir()` se invoca con cada cambio en cualquier casilla editable. Recorre todas las posiciones desactivadas y compara el valor introducido por el jugador con la matriz `sudokuResuelto[][]`. Si todas las casillas están rellenas y son correctas, muestra un `JOptionPane` de victoria y cierra la ventana de juego.

---

## 🧱 Clases principales

| Clase | Descripción |
|---|---|
| `Principal` | Menú principal, selector de dificultad, punto de entrada (`main`) |
| `VentanaSudoku` | Tablero de juego, creación de cuadrantes, validación en tiempo real |
| `Sudoku` | `JPanel` base que actúa como contenedor del tablero |
| `Casillas` | `JTextField` extendido para las casillas del tablero |
| `PanelFondo` | `JPanel` con imagen de fondo para la ventana principal |
| `PanelFondoVentanaSudoku` | `JPanel` con imagen de fondo para la ventana de juego |
| `BotonInicio` | Botón con imagen personalizada para iniciar partida |
| `BotonXCierre` | Botón con imagen personalizada para cerrar la aplicación |

---

## ▶️ Requisitos y ejecución

- **Java JDK 8** o superior
- IDE recomendado: **NetBeans** (los archivos `.form` son formularios de NetBeans GUI Builder)

### Compilar y ejecutar desde terminal

```bash
# Desde la raíz del proyecto
javac -d out -sourcepath src src/ventanas/Principal.java
java -cp out ventanas.Principal
```

### Ejecutar desde NetBeans
1. Abrir el proyecto en NetBeans.
2. Establecer `ventanas.Principal` como clase principal.
3. Ejecutar con `F6`.

---

## 📝 Notas técnicas

- La solución del Sudoku está **hardcodeada** en `VentanaSudoku.java` como matriz `sudokuResuelto[][]`. El juego siempre usa la misma solución base; los huecos son los que varían aleatoriamente en cada partida.
- Los botones de acción (`BotonInicio`, `BotonXCierre`) usan imágenes PNG cargadas desde `src/imagenes/`.
- La fuente personalizada de la UI es la tipografía estándar de Swing con look & feel Nimbus.
- La ventana principal es **no decorada** (`setUndecorated(true)`), por lo que el cierre se gestiona exclusivamente con `BotonXCierre`.

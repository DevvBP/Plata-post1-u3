package com.universidad.tienda.composite;

/**
 * Interfaz Componente del patrón Composite.
 * Tanto los productos hoja como las categorías compuestas implementan esta interfaz,
 * lo que permite tratarlos de forma uniforme.
 */
public interface ItemCatalogo {

    /**
     * @return El nombre del ítem (producto o categoría)
     */
    String getNombre();

    /**
     * @return El precio total del ítem.
     *         Para un Producto, es su precio base.
     *         Para una Categoria, es la suma recursiva de todos sus hijos.
     */
    double getPrecioTotal();

    /**
     * Muestra el ítem en la consola con indentación según su nivel en la jerarquía.
     *
     * @param nivel Nivel de profundidad (0 = raíz, 1 = primer nivel, etc.)
     */
    void mostrar(int nivel);
}

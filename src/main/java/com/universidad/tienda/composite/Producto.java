package com.universidad.tienda.composite;

/**
 * Clase Hoja (Leaf) del patrón Composite.
 * Representa un producto individual que no puede tener hijos.
 * Es el caso base de la recursión.
 */
public class Producto implements ItemCatalogo {

    private final String nombre;
    private final double precioBase;

    public Producto(String nombre, double precioBase) {
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getPrecioTotal() {
        // Un producto hoja retorna simplemente su precio base
        return precioBase;
    }

    @Override
    public void mostrar(int nivel) {
        // Genera la indentación basada en el nivel jerárquico
        String indentacion = "  ".repeat(nivel);
        System.out.printf("%s[Producto] %s - $%.2f%n", indentacion, nombre, precioBase);
    }
}

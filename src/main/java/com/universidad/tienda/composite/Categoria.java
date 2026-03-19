package com.universidad.tienda.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Compuesta (Composite) del patrón Composite.
 * Representa una categoría que puede contener tanto Productos (hojas)
 * como otras Categorias (compuestos), formando estructuras en árbol.
 */
public class Categoria implements ItemCatalogo {

    private final String nombre;
    private final List<ItemCatalogo> hijos;

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.hijos = new ArrayList<>();
    }

    /**
     * Agrega un ítem (producto o sub-categoría) a esta categoría.
     *
     * @param item El ítem a agregar
     */
    public void agregar(ItemCatalogo item) {
        hijos.add(item);
    }

    /**
     * Remueve un ítem de esta categoría.
     *
     * @param item El ítem a remover
     */
    public void remover(ItemCatalogo item) {
        hijos.remove(item);
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * Suma los precios de todos los hijos de forma recursiva.
     * Este es el comportamiento central del patrón Composite.
     */
    @Override
    public double getPrecioTotal() {
        return hijos.stream()
                .mapToDouble(ItemCatalogo::getPrecioTotal)
                .sum();
    }

    @Override
    public void mostrar(int nivel) {
        String indentacion = "  ".repeat(nivel);
        System.out.printf("%s[Categoria] %s (Total: $%.2f)%n",
                indentacion, nombre, getPrecioTotal());
        // Recursión: cada hijo se muestra con un nivel de indentación adicional
        for (ItemCatalogo hijo : hijos) {
            hijo.mostrar(nivel + 1);
        }
    }

    public List<ItemCatalogo> getHijos() {
        return hijos;
    }
}

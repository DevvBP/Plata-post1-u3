package com.universidad.tienda.servicio;

import com.universidad.tienda.adapter.PasarelaPago;
import com.universidad.tienda.composite.Categoria;
import com.universidad.tienda.composite.ItemCatalogo;
import com.universidad.tienda.composite.Producto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Servicio principal de la tienda.
 *
 * Demuestra dos patrones estructurales:
 * 1. Adapter: usa PasarelaPago a través de @Qualifier para seleccionar Stripe
 * 2. Composite: construye un árbol de categorías y productos
 *
 * La Inyección de Dependencias con @Qualifier resuelve la ambigüedad cuando
 * existen múltiples beans que implementan la misma interfaz (PayPal vs Stripe).
 */
@Service
public class TiendaServicio {

    private final PasarelaPago pasarelaPago;

    /**
     * Constructor con @Qualifier para seleccionar explícitamente el adaptador de Stripe.
     * Sin @Qualifier Spring lanzaría NoUniqueBeanDefinitionException.
     */
    public TiendaServicio(@Qualifier("stripe") PasarelaPago pasarelaPago) {
        this.pasarelaPago = pasarelaPago;
    }

    /**
     * Construye un catálogo de productos en forma de árbol usando el patrón Composite.
     *
     * Estructura resultante:
     * Catalogo Tienda (Raiz)
     * ├── Electronica
     * │   └── Computadores
     * │       ├── Laptop Pro     $1,299.99
     * │       └── MacBook Air    $1,099.99
     * └── Libros
     *     ├── Clean Code         $   49.99
     *     └── Design Patterns    $   59.99
     *
     * @return El ítem raíz del catálogo completo
     */
    public ItemCatalogo construirCatalogo() {
        // --- Hojas (Productos) ---
        Producto laptop    = new Producto("Laptop Pro",       1299.99);
        Producto macbook   = new Producto("MacBook Air",      1099.99);
        Producto cleanCode = new Producto("Clean Code",          49.99);
        Producto designPat = new Producto("Design Patterns",    59.99);

        // --- Compuestos (Categorías) ---
        Categoria computadores = new Categoria("Computadores");
        computadores.agregar(laptop);
        computadores.agregar(macbook);

        Categoria electronica = new Categoria("Electronica");
        electronica.agregar(computadores);

        Categoria libros = new Categoria("Libros");
        libros.agregar(cleanCode);
        libros.agregar(designPat);

        // --- Raíz del árbol ---
        Categoria raiz = new Categoria("Catalogo Tienda");
        raiz.agregar(electronica);
        raiz.agregar(libros);

        return raiz;
    }

    /**
     * Realiza una compra de prueba usando la pasarela de pago configurada.
     *
     * @param token  Token del método de pago
     * @param monto  Monto a cobrar
     * @param moneda Código de moneda
     * @return true si la compra fue exitosa
     */
    public boolean realizarCompra(String token, double monto, String moneda) {
        System.out.printf("%n=== Procesando compra con %s ===%n", pasarelaPago.obtenerNombre());
        boolean resultado = pasarelaPago.procesarPago(token, monto, moneda);
        System.out.printf("=== Compra %s ===%n%n", resultado ? "COMPLETADA" : "FALLIDA");
        return resultado;
    }

    public PasarelaPago getPasarelaPago() {
        return pasarelaPago;
    }
}

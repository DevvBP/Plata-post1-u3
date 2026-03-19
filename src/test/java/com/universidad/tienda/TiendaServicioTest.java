package com.universidad.tienda;

import com.universidad.tienda.adapter.PayPalAdapter;
import com.universidad.tienda.adapter.StripeAdapter;
import com.universidad.tienda.composite.Categoria;
import com.universidad.tienda.composite.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas JUnit 5 para los patrones Adapter y Composite.
 * No carga el contexto de Spring para mayor velocidad de ejecución.
 */
@DisplayName("Tests: Patrones Adapter y Composite")
class TiendaServicioTest {

    private PayPalAdapter payPalAdapter;
    private StripeAdapter stripeAdapter;

    @BeforeEach
    void setUp() {
        payPalAdapter = new PayPalAdapter();
        stripeAdapter = new StripeAdapter();
    }

    // =========================================================
    // Tests del Patrón ADAPTER
    // =========================================================

    @Test
    @DisplayName("PayPal Adapter: procesa un pago válido exitosamente")
    void testAdapterPayPalProcesaPago() {
        // Arrange
        String token = "paypal-token-test-001";
        double monto = 99.99;
        String moneda = "USD";

        // Act
        boolean resultado = payPalAdapter.procesarPago(token, monto, moneda);

        // Assert: PayPal siempre retorna un ID con prefijo "PP_", por eso debe ser true
        assertTrue(resultado, "PayPal debería aprobar el pago con un ID de transacción válido (PP_...)");
    }

    @Test
    @DisplayName("Stripe Adapter: rechaza un pago con token vacío")
    void testAdapterStripeRechazaTokenVacio() {
        // Arrange: token vacío simula un método de pago inválido
        String tokenVacio = "";
        double monto = 49.99;
        String moneda = "USD";

        // Act
        boolean resultado = stripeAdapter.procesarPago(tokenVacio, monto, moneda);

        // Assert: Stripe debe rechazar cuando el token está vacío
        assertFalse(resultado, "Stripe debería rechazar el pago cuando el token está vacío");
    }

    // =========================================================
    // Tests del Patrón COMPOSITE
    // =========================================================

    @Test
    @DisplayName("Composite plano: calcula el precio total de productos directos")
    void testCompositeCalculaPrecioTotal() {
        // Arrange: categoría con dos productos directos
        Categoria categoria = new Categoria("Accesorios");
        categoria.agregar(new Producto("Mouse",    25.00));
        categoria.agregar(new Producto("Teclado",  75.00));

        // Act
        double total = categoria.getPrecioTotal();

        // Assert: 25.00 + 75.00 = 100.00
        assertEquals(100.00, total, 0.01,
                "La categoría plana debe sumar correctamente los precios de sus productos directos");
    }

    @Test
    @DisplayName("Composite anidado: suma recursiva de sub-categorías")
    void testCompositeAnidado() {
        // Arrange: construir árbol con dos ramas
        // Rama 1: Electronica -> Computadores (1500 + 800 = 2300)
        Categoria computadores = new Categoria("Computadores");
        computadores.agregar(new Producto("Laptop",  1500.00));
        computadores.agregar(new Producto("Tablet",   800.00));

        Categoria electronica = new Categoria("Electronica");
        electronica.agregar(computadores);

        // Rama 2: Libros (30 + 45 = 75)
        Categoria libros = new Categoria("Libros");
        libros.agregar(new Producto("Java Avanzado",    30.00));
        libros.agregar(new Producto("Spring en Accion", 45.00));

        // Árbol raíz (2300 + 75 = 2375)
        Categoria raiz = new Categoria("Tienda Completa");
        raiz.agregar(electronica);
        raiz.agregar(libros);

        // Act
        double totalRaiz = raiz.getPrecioTotal();

        // Assert: debe sumar recursivamente todas las ramas
        assertEquals(2375.00, totalRaiz, 0.01,
                "El precio total raíz debe ser la suma recursiva de todas las sub-categorías y productos");
    }
}

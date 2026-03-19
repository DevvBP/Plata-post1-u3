package com.universidad.tienda;

import com.universidad.tienda.composite.ItemCatalogo;
import com.universidad.tienda.servicio.TiendaServicio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación Spring Boot.
 *
 * Implementa CommandLineRunner para ejecutar la demostración
 * automáticamente al iniciar la aplicación.
 */
@SpringBootApplication
public class TiendaApp implements CommandLineRunner {

    private final TiendaServicio tiendaServicio;

    public TiendaApp(TiendaServicio tiendaServicio) {
        this.tiendaServicio = tiendaServicio;
    }

    public static void main(String[] args) {
        SpringApplication.run(TiendaApp.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║   Laboratorio: Patrones Adapter y Composite      ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // === DEMO PATRÓN COMPOSITE ===
        System.out.println("\n--- [Composite] Árbol de Catálogo ---");
        ItemCatalogo catalogo = tiendaServicio.construirCatalogo();
        catalogo.mostrar(0);
        System.out.printf("%n  Precio total del catálogo: $%.2f%n", catalogo.getPrecioTotal());

        // === DEMO PATRÓN ADAPTER ===
        System.out.println("\n--- [Adapter] Procesamiento de Pago ---");
        boolean exito = tiendaServicio.realizarCompra("tok_visa_test_4242", 1299.99, "USD");

        // === RESUMEN ===
        System.out.println("\n--- Resumen ---");
        System.out.printf("  Pasarela activa: %s%n", tiendaServicio.getPasarelaPago().obtenerNombre());
        System.out.printf("  Estado del pago: %s%n", exito ? "✓ Aprobado" : "✗ Rechazado");
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║   Demostración completada exitosamente           ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}

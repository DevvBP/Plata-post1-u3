package com.universidad.tienda.adapter;

import org.springframework.stereotype.Component;

/**
 * Adaptador de Stripe.
 * Implementa la interfaz PasarelaPago y adapta la API legacy de Stripe.
 *
 * Diferencia clave: Stripe trabaja con centavos (long),
 * por lo que este adaptador convierte el monto decimal multiplicándolo por 100.
 */
@Component("stripe")
public class StripeAdapter implements PasarelaPago {

    private final StripeAPI stripeAPI;

    public StripeAdapter() {
        this.stripeAPI = new StripeAPI();
    }

    @Override
    public boolean procesarPago(String token, double monto, String moneda) {
        System.out.printf("[StripeAdapter] Adaptando pago de %.2f %s%n", monto, moneda);
        // Adaptación crucial: convierte de unidades a centavos (ej. 15.99 -> 1599)
        long montoCentavos = Math.round(monto * 100);
        // Adapta los nombres de parámetros y tipos al contrato de StripeAPI
        boolean resultado = stripeAPI.charge(token, montoCentavos, moneda.toLowerCase());
        System.out.printf("[StripeAdapter] Resultado: %s%n", resultado ? "APROBADO" : "RECHAZADO");
        return resultado;
    }

    @Override
    public String obtenerNombre() {
        return "Stripe";
    }
}

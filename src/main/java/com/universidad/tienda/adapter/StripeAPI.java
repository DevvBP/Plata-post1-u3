package com.universidad.tienda.adapter;

/**
 * API legacy de Stripe que no puede ser modificada.
 * Esta es la clase "Adaptee" en el patrón Adapter.
 */
public class StripeAPI {

    /**
     * Carga un pago a través de la API de Stripe.
     * Stripe trabaja con centavos (long) en lugar de decimales.
     *
     * @param token        Token de la tarjeta del cliente (generado por Stripe.js)
     * @param amountCents  Monto en centavos (ej: $10.00 USD = 1000 centavos)
     * @param currencyCode Código de moneda en minúsculas (ej: "usd")
     * @return true si el cargo fue aprobado, false si fue rechazado
     */
    public boolean charge(String token, long amountCents, String currencyCode) {
        System.out.printf("[StripeAPI] Procesando cargo: %d centavos de %s con token '%s'%n",
                amountCents, currencyCode, token);

        // Simula lógica de aprobación: el token no debe estar vacío
        if (token == null || token.isBlank()) {
            System.out.println("[StripeAPI] Error: token inválido o vacío.");
            return false;
        }

        System.out.println("[StripeAPI] Cargo aprobado exitosamente.");
        return true;
    }
}

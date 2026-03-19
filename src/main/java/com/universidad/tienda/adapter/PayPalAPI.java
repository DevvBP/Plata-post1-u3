package com.universidad.tienda.adapter;

/**
 * API legacy de PayPal que no puede ser modificada.
 * Esta es la clase "Adaptee" en el patrón Adapter.
 */
public class PayPalAPI {

    /**
     * Ejecuta un pago a través de la API de PayPal.
     * Retorna un código de transacción con prefijo "PP_" seguido de un timestamp.
     *
     * @param amount   Monto a cobrar
     * @param currency Código de moneda ISO
     * @return String con el ID de transacción de PayPal (formato: "PP_<timestamp>")
     */
    public String executePayment(double amount, String currency) {
        // Simula la comunicación con la API real de PayPal
        long timestamp = System.currentTimeMillis();
        System.out.printf("[PayPalAPI] Ejecutando pago: %.2f %s%n", amount, currency);
        return "PP_" + timestamp;
    }
}

package com.universidad.tienda.adapter;

/**
 * Interfaz objetivo del patrón Adapter.
 * Define el contrato que todos los adaptadores de pasarelas de pago deben cumplir.
 */
public interface PasarelaPago {

    /**
     * Procesa un pago con los datos proporcionados.
     *
     * @param token      Token o identificador del método de pago del cliente
     * @param monto      Monto a cobrar en la moneda especificada
     * @param moneda     Código ISO de la moneda (ej: "USD", "EUR")
     * @return true si el pago fue procesado exitosamente, false en caso contrario
     */
    boolean procesarPago(String token, double monto, String moneda);

    /**
     * @return El nombre descriptivo de la pasarela de pago
     */
    String obtenerNombre();
}

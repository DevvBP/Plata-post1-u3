package com.universidad.tienda.adapter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Adaptador de PayPal.
 * Implementa la interfaz PasarelaPago y adapta la API legacy de PayPal.
 *
 * Patrón Adapter: convierte la interfaz de PayPalAPI (executePayment)
 * a la interfaz PasarelaPago (procesarPago) que espera el sistema.
 */
@Component("paypal")
public class PayPalAdapter implements PasarelaPago {

    private final PayPalAPI payPalAPI;

    public PayPalAdapter() {
        this.payPalAPI = new PayPalAPI();
    }

    @Override
    public boolean procesarPago(String token, double monto, String moneda) {
        System.out.printf("[PayPalAdapter] Adaptando pago de %.2f %s%n", monto, moneda);
        // Adapta la llamada: procesarPago -> executePayment
        String transactionId = payPalAPI.executePayment(monto, moneda);
        // La transacción es exitosa si el ID comienza con "PP_"
        boolean exito = transactionId != null && transactionId.startsWith("PP_");
        System.out.printf("[PayPalAdapter] Resultado: %s (ID: %s)%n",
                exito ? "APROBADO" : "RECHAZADO", transactionId);
        return exito;
    }

    @Override
    public String obtenerNombre() {
        return "PayPal";
    }
}

# Laboratorio: Patrones Estructurales — Adapter y Composite

## Descripción

Este proyecto Spring Boot demuestra la implementación de dos patrones de diseño **estructurales** de la familia GoF:

### 🔌 Patrón Adapter
Permite que dos interfaces incompatibles trabajen juntas. En este laboratorio, se adaptan las APIs legacy de **PayPal** (`executePayment`) y **Stripe** (`charge`) a la interfaz unificada `PasarelaPago` (`procesarPago`), sin modificar las APIs originales.

| Participante       | Clase                   | Rol                               |
|--------------------|-------------------------|-----------------------------------|
| Target (Objetivo)  | `PasarelaPago`          | Interfaz que el cliente usa       |
| Adaptee (Adaptado) | `PayPalAPI`, `StripeAPI`| APIs incompatibles preexistentes  |
| Adapter            | `PayPalAdapter`, `StripeAdapter` | Puente que traduce llamadas |
| Client             | `TiendaServicio`        | Usa `PasarelaPago` sin saber más  |

### 🌲 Patrón Composite
Compone objetos en estructuras de árbol para representar jerarquías parte-todo. Permite tratar objetos individuales (`Producto`) y compuestos (`Categoria`) de forma uniforme a través de la interfaz `ItemCatalogo`.

| Participante  | Clase          | Rol                                          |
|---------------|----------------|----------------------------------------------|
| Component     | `ItemCatalogo` | Interfaz común para hojas y compuestos       |
| Leaf (Hoja)   | `Producto`     | Nodo terminal sin hijos                      |
| Composite     | `Categoria`    | Nodo que contiene y gestiona hijos           |
| Client        | `TiendaApp`    | Usa `ItemCatalogo` sin distinguir tipo       |

---

## Estructura del Proyecto

```
tienda-patrones-estructurales/
├── pom.xml
└── src/
    ├── main/java/com/universidad/tienda/
    │   ├── TiendaApp.java                  # Punto de entrada (@SpringBootApplication)
    │   ├── adapter/
    │   │   ├── PasarelaPago.java           # Interfaz Target
    │   │   ├── PayPalAPI.java              # Adaptee (API legacy PayPal)
    │   │   ├── StripeAPI.java              # Adaptee (API legacy Stripe)
    │   │   ├── PayPalAdapter.java          # Adapter (@Component "paypal")
    │   │   └── StripeAdapter.java          # Adapter (@Component "stripe")
    │   ├── composite/
    │   │   ├── ItemCatalogo.java           # Interfaz Component
    │   │   ├── Producto.java               # Leaf (hoja)
    │   │   └── Categoria.java              # Composite (compuesto)
    │   └── servicio/
    │       └── TiendaServicio.java         # @Service con @Qualifier
    └── test/java/com/universidad/tienda/
        └── TiendaServicioTest.java         # 4 tests JUnit 5
```

---

## Inyección de Dependencias con @Qualifier

`TiendaServicio` inyecta `PasarelaPago` usando `@Qualifier("stripe")` para resolver la ambigüedad entre dos beans del mismo tipo:

```java
public TiendaServicio(@Qualifier("stripe") PasarelaPago pasarelaPago) { ... }
```

Sin `@Qualifier`, Spring lanzaría `NoUniqueBeanDefinitionException` porque tanto `PayPalAdapter` como `StripeAdapter` implementan `PasarelaPago`.

---

## Ejecución

### Requisitos
- Java 17+
- Maven 3.8+

### Ejecutar la aplicación

```bash
mvn spring-boot:run
```

### Ejecutar las pruebas

```bash
mvn test
```

### Compilar el paquete

```bash
mvn clean package
```

---

## Tests JUnit 5

| Test                                | Patrón    | Validación                                       |
|-------------------------------------|-----------|--------------------------------------------------|
| `testAdapterPayPalProcesaPago`      | Adapter   | `assertTrue` — PayPal retorna ID con prefijo PP_ |
| `testAdapterStripeRechazaTokenVacio`| Adapter   | `assertFalse` — Stripe rechaza token vacío       |
| `testCompositeCalculaPrecioTotal`   | Composite | `assertEquals` — suma plana de productos         |
| `testCompositeAnidado`              | Composite | `assertEquals` — suma recursiva de sub-categorías|

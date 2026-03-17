# Playwright Java POM Framework

Framework de automatización de pruebas usando **Playwright** con **Java**, **TestNG** y el patrón **Page Object Model (POM)**.

## Tecnologías

| Tecnología | Versión | Propósito |
|---|---|---|
| Java | 17 | Lenguaje de programación |
| Playwright | 1.49.0 | Automatización de navegador |
| TestNG | 7.10.2 | Framework de pruebas |
| AssertJ | 3.26.3 | Aserciones fluidas |
| Log4j2 | 2.24.3 | Logging |
| Maven | 3.x | Gestión de dependencias |

## Estructura del Proyecto

```
playwright-java-pom/
├── pom.xml                                    # Configuración Maven
├── src/
│   ├── main/java/com/automation/
│   │   ├── config/
│   │   │   └── ConfigManager.java             # Gestión de configuración (Singleton)
│   │   ├── pages/
│   │   │   ├── BasePage.java                  # Clase base para Page Objects
│   │   │   ├── LoginPage.java                 # PO: Página de Login
│   │   │   ├── InventoryPage.java             # PO: Página de Productos
│   │   │   ├── CartPage.java                  # PO: Página del Carrito
│   │   │   ├── CheckoutPage.java              # PO: Checkout Step 1
│   │   │   ├── CheckoutOverviewPage.java      # PO: Checkout Overview
│   │   │   └── CheckoutCompletePage.java      # PO: Confirmación de Orden
│   │   └── utils/
│   │       ├── BrowserFactory.java            # Fábrica de navegadores
│   │       └── ScreenshotHelper.java          # Capturas de pantalla
│   └── test/
│       ├── java/com/automation/
│       │   ├── base/
│       │   │   ├── BaseTest.java              # Clase base para tests
│       │   │   └── TestListener.java          # Listener para eventos de test
│       │   └── tests/
│       │       ├── LoginTest.java             # Tests de Login
│       │       ├── InventoryTest.java         # Tests de Inventario
│       │       └── CartTest.java              # Tests de Carrito y Checkout
│       └── resources/
│           ├── config.properties              # Configuración del framework
│           ├── testng.xml                     # Suite de TestNG
│           └── log4j2.xml                     # Configuración de logging
```

## Buenas Prácticas Implementadas

### Page Object Model (POM)
- Cada página tiene su propia clase con locators y métodos encapsulados
- `BasePage` con métodos comunes reutilizables
- Fluent API (method chaining) para mejor legibilidad
- Navegación entre páginas retorna el PO correspondiente

### Gestión del Ciclo de Vida
- `@BeforeSuite` / `@AfterSuite`: Playwright instance
- `@BeforeClass` / `@AfterClass`: Browser instance
- `@BeforeMethod` / `@AfterMethod`: Context + Page (aislamiento por test)

### Aserciones
- **AssertJ** para aserciones fluidas y descriptivas
- Mensajes `.as()` en cada aserción para mejor debugging
- Validaciones tanto positivas como negativas

### Configuración
- `ConfigManager` (Singleton) lee desde `config.properties`
- Soporte para override via System Properties (`-Dbrowser=firefox`)
- Configuración externalizada del código

### Logging & Reporting
- Log4j2 con salida a consola y archivo
- `TestListener` para capturas de pantalla automáticas en fallos
- Tracing habilitado para debugging

## Ejecución

### Pre-requisitos
- Java 17+
- Maven 3.x

### Instalar browsers de Playwright
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### Ejecutar todos los tests
```bash
mvn clean test
```

### Ejecutar con navegador visible
```bash
mvn clean test -Dheadless=false
```

### Ejecutar con Firefox
```bash
mvn clean test -Dbrowser=firefox
```

### Ejecutar una clase de test específica
```bash
mvn clean test -Dtest=LoginTest
```

### Ejecutar un test específico
```bash
mvn clean test -Dtest=LoginTest#testSuccessfulLogin
```

## Aplicación Bajo Prueba

Los tests están escritos contra [SauceDemo](https://www.saucedemo.com/), una aplicación web de demo ideal para practicar automatización.

**Credenciales de prueba:**
- Usuario válido: `standard_user` / `secret_sauce`
- Usuario bloqueado: `locked_out_user` / `secret_sauce`

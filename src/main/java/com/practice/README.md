# 📚 Ejercicios de Práctica Java

Estructura de carpetas para practicar Java y hacer pruebas.

## 📁 Estructura

```
src/
├── main/java/com/practice/
│   ├── basics/           # Ejercicios básicos
│   │   └── JavaBasics.java
│   ├── collections/      # List, Set, Map
│   │   └── CollectionsExercises.java
│   └── streams/          # Java Streams
│       └── StreamsExercises.java
│
└── test/java/com/practice/
    └── basics/
        └── JavaBasicsTest.java
```

## 🚀 Cómo Usar

### 1. Implementa los métodos
Abre cualquier archivo en `src/main/java/com/practice/` y completa los métodos marcados con `TODO`.

### 2. Ejecuta las pruebas
```bash
# Todas las pruebas
mvn test

# Solo pruebas de basics
mvn test -Dtest=JavaBasicsTest
```

### 3. Verifica que pasen
Los tests usan **AssertJ** para validaciones.

## 📝 Ejercicios Disponibles

### ✅ Básicos (JavaBasics)
- ✏️ `sumar()` - Suma de números
- ✏️ `factorial()` - Cálculo factorial
- ✏️ `esPar()` - Verificar par/impar
- ✏️ `encontrarMaximo()` - Máximo en array
- ✏️ `invertirTexto()` - Invertir string
- ✏️ `contarVocales()` - Contar vocales
- ✏️ `esPalindromo()` - Verificar palíndromo

### 📦 Collections
- ✏️ `eliminarDuplicados()` - Usar Set
- ✏️ `contarPalabras()` - Usar Map
- ✏️ `encontrarComunes()` - Intersección
- ✏️ `ordenarDescendente()` - Sort

### 🌊 Streams (Java 8+)
- ✏️ `filtrarPares()` - filter()
- ✏️ `convertirMayusculas()` - map()
- ✏️ `sumarTodos()` - reduce()
- ✏️ `obtenerPrimeros()` - limit()
- ✏️ `calcularPromedio()` - average()

## 💡 Tips

- Usa **IntelliJ keybindings** para snippets rápidos
- `sout` + Tab = System.out.println()
- `Cmd + .` = Quick fix
- `Cmd + Alt + L` = Formatear código

---

**¡Comienza con JavaBasics!** 🎯

# 🚀 API Testing con Playwright

Ejemplos de pruebas de API usando Playwright Java y buenas prácticas.

## 📁 Estructura

```
src/
├── main/java/com/automation/api/
│   └── models/                    # POJOs para request/response
│       ├── User.java
│       ├── UserResponse.java
│       ├── CreateUserRequest.java
│       └── CreateUserResponse.java
│
└── test/java/com/automation/api/
    ├── base/
    │   └── BaseApiTest.java       # Clase base para API tests
    └── tests/
        └── UserApiTest.java       # Tests de ejemplo
```

## ✅ Características

### 🎯 Buenas Prácticas Implementadas

1. **Separación de Concerns**
   - `BaseApiTest`: Manejo de lifecycle
   - POJOs: Modelos tipados
   - Tests: Lógica de validación

2. **Reutilización**
   - Context compartido
   - Headers por defecto
   - Base URL configurada

3. **Logging**
   - Request/Response logging
   - Debug information
   - Execution tracking

4. **Type Safety**
   - Jackson para deserialización
   - POJOs tipados
   - Validaciones con AssertJ

## 🧪 Tests Incluidos

### ✅ GET Requests
- `testGetSingleUser()` - Obtener usuario por ID
- `testGetNonExistentUser()` - Error 404
- `testGetUsersList()` - Lista paginada

### ✅ POST Requests
- `testCreateUser()` - Crear nuevo usuario

### ✅ PUT Requests
- `testUpdateUser()` - Actualizar usuario completo

### ✅ PATCH Requests
- `testPatchUser()` - Actualizar parcialmente

### ✅ DELETE Requests
- `testDeleteUser()` - Eliminar usuario

### ✅ Performance
- `testResponseTime()` - Validar tiempo de respuesta

## 🚀 Ejecutar Tests

```bash
# Todos los API tests
mvn test -Dtest=UserApiTest

# Un test específico
mvn test -Dtest=UserApiTest#testGetSingleUser

# Con logs detallados
mvn test -Dtest=UserApiTest -X
```

## 📊 API Pública Usada

**ReqRes API**: https://reqres.in/

- ✅ Gratis y sin autenticación
- ✅ Endpoints RESTful completos
- ✅ Respuestas reales
- ✅ Perfecto para práctica

## 💡 Ejemplo de Uso

```java
@Test
public void testGetSingleUser() throws Exception {
    // Arrange
    int userId = 2;
    
    // Act
    APIResponse response = request.get("/users/" + userId);
    
    // Assert
    assertThat(response.status()).isEqualTo(200);
    
    UserResponse userResponse = objectMapper.readValue(
        response.text(), 
        UserResponse.class
    );
    
    assertThat(userResponse.getData().getId()).isEqualTo(userId);
}
```

## 🔧 Dependencias Agregadas

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.18.2</version>
</dependency>
```

## 📝 Próximos Pasos

- [ ] Agregar tests de autenticación (Bearer token)
- [ ] Tests de validación de schemas (JSON Schema)
- [ ] Tests combinados UI + API
- [ ] Tests de carga/performance
- [ ] Mock de responses

---

**¡Explora los tests y aprende API testing!** 🎯

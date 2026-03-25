# Algoritmo Ferry - Programación Dinámica

## Descripción del problema

Se dispone de un ferry con dos carriles paralelos de igual longitud L (Babor y Estribor). Se quiere embarcar el máximo número de vehículos de una cola, respetando el orden de llegada, asignando cada vehículo a uno de los dos carriles sin superar la longitud de ninguno.

---

## 1. Definición del estado

La tabla de programación dinámica es `dp[i][j]`, donde:

- **`i`** (fila): número de vehículos considerados hasta el momento (de 0 a N).
- **`j`** (columna): longitud acumulada ocupada en el carril de **Babor** (de 0 a L).

`dp[i][j] = true` si es posible distribuir los primeros `i` vehículos entre los dos carriles de forma que Babor ocupe exactamente `j` unidades de longitud.

### ¿Por qué no es necesario almacenar la longitud de Estribor?

Dado que `sumatorio[i]` es la suma total de las longitudes de los primeros `i` vehículos, y toda esa longitud queda repartida entre los dos carriles, la longitud ocupada en Estribor se puede deducir directamente:

```
longitud_estribor = sumatorio[i] - j
```

Por tanto, conocer `i` y `j` es suficiente para conocer el estado completo del sistema. No es necesaria una tercera dimensión.

---

## 2. Relación de recurrencia

### Caso base

```
dp[0][0] = true
```

Con 0 vehículos embarcados, la longitud ocupada en Babor es 0.

### Paso recursivo

Para cada vehículo `i` (con longitud `v[i]`) y cada longitud de Babor `j`:

```
dp[i][j] = true  si:

  (1) dp[i-1][j - v[i]] = true  AND  j - v[i] >= 0  AND  j <= L
        → Se coloca el vehículo i en Babor

  OR

  (2) dp[i-1][j] = true  AND  sumatorio[i] - j <= L
        → Se coloca el vehículo i en Estribor
          (la longitud de Estribor resultante no supera L)
```

En ambos casos se verifica que ningún carril supere la longitud `L` del ferry.

---

## 3. Complejidad

### Temporal

- La tabla `dp` tiene dimensiones `(N+1) × (L+1)`.
- Cada celda se calcula en tiempo **O(1)**: se consultan como máximo dos celdas de la fila anterior.
- **Coste total: O(N × L)**

### Espacial

- La tabla ocupa **O(N × L)** de memoria.
- El array de sumas acumuladas `sumatorio` ocupa **O(N)**.



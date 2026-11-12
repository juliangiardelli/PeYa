# Delivery Rush

Videojuego desarrollado en **Java** para la materia **Programación I**. El jugador controla a un repartidor que debe recorrer una ciudad, retirar pedidos en los negocios y entregarlos en las casas correspondientes antes de que se agote el tiempo.

El proyecto fue realizado en equipo y obtuvo una calificación de **10**.

## Características

- Mapa urbano compuesto por manzanas, casas y negocios.
- Sistema de pedidos con destinos y tiempo límite para realizar las entregas.
- Enemigos que persiguen al repartidor; algunos pueden arrojar bombas.
- Sistema de disparos para defenderse de los enemigos.
- Detección de colisiones entre el personaje, los elementos del mapa, los enemigos y los disparos.
- Administración de vidas, dinero y combustible.
- Estación de servicio para cargar combustible.
- Aparición de un turbo que aumenta temporalmente la velocidad.
- Pantallas de inicio, victoria y derrota.
- Panel con información del estado de la partida.

El objetivo es completar **cinco entregas**. La partida también puede finalizar si se cumple alguna de las condiciones de derrota.

## Controles

| Tecla / acción | Función |
|---|---|
| **W, A, S, D** o **flechas** | Mover al repartidor |
| **Clic izquierdo** o **barra espaciadora** | Disparar |
| **Enter** | Comenzar la partida o volver a jugar desde la pantalla final |
| **Esc** | Salir desde la pantalla final |

## Tecnologías y conceptos utilizados

- Java.
- Programación orientada a objetos.
- Arreglos y organización del código en paquetes.
- Detección de colisiones y actualización del estado del juego.
- Biblioteca **Entorno**, incluida como `entorno.jar`.

## Organización del proyecto

El código está organizado en paquetes según las responsabilidades de cada parte del juego:

| Paquete | Responsabilidad |
|---|---|
| `juego` | Ciclo principal del juego, panel informativo y pantallas |
| `mapa` | Mapa, manzanas, casas, negocios y turbo |
| `pedidos` | Creación, seguimiento y actualización de los encargos |
| `protagonista` | Comportamiento del repartidor y detección de colisiones |
| `enemigos` | Enemigos y bombas |
| `disparo` | Lógica de los disparos |
| `gasolinera` | Estación de servicio y carga de combustible |

## Compilación y ejecución

1. Clonar o descargar este repositorio.
2. Importar el proyecto en un IDE compatible con Java.
3. Agregar el archivo `entorno.jar`, incluido en el repositorio, al *Build Path* o classpath del proyecto.
4. Ejecutar la clase `juego.Juego`, que contiene el método `main`.
5. Mantener los recursos gráficos disponibles en la estructura de carpetas esperada por el proyecto.

> El repositorio no incluye una configuración de construcción mediante Maven o Gradle. La biblioteca `entorno.jar` debe estar agregada al proyecto para compilar y ejecutar.

## Contexto académico

- **Materia:** Programación I.
- **Resultado:** calificación 10.

## Integrantes

- Rocío Gallardo
- Marcos Marchese
- Julián Giardelli

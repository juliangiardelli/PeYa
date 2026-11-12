# Delivery Rush

**Delivery Rush** es un videojuego desarrollado en Java para la materia **Programación I**. El jugador controla a un repartidor que debe recorrer una ciudad, retirar pedidos en los negocios y entregarlos en las casas correspondientes antes de que se agote el tiempo.

El proyecto fue realizado en equipo y obtuvo una calificación de **10**.

## 🎮 Cómo se juega

El objetivo es completar **cinco entregas** mientras se administra el tiempo, el combustible y las vidas del repartidor. La ciudad está recorrida por enemigos que persiguen al jugador y, algunos de ellos, arrojan bombas.

Durante la partida es necesario:

- Retirar pedidos en los negocios y llevarlos a la casa indicada.
- Completar cada entrega dentro del tiempo límite.
- Evitar a los enemigos y sus bombas.
- Usar disparos para defenderse de los enemigos.
- Administrar el combustible y aprovechar la estación de servicio.
- Recoger el turbo cuando aparece para obtener un aumento temporal de velocidad.

La partida termina al completar las cinco entregas o al cumplirse alguna de las condiciones de derrota.

## 🕹️ Controles

| Tecla / acción | Función |
|---|---|
| **W, A, S, D** o **flechas** | Mover al repartidor |
| **Clic izquierdo** o **barra espaciadora** | Disparar |
| **Enter** | Comenzar la partida o volver a jugar desde la pantalla final |
| **Esc** | Salir desde la pantalla final |

## ✨ Funcionalidades

- Mapa urbano compuesto por manzanas, casas y negocios.
- Sistema de pedidos con destinos y tiempo límite.
- Enemigos con movimiento y comportamiento de persecución.
- Bombas y detección de explosiones.
- Sistema de disparos y colisiones.
- Gestión de vidas, dinero y combustible.
- Estación de servicio para cargar combustible.
- Aparición de un turbo que incrementa temporalmente la velocidad.
- Pantallas de inicio, victoria y derrota.
- Panel de información con el estado de la partida.

## 🧩 Organización del proyecto

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

## 🛠️ Tecnologías y herramientas

- **Java**
- **Entorno**, biblioteca provista para el desarrollo y la ejecución del juego (`entorno.jar`)
- Programación orientada a objetos

## ▶️ Cómo ejecutar el proyecto

1. Clonar o descargar este repositorio.
2. Importar el proyecto en un IDE compatible con Java.
3. Agregar el archivo `entorno.jar`, incluido en el repositorio, al *Build Path* o classpath del proyecto.
4. Ejecutar la clase `juego.Juego), que contiene el método `main`.
5. Mantener los recursos gráficos disponibles en la estructura de carpetas esperada por el proyecto.

> El repositorio no incluye una configuración de construcción mediante Maven o Gradle; la biblioteca `entorno.jar` debe estar agregada al proyecto para compilar y ejecutar.

## 📚 Contexto académico

Proyecto realizado para **Programación I**, como trabajo práctico integrador de aplicación de los conceptos de programación y orientación a objetos vistos durante la cursada.

**Calificación: 10**

## 👥 Integrantes

- Rocío Gallardo
- Marcos Marchese
- Julián Giardelli

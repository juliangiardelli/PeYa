package protagonista;

import java.awt.Color;
import java.awt.Image;

import disparo.Disparo;
import entorno.Entorno;
import entorno.Herramientas;
import gasolinera.Gasolinera;
import mapa.Casa;
import mapa.Manzana;
import mapa.Mapa;
import mapa.Tiendas;

public class PersonajePrincipalDefinido {

	// ---------- Variables de instancia ----------

	// Posición inicial
	private double x = 30;
	private double y = 581;

	// Posición a la que se quiere mover
	private double proximoX;
	private double proximoY;

	// Orientación actual y última orientación guardada
	private double dx = 0;
	private double dy = -1;
	private double direccionX = 0;
	private double direccionY = -1;

	// Dimensiones del personaje
	private double ancho = 32;
	private double alto = 32;

	// Velocidad normal
	private int velocidad = 3;

	// Vidas y ticks que le quedan titilando después de perder una vida
	private int vidas = 3;
	private int ticksTitilando = 0;

	// Variable que indica si el turbo está activo o no y la cantidad de ticks que le quedan activos
	private boolean turboActivo = false;
	private int ticksTurbo = 0;

	// Nafta y dinero
	private int nafta = 10000;
	private int dinero = 0;
	private boolean ya_cargo_nafta = true;

	// Posiciones de la gasolinera
	private double[] gasolinera_cords;

	// Disparo del personaje principal
	private Disparo disparo;

	// Apariencia: color temporal e imagen del repartidor
	private Color color = Color.LIGHT_GRAY;
	private Image imagen = Herramientas.cargarImagen("repartidor.png");

	// ---------- Constructor ----------

	public PersonajePrincipalDefinido(Gasolinera gasolinera) {
		this.gasolinera_cords = gasolinera.ubicaciones_gasolinera();
	}

	// ---------- Metodos publicos ----------

	public void dibujar(Entorno entorno, Mapa mapa) {

		// Calcula la posición a la que quiere moverse
		this.movimientoPersonaje(entorno);
		this.guardarOrientacion();
		this.orientacionPersonaje(entorno);
		this.disparar(entorno);

		// Actualiza el estado del turbo
		this.actualizarTurbo();

		Detector_colisiones colisiones = new Detector_colisiones();

		boolean choca = false;
		Manzana[] manzanas = mapa.getManzanas();

		// Recorre las manzanas y sus casas
		for (Manzana m : manzanas) {
			if (m == null) {
				continue;
			}

			// Colisiones con las casas
			for (Casa casa : m.retornar_casas()) {

				double[] coordenadas = casa.cordenadas();

				if (colisiones.hay_colision(
						this.proximoX, this.proximoY, this.ancho, this.alto,
						coordenadas[0], coordenadas[1],
						coordenadas[2], coordenadas[3])) {

					choca = true;
					break;
				}
			}

			// Colisiones con los negocios
			for (Tiendas tienda : m.retornar_tiendas()) {

				double[] coordenadas = tienda.retornar_ubicaciones();

				if (colisiones.hay_colision(
						this.proximoX, this.proximoY, this.ancho, this.alto,
						coordenadas[0], coordenadas[1],
						coordenadas[2], coordenadas[3])) {

					choca = true;
					break;
				}
			}
		}

		// Si no choca con nada, actualiza su posición
		if (!choca) {
			this.x = this.proximoX;
			this.y = this.proximoY;
		}

		// Calcula la escala de la imagen
		double ladoImagen = Math.max(
				this.imagen.getWidth(null),
				this.imagen.getHeight(null));

		double escala = this.ancho / ladoImagen;

		// Control del titileo después de perder una vida
		if (this.ticksTitilando > 0) {
			this.ticksTitilando--;
		}

		if (this.ticksTitilando == 0 || (this.ticksTitilando / 5) % 2 == 0) {
			entorno.dibujarImagen(this.imagen, this.x, this.y, 0, escala);
		}
	}

	// Resta una vida y empieza a titilar
	public void perderVida() {
		if (this.ticksTitilando > 0) {
			return;
		}

		if (this.vidas > 0) {
			this.vidas--;
			this.ticksTitilando = 90;
		}
	}

	// Elimina el disparo cuando choca con un enemigo, una casa o un negocio.
	public void eliminarDisparo() {
		this.disparo = null;
	}

	// Suma dinero (suma 3 pesos por cada pedido entregado)
	public void sumar_dinero() {
		this.dinero += 3;
	}

	// Resta combustible si el turbo no está activo
	public void gasta_nafta() {
		if (!this.turboActivo) {
			this.nafta -= 5;
		}
	}

	// Habilita la carga de nafta
	public void habilitar_carga_de_nafta() {
		this.ya_cargo_nafta = false;
	}

	// ---------- Metodos privados ----------

	// Establece la orientación según las teclas presionadas
	private void orientacionPersonaje(Entorno entorno) {

		if (entorno.estaPresionada('w') || entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			dy = -1;
			dx = 0;
		}

		if (entorno.estaPresionada('s') || entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			dy = 1;
			dx = 0;
		}

		if (entorno.estaPresionada('a') || entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			dx = -1;
			dy = 0;
		}

		if (entorno.estaPresionada('d') || entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			dx = 1;
			dy = 0;
		}
	}

	private void guardarOrientacion() {
		direccionX = dx;
		direccionY = dy;
	}

	private void movimientoPersonaje(Entorno entorno) {

		// Parte de la posición actual
		this.proximoX = this.x;
		this.proximoY = this.y;

		// Comprueba si puede cargar nafta en la gasolinera
		if (Detector_colisiones.hay_colision(
				gasolinera_cords[0], gasolinera_cords[1],
				gasolinera_cords[2], gasolinera_cords[3],
				this.proximoX, this.proximoY,
				this.ancho, this.alto)
				&& ya_cargo_nafta != true && this.dinero >= 3) {

			this.ya_cargo_nafta = true;
			this.dinero -= 3;
			this.nafta = 10000;
		}

		// Movimiento hacia arriba
		if ((entorno.estaPresionada('w') || entorno.estaPresionada(entorno.TECLA_ARRIBA))
				&& y > 28 + this.alto / 2) {
			this.proximoY -= this.movimientoTurbo();
			gasta_nafta();
		}

		// Movimiento hacia abajo
		if ((entorno.estaPresionada('s') || entorno.estaPresionada(entorno.TECLA_ABAJO))
				&& y < entorno.alto() - this.alto / 2) {
			this.proximoY += this.movimientoTurbo();
			gasta_nafta();
		}

		// Movimiento hacia la izquierda
		if ((entorno.estaPresionada('a') || entorno.estaPresionada(entorno.TECLA_IZQUIERDA))
				&& x > 12) {
			this.proximoX -= this.movimientoTurbo();
			gasta_nafta();
		}

		// Movimiento hacia la derecha
		if ((entorno.estaPresionada('d') || entorno.estaPresionada(entorno.TECLA_DERECHA))
				&& x < entorno.ancho() - 12) {
			this.proximoX += this.movimientoTurbo();
			gasta_nafta();
		}
	}

	// Devuelve la velocidad según el estado del turbo
	private int movimientoTurbo() {
		if (this.turboActivo) {
			return this.velocidad * 2;
		}

		return this.velocidad;
	}

	// Actualiza el estado del turbo y disminuye los ticks si está activo
	private void actualizarTurbo() {
		if (this.turboActivo) {
			this.ticksTurbo--;
			if (this.ticksTurbo <= 0) {
				this.turboActivo = false;
			}
		}
	}

	private void disparar(Entorno entorno) {

		// Crea un disparo si se presiona espacio o clic izquierdo
		if ((entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)
				|| entorno.estaPresionada(entorno.TECLA_ESPACIO))
				&& disparo == null) {

			disparo = new Disparo(this.x, this.y, this.dx, this.dy, 5, Color.red);
		}

		// Mueve y dibuja el disparo activo
		if (disparo != null) {
			disparo.mover();
			disparo.dibujarDisparo(entorno);

			// Elimina el disparo si llegó a su alcance
			if (disparo.salioDelMapa(entorno)) {
				disparo = null;
			}
		}
	}

	// ---------- Getters ----------

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getAncho() {
		return this.ancho;
	}

	public double getAlto() {
		return this.alto;
	}

	public int getVidas() {
		return this.vidas;
	}

	public Disparo getDisparo() {
		return this.disparo;
	}

	// Retorna el dinero acumulado
	public int print_dinero() {
		return dinero;
	}

	// Retorna la nafta restante
	public int print_nafta() {
		return nafta;
	}

	// ---------- Setters ----------

	// Activa o desactiva el turbo
	public void setTurboActivo(boolean turboActivo) {
		this.turboActivo = turboActivo;
		if (turboActivo) {
			this.ticksTurbo = 150; //Cantidad de ticks que dura el turbo activo
		} else {
			this.ticksTurbo = 0;
		}
	}
}

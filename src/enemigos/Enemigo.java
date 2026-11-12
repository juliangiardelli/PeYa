package enemigos;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {

	// ---------- Variables de instancia ----------

	// posicion (centro del enemigo)
	private double x;
	private double y;

	// tamaño
	private double ancho;
	private double alto;

	// movimiento
	private double velocidad;

	// true = se mueve de izquierda a derecha, false = de arriba a abajo
	private boolean horizontal;

	// 1 = avanza (derecha o abajo), -1 = retrocede (izquierda o arriba)
	private int direccion;

	// bombas
	// true si este enemigo puede tirar bombas
	private boolean tiraBombas;

	// cuenta los ticks que faltan para tirar la proxima bomba
	private int contadorBomba;

	// apariencia
	private Image imagen;

	// ---------- Constructor ----------

	public Enemigo(double x, double y, boolean horizontal, boolean tiraBombas) {
		this.x = x;
		this.y = y;
		this.horizontal = horizontal;
		this.ancho = 26;
		this.alto = 26;
		this.velocidad = 2;
		this.direccion = 1;
		this.tiraBombas = tiraBombas;
		this.contadorBomba = 300;
		this.imagen = Herramientas.cargarImagen("enemigo.png");
	}

	// ---------- Metodos publicos ----------

	// mueve al enemigo en linea recta y lo hace rebotar en los bordes
	public void mover(Entorno entorno) {
		if (this.horizontal) {
			this.x += this.velocidad * this.direccion;
			if (this.x <= this.ancho / 2 || this.x >= entorno.ancho() - this.ancho / 2) {
				this.direccion *= -1;
			}
		} else {
			this.y += this.velocidad * this.direccion;
			// 28 es el alto del panel de arriba: el enemigo rebota debajo de la barra
			if (this.y <= 28 + this.alto / 2 || this.y >= entorno.alto() - this.alto / 2) {
				this.direccion *= -1;
			}
		}
	}

	// si le toca, devuelve una bomba nueva en su posicion; si no, devuelve null
	public Bomba tirarBomba() {
		if (!this.tiraBombas) {
			return null;
		}
		this.contadorBomba--;
		if (this.contadorBomba <= 0) {
			this.contadorBomba = 300;
			return new Bomba(this.x, this.y, this.horizontal);
		}
		return null;
	}

	// true si el rectangulo recibido toca al enemigo
	public boolean colisionaCon(double otroX, double otroY, double otroAncho, double otroAlto) {
		return Math.abs(this.x - otroX) < (this.ancho + otroAncho) / 2
			&& Math.abs(this.y - otroY) < (this.alto + otroAlto) / 2;
	}

	public void dibujar(Entorno entorno) {
		// escala para que la imagen entre justo en el cuadrado de colision
		double lado = Math.max(this.imagen.getWidth(null), this.imagen.getHeight(null));
		double escala = this.ancho / lado;
		entorno.dibujarImagen(this.imagen, this.x, this.y, 0, escala);
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
}

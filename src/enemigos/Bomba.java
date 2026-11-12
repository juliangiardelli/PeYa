package enemigos;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bomba {

	// ---------- Variables de instancia ----------

	// posicion (centro de la bomba)
	private double x;
	private double y;

	// true si la bomba esta en una calle horizontal, false si es vertical
	private boolean horizontal;

	// apariencia
	private Image imagen;

	// tamaño de la bomba antes de explotar
	private double tamanio;

	// tamaño de la explosion: el ancho de la calle y una parte de su largo
	private double anchoCalle;
	private double largoExplosion;

	// contadores de tiempo (en ticks)
	private int tiempoParaExplotar;
	private int duracionExplosion;

	// estado
	private boolean explotando;
	private boolean yaLastimo;

	// ---------- Constructor ----------

	public Bomba(double x, double y, boolean horizontal) {
		this.x = x;
		this.y = y;
		this.horizontal = horizontal;
		this.tamanio = 20;
		this.anchoCalle = 45;
		this.largoExplosion = 200;
		this.tiempoParaExplotar = 150;
		this.duracionExplosion = 50;
		this.explotando = false;
		this.imagen = Herramientas.cargarImagen("bomba.png");
	}

	// ---------- Metodos publicos ----------

	// descuenta tiempo: primero espera, despues explota
	public void actualizar() {
		if (!this.explotando) {
			this.tiempoParaExplotar--;
			if (this.tiempoParaExplotar <= 0) {
				this.explotando = true;
			}
		} else {
			this.duracionExplosion--;
		}
	}

	// devuelve true cuando la explosion ya termino (para volverla null)
	public boolean termino() {
		return this.explotando && this.duracionExplosion <= 0;
	}

	// true si el rectangulo recibido toca la bomba o su explosion
	public boolean alcanza(double otroX, double otroY, double otroAncho, double otroAlto) {
		return Math.abs(this.x - otroX) < (this.anchoActual() + otroAncho) / 2
			&& Math.abs(this.y - otroY) < (this.altoActual() + otroAlto) / 2;
	}

	public boolean lastima(double otroX, double otroY, double otroAncho, double otroAlto) {
		if (this.explotando && !this.yaLastimo && this.alcanza(otroX, otroY, otroAncho, otroAlto)) {
			this.yaLastimo = true;
			return true;
		}
		return false;
	}

	public void dibujar(Entorno entorno) {
		if (!this.explotando) {
			// bomba esperando: imagen ajustada a su tamaño
			double lado = Math.max(this.imagen.getWidth(null), this.imagen.getHeight(null));
			double escala = this.tamanio / lado;
			entorno.dibujarImagen(this.imagen, this.x, this.y, 0, escala);
		} else {
			// explosion: tres capas de color para que parezca fuego
			double ancho = this.anchoActual();
			double alto = this.altoActual();
			entorno.dibujarRectangulo(this.x, this.y, ancho, alto, 0, new Color(200, 30, 0));
			entorno.dibujarRectangulo(this.x, this.y, ancho * 0.8, alto * 0.6, 0, Color.ORANGE);
			entorno.dibujarRectangulo(this.x, this.y, ancho * 0.5, alto * 0.3, 0, Color.YELLOW);
		}
	}

	// ---------- Metodos privados ----------

	// ancho y alto del area que hace daño en este momento
	private double anchoActual() {
		if (!this.explotando) {
			return this.tamanio;
		}
		return this.horizontal ? this.largoExplosion : this.anchoCalle;
	}

	private double altoActual() {
		if (!this.explotando) {
			return this.tamanio;
		}
		return this.horizontal ? this.anchoCalle : this.largoExplosion;
	}
}

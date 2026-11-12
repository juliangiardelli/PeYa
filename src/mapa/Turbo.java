package mapa;

import java.awt.Color;

import entorno.Entorno;
import protagonista.Detector_colisiones;
import java.awt.Image;
import entorno.Herramientas;

public class Turbo {

	// ---------- Variables de instancia ----------

	// posicion y tamaño
	private double x;
	private double y;
	private double ancho = 32;
	private double alto = 32;
	private Image imagen;
	// apariencia
	private Color color = Color.YELLOW;

	// ---------- Constructor ----------
	

	public Turbo(double x, double y) {
		this.imagen = Herramientas.cargarImagen("turbo.png");
		this.x = x;
		this.y = y;
	}

	// ---------- Metodos publicos ----------

	// dibuja el turbo ajustado a su tamaño de colision
	public void dibujarTurbo(Entorno entorno) {
		double lado = Math.max(this.imagen.getWidth(null), this.imagen.getHeight(null));
		double escala = this.ancho / lado;
		entorno.dibujarImagen(this.imagen, this.x, this.y, 0, escala);
	}

	public boolean colisionaCon(
			double otroX, double otroY,
			double otroAncho, double otroAlto) {

		return Detector_colisiones.hay_colision(
			this.x, this.y, this.ancho, this.alto,
			otroX, otroY, otroAncho, otroAlto);
	}
}

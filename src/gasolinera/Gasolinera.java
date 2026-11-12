package gasolinera;

import java.awt.Color;
import java.awt.Image;
import entorno.Herramientas;

import entorno.Entorno;

public class Gasolinera {

	// ---------- Variables de instancia ----------

	private double x;
	private double y;
	private double[] ubicaciones;
	private Entorno entorno;
	Image imagen;
	
	// tamaño de la gasolinera (ancho y alto)
	double tamanio = 50;
	// ---------- Constructor ----------

	public Gasolinera(Entorno entorno, double x, double y) {
		this.imagen = Herramientas.cargarImagen("gasolinera.png");
		this.x = x;
		this.y = y;
		this.entorno = entorno;
		this.ubicaciones = new double[]{this.x, this.y, this.tamanio, this.tamanio};
	}

	// ---------- Metodos publicos ----------

	// imprime la gasolinera en el mapa
	// dibuja la gasolinera ajustada a su tamaño de 50x50
	public void pintar_gasolinera() {
		double lado = Math.max(this.imagen.getWidth(null), this.imagen.getHeight(null));
		double escala = this.tamanio / lado;
		entorno.dibujarImagen(this.imagen, this.x, this.y, 0, escala);
		// cartel arriba de la imagen
		entorno.cambiarFont("Arial", 12, java.awt.Color.WHITE, entorno.NEGRITA);
		entorno.escribirTexto("Gasolinera", this.x - 32, this.y - this.tamanio / 2 - 4);
	}

	// ---------- Getters ----------

	public double[] ubicaciones_gasolinera() {
		return this.ubicaciones;
	}
}

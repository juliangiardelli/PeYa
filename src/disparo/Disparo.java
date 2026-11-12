package disparo;

import java.awt.Color;

import entorno.Entorno;

public class Disparo {

	// ---------- Variables de instancia ----------

	// posicion (centro del disparo)
	private double x;
	private double y;

	// direccion y rapidez del movimiento
	private double dx;
	private double dy;
	private double velocidad;

	// apariencia
	private double diametro = 5;
	private Color color;

	// alcance del disparo
	private double alcance = 100;
	private double distanciaRecorrida = 0;

	// ---------- Constructor ----------

	public Disparo(double x, double y, double dx, double dy, double velocidad, Color color) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.velocidad = velocidad;
		this.color = color;
		
	}

	// ---------- Metodos publicos ----------

	// Mueve el disparo en la dirección especificada por dx y dy, multiplicado por la velocidad.
	public void mover() {
		x += dx * velocidad;
		y += dy * velocidad;
		distanciaRecorrida += velocidad;
	}

	// devuelve true si el disparo salio de los limites de la pantalla
	public boolean salioDelMapa(Entorno entorno) {
		return x < 0 || x > entorno.ancho() || y < 0 || y > entorno.alto();
	}

	//dibuja el disparo en el entorno
	public void dibujarDisparo(Entorno entorno) {
		entorno.dibujarCirculo(x, y, diametro, color);
	}

	// ---------- Getters ----------

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getDiametro() {
		return this.diametro;
	}
}

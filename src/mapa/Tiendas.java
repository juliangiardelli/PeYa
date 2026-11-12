package mapa;

import java.awt.Color;

import entorno.Entorno;

public class Tiendas {

	// ---------- Variables de instancia ----------

	// posicion y tamaño
	private double x;
	private double y;
	private double alto;
	private double ancho;
	private double[] ubicaciones;

	// apariencia
	private Color color;

	// estado
	private boolean pedido = false;

	// ---------- Constructor ----------

	public Tiendas(double x, double y, double alto, double ancho, Color color) {
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.color = color;
		this.ubicaciones = new double[] { this.x, this.y, this.ancho, this.alto };
	}

	// ---------- Metodos publicos ----------

	public void pintar_tienda(Entorno entorno) {
		Color color_actual = pedido == false ? color : Color.magenta;
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, color_actual);
	}

	public void hacer_pedido() {
		this.pedido = true;
	}

	public void retirar_pedido() {
		this.pedido = false;
	}

	// ---------- Getters ----------

	public boolean tiene_pedido() {
		return this.pedido;
	}

	public double[] retornar_ubicaciones() {
		return ubicaciones;
	}
}

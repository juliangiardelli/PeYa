package mapa;

import java.awt.Color;

import entorno.Entorno;
import protagonista.PersonajePrincipalDefinido;

public class Casa {

	// ---------- Variables de instancia ----------

	// posicion y tamaño
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double[] ubicaciones;

	// apariencia
	private Color color;

	// estado
	private boolean realizar_pedido = false;

	// referencias
	private PersonajePrincipalDefinido personaje;

	// ---------- Constructor ----------

	public Casa(double x, double y, double ancho, double alto, Color color, PersonajePrincipalDefinido personaje) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.color = color;
		this.personaje = personaje;
		this.ubicaciones = new double[] { this.x, this.y, this.ancho, this.alto };
	}

	// ---------- Metodos publicos ----------

	public void pintar_casa(Entorno entorno) {
		Color color_actual = this.realizar_pedido == false ? this.color : Color.green;
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, color_actual);
	}

	public void hacer_pedido() {
		this.realizar_pedido = true;
	}

	// si el repartidor entrega el pedido entonces la casa le da +3 pesos
	// y habilita al personaje principal a cargar nafta si es que antes no podía
	public void cancelar_pedido() {
		this.personaje.sumar_dinero();
		this.personaje.habilitar_carga_de_nafta();
		this.realizar_pedido = false;
	}

	// ---------- Getters ----------

	public boolean tiene_pedido() {
		return this.realizar_pedido;
	}

	public double[] cordenadas() {
		return ubicaciones;
	}
}

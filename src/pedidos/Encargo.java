package pedidos;

import java.awt.Color;

import entorno.Entorno;

public class Encargo {

	// ---------- Variables de instancia ----------

	// ubicacion de la casa y del negocio: {x, y, ancho, alto}
	private double[] casa;
	private double[] negocio;

	// estado actual: "Realizado", "Esperando", "En camino", "Entregado" o "Perdido"
	private String estado;

	// momento (en milisegundos) en que vence el pedido
	private long vencimiento;

	// ---------- Constructor ----------

	public Encargo(double[] casa, double[] negocio, int segundosLimite) {
		this.casa = casa;
		this.negocio = negocio;
		this.estado = "Realizado";
		this.vencimiento = System.currentTimeMillis() + segundosLimite * 1000L;
	}

	// ---------- Metodos publicos ----------

	// avanza el estado y revisa si se vencio el tiempo
	public void actualizar() {
		if (this.estado.equals("Realizado")) {
			// el negocio recibe el encargo
			this.estado = "Esperando";
		} else if (this.estaActivo() && System.currentTimeMillis() > this.vencimiento) {
			this.estado = "Perdido";
		}
	}

	// si esta esperando, el repartidor esta cerca y hace clic derecho sobre el negocio, lo retira
	public void intentarRetirar(Entorno entorno, double repX, double repY) {
		if (this.estado.equals("Esperando")
				&& this.estaCerca(this.negocio, repX, repY)
				&& this.clicDerechoSobre(entorno, this.negocio)) {
			this.estado = "En camino";
		}
	}

	// si esta en camino, el repartidor esta cerca y hace clic derecho sobre la casa, lo entrega
	public void intentarEntregar(Entorno entorno, double repX, double repY) {
		if (this.estado.equals("En camino")
				&& this.estaCerca(this.casa, repX, repY)
				&& this.clicDerechoSobre(entorno, this.casa)) {
			this.estado = "Entregado";
		}
	}

	// un enemigo le roba el pedido al repartidor (solo si lo lleva encima)
	public void robar() {
		if (this.estado.equals("En camino")) {
			this.estado = "Perdido";
		}
	}

	// true cuando el pedido ya no esta activo
	public boolean termino() {
		return this.estado.equals("Entregado") || this.estado.equals("Perdido");
	}

	// dibuja la flecha sobre el negocio o sobre la casa, segun el estado
	public void dibujar(Entorno entorno) {
		if (this.estado.equals("Esperando")) {
			this.dibujarFlecha(entorno, this.negocio, Color.RED);
		} else if (this.estado.equals("En camino")) {
			this.dibujarFlecha(entorno, this.casa, Color.GREEN);
		}
	}

	// ---------- Metodos privados ----------

	// true mientras el pedido esta esperando o en camino
	private boolean estaActivo() {
		return this.estado.equals("Esperando") || this.estado.equals("En camino");
	}

	// chequea que el repartidor este cerca del edificio (a menos de 60 pixeles de distancia)
	private boolean estaCerca(double[] edificio, double repX, double repY) {
		return Math.abs(edificio[0] - repX) < 60 && Math.abs(edificio[1] - repY) < 60;
	}

	// se hizo clic derecho y el mouse esta sobre el edificio
	private boolean clicDerechoSobre(Entorno entorno, double[] edificio) {
		return entorno.sePresionoBoton(entorno.BOTON_DERECHO)
				&& Math.abs(entorno.mouseX() - edificio[0]) < edificio[2] / 2
				&& Math.abs(entorno.mouseY() - edificio[1]) < edificio[3] / 2;
	}

	// flecha apuntando hacia abajo, arriba del edificio
	// marca el edificio con un marco y una flecha apuntando hacia abajo, encima de el
	private void dibujarFlecha(Entorno entorno, double[] edificio, Color color) {
		double x = edificio[0];
		double y = edificio[1];
		double ancho = edificio[2];
		double alto = edificio[3];

		// marco alrededor del edificio (4 bordes)
		entorno.dibujarRectangulo(x, y - alto / 2, ancho + 6, 4, 0, color);
		entorno.dibujarRectangulo(x, y + alto / 2, ancho + 6, 4, 0, color);
		entorno.dibujarRectangulo(x - ancho / 2, y, 4, alto + 6, 0, color);
		entorno.dibujarRectangulo(x + ancho / 2, y, 4, alto + 6, 0, color);

		// flecha: palito y punta hecha con rectangulos cada vez mas angostos
		entorno.dibujarRectangulo(x, y - 8, 4, 10, 0, color);
		entorno.dibujarRectangulo(x, y - 2, 12, 3, 0, color);
		entorno.dibujarRectangulo(x, y + 1, 8, 3, 0, color);
		entorno.dibujarRectangulo(x, y + 4, 4, 3, 0, color);
	}

	// ---------- Getters ----------

	// segundos que quedan para entregar (nunca menos de 0)
	public int getSegundosRestantes() {
		long resto = (this.vencimiento - System.currentTimeMillis()) / 1000;
		if (resto < 0) {
			return 0;
		}
		return (int) resto;
	}

	public String getEstado() {
		return this.estado;
	}
}

package juego;

import java.awt.Color;

import entorno.Entorno;

public class Panel {

	// ---------- Metodos publicos ----------

	// dibuja la barra de informacion en la parte de arriba de la pantalla
	public void dibujar(Entorno entorno, int segundos, int vidas, int entregados,
			int eliminados, String estadoPedido, int dinero, int nafta) {

		// fondo de la barra
		entorno.dibujarRectangulo(entorno.ancho() / 2, 14, entorno.ancho(), 28, 0, new Color(20, 20, 30));

		entorno.cambiarFont("Arial", 14, Color.WHITE, entorno.NEGRITA);
		entorno.escribirTexto("Tiempo: " + this.formatearTiempo(segundos), 10, 19);
		entorno.escribirTexto("Vidas: " + vidas, 135, 19);
		entorno.escribirTexto("Entregados: " + entregados, 220, 19);
		entorno.escribirTexto("Eliminados: " + eliminados, 350, 19);
		entorno.escribirTexto("Pedido: " + estadoPedido, 480, 19);

		// extras del grupo: dinero y nafta
		entorno.cambiarFont("Arial", 14, Color.ORANGE, entorno.NEGRITA);
		entorno.escribirTexto("$" + dinero, 670, 19);
		entorno.escribirTexto("Nafta: " + nafta / 100, 720, 19);
	}

	// ---------- Metodos privados ----------

	// convierte segundos al formato mm:ss (por ejemplo 95 -> 01:35)
	private String formatearTiempo(int segundos) {
		int min = segundos / 60;
		int seg = segundos % 60;
		String textoMin = (min < 10 ? "0" : "") + min;
		String textoSeg = (seg < 10 ? "0" : "") + seg;
		return textoMin + ":" + textoSeg;
	}
}

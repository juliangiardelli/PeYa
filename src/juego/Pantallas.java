package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Pantallas {

	// ---------- Variables de instancia ----------

	private Image imagenVictoria;
	private Image imagenDerrota;

	// ---------- Constructor ----------

	public Pantallas() {
		this.imagenVictoria = Herramientas.cargarImagen("victoria.png");
		this.imagenDerrota = Herramientas.cargarImagen("derrota.png");
	}

	// ---------- Metodos publicos ----------

	// pantalla antes de empezar a jugar
	public void dibujarInicio(Entorno entorno) {
		this.pintarFondo(entorno, new Color(25, 25, 35));

		entorno.cambiarFont("Arial", 70, Color.ORANGE, entorno.NEGRITA);
		entorno.escribirTexto("DELIVERY RUSH", 130, 200);

		entorno.cambiarFont("Arial", 20, Color.WHITE, entorno.NORMAL);
		entorno.escribirTexto("Moverse: W A S D o flechas", 270, 290);
		entorno.escribirTexto("Disparar: Click Izquierdo o Barra Espaciadora", 310, 320);
		entorno.escribirTexto("Recoger y entregar pedidos sin que te roben", 200, 350);

		entorno.cambiarFont("Arial", 24, Color.YELLOW, entorno.NEGRITA);
		entorno.escribirTexto("Presiona ENTER para empezar", 220, 450);
	}

	// pantalla de victoria o derrota, con el resumen de la partida
	public void dibujarFinal(Entorno entorno, boolean gano, int enemigosEliminados) {
		if (gano) {
			this.pintarFondo(entorno, new Color(20, 90, 40));
			entorno.cambiarFont("Arial", 90, Color.YELLOW, entorno.NEGRITA);
			entorno.escribirTexto("GANASTE", 190, 220);
			// imagen de victoria, ajustada para que mida 200 px de alto
			double escala = 200.0 / this.imagenVictoria.getHeight(null);
			entorno.dibujarImagen(this.imagenVictoria, 400, 420, 0, escala);
		} else {
			this.pintarFondo(entorno, new Color(90, 15, 15));
			entorno.cambiarFont("Arial", 90, Color.RED, entorno.NEGRITA);
			entorno.escribirTexto("PERDISTE", 180, 220);
			// imagen de derrota, ajustada para que mida 200 px de alto
			double escala = 200.0 / this.imagenDerrota.getHeight(null);
			entorno.dibujarImagen(this.imagenDerrota, 400, 420, 0, escala);
		}

		entorno.cambiarFont("Arial", 24, Color.WHITE, entorno.NORMAL);
		entorno.escribirTexto("Enemigos eliminados: " + enemigosEliminados, 270, 300);

		entorno.cambiarFont("Arial", 20, Color.WHITE, entorno.NORMAL);
		entorno.escribirTexto("Jugar de nuevo", 580, 500);
		entorno.escribirTexto("Salir", 100, 500);

		entorno.cambiarFont("Arial", 16, Color.GRAY, entorno.NORMAL);
		entorno.escribirTexto("(ENTER)", 610, 520);
		entorno.escribirTexto("(ESC)", 100, 520);
	}

	// ---------- Metodos privados ----------

	// pinta toda la pantalla de un color
	private void pintarFondo(Entorno entorno, Color color) {
		entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() / 2,
				entorno.ancho(), entorno.alto(), 0, color);
	}
}

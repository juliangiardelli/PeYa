package mapa;

import java.awt.Color;

import entorno.Entorno;
import protagonista.PersonajePrincipalDefinido;

public class Mapa {

	// ---------- Variables de instancia ----------

	// cantidad y distribucion de las manzanas
	private int manzanas_totales = 20;
	private int manzanas_por_fila = 5;
	private double distancia_entre_manzanas = 135;

	// coordenadas de la manzana que se esta creando
	private double cordenada_x = 90;
	private double cordenada_y = 90;
	private int contador_manzanas_por_fila = 0;

	// manzanas del mapa
	private Manzana[] listaManzanas = new Manzana[20];

	// referencias
	private PersonajePrincipalDefinido personaje;

	// ---------- Constructor ----------

	public Mapa(PersonajePrincipalDefinido personaje) {

		this.personaje = personaje;

		for (int i = 0; i < this.manzanas_totales; i++) {

			if (contador_manzanas_por_fila >= this.manzanas_por_fila) {
				cordenada_x = 90;
				cordenada_y += this.distancia_entre_manzanas;
				contador_manzanas_por_fila = 0;
			}

			contador_manzanas_por_fila += 1;

			// establece las cordenadas x e y en ese orden de cada manzana
			listaManzanas[i] = new Manzana(cordenada_x, cordenada_y, personaje);

			cordenada_x += this.distancia_entre_manzanas;
		}
	}

	// ---------- Metodos publicos ----------

	public void pintarMapa(Entorno entorno) {

		// fondo gris de las calles (ocupa toda la pantalla)
		entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() / 2, entorno.ancho(), entorno.alto(), 0, Color.DARK_GRAY);

		for (Manzana m : this.listaManzanas) {
			if (m != null) {
				m.generarManzanas(entorno);
			}
		}
	}

	// ---------- Getters ----------

	public Manzana[] getManzanas() {
		return this.listaManzanas;
	}
}

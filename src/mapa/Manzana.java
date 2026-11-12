package mapa;

import java.awt.Color;

import entorno.Entorno;
import protagonista.PersonajePrincipalDefinido;

public class Manzana {

	// ---------- Variables de instancia ----------

	// posicion
	private double x;
	private double y;

	// edificios de la manzana
	private Casa[] casas = new Casa[3];
	private Tiendas[] tiendas = new Tiendas[1];

	// referencias
	private PersonajePrincipalDefinido personaje;

	// ---------- Constructor ----------

	public Manzana(double x, double y, PersonajePrincipalDefinido personaje) {
		this.personaje = personaje;
		this.x = x;
		this.y = y;

		// añade los objetos casa al array de casas
		casas[0] = new Casa(this.x, this.y, 45, 45, Color.yellow, personaje);
		casas[1] = new Casa(this.x + 45, this.y, 45, 45, Color.blue, personaje);
		casas[2] = new Casa(this.x, this.y + 45, 45, 45, Color.red, personaje);

		// añade el objeto tienda al array de tiendas
		tiendas[0] = new Tiendas(this.x + 45, this.y + 45, 45, 45, Color.white);
	}

	// ---------- Metodos publicos ----------

	public void generarManzanas(Entorno entorno) {

		// pasto verde debajo de la manzana
		entorno.dibujarRectangulo(this.x + 22.5, this.y + 22.5, 100, 100, 0, new Color(90, 170, 70));

		// imprime por pantalla todas las casas del array , haciendo uso de su metodo pintar_casa
		for (Casa casa : casas) {
			casa.pintar_casa(entorno);
		}

		// pinta la unica tienda con ayuda del metodo pintar tienda
		tiendas[0].pintar_tienda(entorno);
	}

	// ---------- Getters ----------

	// retorna el array de objetos casas
	public Casa[] retornar_casas() {
		return casas;
	}

	// retorna el array de objetos tiendas
	public Tiendas[] retornar_tiendas() {
		return tiendas;
	}
}

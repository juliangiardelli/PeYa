package pedidos;

import java.util.Random;

import entorno.Entorno;
import mapa.Casa;
import mapa.Manzana;

public class Pedidos {

	// ---------- Variables de instancia ----------

	private Random random;

	// el pedido activo (null si no hay ninguno)
	private Encargo actual;

	// contadores
	private int entregados;
	private int perdidos;

	// tiempo que tiene el repartidor para completar cada pedido
	private int segundosLimite;

	// ---------- Constructor ----------

	public Pedidos(int segundosLimite) {
		this.random = new Random();
		this.segundosLimite = segundosLimite;
		this.entregados = 0;
		this.perdidos = 0;
	}

	// ---------- Metodos publicos ----------

	// avanza el pedido actual; devuelve true si en este tick se entrego uno
	public boolean actualizar(Entorno entorno, Manzana[] manzanas, double repX, double repY) {
		if (this.actual == null) {
			this.generarPedido(manzanas);
		}

		this.actual.actualizar();
		this.actual.intentarRetirar(entorno, repX, repY);
		this.actual.intentarEntregar(entorno, repX, repY);

		if (this.actual.termino()) {
			boolean seEntrego = this.actual.getEstado().equals("Entregado");
			if (seEntrego) {
				this.entregados++;
			} else {
				this.perdidos++;
			}
			// el pedido terminado se elimina; en el proximo tick se crea otro
			this.actual = null;
			return seEntrego;
		}
		return false;
	}

	// un enemigo choco al repartidor
	public void robar() {
		if (this.actual != null) {
			this.actual.robar();
		}
	}

	public void dibujar(Entorno entorno) {
		if (this.actual != null) {
			this.actual.dibujar(entorno);
		}
	}

	// ---------- Metodos privados ----------

	// elige una manzana al azar que no sea null
	private Manzana elegirManzana(Manzana[] manzanas) {
		Manzana m = null;
		while (m == null) {
			m = manzanas[this.random.nextInt(manzanas.length)];
		}
		return m;
	}

	// una casa al azar le hace un pedido a un negocio al azar
	private void generarPedido(Manzana[] manzanas) {
		Manzana manzanaCasa = this.elegirManzana(manzanas);
		Casa[] casas = manzanaCasa.retornar_casas();
		Casa casa = casas[this.random.nextInt(casas.length)];

		Manzana manzanaNegocio = this.elegirManzana(manzanas);

		this.actual = new Encargo(casa.cordenadas(),
				manzanaNegocio.retornar_tiendas()[0].retornar_ubicaciones(),
				this.segundosLimite);
	}

	// ---------- Getters ----------

	// texto para el panel, por ejemplo "En camino (25s)"
	public String getEstado() {
		if (this.actual == null) {
			return "Sin pedido";
		}
		String estado = this.actual.getEstado();
		if (estado.equals("Esperando") || estado.equals("En camino")) {
			return estado + " (" + this.actual.getSegundosRestantes() + "s)";
		}
		return estado;
	}

	public int getEntregados() {
		return this.entregados;
	}

	public int getPerdidos() {
		return this.perdidos;
	}
}

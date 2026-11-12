package juego;

import java.util.Random;

import disparo.Disparo;
import enemigos.Bomba;
import enemigos.Enemigo;
import entorno.Entorno;
import entorno.InterfaceJuego;
import gasolinera.Gasolinera;
import mapa.Casa;
import mapa.Manzana;
import mapa.Mapa;
import mapa.Tiendas;
import mapa.Turbo;
import pedidos.Pedidos;
import protagonista.Detector_colisiones;
import protagonista.PersonajePrincipalDefinido;

public class Juego extends InterfaceJuego {

	// ---------- Variables de instancia ----------

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Random random;

	// pantallas y panel de informacion
	private Pantallas pantallas;
	private Panel panel;

	// objetos del juego
	private PersonajePrincipalDefinido personaje;
	private Mapa Mapa;
	private Gasolinera gasolinera;
	private Pedidos pedidos;
	private Enemigo[] enemigos;
	private Bomba[] bombas;
	private Turbo turbo;

	// contadores
	private int enemigosEliminados;
	private int ticksSpawnTurbo = 0;

	// momento en que empezo la partida 
	private long tiempoInicio;

	// estados del juego: si alguno es true, el juego se congela
	private boolean gano;
	private boolean perdio;

	// true mientras se muestra la pantalla de inicio
	private boolean enInicio;

	// ---------- Constructor ----------

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "PeYA", 800, 600);
		this.random = new Random();

		this.pantallas = new Pantallas();
		this.panel = new Panel();
		this.enInicio = true;

		this.inicializarPartida();

		// Inicia el juego
		this.entorno.iniciar();
	}

	// ---------- Metodos publicos ----------

	public void tick() {

		// pantalla de inicio: espera Enter para empezar
		if (this.enInicio) {
			this.pantallas.dibujarInicio(this.entorno);
			if (this.entorno.sePresiono(this.entorno.TECLA_ENTER)) {
				this.enInicio = false;
				this.tiempoInicio = System.currentTimeMillis();
			}
			return;
		}

		// pantalla final: Enter reinicia, Esc cierra el juego
		if (this.gano || this.perdio) {
			this.pantallas.dibujarFinal(this.entorno, this.gano, this.enemigosEliminados);
			if (this.entorno.sePresiono(this.entorno.TECLA_ENTER)) {
				this.inicializarPartida();
			}
			if (this.entorno.sePresiono(this.entorno.TECLA_ESCAPE)) {
				System.exit(0);
			}
			return;
		}

		this.Mapa.pintarMapa(entorno);
		this.personaje.dibujar(entorno, this.Mapa);
		this.spawnTurbo();
		Disparo disparo = this.personaje.getDisparo();
		if (this.turbo != null && this.turbo.colisionaCon(
				this.personaje.getX(),
				this.personaje.getY(),
				this.personaje.getAncho(),
				this.personaje.getAlto())) {
			this.personaje.setTurboActivo(true);
			this.turbo = null;
		}
		if (disparo != null) {
			this.colisionDisparoManzana(disparo);
		}

		// pedidos: avanza el estado, revisa el clic derecho y dibuja la flecha
		boolean entrego = this.pedidos.actualizar(this.entorno, this.Mapa.getManzanas(),
				this.personaje.getX(), this.personaje.getY());
		if (entrego) {
			this.personaje.sumar_dinero();
			this.personaje.habilitar_carga_de_nafta();
		}
		this.pedidos.dibujar(this.entorno);

		// actualiza y dibuja las bombas; si la explosion termino, la elimina
		for (int i = 0; i < this.bombas.length; i++) {
			if (this.bombas[i] != null) {
				this.bombas[i].actualizar();
				if (this.bombas[i].termino()) {
					this.bombas[i] = null;
				} else {
					this.bombas[i].dibujar(this.entorno);
				}
			}
		}
		this.chequearExplosiones();

		// mueve y dibuja cada enemigo, tira bombas y chequea colisiones
		for (int i = 0; i < this.enemigos.length; i++) {
			if (this.enemigos[i] != null) {
				this.enemigos[i].mover(this.entorno);
				this.enemigos[i].dibujar(this.entorno);

				Bomba nueva = this.enemigos[i].tirarBomba();
				if (nueva != null) {
					this.agregarBomba(nueva);
				}

				// si el disparo le pega, se eliminan los dos
				Disparo d = this.personaje.getDisparo();
				if (d != null && this.enemigos[i].colisionaCon(d.getX(), d.getY(), d.getDiametro(), d.getDiametro())) {
					this.enemigos[i] = null;
					this.personaje.eliminarDisparo();
					this.enemigosEliminados++;
				}

				// si choca al repartidor, el enemigo desaparece y el repartidor pierde una vida, ademas de que el pedido se pierde
				if (this.enemigos[i] != null && this.enemigos[i].colisionaCon(
						this.personaje.getX(),
						this.personaje.getY(),
						this.personaje.getAncho(),
						this.personaje.getAlto())) {
					this.enemigos[i] = null;
					this.personaje.perderVida();
					this.pedidos.robar();
				}
			}
		}

		// si algun lugar quedo vacio, crea un enemigo nuevo (siempre tiene que haber 4)
		for (int i = 0; i < this.enemigos.length; i++) {
			if (this.enemigos[i] == null) {
				this.enemigos[i] = this.crearEnemigoNuevo();
			}
		}

		// pinta la gasolinera en el mapa
		this.gasolinera.pintar_gasolinera();

		// panel de arriba (se dibuja al final para que quede encima de todo)
		int segundos = (int) ((System.currentTimeMillis() - this.tiempoInicio) / 1000);
		int vidas = this.personaje.getVidas();
		int dinero = this.personaje.print_dinero();
		int nafta = this.personaje.print_nafta();
		this.panel.dibujar(this.entorno, segundos, vidas, this.pedidos.getEntregados(), this.enemigosEliminados, this.pedidos.getEstado(), dinero, nafta);

		// derrota por quedarse sin vidas o si te quedaste sin nafta
		// la nafta se recarga en la gasolinera , para cargar nafta necesitas 5 pesos
		// la plata se consigue entregando pedidos , te dan 3 pesos por cada pedido entregado.
		// si tenes 3 pesos o mas , podes pasar por la gasolinera y cargar
		// en la parte superior tenes el contador de dinero y nafta
		// se puede cargar nafta una vez por pedido , osea , no podemos cargar nafta 2 veces seguidas
		// cargar nafta y entregar un pedido como minimo para volver a cargar nafta (aunque tenga efectivo)
		// lo hice asi para evitar que por cada tick dentro de la gasolinera cargue nafta.
		// derrota: sin vidas, sin nafta o 5 pedidos perdidos, si colisiona con bomba/explosión.
		if (this.personaje.getVidas() <= 0 || this.personaje.print_nafta() <= 0
				|| this.pedidos.getPerdidos() >= 5) {
			this.perdio = true;
		}

		// victoria: al llegar a 5 entregas
		if (this.pedidos.getEntregados() >= 5) {
			this.gano = true;
		}
	}

	// ---------- Metodos privados ----------

	// crea todos los objetos de una partida nueva (se usa al empezar y al reiniciar)
	private void inicializarPartida() {
		// crea la instancia de gasolinera donde se pasa por parametro la posicion x ,y junto con el entorno
		this.gasolinera = new Gasolinera(this.entorno, 750, 75);

		this.personaje = new PersonajePrincipalDefinido(gasolinera);
		this.Mapa = new Mapa(this.personaje);
		// cada pedido tiene 40 segundos para entregarse
		this.pedidos = new Pedidos(60);
		this.tiempoInicio = System.currentTimeMillis();

		// el turbo empieza en null y aparece 10 segundos después de que el personaje lo agarre
		this.turbo = null;
		this.ticksSpawnTurbo = 0;

		// crea 4 enemigos; el cuarto dato indica si tiran bombas
		this.enemigos = new Enemigo[4];
		this.enemigos[0] = new Enemigo(100, 180, true, true);
		this.enemigos[1] = new Enemigo(600, 450, true, false);
		this.enemigos[2] = new Enemigo(315, 300, false, true);
		this.enemigos[3] = new Enemigo(585, 100, false, false);

		// lugar para hasta 10 bombas a la vez
		this.bombas = new Bomba[10];

		this.enemigosEliminados = 0;
		this.gano = false;
		this.perdio = false;
	}

	// Spawn del turbo cada 10 segundos
	private void spawnTurbo() {
		if (this.turbo == null) {
			this.ticksSpawnTurbo++;

			if (this.ticksSpawnTurbo >= 600) {
				double[] callesHorizontales = { 180, 315, 450 };
				double[] callesVerticales = { 180, 315, 450, 585 };

				double x = callesVerticales[this.random.nextInt(callesVerticales.length)];
				double y = callesHorizontales[this.random.nextInt(callesHorizontales.length)];

				this.turbo = new Turbo(x, y);
				this.ticksSpawnTurbo = 0;
			}
		}

		if (this.turbo != null) {
			this.turbo.dibujarTurbo(this.entorno);
		}
	}

	// guarda la bomba en el primer lugar libre del arreglo
	private void agregarBomba(Bomba bomba) {
		for (int i = 0; i < this.bombas.length; i++) {
			if (this.bombas[i] == null) {
				this.bombas[i] = bomba;
				return;
			}
		}
	}

	// si una bomba o su explosion toca al repartidor, pierde el juego (lo pide la consigna)
	private void chequearExplosiones() {
		for (int i = 0; i < this.bombas.length; i++) {
			if (this.bombas[i] != null && this.bombas[i].lastima(
					this.personaje.getX(),
					this.personaje.getY(),
					this.personaje.getAncho(),
					this.personaje.getAlto())) {
				this.perdio = true;
			}
		}
	}

	// revisa si el disparo del repartidor choca con alguna casa o tienda de las manzanas
	private void colisionDisparoManzana(Disparo disparo) {
		Manzana[] manzanas = this.Mapa.getManzanas();

		for (int i = 0; i < manzanas.length; i++) {
			if (manzanas[i] != null) {

				for (Casa casa : manzanas[i].retornar_casas()) {
					double[] coordenadas = casa.cordenadas();

					if (Detector_colisiones.hay_colision(
							disparo.getX(),
							disparo.getY(),
							disparo.getDiametro(),
							disparo.getDiametro(),
							coordenadas[0],
							coordenadas[1],
							coordenadas[2],
							coordenadas[3])) {

						this.personaje.eliminarDisparo();
						return;
					}
				}

				for (Tiendas tienda : manzanas[i].retornar_tiendas()) {
					double[] coordenadas = tienda.retornar_ubicaciones();

					if (Detector_colisiones.hay_colision(
							disparo.getX(),
							disparo.getY(),
							disparo.getDiametro(),
							disparo.getDiametro(),
							coordenadas[0],
							coordenadas[1],
							coordenadas[2],
							coordenadas[3])) {

						this.personaje.eliminarDisparo();
						return;
					}
				}
			}
		}
	}

	// crea un enemigo en el borde de una calle elegida al azar
	private Enemigo crearEnemigoNuevo() {
		double[] callesHorizontales = { 180, 315, 450 };
		double[] callesVerticales = { 180, 315, 450, 585 };

		boolean horizontal = this.random.nextBoolean();
		boolean tiraBombas = this.random.nextBoolean();

		if (horizontal) {
			double y = callesHorizontales[this.random.nextInt(callesHorizontales.length)];
			return new Enemigo(15, y, true, tiraBombas);
		} else {
			double x = callesVerticales[this.random.nextInt(callesVerticales.length)];
			return new Enemigo(x, 45, false, tiraBombas);
		}
	}

	// ---------- Main ----------

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}

package protagonista;

public class Detector_colisiones {

	// ---------- Constructor ----------

	public Detector_colisiones() {
	}

	// ---------- Metodos publicos ----------

	public static boolean hay_colision(
			double x,
			double y,
			double ancho,
			double alto,

			double obj_x,
			double obj_y,
			double obj_ancho,
			double obj_alto) {

		if (x - ancho / 2 < obj_x + obj_ancho / 2
				&& x + ancho / 2 > obj_x - obj_ancho / 2
				&& y - alto / 2 < obj_y + obj_alto / 2
				&& y + alto / 2 > obj_y - obj_alto / 2) {
			return true;
		}

		return false;
	}
}

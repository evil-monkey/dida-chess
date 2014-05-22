package ajedrez.creacion;

import ajedrez.Movimiento;
import ajedrez.Posicion;
import ajedrez.Tablero;
import ajedrez.trebejos.Trebejo;

public class FabricaDeMovimientos implements MovimientoFactory {

	public Movimiento crearMovimiento(Trebejo trebejo, Posicion destino,
			Tablero tablero) {
		return new Movimiento(trebejo, destino, tablero);
	}

}

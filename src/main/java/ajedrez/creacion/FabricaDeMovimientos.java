package ajedrez.creacion;

import ajedrez.Movimiento;
import ajedrez.Posicion;
import ajedrez.Tablero;
import ajedrez.trebejos.Trebejo;

public class FabricaDeMovimientos implements MovimientoFactory {

	@Override
	public Movimiento crearMovimiento(Trebejo trebejo, Posicion posicion,
			Tablero tablero) {
		return new Movimiento(trebejo, posicion, tablero);
	}

}

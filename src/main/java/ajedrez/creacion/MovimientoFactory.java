package ajedrez.creacion;

import ajedrez.Movimiento;
import ajedrez.Posicion;
import ajedrez.Tablero;
import ajedrez.trebejos.Trebejo;

public interface MovimientoFactory {
	Movimiento crearMovimiento(Trebejo trebejo, Posicion destino,
			Tablero tablero);
}

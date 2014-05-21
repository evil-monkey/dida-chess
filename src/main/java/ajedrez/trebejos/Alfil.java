package ajedrez.trebejos;

import ajedrez.Movimiento;
import ajedrez.Posicion;

public class Alfil extends Trebejo {

	public Alfil(Posicion posicion, Boolean blanca) {
		super(posicion, blanca);
	}

	@Override
	protected Boolean esMovimientoValido(Movimiento movimiento) {
		return movimiento.getDestino() != movimiento.getTrebejo().getPosicion()
				&& movimiento.getDeltaH() == movimiento.getDeltaV();
	}
	
	protected Boolean amenazoEsta(Movimiento movimiento) {
		Movimiento amenaza = new Movimiento(this, movimiento.getDestino(), movimiento.getTablero());
		return (esMovimientoValido(amenaza) && amenaza.nadieEnElMedio());
	}
}

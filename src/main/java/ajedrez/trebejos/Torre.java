package ajedrez.trebejos;

import ajedrez.Movimiento;
import ajedrez.Posicion;

public class Torre extends Trebejo {

	public Torre(Posicion posicion, Boolean blanca) {
		super(posicion, blanca);
		this.enroca = true;
	}

	@Override
	protected Boolean esMovimientoValido(Movimiento movimiento) {
		return movimiento.getDestino() != movimiento.getTrebejo().getPosicion()
				&& movimiento.getDeltaH() * movimiento.getDeltaV() == 0;
	}

	@Override
	protected Boolean amenazoEsta(Movimiento movimiento) {
		Movimiento amenaza = new Movimiento(this, movimiento.getDestino());
		return (esMovimientoValido(amenaza) && amenaza.nadieEnElMedio());
	}

}

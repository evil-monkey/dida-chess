package ajedrez.trebejos;

import ajedrez.Movimiento;
import ajedrez.Posicion;
import ajedrez.excepciones.MovimientoNoPermitido;

public class Reina extends Trebejo {

	public Reina(Posicion posicion, Boolean blanca) {
		super(posicion, blanca);
	}

	@Override
	protected Boolean esMovimientoValido(Movimiento movimiento) {
		return movimiento.getDestino() != movimiento.getTrebejo().getPosicion()
				&& movimiento.getDeltaH() * movimiento.getDeltaV() == 0
				|| movimiento.getDestino() != movimiento.getTrebejo()
						.getPosicion()
				&& movimiento.getDeltaH() == movimiento.getDeltaV();
	}

	@Override
	protected Boolean amenazoEsta(Movimiento movimiento) {
		Movimiento amenaza = new Movimiento(this, movimiento.getDestino());
		return (esMovimientoValido(amenaza) && amenaza.nadieEnElMedio());
	}

	@Override
	protected void checkImpedimentos(Movimiento movimiento)
			throws MovimientoNoPermitido {
		if (movimiento.getOcupado() || movimiento.getBloqueado()) {
			throw new MovimientoNoPermitido();
		}

	}
}

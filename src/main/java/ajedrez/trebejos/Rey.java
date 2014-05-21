package ajedrez.trebejos;

import ajedrez.Movimiento;
import ajedrez.Posicion;

public class Rey extends Trebejo {

	public Rey(Posicion posicion, Boolean blanca) {
		super(posicion, blanca);
	}

	@Override
	protected Boolean esMovimientoValido(Movimiento movimiento) {
		return esMovimientoNormal(movimiento) || checkEnroque(movimiento);
	}

	private boolean esMovimientoNormal(Movimiento movimiento) {
		return (movimiento.getDestino() != movimiento.getTrebejo()
				.getPosicion()
				&& movimiento.getDeltaH() * movimiento.getDeltaV() == 0 && movimiento
				.getDeltaH() + movimiento.getDeltaV() == 1) // 1 h o 1 v
				|| (movimiento.getDestino() != movimiento.getTrebejo()
						.getPosicion()
						&& movimiento.getDeltaH() == movimiento.getDeltaV() && movimiento
						.getDeltaH() == 1);
	}

	private Boolean checkEnroque(Movimiento movimiento) {
		return (movimiento.getTrebejo().getCeroKm()
				&& movimiento.getDeltaH() == 2 && movimiento.getDeltaV() == 0 && movimiento
				.getTablero().getEnrocable(movimiento));
	}

	@Override
	protected Boolean amenazoEsta(Movimiento movimiento) {
		Movimiento amenaza = new Movimiento(this, movimiento.getDestino());
		return esMovimientoNormal(amenaza);
	}

}

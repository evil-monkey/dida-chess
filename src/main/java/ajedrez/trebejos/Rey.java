package ajedrez.trebejos;

import ajedrez.Movimiento;
import ajedrez.Posicion;

public class Rey extends Trebejo {

	public Rey(Posicion posicion, Boolean blanca) {
		super(posicion, blanca);
	}

	@Override
	protected Boolean esMovimientoValido(Movimiento movimiento) {
		Boolean result = esMovimientoNormal(movimiento);
		if(!result && checkEnroque(movimiento)) {
			movimiento.setEnrocado(getTorreCandidata(movimiento));
			result = true;
		}
		return result;
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

	private boolean checkEnroque(Movimiento movimiento) {

		boolean enroque = false;

		Trebejo candidata = getTorreCandidata(movimiento);

		if (candidata != null && candidata.getEnroca()
				&& candidata.getBlanca().equals(blanca)) {

			enroque = checkCaminoEnroque(movimiento, candidata);
		}

		return enroque;
	}

	private boolean checkCaminoEnroque(Movimiento movimiento, Trebejo candidata) {
		boolean enroque;
		int diff = Math.abs(candidata.getPosicion().getH()
				- movimiento.getTrebejo().getPosicion().getH());

		Boolean libre = true;
		// chequeo
		while (libre && diff > 1) {
			Posicion nuevaPosicion = new Posicion(
					(byte) (movimiento.getTrebejo().getPosicion().getH() + movimiento
							.getSentidoH()), candidata.getPosicion().getV());

			diff = Math.abs(candidata.getPosicion().getH()
					- nuevaPosicion.getH());

			Trebejo bloqueante = movimiento.getTablero().getTrebejoEn(
					nuevaPosicion);

			if (bloqueante != null) {
				libre = false;
			}
		}

		enroque = libre;
		return enroque;
	}

	private Trebejo getTorreCandidata(Movimiento movimiento) {
		Posicion candidataTorre = new Posicion(
				(byte) (movimiento.getSentidoH() > 0 ? 8 : 1), movimiento
						.getTrebejo().getPosicion().getV());

		return movimiento.getTablero().getTrebejoEn(candidataTorre);
	}

	@Override
	protected Boolean amenazoEsta(Movimiento movimiento) {
		Movimiento amenaza = new Movimiento(this, movimiento.getDestino());
		return esMovimientoNormal(amenaza);
	}

}

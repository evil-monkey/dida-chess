package ajedrez.trebejos;

import ajedrez.Movimiento;
import ajedrez.Posicion;
import ajedrez.excepciones.MovimientoNoPermitido;

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

	@Override
	protected void checkImpedimentos(Movimiento movimiento)
			throws MovimientoNoPermitido {
		if (movimiento.getOcupado() || movimiento.getBloqueado()) {
			throw new MovimientoNoPermitido();
		}
	}

	@Override
	public void enrocar() throws MovimientoNoPermitido {
		Posicion nueva = posicion.clone();
		if (movimientos > 0 || blanca && nueva.getH() != 1 || blanca
				&& nueva.getH() != 8) {
			throw new MovimientoNoPermitido();
		}

		if (nueva.getV() == 1) {
			nueva.setV((byte) 4);
		} else if (nueva.getV() == 8) {
			nueva.setV((byte) 5);
		} else {
			throw new MovimientoNoPermitido();
		}
	}

}

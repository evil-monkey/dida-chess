package ajedrez.trebejos;

import ajedrez.Movimiento;
import ajedrez.Posicion;
import ajedrez.excepciones.MovimientoNoPermitido;

public class Caballo extends Trebejo {

	public Caballo(Posicion posicion, Boolean blanca) {
		super(posicion, blanca);
	}

	@Override
	protected Boolean esMovimientoValido(Movimiento movimiento) {
		return Math.abs(movimiento.getDeltaH() * movimiento.getDeltaV()) == 2;
	}

	@Override
	protected Boolean amenazoEsta(Movimiento movimiento) {
		// como lo quiero al caballo, es el único que me permite amenzar sin fijarme
		// si hay alguien en el medio
		Movimiento amenaza = new Movimiento(this, movimiento.getDestino());
		return esMovimientoValido(amenaza);
	}

	@Override
	protected void checkImpedimentos(Movimiento movimiento) throws MovimientoNoPermitido {
		if(movimiento.getOcupado()) {
			throw new MovimientoNoPermitido();
		}
		
	}
}

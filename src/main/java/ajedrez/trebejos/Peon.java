package ajedrez.trebejos;

import ajedrez.Movimiento;
import ajedrez.Posicion;

public class Peon extends Trebejo {

	public Peon(Posicion posicion, Boolean blanca) {
		super(posicion, blanca);
		this.pasable = true;
	}

	@Override
	protected Boolean esMovimientoValido(Movimiento movimiento) {
		int factor = blanca ? 1 : -1;
		Boolean result = movimiento.getDeltaH() == 0
				&& (factor * movimiento.getDeltaV() == 1 || ceroKM
						&& factor * movimiento.getDeltaV() == 2);

		if (!result) {
			if (checkCaptura(movimiento)) {
				result = true;
			}
		}
		return result;
	}

	private boolean checkCaptura(Movimiento movimiento) {
		return checkCapturaNormal(movimiento);
	}

	private boolean checkCapturaNormal(Movimiento movimiento) {
		int factor = blanca ? 1 : -1;
		Trebejo enDestino = movimiento.getTablero()
				.getTrebejoEn(movimiento.getDestino());
		return movimiento.getDeltaH() == 1
				&& (factor * movimiento.getSentidoV() * movimiento.getDeltaV() == 1)
				&& enDestino != null && enDestino.getBlanca()
						.equals(movimiento.getTrebejo().getBlanca());
	}

	@Override
	protected Boolean amenazoEsta(Movimiento movimiento) {
		int factor = blanca ? 1 : -1;
		Boolean result = movimiento.getDestino().getV() == this.posicion.getV()
				+ factor
				&& (movimiento.getDestino().getH() == this.posicion.getH() - 1 || posicion
						.getH() == this.posicion.getH() + 1);
		return result || checkCapturaAlPaso(movimiento);
	}

	private boolean checkCapturaAlPaso(Movimiento movimiento) {
		int factor = blanca ? 1 : -1;
		Posicion alPaso = movimiento.getDestino().clone();
		alPaso.setV((byte) (alPaso.getV() - movimiento.getSentidoV()));
		Trebejo trebejoAlPaso = movimiento.getTablero()
				.getTrebejoEn(alPaso);
		return movimiento.getDeltaH() == 1
				&& (factor * movimiento.getSentidoV() * movimiento.getDeltaV() == 1)
				&& trebejoAlPaso != null && trebejoAlPaso.getBlanca()
						.equals(movimiento.getTrebejo().getBlanca());
	}

}

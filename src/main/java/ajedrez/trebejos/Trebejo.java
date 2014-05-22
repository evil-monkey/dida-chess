package ajedrez.trebejos;

import ajedrez.Movimiento;
import ajedrez.Posicion;
import ajedrez.excepciones.MovimientoNoPermitido;

public abstract class Trebejo {

	protected Posicion posicion;
	protected Boolean blanca;
	protected Boolean enroca;
	protected Boolean pasable;
	protected Byte movimientos;

	/**
	 * 
	 * @param movimiento
	 * @return
	 */
	protected abstract Boolean esMovimientoValido(Movimiento movimiento);

	/**
	 * 
	 * @param movimiento
	 * @throws MovimientoNoPermitido 
	 */
	protected abstract void checkImpedimentos(Movimiento movimiento) throws MovimientoNoPermitido;

	/*
	 * dejo esta implementación por default ya que sólo los peones pueden
	 * coronarse, en el caso del peón habra un override de este método
	 */
	protected boolean puedoCoronarme() {
		return false;
	}

	/**
	 * 
	 * @return una lista de posiciones amenazadas
	 */
	protected abstract Boolean amenazoEsta(Movimiento movimiento);

	public Trebejo(Posicion posicion, Boolean blanca) {
		this.posicion = posicion;
		this.blanca = blanca;
		this.movimientos = 0;
		this.enroca = false;
		this.pasable = false;
	}

	public void mover(Movimiento movimiento) throws MovimientoNoPermitido {

		/* template method nomás */
		if (!esMovimientoValido(movimiento)) {
			throw new MovimientoNoPermitido();
		}

		// agrego información sobre el contexto
		movimiento.getTablero().checkContexto(movimiento);
		// verifico esa info según qué trebejo es
		checkImpedimentos(movimiento);

		// muevo
		posicion = movimiento.getDestino();

		// va derecho, todas las validaciones fueron previas
		movimiento.setCaptura(getCaptura(movimiento));

		if (puedoCoronarme()) {
			movimiento.setPedirCoronar(true);
		}

		movimientos++;

	}

	public void bloqueoOAmenazo(Movimiento movimiento) {

		// voy de < a > esfuerzo

		// si es el mismo omito
		if (movimiento.getTrebejo().equals(this)) {
			return;
		}

		// si es hay uno del mismo bando ocupando el lugar
		if (movimiento.getTrebejo().getBlanca().equals(blanca)
				&& movimiento.getDestino().equals(this.posicion)) {
			movimiento.setOcupado();
		}

		// chequeo si este esta bloqueando el movimiento
		if (movimiento.getImpedimentos() < 1
				&& movimiento.enCamino(this.posicion)) {
			movimiento.setBloqueado();
		}

		// chequeo si está amenazado (en principio para todos, es lo más
		// costoso pero es el grado 1 de hints
		if (movimiento.getImpedimentos() < 1
				&& !movimiento.getTrebejo().getBlanca().equals(blanca)
				&& amenazoEsta(movimiento)) {
			movimiento.setAmenazado();
		}
	}

	protected Trebejo getCaptura(Movimiento movimiento) {
		return movimiento.getTablero().getTrebejoEn(movimiento.getDestino());
	}

	public Posicion getPosicion() {
		return posicion;
	}
	
	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	public Boolean getBlanca() {
		return blanca;
	}

	public Byte getMovimientos() {
		return movimientos;
	}

	public Boolean getEnroca() {
		return enroca;
	}

	public void setEnroca(Boolean enroca) {
		this.enroca = enroca;
	}

	public Boolean getPasable() {
		return pasable;
	}

	public void setPasable(Boolean pasable) {
		this.pasable = pasable;
	}

	public void enrocar() throws MovimientoNoPermitido {
		throw new MovimientoNoPermitido();
	}

}

package ajedrez;

import java.util.LinkedList;

import ajedrez.trebejos.Trebejo;

public class Movimiento {

	private Posicion destino;
	private Trebejo trebejo;
	/* los voy a usar para movimientos secundarios */
	private Trebejo enrocado;
	private Trebejo pasado;

	private Byte impedimentos;
	private LinkedList<Posicion> camino;

	private Integer deltaH;
	private Integer deltaV;
	private Integer sentidoH;
	private Integer sentidoV;
	private Tablero tablero;

	public Movimiento(Trebejo trebejo, Posicion destino) {
		this.trebejo = trebejo;
		this.destino = destino;
		this.tablero = null;
		this.setPasado(null);
		impedimentos = 0;
	}

	public Movimiento(Trebejo trebejo, Posicion destino, Tablero tablero) {
		this.trebejo = trebejo;
		this.destino = destino;
		this.setTablero(tablero);
		impedimentos = 0;
	}

	public Posicion getDestino() {
		return destino;
	}

	public void setDestino(Posicion destino) {
		this.destino = destino;
	}

	public Trebejo getTrebejo() {
		return trebejo;
	}

	public void setTrebejo(Trebejo trebejo) {
		this.trebejo = trebejo;
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public Byte getImpedimentos() {
		return impedimentos;
	}

	public void setImpedimentos(Byte impedimentos) {
		this.impedimentos = impedimentos;
	}

	public LinkedList<Posicion> getCamino() {
		if(camino == null) {
			haceCamino();
		}
		return camino;
	}

	public void setCamino(LinkedList<Posicion> camino) {
		this.camino = camino;
	}

	public Trebejo getEnrocado() {
		return enrocado;
	}

	public void setEnrocado(Trebejo enrocado) {
		this.enrocado = enrocado;
	}

	public Trebejo getPasado() {
		return pasado;
	}

	public void setPasado(Trebejo pasado) {
		this.pasado = pasado;
	}

	public Integer getDeltaH() {
		if (deltaH == null) {
			deltaH = Math.abs(getDestino().getH()
					- getTrebejo().getPosicion().getH());
		}
		return deltaH;
	}

	public void setDeltaH(Integer deltaH) {
		this.deltaH = deltaH;
	}

	public Integer getDeltaV() {
		if (deltaV == null) {
			deltaV = Math.abs(getDestino().getV()
					- getTrebejo().getPosicion().getV());
		}
		return deltaV;
	}

	public void setDeltaV(Integer deltaV) {
		this.deltaV = deltaV;
	}

	public Integer getSentidoH() {
		if (sentidoH == null) {
			if (getDeltaH() != 0) {
				sentidoH = getDestino().getH()
						- getTrebejo().getPosicion().getH() / getDeltaH();
			} else {
				sentidoH = 0;
			}
		}
		return sentidoH;
	}

	public void setSentidoH(Integer sentidoH) {
		this.sentidoH = sentidoH;
	}

	public Integer getSentidoV() {
		if (sentidoV == null) {
			if (getDeltaV() != 0) {
				sentidoV = getDestino().getV()
						- getTrebejo().getPosicion().getV() / getDeltaV();
			} else {
				sentidoV = 0;
			}
		}
		return sentidoV;
	}

	public void setSentidoV(Integer sentidoV) {
		this.sentidoV = sentidoV;
	}

	public void setAmenazado() {
		setImpedimentos((byte) (getImpedimentos() | 1));
	}

	public void setBloqueado() {
		setImpedimentos((byte) (getImpedimentos() | 2));
	}

	public void setOcupado() {
		setImpedimentos((byte) (getImpedimentos() | 4));
	}

	public void setNoValido() {
		setImpedimentos((byte) (getImpedimentos() | 8));
	}

	protected void haceCamino() {
		
		this.camino = new LinkedList<Posicion>();
		// me muevo deltaH posiciones horizontalmente
		for (int i = 1; i < this.getDeltaH(); i++) {
			this.getCamino().push(
					new Posicion((byte) (trebejo.getPosicion().getH() + this
							.getSentidoH()), trebejo.getPosicion().getV()));
		}

		// me muevo deltaV posiciones verticalmente
		for (int i = 1; i < this.getDeltaV(); i++) {
			this.getCamino().push(
					new Posicion(trebejo.getPosicion().getH(), (byte) (trebejo
							.getPosicion().getV() + this.getSentidoV())));
		}
	}

	public boolean enCamino(Posicion posicion) {
		return getCamino().contains(posicion);
	}

	public boolean nadieEnElMedio() {
		return tablero.elCaminoEstaLibre(this);
	}

}
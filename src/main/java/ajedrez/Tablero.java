package ajedrez;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import ajedrez.creacion.MovimientoFactory;
import ajedrez.trebejos.Trebejo;
import ajedrez.trebejos.creacion.FabricaDeAlfiles;
import ajedrez.trebejos.creacion.FabricaDeCaballos;
import ajedrez.trebejos.creacion.FabricaDeReinas;
import ajedrez.trebejos.creacion.FabricaDeReyes;
import ajedrez.trebejos.creacion.TrebejoFactory;

public class Tablero {

	private Collection<Trebejo> blancas;
	private Collection<Trebejo> negras;

	/*
	 * bajas
	 */
	private Collection<Trebejo> limboBlancas;
	private Collection<Trebejo> limboNegras;

	/*
	 * Factories
	 */
	private TrebejoFactory fabricaDePeones;
	private TrebejoFactory fabricaDeTorres;
	private TrebejoFactory fabricaDeCaballos;
	private TrebejoFactory fabricaDeAlfiles;
	private TrebejoFactory fabricaDeReinas;
	private TrebejoFactory fabricaDeReyes;

	private LinkedList<Movimiento> movimientosBlancas;

	private LinkedList<Movimiento> movimientosNegras;

	private MovimientoFactory fabricaDeMovimientos;

	public Tablero() {
		this.blancas = new ArrayList<Trebejo>(16);
		this.negras = new ArrayList<Trebejo>(16);
		this.movimientosBlancas = new LinkedList<Movimiento>();
		this.movimientosNegras = new LinkedList<Movimiento>();
	}

	public void coronar(Trebejo trebejo) {
		// TODO Auto-generated method stub

	}

	public void init() {
		initBlancas();
		initNegras();
	}

	private void initNegras() {

		negras.addAll(fabricaDePeones.crearCompania((byte) 7, false));

		negras.addAll(fabricaDeTorres.crearCompania((byte) 8, false));

		negras.addAll(fabricaDeCaballos.crearCompania((byte) 8, false));

		negras.addAll(fabricaDeAlfiles.crearCompania((byte) 8, false));

		negras.addAll(fabricaDeReinas.crearCompania((byte) 8, false));

		negras.addAll(fabricaDeReyes.crearCompania((byte) 8, false));

	}

	private void initBlancas() {

		blancas.addAll(fabricaDePeones.crearCompania((byte) 2, true));

		blancas.addAll(fabricaDeTorres.crearCompania((byte) 1, true));

		blancas.addAll(fabricaDeCaballos.crearCompania((byte) 1, true));

		blancas.addAll(fabricaDeAlfiles.crearCompania((byte) 1, true));

		blancas.addAll(fabricaDeReinas.crearCompania((byte) 1, true));

		blancas.addAll(fabricaDeReyes.crearCompania((byte) 1, true));
	}

	public Collection<Trebejo> getBlancas() {
		return blancas;
	}

	public Collection<Trebejo> getNegras() {
		return negras;
	}

	public Collection<Trebejo> getLimboBlancas() {
		return limboBlancas;
	}

	public Collection<Trebejo> getLimboNegras() {
		return limboNegras;
	}

	public void setFabricaDePeones(TrebejoFactory fabrica) {
		this.fabricaDePeones = fabrica;
	}

	public void setFabricaDeTorres(TrebejoFactory fabricaDeTorres) {
		this.fabricaDeTorres = fabricaDeTorres;
	}

	public void setFabricaDeCaballos(FabricaDeCaballos fabricaDeCaballos) {
		this.fabricaDeCaballos = fabricaDeCaballos;

	}

	public void setFabricaDeAlfiles(FabricaDeAlfiles fabricaDeAlfiles) {
		this.fabricaDeAlfiles = fabricaDeAlfiles;

	}

	public void setFabricaDeReinas(FabricaDeReinas fabricaDeReinas) {
		this.fabricaDeReinas = fabricaDeReinas;

	}

	public void setFabricaDeReyes(FabricaDeReyes fabricaDeReyes) {
		this.fabricaDeReyes = fabricaDeReyes;

	}

	public void setFabricaDeMovimientos(MovimientoFactory fabricaDeMovimientos) {
		this.fabricaDeMovimientos = fabricaDeMovimientos;
	}

	public LinkedList<Movimiento> getMovimientosBlancas() {
		return movimientosBlancas;
	}

	public LinkedList<Movimiento> getMovimientosNegras() {
		return movimientosNegras;
	}

	public Boolean sePuedeMover(Movimiento movimiento) {

		for (Trebejo otro : blancas) {
			otro.bloqueoUAmenazo(movimiento);
		}

		for (Trebejo otro : negras) {
			otro.bloqueoUAmenazo(movimiento);
		}

		return true;
	}

	public boolean getEnrocable(Movimiento movimiento) {

		Boolean enrocable = false;

		Posicion candidataTorre = new Posicion(
				(byte) (movimiento.getSentidoH() > 0 ? 8 : 1), movimiento
						.getTrebejo().getPosicion().getV());

		int diff = Math.abs(candidataTorre.getH()
				- movimiento.getTrebejo().getPosicion().getH());

		while (!enrocable && diff > 0) {
			Posicion nuevaPosicion = new Posicion(
					(byte) (movimiento.getTrebejo().getPosicion().getH() + movimiento
							.getSentidoH()), candidataTorre.getV());

			diff = Math.abs(candidataTorre.getH() - nuevaPosicion.getH());

			Trebejo okupa = getTrebejoEn(nuevaPosicion);

			if (okupa != null
					&& movimiento.getTrebejo().getBlanca()
							.equals(okupa.getBlanca()) && okupa.getEnroca()
					&& okupa.getCeroKm()) {
				enrocable = true;
			}
		}

		return enrocable;
	}

	public Trebejo getTrebejoEn(Posicion lugar) {
		Trebejo trebejo = null;

		for (Trebejo candidato : blancas) {
			if (trebejo == null && candidato.getPosicion().equals(lugar)) {
				trebejo = candidato;
			}
		}
		if (trebejo == null) {
			for (Trebejo candidato : negras) {
				if (trebejo == null && candidato.getPosicion().equals(lugar)) {
					trebejo = candidato;
				}
			}
		}

		return trebejo;
	}

	public boolean elCaminoEstaLibre(Movimiento movimiento) {
		boolean libre = true;
		for (Posicion posicion : movimiento.getCamino()) {
			libre &= getTrebejoEn(posicion) == null;
		}
		return libre;
	}
}

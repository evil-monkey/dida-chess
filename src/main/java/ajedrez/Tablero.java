package ajedrez;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import ajedrez.creacion.MovimientoFactory;
import ajedrez.excepciones.FueraDelTablero;
import ajedrez.excepciones.MovimientoNoPermitido;
import ajedrez.excepciones.NoEsSuTurno;
import ajedrez.excepciones.ReyExpuesto;
import ajedrez.excepciones.TrebejoNoValido;
import ajedrez.trebejos.Trebejo;
import ajedrez.trebejos.creacion.FabricaDeAlfiles;
import ajedrez.trebejos.creacion.FabricaDeCaballos;
import ajedrez.trebejos.creacion.FabricaDeReinas;
import ajedrez.trebejos.creacion.FabricaDeReyes;
import ajedrez.trebejos.creacion.TrebejoFactory;

public class Tablero {

	private Collection<Trebejo> trebejos;

	private Boolean mueveBlanco;

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

	private LinkedList<Movimiento> movimientos;

	private MovimientoFactory fabricaDeMovimientos;

	public Tablero() {
		this.trebejos = new ArrayList<Trebejo>(32);
		this.movimientos = new LinkedList<Movimiento>();
		this.mueveBlanco = true;
	}

	public void coronar(Trebejo trebejo) {
		// TODO Auto-generated method stub

	}

	public void init() {
		initBlancas();
		initNegras();
	}

	private void initNegras() {

		trebejos.addAll(fabricaDePeones.crearCompania((byte) 7, false));

		trebejos.addAll(fabricaDeTorres.crearCompania((byte) 8, false));

		trebejos.addAll(fabricaDeCaballos.crearCompania((byte) 8, false));

		trebejos.addAll(fabricaDeAlfiles.crearCompania((byte) 8, false));

		trebejos.addAll(fabricaDeReinas.crearCompania((byte) 8, false));

		trebejos.addAll(fabricaDeReyes.crearCompania((byte) 8, false));

	}

	private void initBlancas() {

		trebejos.addAll(fabricaDePeones.crearCompania((byte) 2, true));

		trebejos.addAll(fabricaDeTorres.crearCompania((byte) 1, true));

		trebejos.addAll(fabricaDeCaballos.crearCompania((byte) 1, true));

		trebejos.addAll(fabricaDeAlfiles.crearCompania((byte) 1, true));

		trebejos.addAll(fabricaDeReinas.crearCompania((byte) 1, true));

		trebejos.addAll(fabricaDeReyes.crearCompania((byte) 1, true));
	}

	private void checkBounds(Posicion posicion) throws FueraDelTablero {
		if (posicion == null || posicion.getH() > 8 || posicion.getH() < 1
				|| posicion.getV() > 8 || posicion.getV() < 1) {
			throw new FueraDelTablero();
		}
	}

	/**
	 * hace los cambios necesarios en las listas de trebejos y agrega el
	 * movimento a la lista
	 * 
	 * @param movimiento
	 * @throws MovimientoNoPermitido
	 * @throws ReyExpuesto 
	 */
	private void actualizarme(Movimiento movimiento)
			throws MovimientoNoPermitido, ReyExpuesto {

		plasmarCaptura(movimiento.getCaptura());

		plasmarEnroque(movimiento.getEnrocado());

		if (checkJaque(movimiento.getTrebejo().getBlanca())) {
			undo(movimiento);
			throw new ReyExpuesto();
		}
		
		movimientos.push(movimiento);
		
		checkJaque(!movimiento.getTrebejo().getBlanca());

	}

	private void undo(Movimiento movimiento) {
		
		if(movimiento.getCaptura() != null ) {
			if(movimiento.getTrebejo().getBlanca()) {
				limboBlancas.remove(movimiento.getCaptura());
			} else {
				limboNegras.remove(movimiento.getCaptura());
			}
			trebejos.add(movimiento.getCaptura());
		}
		
		movimiento.undo();
		
	}

	private boolean checkJaque(Boolean blanca) {
		// TODO Implementar
		return false;
	}

	private void plasmarEnroque(Trebejo enrocable) throws MovimientoNoPermitido {
		enrocable.enrocar();
	}

	private void plasmarCaptura(Trebejo capturado) {
		trebejos.remove(capturado);

		if (capturado.getBlanca()) {
			limboBlancas.add(capturado);
		} else {
			limboNegras.add(capturado);
		}

	}

	public void mover(Posicion desde, Posicion hasta)
			throws MovimientoNoPermitido, NoEsSuTurno, TrebejoNoValido,
			FueraDelTablero, ReyExpuesto {

		checkBounds(desde);
		checkBounds(hasta);

		Trebejo trebejo = getTrebejoEn(desde);

		if (trebejo == null) {
			throw new TrebejoNoValido();
		}

		if (mueveBlanco && !trebejo.getBlanca() || !mueveBlanco
				&& trebejo.getBlanca()) {
			throw new NoEsSuTurno();
		}

		Movimiento movimiento = fabricaDeMovimientos.crearMovimiento(trebejo,
				hasta, this);
		// el trebejo completa el movimiento
		trebejo.mover(movimiento);
		
		actualizarme(movimiento);

	}

	/**
	 * chequea un {@link Movimiento} contra todas las piezas en el tablero
	 * 
	 * @param movimiento
	 */
	public void checkContexto(Movimiento movimiento) {

		for (Trebejo otro : trebejos) {
			otro.bloqueoOAmenazo(movimiento);
		}

	}

	/**
	 * devuelve el trebejo en una posición especificada
	 * 
	 * @param lugar
	 * @return
	 */
	public Trebejo getTrebejoEn(Posicion lugar) {
		Trebejo trebejo = null;

		for (Trebejo candidato : trebejos) {
			if (trebejo == null && candidato.getPosicion().equals(lugar)) {
				trebejo = candidato;
			}
		}

		return trebejo;
	}

	/**
	 * chequea existencia de trebejos en posiciones intermedias de un movimiento
	 * 
	 * @param movimiento
	 * @return
	 */
	public boolean elCaminoEstaLibre(Movimiento movimiento) {
		boolean libre = true;
		for (Posicion posicion : movimiento.getCamino()) {
			libre &= getTrebejoEn(posicion) == null;
		}
		return libre;
	}

	public Collection<Trebejo> getTrebejos() {
		return trebejos;
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

	public LinkedList<Movimiento> getMovimientos() {
		return movimientos;
	}

	public Boolean getMueveBlanco() {
		return mueveBlanco;
	}

}

package ajedrez.trebejos.creacion;

import java.util.Collection;
import java.util.HashSet;

import ajedrez.Posicion;
import ajedrez.trebejos.Peon;
import ajedrez.trebejos.Trebejo;

public class FabricaDePeones implements TrebejoFactory {

	public Trebejo crear(Posicion posicion, Boolean blanca) {
		return new Peon(posicion, blanca);
	}

	public Collection<Trebejo> crearCompania(byte filaInicial, Boolean blanca) {
		Collection<Trebejo> peones = new HashSet<Trebejo>();

		for (byte i = 1; i <= 8; i++) {
			peones.add(crear(new Posicion(filaInicial, i), blanca));
		}

		return peones;
	}
}

package ajedrez.trebejos.creacion;

import java.util.Collection;
import java.util.HashSet;

import ajedrez.Posicion;
import ajedrez.trebejos.Torre;
import ajedrez.trebejos.Trebejo;

public class FabricaDeTorres implements TrebejoFactory {

	public Trebejo crear(Posicion posicion, Boolean blanca) {
		return new Torre(posicion, blanca);
	}

	public Collection<Trebejo> crearCompania(byte filaInicial, Boolean blanca) {
		Collection<Trebejo> torres = new HashSet<Trebejo>(2);
		torres.add(crear(new Posicion(filaInicial, (byte) 1), blanca));
		torres.add(crear(new Posicion(filaInicial, (byte) 8), blanca));
		return torres;
	}

}

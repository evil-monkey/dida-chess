package ajedrez.trebejos.creacion;

import java.util.Collection;
import java.util.HashSet;

import ajedrez.Posicion;
import ajedrez.trebejos.Alfil;
import ajedrez.trebejos.Trebejo;

public class FabricaDeAlfiles implements TrebejoFactory {

	public Trebejo crear(Posicion posicion, Boolean blanca) {
		return new Alfil(posicion, blanca);
	}

	public Collection<Trebejo> crearCompania(byte filaInicial, Boolean blanca) {
		Collection<Trebejo> alfiles = new HashSet<Trebejo>(2);
		alfiles.add(crear(new Posicion(filaInicial, (byte) 3), blanca));
		alfiles.add(crear(new Posicion(filaInicial, (byte) 6), blanca));
		return alfiles;
	}

}

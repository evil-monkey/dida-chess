package ajedrez.trebejos.creacion;

import java.util.Collection;
import java.util.HashSet;

import ajedrez.Posicion;
import ajedrez.trebejos.Caballo;
import ajedrez.trebejos.Trebejo;

public class FabricaDeCaballos implements TrebejoFactory {

	public Trebejo crear(Posicion posicion, Boolean blanca) {
		return new Caballo(posicion, blanca);
	}

	public Collection<Trebejo> crearCompania(byte filaInicial, Boolean blanca) {
		Collection<Trebejo> caballos = new HashSet<Trebejo>(2);
		caballos.add(crear(new Posicion(filaInicial, (byte) 2), blanca));
		caballos.add(crear(new Posicion(filaInicial, (byte) 7), blanca));
		return caballos;
	}

}

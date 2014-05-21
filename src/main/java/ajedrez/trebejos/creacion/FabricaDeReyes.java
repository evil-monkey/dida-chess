package ajedrez.trebejos.creacion;

import java.util.Collection;
import java.util.HashSet;

import ajedrez.Posicion;
import ajedrez.trebejos.Rey;
import ajedrez.trebejos.Trebejo;

public class FabricaDeReyes implements TrebejoFactory {

	public Trebejo crear(Posicion posicion, Boolean blanca) {
		return new Rey(posicion, blanca);
	}

	public Collection<Trebejo> crearCompania(byte filaInicial, Boolean blanca) {
		Collection<Trebejo> reyes = new HashSet<Trebejo>(2);

		reyes.add(crear(new Posicion(filaInicial, (byte) 5), blanca));

		return reyes;
	}

}

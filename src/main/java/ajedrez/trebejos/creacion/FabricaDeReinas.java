package ajedrez.trebejos.creacion;

import java.util.Collection;
import java.util.HashSet;

import ajedrez.Posicion;
import ajedrez.trebejos.Reina;
import ajedrez.trebejos.Trebejo;

public class FabricaDeReinas implements TrebejoFactory {

	public Trebejo crear(Posicion posicion, Boolean blanca) {
		return new Reina(posicion, blanca);
	}

	public Collection<Trebejo> crearCompania(byte filaInicial, Boolean blanca) {
		Collection<Trebejo> reinas = new HashSet<Trebejo>(2);

		reinas.add(crear(new Posicion(filaInicial, (byte) 4), blanca));

		return reinas;
	}

}

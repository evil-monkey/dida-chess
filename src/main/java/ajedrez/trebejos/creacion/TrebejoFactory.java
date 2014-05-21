package ajedrez.trebejos.creacion;

import java.util.Collection;

import ajedrez.Posicion;
import ajedrez.trebejos.Trebejo;

public interface TrebejoFactory {
	// usado para crear instancias individuales
	Trebejo crear(Posicion posicion, Boolean blanca);

	// usada en la preparacion del tablero
	Collection<Trebejo> crearCompania(byte filaInicial, Boolean blanca);
}

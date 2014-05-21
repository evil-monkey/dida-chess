package ajedrez;

import ajedrez.creacion.FabricaDeMovimientos;
import ajedrez.trebejos.creacion.FabricaDeAlfiles;
import ajedrez.trebejos.creacion.FabricaDeCaballos;
import ajedrez.trebejos.creacion.FabricaDePeones;
import ajedrez.trebejos.creacion.FabricaDeReinas;
import ajedrez.trebejos.creacion.FabricaDeReyes;
import ajedrez.trebejos.creacion.FabricaDeTorres;

public class Ajedrez {

	/* un cortito y al pie factory method */
	public static Tablero crearTablero() {
		Tablero tablero = new Tablero();
		
		tablero.setFabricaDePeones(new FabricaDePeones());
		tablero.setFabricaDeTorres(new FabricaDeTorres());
		tablero.setFabricaDeCaballos(new FabricaDeCaballos());
		tablero.setFabricaDeAlfiles(new FabricaDeAlfiles());
		tablero.setFabricaDeReinas(new FabricaDeReinas());
		tablero.setFabricaDeReyes(new FabricaDeReyes());
		
		tablero.setFabricaDeMovimientos(new FabricaDeMovimientos());
		
		
		return tablero;
	}

}

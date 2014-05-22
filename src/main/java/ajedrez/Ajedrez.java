package ajedrez;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ajedrez.creacion.FabricaDeMovimientos;
import ajedrez.excepciones.FueraDelTablero;
import ajedrez.excepciones.MovimientoNoPermitido;
import ajedrez.excepciones.MovimientoNoReconocido;
import ajedrez.excepciones.NoEsSuTurno;
import ajedrez.excepciones.ReyExpuesto;
import ajedrez.excepciones.TrebejoNoValido;
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

	private Tablero tablero;

	public Ajedrez() {
		// TODO Auto-generated constructor stub
	}

	public Tablero init() {
		tablero = crearTablero();
		return tablero;
	}

	public Tablero move(String alg) throws MovimientoNoPermitido, NoEsSuTurno,
			TrebejoNoValido, FueraDelTablero, MovimientoNoReconocido, ReyExpuesto {
		Posicion[] vector = translate(alg);
		tablero.mover(vector[0], vector[1]);
		return tablero;
	}

	private Posicion[] translate(String alg) throws MovimientoNoReconocido {
		Posicion[] tr = null;
		ArrayList<Posicion> ps = new ArrayList<Posicion>(2);
		Pattern re = Pattern
				.compile("^([a-h]{1})([1-8]{1})([a-h]{1})([1-8]{1})$");

		Matcher m = re.matcher(alg);

		if (!m.matches()) {
			throw new MovimientoNoReconocido();
		}
		
		int reference = Character.codePointAt("a", 0) - 1;

		ps.add(new Posicion((byte) (Character.codePointAt(m.group(1)
				.toLowerCase(), 0) - reference), Byte.parseByte(m.group(2))));

		ps.add(new Posicion((byte) (Character.codePointAt(m.group(3)
				.toLowerCase(), 0) - reference), Byte.parseByte(m.group(4))));
		
		return ps.toArray(tr);

	}
}

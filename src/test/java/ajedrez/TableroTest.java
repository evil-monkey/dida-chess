package ajedrez;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ajedrez.trebejos.Peon;
import ajedrez.trebejos.Trebejo;

public class TableroTest {

	Tablero tablero;

	@Before
	public void setUp() {
		tablero = Ajedrez.crearTablero();
		tablero.init();
	}

	@Test
	public void testInit() {
		List<Trebejo> blancas = getBlancas(tablero.getTrebejos());
		List<Trebejo> negras = getNegras(tablero.getTrebejos());
		
		assertNotNull("Blancas no inicializadas", blancas);
		assertNotNull("Negras no inicializadas", negras);

		assertFalse("Sin Blancas", blancas.isEmpty());
		assertFalse("Sin Negras", negras.isEmpty());

		assertEquals("Faltan piezas blancas", 16, blancas.size());
		assertEquals("Faltan piezas negras", 16, negras.size());
	}

	@Test
	public void testPeonesBlancas() {
		Collection<Trebejo> bando = getBlancas(tablero.getTrebejos());
		checkPeones(bando, "blancos", 2);
	}

	@Test
	public void testPeonesNegros() {
		Collection<Trebejo> bando = getNegras(tablero.getTrebejos());
		checkPeones(bando, "negros", 7);
	}

	private List<Trebejo> getBlancas(Collection<Trebejo> trebejos) {
		List<Trebejo> blancas = new ArrayList<Trebejo>();
		for (Trebejo trebejo : trebejos) {
			if (trebejo.getBlanca()) {
				blancas.add(trebejo);
			}
		}
		return blancas;
	}

	private List<Trebejo> getNegras(Collection<Trebejo> trebejos) {
		List<Trebejo> negras = new ArrayList<Trebejo>();
		for (Trebejo trebejo : trebejos) {
			if (!trebejo.getBlanca()) {
				negras.add(trebejo);
			}
		}
		return negras;
	}

	private void checkPeones(Collection<Trebejo> bando, String color, int fila) {
		Map<String, List<Trebejo>> companias = clasificarBando(bando);

		List<Trebejo> peones = companias.get(Peon.class.getName());

		assertEquals("Faltan peones " + color, 8, peones.size());

		Collections.sort(peones, getPeonesComparator());

		// chequeo posiciones 1 a 1

		for (int i = 0; i < 8; i++) {
			Trebejo peon = peones.get(i);
			assertEquals("Posición " + i + " horizontal errónea.", fila,
					(int) peon.getPosicion().getH());
			assertEquals("Posición " + i + " vertical errónea.", i + 1,
					(int) peon.getPosicion().getV());

		}
	}

	private Comparator<Trebejo> getPeonesComparator() {
		return new Comparator<Trebejo>() {

			public int compare(Trebejo o1, Trebejo o2) {
				return o1.getPosicion().getV()
						.compareTo(o2.getPosicion().getV());
			}

		};
	}

	private Map<String, List<Trebejo>> clasificarBando(Collection<Trebejo> bando) {

		Map<String, List<Trebejo>> companias = new HashMap<String, List<Trebejo>>(
				6);

		for (Trebejo trebejo : bando) {
			String className = trebejo.getClass().getName();
			if (!companias.containsKey(className)) {
				companias.put(className, new ArrayList<Trebejo>());
			}
			companias.get(className).add(trebejo);
		}

		return companias;
	}

}

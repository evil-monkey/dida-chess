package ajedrez;

public class Posicion implements Cloneable {

	private Byte h;
	private Byte v;

	public Posicion(Byte h, Byte v) {
		this.h = h;
		this.v = v;
	}

	public Byte getH() {
		return h;
	}

	public void setH(Byte h) {
		this.h = h;
	}

	public Byte getV() {
		return v;
	}

	public void setV(Byte v) {
		this.v = v;
	}

	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object != null) {
			if (object.getClass().equals(this.getClass())) {
				result = (this.h != null && this.v != null
						&& this.h.equals(((Posicion) object).getH()) 
						&& this.v.equals(((Posicion) object).getV()));
			}
		}
		return result;
	}

	public Posicion clone() {
		return new Posicion(h, v);
	}
}

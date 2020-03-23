/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;

/**
 * Un oggetto di questa classe è un risolutore del problema della più lunga
 * sottosequenza comune tra due stringhe date.
 * 
 * @author LORENZO TANGANELLI lorenzo.tanganelli@studenti.unicam.it
 *
 */
public class LCSSolver {

	private final String x;

	private final String y;

	private boolean isSolved = false;

	private String lcsSolver;

	public LCSSolver(String x, String y) {
		if (x == null || y == null)
			throw new NullPointerException("è stato passato un parametro null");
		this.x = x;
		this.y = y;
	}

	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	public int hashCode() {
		int result = 1;
		result += ((x == null) ? 0 : x.hashCode());
		result += ((y == null) ? 0 : y.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof LCSSolver))
			return false;
		LCSSolver other = (LCSSolver) obj;
		// controllo se this.x==other.x e this.y==other.y oppure this.x==other.y e this.y==other.x
		if ((getX().equals(other.getX()) && getY().equals(other.getY()))
				|| (getX().equals(other.getY()) && getY().equals(other.getX())))
			return true;
		return false;
	}

	public void solve() {
		//eseguo tutto il metodo una volta sola
		if (!isSolved) {
			//dichiarazione di un array
			int[][] lengths = new int[x.length() + 1][y.length() + 1];

			//calcoli e assegno i valori all'interno dell'array
			for (int i = 0; i < x.length(); i++)
				for (int j = 0; j < y.length(); j++)
					if (x.charAt(i) == y.charAt(j))
						lengths[i + 1][j + 1] = lengths[i][j] + 1;
					else
						lengths[i + 1][j + 1] = Math.max(lengths[i + 1][j], lengths[i][j + 1]);
			
			//dichiaro un buffer in cui salvo la sottosequenza comune
			StringBuffer sb = new StringBuffer();
			//ciclo per controllare, scorrere e aggiungere i carattere all'array
			for (int a = x.length(), b = y.length(); a != 0 && b != 0;)
				if (lengths[a][b] == lengths[a - 1][b])
					a--;
				else if (lengths[a][b] == lengths[a][b - 1])
					b--;
				else {
					assert x.charAt(a - 1) == y.charAt(b - 1);
					sb.append(x.charAt(a - 1));
					a--;
					b--;
				}
			lcsSolver = sb.reverse().toString();
			isSolved = true;
		}
	}

	public boolean isSolved() {
		return isSolved;
	}

	public int getLengthOfSolution() {

		return lcsSolver.length();
	}

	public String getOneSolution() {
		return lcsSolver;
	}

	public boolean isCommonSubsequence(String z) {
		if (z == null)
			throw new NullPointerException("è stato passato un parametro null");
		if (z.equals(""))
			return true;
			//se la lunghezza è maggiore di quella di x o di y ritorno false
		if (z.length() > x.length() || z.length() > y.length())
			return false;

		//dichiarazione delle variabili di controllo e contatori per scorrere le stringhe
		boolean stringX = false;
		boolean stringY = false;
		int a = 0;
		int b = 0;

		//scorro x e z e cotnrollo se z è una sottostringa di x
		while (a < x.length() && b < z.length()) {
			if (x.charAt(a) == z.charAt(b)) {
				a++;
				b++;
			} else
				a++;
		}
		//se b vale quanto la lunghezza di z allora z è una sottostringa di x
		if (b == z.length())
			stringX = true;
		
		a = 0;
		b = 0;
		//scorro x e z e cotnrollo se z è una sottostringa di x
		while (a < y.length() && b < z.length()) {
			if (y.charAt(a) == z.charAt(b)) {
				a++;
				b++;
			} else
				a++;
		}
		//se b vale quanto la lunghezza di z allora z è una sottostringa di y
		if (b == z.length())
			stringY = true;

		//se entrambi i controlli risultano veri ritorno true, false altrimenti
		return stringX && stringY ? true : false;
	}
}

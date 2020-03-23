/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;

import java.util.HashSet;
import java.util.Set;

/**
 * Un oggetto di questa classe è un risolutore del problema di trovare
 * <b>tutte</b> le più lunghe sottosequenze comuni tra due stringhe date.
 * 
 * @author LORENZO TANGANELLI lorenzo.tanganelli@studenti.unicam.it
 *
 */
public class AllLCSsSolver {

	private final String x;

	private final String y;

	private LCSSolver solver;

	private boolean isSolved = false;
	
	private Set<String> solution;

	private int[][] arr;

	public AllLCSsSolver(String x, String y) {
		if (x == null || y == null)
			throw new NullPointerException("è stato passato un parametro null");
		this.x = x;
		this.y = y;
		//creo un oggetto solver che strutto in vari metodi
		solver = new LCSSolver(x, y);
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
		if ((getX().equals(other.getX()) && getY().equals(other.getY()))
				|| (getX().equals(other.getY()) && getY().equals(other.getX())))
			return true;
		return false;
	}

	public void solve() {
		if (!isSolved){
			//calcoli e assegno i valori all'interno dell'array
			arr = new int[x.length() + 1][y.length() + 1];
			for (int i = 1; i <= x.length(); i++) {
				for (int j = 1; j <= y.length(); j++) {
					if (x.charAt(i - 1) == y.charAt(j - 1))
						arr[i][j] = arr[i - 1][j - 1] + 1;
					else
						arr[i][j] = Math.max(arr[i - 1][j], arr[i][j - 1]);
				}
			}
			solution = lcs(x.length(), y.length());
		}
		isSolved = true;
	}

	public boolean isSolved() {

		return isSolved;
	}

	public int getLengthOfSolutions() {
		solver.solve();
		return solver.getLengthOfSolution();
	}

	public Set<String> getSolutions() {
		return solution;
		
	}

	public boolean isCommonSubsequence(String z) {
		return solver.isCommonSubsequence(z);
	}

	public boolean checkLCSs(Set<String> sequences) {
		if (sequences == null)
			throw new NullPointerException("è stato passato un parametro null");
		String[] seq = new String[sequences.size()];

		System.arraycopy(sequences.toArray(), 0, seq, 0, sequences.size());

		/*
		* Scorro tutta la collezione:
		* se l'elemente a cui punto ha la lunghezza diversa da quella del primo elemento 
		° o non è una sottostringa di x e y ritorno false
		*/
		for (String el : seq) {
			if (seq[0].length() != el.length() || !solver.isCommonSubsequence(el))
				return false;
		}
		return true;
	}

	private Set<String> lcs(int len1, int len2) {
		/*
		* Se len1 o len2 sono ugauli a zero vuol dire che ho scoro completamente 
		* una delle due stringhe e quindi ritorno una collezione(Set) con solamente la stringa vuota
		*/
		if (len1 == 0 || len2 == 0) {
			Set<String> set = new HashSet<String>();
			set.add("");
			return set;
		}
		/*
		* Se il carattere x[lxen1] e ylxen2] sono uguali, devono essere in lcs. 
		* Se non sono uguali, l'LCS verrà costruito dalla riga superiore della matrice o 
		* dalla colonna precedente della matrice in base al valore maggiore.
		* Se entrambi i valori sono uguali, allora l'lcs sarà costruito da entrambi i lati. 
		* Quindi continuo a costruire l'LCS fino a quando non ho costruito tutti gli LCS e 
		* li salvo tutti in un set.
		*/
		if (x.charAt(len1 - 1) == y.charAt(len2 - 1)) {
			Set<String> set = lcs(len1 - 1, len2 - 1);
			Set<String> set1 = new HashSet<>();
			for (String temp : set) {
				temp += x.charAt(len1 - 1);
				set1.add(temp);
			}
			return set1;
		} else {
			Set<String> set = new HashSet<>();
			Set<String> set1 = new HashSet<>();
			if (arr[len1 - 1][len2] >= arr[len1][len2 - 1]) {
				set = lcs(len1 - 1, len2);
			}
			if (arr[len1][len2 - 1] >= arr[len1 - 1][len2]) {
				set1 = lcs(len1, len2 - 1);
			}
			for (String temp : set) {
				set1.add(temp);
			}
			return set1;

		}

	}
}

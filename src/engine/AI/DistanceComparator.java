package engine.AI;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Triangle>{
	@Override
	public int compare(Triangle A, Triangle B) {
		return (int) (A.getF() - B.getF());
	}
}

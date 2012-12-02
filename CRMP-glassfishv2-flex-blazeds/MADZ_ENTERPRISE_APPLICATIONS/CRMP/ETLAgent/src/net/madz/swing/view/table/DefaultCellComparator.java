package net.madz.swing.view.table;

public class DefaultCellComparator implements CellComparator {

	public int compareObjects(Object o1, Object o2) {

		if (o1 == null) {
			if (o2 == null) {
				return 0;
			} else {
				return -1;
			}
		}

		if (o2 == null) {
			return 1;
		}

		if (!(o1 instanceof Comparable && o2 instanceof Comparable)) {

			if (o1.equals(o2))
				return 0;
			else
				return 1;

		}

		Comparable cOne = (Comparable) o1;
		Comparable cTwo = (Comparable) o2;

		return cOne.compareTo(cTwo);
	}
}

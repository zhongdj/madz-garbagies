package net.madz.swing.view.table; import java.util.Comparator;
import java.util.Vector;

public class ColumnComparator implements Comparator {
	/** Index of column to be sorted */
	protected int index = -1;

	/** Sorting policy */
	protected boolean ascending = true;

	/** Cell comparator */
	protected CellComparator cellComparator = null;

	public ColumnComparator(int index, boolean ascending,
			CellComparator cellComparator) {
		if (index < 0) {

		}

		if (cellComparator == null) {

		}

		this.index = index;
		this.ascending = ascending;
		this.cellComparator = cellComparator;
	}



	public int compare(Object one, Object two) {

		if (!(one instanceof Vector && two instanceof Vector)) {

			return 0;
		}

		Vector vOne = (Vector) one;
		Vector vTwo = (Vector) two;

		if (index >= vOne.size() || index >= vTwo.size()) {

			return 0;
		}

		Object oOne = vOne.elementAt(index);
		Object oTwo = vTwo.elementAt(index);

		if (ascending) {
			return this.cellComparator.compareObjects(oOne, oTwo);
		} else {
			return this.cellComparator.compareObjects(oTwo, oOne);
		}
	}
}
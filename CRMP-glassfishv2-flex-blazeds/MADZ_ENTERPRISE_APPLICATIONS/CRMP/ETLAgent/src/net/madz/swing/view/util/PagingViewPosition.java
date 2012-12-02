package net.madz.swing.view.util;

import java.awt.Point;

public class PagingViewPosition implements Comparable {
	/** Current (beginning) row of position */
	public int currentRow = -1;

	/** Current (beginning) column of position */
	public int currentColumn = -1;

	/** Current row page size */
	public int rowPageSize = -1;

	/** Current column page size */
	public int columnPageSize = -1;

	public boolean showScrollBar = false;

	public PagingViewPosition() {
		this(0, 0, 0, 0, false);
	}

	public PagingViewPosition(PagingViewPosition r) {
		this(r.currentRow, r.currentColumn, r.rowPageSize, r.columnPageSize, r.showScrollBar);
	}

	public PagingViewPosition(int currentRow, int currentColumn, int rowPageSize, int columnPageSize) {
		/*
		 * this.currentRow = currentRow; this.currentColumn = currentColumn; this.rowPageSize = rowPageSize; this.columnPageSize =
		 * columnPageSize;
		 */
		this(currentRow, currentColumn, rowPageSize, columnPageSize, false);
	}

	public PagingViewPosition(int currentRow, int currentColumn, int rowPageSize, int columnPageSize, boolean showScrollBar) {
		this.currentRow = currentRow;
		this.currentColumn = currentColumn;
		this.rowPageSize = rowPageSize;
		this.columnPageSize = columnPageSize;
		this.showScrollBar = showScrollBar;
	}

	public PagingViewPosition getPosition() {
		return new PagingViewPosition(this);
	}

	public Point getPagingLocation() {
		return new Point(currentRow, currentColumn);
	}

	public int compareTo(Object o) {
		if (o instanceof PagingViewPosition) {
			PagingViewPosition pos = (PagingViewPosition) o;

			if (this.currentRow == pos.currentRow && this.currentColumn == pos.currentColumn && this.rowPageSize == pos.rowPageSize
					&& this.columnPageSize == pos.columnPageSize && this.showScrollBar == pos.showScrollBar) {
				return 0;
			}
		}

		return -1;
	}

	public boolean equals(Object obj) {
		if (obj instanceof PagingViewPosition) {
			if (getClass() != obj.getClass()) {
				return false;
			}
			return (this.compareTo(obj) == 0) ? true : false;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return paramString().hashCode();
	}

	public String paramString() {
		return "currentRow = " + currentRow + ", currentColumn = " + currentColumn + ", rowPageSize =  " + rowPageSize
				+ ", columnPageSize = " + columnPageSize + ", showScrollBar = " + showScrollBar;
	}

	public String toString() {
		return super.toString() + this.paramString();
	}
}

package net.madz.swing.view.util;

import java.util.Vector;

public interface ColumnHidingCofigureI {
	public void setColumnHidingStatus(int column, boolean isHide);

	public Vector<TableHeaderInfo> getHidableColumnsInfo();

	public Vector<Integer> getHidenColumnsIndex();

	public boolean canAllColumnsUnchecked();
}
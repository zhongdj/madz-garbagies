package net.madz.etl.db;

import net.madz.service.etl.to.DatabaseImportIndicatorTO;

public class AccessDBDataTypes {

	public static final String COUNTER = "COUNTER";
	public static final String DOUBLE = "DOUBLE";
	public static final String DATETIME = "DATETIME";
	public static final String INTEGER = "INTEGER";
	public static final String VARCHAR = "VARCHAR";
	public static final String BIT = "BIT";
	public static final String LONGCHAR = "LONGCHAR";
	public static final String DECIMAL = "DECIMAL";
	public static final String SMALLINT = "SMALLINT";
	public static final String REAL = "REAL";
	public static final String BYTE = "BYTE";
	public static final String VARBINARY = "VARBINARY";
	public static void appendIndicatorLiteral(
			DatabaseImportIndicatorTO indicator, StringBuilder countSb) {
		final String indicatorColumnType = indicator.getIndicatorColumnType();
		if (DATETIME.equals(indicatorColumnType)) {
			countSb.append(" #");
		} else if (!indicatorColumnType
				.equalsIgnoreCase(COUNTER)) {
			countSb.append(" '");
		}
		countSb.append(indicator.getIndicatorValue());
		if (DATETIME.equals(indicatorColumnType)) {
			countSb.append("#");
		} else if (!indicatorColumnType
				.equalsIgnoreCase(COUNTER)) {
			countSb.append("'");
		}
	}

}

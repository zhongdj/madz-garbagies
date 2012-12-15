package net.madz.etl.db;

import net.madz.service.etl.to.ColumnDescriptorTO;

public class MysqlDBDataTypes {

	public static final String VARCHAR = "VARCHAR";
	public static final String INTEGER = "INTEGER";
	public static final String DOUBLE = "DOUBLE";
	public static final String DATETIME = "DATETIME";
	public static final String BIT = "BIT";
	public static final String NVARCHAR = "NVARCHAR";
	public static final String DECIMAL = "DECIMAL";
	public static final String TEXT = "TEXT";
	public static final String SMALLINT = "SMALLINT";
	public static final String FLOAT = "FLOAT";
	public static final String TINYINT = "TINYINT";
	public static final String BLOB = "BLOB";
	public static boolean hasSingleQuote(ColumnDescriptorTO column) {
		return !BIT.equalsIgnoreCase(column
				.getColumnTypeName());
	}
	public static boolean requireDefaultNumber(ColumnDescriptorTO column) {
		return DECIMAL.equalsIgnoreCase(column
				.getColumnTypeName())
				|| DOUBLE.equalsIgnoreCase(column
						.getColumnTypeName());
	}

}

package net.madz.swing.view.util;

import javax.swing.Icon;

public class TableHeaderInfo {
	private Icon _icon = null;
	private int _index = -1;
	private String _name = null;
	private String _tooltip = null;

	public TableHeaderInfo(String name, Icon icon, int index, String tooltip) {
		_name = name;
		_icon = icon;
		_index = index;
		_tooltip = tooltip;
	}

	public String getName() {
		return _name;
	}

	public Icon getIcon() {
		return _icon;
	}

	public int getIndex() {
		return _index;
	}

	public String getTooltip() {
		return _tooltip;
	}
}
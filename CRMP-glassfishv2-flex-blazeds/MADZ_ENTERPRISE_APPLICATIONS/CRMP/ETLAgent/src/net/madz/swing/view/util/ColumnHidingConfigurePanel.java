package net.madz.swing.view.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ColumnHidingConfigurePanel extends JPanel implements ItemListener {
	private ColumnHidingCofigureI _configure = null;
	private HashMap<Integer, JCheckBox> _indexToCheckbox = new HashMap<Integer, JCheckBox>();
	private int _shownColumnCount = -1;
	private boolean _canAllColumnsUnchecked = false;

	public ColumnHidingConfigurePanel(ColumnHidingCofigureI configure) {
		_configure = configure;

		_init();
	}

	private void _init() {
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		Vector<TableHeaderInfo> infos = _configure.getHidableColumnsInfo();
		int count = infos.size();
		for (int i = 0; i < count; i++) {
			TableHeaderInfo info = infos.elementAt(i);
			c.gridx = 0;
			c.gridy = i;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0.0;
			JCheckBox cb = new JCheckBox(null, null, true);
			cb.setToolTipText(info.getTooltip());
			cb.addItemListener(this);
			layout.setConstraints(cb, c);
			this.add(cb);

			JLabel label = new JLabel(info.getName(), info.getIcon(),
					JLabel.LEFT);
			label.setToolTipText(info.getTooltip());
			c.gridx = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			layout.setConstraints(label, c);
			this.add(label);
			_indexToCheckbox.put(Integer.valueOf(info.getIndex()), cb);
		}
		c.gridx = 0;
		c.gridy = count;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1.0;
		c.gridwidth = 2;
		JLabel label = new JLabel("");
		layout.setConstraints(label, c);
		this.add(label);
		_shownColumnCount = count;
		Vector<Integer> indexes = _configure.getHidenColumnsIndex();
		for (int i = indexes.size() - 1; i >= 0; i--) {
			Integer index = indexes.elementAt(i);
			JCheckBox cb = _indexToCheckbox.get(index);
			if (cb != null) {
				cb.setSelected(false);
			}
		}
		_canAllColumnsUnchecked = _configure.canAllColumnsUnchecked();
	}

	public void refreshConfigure() {
		this.removeAll();
		_indexToCheckbox.clear();
		_init();
	}

	public void setConfigureToTable() {
		Iterator<Integer> it = _indexToCheckbox.keySet().iterator();
		while (it.hasNext()) {
			Integer index = it.next();
			JCheckBox cb = _indexToCheckbox.get(index);
			_configure.setColumnHidingStatus(index, !cb.isSelected());
		}
	}

	public void itemStateChanged(ItemEvent e) {
		int stateChange = e.getStateChange();
		if (stateChange == ItemEvent.SELECTED)
			_shownColumnCount++;
		else if (stateChange == ItemEvent.DESELECTED)
			_shownColumnCount--;
		if (_shownColumnCount == 0 && !_canAllColumnsUnchecked) {
			((JCheckBox) e.getSource()).setSelected(true);
		}
	}
}

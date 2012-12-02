package net.madz.swing.view.util;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;


public class PagingHeaderRenderer extends JPanel implements TableCellRenderer {

	public final static Icon NONSORTED = new SortArrowIcon(SortArrowIcon.NONE);

	public final static Icon ASCENDING = new SortArrowIcon(
			SortArrowIcon.ASCENDING);

	public final static Icon DECENDING = new SortArrowIcon(
			SortArrowIcon.DECENDING);

	public final static int FILTER_COMBOBOX_WIDTH = 20;

	public final static int FILTER_COMBOBOX_HEIGHT = 10;

	public final static Color DEFAULT_FILTERED_COMBOBOX_ARROW_COLOR = Color.blue;

	private JLabel _label = new JLabel();

	private JPanel _labelPanel = new JPanel();
	private GridBagLayout _labelLayout = new GridBagLayout();
	private JLabel _iconLabel = new JLabel();

	private JComboBox _filteringComboBox = new JComboBox();

	private FilterControlComboBoxUI _filteringComboUI = new FilterControlComboBoxUI();

	private Color _filteredComboBoxArrowColor = DEFAULT_FILTERED_COMBOBOX_ARROW_COLOR;
	private GridBagLayout _layout = new GridBagLayout();

	private int _prefHeightOfEmptyLabel = -1;

	public PagingHeaderRenderer() {
		setLayout(_layout);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));

		_label.setHorizontalTextPosition(JLabel.LEFT);

		_label.setHorizontalAlignment(JLabel.LEFT);

		Dimension filterComboboxSize = new Dimension(FILTER_COMBOBOX_WIDTH,
				FILTER_COMBOBOX_HEIGHT);

		_filteringComboBox.setMaximumSize(filterComboboxSize);
		_filteringComboBox.setMinimumSize(filterComboboxSize);
		_filteringComboBox.setPreferredSize(filterComboboxSize);
		_filteringComboBox.setUI(_filteringComboUI);

		_iconLabel.setHorizontalTextPosition(JLabel.LEFT);
		_iconLabel.setHorizontalAlignment(JLabel.RIGHT);
		_labelPanel.setLayout(_labelLayout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		JLabel label = new JLabel();
		_labelLayout.setConstraints(label, gbc);
		_labelPanel.add(label);
		gbc.gridx = 1;
		gbc.weightx = 0;
		_labelLayout.setConstraints(_iconLabel, gbc);
		_labelPanel.add(_iconLabel);
		gbc.gridx = 2;
		_labelLayout.setConstraints(_label, gbc);
		_labelPanel.add(_label);
		gbc.gridx = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		label = new JLabel();
		_labelLayout.setConstraints(label, gbc);
		_labelPanel.add(label);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 0.0;
		_layout.setConstraints(_filteringComboBox, c);
		this.add(_filteringComboBox);
		c.gridx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(0, 0, 0, 3);
		_layout.setConstraints(_labelPanel, c);
		this.add(_labelPanel);

		_prefHeightOfEmptyLabel = (new JLabel("test_text")).getPreferredSize().height;
		_labelPanel.setPreferredSize(new Dimension(_labelPanel
				.getPreferredSize().width, _prefHeightOfEmptyLabel));

	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		int index = -1;
		boolean ascending = true;

		Icon headerIcon = null;

		if (table instanceof JPagingTable) {
			JPagingTable pTable = (JPagingTable) table;

			index = pTable.getSortedColumnIndex();
			ascending = pTable.isSortedColumnAscending();

			boolean filteringEnabledFlag = pTable.isFilteringEnabled()
					&& pTable.isFilteringEnabled(column);

			_filteringComboBox.setVisible(filteringEnabledFlag);
			if (filteringEnabledFlag && pTable.getFilter(column) != null) {
				_filteringComboUI.setArrowcolor(_filteredComboBoxArrowColor);
			} else {
				_filteringComboUI.setArrowcolor(null);
			}

			if (pTable._headerIcons != null
					&& pTable._headerIcons.size() > column) {
				headerIcon = pTable._headerIcons.elementAt(table
						.convertColumnIndexToModel(column));
			}
		}

		if (table != null) {
			JTableHeader header = table.getTableHeader();
			if (header != null) {
				setForeground(header.getForeground());
				setBackground(header.getBackground());
				setFont(header.getFont());
			}
		}

		Icon icon = ascending ? ASCENDING : DECENDING;

		_label.setIcon(column == index ? icon : NONSORTED);

		_label.setText((value == null) ? "" : value.toString());

		_iconLabel.setIcon(headerIcon);
		if (headerIcon != null) {
			Dimension d = _labelPanel.getPreferredSize();
			d.height = headerIcon.getIconHeight() > d.height ? headerIcon
					.getIconHeight() : d.height;
			_labelPanel.setPreferredSize(d);
		}

		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		return this;
	}

	public Color getFilteredArrowColor() {
		return _filteredComboBoxArrowColor;
	}

	public void setFilteredArrowColor(Color newColor) {
		_filteredComboBoxArrowColor = newColor;
		_filteringComboUI.setArrowcolor(_filteredComboBoxArrowColor);
	}

	public static class FilterControlComboBoxUI extends BasicComboBoxUI {
		protected JButton createArrowButton() {
			return new FilterArrowButton(BasicArrowButton.SOUTH);
		}

		public void setArrowcolor(Color newColor) {
			((FilterArrowButton) arrowButton).setArrowColor(newColor);
		}

		public static class FilterArrowButton extends BasicArrowButton {

			private Color _arrowColor = null;

			public FilterArrowButton(int direction) {
				super(direction);
			}

			public void setArrowColor(Color newColor) {
				_arrowColor = newColor;
			}

			public void paintTriangle(Graphics g, int x, int y, int size,
					int direction, boolean isEnabled) {
				Color oldColor = g.getColor();
				int mid, i, j;

				j = 0;
				size = Math.max(size, 2);
				mid = size / 2;

				g.translate(x, y);
				g.setColor(_arrowColor);

				switch (direction) {
				case SOUTH:
					if (!isEnabled) {
						g.translate(1, 1);
						g.setColor(_arrowColor);
						for (i = size - 1; i >= 0; i--) {
							g.drawLine(mid - i, j, mid + i, j);
							j++;
						}
						g.translate(-1, -1);
						g.setColor(_arrowColor);
					}

					j = 0;
					for (i = size - 1; i >= 0; i--) {
						g.drawLine(mid - i, j, mid + i, j);
						j++;
					}
					break;
				default:
					break;
				}
				g.translate(-x, -y);
				g.setColor(oldColor);
			}

		}

	}

}

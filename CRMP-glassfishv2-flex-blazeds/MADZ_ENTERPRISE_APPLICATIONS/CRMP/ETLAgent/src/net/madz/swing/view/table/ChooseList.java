package net.madz.swing.view.table;

import java.awt.GridBagConstraints;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.madz.swing.view.util.GraphicFactory;
import net.madz.swing.view.util.ItemsMotionEvent;
import net.madz.swing.view.util.ItemsMotionListener;
import net.madz.swing.view.util.MovingItemsPanel;
import net.madz.swing.view.util.SectioningHeader;

public class ChooseList extends JPanel implements ListSelectionListener, ItemsMotionListener, ListDataListener {
	private MovingItemsPanel _motionPanel;

	GraphicFactory _toolsGraphic = new GraphicFactory();
	JPanel _panelCenter = new JPanel();

	private Vector _itemListener = new Vector();

	private JList _rightList;
	private JList _leftList;

	private JScrollPane _jScrollPaneRight;
	private JScrollPane _jScrollPaneLeft;

	private JLabel _labelRight = new JLabel("");
	private JLabel _labelLeft = new JLabel("");

	private JComponent _SectionHeader = _toolsGraphic.createSectionHeader("");

	public ChooseList() {
		_leftList = new JList();
		_rightList = new JList();

		_leftList.addListSelectionListener(this);
		_rightList.addListSelectionListener(this);

		initPanel();
	}

	public JList getLeftList() {
		return _leftList;
	}

	public JList getRightList() {
		return _rightList;
	}

	public void setLeftModel(AbstractListModel model) {
		_leftList.setModel(model);
		model.addListDataListener(this);
	}

	public void setRightModel(AbstractListModel model) {
		_rightList.setModel(model);
		model.addListDataListener(this);
	}

	public void initPanel() {
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 10, 10, 10);
		c.gridwidth = GridBagConstraints.REMAINDER;
		add(_SectionHeader, c);

		c.gridwidth = 1;// To set the default value
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 5, 0, 5);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		_labelLeft.setHorizontalAlignment(JLabel.LEFT);
		add(_labelLeft, c);

		c.gridx = 2;
		c.gridy = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 5, 0, 5);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;

		_labelRight.setHorizontalAlignment(JLabel.LEFT);
		add(_labelRight, c);

		_leftList.setBorder(BorderFactory.createEtchedBorder());

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(0, 5, 0, 5);
		c.fill = GridBagConstraints.BOTH;

		_jScrollPaneLeft = new JScrollPane(_leftList);
		_leftList.setBorder(BorderFactory.createEtchedBorder());
		add(_jScrollPaneLeft, c);

		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		_motionPanel = new MovingItemsPanel(MovingItemsPanel.MOVING_W_TEXT);
		_motionPanel.setEnabled(MovingItemsPanel.ALL_DOWNLOAD, false);
		_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, false);
		_motionPanel.setEnabled(MovingItemsPanel.SINGLE_DOWNLOAD, false);
		_motionPanel.setEnabled(MovingItemsPanel.ALL_UPLOAD, false);
		_motionPanel.addItemsMotionListener(this);
		add(_motionPanel, c);

		// For the second list selection
		_rightList.setBorder(BorderFactory.createEtchedBorder());

		c.gridx = 2;
		c.gridy = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;

		_jScrollPaneRight = new JScrollPane(_rightList);
		add(_jScrollPaneRight, c);

	}

	public void addMovingItemListener(ItemsMotionListener e) {
		if (_itemListener.contains(e))
			return;
		_itemListener.addElement(e);
	}

	public void fireMovingItemListener(ItemsMotionEvent e) {
		for (int i = 0; i < _itemListener.size(); i++) {
			ItemsMotionListener listener = (ItemsMotionListener) _itemListener.elementAt(i);
			listener.itemsMoved(e);
		}
	}

	public void itemsMoved(ItemsMotionEvent fEvent) {
		fireMovingItemListener(fEvent);

		switch (fEvent.getMovement()) {
		case ItemsMotionEvent.SINGLE_DOWNLOAD:
			/* falls through */
		case ItemsMotionEvent.ALL_DOWNLOAD:
			_motionPanel.setEnabled(MovingItemsPanel.ALL_UPLOAD, true);
			if (_rightList.isSelectionEmpty())
				_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, false);
			else
				_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, true);
			break;
		case ItemsMotionEvent.ALL_UPLOAD:
			_motionPanel.setEnabled(MovingItemsPanel.ALL_UPLOAD, false);
			_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, false);
			break;
		case ItemsMotionEvent.SINGLE_UPLOAD:
			_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, false);
			if (_rightList.getModel().getSize() == 0)
				_motionPanel.setEnabled(MovingItemsPanel.ALL_UPLOAD, false);
			break;
		default:
			break;
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource().equals(_rightList)) {
			if (_rightList.getModel().getSize() == 0) {
				_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, false);
				_motionPanel.setEnabled(MovingItemsPanel.ALL_UPLOAD, false);
			} else {
				if (_rightList.isSelectionEmpty())
					_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, false);
				else
					_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, true);
				_motionPanel.setEnabled(MovingItemsPanel.ALL_UPLOAD, true);
			}
		}

		if (e.getSource().equals(_leftList)) {
			if (_leftList.getModel().getSize() == 0) {
				_motionPanel.setEnabled(MovingItemsPanel.ALL_DOWNLOAD, false);
				_motionPanel.setEnabled(MovingItemsPanel.SINGLE_DOWNLOAD, false);
			} else {
				if (_leftList.isSelectionEmpty())
					_motionPanel.setEnabled(MovingItemsPanel.SINGLE_DOWNLOAD, false);
				else
					_motionPanel.setEnabled(MovingItemsPanel.SINGLE_DOWNLOAD, true);
				_motionPanel.setEnabled(MovingItemsPanel.ALL_DOWNLOAD, true);
			}
		}
	}

	public void setEnableMovingItem(int iItem, boolean bEnable) {
		_motionPanel.setEnabled(iItem, bEnable);
	}

	public void updateSize() {
		_jScrollPaneRight.setPreferredSize(_jScrollPaneLeft.getPreferredSize());
	}

	public void setLeftListTitle(String strTitle) {
		_labelLeft.setText(strTitle);
	}

	public void setRightListTitle(String strTitle) {
		_labelRight.setText(strTitle);
	}

	public void setTitleChoosePanel(String strTitle) {
		((SectioningHeader) _SectionHeader).setTitle(strTitle);
	}

	public void enableMovingItemPanel(boolean bEnable) {
		_motionPanel.setEnabled(MovingItemsPanel.ALL_DOWNLOAD, bEnable);
		_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, bEnable);
		_motionPanel.setEnabled(MovingItemsPanel.SINGLE_DOWNLOAD, bEnable);
		_motionPanel.setEnabled(MovingItemsPanel.ALL_UPLOAD, bEnable);
	}

	public void updateMovingItemPanel() {
		if (_rightList.getModel().getSize() == 0) {
			_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, false);
			_motionPanel.setEnabled(MovingItemsPanel.ALL_UPLOAD, false);
		} else {
			if (_rightList.isSelectionEmpty())
				_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, false);
			else
				_motionPanel.setEnabled(MovingItemsPanel.SINGLE_UPLOAD, true);

			_motionPanel.setEnabled(MovingItemsPanel.ALL_UPLOAD, true);
		}

		if (_leftList.getModel().getSize() == 0) {
			_motionPanel.setEnabled(MovingItemsPanel.ALL_DOWNLOAD, false);
			_motionPanel.setEnabled(MovingItemsPanel.SINGLE_DOWNLOAD, false);
		} else {
			if (_leftList.isSelectionEmpty())
				_motionPanel.setEnabled(MovingItemsPanel.SINGLE_DOWNLOAD, false);
			else
				_motionPanel.setEnabled(MovingItemsPanel.SINGLE_DOWNLOAD, true);

			_motionPanel.setEnabled(MovingItemsPanel.ALL_DOWNLOAD, true);
		}
	}

	// Implement interface ListDataListener

	public void contentsChanged(ListDataEvent event) {
		updateMovingItemPanel();
		updateSize();
	}

	public void intervalAdded(ListDataEvent event) {
		updateMovingItemPanel();
		updateSize();
	}

	public void intervalRemoved(ListDataEvent event) {
		updateMovingItemPanel();
		updateSize();
	}
}

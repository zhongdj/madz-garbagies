package net.madz.swing.view.util;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SizeRequirements;
import javax.swing.border.Border;

import net.madz.swing.resources.ResourceFactory;

public class MovingItemsPanel extends JPanel {
	public static final int MOVING = 0;
	public static final int SELECTING = 1;
	public static final int MOVING_W_TEXT = 2;
	public static final int SELECTING_W_TEXT = 3;
	public static final int MOVING_W_CUST_TEXT = 4;
	public static final int SELECTING_W_CUST_TEXT = 5;

	protected static final int ALL_ATONCE = 0;
	public static final int SINGLE_DOWNLOAD = 1;
	public static final int SINGLE_UPLOAD = 2;
	public static final int ALL_DOWNLOAD = 4;
	public static final int ALL_UPLOAD = 8;
	public static final int ALL_MOVEMENT = SINGLE_UPLOAD | SINGLE_DOWNLOAD | ALL_UPLOAD | ALL_DOWNLOAD;

	int _mode;
	Vector _listeners;

	private JButton _btns[];
	private Icon _movingIcons[];
	private Icon _selectingIcons[];
	private String _movingTexts[];
	private String _selectingTexts[];
	private String _customMovingTexts[];
	private String _customSelectingTexts[];

	public MovingItemsPanel(int fMode) {
		super();

		_verifyLegalMode(fMode);
		_mode = fMode;

		_prefetchIcons();
		_movingTexts = new String[4];
		_customMovingTexts = new String[4];
		_selectingTexts = new String[4];
		_customSelectingTexts = new String[4];
		_prefetchTexts();
		_init(4);
		setUseModeLook(fMode);
	}

	private void _prefetchIcons() {

		final String lDomain = "AlarmResource";
		_movingIcons = new Icon[4];
		String lIconName = null;
		lIconName = ResourceFactory.getInstance().getString(lDomain, "DOWNLOAD_ICON");
		_movingIcons[0] = ResourceFactory.getInstance().createImageIcon(lIconName);
		lIconName = ResourceFactory.getInstance().getString(lDomain, "UPLOAD_ICON");
		_movingIcons[1] = ResourceFactory.getInstance().createImageIcon(lIconName);
		lIconName = ResourceFactory.getInstance().getString(lDomain, "ALL_DOWNLOAD_ICON");
		_movingIcons[2] = ResourceFactory.getInstance().createImageIcon(lIconName);
		lIconName = ResourceFactory.getInstance().getString(lDomain, "ALL_UPLOAD_ICON");
		_movingIcons[3] = ResourceFactory.getInstance().createImageIcon(lIconName);

		_selectingIcons = new Icon[4];
		lIconName = ResourceFactory.getInstance().getString(lDomain, "SEL_FORW_ICON");
		_selectingIcons[0] = ResourceFactory.getInstance().createImageIcon(lIconName);
		lIconName = ResourceFactory.getInstance().getString(lDomain, "SEL_BACK_ICON");
		_selectingIcons[1] = ResourceFactory.getInstance().createImageIcon(lIconName);
		lIconName = ResourceFactory.getInstance().getString(lDomain, "ALL_FORW_ICON");
		_selectingIcons[2] = ResourceFactory.getInstance().createImageIcon(lIconName);
		lIconName = ResourceFactory.getInstance().getString(lDomain, "ALL_BACK_ICON");
		_selectingIcons[3] = ResourceFactory.getInstance().createImageIcon(lIconName);
	}

	private void _prefetchTexts() {
		final String lDomain = "AlarmResource";
		_movingTexts[0] = ResourceFactory.getInstance().getString(lDomain, "MOVE_SEL_FORW");
		_movingTexts[1] = ResourceFactory.getInstance().getString(lDomain, "MOVE_SEL_BACK");
		_movingTexts[2] = ResourceFactory.getInstance().getString(lDomain, "MOVE_ALL_FORW");
		_movingTexts[3] = ResourceFactory.getInstance().getString(lDomain, "MOVE_ALL_BACK");
		for (int li = 0; li < 4; li++) {
			_customMovingTexts[li] = _movingTexts[li];
		}

		_selectingTexts[0] = ResourceFactory.getInstance().getString(lDomain, "COPY_SEL_FORW");
		_selectingTexts[1] = ResourceFactory.getInstance().getString(lDomain, "COPY_SEL_BACK");
		_selectingTexts[2] = ResourceFactory.getInstance().getString(lDomain, "COPY_ALL_FORW");
		_selectingTexts[3] = ResourceFactory.getInstance().getString(lDomain, "COPY_ALL_BACK");
		for (int li = 0; li < 4; li++) {
			_customSelectingTexts[li] = _selectingTexts[li];
		}
	}

	private void _init(int fNbBtns) {
		if (fNbBtns > 16) {
			throw new IllegalArgumentException("Maximum button count exceeded");
		} else if (fNbBtns < 2) {
			throw new IllegalArgumentException("What do you want to do with less" + " than two buttons ?");
		}

		_listeners = new Vector();

		ActionListener lActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent fEvent) {
				int lId = -1;
				try {
					lId = Integer.parseInt(fEvent.getActionCommand());
				} catch (NumberFormatException e) {
					throw new RuntimeException("Should never have reach here !");
				}
				_fireNewEvent(lId);
			}
		};

		_btns = new JButton[fNbBtns];
		for (int li = 0; li < fNbBtns; li++) {
			JButton lBtn = _createButtonOfIdx(li);
			int lId = _getIdFromIdx(li);
			lBtn.setActionCommand("" + lId);
			lBtn.addActionListener(lActionListener);
			_btns[li] = lBtn;
		}

		_placeButtons(_btns);
	}

	protected void _placeButtons(JButton fBtns[]) {
		Border lMarginBdr = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(lMarginBdr);

		SizeRequirements lBtnToBtnV = GraphicFactory.getVIntervalBetween(fBtns[0], fBtns[1]);

		setLayout(new FlowLayout2(FlowLayout2.CENTER_VERTICAL, 2 * lBtnToBtnV.preferred, FlowLayout2.EXPAND_TO_BREADTH));
		fBtns[0].setAlignmentX(0.5f);
		fBtns[0].setVerticalAlignment(JButton.CENTER);
		fBtns[1].setAlignmentX(0.5f);
		fBtns[1].setVerticalAlignment(JButton.CENTER);
		fBtns[2].setAlignmentX(0.5f);
		fBtns[2].setVerticalAlignment(JButton.CENTER);
		fBtns[3].setAlignmentX(0.5f);
		fBtns[3].setVerticalAlignment(JButton.CENTER);
		add(fBtns[0], new Integer(0));
		add(fBtns[1], new Integer(lBtnToBtnV.preferred));
		add(fBtns[3], new Integer(FlowLayout2.TRAILING_CLOSE));
		add(fBtns[2], new Integer(-lBtnToBtnV.preferred));

	}

	private void _verifyLegalMode(int fMode) {
		switch (fMode) {
		case SELECTING:
			break;
		case MOVING:
			break;
		case SELECTING_W_TEXT:
			break;
		case MOVING_W_TEXT:
			break;
		default:
			throw new IllegalArgumentException("UseModeLook has to be" + " among MovingItemsPanel.SELECTING"
					+ " and MovingItemsPanel.MOVING");
		}
	}

	public void setUseModeLook(int fMode) {
		_verifyLegalMode(fMode);
		_mode = fMode;

		for (int li = 0; li < _btns.length; li++) {
			_changeUseModeLookForIdx(_btns[li], li);
		}
		repaint();
	}

	public int getUseModeLook() {
		return _mode;
	}

	final protected int _getIdxFromId(int fMovementId) {
		int lRes = -1;
		int lDec = fMovementId;
		while (lDec > 0) {
			lRes++;
			lDec = lDec >> 1;
		}

		if (lRes >= _btns.length)
			lRes = -1;

		return lRes;
	}

	final protected int _getIdFromIdx(int fIdx) {
		int lRes = 1;
		for (int li = 0; li < fIdx; li++) {
			lRes = lRes << 1;
		}
		return lRes;
	}

	protected JButton _createButtonOfIdx(int fIdx) {
		JButton lRes = null;
		lRes = GraphicFactory.createIconButton(_getIconForButtonAt(fIdx));
		lRes = null;
		return lRes;
	}

	protected Icon _getIconForButtonAt(int fIdx) {
		Icon lRes = null;
		if ((_mode % 2) == MOVING)
			lRes = _selectingIcons[fIdx];
		else
			lRes = _movingIcons[fIdx];
		return lRes;
	}

	protected void _changeUseModeLookForIdx(JButton fBtn, int fIdx) {

		JButton lBtn = fBtn;
		lBtn.setDisabledIcon(null);
		lBtn.setIcon(_getIconForButtonAt(fIdx));

		lBtn.setText("L");
		int lHaut = lBtn.getPreferredSize().height;
		if (_mode < MOVING_W_TEXT) {
			lBtn.setText("");
			int lLarg = lBtn.getPreferredSize().width;
			lLarg = Math.max(lLarg, lHaut);
			lBtn.setPreferredSize(new Dimension(lLarg, lHaut));
		} else {
			lBtn.setPreferredSize(null);
			if ((_mode % 2) == MOVING)
				lBtn.setText(_selectingTexts[fIdx]);
			else
				lBtn.setText(_movingTexts[fIdx]);
			if ((fIdx % 2) == 0)
				lBtn.setHorizontalTextPosition(JButton.LEADING);
			else
				lBtn.setHorizontalTextPosition(JButton.TRAILING);
		}
		final Dimension lDim = new Dimension(3000, 3000);
		lBtn.setMaximumSize(lDim);
		lBtn.repaint();
	}

	protected String _getMotionText(int fMotionIdx) {
		String lRes;
		if ((_mode % 2) == MOVING) {
			lRes = _getSelectingText(fMotionIdx);
		} else {
			lRes = _getMovingText(fMotionIdx);
		}
		return lRes;
	}

	protected String _getSelectingText(int fMotionIdx) {
		String lRes;
		if (_mode < MOVING_W_CUST_TEXT) {
			lRes = _selectingTexts[fMotionIdx];
		} else {
			lRes = _customSelectingTexts[fMotionIdx];
		}

		return lRes;
	}

	protected String _getMovingText(int fMotionIdx) {
		String lRes;
		if (_mode < MOVING_W_CUST_TEXT) {
			lRes = _movingTexts[fMotionIdx];
		} else {
			lRes = _customMovingTexts[fMotionIdx];
		}

		return lRes;
	}

	public void setCustomMotionText(int fMotionId, String fText, boolean fbMoving) {
		int lIdx = _getIdxFromId(fMotionId);
		if (fbMoving) {
			_customMovingTexts[lIdx] = fText;
		} else {
			_customSelectingTexts[lIdx] = fText;
		}
	}

	protected class ListeningPair {
		protected Object _listener;
		protected int _eventType;

		protected ListeningPair(Object fListener, int fWhich) {
			_listener = fListener;
			_eventType = fWhich;
		}

		protected int getWhich() {
			return _eventType;
		}

		protected Object getListener() {
			return _listener;
		}

		public boolean equals(Object fComp) {
			boolean lbRes = false;

			if (fComp instanceof ListeningPair) {
				if (getClass() != fComp.getClass()) {
					return false;
				}
				ListeningPair lComp = (ListeningPair) fComp;
				lbRes = ((lComp._listener == null && _listener == null) || (lComp._listener != null && lComp._listener.equals(_listener)))
						&& lComp._eventType == _eventType;
			}

			return lbRes;
		}
	}

	public void setVisible(int fBtnId, boolean fVisible) {
		int lIdx = _getIdxFromId(fBtnId);

		if (lIdx >= 0) {
			_btns[lIdx].setVisible(fVisible);
		}
	}

	public boolean isVisible(int fBtnId) {
		boolean lbRes = false;
		int lIdx = _getIdxFromId(fBtnId);

		if (lIdx >= 0) {
			lbRes = _btns[lIdx].isVisible();
		}

		return lbRes;
	}

	public void setEnabled(int fBtnId, boolean fEnabled) {
		int lIdx = _getIdxFromId(fBtnId);

		if (lIdx >= 0) {
			_btns[lIdx].setEnabled(fEnabled);
		}
	}

	public boolean isEnabled(int fBtnId) {
		boolean lbRes = false;
		int lIdx = _getIdxFromId(fBtnId);

		if (lIdx >= 0) {
			lbRes = _btns[lIdx].isEnabled();
		}

		return lbRes;
	}

	public void addItemsMotionListener(ItemsMotionListener fListener) {
		ListeningPair lMayNewPair = new ListeningPair(fListener, ALL_ATONCE);
		if (!_listeners.contains(lMayNewPair)) {
			_listeners.addElement(lMayNewPair);
		}
	}

	public void addItemsMotionDetailListener(ItemsMotionDetailListener fListener, int fMask) {
		if ((fMask & ~ALL_MOVEMENT) != 0) {
			throw new IllegalArgumentException("Buttons mask is invalid : " + fMask);
		}

		for (int lBit = 1; lBit <= fMask; lBit = lBit << 1) {
			if ((lBit & fMask) != 0) {
				ListeningPair lMayNewPair = new ListeningPair(fListener, lBit);
				if (!_listeners.contains(lMayNewPair)) {
					_listeners.addElement(lMayNewPair);
				}
			}
		}
	}

	public void removeItemsMotionListener(ItemsMotionListener fListener) {
		ListeningPair lMayNukePair = new ListeningPair(fListener, ALL_ATONCE);
		_listeners.removeElement(lMayNukePair);
	}

	public void removeItemsMotionDetailListener(ItemsMotionDetailListener fListener, int fMask) {
		if ((fMask & ~ALL_MOVEMENT) != 0) {
			throw new IllegalArgumentException("Buttons mask is invalid : " + fMask);
		}

		for (int lBit = 1; lBit <= fMask; lBit = lBit << 1) {
			if ((lBit & fMask) != 0) {
				ListeningPair lMayNukePair = new ListeningPair(fListener, fMask);
				_listeners.removeElement(lMayNukePair);
			}
		}
	}

	protected void _fireEventToDetailListener(ItemsMotionDetailListener fListener, ItemsMotionEvent fEvent) {
		switch (fEvent.getMovement()) {
		case SINGLE_DOWNLOAD:
			fListener.selectedForward(fEvent);
			break;
		case SINGLE_UPLOAD:
			fListener.selectedBackward(fEvent);
			break;
		case ALL_DOWNLOAD:
			fListener.allForward(fEvent);
			break;
		case ALL_UPLOAD:
			fListener.allBackward(fEvent);
			break;
		default:
			break;
		}
	}

	private void _fireNewEvent(int fBtnId) {
		ItemsMotionEvent lNewEvent = new ItemsMotionEvent(this, fBtnId);

		for (Enumeration lePairs = _listeners.elements(); lePairs.hasMoreElements();) {
			ListeningPair lPair = (ListeningPair) lePairs.nextElement();
			if (lPair.getWhich() == ALL_ATONCE) {
				((ItemsMotionListener) lPair.getListener()).itemsMoved(lNewEvent);
			} else if ((lPair.getWhich() & fBtnId) != 0) {
				ItemsMotionDetailListener lListener = (ItemsMotionDetailListener) lPair.getListener();
				_fireEventToDetailListener(lListener, lNewEvent);
			}
		}
	}

	public void setTooltipText(int fBtnId, String fText) {
		int lIdx = _getIdxFromId(fBtnId);

		if (lIdx >= 0) {
			_btns[lIdx].setToolTipText(fText);
		}
	}
}

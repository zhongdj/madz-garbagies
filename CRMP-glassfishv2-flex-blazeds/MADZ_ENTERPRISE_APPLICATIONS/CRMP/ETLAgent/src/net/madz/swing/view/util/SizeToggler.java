package net.madz.swing.view.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.ItemSelectable;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JToggleButton;
import javax.swing.RootPaneContainer;
import javax.swing.border.Border;

public class SizeToggler extends JPanel implements ActionListener,
		ItemSelectable {
	public final int DEFAULT_TOGGLE_SIZE = 13;
	JToggleButton _toggle = null;
	String _title = "";
	Icon _expandedIcon = null;
	Icon _retractedIcon = null;
	Component _hosted = null;
	// This fulfill RootPaneContainer too
	// Container _highestCont = null;
	Container _hostedParent = null;
	Object _hostedConstraint = null;
	Vector _listeners = null;
	boolean _horizResize = false;
	boolean _vertResize = true;
	Dimension _lastOpenedSize = null;

	
	public SizeToggler(String fTitle) {
		_title = fTitle;
		init(_title, 0);
	}

	
	public SizeToggler(String fTitle, int fSideSize) {
		_title = fTitle;
		init(_title, fSideSize);
	}

	void stepCreateToggle(int fSideSize) {
		

		_toggle = new JToggleButton();
		_toggle.setSelected(false);
		_toggle.setBorder(null);
		_toggle.setContentAreaFilled(false);
		_toggle.setMinimumSize(new Dimension(13, 13));
		int lSideSize = fSideSize;
		if (lSideSize < 13)
			lSideSize = DEFAULT_TOGGLE_SIZE;
		// _expandedIcon = new ArrowIcon(ArrowIcon.SOUTH, lSideSize -9,
		// lSideSize -9);
		// _retractedIcon = new ArrowIcon(ArrowIcon.EAST, lSideSize -9,
		// lSideSize -9);
		_expandedIcon = new ArrowIcon(ArrowIcon.SOUTH, 5, 8);
		_retractedIcon = new ArrowIcon(ArrowIcon.EAST, 8, 5);
		_toggle.setSelectedIcon(_expandedIcon);
		_toggle.setIcon(_retractedIcon);
		_toggle.setPreferredSize(new Dimension(lSideSize, lSideSize));
		// _toggle.setPreferredSize(new Dimension(5, 5));
		_toggle.addActionListener(this);
	}

	
	void init(String fTitle, int fToggleSize) {
		Border lThisBorder = BorderFactory.createEmptyBorder(0, 0, 4, 0);
		setBorder(lThisBorder);
		setLayout(new BorderLayout(0, 0));

		JComponent lHeader = GraphicFactory.createSectionHeader(fTitle);

		int lSideSize = fToggleSize;
		if (lSideSize == 0)
			lSideSize = lHeader.getPreferredSize().height;
		stepCreateToggle(lSideSize);

		JPanel lPanel = new JPanel();
		lPanel.setLayout(new FlowLayout2(FlowLayout2.WEST, 0, false, true));
		_toggle.setAlignmentY(0.5f);
		if (lPanel.add(_toggle) == null) {
		}

		lHeader.setAlignmentY(0.5f);
		lPanel.add(lHeader, new Integer(5));

		add(lPanel, BorderLayout.NORTH);

		setMaximumSize(new Dimension(getMaximumSize().width,
				getPreferredSize().height));
	}


	public String toString() {
		String lRes = "";
		lRes += "SizeToggler : title = " + _title + "\n" + "	manages = "
				+ _hosted + "\n";
		return lRes;
	}

	
	private Container getResizableRoot() {
		Container lRes = null;

		
		if (_hostedParent == null) {
			throw new RuntimeException("Where should I add the eclipsable ?"
					+ " as its parent is null !");
		} else if (_hostedParent instanceof JRootPane) {
			lRes = ((JRootPane) _hostedParent).getParent();
		}
		// usual case : a JComponent -> ask for the parent of its root pane
		else if (_hostedParent instanceof JComponent) {
			JRootPane lRoot = ((JComponent) _hostedParent).getRootPane();
			if (lRoot != null)
				lRes = lRoot.getParent();
			else
				lRes = lRoot;
		}
		// if we are just under an AWT window or dialog -> UNTESTED
		else if (_hostedParent.getParent() instanceof Window) {
			lRes = _hostedParent.getParent();
		}
		// if we are to insert directly in a window -> UNTESTED
		else if (_hostedParent instanceof Window) {
			lRes = _hostedParent;
		}

		if (lRes != null) {
			if (lRes instanceof JInternalFrame) {
				if (((JInternalFrame) lRes).isMaximum()) {
					lRes = null;
				}
			}
		}

		return lRes;
	}

	
	public Component setEclipsable(Component fComp, Container fControledParent,
			Object fConstraint) {
		return _setEclipsable(fComp, fControledParent, fConstraint);
	}

	
	public Component getEclipsable() {
		return _hosted;
	}

	
	public void setHResizable(boolean fbHResizable) {
		_horizResize = fbHResizable;
	}

	
	public void setVResizable(boolean fbVResizable) {
		_vertResize = fbVResizable;
	}

	
	public boolean isHResizable() {
		return _horizResize;
	}

	
	public boolean isVResizable() {
		return _vertResize;
	}

	
	private Component _setEclipsable(Component fComp,
			Container fControledParent, Object fConstraint) {
		Component lRes = null;

		if (fComp != null && fControledParent == null) {
			// where to put fComp component ?
			throw new RuntimeException("Can't add to nothing");
		}

		_hostedConstraint = fConstraint;

		// if this change is really a change ...
		if (fComp != null && !fComp.equals(_hosted)) {
			lRes = _hosted;
			if (_hosted != null && _toggle.isSelected()) {
				toggleHostedVisibility(false);
				if (fComp == null)
					_toggle.setSelected(false);

				_hostedParent.validate();
			}

			_hosted = fComp;
			_hostedParent = fControledParent;

			if (_hosted != null && !fControledParent.isAncestorOf(fComp)) {
				fControledParent.add(fComp, fConstraint);
				fComp.setVisible(false);
			}

			if (_hosted != null && _toggle.isSelected()) {
				toggleHostedVisibility(true);

				_hostedParent.validate();
			}
		}

		return lRes;
	}

	
	private void _calcBeforesAndAftersAndAdd(Container fRoot,
			boolean fbRetract, Dimension mWouldPrefBefore,
			Dimension mWouldPrefAfter) {
		// System.out.println(">_calcBeforesAndAftersAndAdd");
		LayoutManager lRootLayout = null;
		if (fRoot != null)
			lRootLayout = fRoot.getLayout();

		if (lRootLayout != null) {
			mWouldPrefBefore.setSize(lRootLayout
					.preferredLayoutSize(getParent()));

			_hosted.setVisible(!fbRetract);

			mWouldPrefAfter.setSize(lRootLayout
					.preferredLayoutSize(getParent()));
		} else {
			// won't do much but won't core !
			mWouldPrefBefore.setSize(fRoot.getPreferredSize());

			_hosted.setVisible(!fbRetract);

			mWouldPrefAfter.setSize(fRoot.getPreferredSize());
		}
		// System.out.println("<_calcBeforesAndAftersAndAdd");
	}

	
	void toggleHostedVisibility(boolean fbRetract) {
		try {

			Container lRoot = getResizableRoot();

			// System.out.println("pref before : "+lWouldPrefBefore+"\n"+
			// "pref after : "+lWouldPrefAfter);
			Container lContentPane = null;
			if (lRoot != null) {
				try {
					if (!(lRoot instanceof JRootPane)
							&& (lRoot instanceof RootPaneContainer))
						lContentPane = ((RootPaneContainer) lRoot)
								.getContentPane();
				} catch (Exception e) {

					lContentPane = null;
				}
			}

			if (lContentPane != null) {
				Dimension lContentSize = lContentPane.getSize();
				Rectangle lNowBounds = new Rectangle(lRoot.getBounds());
				Dimension lAncHighSize = new Dimension(lContentSize.width,
						lContentSize.height);
				Dimension lDiff = new Dimension(lNowBounds.width
						- lAncHighSize.width, lNowBounds.height
						- lAncHighSize.height);
				Dimension lNewHighSize = null;

				if (_hostedConstraint != null
						&& (_hostedConstraint instanceof GridBagConstraints)) {
					GridBagConstraints lConstraints = (GridBagConstraints) _hostedConstraint;

					Dimension lWouldPrefBefore = new Dimension();
					Dimension lWouldPrefAfter = new Dimension();

					_calcBeforesAndAftersAndAdd(lRoot, fbRetract,
							lWouldPrefBefore, lWouldPrefAfter);

					float lMultH = (float) (1 - lConstraints.weightx);
					float lMultV = (float) (1 - lConstraints.weighty);

					if (!fbRetract) {
						if (Float.isNaN(0.0f / lMultH)) {
							lMultH = 1 / lMultH;
						}
						if (Float.isNaN(0.0f / lMultV)) {
							lMultV = 1 / lMultV;
						}
					}

					float lfNewH = (0.0f + lAncHighSize.width - lWouldPrefBefore.width)
							* lMultH + lWouldPrefAfter.width;
					float lfNewV = (0.0f + lAncHighSize.height - lWouldPrefBefore.height)
							* lMultV + lWouldPrefAfter.height;

					lNewHighSize = new Dimension(Math.round(lfNewH), Math
							.round(lfNewV));
				} else if (!fbRetract) {
					_hosted.setVisible(!fbRetract);
					Dimension lPref = _hosted.getPreferredSize();
					lNewHighSize = new Dimension(lContentSize.width
							+ lPref.width, lContentSize.height + lPref.height);
				} else {
					_hosted.setVisible(!fbRetract);
					Dimension lNowSize = _hosted.getSize();
					// lNewHighSize = lWouldPrefAfter;
					lNewHighSize = new Dimension(lContentSize.width
							- lNowSize.width, lContentSize.height
							- lNowSize.height);
				}

				//System.out.println("anc size :"+lAncHighSize+"\n"+"new size :"
				// +lNewHighSize);
				if ((lRoot != null)
						&& (!(lRoot instanceof JInternalFrame) || !((JInternalFrame) lRoot)
								.isMaximum())) {
					int lNewWidth;
					int lNewHeight;
					if (_horizResize) {
						lNewWidth = lNewHighSize.width + lDiff.width;
					} else {
						lNewWidth = lNowBounds.width;
					}
					if (_vertResize) {
						lNewHeight = lNewHighSize.height + lDiff.height;
					} else {
						lNewHeight = lNowBounds.height;
					}
					lRoot.setSize(new Dimension(lNewWidth, lNewHeight));
					lRoot.validate();
				} else if (lRoot != null) {
					lRoot.invalidate();
					lRoot.validate();
				}
			}
		} catch (Exception e) {

		}
	}


	public void actionPerformed(ActionEvent event) {
		if (event.getSource() != null && event.getSource().equals(_toggle)) {
			Container lRoot = getResizableRoot();
			if (_hosted != null && lRoot != null && lRoot.getLayout() != null
					&& _hostedParent != null) {
				toggleHostedVisibility(!_toggle.isSelected());
			} else if (_hosted != null && _hostedParent != null) {
				_hosted.setVisible(_toggle.isSelected());
			}

			if (_hostedParent != null) {
				// _hostedParent.validate();
			}
			fireItemEvent();
		}
	}

	
	public void addItemListener(ItemListener fListener) {
		synchronized (this) {
			if (_listeners == null) {
				_listeners = new Vector();
			}
		}
		_listeners.addElement(fListener);
	}

	
	public void removeItemListener(ItemListener fListener) {
		if (_listeners != null) {
			_listeners.removeElement(fListener);
		}
	}

	
	protected void fireItemEvent() {
		if (_listeners != null) {
			ItemEvent lEvent = new ItemEvent(this,
					ItemEvent.ITEM_STATE_CHANGED, this,
					(_toggle.isSelected() ? ItemEvent.SELECTED
							: ItemEvent.DESELECTED));
			for (Enumeration leListeners = _listeners.elements(); leListeners
					.hasMoreElements();) {
				ItemListener lListener = (ItemListener) leListeners
						.nextElement();
				lListener.itemStateChanged(lEvent);
			}
		}
	}

	public boolean isSelected() {
		return _toggle.isSelected();
	}

	
	public void setSelected(boolean fbSelected) {
		if (fbSelected != _toggle.isSelected()) {
			_toggle.setSelected(fbSelected);
			toggleHostedVisibility(!fbSelected);
			fireItemEvent();
		}
	}

	
	public Object[] getSelectedObjects() {
		Object[] lRes = null;

		if (_toggle.isSelected()) {
			lRes = new Object[1];
			lRes[0] = this;
		}

		return lRes;
	}
}

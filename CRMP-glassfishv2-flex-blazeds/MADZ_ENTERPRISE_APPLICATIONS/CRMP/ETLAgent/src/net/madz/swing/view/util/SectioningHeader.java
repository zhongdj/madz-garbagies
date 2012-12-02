package net.madz.swing.view.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class SectioningHeader extends JComponent implements SwingConstants {
	static final Dimension _DUMMY_SIZE = new Dimension(1, 1);
	static final int _GAP = 4;
	static final int _LINE_MINLONG = 3;
	String _title;
	static Border _defaultAsTheBorder = new EtchedBorder();
	Border _asTheBorder;

	private Vector _titleVect = null;

	private boolean _displayLine = true;

	public SectioningHeader(String fTitle) {
		_title = fTitle;
		_asTheBorder = _defaultAsTheBorder;
	}

	public SectioningHeader(Vector fTitle) {
		_titleVect = fTitle;
		_asTheBorder = _defaultAsTheBorder;
	}

	public SectioningHeader(String fTitle, Border fBorder) {
		_title = fTitle;
		_asTheBorder = fBorder;
	}

	Dimension _computePreferredSize() {
		int lHInsets = 0;
		int lVInsets = 0;
		Insets lInsets = getInsets();
		if (lInsets != null) {
			lHInsets = lInsets.left + lInsets.right;
			lVInsets = lInsets.top + lInsets.bottom;
		}

		Font lFont = getFont();
		if (lFont == null)
			throw new RuntimeException("Look at font things");

		FontMetrics lFM = getFontMetrics(lFont);
		int lTitleWidth = 0;

		if (_titleVect != null) {
			for (int i = 0; i < _titleVect.size(); i++) {
				Object o = (Object) _titleVect.elementAt(i);

				if (o instanceof String) {
					lTitleWidth += lFM.stringWidth((String) o) + _GAP;
				}

				else if (o instanceof Icon) {
					lTitleWidth += ((Icon) o).getIconWidth() + _GAP;
				}

				else {

				}
			}
		}

		else if (_title != null) {
			lTitleWidth = lFM.stringWidth(_title) + _GAP;
		}

		int lTitleHeight = lFM.getHeight();
		if ((_titleVect != null) && (!_titleVect.isEmpty())) {
			for (int i = 0; i < _titleVect.size(); i++) {
				Object o = (Object) _titleVect.elementAt(i);

				if (o instanceof Icon) {
					if (((Icon) o).getIconHeight() > lTitleHeight) {
						lTitleHeight = ((Icon) o).getIconHeight();
					}
				}
			}
		}

		return new Dimension(lTitleWidth + _LINE_MINLONG + lHInsets, lTitleHeight + lVInsets);
	}

	public Dimension getMaximumSize() {
		Dimension lRes = super.getMaximumSize();
		if (lRes == null || lRes.equals(_DUMMY_SIZE)) {
			lRes = _computePreferredSize();
			lRes.width = Integer.MAX_VALUE;
		}

		return lRes;
	}

	public Dimension getMinimumSize() {
		Dimension lRes = super.getMinimumSize();
		if (lRes == null || lRes.equals(_DUMMY_SIZE)) {
			lRes = _computePreferredSize();
		}

		return lRes;
	}

	public Dimension getPreferredSize() {
		Dimension lRes = super.getPreferredSize();
		if (lRes == null || lRes.equals(_DUMMY_SIZE)) {
			lRes = _computePreferredSize();
		}

		return lRes;
	}

	private void _drawLine(Graphics fg, int fYTop, int fXLeft, int fXRight) {
		Rectangle lSaveRect = fg.getClipBounds();
		Insets lBInsets = _asTheBorder.getBorderInsets(this);

		Rectangle lClipRect = new Rectangle(fXLeft, fYTop, fXRight - fXLeft, lBInsets.top);
		Rectangle lPaintRect = new Rectangle(fXLeft - lBInsets.left, fYTop, fXRight - fXLeft + lBInsets.right + lBInsets.left, lBInsets.top
				+ lBInsets.bottom + 1);
		fg.setClip(lClipRect);
		_asTheBorder.paintBorder(this, fg, lPaintRect.x, lPaintRect.y, lPaintRect.width, lPaintRect.height);
		fg.setClip(lSaveRect);
	}

	public void paint(Graphics fg) {
		Dimension lDim = getSize();
		Insets lInsets = getInsets();
		Font lFont = getFont();
		FontMetrics lFM = getFontMetrics(lFont);

		int lx = 0;
		int ly = 0;
		int lTitleWidth = 0;

		if (lInsets != null) {
			lDim.width -= lInsets.right;
			lDim.height -= lInsets.bottom;
			lx += lInsets.left;
			ly += lInsets.top;
			lTitleWidth += lInsets.left;
		}

		if ((_titleVect != null) && (!_titleVect.isEmpty())) {
			for (int i = 0; i < _titleVect.size(); i++) {
				Object o = (Object) _titleVect.elementAt(i);

				if (o instanceof String) {
					lTitleWidth += lFM.stringWidth((String) o) + _GAP;
				}

				else if (o instanceof Icon) {
					lTitleWidth += ((Icon) o).getIconWidth() + _GAP;
				}

				else {

				}
			}
		}

		else if (_title != null) {
			lTitleWidth += lFM.stringWidth(_title) + _GAP;
		}

		fg.setColor(getBackground());
		if (isOpaque())
			fg.fillRect(lx, ly, lDim.width, lDim.height);

		int lYLine = ly + lFM.getAscent() - 1;

		fg.setColor(getForeground());
		fg.setFont(getFont());

		if ((_titleVect != null) && (!_titleVect.isEmpty())) {
			for (int i = 0; i < _titleVect.size(); i++) {
				Object o = (Object) _titleVect.elementAt(i);

				if (o instanceof String) {
					fg.drawString((String) o, lx, lYLine);
					lx += lFM.stringWidth((String) o) + _GAP;
				}

				else if (o instanceof Icon) {
					((Icon) o).paintIcon(this, fg, lx, lYLine - ((Icon) o).getIconHeight());
					lx += ((Icon) o).getIconWidth() + _GAP;
				}

				else {
					// Trace already written in the previous loop.
				}
			}
		}

		else if (_title != null) {
			fg.drawString(_title, lx, lYLine);
		}

		if (_displayLine) {
			_drawLine(fg, lYLine, lTitleWidth, lDim.width);
		}
	}

	public void setTitle(String title) {
		_titleVect = null;
		_title = title;
		repaint();
	}

	public void setTitle(Vector title) {
		_titleVect = title;
		repaint();
	}

	public void displayLine(boolean displayLine) {
		_displayLine = displayLine;
	}

	public String getTitleAsString() {
		if (_titleVect == null) {
			return _title;
		} else {
			StringBuffer titleAsString = new StringBuffer("");
			for (int i = 0; i < _titleVect.size(); i++) {
				if (_titleVect.elementAt(i) instanceof String) {
					if (titleAsString.length() != 0) {
						titleAsString.append("   ");
					}
					titleAsString.append(_titleVect.elementAt(i));
				}
			}
			return (titleAsString.toString());
		}
	}
}

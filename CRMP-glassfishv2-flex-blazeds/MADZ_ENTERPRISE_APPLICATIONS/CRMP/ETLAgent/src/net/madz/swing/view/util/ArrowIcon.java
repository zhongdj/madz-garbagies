package net.madz.swing.view.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.SwingConstants;

public class ArrowIcon implements Icon, SwingConstants {
	private static int DEFAULT_WIDTH = 9;
	private static int DEFAULT_HEIGHT = 18;

	protected int _width;
	protected int _height;

	private int _nbPts;
	private int[] _xPoints;
	private int[] _yPoints;

	public ArrowIcon(int fDirection, Dimension fSize) {
		_init(fDirection, fSize.width, fSize.height);
	}

	public ArrowIcon(int fDirection, int fWidth, int fHeight) {
		_init(fDirection, fWidth, fHeight);
	}

	protected void _init(int fDirection, int fWidth, int fHeight) {
		switch (fDirection) {
		case EAST:
			break;
		case WEST:
			break;
		case NORTH:
			break;
		case SOUTH:
			break;
		default:
			throw new IllegalArgumentException("direction must be a side "
					+ "compass-direction");
		}

		_width = fWidth;
		_height = fHeight;

		_nbPts = 3;
		boolean lbNeedMoreH = (_width % 2 == 0)
				&& ((fDirection == NORTH) || (fDirection == SOUTH));
		boolean lbNeedMoreV = (_height % 2 == 0)
				&& ((fDirection == WEST) || (fDirection == EAST));
		if (lbNeedMoreH || lbNeedMoreV) {
			_nbPts = 4;
		}

		_xPoints = new int[_nbPts];
		_yPoints = new int[_nbPts];

		switch (fDirection) {
		case NORTH:
			_xPoints[0] = -1;
			_yPoints[0] = _height - 1;
			_xPoints[1] = _width;
			_yPoints[1] = _height - 1;
			_xPoints[2] = _width / 2;
			_yPoints[2] = 0;
			if (lbNeedMoreH) {
				_xPoints[3] = _width / 2 - 1;
				_yPoints[3] = 0;
			}
			break;
		case WEST:
			_xPoints[0] = _width - 1;
			_yPoints[0] = -1;
			_xPoints[1] = _width - 1;
			_yPoints[1] = _height;
			_xPoints[2] = 0;
			_yPoints[2] = _height / 2;
			if (lbNeedMoreV) {
				_xPoints[3] = 0;
				_yPoints[3] = _height / 2 - 1;
			}
			break;
		case SOUTH:
			_xPoints[0] = -1;
			_yPoints[0] = 0;
			_xPoints[1] = _width;
			_yPoints[1] = 0;
			_xPoints[2] = _width / 2;
			_yPoints[2] = _height - 1;
			if (lbNeedMoreH) {
				_xPoints[3] = _width / 2 - 1;
				_yPoints[3] = _height - 1;
			}
			break;
		case EAST:
			_xPoints[0] = 0;
			_yPoints[0] = -1;
			_xPoints[1] = 0;
			_yPoints[1] = _height;
			_xPoints[2] = _width - 1;
			_yPoints[2] = _height / 2;
			if (lbNeedMoreV) {
				_xPoints[3] = _width - 1;
				_yPoints[3] = _height / 2 - 1;
			}
			break;
		default:
			break;
		}
	}

	public int getIconHeight() {
		return _height;
	}

	public int getIconWidth() {
		return _width;
	}

	public void paintIcon(Component fComp, Graphics fg, int fx, int fy) {
		int lLength = _xPoints.length;
		int lTranslatedXPoints[] = new int[lLength];
		int lTranslatedYPoints[] = new int[lLength];

		for (int li = 0; li < lLength; li++) {
			lTranslatedXPoints[li] = _xPoints[li] + fx;
			lTranslatedYPoints[li] = _yPoints[li] + fy;
		}

		if (fComp.isEnabled()) {
			fg.setColor(Color.black);
		} else {
			fg.setColor(Color.gray);
		}

		fg.fillPolygon(lTranslatedXPoints, lTranslatedYPoints, lLength);
	}
}

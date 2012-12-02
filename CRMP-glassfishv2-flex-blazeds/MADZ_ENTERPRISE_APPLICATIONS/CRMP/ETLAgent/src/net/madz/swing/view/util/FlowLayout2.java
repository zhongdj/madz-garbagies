package net.madz.swing.view.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.util.Hashtable;

import javax.swing.SwingConstants;

public class FlowLayout2 implements LayoutManager2, SwingConstants {
	private static final int _GIVEN_LENGTH = 0;
	private static final int _GIVEN_BREATH = 1;
	private static final int _FREE_BREATH = 2;
	private static final int _BACK_H = 3;
	private static final int _BACK_V = 4;
	private static final int _MIN_ABS_ABS = 5;
	private static final int _MIN_ORD_ABS = 6;
	private static final int _TRAILING_CLOSE = Integer.MIN_VALUE;

	public static final int TRAILING_CLOSE = _TRAILING_CLOSE;
	public static final Integer TRAILINGCLOSE = new Integer(_TRAILING_CLOSE);
	public static final int CENTER_VERTICAL = NORTH_WEST + 1;
	public static final int CENTER_HORIZONTAL = CENTER_VERTICAL + 1;
	public static final boolean EXPAND_TO_BREADTH = true;
	public static final boolean ALIGNMENTS_ON_BREADTH = false;
	public static final boolean LAST_GROWS = true;
	protected Hashtable _deltas;
	private int _orient;
	private int _middleGap;
	private Float _breadthAlignment;
	private boolean _calculateBreadthAlignment;
	private boolean _expandToBreadth;
	private boolean _lastIsElastic = false;
	private boolean _strictContainment;
	protected static final Point ORIGIN = new Point();

	public FlowLayout2() {
		init(WEST, 5, new Float(0.5f), false, false);
	}

	public FlowLayout2(int fOrient, int fMiddleGap, Float fAlign) {
		init(fOrient, fMiddleGap, fAlign, false, false);
	}

	public FlowLayout2(int fOrient, int fMiddleGap, boolean fbExpandToBreadth) {
		init(fOrient, fMiddleGap, null, fbExpandToBreadth, false);
	}

	public FlowLayout2(int fOrient, int fMiddleGap, Float fAlign,
			boolean fbIsElastic) {
		init(fOrient, fMiddleGap, fAlign, false, fbIsElastic);
	}

	public FlowLayout2(int fOrient, int fMiddleGap, boolean fbExpandToBreadth,
			boolean fbIsElastic) {
		init(fOrient, fMiddleGap, null, fbExpandToBreadth, fbIsElastic);
	}

	protected void init(int fOrient, int fMiddleGap, Float fAlign,
			boolean fbExpandToBreadth, boolean fbIsElastic) {
		switch (fOrient) {

		case EAST:
			break;
		case WEST:
			break;
		case NORTH:
			break;
		case SOUTH:
			break;
		case CENTER_HORIZONTAL:
			break;
		case CENTER_VERTICAL:
			break;
		default:
			throw new IllegalArgumentException("anchor must be a side "
					+ "compass-direction or" + "CENTER_HORIZONTAL or"
					+ "CENTER_VERTICAL");
		}
		_orient = fOrient;
		_middleGap = fMiddleGap;
		_deltas = new Hashtable();
		_breadthAlignment = fAlign;
		_calculateBreadthAlignment = (fAlign == null);
		_expandToBreadth = fbExpandToBreadth;
		_lastIsElastic = fbIsElastic;
		_strictContainment = !isHorizontalDir(_orient);

		if (!_calculateBreadthAlignment) {
			float lf = fAlign.floatValue();
			if (lf < 0.0f || lf > 1.0f) {
				throw new IllegalArgumentException(
						"Breadth alignment should be"
								+ " between 0.0f and 1.0f");
			}
		}
	}

	public void setStrictBreadthContainment(boolean fbStrict) {
		_strictContainment = fbStrict;
	}

	public boolean isStrictBreadthContainment() {
		return _strictContainment;
	}

	public void setBreadthAlignment(Float fAlign) {
		_breadthAlignment = fAlign;
		_calculateBreadthAlignment = (fAlign == null);
	}

	public Float getBreadthAlignment() {
		return _breadthAlignment;
	}

	public void setExpandToBreadth(boolean fbExpandToBreadth) {
		_expandToBreadth = fbExpandToBreadth;
		if (fbExpandToBreadth) {
			setBreadthAlignment(null);
		}
	}

	public boolean getExpandToBreadth() {
		return _expandToBreadth;
	}

	public void setOrientation(int fOrient) {
		_orient = fOrient;
	}

	public int getOrientation() {
		return _orient;
	}

	public void setMiddleGap(int fGapSize) {
		_middleGap = fGapSize;
	}

	public int getMiddleGap() {
		return _middleGap;
	}

	public void setLastIsElastic(boolean fbElastic) {
		_lastIsElastic = fbElastic;
	}

	public boolean isLastElastic() {
		return _lastIsElastic;
	}

	public void addLayoutComponent(String name, Component comp) {
	}

	public Integer getComponentGap(Component fComp) {
		Integer lRes = null;
		if (fComp != null) {
			lRes = (Integer) _deltas.get(fComp);
		}
		return lRes;
	}

	public void changeComponentGap(Component fComp, Integer foGap) {
		if (fComp != null) {
			if (foGap != null) {
				_deltas.put(fComp, foGap);
			} else {
				_deltas.remove(fComp);
			}
		}
	}

	public void addLayoutComponent(Component fComp, Object fConstraints) {
		if (fComp != null) {
			Object lConstraints = fConstraints;
			if (lConstraints != null && !(lConstraints instanceof Number)) {
				throw new IllegalArgumentException("Constraints are "
						+ "instances of Integer");
			}

			if (lConstraints != null) {
				_deltas.put(fComp, lConstraints);
			}
		}
	}

	public void invalidateLayout(Container target) {
		if (_calculateBreadthAlignment)
			_breadthAlignment = null;
	}

	public void removeLayoutComponent(Component fComp) {
		_deltas.remove(fComp);
	}

	public float getLayoutAlignmentX(Container fParent) {
		float lRes;
		switch (_orient) {
		case CENTER_HORIZONTAL:
			lRes = 0.5f;
			break;
		case WEST:
			lRes = 0.0f;
			break;
		case EAST:
			lRes = 1.0f;
			break;
		default:
			// compute layout size makes _breadthAlignement != null
			if (_breadthAlignment == null) {
				if (_computeLayoutSize(fParent, null) == null) {
				}
			}
			if (_expandToBreadth)
				lRes = 0.5f;
			else
				lRes = _breadthAlignment.floatValue();
			break;
		}
		return lRes;
	}

	public float getLayoutAlignmentY(Container fParent) {
		float lRes;
		switch (_orient) {
		case CENTER_VERTICAL:
			lRes = 0.5f;
			break;
		case NORTH:
			lRes = 0.0f;
			break;
		case SOUTH:
			lRes = 1.0f;
			break;
		default:
			// compute layout size makes _breadthAlignement != null
			if (_breadthAlignment == null) {
				if (_computeLayoutSize(fParent, null) == null) {
				}
			}
			if (_expandToBreadth)
				lRes = 0.5f;
			else
				lRes = _breadthAlignment.floatValue();
			break;
		}
		return lRes;
	}

	public static boolean isHorizontalDir(int fDir) {
		boolean lbRes = false;

		switch (fDir) {
		case CENTER_HORIZONTAL:
			lbRes = true;
			break;
		case WEST:
			lbRes = true;
			break;
		case EAST:
			lbRes = true;
			break;
		default:
			break;
		}

		return lbRes;
	}

	protected int _getGapFor(Component fComp) {
		int lRes = _middleGap;
		Integer loGap = null;
		if (fComp != null) {
			loGap = (Integer) _deltas.get(fComp);
		}
		if (loGap != null) {
			lRes = ((Integer) loGap).intValue();
		}
		return lRes;
	}

	private Dimension _computeLayoutSize(Container fParent, float[] mfRepart) {
		Dimension lRes = new Dimension(0, 0);
		int lAbscis = _middleGap;
		float lOverBreadth = 0.0f;
		float lUnderBreadth = 0.0f;

		synchronized (fParent.getTreeLock()) {
			int lNbMembers = fParent.getComponentCount();
			for (int lIdx = 0; lIdx < lNbMembers; lIdx++) {
				Component lComp = fParent.getComponent(lIdx);
				if (lComp.isVisible()) {
					int lDelta = _getGapFor(lComp);
					if (lDelta == _TRAILING_CLOSE)
						lDelta = 0;
					else
						lDelta = Math.abs(lDelta);

					lAbscis += lDelta;

					Dimension lCompDim = null;
					lCompDim = lComp.getPreferredSize();

					int lBreadth = 0;
					if (isHorizontalDir(_orient)) {
						lDelta = lCompDim.width;
						lBreadth = lCompDim.height;
					} else {
						lDelta = lCompDim.height;
						lBreadth = lCompDim.width;
					}
					lAbscis += lDelta;

					float lfCompAlign = lComp.getAlignmentX();
					if (isHorizontalDir(_orient))
						lfCompAlign = lComp.getAlignmentY();

					lOverBreadth = Math.max(lOverBreadth, lBreadth
							* lfCompAlign);
					lUnderBreadth = Math.max(lUnderBreadth, lBreadth
							* (1 - lfCompAlign));
				}
			}
		}

		if (isHorizontalDir(_orient)) {
			lRes.width = lAbscis;
			lRes.height = Math.round(lOverBreadth) + Math.round(lUnderBreadth);
		} else {
			lRes.height = lAbscis;
			lRes.width = Math.round(lOverBreadth) + Math.round(lUnderBreadth);
		}

		Insets lInsets = fParent.getInsets();
		lRes.width += lInsets.left + lInsets.right;
		lRes.height += lInsets.top + lInsets.bottom;

		if (mfRepart != null) {
			mfRepart[0] = lOverBreadth;
			mfRepart[1] = lUnderBreadth;
		}

		if (_calculateBreadthAlignment) {
			_breadthAlignment = new Float(lOverBreadth
					/ (lOverBreadth + lUnderBreadth));
		}

		return lRes;
	}

	public Dimension minimumLayoutSize(Container fParent) {
		// as we won't give more space (we don't restrict it neither).
		return _computeLayoutSize(fParent, null);
	}

	public Dimension preferredLayoutSize(Container fParent) {
		return _computeLayoutSize(fParent, null);
	}

	public Dimension maximumLayoutSize(Container fParent) {
		Dimension lRes = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		return lRes;
	}

	private void _storePrefLengthesBreathesAndGaps(Container fParent,
			int[] mLengthes, int[] mBreadthes, int[] mGaps) {
		int lNbMembers = fParent.getComponentCount();
		Dimension lCompDim = null;
		Component lComp = null;
		if (isHorizontalDir(_orient)) {
			for (int lIdx = 0; lIdx < lNbMembers; lIdx++) {
				lComp = fParent.getComponent(lIdx);
				if (lComp.isVisible()) {
					lCompDim = lComp.getPreferredSize();
					mLengthes[lIdx] = lCompDim.width;
					mBreadthes[lIdx] = lCompDim.height;
					mGaps[lIdx] = _getGapFor(lComp);
				} else {
					mLengthes[lIdx] = 0;
					mBreadthes[lIdx] = 0;
					mGaps[lIdx] = 0;
				}
			}
		} else {
			for (int lIdx = 0; lIdx < lNbMembers; lIdx++) {
				lComp = fParent.getComponent(lIdx);
				if (lComp.isVisible()) {
					lCompDim = lComp.getPreferredSize();
					mLengthes[lIdx] = lCompDim.height;
					mBreadthes[lIdx] = lCompDim.width;
					mGaps[lIdx] = _getGapFor(lComp);
				} else {
					mLengthes[lIdx] = 0;
					mBreadthes[lIdx] = 0;
					mGaps[lIdx] = 0;
				}
			}
		}
	}

	private void _setSizesAndLocations(Container fParent, int[] fLengthes,
			int[] fBreadthes, int[] fAbscisses, int[] fOrdonnees) {
		int lNbMembers = fParent.getComponentCount();
		Component lComp = null;
		Dimension lCompDim = null;
		if (isHorizontalDir(_orient)) {
			for (int lIdx = 0; lIdx < lNbMembers; lIdx++) {
				lComp = fParent.getComponent(lIdx);
				if (lComp.isVisible()) {
					lCompDim = new Dimension(fLengthes[lIdx], fBreadthes[lIdx]);
					lComp.setSize(lCompDim);
					lComp.setLocation(fAbscisses[lIdx], fOrdonnees[lIdx]);
				}
			}
		} else {
			for (int lIdx = 0; lIdx < lNbMembers; lIdx++) {
				lComp = fParent.getComponent(lIdx);
				if (lComp.isVisible()) {
					lCompDim = new Dimension(fBreadthes[lIdx], fLengthes[lIdx]);
					lComp.setSize(lCompDim);
					lComp.setLocation(fOrdonnees[lIdx], fAbscisses[lIdx]);
				}
			}
		}
	}

	private void _calcParameters(Container fParent, Dimension fPrefDim,
			int[] mParameters) {
		Insets lInsets = fParent.getInsets();
		int lGivenLength = 0;
		int lBackH = 0;
		int lBackV = 0;
		int lFreeBreadth = 0;
		int lGivenBreadth = 0;
		int lMinAbsAbs = 0;
		int lMinOrdAbs = 0;

		switch (_orient) {
		case EAST:
			lBackH = 1; // no break on purpose
			/* falls through */
		case WEST:
			lGivenLength = fParent.getSize().width - lInsets.left
					- lInsets.right;
			lGivenBreadth = fParent.getSize().height - lInsets.top
					- lInsets.bottom;
			lFreeBreadth = lGivenBreadth - fPrefDim.height;
			lMinAbsAbs = lInsets.left;
			lMinOrdAbs = lInsets.top;
			break;
		case SOUTH:
			lBackV = 1; // no break on purpose
			/* falls through */
		case NORTH:
			lGivenLength = fParent.getSize().height - lInsets.top
					- lInsets.bottom;
			lGivenBreadth = fParent.getSize().width - lInsets.left
					- lInsets.right;
			lFreeBreadth = lGivenBreadth - fPrefDim.width;
			lMinAbsAbs = lInsets.top;
			lMinOrdAbs = lInsets.left;
			break;
		case CENTER_HORIZONTAL:
			lGivenLength = fPrefDim.width - lInsets.left - lInsets.right;
			lGivenBreadth = fParent.getSize().height - lInsets.top
					- lInsets.bottom;
			lFreeBreadth = lGivenBreadth - fPrefDim.height;
			lMinAbsAbs = (fParent.getSize().width - fPrefDim.width) / 2;
			lMinOrdAbs = lInsets.top;
			break;
		case CENTER_VERTICAL:
			lGivenLength = fPrefDim.height - lInsets.top - lInsets.bottom;
			lGivenBreadth = fParent.getSize().width - lInsets.left
					- lInsets.right;
			lFreeBreadth = lGivenBreadth - fPrefDim.width;
			lMinAbsAbs = (fParent.getSize().height - fPrefDim.height) / 2;
			lMinOrdAbs = lInsets.left;
			break;
		default:
			break;
		}

		mParameters[_GIVEN_LENGTH] = lGivenLength;
		mParameters[_GIVEN_BREATH] = lGivenBreadth;
		mParameters[_FREE_BREATH] = lFreeBreadth;
		mParameters[_BACK_H] = lBackH;
		mParameters[_BACK_V] = lBackV;
		mParameters[_MIN_ABS_ABS] = lMinAbsAbs;
		mParameters[_MIN_ORD_ABS] = lMinOrdAbs;
	}

	private void _modifLengthes(Container fParent, int fGivenLength,
			int[] mLengthes, int[] fGaps) {
		int lNbMembers = fParent.getComponentCount();
		int lGap = 0;
		int lTotalLength = _middleGap;
		if (lTotalLength == _TRAILING_CLOSE) {
			lTotalLength = 0;
		}
		for (int lIdx = 0; lIdx < lNbMembers; lIdx++) {
			lGap = fGaps[lIdx];
			if (lGap == _TRAILING_CLOSE) {
				lGap = 0;
			} else if (lGap < 0) {
				lGap = -lGap;
			}
			lTotalLength += mLengthes[lIdx] + lGap;
		}

		// if ( fGivenLength > lTotalLength )
		if (_lastIsElastic) {
			Component lComp = null;
			Dimension lCompMaxDim = null;
			Dimension lCompMinDim = null;
			int lCompMaxLength = 0;
			int lCompMinLength = 0;
			int lLastExtensibleIdx = -1;
			int lIdx = lNbMembers - 1;
			while (lIdx >= 0 && lLastExtensibleIdx == -1) {
				lComp = fParent.getComponent(lIdx);
				if (lComp.isVisible()) {
					lCompMaxDim = lComp.getMaximumSize();
					lCompMinDim = lComp.getMinimumSize();
					if (isHorizontalDir(_orient)) {
						lCompMaxLength = lCompMaxDim.width;
						lCompMinLength = lCompMinDim.width;
					} else {
						lCompMaxLength = lCompMaxDim.height;
						lCompMinLength = lCompMinDim.height;
					}

					if (fGivenLength > lTotalLength
							&& lCompMaxLength > mLengthes[lIdx]) {
						// mLengthes[lIdx] += fGivenLength -lTotalLength;
						mLengthes[lIdx] = Math.min(mLengthes[lIdx]
								+ fGivenLength - lTotalLength, lCompMaxLength);
						lLastExtensibleIdx = lIdx;
					} else if (fGivenLength < lTotalLength
							&& lCompMinLength < mLengthes[lIdx]) {
						mLengthes[lIdx] = Math.max(mLengthes[lIdx]
								+ fGivenLength - lTotalLength, lCompMinLength);
						lLastExtensibleIdx = lIdx;
					}
				}
				lIdx--;
			}
		}
	}

	private void _modifBreadthes(Container fParent, int fGivenBreadth,
			int[] mBreadthes) {
		Component lComp = null;
		Dimension lCompMaxDim = null;
		int lNbMembers = fParent.getComponentCount();
		if (_strictContainment) {
			for (int lIdx = 0; lIdx < lNbMembers; lIdx++) {
				int lCompMaxBreadth = 0;
				if (fGivenBreadth < mBreadthes[lIdx]) {
					mBreadthes[lIdx] = fGivenBreadth;
				} else if (_expandToBreadth) {
					lComp = fParent.getComponent(lIdx);
					lCompMaxDim = lComp.getMaximumSize();
					if (!isHorizontalDir(_orient)) {
						lCompMaxBreadth = lCompMaxDim.width;
					} else {
						lCompMaxBreadth = lCompMaxDim.height;
					}
					mBreadthes[lIdx] = Math.min(fGivenBreadth, lCompMaxBreadth);
				}
			}
		} else if (_expandToBreadth) {
			int lCompMaxBreadth = 0;
			for (int lIdx = 0; lIdx < lNbMembers; lIdx++) {
				if (mBreadthes[lIdx] < fGivenBreadth) {
					lComp = fParent.getComponent(lIdx);
					lCompMaxDim = lComp.getMaximumSize();
					if (!isHorizontalDir(_orient)) {
						lCompMaxBreadth = lCompMaxDim.width;
					} else {
						lCompMaxBreadth = lCompMaxDim.height;
					}
					mBreadthes[lIdx] = Math.min(fGivenBreadth, lCompMaxBreadth);
				}
			}
		}
	}

	private void _setAbscisses(int fAbsoluteMinAbs, int fGivenLength,
			int fSens, int[] fLengthes, int[] fGaps, int[] mAbscisses) {
		int lMinAbscisse = 0;
		int lMaxAbscisse = fGivenLength;
		int lAbscisse;
		int lDelta = 0;

		for (int lIdx = 0; lIdx < fLengthes.length; lIdx++) {
			lDelta = fGaps[lIdx];
			if (lDelta >= 0) {
				lMinAbscisse += lDelta;
				lAbscisse = lMinAbscisse;
				lMinAbscisse += fLengthes[lIdx];
			} else {
				if (lDelta == _TRAILING_CLOSE)
					lDelta = 0;
				lMaxAbscisse += lDelta;
				lMaxAbscisse -= fLengthes[lIdx];
				lAbscisse = lMaxAbscisse;
			}

			mAbscisses[lIdx] = fAbsoluteMinAbs + lAbscisse * (1 - 2 * fSens)
					+ fSens * (fGivenLength - fLengthes[lIdx]);
		}
	}

	private void _setOrdonnees(Container fParent, float fBaseLine,
			int[] fBreadthes, int[] mOrdonnees) {
		if (!_expandToBreadth) {
			Component lComp = null;
			for (int lIdx = 0; lIdx < fBreadthes.length; lIdx++) {
				lComp = fParent.getComponent(lIdx);
				mOrdonnees[lIdx] = (int) Math.round(fBaseLine
						- lComp.getAlignmentY() * fBreadthes[lIdx]);
			}
		} else {
			for (int lIdx = 0; lIdx < fBreadthes.length; lIdx++) {
				mOrdonnees[lIdx] = (int) Math.round(fBaseLine
						- _breadthAlignment.floatValue() * fBreadthes[lIdx]);
			}
		}
	}

	public void layoutContainer(Container fParent) {
		float lafBreadths[] = new float[2];

		Dimension lPrefDim = _computeLayoutSize(fParent, lafBreadths);
		float lfOverBreadth = lafBreadths[0];
		float lfUnderBreadth = lafBreadths[1];

		int[] lParams = new int[_MIN_ORD_ABS + 1];
		_calcParameters(fParent, lPrefDim, lParams);
		int lGivenLength = lParams[_GIVEN_LENGTH];
		int lBackH = lParams[_BACK_H];
		int lBackV = lParams[_BACK_V];
		int lFreeBreadth = lParams[_FREE_BREATH];
		int lGivenBreadth = lParams[_GIVEN_BREATH];
		int lMinAbsAbs = lParams[_MIN_ABS_ABS];
		int lMinOrdAbs = lParams[_MIN_ORD_ABS];

		float lBaseLine = lfOverBreadth;

		if (!_expandToBreadth && lFreeBreadth >= 0.0) {
			lBaseLine += lFreeBreadth * _breadthAlignment.floatValue();
		} else {
			lBaseLine = lGivenBreadth * _breadthAlignment.floatValue();
		}

		synchronized (fParent.getTreeLock()) {
			int lSize = fParent.getComponentCount();
			int[] lLengthes = new int[lSize];
			int[] lBreadthes = new int[lSize];
			int[] lAbscisses = new int[lSize];
			int[] lOrdonnees = new int[lSize];
			int[] lGaps = new int[lSize];
			_storePrefLengthesBreathesAndGaps(fParent, lLengthes, lBreadthes,
					lGaps);
			_modifLengthes(fParent, lGivenLength, lLengthes, lGaps);
			_modifBreadthes(fParent, lGivenBreadth, lBreadthes);
			_setAbscisses(lMinAbsAbs, lGivenLength, lBackH + lBackV, lLengthes,
					lGaps, lAbscisses);
			_setOrdonnees(fParent, lBaseLine + lMinOrdAbs, lBreadthes,
					lOrdonnees);
			_setSizesAndLocations(fParent, lLengthes, lBreadthes, lAbscisses,
					lOrdonnees);
		}
	}
}

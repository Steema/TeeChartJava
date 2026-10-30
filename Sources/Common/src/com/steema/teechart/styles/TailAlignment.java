package com.steema.teechart.styles;

import static com.steema.teechart.styles.MarksTail.None;
import static com.steema.teechart.styles.MarksTail.WithPointer;

public enum TailAlignment {
	// The object or text is aligned according to chart controlled criteria
	Auto(0),
	// The object or text is aligned on the bottom wall left of the control element.
	BottomLeft(1),
	// The object or text is aligned on the bottom wall right of the control element.
	BottomRight(2),
	// The object or text is aligned in the bottom wall center of the control element.
	BottomCenter(3),
	// The object or text is aligned on the top wall left of the control element.
	TopLeft(4),
	// The object or text is aligned on the top wall right of the control element.
	TopRight(5),
	// The object or text is aligned in the top wall center of the control element.
	TopCenter(6),
	// The object or text is aligned in the left wall top of the control element.
	LeftTop(7),
	// The object or text is aligned in the left wall center of the control element.
	LeftCenter(8),
	// The object or text is aligned in the left wall bottom of the control element.
	LeftBottom(9),
	// The object or text is aligned in the right wall top of the control element.
	RightTop(10),
	// The object or text is aligned in the right wall center of the control element.
	RightCenter(11),
	// The object or text is aligned in the right wall bottom of the control element.
	RightBottom(12);

	int index;

	TailAlignment(int i) {
		index = i;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
        
        static public TailAlignment fromInt(int value) {
            switch (value) {
                case 0:  return Auto;
                case 1:  return BottomLeft;
                case 2:  return BottomRight;
                case 3:  return BottomCenter;
                case 4:  return TopLeft;
                case 5:  return TopRight;
                case 6:  return TopCenter;
                case 7:  return LeftTop;
                case 8:  return LeftCenter;
                case 9:  return LeftBottom;
                case 10:  return RightTop;
                case 11:  return RightCenter;
                case 12:  return RightBottom;
                default: return Auto;
        }
    }
}

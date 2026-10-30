package com.steema.teechart.styles;

/**
 * Created by steema on 02/03/2016.
 */
public enum MarksTail {
    None (0),
    WithPointer (1);
    
    int index;

	MarksTail(int i) {
		index = i;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
       
    static public MarksTail fromInt(int value) {
        switch (value) {
        case 0:
            return None;
        case 1:
            return WithPointer;
        default: return WithPointer;
        }
    }
}

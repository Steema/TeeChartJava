package com.steema.teechart.drawing;

import com.steema.teechart.misc.Enum;
import com.steema.teechart.misc.ImageUtils;

/**
 * <p>Title: HatchStyle class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class HatchStyle extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final HatchStyle HORIZONTAL = new HatchStyle(0);
    public static final HatchStyle VERTICAL = new HatchStyle(1);
    public static final HatchStyle FORWARDDIAGONAL = new HatchStyle(2);
    public static final HatchStyle BACKWARDDIAGONAL = new HatchStyle(3);
    public static final HatchStyle CROSS = new HatchStyle(4);
    public static final HatchStyle DIAGONALCROSS = new HatchStyle(5);
    public static final HatchStyle PERCENT05 = new HatchStyle(6);
    public static final HatchStyle PERCENT10 = new HatchStyle(7);
    public static final HatchStyle PERCENT20 = new HatchStyle(8);
    public static final HatchStyle PERCENT25 = new HatchStyle(9);
    public static final HatchStyle PERCENT30 = new HatchStyle(10);
    public static final HatchStyle PERCENT40 = new HatchStyle(11);
    public static final HatchStyle PERCENT50 = new HatchStyle(12);
    public static final HatchStyle PERCENT60 = new HatchStyle(13);
    public static final HatchStyle PERCENT70 = new HatchStyle(14);
    public static final HatchStyle PERCENT75 = new HatchStyle(15);
    public static final HatchStyle PERCENT80 = new HatchStyle(16);
    public static final HatchStyle PERCENT90 = new HatchStyle(17);
    public static final HatchStyle LIGHTDOWNWARDDIAGONAL = new HatchStyle(18);
    public static final HatchStyle LIGHTUPWARDDIAGONAL = new HatchStyle(19);
    public static final HatchStyle DARKDOWNWARDDIAGONAL = new HatchStyle(20);
    public static final HatchStyle DARKUPWARDDIAGONAL = new HatchStyle(21);
    public static final HatchStyle WIDEDOWNWARDDIAGONAL = new HatchStyle(22);
    public static final HatchStyle WIDEUPWARDDIAGONAL = new HatchStyle(23);
    public static final HatchStyle LIGHTVERTICAL = new HatchStyle(24);
    public static final HatchStyle LIGHTHORIZONTAL = new HatchStyle(25);
    public static final HatchStyle NARROWVERTICAL = new HatchStyle(26);
    public static final HatchStyle NARROWHORIZONTAL = new HatchStyle(27);
    public static final HatchStyle DARKVERTICAL = new HatchStyle(28);
    public static final HatchStyle DARKHORIZONTAL = new HatchStyle(29);
    public static final HatchStyle DASHEDDOWNWARDDIAGONAL = new HatchStyle(30);
    public static final HatchStyle DASHEDUPWARDDIAGONAL = new HatchStyle(31);
    public static final HatchStyle DASHEDHORIZONTAL = new HatchStyle(32);
    public static final HatchStyle DASHEDVERTICAL = new HatchStyle(33);
    public static final HatchStyle SMALLCONFETTI = new HatchStyle(34);
    public static final HatchStyle LARGECONFETTI = new HatchStyle(35);
    public static final HatchStyle ZIGZAG = new HatchStyle(36);
    public static final HatchStyle WAVE = new HatchStyle(37);
    public static final HatchStyle DIAGONALBRICK = new HatchStyle(38);
    public static final HatchStyle HORIZONTALBRICK = new HatchStyle(39);
    public static final HatchStyle WEAVE = new HatchStyle(40);
    public static final HatchStyle PLAID = new HatchStyle(41);
    public static final HatchStyle DIVOT = new HatchStyle(42);
    public static final HatchStyle DOTTEDGRID = new HatchStyle(43);
    public static final HatchStyle DOTTEDDIAMOND = new HatchStyle(44);
    public static final HatchStyle SHINGLE = new HatchStyle(45);
    public static final HatchStyle TRELLIS = new HatchStyle(46);
    public static final HatchStyle SPHERE = new HatchStyle(47);
    public static final HatchStyle SMALLGRID = new HatchStyle(48);
    public static final HatchStyle SMALLCHECKERBOARD = new HatchStyle(49);
    public static final HatchStyle LARGECHECKERBOARD = new HatchStyle(50);
    public static final HatchStyle OUTLINEDDIAMOND = new HatchStyle(51);
    public static final HatchStyle SOLIDDIAMOND = new HatchStyle(52);

    private HatchStyle(int value) {
        super(value);
    }

    static public HatchStyle fromInt(int value) {
        switch (value) {

        case 0:
            return HORIZONTAL;
        case 1:
            return VERTICAL;
        case 2:
            return FORWARDDIAGONAL;
        case 3:
            return BACKWARDDIAGONAL;
        case 4:
            return CROSS;
        case 5:
            return DIAGONALCROSS;
        case 6:
            return PERCENT05;
        case 7:
            return PERCENT10;
        case 8:
            return PERCENT20;
        case 9:
            return PERCENT25;
        case 10:
            return PERCENT30;
        case 11:
            return PERCENT40;
        case 12:
            return PERCENT50;
        case 13:
            return PERCENT60;
        case 14:
            return PERCENT70;
        case 15:
            return PERCENT75;
        case 16:
            return PERCENT80;
        case 17:
            return PERCENT90;
        case 18:
            return LIGHTDOWNWARDDIAGONAL;
        case 19:
            return LIGHTUPWARDDIAGONAL;
        case 20:
            return DARKDOWNWARDDIAGONAL;
        case 21:
            return DARKUPWARDDIAGONAL;
        case 22:
            return WIDEDOWNWARDDIAGONAL;
        case 23:
            return WIDEUPWARDDIAGONAL;
        case 24:
            return LIGHTVERTICAL;
        case 25:
            return LIGHTHORIZONTAL;
        case 26:
            return NARROWVERTICAL;
        case 27:
            return NARROWHORIZONTAL;
        case 28:
            return DARKVERTICAL;
        case 29:
            return DARKHORIZONTAL;
        case 30:
            return DASHEDDOWNWARDDIAGONAL;
        case 31:
            return DASHEDUPWARDDIAGONAL;
        case 32:
            return DASHEDHORIZONTAL;
        case 33:
            return DASHEDVERTICAL;
        case 34:
            return SMALLCONFETTI;
        case 35:
            return LARGECONFETTI;
        case 36:
            return ZIGZAG;
        case 37:
            return WAVE;
        case 38:
            return DIAGONALBRICK;
        case 39:
            return HORIZONTALBRICK;
        case 40:
            return WEAVE;
        case 41:
            return PLAID;
        case 42:
            return DIVOT;
        case 43:
            return DOTTEDGRID;
        case 44:
            return DOTTEDDIAMOND;
        case 45:
            return SHINGLE;
        case 46:
            return TRELLIS;
        case 47:
            return SPHERE;
        case 48:
            return SMALLGRID;
        case 49:
            return SMALLCHECKERBOARD;
        case 50:
            return LARGECHECKERBOARD;
        case 51:
            return OUTLINEDDIAMOND;
        default:
            return SOLIDDIAMOND;
        }
    }

    public Image getImage(Object c) {
        return ImageUtils.getImage(getClass().getResource(
                    "hatches/"
                    + DESCRIPTIONS[getValue()]
                    +".gif"), c);
    }

    public static final String[] DESCRIPTIONS = {
                                                "Horizontal",
                                                "Vertical",
                                                "ForwardDiagonal",
                                                "BackwardDiagonal",
                                                "Cross",
                                                "DiagonalCross",
                                                "Percent05",
                                                "Percent10",
                                                "Percent20",
                                                "Percent25",
                                                "Percent30",
                                                "Percent40",
                                                "Percent50",
                                                "Percent60",
                                                "Percent70",
                                                "Percent75",
                                                "Percent80",
                                                "Percent90",
                                                "LightDownwardDiagonal",
                                                "LightUpwardDiagonal",
                                                "DarkDownwardDiagonal",
                                                "DarkUpwardDiagonal",
                                                "WideDownwardDiagonal",
                                                "WideUpwardDiagonal",
                                                "LightVertical",
                                                "LightHorizontal",
                                                "NarrowVertical",
                                                "NarrowHorizontal",
                                                "DarkVertical",
                                                "DarkHorizontal",
                                                "DashedDownwardDiagonal",
                                                "DashedUpwardDiagonal",
                                                "DashedHorizontal",
                                                "DashedVertical",
                                                "SmallConfetti",
                                                "LargeConfetti",
                                                "ZigZag",
                                                "Wave",
                                                "DiagonalBrick",
                                                "HorizontalBrick",
                                                "Weave",
                                                "Plaid",
                                                "Divot",
                                                "DottedGrid",
                                                "DottedDiamond",
                                                "Shingle",
                                                "Trellis",
                                                "Sphere",
                                                "SmallGrid",
                                                "SmallCheckerBoard",
                                                "LargeCheckerBoard",
                                                "OutlinedDiamond",
                                                "SolidDiamond"
    };
}


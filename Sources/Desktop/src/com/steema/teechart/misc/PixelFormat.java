/*
 * Copyright 2026 Steema Software SL
 * SPDX-License-Identifier: Apache-2.0
 */
package com.steema.teechart.misc;

import java.awt.color.ColorSpace;
import java.awt.image.DataBuffer;
import java.awt.image.DirectColorModel;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;

/** Pixel layout descriptor preserving TeeChart's thirteen-byte wire format. */
public class PixelFormat {
    public static final PixelFormat BGR233 = new PixelFormat(8, 8, false, true, 7, 7, 3, 0, 3, 6);
    public static final PixelFormat RGB888 = new PixelFormat(32, 24, false, true, 255, 255, 255, 16, 8, 0);

    public int bitsPerPixel;
    public int depth;
    public boolean bigEndian;
    public boolean trueColour;
    public int redMax;
    public int greenMax;
    public int blueMax;
    public int redShift;
    public int greenShift;
    public int blueShift;

    private DirectColorModel sourceModel;
    private int[] sourceMasks;
    private int[] sourceOffsets;

    public PixelFormat() {
    }

    public PixelFormat(int bitsPerPixel, int depth, boolean bigEndian, boolean trueColour,
                       int redMax, int greenMax, int blueMax,
                       int redShift, int greenShift, int blueShift) {
        this.bitsPerPixel = bitsPerPixel;
        this.depth = depth;
        this.bigEndian = bigEndian;
        this.trueColour = trueColour;
        this.redMax = redMax;
        this.greenMax = greenMax;
        this.blueMax = blueMax;
        this.redShift = redShift;
        this.greenShift = greenShift;
        this.blueShift = blueShift;
    }

    public PixelFormat(PixelFormat layout) {
        this(layout.bitsPerPixel, layout.depth, layout.bigEndian, layout.trueColour,
             layout.redMax, layout.greenMax, layout.blueMax,
             layout.redShift, layout.greenShift, layout.blueShift);
    }

    public PixelFormat(DataInput input) throws IOException {
        this(input.readUnsignedByte(), input.readUnsignedByte(),
             input.readUnsignedByte() == 1, input.readUnsignedByte() == 1,
             input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(),
             input.readUnsignedByte(), input.readUnsignedByte(), input.readUnsignedByte());
    }

    public void writeData(DataOutput output) throws IOException {
        int[] bytes = {bitsPerPixel, depth, bigEndian ? 1 : 0, trueColour ? 1 : 0,
                       redMax >>> 8, redMax, greenMax >>> 8, greenMax, blueMax >>> 8, blueMax,
                       redShift, greenShift, blueShift};
        for (int value : bytes) {
            output.writeByte(value);
        }
    }

    public void print(PrintStream output) {
        String[] names = {"Bits-per-pixel: ", "Depth:          ", "Big Endian:     ", "True Colour:    ",
                          "R max:   ", "G max:   ", "B max:   ", "R shift: ", "G shift: ", "B shift: "};
        Object[] values = {bitsPerPixel, depth, bigEndian, trueColour,
                           redMax, greenMax, blueMax, redShift, greenShift, blueShift};
        for (int index = 0; index < (trueColour ? names.length : 4); index++) {
            output.println(names[index] + values[index]);
        }
    }

    public void setDirectColorModel(DirectColorModel model) {
        int[] masks = {model.getRedMask(), model.getGreenMask(), model.getBlueMask()};
        int[] maxima = {redMax, greenMax, blueMax};
        int[] offsets = new int[3];
        for (int channel = 0; channel < masks.length; channel++) {
            int maximum = maxima[channel];
            boolean lowBitMask = maximum >= 0 && maximum <= 255 && (maximum & (maximum + 1)) == 0;
            int discarded = lowBitMask ? 8 - Integer.bitCount(maximum) : 8;
            offsets[channel] = Integer.numberOfTrailingZeros(masks[channel]) + discarded;
        }
        sourceMasks = masks;
        sourceOffsets = offsets;
        sourceModel = model;
    }

    public DirectColorModel getDirectColorModel() {
        return sourceModel;
    }

    public int translatePixel(int pixel) {
        if (sourceModel == null) {
            return pixel;
        }
        int translated = 0;
        for (int channel = 0; channel < 3; channel++) {
            int destination = channel == 0 ? redShift : channel == 1 ? greenShift : blueShift;
            int component = (pixel & sourceMasks[channel]) >> sourceOffsets[channel];
            translated |= component << destination;
        }
        return translated;
    }

    public DirectColorModel toDirectColorModel() {
        return new DirectColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), depth,
                redMax << redShift, greenMax << greenShift, blueMax << blueShift,
                0, true, DataBuffer.TYPE_INT);
    }
}

package com.steema.teechart.exports;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Graphics3DSVG;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SVGFormat extends ImageExportFormat {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SVGFormat(IBaseChart chart) {
        super(chart);

        fileExtension = "svg";
    }

    @Override
    public void save(String fileName) throws FileNotFoundException, IOException {
        this.save(fileName, new Point());
    }

    public void save(String fileName, Point p) throws FileNotFoundException, IOException {
        this.save(fileName, p, false);
    }

    public void save(String fileName, Point p, boolean append) throws FileNotFoundException, IOException {
        IGraphics3D oldGraphics = getChart().getGraphics3D();

        int nClip = 0;
        if (append) {
            nClip = prepareFile(fileName, p);
        }

        if (nClip > -1) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(fileName, append);
                getChart().paint(new Graphics3DSVG(fos, getChart(), p, append, nClip), new Rectangle(p.x, p.y, getWidth(), getHeight()));
            } finally {
                getChart().setGraphics3D(oldGraphics);
                fos.close();
            }
        }
    }

   /**
    * prepareFile() removes the last line "</svg>" from the File fileName so the next chart
    * can be appended at the end of the file. It also changes the width and height on the 
    * header so the resulting file can fit all the charts in it.
    * Returns the number of Clip regions found so the next ahart to be appended can create
    * new Clip regions with a bigger id.
    */
    private int prepareFile(String fileName, Point p) throws IOException {
        int result = 0;
        File inFile = new File(fileName);
        File tempFile = File.createTempFile(inFile.getName(), ".tmp", inFile.getParentFile());

        if (!inFile.isFile()) {
            System.out.println("Parameter is not an existing file");
            return -1;
        }

        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String line;
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new FileWriter(tempFile));

            while ((line = br.readLine()) != null) {
                if (!line.trim().equals("</svg>")) {
                    String trLine = line.trim();
                    if (trLine.contains("<svg xmlns=\"http://www.w3.org/2000/svg\" ")) {
                        int wPos = 47;
                        int w = Integer.parseInt(trLine.substring(wPos, trLine.indexOf("px", wPos)));
                        int hPos = trLine.indexOf("height", 50) + 8;
                        int h = Integer.parseInt(trLine.substring(hPos, trLine.indexOf("px", hPos)));
                        line = "<svg xmlns=\"http://www.w3.org/2000/svg\" ";
                        line += "width=\"" + Integer.toString(Math.max(getWidth() + p.x, w)) + "px\" ";
                        line += "height=\"" + Integer.toString(Math.max(getHeight() + p.y, h)) + "px\"";
                        line += " style=\"text-antialiasing:true\">";
                    } else if (trLine.contains("<clipPath")) {
                        int cPos = trLine.indexOf("Clip", 14) + 4;
                        result = Integer.parseInt(trLine.substring(cPos, trLine.indexOf("\"", cPos)));
                    }

                    pw.println(line);
                    pw.flush();
                }
            }
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        }

        if (!inFile.setExecutable(true)) {
            System.out.println("Could make file executable");
            return -1;
        }

        //Delete the original file
        if (!inFile.delete()) {
            System.out.println("Could not delete file");
            return -1;
        }

        //Rename the new file to the filename the original file had.
        if (!tempFile.renameTo(inFile)) {
            System.out.println("Could not rename file");
        }

        inFile = null;
        tempFile = null;

        return result;
    }
}

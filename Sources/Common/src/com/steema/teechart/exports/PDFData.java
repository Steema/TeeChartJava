package com.steema.teechart.exports;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import com.steema.teechart.misc.MemoryOutputStream;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.languages.Language;
import java.io.IOException;

/**
 *
 * <p>Title: PDFData class</p>
 *
 * <p>Description: Summary description for PDFData.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PDFData {

    private MemoryOutputStream m_objStream = null;
    private int m_intPosition = 0;
    private PDFSegmentList m_objSegments = new PDFSegmentList();
    private PDFResourceList m_objResources = new PDFResourceList();
    private PDFFonts m_fontlist = new PDFFonts();
    private PDFImages m_imagelist = new PDFImages();


    public class PDFPage {

        private String m_string="";
        private int width, height;
        private int object_count = 0;
        private int parentNumber, catalogNumber, resourceNumber;
        private int xrefPos;
        private ArrayList<String> m_offsets = new ArrayList<String>();
        private MemoryOutputStream m_stream = null;
        private MemoryOutputStream canvasstream = null;
        private PDFData m_pdfdata = null;

        private void addToStream(MemoryOutputStream s, String text) {
            String linetxt = text +"\n";
            try
            {
                s.write(linetxt.getBytes());
            }
            catch (IOException ex) {}
        }

        private String LongToString(int val, int len) {
            String res = "";
            String S = "";
            if (val >=0) S = Integer.toString(val);
            int i = len - S.length();

            for (int j=0; j<i; j++) res += "0";
            res += S;
            return res;
        }

        private void addToOffsets(int offset) {
            m_offsets.add(LongToString(offset,10));
        }

        // Page elements

        private void pageFonts(MemoryOutputStream s)
        {

        }

        private void pageImages(MemoryOutputStream s)
        {

        }

        private void pageResources(MemoryOutputStream s)
        {
            object_count ++;
            resourceNumber = object_count;
            addToOffsets(s.size());
            addToStream(s,Integer.toString(object_count)+" 0 obj");
            addToStream(s,"<< /ProcSet [/PDF /Text /ImageC]");

            // TODO: add fonts and images

            addToStream(s,">>");
            addToStream(s,"endobj");
        }

        private void pageHeader(MemoryOutputStream s)
        {
            addToStream(s,"%PDF-1.4");
        }

        private void pageInfo(MemoryOutputStream s)
        {
            object_count ++;
            addToOffsets(s.getCount());
            addToStream(s,Integer.toString(object_count)+" 0 obj");
            m_string = "<<\n/Creator ("+Language.getString("TeeChart")+")\n/Producer ("+Language.getString("TeeChart")+")\n";
            // m_string +="/CreationDate (D:"+System.ge.ToString("yyyyMMddHHmmss")+")\n";
            m_string +="/ModDate ()\n/Keywords ()\n/Title (TChart Export)\n>>";
            addToStream(s,m_string);
            addToStream(s,"endobj");
        }

        private void pages(MemoryOutputStream s)
        {
            object_count ++;
            addToOffsets(s.size());
            parentNumber = object_count;
            int pageNumber = parentNumber+1;
            addToStream(s,Integer.toString(object_count) + " 0 obj");
            // Must be followed by WritePage call, otherwise object reference will not be correct
            m_string = "<< /Type /Pages\n/Kids [" + Integer.toString(pageNumber) + " 0 R]\n";
            m_string += "/Count 1\n>>";
            addToStream(s,m_string);
            addToStream(s,"endobj");
        }

        private void page(MemoryOutputStream s)
        {
            object_count ++;
            addToOffsets(s.size());
            addToStream(s,Integer.toString(object_count) + " 0 obj");
            addToStream(s,"<< /Type /Page\n /Parent "+Integer.toString(parentNumber) + " 0 R");
            addToStream(s,"/MediaBox [ 0 0 " + Integer.toString(width) + " " + Integer.toString(height) + " ]");
            addToStream(s,"/Contents 2 0 R");
            addToStream(s,"/Resources " + Integer.toString(resourceNumber) + " 0 R");
            addToStream(s,">>");
            addToStream(s,"endobj");
        }

        private void pageCatalog(MemoryOutputStream s)
        {
            object_count ++;
            catalogNumber = object_count;
            addToOffsets(s.size());
            addToStream(s,Integer.toString(object_count) + " 0 obj");
            addToStream(s,"<< /Type /Catalog");
            addToStream(s,"/Pages " + Integer.toString(parentNumber) + " 0 R");
            addToStream(s,">>");
            addToStream(s,"endobj");
        }

        private void pageTrailer(MemoryOutputStream s)
        {
            addToStream(s,"trailer");
            addToStream(s,"<< /Size "+ Integer.toString(object_count));
            addToStream(s,"/Root "+ Integer.toString(catalogNumber)+ " 0 R");
            addToStream(s,"/Info 1 0 R");
            addToStream(s,">>");
            addToStream(s,"startxref");
            addToStream(s,Integer.toString(xrefPos));
        }


        private void pageXRef(MemoryOutputStream s)
        {
            object_count ++;
            // no need to add xref to xref itself.
            xrefPos = s.size();
            addToStream(s,"xref");
            addToStream(s,"0 "+Integer.toString(object_count));
            addToStream(s,"0000000000 65535 f");
            for (int t=0; t<m_offsets.size(); t++)
                addToStream(s,m_offsets.get(t).toString() + " " +LongToString(0,5)+ " n");

            pageTrailer(s);
        }

        public PDFData getPDFData() {
            return m_pdfdata;
        }

        public PDFPage (MemoryOutputStream s, int w, int h) {
            m_stream = s;
            width = w;
            height = h;
            m_offsets.clear();
            if (canvasstream==null) canvasstream = new MemoryOutputStream();
            if (m_pdfdata==null) m_pdfdata = new PDFData(s);
        }

        public void constructPage(MemoryOutputStream s) {
            object_count = 0;
            s.reset();

            // #1: Start, page header and info
            pageHeader(s);
            pageInfo(s);

            // #2: Chart canvas code
            object_count ++;
            addToOffsets(s.size());
            addToStream(s,Integer.toString(object_count)+" 0 obj");
            addToStream(s,"<< /Length "+Integer.toString(m_pdfdata.getStream().size())+ " >>");
            addToStream(s,"stream");
            try
            {
                m_pdfdata.getStream().writeTo(s);
            }
            catch (IOException ex) {};
            addToStream(s,"endstream");
            addToStream(s,"endobj");

            // #3: Page fonts images and resources list
            pageFonts(s);
            pageImages(s);
            pageResources(s);

            // #4: Pages and page and it's catalog
            pages(s);
            page(s);
            pageCatalog(s);

            // #5: xref, finish
            pageXRef(s);
            addToStream(s,"%%EOF");
        }

    }

    public class PDFResourceObjectList {
        private ArrayList<PDFResourceObject> m_objSegmentsArrayList = 
                new ArrayList<PDFResourceObject>();
        private Hashtable<Integer, PDFResourceObject> m_objSegmentsHashtable = 
                new Hashtable<Integer, PDFResourceObject>();

        public PDFResourceObject add(int objectNumber, int position) {
            PDFResourceObject objObject = new PDFResourceObject(objectNumber,
                    position);

            m_objSegmentsArrayList.add(objObject);
            objObject.setObjectOffset(m_objSegmentsArrayList.size());
            m_objSegmentsHashtable.put(new Integer(objectNumber), objObject);

            return objObject;
        }

        public PDFResourceObject get(int objectNumber) {
            return (PDFResourceObject) m_objSegmentsHashtable.get(new Integer(
                    objectNumber));
        }

        public int getCount() {
            return m_objSegmentsArrayList.size();
        }

        public Iterator getEnumerator() {
            return m_objSegmentsArrayList.iterator();
        }
    }


    public class PDFResourceObject {

        private int m_intObjectNumber;
        private int m_intObjectOffset;
        private int m_intPosition;
        private PDFSegmentList m_objSegments = new PDFSegmentList();
        private boolean m_blnHasStream = false;
        private int m_intStreamPosition;
        private int m_intStreamLength;

        public PDFResourceObject(int objectNumber, int position) {
            m_intObjectNumber = objectNumber;
            m_intPosition = position;
        }

        public void setStream(int position, int length) {
            m_blnHasStream = true;
            m_intStreamPosition = position;
            m_intStreamLength = length;
        }

        public int getObjectNumber() {
            return m_intObjectNumber;
        }

        public void setObjectNumber(int value) {
            m_intObjectNumber = value;
        }

        public int getObjectOffset() {
            return m_intObjectOffset;
        }

        public void setObjectOffset(int value) {
            m_intObjectOffset = value;
        }

        public int getPosition() {
            return m_intPosition;
        }

        public void setPosition(int value) {
            m_intPosition = value;
        }

        public boolean getHasStream() {
            return m_blnHasStream;
        }

        public void setHasStream(boolean value) {
            m_blnHasStream = value;
        }

        public int getStreamPosition() {
            return m_intStreamPosition;
        }

        public void setStreamPosition(int value) {
            m_intStreamPosition = value;
        }

        public int getStreamLength() {
            return m_intStreamLength;
        }

        public void setStreamLength(int value) {
            m_intStreamLength = value;
        }

        public PDFSegmentList getSegments() {
            return m_objSegments;
        }

        public void setSegments(PDFSegmentList value) {
            m_objSegments = value;
        }
    }


    public class PDFResource {
        private int m_intObjectNumber;
        private PDFResourceType m_enmType;
        private PDFResourceObjectList m_objObjects = new PDFResourceObjectList();

        public PDFResource(int objectNumber, PDFResourceType type) {
            m_intObjectNumber = objectNumber;
            m_enmType = type;
        }

        public int getObjectNumber() {
            return m_intObjectNumber;
        }

        public void setObjectNumber(int value) {
            m_intObjectNumber = value;
        }

        public PDFResourceObjectList getObjects() {
            return m_objObjects;
        }

        public PDFResourceType getType() {
            return m_enmType;
        }

        public void setType(PDFResourceType value) {
            m_enmType = value;
        }
    }


    public class PDFSegmentList {
        private ArrayList<PDFSegment> m_objSegments = 
                new ArrayList<PDFSegment>();

        public void add(int length, int objectNumber, int tagLength) {
            m_objSegments.add(new PDFSegment(length, objectNumber, tagLength));
        }

        public Iterator getEnumerator() {
            return m_objSegments.iterator();
        }

        public int getCount() {
            return m_objSegments.size();
        }
    }


    public class PDFResourceList {
        private ArrayList<PDFResource> m_objResources = 
                new ArrayList<PDFResource>();

        public PDFResource add(int objectNumber, PDFResourceType type) {
            PDFResource objResource = new PDFResource(objectNumber, type);
            m_objResources.add(objResource);
            return objResource;
        }

        public Iterator getEnumerator() {
            return m_objResources.iterator();
        }
    }


    public class PDFSegment {
        //private int m_intPosition;
        private int m_intLength;
        private int m_intObjectNumber;
        private int m_intTagLength;

        public PDFSegment(int length, int objectNumber, int tagLength) {
            //m_intPosition = position;
            m_intLength = length;
            m_intObjectNumber = objectNumber;
            m_intTagLength = tagLength;
        }

        public int getLength() {
            return m_intLength;
        }

        public void setLength(int value) {
            m_intLength = value;
        }

        public int getObjectNumber()

        {
            return m_intObjectNumber;
        }

        public void setObjectNumber(int value) {
            m_intObjectNumber = value;
        }

        public int getTagLength()

        {
            return m_intTagLength;
        }

        public void setTagLength(int value) {
            m_intTagLength = value;
        }
    }


    public class PDFImages {

        ArrayList<PDFImageEnumerator> m_images = 
                new ArrayList<PDFImageEnumerator>();

        public int getCount() {
            return m_images.size();
        }

        /**
         * Adds new image to the list
         *
         * @param image Image
         * @return int
         */
        public int add(Image image) {
            PDFImageEnumerator m_image = new PDFImageEnumerator(this, image);
            m_images.add(m_image);
            return m_images.size() - 1;
        }

        /**
         * Select the image in image list. Returns the index of selected image,
         * starting from 0<br>
         *
         * @param image Image
         * @return int
         */
        public int findImage(Image image) {
            for (int i = 0; i < getCount(); i++) {
                if (m_images.get(i).equals(image)) {
                    return i;
                }
            }
            return add(image);
        }

        public PDFImageEnumerator get(int index) {
            return (PDFImageEnumerator) m_images.get(index);
        }
    }


    public PDFData(MemoryOutputStream stream, int position) {
        m_objStream = stream;
        m_intPosition = position;
    }

    public PDFData(MemoryOutputStream stream) {
        m_objStream = stream;
    }

    public MemoryOutputStream getStream() {
        return m_objStream;
    }

    public void setStream(MemoryOutputStream value) {
        m_objStream = value;
    }

    public PDFSegmentList getSegments() {
        return m_objSegments;
    }

    public void setSegments(PDFSegmentList value) {
        m_objSegments = value;
    }

    public PDFResourceList getResources() {
        return m_objResources;
    }

    public void setResources(PDFResourceList value) {
        m_objResources = value;
    }

    public int getPosition() {
        return m_intPosition;
    }

    public void setPosition(int value) {
        m_intPosition = value;
    }

    public PDFFonts getFonts() {
        return m_fontlist;
    }

    public void setFonts(PDFFonts value) {
        m_fontlist = value;
    }

    public PDFImages getImages() {
        return m_imagelist;
    }

    public void setImages(PDFImages value) {
        m_imagelist = value;
    }
}


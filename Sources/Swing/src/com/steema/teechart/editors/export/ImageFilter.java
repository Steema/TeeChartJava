package com.steema.teechart.editors.export;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import com.steema.teechart.languages.Language;

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
public class ImageFilter extends FileFilter {


     //Accept all directories and all gif, jpg, tiff, or png files.
     public boolean accept(File f) {
         if (f.isDirectory()) {
             return true;
         }

         String extension = getExtension(f.getName());
         if (extension != null) {
             if (extension.equals(Language.getString("BMP")) ||
                 extension.equals(Language.getString("PNG")) ||
                 extension.equals(Language.getString("JPG")) ||
                 extension.equals(Language.getString("JPEG"))) {
                     return true;
             } else {
                 return false;
             }
         }

         return false;
     }

     private String getExtension(String filename)
     {
         // filename without the extension
         String choppedFilename;

         // extension without the dot
         String ext;

         // where the last dot is. There may be more than one.
         int dotPlace = filename.lastIndexOf('.');

         if (dotPlace >= 0) {
             // possibly empty
             choppedFilename = filename.substring(0, dotPlace);

             // possibly empty
             ext = filename.substring(dotPlace + 1);
         } else {
             // was no extension
             choppedFilename = filename;
             ext = "";
         }
         return ext;
     }

     //The description of this filter
     public String getDescription() {
           return Language.getString("Image_only_formats");
     }
 }


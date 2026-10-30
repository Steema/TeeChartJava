/* Copyright 2026 Steema Software SL; SPDX-License-Identifier: Apache-2.0 */
import com.steema.teechart.events.EventListenerList;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.misc.PixelFormat;
import features.utils.JavaSyntaxDocument;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.DirectColorModel;
import java.io.*;
import java.util.Arrays;
import java.util.EventListener;
import java.util.Random;
import javax.swing.text.StyleConstants;

public class CompatibilityChecks {
    private static int checks;
    public interface SampleListener extends EventListener {}
    public static class Saved implements SampleListener, Serializable {
        private static final long serialVersionUID = 1L;
        private final int id;
        public Saved(int id) { this.id = id; }
        public boolean equals(Object other) { return other instanceof Saved && ((Saved) other).id == id; }
        public int hashCode() { return id; }
        public String toString() { return "saved-" + id; }
    }
    public static class Unsaved implements SampleListener {}

    private static void check(boolean condition, String description) {
        checks++;
        if (!condition) { throw new AssertionError(description); }
    }

    private static EventListenerList sampleList() {
        EventListenerList list = new EventListenerList();
        list.add(SampleListener.class, new Saved(1));
        list.add(SampleListener.class, new Unsaved());
        list.add(SampleListener.class, new Saved(2));
        return list;
    }

    private static void listeners() throws Exception {
        EventListenerList list = new EventListenerList();
        check(list.getListenerCount() == 0 && list.getListenerList().length == 0, "empty list");
        list.add(null, null);
        list.remove(null, null);
        Saved first = new Saved(1), second = new Saved(2);
        list.add(SampleListener.class, first);
        Object[] snapshot = list.getListenerList();
        list.add(SampleListener.class, second);
        list.add(SampleListener.class, new Saved(1));
        check(snapshot.length == 2 && snapshot[1] == first, "dispatch snapshot remains stable");
        EventListener[] values = list.getListeners(SampleListener.class);
        check(values.getClass() == SampleListener[].class, "typed result array");
        check(values.length == 3 && values[1] == second && values[2] == first, "reverse registration order");
        check(list.getListenerCount(EventListener.class) == 0, "exact type, not inheritance matching");
        list.remove(SampleListener.class, first);
        check(list.getListeners(SampleListener.class)[1] == first, "remove latest equal registration");
        list.remove(SampleListener.class, new Saved(999));
        check(list.getListenerCount() == 2, "removing absent listener is a no-op");
        try { list.add(Runnable.class, first); throw new AssertionError("wrong type accepted"); }
        catch (IllegalArgumentException expected) { checks++; }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytes);
        out.writeObject(sampleList()); out.close();
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()));
        assertSaved((EventListenerList) in.readObject()); in.close();
    }

    private static void assertSaved(EventListenerList list) {
        EventListener[] values = list.getListeners(SampleListener.class);
        check(values.length == 2 && values[0].equals(new Saved(2)) && values[1].equals(new Saved(1)),
                "serializable listeners, registration order and stream compatibility");
    }

    private static void pixels() throws Exception {
        PixelFormat layout = new PixelFormat(32, 24, true, true, 255, 255, 255, 16, 8, 0);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        layout.writeData(new DataOutputStream(bytes));
        byte[] expected = {32, 24, 1, 1, 0, -1, 0, -1, 0, -1, 16, 8, 0};
        check(Arrays.equals(expected, bytes.toByteArray()), "13-byte network representation");
        PixelFormat restored = new PixelFormat(new DataInputStream(new ByteArrayInputStream(expected)));
        ByteArrayOutputStream copy = new ByteArrayOutputStream();
        new PixelFormat(restored).writeData(new DataOutputStream(copy));
        check(Arrays.equals(expected, copy.toByteArray()), "wire and copy constructors");
        check(new PixelFormat().translatePixel(0xaabbccdd) == 0xaabbccdd, "no source model means identity");
        DirectColorModel source = new DirectColorModel(24, 0xff0000, 0xff00, 0xff);
        Random random = new Random(4262026L);
        for (int bits = 0; bits <= 8; bits++) {
            int maximum = (1 << bits) - 1;
            PixelFormat target = new PixelFormat(32, 24, false, true, maximum, maximum, maximum, 16, 8, 0);
            target.setDirectColorModel(source);
            check(target.getDirectColorModel() == source, "source model identity");
            for (int sample = 0; sample < 1000; sample++) {
                int rgb = random.nextInt();
                int wanted = (((rgb >>> 16) & 255) >>> (8 - bits)) << 16;
                wanted |= (((rgb >>> 8) & 255) >>> (8 - bits)) << 8;
                wanted |= (rgb & 255) >>> (8 - bits);
                check(target.translatePixel(rgb) == wanted, "channel quantization");
            }
        }
        PixelFormat compact = new PixelFormat(PixelFormat.BGR233);
        compact.setDirectColorModel(source);
        check(compact.translatePixel(0xffffff) == 255 && compact.translatePixel(0) == 0, "BGR233 extremes");
        PixelFormat other = new PixelFormat(PixelFormat.RGB888);
        other.setDirectColorModel(new DirectColorModel(16, 0xf800, 0x7e0, 0x1f));
        check(other.translatePixel(0xffff) == 0x1f3f1f, "legacy RGB565 truncation without upscaling");
        other.setDirectColorModel(new DirectColorModel(24, 0xff, 0xff00, 0xff0000));
        check(other.translatePixel(0x563412) == 0x123456, "reordered source channels");
        other.setDirectColorModel(new DirectColorModel(32, 0xff000000, 0xff0000, 0xff00));
        check(other.translatePixel(0x80abcdef) == 0xff80abcd, "legacy signed shift behavior");
        other.setDirectColorModel(source);
        other.redShift = 0;
        other.blueShift = 16;
        check(other.translatePixel(0x123456) == 0x563412, "public destination shifts remain mutable");
        byte[] flags = expected.clone();
        flags[2] = 2; flags[3] = -1;
        PixelFormat nonCanonical = new PixelFormat(new DataInputStream(new ByteArrayInputStream(flags)));
        check(!nonCanonical.bigEndian && !nonCanonical.trueColour, "wire booleans recognize exactly one");
        DirectColorModel rgb = PixelFormat.RGB888.toDirectColorModel();
        check(rgb.getRedMask() == 0xff0000 && rgb.getBlueMask() == 255, "direct color model masks");
        try { new PixelFormat(new DataInputStream(new ByteArrayInputStream(new byte[12]))); throw new AssertionError("truncated wire accepted"); }
        catch (EOFException expectedEnd) { checks++; }
        ByteArrayOutputStream description = new ByteArrayOutputStream();
        layout.print(new PrintStream(description));
        check(description.toString("UTF-8").startsWith("Bits-per-pixel: 32"), "diagnostic output");
    }

    private static void mouse() {
        Canvas component = new Canvas();
        FrameworkMouseEvent simple = new FrameworkMouseEvent(component, MouseEvent.MOUSE_MOVED, 10, 0, 7, 11, 0, false);
        FrameworkMouseEvent click = new FrameworkMouseEvent(component, MouseEvent.MOUSE_PRESSED, 20, 0, 7, 11, 1, true, MouseEvent.BUTTON1);
        FrameworkMouseEvent absolute = new FrameworkMouseEvent(component, MouseEvent.MOUSE_PRESSED, 20, 0, 7, 11, 500, 600, 1, true, MouseEvent.BUTTON1);
        check(simple.getButton() == MouseEvent.NOBUTTON, "default button");
        check(click.getButton() == MouseEvent.BUTTON1 && click.isPopupTrigger(), "mouse properties");
        check(absolute.getX() == click.getX() && absolute.getXOnScreen() == click.getXOnScreen(), "legacy absolute-coordinate behavior");
        check(simple.getPoint().getClass().getName().equals("com.steema.teechart.drawing.Point"), "covariant Point");
        simple.getPoint().translate(1, 1);
        check(simple.getX() == 7 && simple.getY() == 11, "returned point is independent");
        check(simple.widget == null, "widget default");
        simple.widget = component;
        check(simple.widget == component, "widget remains writable");
    }

    private static Color color(JavaSyntaxDocument document, int position) {
        return StyleConstants.getForeground(document.getCharacterElement(position).getAttributes());
    }

    private static void syntax() throws Exception {
        JavaSyntaxDocument document = new JavaSyntaxDocument();
        String code = "public class Sample {\n/* comment\ncontinued */ int n; // line\nString s = \"a\\\"b\"; -- legacy\nreturn; }";
        document.insertString(0, code, null);
        check(document.getText(0, document.getLength()).equals(code), "text unchanged by styling");
        check(StyleConstants.isBold(document.getCharacterElement(0).getAttributes()), "keyword bold");
        check(color(document, code.indexOf("continued")).equals(new Color(115, 115, 115)), "multiline comment");
        check(color(document, code.indexOf("// line")).equals(new Color(0, 111, 0)), "Java line comment");
        check(color(document, code.indexOf("legacy")).equals(new Color(0, 111, 0)), "legacy SQL line comment");
        check(color(document, code.indexOf("b\"")).equals(new Color(153, 0, 107)), "escaped quote stays in string");
        int close = code.indexOf("*/");
        document.remove(close, 2);
        check(color(document, document.getLength() - 1).equals(new Color(115, 115, 115)), "deleting delimiter restyles following lines");
        document.insertString(close, "*/", null);
        check(StyleConstants.isBold(document.getCharacterElement(code.indexOf("return")).getAttributes()), "restored delimiter restyles following lines");
        document.remove(0, document.getLength());
        document.insertString(0, null, null);
        document.insertString(0, "sqrt(value) '\\''", null);
        check(color(document, 0).equals(new Color(100, 0, 200)), "function highlight");
        check(color(document, 12).equals(Color.gray), "character literal");
        check(document.JavaKeywords.length == 51 && document.JavaFunctions.length == 20, "public vocabulary arrays");
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 2 && args[0].equals("--write")) {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[1]));
            out.writeObject(sampleList()); out.close(); return;
        }
        if (args.length == 2 && args[0].equals("--read")) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(args[1]));
            assertSaved((EventListenerList) in.readObject()); in.close();
            System.out.println("Cross-version serialization passed"); return;
        }
        listeners(); pixels(); mouse();
        if (args.length == 0) { syntax(); }
        System.out.println("Compatibility assertions passed: " + checks);
    }
}

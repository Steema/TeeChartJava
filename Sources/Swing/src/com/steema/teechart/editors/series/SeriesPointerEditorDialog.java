package com.steema.teechart.editors.series;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JDialog;

import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.PointerStyle;
import com.steema.teechart.styles.SeriesPointer;

public class SeriesPointerEditorDialog extends JDialog {

	private SeriesPointer pointer;
	private SeriesPointerEditor pointEditor;
	JButton cbOK = new JButton();
	JPanel jPanel1 = new JPanel();
	private boolean result;
	
	public SeriesPointerEditorDialog() {
		// TODO Auto-generated constructor stub
	}

//	public GaugeSeriesPointerEditor(SeriesPointer pointer) {
//		super(pointer);
//		// TODO Auto-generated constructor stub
//	}
	
	public SeriesPointerEditorDialog(SeriesPointer pointer) {
		this.pointer = pointer;
		pointEditor = new SeriesPointerEditor((com.steema.teechart.styles.SeriesPointer)pointer); //,Language.getString("Pointer"), 0);
		add(pointEditor);

    pointEditor.cbStyle.addItem(Language.getString("Hand"));
		pointEditor.cbStyle.addItem(Language.getString("Center"));
		pointEditor.cbStyle.addItem(Language.getString("MinorTick"));
		pointEditor.cbStyle.addItem(Language.getString("ColorLine"));
		
		pointEditor.cbStyle.removeActionListener(pointEditor.cbStyle.getActionListeners()[0]);
		pointEditor.cbStyle.addActionListener(new SeriesPointerEditorDialog_cbStyle_actionAdapter(this));
		
		jPanel1.setMinimumSize(new Dimension(10, 30));
    jPanel1.setPreferredSize(new Dimension(10, 30));
    jPanel1.setLayout(null);
    add(jPanel1, java.awt.BorderLayout.SOUTH);
		cbOK.setBounds(new Rectangle(320, 2, 75, 23));
    cbOK.setText(Language.getString("OK"));
    cbOK.addActionListener(new SeriesPointerEditorDialog_cbOK_actionAdapter(this));
    jPanel1.add(cbOK);
	}
	
  public void cbStyle_actionPerformed(ActionEvent e) {
    if (pointer != null) {
    	
        pointer.setStyle(PointerStyle.fromInt(pointEditor.cbStyle.getSelectedIndex()));
    }
  }
  
  static public boolean edit(SeriesPointer pointer) {
    return edit(null, pointer);
  }

  static public boolean edit(Container owner, SeriesPointer pointer) {
  	SeriesPointerEditorDialog editor = new SeriesPointerEditorDialog(pointer);


  	editor.setSize(430, 355);
  	editor.setModal(true);
  	editor.setLocationRelativeTo(owner);
  	editor.setVisible(true);

  	return editor.result;
  }
  
  public void cbOK_actionPerformed(ActionEvent e) {
    result = true;
    dispose();
  }
	
	private void initGUI() {
		try {
			{
				this.setPreferredSize(new java.awt.Dimension(307, 196));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

final class SeriesPointerEditorDialog_cbStyle_actionAdapter implements ActionListener {
  private SeriesPointerEditorDialog adaptee;
  SeriesPointerEditorDialog_cbStyle_actionAdapter(SeriesPointerEditorDialog adaptee) {
      this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
      adaptee.cbStyle_actionPerformed(e);
  }
}

final class SeriesPointerEditorDialog_cbOK_actionAdapter implements ActionListener {
  private SeriesPointerEditorDialog adaptee;
  SeriesPointerEditorDialog_cbOK_actionAdapter(SeriesPointerEditorDialog adaptee) {
      this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
      adaptee.cbOK_actionPerformed(e);
  }
}
package com.steema.teechart.exports;

import java.beans.DefaultPersistenceDelegate;
import java.beans.ExceptionListener;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.TeeBase;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.styles.Series;

final public class TemplateExport {

	final private class ColorPersistenceDelegate extends
			DefaultPersistenceDelegate {

		public ColorPersistenceDelegate(String[] constructorPropertyNames) {
			super(constructorPropertyNames);
		}

		protected boolean mutatesTo(Object oldInstance, Object newInstance) {
			return oldInstance.equals(newInstance);
		}
	}

	private IBaseChart chart;

	public TemplateExport(IBaseChart c) {
		chart = c;
	}

	private PropertyDescriptor[] getProperties(Class value) {
		try {
			return Introspector.getBeanInfo(value).getPropertyDescriptors();
		} catch (IntrospectionException ex) {
		}

		return null;
	}

	/**
	 * Returns all chart properties that are different from their default
	 * values, in XML text format.
	 * 
	 * @return String
	 */
	public String getXML() {
		return getXML(chart);
	}

	/**
	 * Returns all properties in "value" Object that are different from their
	 * default values, in XML text format.
	 * 
	 * @param value
	 *            Object
	 * @return String
	 */
	public String getXML(Object value) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		toXML(value, stream);
		return new String(stream.toByteArray());
	}

	/**
	 * Stores all chart properties to a file in XML format using Java XMLEncoder
	 * class.
	 * 
	 * @param file
	 *            File
	 * @throws FileNotFoundException
	 */
	public void toXML(File file) throws FileNotFoundException {
		toXML(file.getPath());
	}

	/**
	 * Stores all chart properties to a file in XML format using Java XMLEncoder
	 * class.
	 * 
	 * @param fileName
	 *            String
	 * @throws FileNotFoundException
	 */
	public void toXML(String fileName) throws FileNotFoundException {
		toXML(new BufferedOutputStream(new FileOutputStream(fileName)));
	}

	/**
	 * Stores all chart properties to stream, in XML text format using Java
	 * XMLEncoder class.
	 * 
	 * @param stream
	 *            OutputStream
	 */
	public void toXML(OutputStream stream) {
		toXML(chart, stream);
	}

	private void prepareTransients() {
		PropertyDescriptor[] propertyDescriptors = getProperties(Chart.class);

		for (int i = 0; i < propertyDescriptors.length; ++i) {
			PropertyDescriptor pd = propertyDescriptors[i];
			if (pd.getName().equals("graphics3D")) {
				pd.setValue("transient", Boolean.TRUE);
			}
		}

		PropertyDescriptor[] propertyDescriptors2 = getProperties(TeeBase.class);

		for (int i = 0; i < propertyDescriptors2.length; ++i) {
			PropertyDescriptor pd = propertyDescriptors2[i];
			if (pd.getName().equals("chart")) {
				pd.setValue("transient", Boolean.TRUE);
			}
		}

		PropertyDescriptor[] propertyDescriptors3 = getProperties(Series.class);

		for (int i = 0; i < propertyDescriptors3.length; ++i) {
			PropertyDescriptor pd = propertyDescriptors3[i];
			if ((pd.getName().equals("endZ"))
					|| (pd.getName().equals("middleZ"))
					|| (pd.getName().equals("startZ"))) {
				pd.setValue("transient", Boolean.TRUE);
			}
		}
	}

	/**
	 * Stores value object properties to stream, in XML text format using Java
	 * XMLEncoder class.
	 * 
	 * @param value
	 *            Object
	 * @param stream
	 *            OutputStream
	 */
	public void toXML(Object value, OutputStream stream) {

		XMLEncoder xe = new XMLEncoder(stream);

		xe.setExceptionListener(new ExceptionListener() {
			public void exceptionThrown(Exception exception) {
				exception.printStackTrace();
			}
		});

		prepareTransients();

		ColorPersistenceDelegate colorDel = new ColorPersistenceDelegate(
				new String[] { "red", "green", "blue", "alpha" });
		xe.setPersistenceDelegate(Color.class, colorDel);

		xe.writeObject(value);
		xe.close();
	}

	/**
	 * Writes all non-transient chart fields to a file using Java standard
	 * serialization mechanism.
	 * 
	 * @param fileName
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void toFile(String fileName) throws FileNotFoundException,
			IOException {
		toStream(new BufferedOutputStream(new FileOutputStream(fileName)));
	}

	/**
	 * Writes all non-transient chart fields to a stream using Java standard
	 * serialization mechanism.
	 * 
	 * @param stream
	 *            OutputStream
	 * @throws IOException
	 */
	public void toStream(OutputStream stream) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(stream);
		prepareTransients();
		out.writeObject(chart);
		out.close();
	}
}
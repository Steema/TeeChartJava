// Enum
package com.steema.teechart.misc;

//TODO: Replace this class with Java 5.0 SDK "enum" keyword class

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.io.IOException;

/**
 *
 * <p>Title: Enum class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public abstract class Enum implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient String name;
    private transient int value;

    protected Enum(int value) {
        this();
        this.value = value;
    }

    protected Enum() {
        super();
    }

    protected Object readResolve() throws NoSuchFieldException,
            IllegalAccessException {
        return getClass().getField(name).get(null);
    }

    public int getValue() {
        return value;
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        name = (String) in.readObject();
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(getName());
    }

    private String getName() {
        Class c = getClass();
        Field[] f = c.getDeclaredFields();

        for (int i = 0; i < f.length; i++) {
            int mod = f[i].getModifiers();

            if (Modifier.isStatic(mod) && Modifier.isFinal(mod)
                && Modifier.isPublic(mod)) {

                try {
                    if (this == f[i].get(null)) {
                        return f[i].getName();
                    }
                } catch (IllegalAccessException ex) {
                    return "";
                } catch (IllegalArgumentException ex) {
                    return "";
                }
            }
        }

        return "";
    }

}


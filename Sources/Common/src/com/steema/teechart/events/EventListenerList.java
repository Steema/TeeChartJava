/*
 * Copyright 2026 Steema Software SL
 * SPDX-License-Identifier: Apache-2.0
 */
package com.steema.teechart.events;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

/**
 * Listener registrations stored in insertion order as alternating type/value
 * pairs. Mutations publish a new array, so an earlier dispatch snapshot remains
 * usable. Callers must treat the array returned by getListenerList as read-only.
 */
public class EventListenerList implements Serializable {
    private static final long serialVersionUID = 1L;

    protected transient Object[] listenerList = new Object[0];

    public Object[] getListenerList() {
        return listenerList;
    }

    public EventListener[] getListeners(Class type) {
        List<EventListener> matches = new ArrayList<EventListener>();
        Object[] registrations = listenerList;
        for (int entry = registrations.length / 2; entry > 0; entry--) {
            int offset = (entry - 1) * 2;
            if (type == registrations[offset]) {
                matches.add((EventListener) registrations[offset + 1]);
            }
        }
        EventListener[] typed = (EventListener[]) Array.newInstance(type, matches.size());
        return matches.toArray(typed);
    }

    public int getListenerCount() {
        return listenerList.length / 2;
    }

    public int getListenerCount(Class type) {
        int matches = 0;
        Object[] registrations = listenerList;
        for (int entry = 0; entry < registrations.length / 2; entry++) {
            if (registrations[entry * 2] == type) {
                matches++;
            }
        }
        return matches;
    }

    public synchronized void add(Class type, EventListener listener) {
        if (listener != null) {
            checkType(type, listener);
            List<Object> changed = new ArrayList<Object>(Arrays.asList(listenerList));
            changed.add(type);
            changed.add(listener);
            listenerList = changed.toArray();
        }
    }

    public synchronized void remove(Class type, EventListener listener) {
        if (listener == null) {
            return;
        }
        checkType(type, listener);
        List<Object> changed = new ArrayList<Object>(Arrays.asList(listenerList));
        for (int entry = changed.size() / 2; entry > 0; entry--) {
            int offset = (entry - 1) * 2;
            if (changed.get(offset) == type && changed.get(offset + 1).equals(listener)) {
                changed.subList(offset, offset + 2).clear();
                listenerList = changed.toArray();
                return;
            }
        }
    }

    private static void checkType(Class type, EventListener listener) {
        if (!type.isInstance(listener)) {
            throw new IllegalArgumentException("Registration does not implement " + type.getName());
        }
    }

    // Keep the existing stream contract: repeated type-name/listener pairs,
    // terminated by null. Non-serializable listeners are deliberately omitted.
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        Object[] registrations = listenerList;
        for (int entry = 0; entry < registrations.length / 2; entry++) {
            int offset = entry * 2;
            Object value = registrations[offset + 1];
            if (value instanceof Serializable) {
                stream.writeObject(((Class) registrations[offset]).getName());
                stream.writeObject(value);
            }
        }
        stream.writeObject(null);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        listenerList = new Object[0];
        for (Object name = stream.readObject(); name != null; name = stream.readObject()) {
            EventListener listener = (EventListener) stream.readObject();
            Class type = Class.forName((String) name, true, Thread.currentThread().getContextClassLoader());
            add(type, listener);
        }
    }

    public String toString() {
        Object[] registrations = listenerList;
        StringBuilder description = new StringBuilder("EventListenerList: ");
        description.append(registrations.length / 2).append(" listeners: ");
        for (int entry = 0; entry < registrations.length / 2; entry++) {
            int offset = entry * 2;
            description.append(" type ").append(((Class) registrations[offset]).getName());
            description.append(" listener ").append(registrations[offset + 1]);
        }
        return description.toString();
    }
}

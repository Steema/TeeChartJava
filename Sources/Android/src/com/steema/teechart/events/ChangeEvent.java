package com.steema.teechart.events;

public final class ChangeEvent {
	private Object source;

	public ChangeEvent(Object source) {
		this.source = source;
	}

	public Object getSource() {
		return source;
	}
}

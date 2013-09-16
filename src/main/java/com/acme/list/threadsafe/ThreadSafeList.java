package com.acme.list.threadsafe;

import java.util.List;

public class ThreadSafeList<E> {
	
	private final List<E> backList;

	public ThreadSafeList(List<E> backList) {
		this.backList = backList;
	}

	public int size() {
		return backList.size();
	}

	
	public synchronized boolean add(E e) {
		return backList.add(e);
	}

	public synchronized boolean remove(E e) {
		return backList.remove(e);
	}

}

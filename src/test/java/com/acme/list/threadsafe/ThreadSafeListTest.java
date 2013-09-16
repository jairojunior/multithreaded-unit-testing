package com.acme.list.threadsafe;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.jmock.lib.concurrent.Blitzer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ThreadSafeListTest {

	private static final int TOTAL_NUMER_OF_THREADS = 50000;
	private Blitzer blitzer;
	private ThreadSafeList<String> list;

	@Before
	public void setUp() {
		blitzer = new Blitzer(TOTAL_NUMER_OF_THREADS);
		list = new ThreadSafeList<String>(new ArrayList<String>());
	}
	
	@After 
	public void tearDown() {
		blitzer.shutdown();
	}

	@Test
	public void shouldSupportConcurrentAdd() throws Exception {
		blitzer.blitz(new Runnable() {
			@Override
			public void run() {
				list.add("test");
			}
		});
		assertThat(list.size(), equalTo(blitzer.totalActionCount()));
	}

	
	@Test
	public void shouldSupportConcurrentRemove() throws Exception {
		fillListWithNElements(TOTAL_NUMER_OF_THREADS);
		
		blitzer.blitz(new Runnable() {
			@Override
			public void run() {
				list.remove("test");
			}
		});
		
		assertThat(list.size(), equalTo(TOTAL_NUMER_OF_THREADS - blitzer.totalActionCount()));
	}

	private void fillListWithNElements(int n) {
		for (int i = 0; i < n; i++) {
			list.add("test");
		}
	}


}

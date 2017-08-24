package com.scorpio.framework.core;

import android.annotation.SuppressLint;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

@SuppressLint("ParserError")
public class SerialExecutor implements Executor {
	final Queue tasks = new ArrayDeque();
	final Executor executor;
	private String name;
	Runnable active;

	public SerialExecutor(Executor executor, String tname) {
		this.executor = executor;
		this.name = tname;
	}

	@SuppressWarnings("unchecked")
	public synchronized void execute(final Runnable r) {
		tasks.offer(new Runnable() {
			public void run() {
				try {
					Thread.currentThread().setName(name);
					r.run();
				} finally {
					scheduleNext();
				}
			}
		});
		if (active == null) {
			scheduleNext();
		}
	}

	@SuppressLint("ParserError")
	protected synchronized void scheduleNext() {
		if ((active = (Runnable) tasks.poll()) != null) {
			executor.execute(active);
		}
	}

}
package com.zzvc.mmps.app.signal;

import com.zzvc.mmps.app.util.BeanFactory;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * SignalHandler to shutdown application when INT or TERMINAL signal raised
 * 
 * @author cuihbin
 * 
 */
public class ShutdownSignalHandler implements SignalHandler {

	public void init() {
		Signal.handle(new Signal("INT"), this);
		Signal.handle(new Signal("TERM"), this);
	}

	@Override
	public void handle(Signal sig) {
		BeanFactory.close();
		System.exit(0);
	}
}

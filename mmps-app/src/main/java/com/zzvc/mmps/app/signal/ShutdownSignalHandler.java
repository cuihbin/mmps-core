package com.zzvc.mmps.app.signal;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import com.zzvc.mmps.app.util.BeanFactory;

/**
 * SignalHandler to shutdown application when INT or TERMINAL signal raised
 * 
 * @author cuihbin
 * 
 */
public class ShutdownSignalHandler implements SignalHandler {

	public void init() {
		Signal.handle(new Signal("INT"), this);
	}

	@Override
	public void handle(Signal sig) {
		BeanFactory.close();
		System.exit(0);
	}
}

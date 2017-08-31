package zzu.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Execfactory {
	final static ExecutorService exec=Executors.newCachedThreadPool();
	public Execfactory() {
	
	}
	public static ExecutorService getexec(){
		if(exec!=null)return exec;
		else return Executors.newCachedThreadPool();
	}
}

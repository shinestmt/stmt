package com.hanghang.codestore.util;

/**
 * Ãë±í
 */
public class Stopwatch {

    private boolean nano;

    private long start;

    private long end;

    private Stopwatch() {
    	
	}
    
    public static Stopwatch begin() {
    	return begin(false);
    }

    public static Stopwatch begin(boolean nano) {
        Stopwatch sw = new Stopwatch();
        sw.nano = nano;
        sw.start();
        return sw;
    }

    public static Stopwatch create() {
        return create(false);
    }

    public static Stopwatch create(boolean nano) {
        Stopwatch sw = new Stopwatch();
        sw.nano = nano;
        return sw;
    }

    public static Stopwatch run(Runnable atom) {
        return run(atom, false);
    }

    public static Stopwatch run(Runnable atom, boolean nano) {
        Stopwatch sw = begin(nano);
        atom.run();
        sw.stop();
        return sw;
    }

    public long start() {
    	start = nano ? System.nanoTime() : System.currentTimeMillis();
        return start;
    }

    public long stop() {
        end = nano ? System.nanoTime() : System.currentTimeMillis();
        return end;
    }

    public long getDuration() {
        return end - start;
    }

    public long getStartTime() {
        return start;
    }

    public long getEndTime() {
        return end;
    }

    @Override
    public String toString() {
        return String.format("Total: %d%s : [%s]=>[%s]",
                                this.getDuration(),
                                (nano ? "ns" : "ms"),
                                new java.sql.Timestamp(start).toString(),
                                new java.sql.Timestamp(end).toString());
    }
    
    public void show() {
    	Log.print(this.toString());
//    	Log.print(this);
	}

}

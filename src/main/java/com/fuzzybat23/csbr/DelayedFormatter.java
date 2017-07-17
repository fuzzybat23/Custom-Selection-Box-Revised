package com.fuzzybat23.csbr;

public class DelayedFormatter{
    String format;  Object[] args;

    public DelayedFormatter(String format, Object... args){
        this.format = format;  this.args = args;
    }

    public static DelayedFormatter format(String format, Object... args){
        return new DelayedFormatter(format, args);
    }

    @Override public String toString(){
        return String.format(format, args);
    }
}
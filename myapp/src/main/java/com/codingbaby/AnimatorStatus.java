package com.codingbaby;

public class AnimatorStatus {

    private boolean close = false;


    public void close() {
        close = true;
    }

    public void open() {
        close = false;
    }

    public boolean isClose() {
        return close == true;
    }

    public boolean isOpen() {
        return close == false;
    }
}

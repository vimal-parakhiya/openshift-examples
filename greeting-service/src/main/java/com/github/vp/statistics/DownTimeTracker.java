package com.github.vp.statistics;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vimalpar on 03/06/17.
 */
public class DownTimeTracker {
    private Status previousStatus = Status.UP;
    private StopWatch stopWatch;
    private List<Long> downTimes = new ArrayList<>();

    public void serviceDown() {
        if(previousStatus == Status.UP) {
            stopWatch = new StopWatch();
            stopWatch.start();
        }
        previousStatus = Status.DOWN;
    }

    public void serviceUp() {
        if(previousStatus == Status.DOWN) {
            stopWatch.stop();
            downTimes.add(stopWatch.getTotalTimeMillis());
        }
        previousStatus = Status.UP;
    }

    enum Status {
        UP, DOWN;
    }

    @Override
    public String toString() {
        return "DownTimeTracker{" +
                "downTimes=" + downTimes +
                " Average Down Time (ms): " + average(downTimes) +
                '}';
    }

    private long average(List<Long> downTimes) {
        long totalDownTime = 0;
        for(long downTime : downTimes) {
            totalDownTime += downTime;
        }

        return  downTimes.isEmpty() ? 0 : totalDownTime/downTimes.size();
    }
}

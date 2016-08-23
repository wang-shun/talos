package me.ele.bpm.talos.common.soa;

import me.ele.metrics.statsd.MetricClient;

public class MetricUtils {

	public static void record(String name) {
		new MetricClient().name(name).recordIncrement();
	}

	public static void recordTimes(String name, long cost){
		new MetricClient().name(name).recordTimeInMillis(cost);
	}
}

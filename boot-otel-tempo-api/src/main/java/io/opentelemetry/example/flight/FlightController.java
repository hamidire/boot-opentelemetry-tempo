package io.opentelemetry.example.flight;

import java.io.IOException;
import java.util.List;

import io.micrometer.core.annotation.Timed;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {

	// Counter should have Exemplars when the OpenTelemetry agent is attached.
	private final Counter requestCounter = Counter.build().name("requests_greeting_counter_total").help("Total number of requests.")
			.labelNames("path").register();
//
//	// Gauges don't have Exemplars.
//	private final Gauge lastRequestTimestamp = Gauge.build().name("last_request_timestamp")
//			.help("unix time of the last request").labelNames("path").register();
//
//	// Histogram should have Exemplars when the OpenTelemetry agent is attached.
//	private final Histogram requestDurationHistogram = Histogram.build().name("request_duration_histogram")
//			.help("Request duration in seconds").labelNames("path")
//			.buckets(0.001, 0.002, 0.003, 0.004, 0.005, 0.006, 0.007, 0.008, 0.009).register();
//
//	// Summaries don't have Exemplars
//	private final Summary requestDurationSummary = Summary.build().name("request_duration_summary")
//			.help("Request duration in seconds").labelNames("path").quantile(0.75, 0.01).quantile(0.85, 0.01)
//			.register();
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);
	
	private FlightService flightService;
	
	public FlightController(FlightService flightService) {
		this.flightService = flightService;
	}

//	@Timed(value = "greeting.time", description = "Time taken to return greeting")
	@GetMapping("/flights")
    public List<Flight> greeting(@RequestParam(value = "origin", defaultValue = "India") String origin, @RequestParam String type) throws IOException {
    	LOGGER.info("Before Service Method Call");
		String path = "/helloh";
		requestCounter.labels(path).inc();
//		lastRequestTimestamp.labels(path).setToCurrentTime();
//		Histogram.Timer histogramRequestTimer = requestDurationHistogram.labels(path).startTimer();
//		Summary.Timer summaryRequestTimer = requestDurationSummary.labels(path).startTimer();
		List<Flight> list = flightService.getFlights(origin);
//		histogramRequestTimer.observeDuration();
//		summaryRequestTimer.observeDuration();
		try {
			// Generate some random errors
//			randomError(path);

			// Randomly sleeps a bit
			try {
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				throw new IOException(e);
			}


		} finally {
//			histogramRequestTimer.observeDuration();
//			summaryRequestTimer.observeDuration();
		}
        return list;
    }
	private void randomError(String path) throws IOException {
		if (Math.random() > 0.9) {
			throw new IOException("Random error with " + path + "!");
		}
	}

}

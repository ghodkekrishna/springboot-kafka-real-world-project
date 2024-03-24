package net.codefusionhub.springboot;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikiMediaChangesProducer {

    public static final Logger LOGGER = LoggerFactory.getLogger(WikiMediaChangesProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    public WikiMediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {

        String topic = "wikimedia_recentchanges";

        // to read real time stream data from wikimedia we use event source.
        BackgroundEventHandler eventHandler = new WikiMediaChangesHandler(kafkaTemplate, topic);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        EventSource.Builder builder;
        builder = new EventSource.Builder(URI.create(url));

        BackgroundEventSource.Builder builder1 = new BackgroundEventSource.Builder(eventHandler,builder);
        BackgroundEventSource eventSource = builder1.build();

        eventSource.start();
        TimeUnit.MINUTES.sleep(10);


    }
}

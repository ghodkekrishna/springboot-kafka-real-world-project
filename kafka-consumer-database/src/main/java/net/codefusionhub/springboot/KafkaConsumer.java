package net.codefusionhub.springboot;

import net.codefusionhub.springboot.entity.Wikimedia;
import net.codefusionhub.springboot.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private WikimediaDataRepository wikimediaDataRepository;

    public KafkaConsumer(WikimediaDataRepository wikimediaDataRepository) {
        this.wikimediaDataRepository = wikimediaDataRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String eventMessage){

        LOGGER.info(String.format("Event message received ---> %s", eventMessage));

        Wikimedia wikimedia = new Wikimedia();
        wikimedia.setWikiEventData(eventMessage);
        wikimediaDataRepository.save(wikimedia);

        LOGGER.info(String.format("Event message saved to DB"));
    }
}

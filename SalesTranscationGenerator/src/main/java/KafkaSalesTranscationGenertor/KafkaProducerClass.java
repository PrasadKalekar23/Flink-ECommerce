package KafkaSalesTranscationGenertor;

import ObjectClass.TransactionRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.LongSerializer;


import java.util.Date;
import java.util.Properties;

public class KafkaProducerClass {

    public static void main(String[] args) {
        // Kafka producer configuration settings
        String topicName = "sales-topic";
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<Long, String> producer = new KafkaProducer<>(props);

        try {

            long startTime = System.currentTimeMillis();

            while((startTime + (1 * 0.5 * 1000)) > System.currentTimeMillis()){
                TransactionRecord record = TransactionGenerator.generateSalesTransaction();

                ObjectMapper objectMapper = new ObjectMapper();
                String recordValue = objectMapper.writeValueAsString(record);

                ProducerRecord<Long, String> producerRecord = new ProducerRecord<>(topicName, System.currentTimeMillis(), recordValue);

                producer.send(producerRecord);

                System.out.println("Record sent to topic " + topicName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}

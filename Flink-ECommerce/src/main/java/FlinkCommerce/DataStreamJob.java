package FlinkCommerce;

import Deserializer.JSONValueDeserializationSchema;
import Dto.SalesPerCategory;
import Dto.SalesPerDay;
import Dto.SalesPerProduct;
import Dto.Transaction;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.connector.elasticsearch.sink.Elasticsearch7SinkBuilder;
import org.apache.flink.connector.elasticsearch.sink.FlushBackoffType;
import org.apache.flink.elasticsearch7.shaded.org.apache.http.HttpHost;
import org.apache.flink.elasticsearch7.shaded.org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.flink.elasticsearch7.shaded.org.elasticsearch.action.index.IndexRequest;
import org.apache.flink.elasticsearch7.shaded.org.elasticsearch.client.Requests;
import org.apache.flink.elasticsearch7.shaded.org.elasticsearch.common.xcontent.XContentType;
import org.apache.flink.elasticsearch7.shaded.org.elasticsearch.search.DocValueFormat;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkBase;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Properties;



import static utils.JsonUtil.convertTransactionToJson;

public class DataStreamJob {

    public static void main(String[] args) throws Exception {


        System.out.println("Job is starting//...");

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        String topic = "sales-topic";

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "flink-consumer-group");
        properties.setProperty("auto.offset.reset", "earliest");

        FlinkKafkaConsumer<Transaction> kafkaConsumer = new FlinkKafkaConsumer<>(
                topic,
                new JSONValueDeserializationSchema(),
                properties
        );

        DataStream<Transaction> transactionStream = env.addSource(kafkaConsumer);

        DataStream<SalesPerProduct> salesPerProductDataStream = transactionStream.map(new SalesPerProductFunction());
        salesPerProductDataStream.print();

        DataStream<SalesPerDay> salesPerDayDataStream = transactionStream.map(new SalesPerDayFunction());
        salesPerDayDataStream.print();

        DataStream<SalesPerCategory> salesPerCategoryDataStream = transactionStream.map(new SalesPerCategoryFunction());
        salesPerCategoryDataStream.print();

        transactionStream.sinkTo(
                new Elasticsearch7SinkBuilder<Transaction>()
                        .setHosts(new HttpHost("localhost", 9200, "http"))
                        .setConnectionUsername("mewto")
                        .setConnectionPassword("Mewto@123")
                        .setEmitter((transaction, runtimeContext, requestIndexer) -> {

                            String json = convertTransactionToJson(transaction);

                            IndexRequest indexRequest = Requests.indexRequest()
                                    .index("transactions")
                                    .id(Long.toString(transaction.getTransactionId()))
                                    .source(json, XContentType.JSON);
                            requestIndexer.add(indexRequest);
                        })
                        .setBulkFlushBackoffStrategy(FlushBackoffType.EXPONENTIAL, 5, 1000)
                        .build()
        ).name("Elasticsearch Sink");

        System.out.println("Data Stored in Elasticsearch..");

        env.execute("Flink-Commerce");

    }

}

class getProductName implements MapFunction<Transaction, String>{
    @Override
    public String map(Transaction t) {
        String productName = t.getProductName();
        return productName;
    }
}

class SalesPerProductFunction implements MapFunction<Transaction, SalesPerProduct>{
    public SalesPerProduct map(Transaction t){
        long transactionId = t.getTransactionId();
        String productId = t.getProductId();
        String productName = t.getProductName();

        return new SalesPerProduct(transactionId, productId, productName);
    }
}

class SalesPerDayFunction implements MapFunction<Transaction, SalesPerDay>{
    public SalesPerDay map(Transaction t){
        long transactionId = t.getTransactionId();

        String dateString = t.getTransactionDate().substring(0,10);
        LocalDate localDate = LocalDate.parse(dateString);

        Double totalSalesAmount = t.getTotalPayAmount();

        return new SalesPerDay(transactionId, localDate, totalSalesAmount);
    }
}

class SalesPerCategoryFunction implements MapFunction<Transaction, SalesPerCategory>{
    public SalesPerCategory map(Transaction t){
        long transactionId = t.getTransactionId();
        String category = t.getProductCategory();

        return new SalesPerCategory(transactionId, category);
    }
}
package com.example.nakedkafkastreams

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Printed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableKafkaStreams
class NakedKafkaStreamsApplication {


    @Bean
    fun quotes(): NewTopic {
        return NewTopic("quotes", 4, 1)
    }

    @Bean
    fun counts(): NewTopic {
        return NewTopic("quote-counts", 4, 1)
    }

    @Component
    class Processor {
        @Autowired
        fun process(builder: StreamsBuilder) {
            val stringSerde = Serdes.String()
            val countStream = builder.stream<String, String>("quotes", Consumed.with(stringSerde, stringSerde))
                .flatMapValues { value: String -> value.lowercase().split("\\W+".toRegex()) }
                .groupBy({ _, value -> value }, Grouped.with(stringSerde, stringSerde))
                .count(Materialized.`as`("word-count-state-store"))
                .toStream()
            countStream.print(Printed.toSysOut())

            countStream.to("quote-counts")
        }
    }


}

fun main(args: Array<String>) {
    runApplication<NakedKafkaStreamsApplication>(*args)
}

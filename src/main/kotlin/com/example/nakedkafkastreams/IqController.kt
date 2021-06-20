package com.example.nakedkafkastreams

import org.apache.kafka.streams.StoreQueryParameters
import org.apache.kafka.streams.state.QueryableStoreTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.config.StreamsBuilderFactoryBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class IqController {
    @Autowired
    private val streamsBuilderFactoryBean: StreamsBuilderFactoryBean? = null
//    private lateinit var iqService: InteractiveQueryService
//    private lateinit var
    @GetMapping("/iq/count/{word}")
    fun getCount(@PathVariable word:String): Long?{
    val kafkaStreams =streamsBuilderFactoryBean?.getKafkaStreams()
    val keyValueStore =  kafkaStreams?.store(
        StoreQueryParameters.fromNameAndType("word-count-state-store", QueryableStoreTypes.keyValueStore<String,Long>()));
    return keyValueStore?.get(word)
//        val store= iqService.getQueryableStore("word-count-state-store", QueryableStoreTypes.keyValueStore<String,Long>())

//        return store[word]
//    return 0L
    }
}

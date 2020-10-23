package bcc.kafkaelk.kafkaelk.config

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ElasticsearchConfiguration {

    @Value("\${elasticsearch.host}")
    private val EsHost: String? = "localhost"

    @Value("\${elasticsearch.port}")
    private val EsPort: Int = 9200

    @Value("\${elasticsearch.clustername}")
    private val EsClusterName: String? = "elasticsearch"

    @Bean
    fun clientES(): RestHighLevelClient {
        val builder = RestClient.builder(HttpHost(EsHost, EsPort, "http"))
        return RestHighLevelClient(builder)
    }
}
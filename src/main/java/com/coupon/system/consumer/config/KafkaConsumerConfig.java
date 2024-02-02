package com.coupon.system.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
public class KafkaConsumerConfig {

	@Bean
	public ConsumerFactory<String, Long> consumerFactory() { // Consumer 인스턴스를 생성할 때 필요한 값들을 설정하기 위한 컨슈머 팩토리 생성
		Map<String, Object> config = new HashMap<>();
		
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // 서버의 정보
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_1"); // 그룹 아이디 정보
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // 키 디시리얼라이저 클래스 정보
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class); // 밸류 디시리얼라이저 클래스 정보
		
		return new DefaultKafkaConsumerFactory<>(config);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Long> kafkaListenerContainerFactory() { // 토픽으로부터 메세지를 전달받기 위한 카프카 리스너를 만드는 카프카 리스너 컨테이너 팩토리 생성
		ConcurrentKafkaListenerContainerFactory<String, Long> factory = new ConcurrentKafkaListenerContainerFactory<String, Long>(); // 컨커런트 카프카 리스너 컨테이너 팩토리를 사용할 거싱기 때문에 변수로 추가
		factory.setConsumerFactory(consumerFactory()); // 컨커런트 카프카 리스너 컨테이너 팩토리에 컨슈머 팩토리를 세팅
		
		return factory;
	}
}

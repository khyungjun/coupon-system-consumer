package com.coupon.system.consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.coupon.system.consumer.domain.Coupon;
import com.coupon.system.consumer.domain.FailedEvent;
import com.coupon.system.consumer.repository.CouponRepository;
import com.coupon.system.consumer.repository.FailedEventRepository;

@Component
public class CouponCreatedConsumer { // 토픽에 전송된 데이터를 가져오기 위한 컨슈머 생성

//	@KafkaListener(topics = "coupon_create", groupId = "group_1")
//	public void listener(Long userId) {
//		System.out.println(userId); // Consumer가 Topic에서 가져온 유저 아이디 출력 
//	}
	
	// 추가
	private final CouponRepository couponRepository;

//	public CouponCreatedConsumer(CouponRepository couponRepository) {
//		this.couponRepository = couponRepository;
//	}
//	
//	@KafkaListener(topics = "coupon_create", groupId = "group_1")
//	public void listener(Long userId) {
//		// System.out.println(userId); // Consumer가 Topic에서 가져온 유저 아이디 출력 
//		couponRepository.save(new Coupon(userId)); // 쿠폰 레포지토리를 사용해서 쿠폰을 생성하도록 변경
//	}
	
	// 추추가
	private final FailedEventRepository failedEventRepository;
	
	private final Logger logger = LoggerFactory.getLogger(CouponCreatedConsumer.class);
	
	public CouponCreatedConsumer(CouponRepository couponRepository, FailedEventRepository failedEventRepository) {
		this.couponRepository = couponRepository;
		this.failedEventRepository = failedEventRepository;
	}

	@KafkaListener(topics = "coupon_create", groupId = "group_1")
	public void listener(Long userId) {
		// System.out.println(userId); // Consumer가 Topic에서 가져온 유저 아이디 출력 
		// couponRepository.save(new Coupon(userId)); // 쿠폰 레포지토리를 사용해서 쿠폰을 생성하도록 변경

		// 쿠폰 발급을 진행하다가 에러가 발생한다면 로거를 사용해서 로그를 남겨주도록 쿠폰 발급 로직 변경
		try {
			couponRepository.save(new Coupon(userId)); // 쿠폰 레포지토리를 사용해서 쿠폰을 생성
		} catch (Exception e) {
			logger.error("failed to create coupon"); // 에러 발생 시 로그 출력
			failedEventRepository.save(new FailedEvent(userId)); // FailedEventRepository를 사용해서 실패한 유저의 아이디를 저장
		}
	}
}

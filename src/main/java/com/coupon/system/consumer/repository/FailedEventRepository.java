package com.coupon.system.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coupon.system.consumer.domain.FailedEvent;

public interface FailedEventRepository extends JpaRepository<FailedEvent, Long> {

}

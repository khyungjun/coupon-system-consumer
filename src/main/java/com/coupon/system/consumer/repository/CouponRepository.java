package com.coupon.system.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coupon.system.consumer.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}

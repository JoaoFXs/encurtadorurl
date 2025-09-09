package com.jfelixy.encurtadorurl.repository;

import com.jfelixy.encurtadorurl.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortKey(String shortKey);

    boolean existByShortKey(String shortKey);

}

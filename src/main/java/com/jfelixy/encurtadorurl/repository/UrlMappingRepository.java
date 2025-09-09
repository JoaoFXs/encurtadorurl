package com.jfelixy.encurtadorurl.repository;

import com.jfelixy.encurtadorurl.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortKey(String shortKey);

    boolean existByShortKey(String shortKey);

}

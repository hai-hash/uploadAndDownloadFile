package com.laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.domain.Image;
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	
	Image findOneById(Long id);

}

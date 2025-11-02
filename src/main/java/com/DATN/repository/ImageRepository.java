package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
}

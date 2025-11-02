package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Wishlist;
import com.DATN.model.WishlistId;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {

}

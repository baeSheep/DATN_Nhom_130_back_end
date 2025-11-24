package com.DATN.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.DATN.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	List<Address> findByUsers_UserID(Integer userId);

	@Modifying
	@Query("UPDATE Address a SET a.isDefault = false WHERE a.users.userID = :userId")
	void resetDefaultAddresses(@Param("userId") Integer userId);

	
}

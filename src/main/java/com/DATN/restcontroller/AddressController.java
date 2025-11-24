package com.DATN.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.model.Address;
import com.DATN.model.Users;
import com.DATN.repository.AddressRepository;
import com.DATN.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = {
	    "http://localhost:3000",
	    "http://localhost:4200"
	}, allowCredentials = "true")
public class AddressController {

    private final AddressRepository addressRepo;
    private final UserRepository userRepo;

    // Lấy tất cả địa chỉ của user
    @GetMapping("/user/{userId}")
    public List<Address> getAddresses(@PathVariable Integer userId) {
        return addressRepo.findByUsers_UserID(userId);
    }


    // Thêm địa chỉ mới
    @PostMapping("/user/{userId}")
    @Transactional
    public Address addAddress(@PathVariable Integer userId, @RequestBody Address address) {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (Boolean.TRUE.equals(address.getIsDefault())) {
            addressRepo.resetDefaultAddresses(userId);
        }

        address.setUsers(user);
        return addressRepo.save(address);
    }


    // Cập nhật địa chỉ
    @PutMapping("/{addressId}")
    public Address updateAddress(@PathVariable Integer addressId, @RequestBody Address address) {
        Address exist = addressRepo.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (address.getIsDefault() != null && address.getIsDefault()) {
            addressRepo.findAll().stream()
                .filter(a -> a.getUsers().getUserID().equals(exist.getUsers().getUserID()))
                .forEach(a -> { a.setIsDefault(false); addressRepo.save(a); });
        }

        // set các field
        exist.setReceiverName(address.getReceiverName());
        exist.setPhone(address.getPhone());
        exist.setProvince(address.getProvince());
        exist.setDistrict(address.getDistrict());
        exist.setWard(address.getWard());
        exist.setDetailAddress(address.getDetailAddress());
        exist.setIsDefault(address.getIsDefault());

        return addressRepo.save(exist);
    }


    // Xóa địa chỉ
    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable Integer addressId) {
        addressRepo.deleteById(addressId);
    }

}
package com.fmy.server.dao.jpa;

import com.fmy.server.dao.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressDao extends JpaRepository<Address,Integer> {
}

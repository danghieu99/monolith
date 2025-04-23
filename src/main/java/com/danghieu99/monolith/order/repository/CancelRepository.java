package com.danghieu99.monolith.order.repository;

import com.danghieu99.monolith.order.entity.Cancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelRepository extends JpaRepository<Cancel, Integer> {

}

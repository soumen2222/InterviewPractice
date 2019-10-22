package com.soumen.boot.repository;

import org.springframework.data.repository.CrudRepository;

import com.soumen.boot.model.Shipwreck;

public interface ShipwreckRepository extends CrudRepository<Shipwreck, Long> {

}

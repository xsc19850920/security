package com.genpact.security.modules.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genpact.security.modules.demo.entity.DemoCache;

public interface DemoCacheRepository extends JpaRepository<DemoCache, Integer> {
	
}

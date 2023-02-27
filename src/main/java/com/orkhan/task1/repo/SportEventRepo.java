package com.orkhan.task1.repo;

import com.orkhan.task1.model.entity.SportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SportEventRepo extends JpaRepository<SportEvent, Long>, JpaSpecificationExecutor<SportEvent> {
}

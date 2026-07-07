package com.cognizant.spring_learn.repository;

import com.cognizant.spring_learn.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    // Additional query methods if needed
}

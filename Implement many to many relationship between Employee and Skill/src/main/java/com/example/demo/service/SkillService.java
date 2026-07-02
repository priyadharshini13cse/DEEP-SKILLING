package com.example.demo.service;

import com.example.demo.entity.Skill;
import com.example.demo.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill get(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found with id: " + id));
    }

    @Transactional
    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }
}

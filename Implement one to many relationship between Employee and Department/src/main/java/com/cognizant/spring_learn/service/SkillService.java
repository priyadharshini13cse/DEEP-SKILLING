package com.cognizant.spring_learn.service;

import com.cognizant.spring_learn.model.Skill;
import com.cognizant.spring_learn.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkillService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkillService.class);

    @Autowired
    private SkillRepository skillRepository;

    @Transactional
    public Skill get(int id) {
        LOGGER.info("Start get Skill id={}", id);
        return skillRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Skill skill) {
        LOGGER.info("Start save Skill");
        skillRepository.save(skill);
        LOGGER.info("End save Skill");
    }
}

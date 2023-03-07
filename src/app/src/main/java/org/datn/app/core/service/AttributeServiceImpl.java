package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Attribute;
import org.datn.app.core.repo.AttributeRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = RuntimeException.class)
public class AttributeServiceImpl implements AttributeService{
    private final AttributeRepo attributeRepo;
    @Override
    public Attribute doInsert(Attribute attribute) {
        return attributeRepo.save(attribute);
    }

    @Override
    public Attribute doUpdateById(Attribute attribute, Long aLong) {
        Attribute attribute1 = attributeRepo.findById(aLong).get();
        attribute1.setName(attribute.getName());
        attribute1.setType(attribute.getType());
        attribute1.setIsTrash(attribute.getIsTrash());
        return attributeRepo.save(attribute1);
    }

    @Override
    public Attribute doDeleteById(Long aLong) {
        Attribute attribute = attributeRepo.findById(aLong).orElse(null);
        attributeRepo.delete(attribute);
        return null;
    }

    @Override
    public Attribute findById(Long aLong) {
        return attributeRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Attribute> findAll() {
        return attributeRepo.findAll();
    }

    @Override
    public Page<Attribute> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return attributeRepo.findAll(pageable);
    }
}

package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Category;
import org.datn.app.core.repo.CategoryRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = RuntimeException.class)
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepo categoryRepo;
    @Override
    public Category doInsert(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category doUpdateById(Category category, Long aLong) {
        Category category1 = categoryRepo.findById(aLong).get();
        category1.setName(category.getName());
        category1.setIsTrash(category.getIsTrash());
        category1.setDescription(category.getDescription());
        return categoryRepo.save(category1);
    }

    @Override
    public Category doDeleteById(Long aLong) {
        Category category = categoryRepo.findById(aLong).get();
        categoryRepo.delete(category);
        return null;
    }

    @Override
    public Category findById(Long aLong) {
        return categoryRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Page<Category> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return categoryRepo.findAll(pageable);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepo.findByName(name);
    }
}

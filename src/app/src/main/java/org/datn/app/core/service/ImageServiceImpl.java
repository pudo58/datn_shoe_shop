package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Image;
import org.datn.app.core.repo.ImageRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ImageRepo imageRepo;
    @Override
    public Image doInsert(Image image) {
        return imageRepo.save(image);
    }

    @Override
    public Image doUpdateById(Image image, Long aLong) {
        Image image1 = imageRepo.findById(aLong).get();
        image1.setUrl(image.getUrl());
        return imageRepo.save(image1);
    }

    @Override
    public Image doDeleteById(Long aLong) {
        Image image = imageRepo.findById(aLong).get();
        imageRepo.delete(image);
        return null;
    }

    @Override
    public Image findById(Long aLong) {
        return imageRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Image> findAll() {
        return imageRepo.findAll();
    }

    @Override
    public Page<Image> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return imageRepo.findAll(pageable);
    }
}

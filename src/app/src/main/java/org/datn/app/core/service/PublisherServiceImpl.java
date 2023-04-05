package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.constant.ProductConstant;
import org.datn.app.core.entity.Publisher;
import org.datn.app.core.entity.extend.PublisherResponse;
import org.datn.app.core.repo.PublisherRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService{
    private final PublisherRepo publisherRepo;
    @Override
    public Publisher doInsert(Publisher publisher) {
        return publisherRepo.save(publisher);
    }

    @Override
    public Publisher doUpdateById(Publisher publisher, Long aLong) {
        Publisher publisher1 = publisherRepo.findById(aLong).get();
        publisher1.setName(publisher.getName());
        publisher1.setWebsite(publisher.getWebsite());
        return publisherRepo.save(publisher1);
    }

    @Override
    public Publisher doDeleteById(Long aLong) {
        publisherRepo.deleteById(aLong);
        return null;
    }

    @Override
    public Publisher findById(Long aLong) {
        return publisherRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Publisher> findAll() {
        return publisherRepo.findAll();
    }

    @Override
    public Page<Publisher> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return publisherRepo.findAll(pageable);
    }

    @Override
    public List<Publisher> findByName(String name) {
        List publisherList = publisherRepo.findByName(name);
        if(publisherList.size() == 0){
            return null;
        }
        return publisherList;
    }

    @Override
    public List<PublisherResponse> findAllData() {
        List<PublisherResponse> publisherResponseList = new ArrayList<>();
        List<Publisher> publisherList = publisherRepo.findAll();
        for (Publisher publisher : publisherList) {
            PublisherResponse publisherResponse = new PublisherResponse();
            publisherResponse.setId(publisher.getId());
            publisherResponse.setName(publisher.getName());
            publisherResponse.setWebsite(publisher.getWebsite());
            publisherResponse.setTotalProduct(publisher.getProducts().stream().filter(product -> product.getStatus() == ProductConstant.EFFECT).count());
            publisherResponseList.add(publisherResponse);
        }
        return publisherResponseList;
    }
}

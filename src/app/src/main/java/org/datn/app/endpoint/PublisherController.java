package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Publisher;
import org.datn.app.core.service.PublisherService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publisher")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @PostMapping("/add")
    public Publisher addPublisher(@RequestBody Publisher publisher){
        if(publisher.getName() == null || publisher.getName().trim().isEmpty()){
            return null;
        }
        if(publisherService.findByName(publisher.getName()) != null){
            return null;
        }
        return publisherService.doInsert(publisher);
    }

    @GetMapping("/get/{id}")
    public Publisher getPublisherById(@PathVariable Long id){
        return publisherService.findById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deletePublisherById(@PathVariable Long id){
        publisherService.doDeleteById(id);
    }
    @PutMapping("/update/{id}")
    public Publisher updatePublisherById(@PathVariable Long id,@RequestBody Publisher publisher){
        return publisherService.doUpdateById(publisher,id);
    }
    @GetMapping("/page/{page}/{size}")
    public Page<Publisher> getAllPublisher(@PathVariable int page, @PathVariable int size){
        return publisherService.findAll(page,size);
    }
    @GetMapping("/getByName/{name}")
    public List<Publisher> getPublisherByName(@PathVariable String name){
        return publisherService.findByName(name);
    }
}

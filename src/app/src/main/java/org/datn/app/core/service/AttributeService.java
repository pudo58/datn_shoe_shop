package org.datn.app.core.service;

import org.datn.app.core.dto.AttributeAddAllRequest;
import org.datn.app.core.dto.DeleteAttributeRequest;
import org.datn.app.core.entity.Attribute;
import org.datn.app.core.service.base.ServiceCore;

import java.util.List;

public interface AttributeService extends ServiceCore<Attribute,Long> {
    Attribute findByName(String name);
    List<Attribute> addAll(AttributeAddAllRequest attributeAddAllRequest);
    List<Attribute> findByCategoryId(Long categoryId);
    void deleteAttributeByAttributeId(DeleteAttributeRequest request);
}

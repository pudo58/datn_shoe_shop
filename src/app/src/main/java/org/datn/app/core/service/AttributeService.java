package org.datn.app.core.service;

import org.datn.app.core.entity.Attribute;
import org.datn.app.core.service.base.ServiceCore;

public interface AttributeService extends ServiceCore<Attribute,Long> {
    Attribute findByName(String name);
}

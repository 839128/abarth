package org.aoju.bus.example.service.impl;

import org.aoju.bus.example.entity.CommonVersion;
import org.aoju.bus.example.mapper.CommonVersionMapper;
import org.aoju.bus.example.service.CommonVersionService;
import org.aoju.bus.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommonVersionServiceImpl extends BaseServiceImpl<CommonVersionMapper, CommonVersion>
        implements CommonVersionService {

}

package org.miaixz.bus.example.service.impl;

import org.miaixz.bus.base.service.DefaultService;
import org.miaixz.bus.example.entity.CommonVersion;
import org.miaixz.bus.example.mapper.CommonVersionMapper;
import org.miaixz.bus.example.service.CommonVersionService;
import org.springframework.stereotype.Service;

@Service
public class CommonVersionServiceImpl extends DefaultService<CommonVersionMapper, CommonVersion>
        implements CommonVersionService {

}

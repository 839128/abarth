package org.miaixz.bus.example.service.impl;

import org.miaixz.bus.base.service.DefaultService;
import org.miaixz.bus.example.entity.CommonVersion;
import org.miaixz.bus.example.mapper.CommonVersionMapper;
import org.miaixz.bus.example.service.CommonPreviewService;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;

@Service
public class CommonPreviewServiceImpl extends DefaultService<CommonVersionMapper, CommonVersion>
        implements CommonPreviewService {

    @Override
    public void preview(InputStream inputStream, OutputStream outputStream, String type, String filename) {

    }

}

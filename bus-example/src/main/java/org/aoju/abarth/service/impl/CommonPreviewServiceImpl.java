package org.aoju.abarth.service.impl;

import org.aoju.abarth.entity.CommonVersion;
import org.aoju.abarth.mapper.CommonVersionMapper;
import org.aoju.abarth.service.CommonPreviewService;
import org.aoju.bus.base.service.impl.BaseServiceImpl;
import org.aoju.bus.core.utils.FileUtils;
import org.aoju.bus.office.Provider;
import org.aoju.bus.office.Registry;
import org.aoju.bus.office.magic.family.DefaultFormatRegistry;
import org.aoju.bus.starter.preview.PreviewProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;

@Service
public class CommonPreviewServiceImpl extends BaseServiceImpl<CommonVersionMapper, CommonVersion>
        implements CommonPreviewService {

    @Autowired
    PreviewProviderService previewProviderService;

    @Override
    public void preview(InputStream inputStream, OutputStream outputStream, String type, String filename) {
        Provider provider = previewProviderService.get(Registry.LOCAL);
        provider.convert(inputStream)
                .as(DefaultFormatRegistry.getFormatByExtension(FileUtils.getExtension(filename)))
                .to(outputStream)
                .as(DefaultFormatRegistry.getFormatByExtension(type))
                .execute();

    }

}

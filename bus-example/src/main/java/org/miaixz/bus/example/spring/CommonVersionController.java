package org.miaixz.bus.example.spring;

import org.miaixz.bus.base.spring.BaseController;
import org.miaixz.bus.core.basics.normal.ErrorCode;
import org.miaixz.bus.core.xyz.StringKit;
import org.miaixz.bus.example.entity.CommonVersion;
import org.miaixz.bus.example.service.CommonVersionService;
import org.miaixz.bus.oauth.Provider;
import org.miaixz.bus.oauth.Registry;
import org.miaixz.bus.sensitive.Builder;
import org.miaixz.bus.sensitive.magic.annotation.Sensitive;
import org.miaixz.bus.starter.jdbc.DataSourceHolder;
import org.miaixz.bus.starter.oauth.AuthProviderService;
import org.miaixz.bus.starter.storage.StorageProviderService;
import org.miaixz.bus.validate.magic.annotation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 版本管理
 */
@RestController
@RequestMapping("version")
public class CommonVersionController extends BaseController<CommonVersionService, CommonVersion> {

    @Autowired
    AuthProviderService authProviderService;
    @Autowired
    StorageProviderService storageProviderService;

    /**
     * 获取版本信息
     *
     * @param entity 版本信息
     * @return json
     */
    @RequestMapping(value = "/gets")
    @Sensitive(value = Builder.SENS, stage = Builder.OUT)
    public Object gets(@Valid(skip = {"url"}) CommonVersion entity) {
        if (StringKit.isEmpty(entity.getScope()) && StringKit.isEmpty(entity.getVersion())) {
            return write(ErrorCode.EM_100506);
        }
        Provider p = authProviderService.require(Registry.DINGTALK);
        p.authorize("参数");
        CommonVersion param = new CommonVersion();
        param.setScope(entity.getScope());
        param.setType(entity.getType());
        param.setVersion(entity.getVersion());
        //CommonVersion co = service.selectOne(param);
        param.setPageNo(1);
        param.setPageSize(10);
        DataSourceHolder.setKey("ds1");
        return write(service.page(param));
    }

}

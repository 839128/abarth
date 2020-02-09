package org.aoju.abarth.spring;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aoju.abarth.entity.CommonVersion;
import org.aoju.abarth.service.CommonVersionService;
import org.aoju.bus.base.consts.ErrorCode;
import org.aoju.bus.base.entity.Result;
import org.aoju.bus.base.spring.BaseController;
import org.aoju.bus.core.utils.StringUtils;
import org.aoju.bus.oauth.Provider;
import org.aoju.bus.oauth.Registry;
import org.aoju.bus.sensitive.Builder;
import org.aoju.bus.sensitive.annotation.Sensitive;
import org.aoju.bus.starter.druid.DataSourceHolder;
import org.aoju.bus.starter.oauth.AuthProviderService;
import org.aoju.bus.starter.storage.StorageProviderService;
import org.aoju.bus.storage.Context;
import org.aoju.bus.validate.annotation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("version")
@Api(value = "版本管理", tags = "CommonVersionController")
public class CommonVersionController extends BaseController<CommonVersionService, CommonVersion> {

    @Autowired
    AuthProviderService authProviderService;
    @Autowired
    StorageProviderService storageProviderService;

    @RequestMapping(value = "/gets")
    @ApiOperation(value = "获取版本信息", notes = "所属域或版本号", response = CommonVersion.class)
    @Sensitive(value = Builder.SENS, stage = Builder.OUT)
    public Object gets(@Valid(skip = {"url"}) CommonVersion entity) {
        if (StringUtils.isEmpty(entity.getScope()) && StringUtils.isEmpty(entity.getVersion())) {
            return write(ErrorCode.EM_100506);
        }
        Map<org.aoju.bus.storage.Registry, Context> map = storageProviderService.properties.getType();

        Provider p = authProviderService.get(Registry.DINGTALK);
        p.authorize("参数");
        CommonVersion param = new CommonVersion();
        param.setScope(entity.getScope());
        param.setType(entity.getType());
        param.setVersion(entity.getVersion());
        //CommonVersion co = service.selectOne(param);
        param.setPageNo(1);
        param.setPageSize(10);
        DataSourceHolder.setKey("ds1");
        Result<CommonVersion> list = service.page(param);
        return write(list);
    }

}

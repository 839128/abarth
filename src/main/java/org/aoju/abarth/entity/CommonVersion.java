package org.aoju.abarth.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.aoju.bus.base.entity.BaseEntity;
import org.aoju.bus.sensitive.Builder;
import org.aoju.bus.sensitive.annotation.Privacy;
import org.aoju.bus.sensitive.annotation.Sensitive;
import org.aoju.bus.sensitive.annotation.Shield;
import org.aoju.bus.validate.annotation.NotNull;

import javax.persistence.Table;

@Data
@Sensitive(value = Builder.SAFE)
@Table(name = "hi_common_version")
public class CommonVersion extends BaseEntity {

    @ApiModelProperty(value = "名称")
    @Privacy
    @Shield(type = Builder.Type.NAME)
    private String name;

    @ApiModelProperty(value = "类型")
    @NotNull
    private String type;

    @ApiModelProperty(value = "作用域")
    @NotNull
    private String scope;

    @ApiModelProperty(value = "URL")
    @NotNull
    private String url;
    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "版本信息")
    @NotNull
    private String version;

    @ApiModelProperty(value = "描述")
    private String description;

}

package org.aoju.bus.example.entity;

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

    /**
     * 名称
     */
    @Privacy
    @Shield(type = Builder.Type.NAME)
    private String name;

    /**
     * 类型
     */
    @NotNull
    private String type;

    /**
     * 作用域
     */
    @NotNull
    private String scope;

    /**
     * URL
     */
    @NotNull
    private String url;

    /**
     * 内容
     */
    private String content;

    /**
     * 版本信息
     */
    @NotNull
    private String version;

    /**
     * 描述
     */
    private String description;

}

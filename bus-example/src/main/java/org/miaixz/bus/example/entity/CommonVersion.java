package org.miaixz.bus.example.entity;

import jakarta.persistence.Table;
import lombok.Data;
import org.miaixz.bus.base.entity.BaseEntity;
import org.miaixz.bus.sensitive.Builder;
import org.miaixz.bus.sensitive.magic.annotation.Privacy;
import org.miaixz.bus.sensitive.magic.annotation.Sensitive;
import org.miaixz.bus.sensitive.magic.annotation.Shield;
import org.miaixz.bus.validate.magic.annotation.NotNull;

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

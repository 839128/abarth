package org.aoju.bus.example.entity;

import lombok.Data;
import org.aoju.bus.base.entity.BaseEntity;
import org.aoju.bus.validate.annotation.NotNull;

import javax.persistence.Table;

@Data
@Table(name = "hi_common_version")
public class CommonUser extends BaseEntity {

    @NotNull
    private String name;
    @NotNull
    private String description;

}

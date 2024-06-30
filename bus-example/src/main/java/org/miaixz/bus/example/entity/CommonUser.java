package org.miaixz.bus.example.entity;

import jakarta.persistence.Table;
import lombok.Data;
import org.miaixz.bus.base.entity.BaseEntity;
import org.miaixz.bus.validate.magic.annotation.NotNull;

@Data
@Table(name = "hi_common_version")
public class CommonUser extends BaseEntity {

    @NotNull
    private String name;
    @NotNull
    private String description;

}

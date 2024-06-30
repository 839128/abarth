package org.miaixz.bus.core.lang.test.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExamInfoDict implements Serializable {

    private static final long serialVersionUID = -1L;

    // 主键
    private Integer id; // 可当作题号
    // 试题类型 客观题 0主观题 1
    private Integer examType;
    // 试题是否作答
    private Integer answerIs;

    public Integer getId(final Integer defaultValue) {
        return this.id == null ? defaultValue : this.id;
    }

}

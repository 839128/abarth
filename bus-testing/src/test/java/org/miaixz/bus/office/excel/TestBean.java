package org.miaixz.bus.office.excel;

import lombok.Data;

import java.util.Date;

@Data
public class TestBean {

    private String name;
    private int age;
    private double score;
    private boolean isPass;
    private Date examDate;

    @Data
    public static class OrderExcel {
        private String id;
        private String num;
        private String body;
    }

}

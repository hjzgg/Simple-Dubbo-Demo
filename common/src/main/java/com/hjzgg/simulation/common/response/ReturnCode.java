package com.hjzgg.simulation.common.response;

public enum ReturnCode {

    /**
     * 返回成功
     */
    SUCCESS(0),
    /**
     * 返回失败
     */
    FAILURE(1);

    Integer value;

    ReturnCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
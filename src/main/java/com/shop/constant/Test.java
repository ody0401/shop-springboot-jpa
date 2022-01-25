package com.shop.constant;

import lombok.Getter;

@Getter
public enum Test {

    TOP("top");

    private String top;

    Test(String top) {
        this.top = top;
    }
}

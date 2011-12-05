package com.qc.entity;

public enum PhotoCategory {
    UNDERWEAR("内衣"),
    COAT("上衣外套"),
    HAT("帽子"),
    GLASSES("眼镜"),
    SKIRT("裙子"),
    PANT("裤子"),
    SHOE("鞋子"),
    BAG_WALLET("香包皮夹"),
    ACCESSORY("手表配饰"),
    PERFUME("彩妆香水"),
    SOCKS("袜子"),
    OTHER("配件单品");

    private String text;

    PhotoCategory(String text) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }
}

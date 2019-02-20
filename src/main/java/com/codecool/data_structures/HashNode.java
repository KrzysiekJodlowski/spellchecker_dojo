package com.codecool.data_structures;

public class HashNode<Integer, String> {

    private Integer key;
    private String value;

    public HashNode(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

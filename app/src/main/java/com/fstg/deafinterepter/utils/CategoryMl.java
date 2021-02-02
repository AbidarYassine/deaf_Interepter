package com.fstg.deafinterepter.utils;

public class CategoryMl {
    private String label;
    private float score;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public CategoryMl(String label, float score) {
        this.label = label;
        this.score = score;
    }

    public CategoryMl() {
    }

    @Override
    public String toString() {
        return "CategoryMl{" +
                "label='" + label + '\'' +
                ", score=" + score +
                '}';
    }
}

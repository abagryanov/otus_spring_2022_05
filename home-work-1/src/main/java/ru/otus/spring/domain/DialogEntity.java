package ru.otus.spring.domain;

import java.util.Objects;

public abstract class DialogEntity {
    private int id;
    private String text;

    public DialogEntity() {}

    public DialogEntity(int id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DialogEntity that = (DialogEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "DialogEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

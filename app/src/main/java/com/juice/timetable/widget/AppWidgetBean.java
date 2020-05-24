package com.juice.timetable.widget;

public class AppWidgetBean {
    private Long id;

    private int type;

    public AppWidgetBean(Long id, int type) {
        this.id = id;
        this.type = type;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

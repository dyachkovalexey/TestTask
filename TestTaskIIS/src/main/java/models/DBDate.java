package models;


import com.google.common.base.MoreObjects;

public class DBDate {
    private int id;
    private String depCode;
    private String depJob;
    private String description;

    public DBDate() {

    }

    public DBDate(String depCode, String depJob, String description) {
        this.depCode = depCode;
        this.depJob = depJob;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDepCode() {
        return depCode;
    }

    public String getDepJob() {
        return depJob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ID", this.getId())
                .add("DepCode", this.getDepCode())
                .add("DepJob", this.getDepJob())
                .add("Description", this.getDescription())
                .toString();
    }
}

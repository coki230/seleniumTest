public class Relation {
    private String projectName;
    private String mark;
    private String dept;
    private String ownerName;
    private String isUse;
    private String belong;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public void setValue(String value) {
        if (!value.equals("")) {
            if (projectName == null) {
                projectName = value;
            } else if (mark == null) {
                mark = value;
            } else if (dept == null) {
                dept = value;
            } else if (ownerName == null) {
                ownerName = value;
            } else if (isUse == null) {
                isUse = value;
            } else if (belong == null) {
                belong = value;
            }
        }
    }
}

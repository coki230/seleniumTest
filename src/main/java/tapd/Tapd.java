package tapd;

public class Tapd {
    private String title;
    private String dealMan;
    private String dept;
    private String range;
    private String mark;
    private String namespace;
    private String tapdUrl;
    private String env;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getTapdUrl() {
        return tapdUrl;
    }

    public void setTapdUrl(String tapdUrl) {
        this.tapdUrl = tapdUrl;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDealMan() {
        return dealMan;
    }

    public void setDealMan(String dealMan) {
        this.dealMan = dealMan;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Tapd{" +
                "title='" + title + '\'' +
                ", dealMan='" + dealMan + '\'' +
                ", dept='" + dept + '\'' +
                ", range='" + range + '\'' +
                ", mark='" + mark + '\'' +
                ", namespace='" + namespace + '\'' +
                ", tapdUrl='" + tapdUrl + '\'' +
                ", env='" + env + '\'' +
                ", type='" + type + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}

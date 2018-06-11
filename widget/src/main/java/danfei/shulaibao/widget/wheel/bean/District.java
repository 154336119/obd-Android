package danfei.shulaibao.widget.wheel.bean;

/**
 * 描述：区
 * Created by Lee
 * on 2016/10/9.
 */
public class District {

    /**
     * areaId : 102
     * areaLevel : 3
     * areaName : 长安区
     * areacode : changanqu
     * childrenList : null
     * city : 101
     * parentId : 101
     * province : 100
     * zip : null
     */

    private int areaId;
    private String areaLevel;
    private String areaName;
    private String areacode;
    private Object childrenList;
    private int city;
    private int parentId;
    private int province;
    private Object zip;

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public void setChildrenList(Object childrenList) {
        this.childrenList = childrenList;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public void setZip(Object zip) {
        this.zip = zip;
    }

    public int getAreaId() {
        return areaId;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getAreacode() {
        return areacode;
    }

    public Object getChildrenList() {
        return childrenList;
    }

    public int getCity() {
        return city;
    }

    public int getParentId() {
        return parentId;
    }

    public int getProvince() {
        return province;
    }

    public Object getZip() {
        return zip;
    }
}


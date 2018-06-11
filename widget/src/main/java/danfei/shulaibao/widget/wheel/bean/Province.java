package danfei.shulaibao.widget.wheel.bean;

import java.util.List;

/**
 * 描述：省
 * Created by Lee
 * on 2016/10/9.
 */
public class Province {

    /**
     * areaId : 100
     * areaLevel : 1
     * areaName : 河北省
     * areacode : hebeisheng
     * city : null
     * parentId : 3305
     * province : 3297
     * zip : null
     */

    private int areaId;
    private String areaLevel;
    private String areaName;
    private String areacode;
    private int city;
    private int parentId;
    private int province;
    private Object zip;
    private List<City> childrenList;
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

    public Object getCity() {
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

    public List<City> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<City> childrenList) {
        this.childrenList = childrenList;
    }
}

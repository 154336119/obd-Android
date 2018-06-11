package danfei.shulaibao.widget.wheel.bean;

import java.util.List;

/**
 * 描述：市
 * Created by Lee
 * on 2016/10/9.
 */
public class City {

    /**
     * areaId : 101
     * areaLevel : 2
     * areaName : 石家庄市
     * areacode : shijiazhuangshi
     * city : null
     * parentId : 3297
     * province : 3297
     * zip : null
     */

    private int areaId;
    private String areaLevel;
    private String areaName;
    private String areacode;
    private Object city;
    private int parentId;
    private int province;
    private Object zip;
    private List<District> childrenList;
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

    public void setCity(Object city) {
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

    public List<District> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<District> childrenList) {
        this.childrenList = childrenList;
    }
}

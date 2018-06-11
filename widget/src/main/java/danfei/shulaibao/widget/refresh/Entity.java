package danfei.shulaibao.widget.refresh;

/**
 * 作者:刁剑
 * 时间:2016/9/22
 * 注释:
 */
public class Entity {
    String name;
    String token;

    public Entity(String name,String token){
        this.name=name;
        this.token=token;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

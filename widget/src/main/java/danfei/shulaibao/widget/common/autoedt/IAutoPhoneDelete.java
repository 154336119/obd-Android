package danfei.shulaibao.widget.common.autoedt;

import java.util.List;

/**
 * 刁剑
 * 2017/7/20
 * 注释:
 */

public interface IAutoPhoneDelete {
    /**删除选项*/
    void delete(List<PhoneContactEntity> list);
    /**文字改变自动适配*/
    void textChangeFilter(List<PhoneContactEntity> list);
}

package cn.zb.base.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zb.base.annotation.SoftDeleteable;
import cn.zb.base.entity.BaseEntity;
import cn.zb.base.entity.BaseUnionKey;
import cn.zb.base.entity.EntityUtil;
import cn.zb.base.model.Encryptable;
import cn.zb.base.model.ObjectDifference;
import cn.zb.base.result.ResultStatus;
import cn.zb.base.service.BaseService;
import cn.zb.exception.OperNeedAuditException;
import cn.zb.utils.ClassUtils;

/**
 * 
 * @ClassName:  IBaseController0   
 * @Description:controller 基础类的业务实现   
 * @author: 陈军
 * @date:   2019年2月19日 上午9:51:34   
 *   
 * @param <S>
 * @param <E>
 * @param <ID>  
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface IBaseController0<S extends BaseService<E, ID>, E extends BaseEntity<ID>, ID extends Serializable>
        extends CommonController {
    /**
     * 获取控制层对应的service
     * 
     * @return
     */
    S getService();

    /**
     * 
     * @Title: findById   
     * @Description: 根据id查询，如果id是基本类型 如string int等的情况可以直接使用，如果id是一个对象/联合主键时，可以重写该方法，
     *               将请求方式修改为post，id的注解修改为RequestBody，即可
     * @PostMapping("findbyid")
        public void findById(HttpServletRequest request, HttpServletResponse response, @RequestBody() CorpKey id) {
            BaseController.super.findById(request, response, id);
        }
     * @author:陈军
     * @date 2019年1月3日 上午10:38:22 
     * @param request
     * @param response
     * @param id      
     * void      
     * @throws
     */
    default void findById(HttpServletRequest request, HttpServletResponse response, ID id) {
        try {
            @SuppressWarnings("unused")
            CallContext callcontext = getServiceController().getCallContext(request);

            E t = getService().findById(id);
            if (getLogger().isDebugEnabled()) {
                getLogger().debug("result:{}", JSONObject.toJSON(t));
            }

            getServiceController().writeSuccessJsonToClient(response, t);
        } catch (Exception e) {
            getLogger().error("查询失败：{},error{}", e.getMessage(), e);
            getServiceController().writeFailDataJsonToClient(response, "查询失败");
        }
    }

    /**
     * 删除某一条记录,可以通过修改service层里的checkDeleteAuth方法来确认当前用户是否有删除该记录的权限,
     * 如果删除的逻辑为软删除,可以重写delete方法进行逻辑删除操作,ID不是基本对象，为自定义对象的情况(联合主键的情况)下可以参考findById
     * 
     * @param request
     * @param response
     * @param id
     */
    @SuppressWarnings("unchecked")
    default void deleteById(HttpServletRequest request, HttpServletResponse response, ID id) {
        try {
            CallContext callcontext = getServiceController().getCallContext(request);

            E t = getService().findById(id);
            // 非空校验
            if (t == null) {
                getLogger().error("不存在id为{}的记录", id);
                getServiceController().writeFailDataJsonToClient(response, "记录不存在");
                return;
            }
            E target = (E) t.cloneEntity();
            // 校验是否有删除的权限
            if (!getService().checkDeleteAuth(t, callcontext)) {
                getLogger().error("当前用户{}没有删除id为{}的权限", callcontext.getUserName(), id);
                getServiceController().writeFailDataJsonToClient(response, "当前用户没有权限删除");
                return;
            }
            // 删除的业务逻辑

            SoftDeleteable softDeleteable = this.getClass().getAnnotation(SoftDeleteable.class);
            // System.out.println(softDeleteable);

            if (softDeleteable == null) {
                // throw new Exception();
                getService().delete(target, callcontext);

            } else {

                Class<?> tc = t.getClass();

                Field f = ClassUtils.getField(tc, softDeleteable.fieldName());

                Object status = t.getField(f);

                int deleteStatus = softDeleteable.deleteStatus();

                if (status != null && status.equals(deleteStatus)) {
                    getServiceController().writeFailDataJsonToClient(response, "已废弃的对象不能重复废弃");
                    return;

                }
                target.setField(f, deleteStatus);

                getService().softDelete(target, t, callcontext);

                getServiceController().writeJsonToClient(response, ResultStatus.NEEDAUDIT.getCode(), "操作审核中",
                        "操作成功，进入审核");

                return;

            }

            getServiceController().writeSuccessJsonToClient(response, "删除成功");

        } catch (OperNeedAuditException oe) {
            getServiceController().writeJsonToClient(response, ResultStatus.NEEDAUDIT.getCode(), "操作审核中", "操作成功，进入审核");
            // getServiceController().writeFailDataJsonToClient(response, "操作成功，进入审核");
        } catch (Exception e) {
            getLogger().error("删除失败：{} error{}", e.getMessage(), e);
            getServiceController().writeFailDataJsonToClient(response, "删除失败:" + e.getMessage());
        }
    }

    /**
     * 
     * @Title: save   
     * @Description: 单条记录保存
     * @author:陈军
     * @date 2019年1月3日 上午9:27:53 
     * @param request
     * @param response
     * @param e      
     * void      
     * @throws
     */
    default void save(HttpServletRequest request, HttpServletResponse response, E e) {
        try {
            CallContext callContext = getServiceController().getCallContext(request);

            if (getLogger().isDebugEnabled()) {
                getLogger().debug("result:{}", JSONObject.toJSON(e));
            }

            if (e == null) {
                getLogger().error("获取参数异常");
                getServiceController().writeSuccessJsonToClient(response, "获取保存参数失败");
                return;
            }
            if(e instanceof Encryptable	) {
            	((Encryptable) e).decryptId();
            }
            ID id = e.getId();

            checkFields(e);

            boolean isUnionKey = false;

            Field idField = EntityUtil.getIdField(getService().entityClass());
            EmbeddedId embeddedId = idField.getAnnotation(EmbeddedId.class);

            Class<?> idClass = idField.getType();
            // 主键是联合主键
            isUnionKey = embeddedId != null || BaseUnionKey.class.isAssignableFrom(idClass);

            if (id == null) {
                if (isUnionKey) {
                    getServiceController().writeFailDataJsonToClient(response, "必须初始化联合主键");
                    return;
                }
                // 新增是非空字段的校验
                e.notNull();
                // 字段的合规校验

                // 新增权限的校验
                if (!getService().checkInsertAuth(e, callContext)) {
                    getServiceController().writeSuccessJsonToClient(response, "你没有新增的权限");
                    getLogger().error("当前用户{}没有新增的权限", callContext.getUserName());
                    return;
                }
                // 新增的业务逻辑
                getService().insert(e, callContext);
            } else {
                // 字段的合规校验

                E old = getService().findById(id);

                if (old == null) {
                    if (isUnionKey) {
                        e.notNull();
                        // 字段的合规校验

                        // 新增权限的校验
                        if (!getService().checkInsertAuth(e, callContext)) {
                            getServiceController().writeSuccessJsonToClient(response, "你没有新增的权限");
                            getLogger().error("当前用户{}没有新增的权限", callContext.getUserName());
                            return;
                        }
                        // 新增的业务逻辑
                        getService().insert(e, callContext);
                        getServiceController().writeSuccessJsonToClient(response, "保存成功");
                        return;
                    }
                    // 这里要做下判断 看id的类型是否是 联合主键或者id上有@EmbeddedId注解
                    getLogger().error("不存在id为{}的记录", id);
                    getServiceController().writeFailDataJsonToClient(response, "不存在id为" + id + "的记录");
                    return;
                }
                e.cloneFromOtherNotNull(old);

                List<ObjectDifference> diffrence = e.checkDiffFromOther(old);

                if (getLogger().isDebugEnabled()) {
                    getLogger().debug("saveentity:{},origin:{},differece:{}", JSONObject.toJSON(e),
                            JSONObject.toJSON(old), JSONArray.toJSONString(diffrence));
                }

                /**
                 * 比较字段是否进行了修改
                 */
                if (diffrence.size() == 0) {
                    getServiceController().writeFailDataJsonToClient(response, "未进行修改");
                    return;
                }

                // 修改权限的校验
                if (!getService().checkModifyAuth(old, callContext)) {
                    getLogger().error("当前用户{}没有修改的权限的权限", callContext.getUserName());
                    getServiceController().writeFailDataJsonToClient(response, "你没有操作的权限");
                    return;
                }
                // 修改的业务逻辑
                getService().update(e, old, callContext);
            }

            getServiceController().writeSuccessJsonToClient(response, "保存成功");

        } catch (OperNeedAuditException oe) {
            getServiceController().writeJsonToClient(response, ResultStatus.NEEDAUDIT.getCode(), "操作审核中", "操作成功，进入审核");
        }

        catch (Exception e1) {
            getLogger().error("保存失败：{} error{}", e1.getMessage(), e1);
            getServiceController().writeFailDataJsonToClient(response, "保存失败：" + e1.getMessage());
        }

    }

    /**
     * 
     * @Title: checkFields   
     * @Description: 用来校验接收参数中某些需要校验的字段是否合规,  
     * @author:陈军
     * @date 2019年2月18日 上午9:28:17 
     * @param e
     * @throws Exception      
     * void      
     * @throws
     */
    default void checkFields(E e) throws Exception {

    }

}

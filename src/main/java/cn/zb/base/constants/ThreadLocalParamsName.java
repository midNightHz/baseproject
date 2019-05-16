package cn.zb.base.constants;

public interface ThreadLocalParamsName {

    // 实体类保存时是否返回实体类
    String SAVE_RETURN_ENTITY = "returnEntity";
    // 分页查询时是采用pagehelper还是
    String QUERY_USER_PAGEHELPER = "use_pagehelper";
    // 当前登录用户是否是客户
    String IS_CUSTOM = "isCustom";
    // 对议价表单进行修改的操作是否是审核操作
    String CORP_PROTOCOL_AUDIT = "protocol_audit";
    // 商品图片上传是商品的id
    String IMAGE_UPLOAD_GOODSID = "mapgoodsid";
    // 用户登录的ip
    String USER_LOGIN_IP = "user_login_ip";

}

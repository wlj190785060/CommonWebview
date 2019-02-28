package port;

import bean.ZBJTGetAppInfoRspBean;
import bean.ZBJTGetLocalRspBean;
import bean.ZBJTGetValueFromLocalBean;
import bean.ZBJTGetValueFromLocalRspBean;
import bean.ZBJTModifyUserInfoRspBean;
import bean.ZBJTOpenAppMobileBean;
import bean.ZBJTOpenAppMobileRspBean;
import bean.ZBJTOpenAppShareMenuBean;
import bean.ZBJTOpenAppShareMenuRspBean;
import bean.ZBJTReturnBean;
import bean.ZBJTSelectImageBean;
import bean.ZBJTSelectImageRspBean;
import bean.ZBJTStartRecordRspBean;
import bean.ZBJTUploadFileBean;
import bean.ZBJTUploadFileRspBean;

/**
 * JSSDK具体业务逻辑接口,可在各个项目中通用
 * Created by wanglinjie.
 * create time:2019/2/15  下午3:43
 */
public interface ZBJTJSInterFace {
    /**
     * 打开分享，分享成功后传入回调
     */
    void openAppShareMenu(ZBJTOpenAppShareMenuBean bean, ZBJTOpenAppShareMenuRspBean beanRsp, String callback);

    /**
     * 更新原生分享内容接口
     *
     * @return
     */
    void updateAppShareData(ZBJTOpenAppShareMenuBean bean, ZBJTReturnBean beanRsp, String callback);

    /**
     * 拍照或从手机相册中选择图片，并将结果回传给Js
     *
     * @return
     */
    void selectImage(ZBJTSelectImageBean bean, ZBJTSelectImageRspBean beanRsp, String callback);

    /**
     * 录音
     *
     * @return
     */
    void startRecord(ZBJTStartRecordRspBean beanRsp, String callback);

    /**
     * 获取客户端信息接口
     *
     * @return
     */
    void getAppInfo(ZBJTGetAppInfoRspBean BeanRsp, String callback);

    /**
     * 定位
     *
     * @return
     */
    void getLocation(ZBJTGetLocalRspBean BeanRsp, String callback);

    /**
     * 文件上传
     */
    void uploadFile(ZBJTUploadFileBean bean, ZBJTUploadFileRspBean beanRsp, String callback);

    /**
     * 关闭页面
     *
     * @return
     */
    void closeWindow(ZBJTReturnBean beanRsp, String callback);

    /**
     * 利用客户端进行数据Key-Value存值
     *
     * @return
     */
    void saveValueToLocal(ZBJTGetValueFromLocalBean bean, ZBJTReturnBean beanRsp, String callback);

    /**
     * 利用客户端进行数据Key-Value取值
     */
    void getValueFromLocal(ZBJTGetValueFromLocalRspBean beanRsp, String callback);

    /**
     * 登录
     *
     * @return
     */
    void login(ZBJTReturnBean beanRsp, String callback);

    //TODO 这里用户信息的bean到具体的业务逻辑中去实现

    /**
     * 获取当前用户信息
     *
     * @return
     */
    void getUserInfo(String json, String callback);

    /**
     * 实名认证功能-绑定手机号（调用判断手机绑定前先判断登录状态）
     *
     * @return
     */
    void openAppMobile(ZBJTOpenAppMobileBean bean, ZBJTOpenAppMobileRspBean beanRsp, String callback);

    /**
     * 修改用户相关信息-[收货名称\收货地址] 调用判断用户收货名称修改前先判断登录状态
     *
     * @return
     */
    void modifyUserInfo(ZBJTModifyUserInfoRspBean beanRsp, String callback);
}

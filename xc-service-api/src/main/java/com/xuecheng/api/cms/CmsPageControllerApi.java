package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    //网页分页查询和条件查询   page 当前页     size  每页显示的条数
    //响应结果类型在common模块里，请求参数QueryPageRequest使用这个类型的原因是方便统一维护
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
                    @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
                    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 增加页面
     * @param cmsPage
     * @return
     */
    @ApiOperation("新增页面")
    public CmsPageResult add(CmsPage cmsPage);


    /**
     * 修改页面信息
     *      1、根据ID查询页面
     *      2、根据第一步查询得到的页面，进行修改
     *
     *
     */
    @ApiOperation("通过ID查询页面")
    public CmsPage findById(String pageId);
    @ApiOperation("修改页面")
    public CmsPageResult update(String pageId,CmsPage cmsPage);

    /**
     * 删除页面
     */
    @ApiOperation("删除页面")
    public ResponseResult delet(String pageId);






    @ApiOperation("发布页面")
    public ResponseResult post(String pageId) throws Exception;


    @ApiOperation("修改页面")
    public CmsPageResult save(CmsPage cmsPage) throws Exception;

}

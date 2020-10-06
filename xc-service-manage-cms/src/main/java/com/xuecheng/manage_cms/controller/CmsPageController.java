package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.service.CmsPageService;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {


    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsPageService cmsPageService;


    @Autowired
    PageService pageService;
   /* @RequestMapping("/staticdemo/{page}/{size}")
    @Override
    public QueryResponseResult findList(@PathVariable(value = "page",required = false) int page, @PathVariable(value =  "size",required = false) int size, QueryPageRequest queryPageRequest) {

        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试页面ok");

        List<CmsPage> list = new ArrayList<>();
        list.add(cmsPage);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(999);
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);

        return queryResponseResult;
    }*/

    @RequestMapping("/list/{page}/{size}")
    @Override
    public QueryResponseResult findList(@PathVariable(value = "page",required = false) int page, @PathVariable(value =  "size",required = false) int size, QueryPageRequest queryPageRequest) {

        QueryResponseResult queryResponseResult = cmsPageService.findList(page, size, queryPageRequest);
        return queryResponseResult;
    }


    /**
     * 增加页面
     */
    @PostMapping("/add")
    @Override
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        CmsPageResult add = cmsPageService.add(cmsPage);
        return add;
    }

    /**
     * 修改页面信息
     *      1、根据ID查询页面
     *      2、根据第一步查询得到的页面，进行修改
     *
     *
     */
    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String pageId) {
        CmsPage cmsPage = cmsPageService.findById(pageId);
        return cmsPage;
    }

    @Override
    @PutMapping("/edit/{id}")//这里使用put方法，http 方法中put表示更新
    public CmsPageResult update(@PathVariable("id") String pageId, @RequestBody CmsPage cmsPage) {
        CmsPageResult cmsPageResult = cmsPageService.update(pageId, cmsPage);
        return cmsPageResult;
    }

    /**
     * 删除页面
     * @param pageId
     * @return
     */
    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delet(@PathVariable("id") String pageId) {
        ResponseResult delet = cmsPageService.delet(pageId);
        return delet;
    }

    /**
     * 页面发布
     * @param pageId
     * @return
     */
    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId")String pageId) throws Exception {

        ResponseResult responseResult = pageService.postPage(pageId);

        return responseResult;
    }

    @Override
    @PostMapping("/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        CmsPage one = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(one !=null){
            // one.getPageId = 5a92141cb00ffc5a448ff1a0
            return this.update(one.getPageId(),cmsPage);
        }

        return this.add(cmsPage);
    }
}
package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CmsPageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    /**
     * 分页站点查询
     * 添加查询条件
     *
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {

        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }


        if (page <= 0) {
            page = 1;
        }
        //为了适应mongodb的接口将页码减1
        page = page - 1;
        if (size <= 0) {
            size = 20;
        }
        //分页查询的条件对象
        Pageable pageable = PageRequest.of(page, size);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage cmsPage = new CmsPage();
        if (StringUtils.isNoneEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }

        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        //spring data mongo 提供的分页查找方法， 其中pageable是分页查询的条件
        Page<CmsPage> allPage = cmsPageRepository.findAll(example, pageable);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(allPage.getContent());
        queryResult.setTotal(allPage.getTotalElements());

        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }


    /**
     * 增加页面
     *
     * @param cmsPage
     * @return
     */
    public CmsPageResult add(CmsPage cmsPage)  {
        if (cmsPage == null) {
            //抛出页面为空异常
            //进行了封装
            throw ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //根据唯一索引，页面名称，站点ID，页面路径；去查询
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());

        //如果查不到，就是==null，就执行保存。
        if (cmsPage1 == null) {
            //防止前端传来的带有id，所以设置成null
            cmsPage.setPageId(null);
            CmsPage save = cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, save);
        } else {
            //页面存在，抛出异常类型
            //throw new CustomException(CommonCode.FAIL);
            //进行封装
           throw ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);

        }
    }

    /**
     * 修改页面信息
     * 1、根据ID查询页面
     * 2、根据第一步查询得到的页面，进行修改
     */
    public CmsPage findById(String pageId) {
        //根据id查询
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);

        if (optional.isPresent()) {
            return optional.get();
        }
        //返回空
        return null;
    }

    public CmsPageResult update(String pageId, CmsPage cmsPage) {
        CmsPage one = this.findById(pageId);
        if (one != null) {
            //更新模板ID
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新DataUrl
            one.setDataUrl(cmsPage.getDataUrl());
            //执行更新
            CmsPage save = cmsPageRepository.save(one);
            if (save != null) {
                //返回成功
                CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
                return cmsPageResult;
            }
        }
        //返回失败
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    /**
     * 删除页面
     *
     * @param pageId
     * @return
     */
    public ResponseResult delet(String pageId) {
        CmsPage one = this.findById(pageId);
        if (one != null) {
            cmsPageRepository.deleteById(pageId);
            return new ResponseResult(CommonCode.SUCCESS);
        }

        return new ResponseResult(CommonCode.FAIL);
    }

    public CmsPage findByTemplateId(String id){
        CmsPage byTemplateId = cmsPageRepository.findByTemplateId(id);
        return byTemplateId;
    }
}
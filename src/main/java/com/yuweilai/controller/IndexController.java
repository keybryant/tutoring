package com.yuweilai.controller;

import com.yuweilai.entity.Company;
import com.yuweilai.entity.IndexPic;
import com.yuweilai.entity.TeacherInfo;
import com.yuweilai.entity.UserInfo;
import com.yuweilai.service.ICompanyService;
import com.yuweilai.service.IIndexPicService;
import com.yuweilai.service.IRecom;
import com.yuweilai.service.IUserService;
import com.yuweilai.util.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 87734 on 2017/11/22.
 */
@RestController
public class IndexController {
    private static final Logger logger = Logger.getLogger(IndexController.class);
    @Autowired
    private IRecom recom;
    @Autowired
    private IUserService userService;
    @Autowired
    private IIndexPicService indexPicService;
    @Autowired
    private ICompanyService companyService;
    @RequestMapping(value="/getRecom",method = RequestMethod.GET)
    public @ResponseBody
    Object getRecom(HttpServletRequest request, HttpServletResponse response){
        int pageNum=0;
        int pageSize=2;
        List<TeacherInfo> teacherInfoList=recom.getRecom(pageNum,pageSize);
        if(teacherInfoList.isEmpty()){
            return ResultUtil.build(101,"there are no teacher");//推荐老师或者用户信息为空
        }
        return ResultUtil.ok(teacherInfoList);
    }
    @RequestMapping(value="/getIndexPic",method = RequestMethod.GET)
    public @ResponseBody
    Object getIndexPic(HttpServletRequest request, HttpServletResponse response){
        List<IndexPic> indexPicList=indexPicService.getIndexPic();
        if(!indexPicList.isEmpty()){
            return ResultUtil.ok(indexPicList);
        }else {
            return ResultUtil.build(101,"indexPic is empty");
        }
    }
    @RequestMapping(value="/getCompany",method = RequestMethod.GET)
    public @ResponseBody
    Object getConpany(HttpServletRequest request, HttpServletResponse response){
        Company company=companyService.getCompany();
        return ResultUtil.ok(company);
    }
    @RequestMapping(value="/getIndex",method = RequestMethod.GET)
    public @ResponseBody
    Object getIndex(HttpServletRequest request, HttpServletResponse response) {
        int pageNum=1;
        int pageSize=3;
        List<TeacherInfo> teacherInfoList=recom.getRecom(pageNum,pageSize);
        Company company=companyService.getCompany();
        List<IndexPic> indexPicList=indexPicService.getIndexPic();
        Map resultMap=new HashMap<String,Object>();
        resultMap.put("teacherInfoList",teacherInfoList);
        resultMap.put("indexPicList",indexPicList);
        resultMap.put("company",company);
        return ResultUtil.ok(resultMap);
    }
}

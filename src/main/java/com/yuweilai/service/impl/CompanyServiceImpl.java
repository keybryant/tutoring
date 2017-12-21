package com.yuweilai.service.impl;

import com.yuweilai.controller.IndexController;
import com.yuweilai.dao.CompanyMapper;
import com.yuweilai.entity.Company;
import com.yuweilai.entity.CompanyExample;
import com.yuweilai.service.ICompanyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 87734 on 2017/11/27.
 */
@Service
public class CompanyServiceImpl implements ICompanyService {
    private static final Logger logger = Logger.getLogger(CompanyServiceImpl.class);
    /*
    获取公司信息
     */
    @Autowired
    private CompanyMapper companyMapper;
    @Override
    public Company getCompany() {
        CompanyExample companyExample=new CompanyExample();
        CompanyExample.Criteria criteria=companyExample.createCriteria();
        List<Company> companyList=companyMapper.selectByExample(companyExample);
        logger.info("--------------------------------------------------");
        logger.info(companyList.get(0).getIntroduce()+companyList.get(0).getTel());
        if(!companyList.isEmpty()){
            return companyList.get(0);
        }
        return null;
    }
}

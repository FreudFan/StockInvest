package com.fmy.server.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebAppConfiguration
public class OrgServiceImplTest {

    @Resource
    private OrgServiceImpl orgServiceImpl;

    @Test
    public void getAllPositions() {
    }

//    @Test
//    public void getOrgTree() {
//        orgServiceImpl.getOrgTree();
//    }
}
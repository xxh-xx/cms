package com.xxh.cms.otherArticle.controller;


import com.xxh.cms.otherArticle.service.impl.RegulationServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxh
 * @since 2021-03-06
 */
@Api(value = "RegulationController",tags = "Regulation")
@CrossOrigin
@RestController
@RequestMapping("/regulation")
public class RegulationController {

    private final RegulationServiceImpl regulationService;

    public RegulationController(RegulationServiceImpl regulationService){
        this.regulationService = regulationService;
    }

}

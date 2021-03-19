package com.xxh.cms.users.service.impl;

import com.xxh.cms.users.entity.Persons;
import com.xxh.cms.users.mapper.PersonsMapper;
import com.xxh.cms.users.service.PersonsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxh
 * @since 2021-03-17
 */
@Service
public class PersonsServiceImpl extends ServiceImpl<PersonsMapper, Persons> implements PersonsService {

}

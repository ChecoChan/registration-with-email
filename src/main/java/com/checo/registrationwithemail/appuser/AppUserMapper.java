package com.checo.registrationwithemail.appuser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Mapper
public interface AppUserMapper extends BaseMapper<AppUser> {
}
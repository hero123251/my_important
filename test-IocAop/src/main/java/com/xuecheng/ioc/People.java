package com.xuecheng.ioc;

import com.xuecheng.domain.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class People {

    @Autowired
    User user;
}

package com.xxh.cms.users.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xxh
 */
@Data
public class User implements Serializable {

    private String username;
    private String password;

}

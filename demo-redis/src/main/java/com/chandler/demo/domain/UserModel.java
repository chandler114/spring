package com.chandler.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 未序列化，错误示范
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable {
    private int id;
    private String code;
    private String name;
}

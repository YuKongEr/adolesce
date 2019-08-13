package com.yukong.netty.heart;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author yukong
 * @date 2019-08-09 17:09
 * 数据包封装
 */
@Data
@Accessors(chain = true)
public class Packet implements Serializable {

    /**
     * 长度
     */
    private Integer length;

    /**
     * 类型
     */
    private Character type;

    /**
     * 消息体
     */
    private String content;


    public Integer getLength() {
        return  2 + Optional.ofNullable(content).map( s -> s.getBytes().length).orElse(0);
    }
}

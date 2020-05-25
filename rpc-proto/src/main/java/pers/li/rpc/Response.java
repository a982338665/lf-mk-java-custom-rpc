package pers.li.rpc;

import lombok.Data;

/**
 * RPC响应
 */
@Data
public class Response {

    /**
     * 默认成功
     */
    private int code = 0;
    private String message = "ok";
    private Object data;
}

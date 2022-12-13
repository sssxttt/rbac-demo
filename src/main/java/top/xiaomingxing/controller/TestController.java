package top.xiaomingxing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xiaomingxing.response.ResponseResult;

@RestController
public class TestController {
    @GetMapping("/test")
    public ResponseResult test() {
        return new ResponseResult().ok();
    }
}

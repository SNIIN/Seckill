package cn.edu.xmu.seckill.controller.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class LoginVo {
    @NotNull
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确")
    private String mobile;

    @NotNull
    @Size(min = 32, max = 32, message = "密码未加密")
    private String password;
}

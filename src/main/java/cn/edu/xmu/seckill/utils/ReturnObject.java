package cn.edu.xmu.seckill.utils;

import cn.edu.xmu.seckill.utils.ReturnNo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnObject {
    private ReturnNo returnNo = ReturnNo.SUCCESS;

    private String specMessage = "暂无更多参考信息";

    private Object data = null;

    public ReturnObject(ReturnNo returnNo) {
        this.returnNo = returnNo;
    }
    public ReturnObject(ReturnNo returnNo,Object data) {
        this.returnNo = returnNo;
        this.data = data;
    }

}

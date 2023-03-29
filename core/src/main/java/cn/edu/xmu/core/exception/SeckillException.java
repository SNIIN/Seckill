package cn.edu.xmu.core.exception;


import cn.edu.xmu.core.utils.ReturnNo;
import lombok.Getter;

@Getter
public class SeckillException extends RuntimeException{
    private ReturnNo returnNo;

    public SeckillException(ReturnNo returnNo, String message) {
        super(message);
        this.returnNo = returnNo;
    }

    public SeckillException(ReturnNo returnNo) {
        super(returnNo.getMessage());
        this.returnNo = returnNo;
    }
}

package com.xuecheng.framework.model.response;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
public class QueryResponseResult extends ResponseResult {

    QueryResult queryResult;

    public QueryResponseResult(ResultCode resultCode,QueryResult queryResult){
        super(resultCode);
       this.queryResult = queryResult;
    }

}

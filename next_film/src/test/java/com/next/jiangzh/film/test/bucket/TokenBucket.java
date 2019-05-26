package com.next.jiangzh.film.test.bucket;

public class TokenBucket {

    private int bucketNums = 10;   // 令牌桶的容量
    private int rate = 1;           // 令牌流入速率
    private int nowTokens;         // 当前令牌数量
    private long timestamp = getNowTime();    // 时间



    /*
        判断当前是否有令牌可用
     */
    public boolean hasToken(){
        // 记录当前拿取令牌的时间
        long nowTime = getNowTime();

        // 添加令牌【延迟加载令牌】
        /*
            nowTime : 2020   - 当前正在拿去令牌的时间
            timestamp : 2019 - 最后一次拿取令牌的时间
             (nowTime - timestamp)* rate -> 自从上一次拿取令牌之后，应该新增多少令牌
         */
        nowTokens = nowTokens + (int)((nowTime - timestamp)* rate);

        /*
            上面的内容很容易超出令牌桶上限？
            1、如果超出令牌桶上限，就返回令牌桶上线
            2、如果不足上限，则返回当前令牌数量
         */
        nowTokens = describeTokens(nowTokens);
        System.out.println("当前令牌数量="+nowTokens);
        // 更新最后拿去令牌时间
        timestamp = nowTime;

        // 判断令牌是否多余1个
        if(nowTokens >= 1){
            nowTokens -= 1;
            return true;
        }else{
            return false;
        }
    }

    /*
        判断令牌数是否超出桶的上限
     */
    private int describeTokens(int tokenNum){
        if(bucketNums > tokenNum){
            return tokenNum;
        }else{
            return bucketNums;
        }
    }



    private long getNowTime(){
        return System.currentTimeMillis();
    }



}

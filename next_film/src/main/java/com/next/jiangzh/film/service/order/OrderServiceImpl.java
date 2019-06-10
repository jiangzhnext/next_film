package com.next.jiangzh.film.service.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.next.jiangzh.film.common.utils.ToolUtils;
import com.next.jiangzh.film.config.properties.OrderProperties;
import com.next.jiangzh.film.controller.cinema.vo.CinemaFilmInfoVO;
import com.next.jiangzh.film.controller.cinema.vo.FieldHallInfoVO;
import com.next.jiangzh.film.controller.order.vo.response.OrderDetailResVO;
import com.next.jiangzh.film.controller.order.vo.response.OrderPayResVO;
import com.next.jiangzh.film.controller.order.vo.response.QRCodeResVO;
import com.next.jiangzh.film.dao.entity.FilmOrderT;
import com.next.jiangzh.film.dao.mapper.FilmOrderTMapper;
import com.next.jiangzh.film.example.alipay.trade.config.Configs;
import com.next.jiangzh.film.example.alipay.trade.model.ExtendParams;
import com.next.jiangzh.film.example.alipay.trade.model.GoodsDetail;
import com.next.jiangzh.film.example.alipay.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.next.jiangzh.film.example.alipay.trade.model.result.AlipayF2FPrecreateResult;
import com.next.jiangzh.film.example.alipay.trade.service.AlipayTradeService;
import com.next.jiangzh.film.example.alipay.trade.service.impl.AlipayMonitorServiceImpl;
import com.next.jiangzh.film.example.alipay.trade.service.impl.AlipayTradeServiceImpl;
import com.next.jiangzh.film.example.alipay.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.next.jiangzh.film.example.alipay.trade.utils.ZxingUtils;
import com.next.jiangzh.film.service.cinema.CinemaServiceAPI;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import com.next.jiangzh.film.service.order.bo.OrderPriceBO;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrderServiceImpl implements OrderServiceAPI{

    @Autowired
    private FilmOrderTMapper filmOrderTMapper;

    @Autowired
    private CinemaServiceAPI cinemaServiceAPI;

    @Autowired
    private OrderProperties orderProperties;

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;
    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }


    /*
        检查座位信息
        - 文件信息会在分布式的文件存储 | 对象存储里
        FILE_PATH + seat_address = C:/Users/A/Desktop/seats/cgs.json
     */
    @Override
    public void checkSeats(String fieldId, String seats) throws CommonServiceExcetion, IOException {
        FieldHallInfoVO fieldHallInfoVO = cinemaServiceAPI.describeHallInfoByFieldId(fieldId);
        if(fieldHallInfoVO==null || ToolUtils.isEmpty(fieldHallInfoVO.getSeatFile())){
            throw new CommonServiceExcetion(404,"场次编号不正确");
        }

        String seatsPath = orderProperties.getFilePathPre() + fieldHallInfoVO.getSeatFile();

        // 关闭文件流
        @Cleanup
        BufferedReader bufferedReader = new BufferedReader(
          new FileReader(seatsPath)
        );

        StringBuffer stringBuffer = new StringBuffer();
        String temp = new String();
        while ((temp = bufferedReader.readLine()) != null){
            stringBuffer.append(temp);
        }

        JSONObject jsonObject = JSON.parseObject(stringBuffer.toString());
        // 获取ids节点
        String idsStr = jsonObject.getString("ids");

        /*
            用户购买： 3,11,12
            ids: 1 - 24
         */
        List<String> idsList = Arrays.asList(idsStr.split(","));
        String[] seatArr = seats.split(",");

        for(String seatId : seatArr){
            boolean contains = idsList.contains(seatId);
            if(!contains){
                throw new CommonServiceExcetion(500,"传入的错误信息有误");
            }
        }

    }


    /*
        检查待售卖的座位是否有已售座位信息
     */
    @Override
    public void checkSoldSeats(String fieldId, String seats) throws CommonServiceExcetion {
        String soldSeats = filmOrderTMapper.describeSoldSeats(fieldId);
        /*
            用户购买： 3,11,12
            ids: 1 - 24
         */
        List<String> soldSeatsList = Arrays.asList(soldSeats.split(","));
        String[] seatArr = seats.split(",");

        for(String seatId : seatArr){
            boolean contains = soldSeatsList.contains(seatId);
            if(contains){
                throw new CommonServiceExcetion(500,seatId+" 为已售座位，不能重复销售");
            }
        }
    }

    /*
        根据用户编号，获取用户订单信息列表
     */
    @Override
    public IPage<OrderDetailResVO> describeOrderInfoByUser(int nowPage,int pageSize,String userId) throws CommonServiceExcetion {
        Page<FilmOrderT> page = new Page<>(nowPage,pageSize);
        return filmOrderTMapper.describeOrderDetailsByUser(page,userId);
    }

    /*
        获取二维码地址
     */
    @Override
    public QRCodeResVO describeQRCodeAddress(String orderId) throws CommonServiceExcetion {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uuid",orderId);

        List<FilmOrderT> filmOrders = filmOrderTMapper.selectList(queryWrapper);
        FilmOrderT filmOrderT = null;
        if(filmOrders.size() >=1 ){
            filmOrderT = filmOrders.get(0);
        }else{
            throw new CommonServiceExcetion(404,"未寻找到订单");
        }

        // 获取原始数据
        String oid = filmOrderT.getUuid();
        String orderPrice = filmOrderT.getOrderPrice()+"";
        int soldNum = filmOrderT.getSeatsIds().split(",").length;

        // 二维码地址
        String filePath = trade_precreate(orderId, orderPrice, soldNum);

        QRCodeResVO qrCodeResVO = new QRCodeResVO();
        qrCodeResVO.setOrderId(oid);
        qrCodeResVO.setQRCodeAddress(filePath);

        return qrCodeResVO;
    }

    /*
        获取订单支付状态
     */
    @Override
    public OrderPayResVO describePayResult(String orderId) throws CommonServiceExcetion {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uuid",orderId);

        List<FilmOrderT> filmOrders = filmOrderTMapper.selectList(queryWrapper);
        FilmOrderT filmOrderT = null;
        if(filmOrders.size() >=1 ){
            filmOrderT = filmOrders.get(0);
        }else{
            throw new CommonServiceExcetion(404,"未寻找到订单");
        }

        OrderPayResVO orderPayResVO = new OrderPayResVO();
        orderPayResVO.setOrderId(filmOrderT.getUuid());
        orderPayResVO.setOrderStatus(filmOrderT.getOrderStatus());
        return orderPayResVO;
    }

    // seatIds = 1，2，3，4
    @Override
    public OrderDetailResVO saveOrder(String seatIds, String seatNames, String fieldId, String userId) throws CommonServiceExcetion {

        // sdlfkj-sdjfksdf-sdfjksdf
        String uuid = UUID.randomUUID().toString().replace("-","");

        OrderPriceBO orderPriceBO = filmOrderTMapper.describeFilmPriceByFieldId(fieldId);
        // 单个座位的票价
        double filmPrice = orderPriceBO.getFilmPrice();
        // 销售的座位数 -> 票数
        int seatNum = seatIds.split(",").length;
        // 计算以后的总票价 - > 预留一个问题
        double totalPrice = getTotalPrice(filmPrice,seatNum);

        // 获取filmId
        CinemaFilmInfoVO cinemaFilmInfoVO = cinemaServiceAPI.describeFilmInfoByFieldId(fieldId);

        FilmOrderT filmOrderT = new FilmOrderT();
        filmOrderT.setUuid(uuid);
        filmOrderT.setSeatsName(seatNames);
        filmOrderT.setSeatsIds(seatIds);
        filmOrderT.setOrderUser(Integer.parseInt(userId));
        filmOrderT.setOrderPrice(totalPrice);
        filmOrderT.setFilmPrice(filmPrice);
        filmOrderT.setFilmId(Integer.parseInt(cinemaFilmInfoVO.getFilmId()));
        filmOrderT.setFieldId(Integer.parseInt(fieldId));
        filmOrderT.setCinemaId(Integer.parseInt(orderPriceBO.getCinemaId()));

        filmOrderTMapper.insert(filmOrderT);

        OrderDetailResVO orderDetailResVO = filmOrderTMapper.describeOrderDetailsById(uuid);

        return orderDetailResVO;
    }

    // 计算总票价
    private double getTotalPrice(double filmPrice,int seatNum){
        BigDecimal b1 = new BigDecimal(filmPrice);
        BigDecimal b2 = new BigDecimal(seatNum);

        BigDecimal bigDecimal = b1.multiply(b2);

        // 小数点后取两位，同时四舍五入
        BigDecimal result = bigDecimal.setScale(2, RoundingMode.HALF_UP);

        return result.doubleValue();
    }


    /*
        获取支付二维码地址
     */
    private String trade_precreate(String orderId,String totalAmountStr,int soldNum) throws CommonServiceExcetion{
        String filePath = "";
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = orderId;

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "NextFilm购买电影票扫码消费";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = totalAmountStr;

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买商品"+soldNum+"张电影票，共"+totalAmount+"元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
//        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
//        goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
//        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
//        goodsDetailList.add(goods2);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //                .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                // 需要修改为运行机器上的路径
                filePath = String.format("qr-code/qr-%s.png",
                        response.getOutTradeNo());
                log.info("filePath:" + filePath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, orderProperties.getFilePathPre()+filePath);
                return filePath;

            case FAILED:
                log.error("支付宝预下单失败!!!");
                throw new CommonServiceExcetion(500,"二维码生成错误");

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                throw new CommonServiceExcetion(500,"二维码生成错误");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                throw new CommonServiceExcetion(500,"二维码生成错误");
        }
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

}

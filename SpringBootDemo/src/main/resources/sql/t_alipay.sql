/*
Navicat MySQL Data Transfer

Source Server         : 39.108.107.14
Source Server Version : 50721
Source Host           : 39.108.107.14:3306
Source Database       : hwdb

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-09-17 15:08:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_alipay_refund
-- ----------------------------
DROP TABLE IF EXISTS `t_alipay_refund`;
CREATE TABLE `t_alipay_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trade_no` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '支付宝交易号',
  `out_trade_no` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商户网站唯一订单号',
  `buyer_logon_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户登录ID',
  `fund_change` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '本次退款是否发生了资金变化',
  `refund_fee` decimal(9,2) DEFAULT NULL COMMENT '退款金额',
  `refund_currency` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '退款币种信息',
  `gmt_refund_pay` date DEFAULT NULL COMMENT '退款支付时间',
  `buyer_user_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '买家在支付宝的用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='alipay退款表';

-- ----------------------------
-- Table structure for t_alipay_request
-- ----------------------------
DROP TABLE IF EXISTS `t_alipay_request`;
CREATE TABLE `t_alipay_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `body` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。',
  `subject` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品的标题/交易标题/订单标题/订单关键字等',
  `out_trade_no` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商户网站唯一订单号',
  `timeout_express` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。\r\n注：若为空，则默认为15d。',
  `total_amount` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]',
  `product_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY',
  `goods_type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品主类型：0—虚拟类商品，1—实物类商品\r\n注：虚拟类商品不支持使用花呗渠道',
  `passback_params` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝',
  `promo_params` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '优惠参数',
  `extend_params` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `enable_pay_channels` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '可用渠道，用户只能在指定渠道范围内支付',
  `disable_pay_channels` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '禁用渠道，用户不可用指定渠道支付',
  `store_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商户门店编号。该参数用于请求参数中以区分各门店，非必传项。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='alipay支付';

-- ----------------------------
-- Table structure for t_alipay_response
-- ----------------------------
DROP TABLE IF EXISTS `t_alipay_response`;
CREATE TABLE `t_alipay_response` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notify_time` datetime DEFAULT NULL COMMENT '通知时间',
  `notify_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '通知类型',
  `notify_id` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '通知校验ID',
  `trade_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '支付宝交易号',
  `out_trade_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商户订单号',
  `out_biz_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商户业务号',
  `buyer_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '买家支付宝用户号	',
  `buyer_logon_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '买家支付宝账号',
  `trade_status` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'WAIT_BUYER_PAY	交易创建，等待买家付款\r\nTRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款\r\nTRADE_SUCCESS	交易支付成功\r\nTRADE_FINISHED	交易结束，不可退款',
  `total_amount` decimal(9,2) DEFAULT NULL COMMENT '交易金额	\r\n本次交易支付的订单金额，单位为人民币',
  `receipt_amount` decimal(9,2) DEFAULT NULL COMMENT '实收金额 商家在交易中实际收到的款项，单位为元',
  `invoice_amount` decimal(9,2) DEFAULT NULL COMMENT '开票金额 			\r\n用户在交易中支付的可开发票的金额',
  `buyer_pay_amount` decimal(9,2) DEFAULT NULL COMMENT '付款金额  用户在交易中支付的金额',
  `point_amount` decimal(9,2) DEFAULT NULL COMMENT '集分宝金额  使用集分宝支付的金额',
  `refund_fee` decimal(9,2) DEFAULT NULL COMMENT '总退款金额 退款通知中，返回总退款金额，单位为元，支持两位小数',
  `subject` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '订单标题  商品的标题/交易标题/订单标题/订单关键字等，是请求时对应的参数，原样通知回来',
  `body` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品描述  该订单的备注、描述、明细等。对应请求时的body参数，原样通知回来',
  `gmt_create` datetime DEFAULT NULL COMMENT '交易创建时间 该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss',
  `gmt_payment` datetime DEFAULT NULL COMMENT '交易付款时间  该笔交易的买家付款时间。格式为yyyy-MM-dd HH:mm:ss',
  `gmt_refund` datetime DEFAULT NULL COMMENT '交易退款时间 该笔交易的退款时间。格式为yyyy-MM-dd HH:mm:ss.S',
  `gmt_close` datetime DEFAULT NULL COMMENT '交易结束时间',
  `fund_bill_list` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '支付金额信息',
  `passback_params` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '回传参数',
  `voucher_detail_list` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '优惠券信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='alipay支付返回';

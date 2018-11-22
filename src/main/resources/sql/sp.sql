CREATE PROCEDURE `sp_rpt_order_day`(IN `orderDate` vARCHAR(50))
	LANGUAGE SQL
	NOT DETERMINISTIC
	CONTAINS SQL
	SQL SECURITY DEFINER
	COMMENT '基于订单的销售日报：交易量、成交量、金额'
BEGIN

declare queryDate varchar(50) default curDate();
declare total_count decimal(15,2);
declare payed_count decimal(15,2);
declare payed_fee_sum decimal(15,2);

IF orderDate is not null THEN
	set queryDate = orderDate;
END IF;


select  count(*) into total_count from t_order a where date_format(a.createTime,'%Y%m%d') = date_format(queryDate,'%Y%m%d');
select  count(*),sum(a.fee) into payed_count,payed_fee_sum from t_order a where date_format(a.payTime,'%Y%m%d') = date_format(queryDate,'%Y%m%d');

if payed_fee_sum is null then
	set payed_fee_sum = 0;
end if;

select total_count,payed_count,payed_fee_sum;


END
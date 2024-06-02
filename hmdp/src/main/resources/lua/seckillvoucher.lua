-- 时间字符串转化为时间戳（秒）
local function parseDateTime(dateTimeStr)
    local year, month, day, hour, min, sec = dateTimeStr:match("(%d+)-(%d+)-(%d+)T(%d+):(%d+):(%d+)")
    if not year or not month or not day or not hour or not min or not sec then
        -- 匹配失败，返回nil或处理错误
        return nil
    end
    year, month, day, hour, min, sec = tonumber(year), tonumber(month), tonumber(day), tonumber(hour), tonumber(min), tonumber(sec)

    -- 检查闰年
    local function isLeapYear(y)
        return (y % 4 == 0 and y % 100 ~= 0) or (y % 400 == 0)
    end

    -- 每个月的天数，考虑闰年2月有29天
    local daysInMonth = {31, isLeapYear(year) and 29 or 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}

    -- 计算到1970年以来的天数
    local days = day - 1 -- 当月已过的天数
    for y = 1970, year - 1 do
        days = days + (isLeapYear(y) and 366 or 365)
    end
    for m = 1, month - 1 do
        days = days + daysInMonth[m]
    end

    -- 计算总秒数
    local seconds = (((days * 24 + hour) * 60 + min) * 60 + sec)

    return seconds
end

-- 查询优惠券是否存在
local seckillVoucherKey = KEYS[1]
-- 优惠券存在返回1
if (redis.call('EXISTS', seckillVoucherKey) ~= 1) then
    return nil;
end

-- 当前时间（时间戳，单位：秒）
local currentTime = redis.call('TIME')[1];

-- 查询秒杀是否开始
local beginTimeStr = redis.call('HGET', seckillVoucherKey, 'beginTime');
local beginTime = parseDateTime(beginTimeStr);

if (currentTime - beginTime < 0) then
    return nil;
end

-- 查询秒杀是否结束
local endTimeStr = redis.call('HGET', seckillVoucherKey, 'endTime');
local endTime = parseDateTime(endTimeStr);

if (currentTime - endTime > 0) then
    return nil;
end

-- 查询库存
local stock = tonumber(redis.call('HGET', seckillVoucherKey, 'stock'));
if (stock <= 0) then
    return nil;
end

-- 一人一单
local seckillVoucherUserSetKey = KEYS[2];
local userId = ARGV[1];
if (redis.call('SISMEMBER', seckillVoucherUserSetKey, userId) == 1) then
    return nil;
end

-- 扣库存
redis.call('HINCRBY', seckillVoucherKey, 'stock', -1);

-- 用户下单
redis.call('SADD', seckillVoucherUserSetKey, userId);

-- 添加到消息队列
local createOrderMessageQueueKey = KEYS[3];

local voucherId = ARGV[2];
local orderId = ARGV[3];
redis.call('XADD', createOrderMessageQueueKey, '*', 'userId', userId, 'voucherId', voucherId, 'orderId', orderId);

return 0;


-- 锁的key
local key = KEYS[1]
-- 当前线程标识
local threaId = ARGV[1]

if (redis.call('get', key) == threaId) then
    return redis.call('del', key)
end

return 0
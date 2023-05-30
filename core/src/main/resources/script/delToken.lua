local token1 = redis.call('GET', KEYS[1])

if token1 then
    redis.call('DEL', token1)
end
redis.call('SET', KEYS[2], ARGV[1])
redis.call('SET', KEYS[1], KEYS[2])
redis.call('EXPIRE', KEYS[1], "3600")
redis.call('EXPIRE', KEYS[2], "3600")
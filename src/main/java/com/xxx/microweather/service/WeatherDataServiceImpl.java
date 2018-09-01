package com.xxx.microweather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.microweather.vo.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author xin
 * @date 2018/8/30 22:41
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService{

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);

    private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final long TIMEOUT = 1800L;

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String uri = WEATHER_URI + "citykey=" + cityId;
        /**
         * 这个破api竟然没有指定content-type,默认是application/octet-stream，没有对应的messasge converter
         * 所以不能直接使用下面的方式
         * return restTemplate.getForObject(uri, WeatherResponse.class);
         */

        return doGetWeatherResponse(uri);
    }

    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String uri = WEATHER_URI + "city=" + cityName;
        return doGetWeatherResponse(uri);
    }

    private WeatherResponse doGetWeatherResponse(String uri) {
        String key = uri;
        String respString = null;
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        // first check cache
        if (stringRedisTemplate.hasKey(key)) {
            logger.info("Redis has data");
            respString = ops.get(key);
        } else {
            logger.info("Redis has no data");
            // if no cache, get from api and write cache.
            respString = restTemplate.getForObject(uri, String.class);
            ops.set(key, respString, TIMEOUT, TimeUnit.SECONDS);
        }

        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse response = null;

        try {
            response = mapper.readValue(respString, WeatherResponse.class);
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error("", e);
        }

        return response;
    }
}

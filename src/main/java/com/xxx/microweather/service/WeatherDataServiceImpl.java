package com.xxx.microweather.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.microweather.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author xin
 * @date 2018/8/30 22:41
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService{

    private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";

    @Autowired
    private RestTemplate restTemplate;

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
        String respString = restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse response = null;

        try {
            response = mapper.readValue(respString, WeatherResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}

package com.xxx.microweather.service;

import com.xxx.microweather.vo.WeatherResponse;

/**
 * Weather data service.
 * @author xin
 * @date 2018/8/30 22:37
 */
public interface WeatherDataService {

    /**
     * 根据城市ID查询天气数据
     * @param cityId
     * @return
     */
    WeatherResponse getDataByCityId(String cityId);

    /**
     * 根据城市名称查询天气数据
     * @param cityName
     * @return
     */
    WeatherResponse getDataByCityName(String cityName);

    /**
     * 根据城市ID同步天气
     * @param cityId
     */
    void syncDataByCityId(String cityId);
}

package com.xxx.microweather.service;

import com.xxx.microweather.vo.City;

import java.util.List;

/**
 * @author xin
 * @date 2018/9/1 22:49
 */
public interface CityDataService {

    List<City> listCity() throws Exception;
}

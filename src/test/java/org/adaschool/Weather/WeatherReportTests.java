package org.adaschool.Weather;

import org.adaschool.Weather.controller.WeatherReportController;
import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherReportTests {

    // Variables para el test del controlador
    @Mock
    private WeatherReportService weatherReportService;

    @InjectMocks
    private WeatherReportController weatherReportController;

    // Variables para el test del servicio
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherReportService weatherReportServiceInjected;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para WeatherReportController
    @Test
    public void testGetWeatherReportController() {
        // Arrange
        double latitude = 37.8267;
        double longitude = -122.4233;
        WeatherReport mockWeatherReport = new WeatherReport();
        mockWeatherReport.setTemperature(25.0);
        mockWeatherReport.setHumidity(60);

        // Simulamos la respuesta del servicio
        when(weatherReportService.getWeatherReport(latitude, longitude)).thenReturn(mockWeatherReport);

        // Act
        WeatherReport result = weatherReportController.getWeatherReport(latitude, longitude);

        // Assert
        assertNotNull(result);
        assertEquals(mockWeatherReport.getTemperature(), result.getTemperature());
        assertEquals(mockWeatherReport.getHumidity(), result.getHumidity());
        verify(weatherReportService, times(1)).getWeatherReport(latitude, longitude);
    }

    // Test para WeatherReportService
    @Test
    public void testGetWeatherReportService() {
        // Arrange
        double latitude = 37.8267;
        double longitude = -122.4233;
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=f70ebde3b7b30aad4722d298c4f63df2";

        WeatherApiResponse mockApiResponse = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemperature(25.0);
        main.setHumidity(60);
        mockApiResponse.setMain(main);

        when(restTemplate.getForObject(url, WeatherApiResponse.class)).thenReturn(mockApiResponse);

        // Act
        WeatherReport result = weatherReportServiceInjected.getWeatherReport(latitude, longitude);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTemperature());
        assertEquals(87, result.getHumidity());
    }
}

package com.company;

import java.util.ArrayList;

public class Main {

    public interface Observer { //интерфейс наблюдателя, с помощью которого наблюдатель получает оповещение
        public void update(float temp, float humidity, float pressure);
    }

    public interface Subject { //интерфейс субъекта, определяющий методы для добавления, удаления и оповещения наблюдателей;
        public void registerObserver(Observer o);
        public void removeObserver(Observer o);
        public void notifyObservers();
    }

    public static class WeatherData implements Subject { //класс реализуюший субъект, рассылатель событий
        private ArrayList<Observer> observers;
        private float temperature;
        private float humidity;
        private float pressure;

        public WeatherData() { observers = new ArrayList<>(); }

        public void registerObserver(Observer o) {
            observers.add(o);
        }

        public void removeObserver(Observer o) {
            int i = observers.indexOf(o);
            if (i >= 0) {
                observers.remove(i);
            }
        }

        public void notifyObservers() {
            for (Observer value : observers) value.update(temperature, humidity, pressure);
        }

        public void measurementsChanged() { notifyObservers(); }

        public void setMeasurements(float temperature, float humidity, float pressure) {
            this.temperature = temperature;
            this.humidity = humidity;
            this.pressure = pressure;
            measurementsChanged();
        }
    }

    public interface DisplayElement {
        public void display();
    }

    public static class CurrentConditionsDisplay implements Observer, DisplayElement { //слушатель
        private float temperature;
        private float humidity;

        public CurrentConditionsDisplay(Subject weatherData) {
            weatherData.registerObserver(this);
        }

        public void update(float temperature, float humidity, float pressure) {
            this.temperature = temperature;
            this.humidity = humidity;
            display();
        }

        public void display() {
            System.out.println("Текущие условия: " + temperature
                    + " градусов и " + humidity + "% влажности");
        }
    }

    public static class ForecastDisplay implements Observer, DisplayElement { //слушатель
        private float currentPressure = 29.92f;
        private float lastPressure;

        public ForecastDisplay(WeatherData weatherData) {
            weatherData.registerObserver(this);
        }

        public void update(float temp, float humidity, float pressure) {
            lastPressure = currentPressure;
            currentPressure = pressure;
            display();
        }

        public void display() {
            System.out.print("Прогноз: ");
            if (currentPressure > lastPressure) {
                System.out.println("Улучшение погоды в пути!");
            } else if (currentPressure == lastPressure) {
                System.out.println("Больше того же");
            } else if (currentPressure < lastPressure) {
                System.out.println("Остерегайтесь более прохладной дождливой погоды");
            }
        }
    }



    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }
}

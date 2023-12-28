//
//  ContentView.swift
//  WeatherApp
//
//  Created by kanagasabapathy on 16/12/23.
//

import SwiftUI

struct ContentView: View {
    @StateObject private var viewModel = WeatherViewModel()
    @State private var city = "San Francisco"
    var body: some View {
        WeatherView(weatherList: $viewModel.weatherList)
        TextField("Enter city", text: $city)
        Button("Get Weather") {
            fetch()
        }
    }
    private func fetch() {
        viewModel.fetchWeather(for: city)
    }
}

struct WeatherView: View {
    @Binding var weatherList: [Weather]
    var formatter = defaultFormatter
    var body: some View {
        List {
            LazyVStack {
                ForEach(weatherList) { weather in
                    Text(weather.cityName)
                        .customizedView()
                        .background(Color.green)
                    Text(weather.temp)
                        .customizedView()
                        .background(Color.red)
                    Text(weather.desc)
                        .customizedView()
                        .background(Color.blue)
                    Text(weather.newUpdateTime)
                }
            }
        }
    }
}
private extension WeatherView {
    static let defaultFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "MM-dd-yyyy"
        return formatter
    }()
}

#Preview {
    ContentView()
}

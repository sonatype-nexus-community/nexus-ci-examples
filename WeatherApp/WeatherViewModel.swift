//
//  WeatherViewModel.swift
//  WeatherApp
//
//  Created by kanagasabapathy on 16/12/23.
//

import Foundation
import Combine

struct Weather: Codable, Identifiable {
    var id: String
    let cityName: String
    let temparature: Int
    let description: String
    let updateTime: Date

    enum CodingKeys: String, CodingKey {
        case id
        case cityName = "city_name"
        case temparature
        case description
        case updateTime
    }
    var desc: String {
         "Description: \(description)"
    }
    var temp: String {
        "\(temparature)C"
    }
    var newUpdateTime: String {
        updateTime.stringFromDate()
    }
}

extension Date {
    func stringFromDate(format: CustomFormatter = .MMddyyyy) -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = format.rawValue
        return formatter.string(from: self)
    }
    enum CustomFormatter: String {
        case MMddyyyy = "MM-dd-yyyy"
        case MMdd = "MM-dd"
    }
}

final class WeatherViewModel: ObservableObject {
    private let urlSession: URLSession
    @Published var weatherList: [Weather] = [
        Weather(
            id: "1", cityName: "Prague",
            temparature: 2,
            description: "Ss",
            updateTime: .now
        ),
        Weather(
            id: "2", cityName: "Bangalore",
            temparature: 2,
            description: "YY",
            updateTime: .now
        )
    ]
    init(urlSession: URLSession = URLSession.shared) {
        self.urlSession = urlSession
    }
    private var cancellables = Set<AnyCancellable>()
    func fetchWeather(for city: String) {
        let urlString = Endpoint.baseUrl + "\(weatherList.count + 1)"
        guard let url = URL(string: urlString) else { return } // 1
        urlSession.dataTaskPublisher(for: url)
            .map(\.data)
            .decode(type: Weather.self, decoder: JSONDecoder())
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { result in
                print(result)
            }, receiveValue: { [weak self] weather in
                self?.weatherList.append(weather)
            }).store(in: &cancellables)
    }
}

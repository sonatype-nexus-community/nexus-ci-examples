//
//  WeatherAppUITests.swift
//  WeatherAppUITests
//
//  Created by kanagasabapathy on 16/12/23.
//

import XCTest

final class WeatherAppUITests: XCTestCase {

    func testLaunchPerformance() throws {
        if #available(macOS 10.15, iOS 13.0, tvOS 13.0, watchOS 7.0, *) {
            // This measures how long it takes to launch your application.
            measure(metrics: [XCTApplicationLaunchMetric()]) {
                XCUIApplication().launch()
            }
        }
    }
}

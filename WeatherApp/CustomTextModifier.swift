//
//  CustomTextModifier.swift
//  WeatherApp
//
//  Created by kanagasabapathy on 21/12/23.
//

import Foundation
import SwiftUI

struct CustomTextModifier: ViewModifier {
    func body(content: Content) -> some View {
        content
            .padding()
            .cornerRadius(10)
    }
}
extension View {
    func customizedView() -> some View {
        modifier(CustomTextModifier())
    }
}

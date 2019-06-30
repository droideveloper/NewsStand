//
//  AuthInterceptor.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

class AuthInterceptor: Interceptor {
	
	private let X_API_KEY = "X-Api-Key"
	private let X_API_VALUE = "a94251d2d4954105ba74bbb91e37f424"
	
	func intercept(_ request: URLRequest) -> URLRequest {
		var proxy = request
		proxy.addValue(X_API_VALUE, forHTTPHeaderField: X_API_KEY)
		return proxy
	}
}

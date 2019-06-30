//
//  EndpointRequestable.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

enum EndpointRuqestable: Requestable {
	case source
	case news(String, Int?)
	
	var baseUrl: String {
		get {
			return "https://newsapi.org/v2"
		}
	}
	
	var request: URLRequest {
		get {
			switch self {
				case .source:
					return create(url: "\(baseUrl)/sources", httpMethod: .get)
				case .news(let source, let page):
					if let page = page {
						return create(url: "\(baseUrl)/top-headlines?sources=\(source)&page=\(page)", httpMethod: .get)
					}
					return create(url: "\(baseUrl)/top-headlines?sources=\(source)", httpMethod: .get)
			}
		}
	}
}

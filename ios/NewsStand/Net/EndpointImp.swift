//
//  EndpointImp.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift
import Alamofire
import MVICocoa

class EndpointImp: Endpoint {
	
	private let auth: Interceptor
	
	init(auth: Interceptor) {
		self.auth = auth
	}
	
	func sources() -> Observable<Response<[Source]>> {
		let method = EndpointRuqestable.source
		let request = auth.intercept(method.request)
		return Alamofire.request(request)
			.serialize()
			.debug(with: request)
	}
	
	func news(source: String, page: Int?) -> Observable<Response<[News]>> {
		let method = EndpointRuqestable.news(source, page)
		let request = auth.intercept(method.request)
		return Alamofire.request(request)
			.serialize()
			.debug(with: request)
	}
}

extension Observable: Loggable {
	
	public func debug<T>(with request: URLRequest? = nil) -> Observable<Response<T>> where Element == Response<T> {
		#if DEBUG
		if let request = request {
			let requestString = "\(request.httpMethod ?? "") --> \(request.description)"
			if let headers = request.allHTTPHeaderFields {
				headers.forEach { key, value in
					log(.debug, "\(key): \(value)")
				}
			}
			log(.debug, requestString)
			if let body = request.httpBody {
				let payload = String(data: body, encoding: .utf8) ?? ""
				log(.debug, "\(payload)")
			}
		}
		return self.do(onNext: { [weak weakSelf = self] response in
			if let request = request {
				let responseString = "\(request.httpMethod ?? "") <-- \(request.description)"
				weakSelf?.log(.debug, responseString)
			}
			weakSelf?.log(.debug, response.description)
		})
		#else
		return self
		#endif
	}
}


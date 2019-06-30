//
//  EndpointProxyImp.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

class EndpointProxyImp: EndpointProxy {
	
	private let endpoint: Endpoint
	
	init(endpoint: Endpoint) {
		self.endpoint = endpoint
	}
	
	func sources() -> Observable<Resource<[Source]>> {
		return endpoint.sources()
			.applyResources()
	}
	
	func news(source: String, page: Int?) -> Observable<Resource<[News]>> {
		return endpoint.news(source: source, page: page)
			.applyResources()
	}
}

extension Observable {
	
	public func applyResources<T>() -> Observable<Resource<T>> where T: Codable, Element == Response<T> {
		return map { response -> Resource<T> in
			if response.message != nil {
				return .failure(response.code, response.message)
			} else {
				let hasSource = response.sources != nil
				if hasSource {
					return .success(response.sources, nil)
				} else {
					return .success(response.articles, response.totalResults)
				}
			}
		}//.onErrorRetry()
	}
}

extension ObservableType {
	
	func onErrorRetry(with max: Int = 3, and delay: Double = 3) -> Observable<Element> {
		return retryWhen { errors in
			return errors.flatMap { (error: Error) -> Observable<Int> in
				return Observable.error(error)
			}
		}
	}
}

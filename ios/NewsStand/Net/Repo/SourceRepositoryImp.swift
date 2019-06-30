//
//  SourceRepositoryImp.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

class SourceRepositoryImp: SourceRepository {
	
	private let proxy: EndpointProxy
	
	init(proxy: EndpointProxy) {
		self.proxy = proxy
	}
	
	func sources() -> Observable<Resource<[Source]>> {
		return proxy.sources()
	}
}

//
//  EndpointProxy.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

protocol EndpointProxy {
	
	func sources() -> Observable<Resource<[Source]>>
	func news(source: String, page: Int?) -> Observable<Resource<[News]>>
}

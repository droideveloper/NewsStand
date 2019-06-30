//
//  Endpoint.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

protocol Endpoint {
	
	func sources() -> Observable<Response<[Source]>>
	func news(source: String, page: Int?) -> Observable<Response<[News]>>
}

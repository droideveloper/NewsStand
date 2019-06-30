//
//  AppDependency.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import Swinject
import RxSwift
import MVICocoa

class AppDependency: Dependency {
	
	private let container: Container
	
	init(container: Container) {
		self.container = container
	}
	
	func register() {
		// register container
		container.register(Interceptor.self) { _ in
			return AuthInterceptor()
		}.inObjectScope(.container)

		// register navigation
		container.register(Navigation<Source>.self) { _ in
			if UIDevice.isTabletDevice {
				return TabletNavigation()
			} else {
				return HandsetNavigation()
			}
		}.inObjectScope(.container)

		// register endpoint
		container.register(Endpoint.self) { resolver in
			guard let auth = resolver.resolve(Interceptor.self) else {
				fatalError("dependency could not resolved \(Interceptor.self)")
			}
			return EndpointImp(auth: auth)
		}.inObjectScope(.container)

		// registe endpoint proxy
		container.register(EndpointProxy.self) { resolver in
			guard let endpoint = resolver.resolve(Endpoint.self) else {
				fatalError("dependency could not resolved \(Endpoint.self)")
			}
			return EndpointProxyImp(endpoint: endpoint)
		}.inObjectScope(.container)

		// register dao
		container.register(BookmarkDao.self) { resolver in
			return BookmarkDaoImp()
		}.inObjectScope(.container)
		
		// register source repository
		container.register(SourceRepository.self) { resolver in
			guard let proxy = resolver.resolve(EndpointProxy.self) else {
				fatalError("dependency could not resolved \(EndpointProxy.self)")
			}
			return SourceRepositoryImp(proxy: proxy)
		}.inObjectScope(.container)

		container.register(NewsRepository.self) { resolver in
			guard let eProxy = resolver.resolve(EndpointProxy.self) else {
				fatalError("dependency could not resolved \(EndpointProxy.self)")
			}
			guard let dProxy = resolver.resolve(BookmarkDao.self) else {
				fatalError("dependency could not resolved \(BookmarkDao.self)")
			}
			return NewsRepositoryImp(eProxy: eProxy, dProxy: dProxy)
		}.inObjectScope(.container)
	}
}

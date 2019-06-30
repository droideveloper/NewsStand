//
//  NewsRepositoryImp.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

class NewsRepositoryImp: NewsRepository {
	
	private let eProxy: EndpointProxy
	private let dProxy: BookmarkDao
	
	init(eProxy: EndpointProxy, dProxy: BookmarkDao) {
		self.eProxy = eProxy
		self.dProxy = dProxy
	}
	
	func addBookmark(news: News) -> Observable<News> {
		return dProxy.insert(news: news)
			.andThen(Observable.of(news))
	}
	
	func removeBookmark(news: News) -> Observable<News> {
		return dProxy.delete(news: news)
			.andThen(Observable.of(news))
	}
	
	func hasBookmark(url: String) -> Observable<News> {
		return dProxy.hasBookmark(url: url)
	}
	
	func news(source: String, page: Int?) -> Observable<Resource<[News]>> {
		return eProxy.news(source: source, page: page)
	}
}

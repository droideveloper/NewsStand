//
//  NewsRepository.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

protocol NewsRepository {
	func addBookmark(news: News) -> Observable<News>
	func removeBookmark(news: News) -> Observable<News>
	func hasBookmark(url: String) -> Observable<News>
	func news(source: String, page: Int?) -> Observable<Resource<[News]>>
}
